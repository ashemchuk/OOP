package ru.ashemchuk.model.food;

import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.snake.Snake;

/**
 * Abstract base class for all food items in the game.
 * Food can be placed on the field, eaten by a snake, and have various effects.
 */
public abstract class Food {
    protected Point position;
    protected final FoodSpec.FoodType type;

    /**
     * Constructs a food item at the specified position with the given type.
     *
     * @param position the position of the food on the game field
     * @param type     the type of food (apple, grapes, poison)
     */
    public Food(Point position, FoodSpec.FoodType type) {
        this.position = position;
        this.type = type;
    }

    /**
     * Returns the current position of this food item.
     *
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets a new position for this food item.
     *
     * @param position the new position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Returns the type of this food item.
     *
     * @return the food type
     */
    public FoodSpec.FoodType getType() {
        return type;
    }

    /**
     * Called when food is eaten by a snake.
     * Performs the effect on the snake.
     */
    public abstract void effect(Snake snake);

    /**
     * Called when food is removed from the field (eaten).
     */
    public void eat() {
        // default does nothing
    }

    /**
     * Called when food is placed on the field.
     */
    public void post() {
        // default does nothing
    }
}