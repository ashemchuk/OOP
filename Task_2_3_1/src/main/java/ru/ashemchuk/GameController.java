package ru.ashemchuk;

import java.util.List;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ru.ashemchuk.engine.GameEngine;
import ru.ashemchuk.engine.GameUpdateListener;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.food.FoodSpec;
import ru.ashemchuk.model.game.Game;
import ru.ashemchuk.model.level.Level;
import ru.ashemchuk.model.level.RobotSpec;
import ru.ashemchuk.view.GameView;

/**
 * Controller for the Snake game UI (MVC pattern).
 * Manages the game lifecycle, level progression, user input, and UI updates.
 * Binds the game model ({@link Game}) with
 * the view ({@link GameView}) via the engine ({@link GameEngine}).
 */
public class GameController {
    /**
     * Predefined levels for the game.
     */
    private static final List<Level> LEVELS = List.of(
        // Level 1: 12x12, speed 250, win length 8, food amount 2,
        // no robots, only apples
        new Level(12, 12, 2, 1, 250, 3, List.of(),
            List.of(new FoodSpec(FoodSpec.FoodType.APPLE, 1))),
        // Level 2: 18x18, speed 180, win length 12, food amount 3,
        // orange robot (RANDOM), apples and grapes
        new Level(18, 18, 3, 1, 180, 4,
            List.of(new RobotSpec(RobotSpec.RobotType.RANDOM, -3, 0)),
            List.of(
                new FoodSpec(FoodSpec.FoodType.APPLE, 2),
                new FoodSpec(FoodSpec.FoodType.GRAPES, 1)
            )),
        // Level 3: 25x25, speed 120, win length 20,food amount 5,
        // blue robot (FOLLOW_PLAYER), all fruits
        new Level(25, 25, 5, 1, 120, 5,
            List.of(new RobotSpec(RobotSpec.RobotType.FOLLOW_PLAYER, -3, 0)),
            List.of(
                new FoodSpec(FoodSpec.FoodType.APPLE, 1),
                new FoodSpec(FoodSpec.FoodType.GRAPES, 1),
                new FoodSpec(FoodSpec.FoodType.POISON, 1)
            ))
    );

    private Game game;
    private GameView gameView;
    private GameEngine gameEngine;
    private int currentLevelIndex = 0;
    private PauseTransition resizeTimer;
    private Level currentLevel;
    private boolean gameStarted = false;
    private boolean resizeListenersAdded = false;
    private ChangeListener<Number> widthListener;
    private ChangeListener<Number> heightListener;
    private boolean adjustingCellSize = false;
    private Scene resizeScene;
    private double previousCellSize = 0;
    private double previousSceneWidth = 0;
    private double previousSceneHeight = 0;
    private int adjustRetryCount = 0;
    private static final int MAX_ADJUST_RETRY = 5;
    private long lastResizeTime = 0;

    @FXML
    private GridPane gameGrid;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label lengthLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private VBox startOverlay;
    @FXML
    private VBox pauseOverlay;
    @FXML
    private VBox winOverlay;
    @FXML
    private VBox gameOverOverlay;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button restartButton;

    /**
     * Initializes the controller after FXML loading.
     * Starts the first level.
     */
    @FXML
    public void initialize() {
        startLevel(currentLevelIndex);
    }

    /**
     * Starts (or restarts) a specific level.
     *
     * @param levelIndex index of the level to start (0‑based)
     */
    private void startLevel(int levelIndex) {
        if (levelIndex >= LEVELS.size()) {
            // All levels completed
            statusLabel.setText("You Win All Levels!");
            return;
        }
        currentLevelIndex = levelIndex;

        // Create game with all levels if not already created
        if (game == null) {
            game = new Game(LEVELS);
        }
        // Set the game to the desired level
        game.setLevel(levelIndex);

        // Create view if not already created
        if (gameView == null) {
            gameView = new GameView(gameGrid, scoreLabel, lengthLabel, levelLabel, statusLabel);
        }
        Level level = LEVELS.get(levelIndex);
        currentLevel = level;
        previousCellSize = 0; // force cell size recalculation
        adjustRetryCount = 0; // reset retry counter
        gameView.setupGrid(level.width(), level.height());

        // Update level label
        levelLabel.setText(String.valueOf(levelIndex + 1));

        // Setup resize listener and adjust cell size after layout
        setupResizeListener();
        javafx.application.Platform.runLater(() -> {
            if (gameGrid.getWidth() > 0 && gameGrid.getHeight() > 0) {
                adjustCellSize();
            } else {
                // grid not yet laid out, try again after the next pulse
                javafx.application.Platform.runLater(this::adjustCellSize);
            }
        });

        // Create engine with listener that updates the view and checks for level completion
        GameUpdateListener listener = state -> {
            gameView.update(state, gameEngine.isPaused());
            // Update overlays based on game state
            updateOverlays(gameEngine.isPaused(), state.win(), state.gameOver());
            if (state.win()) {
                // Advance to next level after a short delay? For simplicity, advance immediately.
                javafx.application.Platform.runLater(() -> advanceLevel());
            }
        };
        if (gameEngine != null) {
            gameEngine.stop();
        }
        gameEngine = new GameEngine(game, listener);
        gameEngine.start();
        // Pause initially and show start overlay
        gameEngine.pause();
        hideAllOverlays();
        if (startOverlay != null) {
            startOverlay.setVisible(true);
        }
        gameStarted = false;

        // Set up keyboard controls
        gameGrid.setFocusTraversable(true);
        gameGrid.requestFocus();
        gameGrid.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.SPACE) {
                togglePause();
                event.consume();
                return;
            }
            // Arrow keys only active when game is playing (not paused, not win, not game over)
            if (isGameActive()) {
                switch (code) {
                    case UP -> game.changeDirection(Direction.UP);
                    case DOWN -> game.changeDirection(Direction.DOWN);
                    case LEFT -> game.changeDirection(Direction.LEFT);
                    case RIGHT -> game.changeDirection(Direction.RIGHT);
                    default -> {
                    }
                }
                // Consume the event to prevent focus traversal
                if (code == KeyCode.UP || code == KeyCode.DOWN || code == KeyCode.LEFT
                    || code == KeyCode.RIGHT) {
                    event.consume();
                }
            }
        });
    }

    /**
     * Sets up a listener to resize the grid cells when the window size changes.
     * Uses a debounce timer to avoid excessive updates.
     * Listens to the scene's width/height changes, not the grid's own size,
     * to avoid feedback loops.
     */
    private void setupResizeListener() {
        if (resizeTimer == null) {
            resizeTimer = new PauseTransition(Duration.millis(500));
            resizeTimer.setOnFinished(event -> adjustCellSize());
        }
        if (resizeListenersAdded) {
            return;
        }
        // Wait for the grid to be attached to a scene
        gameGrid.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                attachSceneListeners(newScene);
            }
        });
        // If scene is already present (e.g., after level restart), attach immediately
        if (gameGrid.getScene() != null) {
            attachSceneListeners(gameGrid.getScene());
        }
        resizeListenersAdded = true;
    }

    private void attachSceneListeners(Scene scene) {
        if (resizeScene != null && resizeScene == scene) {
            // Already attached to this scene
            return;
        }
        // Remove any existing listeners from previous scene
        detachResizeListeners();
        resizeScene = scene;
        widthListener = (obs, old, newVal) -> scheduleResizeIfNeeded(newVal.doubleValue(),
            resizeScene.getHeight());
        heightListener = (obs, old, newVal) -> scheduleResizeIfNeeded(resizeScene.getWidth(),
            newVal.doubleValue());
        scene.widthProperty().addListener(widthListener);
        scene.heightProperty().addListener(heightListener);
        // Initialize previous dimensions
        previousSceneWidth = scene.getWidth();
        previousSceneHeight = scene.getHeight();
    }

    private void scheduleResizeIfNeeded(double newWidth, double newHeight) {
        if (adjustingCellSize) {
            return;
        }
        // Check if change is significant (at least 5 pixels)
        if (Math.abs(newWidth - previousSceneWidth) < 5.0
            && Math.abs(newHeight - previousSceneHeight) < 5.0) {
            // No significant change in either dimension
            return;
        }
        previousSceneWidth = newWidth;
        previousSceneHeight = newHeight;
        scheduleResize();
    }

    private void scheduleResize() {
        if (adjustingCellSize) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastResizeTime < 500) {
            return;
        }
        if (resizeTimer != null) {
            resizeTimer.stop();
            resizeTimer.playFromStart();
        }
    }

    private void detachResizeListeners() {
        if (resizeScene != null && widthListener != null) {
            resizeScene.widthProperty().removeListener(widthListener);
        }
        if (resizeScene != null && heightListener != null) {
            resizeScene.heightProperty().removeListener(heightListener);
        }
    }

    private void attachResizeListeners() {
        if (resizeScene != null && widthListener != null) {
            resizeScene.widthProperty().addListener(widthListener);
        }
        if (resizeScene != null && heightListener != null) {
            resizeScene.heightProperty().addListener(heightListener);
        }
    }

    private void adjustCellSize() {
        if (currentLevel == null || gameView == null) {
            return;
        }
        adjustingCellSize = true;
        detachResizeListeners();
        try {
            double availableWidth = gameGrid.getWidth();
            double availableHeight = gameGrid.getHeight();
            if (availableWidth <= 0 || availableHeight <= 0) {
                // Grid not yet laid out, retry after a short delay
                if (adjustRetryCount < MAX_ADJUST_RETRY) {
                    adjustRetryCount++;
                    javafx.application.Platform.runLater(this::adjustCellSize);
                }
                return;
            }
            // Reset retry count on successful measurement
            adjustRetryCount = 0;
            double hgap = gameGrid.getHgap();
            double vgap = gameGrid.getVgap();
            double totalGapWidth = (currentLevel.width() - 1) * hgap;
            double totalGapHeight = (currentLevel.height() - 1) * vgap;
            double cellWidth = (availableWidth - totalGapWidth) / currentLevel.width();
            double cellHeight = (availableHeight - totalGapHeight) / currentLevel.height();
            double newCellSize = Math.min(cellWidth, cellHeight);
            // Ensure a minimum cell size (e.g., 5 pixels)
            if (newCellSize < 5) {
                newCellSize = 5;
            }
            // Skip if the cell size hasn't changed significantly (prevents feedback loops)
            if (Math.abs(newCellSize - previousCellSize) < 0.1) {
                previousCellSize = newCellSize; // keep previous up to date
                return;
            }
            previousCellSize = newCellSize;
            gameView.resizeCells(newCellSize);
            lastResizeTime = System.currentTimeMillis();
        } finally {
            adjustingCellSize = false;
            attachResizeListeners();
        }
    }

    private void hideAllOverlays() {
        if (startOverlay != null) {
            startOverlay.setVisible(false);
        }
        if (pauseOverlay != null) {
            pauseOverlay.setVisible(false);
        }
        if (winOverlay != null) {
            winOverlay.setVisible(false);
        }
        if (gameOverOverlay != null) {
            gameOverOverlay.setVisible(false);
        }
    }

    private void updateOverlays(boolean paused, boolean win, boolean gameOver) {
        hideAllOverlays();
        if (win) {
            if (winOverlay != null) {
                winOverlay.setVisible(true);
            }
        } else if (gameOver) {
            if (gameOverOverlay != null) {
                gameOverOverlay.setVisible(true);
            }
        } else if (paused) {
            if (pauseOverlay != null) {
                pauseOverlay.setVisible(true);
            }
        }
        // If not paused, not win, not gameOver, then playing -> no overlay
        updateButtonStates(paused, win, gameOver);
    }

    private void updateButtonStates(boolean paused, boolean win, boolean gameOver) {
        if (startButton == null || pauseButton == null || restartButton == null) {
            return;
        }
        boolean playing = !paused && !win && !gameOver;
        // Start button: enabled when paused (to resume) or when game over/win?
        // Actually you can't start from win/game over, you need restart.
        startButton.setDisable(!paused || win || gameOver);
        // Pause button: enabled only when playing
        pauseButton.setDisable(!playing);
        // Restart button: always enabled
        restartButton.setDisable(false);
    }

    private boolean isGameActive() {
        return game != null && !gameEngine.isPaused() && !game.isWin() && !game.isGameOver();
    }

    /**
     * Advances to the next level when the current level is won.
     */
    private void advanceLevel() {
        if (game.hasNextLevel()) {
            game.nextLevel();
            // Update UI for the new level
            Level level = game.getCurrentLevel();
            currentLevel = level;
            previousCellSize = 0; // force cell size recalculation
            adjustRetryCount = 0; // reset retry counter
            gameView.setupGrid(level.width(), level.height());
            levelLabel.setText(String.valueOf(game.getCurrentLevelIndex() + 1));
            // No need to restart engine because game is same
            currentLevelIndex = game.getCurrentLevelIndex();
            // Hide any overlays that might be showing
            hideAllOverlays();
            // Adjust cell size for new level
            javafx.application.Platform.runLater(() -> {
                if (gameGrid.getWidth() > 0 && gameGrid.getHeight() > 0) {
                    adjustCellSize();
                } else {
                    // grid not yet laid out, try again after the next pulse
                    javafx.application.Platform.runLater(this::adjustCellSize);
                }
            });
        } else {
            statusLabel.setText("You Win All Levels!");
            gameEngine.pause();
        }
    }

    /**
     * Handles the Start button click (resumes the game).
     */
    @FXML
    private void handleStart() {
        gameEngine.resume();
        statusLabel.setText("Playing");
        hideAllOverlays();
        gameStarted = true;
    }

    /**
     * Handles the Pause button click (pauses the game).
     */
    @FXML
    private void handlePause() {
        gameEngine.pause();
        statusLabel.setText("Paused");
        // Update overlays immediately
        boolean win = game != null && game.isWin();
        boolean gameOver = game != null && game.isGameOver();
        updateOverlays(true, win, gameOver);
    }

    /**
     * Handles the Restart button click (restarts from the first level).
     */
    @FXML
    private void handleRestart() {
        // Restart from first level
        startLevel(0);
    }


    /**
     * Toggles pause state via spacebar.
     */
    private void togglePause() {
        gameEngine.togglePause();
        statusLabel.setText(gameEngine.isPaused() ? "Paused" : "Playing");
        // Update overlays immediately
        boolean win = game != null && game.isWin();
        boolean gameOver = game != null && game.isGameOver();
        updateOverlays(gameEngine.isPaused(), win, gameOver);
    }
}