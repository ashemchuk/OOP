package ru.ashemchuk.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.food.AppleFood;
import ru.ashemchuk.model.food.Food;
import ru.ashemchuk.model.level.Level;
import ru.ashemchuk.model.level.LevelBuilder;

/**
 * Unit tests for {@link GameState}.
 */
class GameStateTest {

    @Test
    void recordFields_areCorrectlySet() {
        List<Point> playerPos = List.of(new Point(5, 5), new Point(5, 6));
        List<List<Point>> robotPos = List.of(
            List.of(new Point(10, 10), new Point(10, 11)),
            List.of(new Point(15, 15))
        );
        List<Food> foods = List.of(
            new AppleFood(new Point(2, 2)),
            new AppleFood(new Point(3, 3))
        );
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(3)
            .build();

        GameState state = new GameState(playerPos, robotPos, foods, 42, false, true, level);

        assertEquals(playerPos, state.playerSnakePositions());
        assertEquals(robotPos, state.robotSnakePositions());
        assertEquals(foods, state.foods());
        assertEquals(42, state.score());
        assertFalse(state.gameOver());
        assertTrue(state.win());
        assertEquals(level, state.currentLevel());
    }

    @Test
    void snakePositions_returnsPlayerPositions() {
        List<Point> playerPos = List.of(new Point(1, 1));
        List<List<Point>> robotPos = List.of();
        List<Food> foods = List.of();
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(3)
            .build();

        GameState state = new GameState(playerPos, robotPos, foods, 0, false, false, level);

        assertEquals(playerPos, state.snakePositions());
    }

    @Test
    void foodPositions_returnsListOfPoints() {
        Point p1 = new Point(2, 2);
        Point p2 = new Point(3, 3);
        List<Food> foods = List.of(
            new AppleFood(p1),
            new AppleFood(p2)
        );
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(3)
            .build();

        GameState state = new GameState(List.of(), List.of(), foods, 0, false, false, level);

        List<Point> foodPositions = state.foodPositions();
        assertEquals(2, foodPositions.size());
        assertTrue(foodPositions.contains(p1));
        assertTrue(foodPositions.contains(p2));
    }

    @Test
    void fromGame_createsCorrectState() {
        // Create a simple level
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0) // no food initially
            .build();

        // Build a game with that level
        Game game = new Game(level);

        // Get state
        GameState state = GameState.fromGame(game);

        // Verify player snake positions (should be a single point at center)
        assertEquals(1, state.playerSnakePositions().size());
        Point start = new Point(level.width() / 2, level.height() / 2);
        assertEquals(start, state.playerSnakePositions().get(0));

        // No robots in this level
        assertTrue(state.robotSnakePositions().isEmpty());

        // No foods because foodAmount = 0
        assertTrue(state.foods().isEmpty());

        assertEquals(0, state.score());
        assertFalse(state.gameOver());
        assertFalse(state.win());
        assertEquals(level, state.currentLevel());
    }
}