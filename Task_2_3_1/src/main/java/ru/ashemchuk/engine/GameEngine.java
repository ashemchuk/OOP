package ru.ashemchuk.engine;

import javafx.animation.AnimationTimer;
import ru.ashemchuk.model.game.Game;

/**
 * Game engine that drives the game loop using JavaFX {@link AnimationTimer}.
 * Updates the game model at intervals defined by the player snake's speed,
 * notifies a listener after each update, and supports pausing/resuming.
 */
public class GameEngine {
    private final Game game;
    private final GameUpdateListener listener;
    private AnimationTimer gameLoop;
    private boolean paused = false;

    /**
     * Constructs a game engine for the given game and listener.
     *
     * @param game     the game model to update
     * @param listener callback to be notified after each game update
     */
    public GameEngine(Game game, GameUpdateListener listener) {
        this.game = game;
        this.listener = listener;
    }

    /**
     * Starts the game loop. If already started, does nothing.
     */
    public void start() {
        if (gameLoop != null) {
            return;
        }
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (paused || game.isGameOver() || game.isWin()) {
                    return;
                }
                // Convert nanoseconds to milliseconds
                long elapsedMs = (now - lastUpdate) / 1_000_000;
                if (lastUpdate == 0 || elapsedMs >= game.getPlayerSnake().getSpeed()) {
                    game.update();
                    listener.onGameUpdate(game.getState());
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Stops the game loop and releases resources.
     */
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
            gameLoop = null;
        }
    }

    /**
     * Pauses the game updates (the loop continues but skips updates).
     */
    public void pause() {
        paused = true;
    }

    /**
     * Resumes the game updates.
     */
    public void resume() {
        paused = false;
    }

    /**
     * Returns whether the game is currently paused.
     *
     * @return true if paused, false otherwise
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Toggles the pause state.
     */
    public void togglePause() {
        paused = !paused;
    }
}