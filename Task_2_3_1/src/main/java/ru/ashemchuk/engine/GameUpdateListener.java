package ru.ashemchuk.engine;

import ru.ashemchuk.model.game.GameState;

/**
 * Functional interface for receiving game state updates.
 * Used by {@link GameEngine} to notify listeners (e.g., the UI)
 * after each game tick.
 */
@FunctionalInterface
public interface GameUpdateListener {
    /**
     * Called when the game state has been updated.
     *
     * @param state the current game state snapshot
     */
    void onGameUpdate(GameState state);
}