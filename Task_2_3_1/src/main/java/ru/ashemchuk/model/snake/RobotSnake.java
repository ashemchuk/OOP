package ru.ashemchuk.model.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.food.Food;
import ru.ashemchuk.model.level.Level;

/**
 * Abstract base class for robot-controlled snakes.
 * Provides common logic for movement, wall avoidance, eating, and death.
 * Subclasses must implement {@link #decideDirection()} to define AI behavior.
 */
public abstract class RobotSnake extends Snake {
    protected boolean alive = true;
    protected boolean growNextMove = false;
    protected Random random = new Random();
    protected int levelWidth;
    protected int levelHeight;

    /**
     * Constructs a robot snake at the given starting point with specified speed and level.
     *
     * @param start starting position of the snake's head
     * @param speed movement speed (ticks per move)
     * @param level the level containing width and height for bounds checking
     */
    public RobotSnake(Point start, int speed, Level level) {
        super(new ArrayList<>(List.of(start)), speed);
        this.levelWidth = level.width();
        this.levelHeight = level.height();
        // Robots start with a random direction
        this.direction = Direction.values()[random.nextInt(Direction.values().length)];
    }

    /**
     * Moves the robot snake one step forward, using AI to decide direction and avoiding walls.
     */
    @Override
    public void move() {
        if (!alive) {
            return;
        }

        // Decide next direction based on AI
        decideDirection();

        Point head = body.get(0);
        Point newHead = calculateNewHead(head, direction);

        // Ensure new head is within bounds; if not, adjust direction
        if (isOutOfBounds(newHead)) {
            avoidWall();
            // Recalculate new head after direction change
            newHead = calculateNewHead(head, direction);
        }

        // Add new head at the beginning
        body.add(0, newHead);

        // If not growing, remove tail
        if (!growNextMove) {
            body.remove(body.size() - 1);
        } else {
            growNextMove = false;
        }
    }

    /**
     * Calculates the next head position based on current direction.
     *
     * @param head current head position
     * @param dir  direction to move
     * @return new head position after moving one step
     */
    protected Point calculateNewHead(Point head, Direction dir) {
        return switch (dir) {
            case UP -> new Point(head.x(), head.y() - 1);
            case DOWN -> new Point(head.x(), head.y() + 1);
            case LEFT -> new Point(head.x() - 1, head.y());
            case RIGHT -> new Point(head.x() + 1, head.y());
        };
    }

    private boolean isOutOfBounds(Point point) {
        return point.x() < 0 || point.x() >= levelWidth
            || point.y() < 0 || point.y() >= levelHeight;
    }

    private void avoidWall() {
        // Simple wall avoidance: turn 90 degrees left
        direction = switch (direction) {
            case UP -> Direction.LEFT;
            case DOWN -> Direction.RIGHT;
            case LEFT -> Direction.DOWN;
            case RIGHT -> Direction.UP;
        };
    }

    /**
     * Eats the given food, applying its effect and scheduling growth for the next move.
     *
     * @param food the food to eat
     */
    @Override
    public void eat(Food food) {
        food.effect(this);
        growNextMove = true;
    }

    /**
     * Marks the robot snake as dead.
     */
    @Override
    public void die() {
        alive = false;
    }

    /**
     * Changes the robot snake's direction without restrictions.
     *
     * @param newDirection the new direction
     */
    @Override
    public void turn(Direction newDirection) {
        // Robots can turn freely (no restriction)
        this.direction = newDirection;
    }

    /**
     * Returns whether the robot snake is alive.
     *
     * @return true if alive, false if dead
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the alive status of the robot snake.
     *
     * @param alive new alive status
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Returns whether the robot snake will grow on the next move.
     *
     * @return true if it will grow, false otherwise
     */
    public boolean isGrowing() {
        return growNextMove;
    }

    /**
     * AI decision method to be implemented by subclasses.
     * Determines the next direction the robot snake should move.
     */
    protected abstract void decideDirection();
}