package ru.ashemchuk.model.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.level.Level;
import ru.ashemchuk.model.level.LevelBuilder;

/**
 * Unit tests for {@link RandomRobot}.
 */
class RandomRobotTest {
    private Level level;
    private RandomRobot robot;

    @BeforeEach
    void setUp() {
        level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .build();
        robot = new RandomRobot(new Point(10, 10), 5, level);
    }

    @Test
    void constructor_initializesWithRandomDirection() {
        // Direction should be one of the four
        Direction dir = robot.getDirection();
        assertTrue(dir == Direction.UP || dir == Direction.DOWN
            || dir == Direction.LEFT || dir == Direction.RIGHT);
    }

    @Test
    void move_updatesPosition() {
        Point initialHead = robot.getBody().get(0);
        robot.move();
        Point newHead = robot.getBody().get(0);
        // Should move one step in its initial direction
        assertNotEquals(initialHead, newHead);
        // Check that movement is consistent with direction
        Direction dir = robot.getDirection();
        switch (dir) {
            case UP -> assertEquals(initialHead.y() - 1, newHead.y());
            case DOWN -> assertEquals(initialHead.y() + 1, newHead.y());
            case LEFT -> assertEquals(initialHead.x() - 1, newHead.x());
            case RIGHT -> assertEquals(initialHead.x() + 1, newHead.x());
        }
    }

    @Test
    void decideDirection_changesWithProbability() throws Exception {
        // Use reflection to replace Random with a mock that returns predictable values
        Field randomField = RandomRobot.class.getDeclaredField("random");
        randomField.setAccessible(true);
        // Create a mock Random that returns 0.0 (always change) then 1.0 (never change)
        Random mockRandom = new Random() {
            private int callCount = 0;

            @Override
            public double nextDouble() {
                return (callCount++ == 0) ? 0.0 : 1.0;
            }

            @Override
            public int nextInt(int bound) {
                return 0; // always pick first direction (UP)
            }
        };
        randomField.set(robot, mockRandom);

        // First move: probability 0.0 < 0.3,
        // direction should change to UP (since nextInt returns 0)
        robot.move();
        assertEquals(Direction.UP, robot.getDirection());

        // Second move: probability 1.0 > 0.3,
        // direction should stay UP
        robot.move();
        assertEquals(Direction.UP, robot.getDirection());
    }

    @Test
    void move_wallAvoidance_kicksInWhenOutOfBounds() throws Exception {
        // Place robot at (0,0) facing UP, moving out of bounds
        RandomRobot edgeRobot = new RandomRobot(new Point(0, 0), 5, level);
        // Force direction UP
        edgeRobot.turn(Direction.UP);
        // Inject a mock random that does NOT change direction (nextDouble > 0.3)
        Field randomField = RandomRobot.class.getDeclaredField("random");
        randomField.setAccessible(true);
        Random mockRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.5; // >0.3, so direction stays
            }

            @Override
            public int nextInt(int bound) {
                return 0; // not used because direction doesn't change
            }
        };
        randomField.set(edgeRobot, mockRandom);
        // Move should trigger wall avoidance and turn left
        edgeRobot.move();
        // After avoidance, direction should be LEFT (since UP -> LEFT)
        assertEquals(Direction.LEFT, edgeRobot.getDirection());
        // Head should be at (-1,0) because moving LEFT from (0,0)
        // is out of bounds, but the robot still moves there.
        Point head = edgeRobot.getBody().get(0);
        assertEquals(new Point(-1, 0), head);
    }

    @Test
    void eat_setsGrowNextMove() {
        // Use a mock food
        ru.ashemchuk.model.food.Food mockFood =
            new ru.ashemchuk.model.food.AppleFood(new Point(0, 0));
        robot.eat(mockFood);
        assertTrue(robot.isGrowing());
    }

    @Test
    void die_setsAliveFalse() {
        robot.die();
        assertFalse(robot.isAlive());
        // After death, move should not change position
        Point before = robot.getBody().get(0);
        robot.move();
        Point after = robot.getBody().get(0);
        assertEquals(before, after);
    }
}