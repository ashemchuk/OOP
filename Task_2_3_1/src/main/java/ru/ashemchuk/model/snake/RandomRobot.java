package ru.ashemchuk.model.snake;

import java.util.Random;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.level.Level;

/**
 * Robot snake that moves randomly.
 * Each move, there is a 30% chance it will change direction to a random one.
 */
public class RandomRobot extends RobotSnake {
    private final Random random = new Random();

    /**
     * Constructs a random robot snake.
     *
     * @param start starting position of the snake's head
     * @param speed movement speed (ticks per move)
     * @param level the level containing width and height for bounds checking
     */
    public RandomRobot(Point start, int speed, Level level) {
        super(start, speed, level);
    }

    /**
     * Randomly changes direction with 30% probability each move;
     * otherwise keeps the current direction.
     */
    @Override
    protected void decideDirection() {
        // Randomly change direction with 30% probability each move
        if (random.nextDouble() < 0.3) {
            Direction[] directions = Direction.values();
            direction = directions[random.nextInt(directions.length)];
        }
        // Otherwise keep current direction
    }
}