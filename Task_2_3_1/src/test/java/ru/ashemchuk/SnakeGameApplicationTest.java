package ru.ashemchuk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for {@link SnakeGameApplication}.
 */
class SnakeGameApplicationTest {
    @Test
    void applicationCanBeInstantiated() {
        // Just ensure the class can be instantiated (no JavaFX toolkit required)
        SnakeGameApplication app = new SnakeGameApplication();
        assertNotNull(app);
    }
}