package ru.ashemchuk.model.snake;

import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.level.Level;

/**
 * Robot snake that follows the player snake using a simple Manhattan‑distance heuristic.
 * Moves horizontally or vertically to reduce distance to the player's head.
 * Moves at half the speed of the player (skips every other tick).
 */
public class FollowPlayerRobot extends RobotSnake {
    private final PlayerSnake player;
    private int moveCounter = 0;

    /**
     * Constructs a follow‑player robot snake.
     *
     * @param start  starting position of the robot's head
     * @param speed  movement speed (ticks per move)
     * @param player the player snake to follow
     * @param level  the level containing width and height for bounds checking
     */
    public FollowPlayerRobot(Point start, int speed, PlayerSnake player, Level level) {
        super(start, speed, level);
        this.player = player;
    }

    /**
     * Moves the robot every second tick, updating direction
     * on skipped ticks to keep reacting to the player.
     */
    @Override
    public void move() {
        moveCounter++;
        if (moveCounter % 2 == 0) {
            // Move only every second tick
            super.move();
        } else {
            // Skip movement but still update direction to keep reacting to player
            decideDirection();
        }
    }

    /**
     * Chooses a direction that reduces Manhattan distance to the player's head,
     * preferring horizontal movement when horizontal distance is greater.
     */
    @Override
    protected void decideDirection() {
        // Simple AI: move towards player's head
        Point playerHead = player.getBody().get(0);
        Point myHead = body.get(0);

        // Determine direction that reduces Manhattan distance
        int dx = playerHead.x() - myHead.x();
        int dy = playerHead.y() - myHead.y();

        // Prefer horizontal movement if horizontal distance is greater
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }
        } else {
            if (dy > 0) {
                direction = Direction.DOWN;
            } else {
                direction = Direction.UP;
            }
        }

        // Ensure not moving opposite direction (optional)
        // RobotSnake has no turn restrictions
    }
}