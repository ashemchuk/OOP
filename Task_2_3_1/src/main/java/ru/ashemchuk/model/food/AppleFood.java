package ru.ashemchuk.model.food;

import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.snake.Snake;

/**
 * Apple food item. Increases snake length when eaten.
 */
public class AppleFood extends Food {

    /**
     * Creates an apple food item at the specified position.
     *
     * @param position the position of the apple on the game field
     */
    public AppleFood(Point position) {
        super(position, FoodSpec.FoodType.APPLE);
    }

    /**
     * Apple increases snake length by one segment.
     * The actual growth is handled by the snake's grow flag.
     *
     * @param snake the snake that ate the apple
     */
    @Override
    public void effect(Snake snake) {
        // Apple increases length by 1 (already handled by snake's grow flag)
        // No additional effect
    }

    /**
     * Removes the apple from the field.
     */
    @Override
    public void eat() {
        // Remove from field
    }

    /**
     * Places the apple on the field.
     */
    @Override
    public void post() {
        // Place on field
    }
}