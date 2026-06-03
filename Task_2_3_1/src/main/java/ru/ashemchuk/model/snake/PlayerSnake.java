package ru.ashemchuk.model.snake;

import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.food.Food;

/**
 * Represents the player-controlled snake.
 * Handles movement, growth, shrinking, and turning with restrictions (no 180-degree turns).
 */
public class PlayerSnake extends Snake {
    private boolean alive = true;
    private boolean growNextMove = false;
    private boolean shrinkNextMove = false;
    private boolean turnedThisTick = false;

    /**
     * Creates a player snake at the given starting point with a default direction of RIGHT.
     *
     * @param start initial head position
     * @param speed movement speed in milliseconds per move
     */
    public PlayerSnake(Point start, int speed) {
        super(new ArrayList<>(List.of(start)), speed);
        this.direction = Direction.RIGHT; // default direction
    }

    /**
     * Moves the player snake one step forward according to its current direction.
     * Handles growth and shrinking if scheduled.
     */
    @Override
    public void move() {
        if (!alive) {
            return;
        }
        turnedThisTick = false;

        Point head = body.get(0);
        Point newHead = calculateNewHead(head, direction);

        // Add new head at the beginning
        body.add(0, newHead);

        // If not growing, remove tail
        if (!growNextMove) {
            body.remove(body.size() - 1);
        } else {
            growNextMove = false;
        }

        // If shrinking, remove an extra segment (if length > 1)
        if (shrinkNextMove && body.size() > 1) {
            body.remove(body.size() - 1);
            shrinkNextMove = false;
        }
    }

    private Point calculateNewHead(Point head, Direction dir) {
        return switch (dir) {
            case UP -> new Point(head.x(), head.y() - 1);
            case DOWN -> new Point(head.x(), head.y() + 1);
            case LEFT -> new Point(head.x() - 1, head.y());
            case RIGHT -> new Point(head.x() + 1, head.y());
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
        // After eating, the snake should grow on the next move
        growNextMove = true;
    }

    /**
     * Marks the snake to shrink by one segment on the next move.
     */
    public void shrink() {
        shrinkNextMove = true;
    }

    /**
     * Marks the snake as dead.
     */
    @Override
    public void die() {
        alive = false;
    }

    /**
     * Changes the snake's direction, respecting no 180-degree turns and one turn per tick.
     *
     * @param newDirection the desired new direction
     */
    @Override
    public void turn(Direction newDirection) {
        // Prevent 180-degree turn (opposite direction)
        if (isOpposite(newDirection)) {
            return;
        }
        // Allow only one turn per tick
        if (turnedThisTick) {
            return;
        }
        this.direction = newDirection;
        turnedThisTick = true;
    }

    private boolean isOpposite(Direction newDir) {
        return (direction == Direction.UP && newDir == Direction.DOWN)
            || (direction == Direction.DOWN && newDir == Direction.UP)
            || (direction == Direction.LEFT && newDir == Direction.RIGHT)
            || (direction == Direction.RIGHT && newDir == Direction.LEFT);
    }

    /**
     * Returns whether the snake is still alive.
     *
     * @return true if alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the alive state of the snake.
     *
     * @param alive new alive state
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Returns whether the snake will grow on the next move.
     *
     * @return true if growing next move
     */
    public boolean isGrowing() {
        return growNextMove;
    }

    /**
     * Returns whether the snake will shrink on the next move.
     *
     * @return true if shrinking next move
     */
    public boolean isShrinking() {
        return shrinkNextMove;
    }
}