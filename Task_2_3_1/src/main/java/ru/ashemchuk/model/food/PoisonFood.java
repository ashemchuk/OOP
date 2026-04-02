package ru.ashemchuk.model.food;

import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.snake.PlayerSnake;
import ru.ashemchuk.model.snake.Snake;

/**
 * Poison food item. Shrinks the snake when eaten.
 */
public class PoisonFood extends Food {

    /**
     * Creates a poison food item at the specified position.
     *
     * @param position the position of the poison on the game field
     */
    public PoisonFood(Point position) {
        super(position, FoodSpec.FoodType.POISON);
    }

    /**
     * Poison shrinks the player snake by one segment.
     * Has no effect on robot snakes.
     *
     * @param snake the snake that ate the poison
     */
    @Override
    public void effect(Snake snake) {
        // Shrink snake by one segment
        if (snake instanceof PlayerSnake) {
            ((PlayerSnake) snake).shrink();
        }
        // For other snakes, we could also add shrink capability later
    }

    /**
     * Removes the poison from the field.
     */
    @Override
    public void eat() {
        // Remove from field
    }

    /**
     * Places the poison on the field.
     */
    @Override
    public void post() {
        // Place on field
    }
}