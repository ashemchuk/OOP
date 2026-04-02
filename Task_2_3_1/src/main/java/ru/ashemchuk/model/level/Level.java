package ru.ashemchuk.model.level;

import java.util.List;
import ru.ashemchuk.model.food.FoodSpec;

/**
 * Represents a game level configuration.
 *
 * @param width            width of the game field in cells
 * @param height           height of the game field in cells
 * @param foodAmount       maximum number of food items on the field
 * @param snakeLengthInit  initial length of the player snake
 * @param snakeSpeed       initial speed of the player snake (movement delay in ms)
 * @param snakeLengthFinal target length to win the level
 * @param robotSpecs       specifications for robot snakes in this level
 * @param foodSpecs        specifications for food types and their weights
 */
public record Level(int width, int height, int foodAmount, int snakeLengthInit, int snakeSpeed,
                    int snakeLengthFinal,
                    List<RobotSpec> robotSpecs, List<FoodSpec> foodSpecs) {
    /**
     * Level's static init.
     */
    public Level {
        // Defensive copy if needed
        robotSpecs = List.copyOf(robotSpecs);
        foodSpecs = List.copyOf(foodSpecs);
    }
}