package ru.ashemchuk.model.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.level.Level;
import ru.ashemchuk.model.level.LevelBuilder;

/**
 * Unit tests for {@link FollowPlayerRobot}.
 */
class FollowPlayerRobotTest {
    private Level level;
    private PlayerSnake player;
    private FollowPlayerRobot robot;

    @BeforeEach
    void setUp() {
        level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .build();
        player = new PlayerSnake(new Point(5, 5), 5);
        robot = new FollowPlayerRobot(new Point(10, 10), 5, player, level);
    }

    @Test
    void constructor_initializesWithRandomDirection() {
        // Direction should be one of the four (randomly assigned by RobotSnake)
        Direction dir = robot.getDirection();
        assertTrue(dir == Direction.UP || dir == Direction.DOWN
            || dir == Direction.LEFT || dir == Direction.RIGHT);
    }

    @Test
    void decideDirection_movesTowardsPlayerHorizontally() {
        // Player at (5,5), robot at (10,5) -> dx = -5, dy = 0 -> horizontal.
        robot = new FollowPlayerRobot(new Point(10, 5), 5, player, level);
        // First move: direction updated, but no movement (half-speed)
        robot.move(); // calls decideDirection
        assertEquals(Direction.LEFT, robot.getDirection());
        Point head = robot.getBody().get(0);
        assertEquals(10, head.x()); // x unchanged
        assertEquals(5, head.y());
        // Second move: robot moves
        robot.move();
        assertEquals(Direction.LEFT, robot.getDirection());
        head = robot.getBody().get(0);
        assertEquals(9, head.x()); // x decreased by 1
        assertEquals(5, head.y());
    }

    @Test
    void decideDirection_movesTowardsPlayerVertically() {
        // Robot at (5,10), player at (5,5) -> dx = 0, dy = -5 -> vertical
        robot = new FollowPlayerRobot(new Point(5, 10), 5, player, level);
        // First move: direction updated, no movement
        robot.move();
        assertEquals(Direction.UP, robot.getDirection());
        Point head = robot.getBody().get(0);
        assertEquals(5, head.x());
        assertEquals(10, head.y()); // unchanged
        // Second move: robot moves
        robot.move();
        assertEquals(Direction.UP, robot.getDirection());
        head = robot.getBody().get(0);
        assertEquals(5, head.x());
        assertEquals(9, head.y()); // moved up
    }

    @Test
    void decideDirection_whenPlayerRightAndDown() {
        // Robot at (0,0), player at (3,7) -> dx = 3, dy = 7,
        // |dy| > |dx| -> vertical, dy > 0 -> DOWN
        robot = new FollowPlayerRobot(new Point(0, 0), 5, player, level);
        // Move player to (3,7)
        player.setBody(List.of(new Point(3, 7)));
        // First move: direction updated, no movement
        robot.move();
        assertEquals(Direction.DOWN, robot.getDirection());
        Point head = robot.getBody().get(0);
        assertEquals(0, head.x());
        assertEquals(0, head.y()); // unchanged
        // Second move: robot moves
        robot.move();
        assertEquals(Direction.DOWN, robot.getDirection());
        head = robot.getBody().get(0);
        assertEquals(0, head.x());
        assertEquals(1, head.y()); // moved down
    }

    @Test
    void move_wallAvoidance_adjustsDirection() {
        // Place robot at (0,0) with player at (0, -5) (above robot) so robot wants to go UP (out of bounds).
        player.setBody(List.of(new Point(0, -5)));
        robot = new FollowPlayerRobot(new Point(0, 0), 5, player, level);
        // First move: direction updated to UP, no movement (half-speed)
        robot.move();
        assertEquals(Direction.UP, robot.getDirection());
        Point head = robot.getBody().get(0);
        assertEquals(new Point(0, 0), head); // unchanged
        // Second move: robot attempts to move UP,
        // triggers wall avoidance, turns LEFT and moves LEFT (out of bounds)
        robot.move();
        assertEquals(Direction.LEFT, robot.getDirection());
        head = robot.getBody().get(0);
        assertEquals(new Point(-1, 0), head);
    }

    @Test
    void eat_growsNextMove() {
        ru.ashemchuk.model.food.Food mockFood =
            new ru.ashemchuk.model.food.AppleFood(new Point(0, 0));
        robot.eat(mockFood);
        assertTrue(robot.isGrowing());
        // After eating, the robot will grow on
        // its next movement (which may be the second tick due to half-speed)
        int lengthBefore = robot.getBody().size();
        // First move: may skip movement, length unchanged
        robot.move();
        int lengthAfterFirst = robot.getBody().size();
        assertEquals(lengthBefore, lengthAfterFirst);
        robot.move();
        int lengthAfterSecond = robot.getBody().size();
        assertEquals(lengthBefore + 1, lengthAfterSecond);
        // After that move, grow flag should be false
        assertFalse(robot.isGrowing());
    }

    @Test
    void die_stopsMovement() {
        robot.die();
        assertFalse(robot.isAlive());
        Point before = robot.getBody().get(0);
        robot.move();
        Point after = robot.getBody().get(0);
        assertEquals(before, after);
    }
}