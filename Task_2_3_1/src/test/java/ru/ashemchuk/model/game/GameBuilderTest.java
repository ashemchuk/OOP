package ru.ashemchuk.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.level.Level;
import ru.ashemchuk.model.level.LevelBuilder;

/**
 * Unit tests for {@link GameBuilder}.
 */
class GameBuilderTest {

    @Test
    void build_withSingleLevel_createsGame() {
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(3)
            .build();

        Game game = new GameBuilder()
            .level(level)
            .build();

        assertNotNull(game);
        assertEquals(level, game.getCurrentLevel());
        assertEquals(0, game.getCurrentLevelIndex());
        assertFalse(game.isGameOver());
        assertFalse(game.isWin());
    }

    @Test
    void build_withMultipleLevels_createsGameWithFirstLevel() {
        Level level1 = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(3)
            .build();
        Level level2 = new LevelBuilder()
            .width(30)
            .height(30)
            .snakeSpeed(6)
            .snakeLengthFinal(15)
            .foodAmount(5)
            .build();

        Game game = new GameBuilder()
            .level(level1)
            .level(level2)
            .build();

        assertNotNull(game);
        assertEquals(level1, game.getCurrentLevel());
        assertEquals(0, game.getCurrentLevelIndex());
    }

    @Test
    void build_withLevelsList_createsGame() {
        Level level1 = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(3)
            .build();
        Level level2 = new LevelBuilder()
            .width(30)
            .height(30)
            .snakeSpeed(6)
            .snakeLengthFinal(15)
            .foodAmount(5)
            .build();

        Game game = new GameBuilder()
            .levels(List.of(level1, level2))
            .build();

        assertNotNull(game);
        assertEquals(level1, game.getCurrentLevel());
        assertEquals(0, game.getCurrentLevelIndex());
    }

    @Test
    void build_withNoLevels_throwsIllegalStateException() {
        GameBuilder builder = new GameBuilder();
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void clearLevels_removesAllLevels() {
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(3)
            .build();

        GameBuilder builder = new GameBuilder()
            .level(level)
            .clearLevels();

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void level_chaining_works() {
        Level level1 = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(3)
            .build();
        Level level2 = new LevelBuilder()
            .width(30)
            .height(30)
            .snakeSpeed(6)
            .snakeLengthFinal(15)
            .foodAmount(5)
            .build();

        GameBuilder builder = new GameBuilder()
            .level(level1)
            .level(level2);

        Game game = builder.build();
        assertEquals(0, game.getCurrentLevelIndex());
        assertTrue(game.hasNextLevel());
    }
}