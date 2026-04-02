package ru.ashemchuk.model.snake;

import java.util.Collections;
import java.util.List;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.food.Food;

/**
 * Abstract base class representing a snake in the game.
 * A snake consists of a body (list of points), a movement speed, and a direction.
 * Subclasses must implement movement, eating, dying, and turning behavior.
 */
public abstract class Snake {
    // 0th element - head, link's coordinates on field
    protected List<Point> body;
    protected int speed;
    protected Direction direction;
    // Temporary speed boost
    private int baseSpeed;
    private long boostEndTime; // 0 means no active boost
    private static final int BOOST_AMOUNT = 50;

    /**
     * Constructs a snake with the given body and speed.
     *
     * @param body  the initial body segments, where the first element is the head
     * @param speed the movement speed in milliseconds per move
     */
    public Snake(List<Point> body, int speed) {
        this.body = body;
        this.baseSpeed = speed;
        this.speed = speed;
        this.boostEndTime = 0;
    }

    /**
     * Moves the snake one step forward according to its current direction.
     * Updates the body accordingly (adds new head, removes tail unless growing).
     */
    public abstract void move();

    /**
     * Applies the effect of eaten food to this snake.
     *
     * @param food the food that was eaten
     */
    public abstract void eat(Food food);

    /**
     * Kills the snake (sets its state to dead).
     */
    public abstract void die();

    /**
     * Changes the snake's direction, respecting movement rules (e.g., no 180-degree turns).
     *
     * @param direction the new direction to attempt to turn to
     */
    public abstract void turn(Direction direction);

    /**
     * Returns an unmodifiable view of the snake's body.
     *
     * @return the list of body points
     */
    public List<Point> getBody() {
        return Collections.unmodifiableList(body);
    }

    /**
     * Returns the current movement speed (delay between moves in milliseconds).
     *
     * @return speed in milliseconds
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the current direction of movement.
     *
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Replaces the snake's body with a new list of points.
     *
     * @param body new body segments
     */
    public void setBody(List<Point> body) {
        this.body = body;
    }

    /**
     * Sets a new movement speed.
     * Updates the base speed and adjusts effective speed if a boost is active.
     *
     * @param speed new speed in milliseconds
     */
    public void setSpeed(int speed) {
        this.baseSpeed = speed;
        updateEffectiveSpeed();
    }

    /**
     * Sets the direction of the snake.
     *
     * @param direction new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // ==================== Temporary speed boost ====================

    /**
     * Returns whether a speed boost is currently active.
     */
    private boolean isBoostActive() {
        return boostEndTime > 0 && System.currentTimeMillis() < boostEndTime;
    }

    /**
     * Updates the effective speed based on base speed and active boost.
     * Ensures speed never goes below 50 ms.
     */
    private void updateEffectiveSpeed() {
        int effective = baseSpeed;
        if (isBoostActive()) {
            effective -= BOOST_AMOUNT;
            if (effective < 50) {
                effective = 50;
            }
        }
        this.speed = effective;
    }

    /**
     * Applies a temporary speed boost for the given duration.
     * If a boost is already active, its timer is reset.
     *
     * @param durationMs duration of the boost in milliseconds
     */
    public void applySpeedBoost(long durationMs) {
        boostEndTime = System.currentTimeMillis() + durationMs;
        updateEffectiveSpeed();
    }

    /**
     * Checks if the current boost has expired and reverts speed if needed.
     * Should be called periodically (e.g., each game update).
     */
    public void updateBoost() {
        if (boostEndTime > 0 && System.currentTimeMillis() >= boostEndTime) {
            boostEndTime = 0;
            updateEffectiveSpeed();
        }
    }
}