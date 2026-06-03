package ru.ashemchuk.view;

import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.game.GameState;

/**
 * Visual representation of the game (MVC view).
 * Manages the game grid and updates UI elements (labels, cell colors)
 * based on the current game state.
 */
public class GameView {
    private double cellSize = 25.0;
    private final GridPane gameGrid;
    private final Label scoreLabel;
    private final Label lengthLabel;
    private final Label levelLabel;
    private final Label statusLabel;
    private int gridWidth;
    private int gridHeight;

    /**
     * Constructs a game view bound to the given UI components.
     *
     * @param gameGrid    the GridPane that displays the game field
     * @param scoreLabel  label for displaying the score
     * @param lengthLabel label for displaying the player snake length
     * @param levelLabel  label for displaying the current level number
     * @param statusLabel label for displaying game status (playing, paused, win, game over)
     */
    public GameView(GridPane gameGrid, Label scoreLabel, Label lengthLabel,
                    Label levelLabel, Label statusLabel) {
        this.gameGrid = gameGrid;
        this.scoreLabel = scoreLabel;
        this.lengthLabel = lengthLabel;
        this.levelLabel = levelLabel;
        this.statusLabel = statusLabel;
    }

    /**
     * Clears and reinitializes the grid with a given width and height.
     * Creates a rectangular cell for each grid position and sets column/row constraints.
     *
     * @param width  number of columns
     * @param height number of rows
     */
    public void setupGrid(int width, int height) {
        this.gridWidth = width;
        this.gridHeight = height;
        gameGrid.getChildren().clear();
        gameGrid.getColumnConstraints().clear();
        gameGrid.getRowConstraints().clear();
        for (int x = 0; x < width; x++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(0);
            col.setPrefWidth(cellSize);
            col.setMaxWidth(Double.MAX_VALUE);
            gameGrid.getColumnConstraints().add(col);
        }
        for (int y = 0; y < height; y++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(0);
            row.setPrefHeight(cellSize);
            row.setMaxHeight(Double.MAX_VALUE);
            gameGrid.getRowConstraints().add(row);
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(Color.LIGHTGRAY);
                cell.setStroke(Color.DARKGRAY);
                gameGrid.add(cell, x, y);
            }
        }
    }

    /**
     * Resizes all grid cells to a new size (in pixels).
     * Updates the stored cell size, adjusts column/row constraints
     * and each rectangle's width and height.
     *
     * @param newCellSize the new cell size (must be positive)
     */
    public void resizeCells(double newCellSize) {
        if (newCellSize <= 0) {
            return;
        }
        this.cellSize = newCellSize;
        // Update column constraints
        for (ColumnConstraints col : gameGrid.getColumnConstraints()) {
            col.setMinWidth(0);
            col.setPrefWidth(newCellSize);
            col.setMaxWidth(Double.MAX_VALUE);
        }
        // Update row constraints
        for (RowConstraints row : gameGrid.getRowConstraints()) {
            row.setMinHeight(0);
            row.setPrefHeight(newCellSize);
            row.setMaxHeight(Double.MAX_VALUE);
        }
        // Update rectangle sizes
        for (var node : gameGrid.getChildren()) {
            if (node instanceof Rectangle rect) {
                rect.setWidth(newCellSize);
                rect.setHeight(newCellSize);
            }
        }
    }

    /**
     * Returns the current cell size.
     */
    public double getCellSize() {
        return cellSize;
    }

    /**
     * Updates the view according to the current game state and pause status.
     * Updates labels and recolors each cell based on what occupies it
     * (player snake, robot snake, food, empty).
     *
     * @param state  current game state snapshot
     * @param paused whether the game is paused
     */
    public void update(GameState state, boolean paused) {
        // Update labels
        scoreLabel.setText(String.valueOf(state.score()));
        lengthLabel.setText(
            state.playerSnakePositions().size() + " / " + state.currentLevel().snakeLengthFinal());
        // level label is set by controller, do not update here

        // Update status
        if (state.win()) {
            statusLabel.setText("You Win!");
        } else if (state.gameOver()) {
            statusLabel.setText("Game Over");
        } else if (paused) {
            statusLabel.setText("Paused");
        } else {
            statusLabel.setText("Playing");
        }

        // Update grid colors
        int width = state.currentLevel().width();
        int height = state.currentLevel().height();
        // Precompute head positions for differentiation
        Point playerHead =
            state.playerSnakePositions().isEmpty() ? null : state.playerSnakePositions().get(0);
        List<Point> robotHeads = state.robotSnakePositions().stream()
            .filter(robot -> !robot.isEmpty())
            .map(robot -> robot.get(0))
            .toList();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Rectangle cell = getNodeByRowColumnIndex(y, x);
                if (cell == null) {
                    continue;
                }
                Color fill = Color.LIGHTGRAY;
                final int fx = x;
                final int fy = y;
                // Check if this cell is player snake
                boolean isPlayer = false;
                boolean isPlayerHead = false;
                if (playerHead != null && playerHead.x() == fx && playerHead.y() == fy) {
                    isPlayerHead = true;
                    isPlayer = true;
                } else if (state.playerSnakePositions().stream()
                    .anyMatch(p -> p.x() == fx && p.y() == fy)) {
                    isPlayer = true;
                }
                boolean isFood = false;
                if (isPlayer) {
                    fill = isPlayerHead ? Color.DARKGREEN : Color.GREEN;
                } else {
                    // Check if this cell is robot snake
                    boolean robotFound = false;
                    List<List<Point>> robotPositions = state.robotSnakePositions();
                    for (int i = 0; i < robotPositions.size(); i++) {
                        List<Point> robot = robotPositions.get(i);
                        if (robot.isEmpty()) {
                            continue;
                        }
                        Point robotHead = robot.get(0);
                        boolean isRobotHead = robotHead.x() == fx && robotHead.y() == fy;
                        boolean isRobotBody = !isRobotHead
                            && robot.stream().anyMatch(p -> p.x() == fx && p.y() == fy);
                        if (isRobotHead || isRobotBody) {
                            // Assign different colors per robot
                            if (i == 0) {
                                fill = isRobotHead ? Color.DARKBLUE : Color.BLUE;
                            } else {
                                fill = isRobotHead ? Color.DARKORANGE : Color.ORANGE;
                            }
                            robotFound = true;
                            break;
                        }
                    }
                    if (!robotFound) {
                        // Check if this cell is food and determine its type
                        for (var food : state.foods()) {
                            Point pos = food.getPosition();
                            if (pos.x() == fx && pos.y() == fy) {
                                switch (food.getType()) {
                                    case APPLE -> fill = Color.RED;
                                    case GRAPES -> fill = Color.PURPLE;
                                    case POISON -> fill = Color.DARKGREEN;
                                }
                                isFood = true;
                                break;
                            }
                        }
                    }
                }
                cell.setFill(fill);
                if (isFood) {
                    cell.setArcWidth(cellSize);
                    cell.setArcHeight(cellSize);
                } else {
                    cell.setArcWidth(0);
                    cell.setArcHeight(0);
                }
            }
        }
    }

    /**
     * Retrieves the Rectangle node at the specified row and column in the grid.
     *
     * @param row    row index (0‑based)
     * @param column column index (0‑based)
     * @return the Rectangle at that position, or null if not found
     */
    private Rectangle getNodeByRowColumnIndex(int row, int column) {
        for (var node : gameGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return (Rectangle) node;
            }
        }
        return null;
    }
}