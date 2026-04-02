package ru.ashemchuk.model.food;

import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.snake.Snake;

/**
 * Grapes food item. Increases snake speed when eaten.
 */
public class GrapesFood extends Food {

    /**
     * Creates a grapes food item at the specified position.
     *
     * @param position the position of the grapes on the game field
     */
    public GrapesFood(Point position) {
        super(position, FoodSpec.FoodType.GRAPES);
    }

    /**
     * Grapes apply a temporary speed boost to the snake.
     * The boost lasts for 5 seconds.
     *
     * @param snake the snake that ate the grapes
     */
    @Override
    public void effect(Snake snake) {
        // Apply temporary speed boost for 5 seconds
        snake.applySpeedBoost(5000);
    }

    /**
     * Removes the grapes from the field.
     */
    @Override
    public void eat() {
        // Remove from field
    }

    /**
     * Places the grapes on the field.
     */
    @Override
    public void post() {
        // Place on field
    }
}