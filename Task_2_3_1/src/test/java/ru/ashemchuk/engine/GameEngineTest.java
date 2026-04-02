package ru.ashemchuk.engine;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashemchuk.model.game.Game;
import ru.ashemchuk.model.game.GameState;

/**
 * Unit tests for {@link GameEngine}.
 */
@ExtendWith(MockitoExtension.class)
class GameEngineTest {
    @Mock
    private Game game;
    @Mock
    private GameUpdateListener listener;
    @Mock
    private GameState gameState;

    private GameEngine engine;

    @BeforeEach
    void setUp() {
        engine = new GameEngine(game, listener);
    }

    @Test
    void constructor_initializesFields() {
        assertNotNull(engine);
        assertFalse(engine.isPaused());
    }

    @Test
    void pause_setsPausedTrue() {
        engine.pause();
        assertTrue(engine.isPaused());
    }

    @Test
    void resume_setsPausedFalse() {
        engine.pause();
        engine.resume();
        assertFalse(engine.isPaused());
    }

    @Test
    void togglePause_togglesState() {
        assertFalse(engine.isPaused());
        engine.togglePause();
        assertTrue(engine.isPaused());
        engine.togglePause();
        assertFalse(engine.isPaused());
    }
}