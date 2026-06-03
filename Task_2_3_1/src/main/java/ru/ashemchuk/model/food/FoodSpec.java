package ru.ashemchuk.model.food;

/**
 * Specification of a food type with its weight (spawn probability).
 *
 * @param type   the type of food
 * @param weight the weight used for random selection
 */
public record FoodSpec(FoodType type, int weight) {
    /**
     * Enumeration of possible food types.
     */
    public enum FoodType {
        /**
         * Apple food, increases length.
         */
        APPLE,
        /**
         * Grapes food, increases speed.
         */
        GRAPES,
        /**
         * Poison food, shrinks snake.
         */
        POISON
    }
}