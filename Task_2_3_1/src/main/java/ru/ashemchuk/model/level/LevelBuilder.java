package ru.ashemchuk.model.level;

import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.model.food.FoodSpec;

/**
 * Builder for constructing {@link Level} instances with fluent API.
 */
public class LevelBuilder {
    private int width = 12;
    private int height = 12;
    private int foodAmount = 1;
    private int snakeLengthInit = 1;
    private int snakeSpeed = 250;
    private int snakeLengthFinal = 8;
    private final List<RobotSpec> robotSpecs = new ArrayList<>();
    private final List<FoodSpec> foodSpecs = new ArrayList<>();

    /**
     * Sets the width of the level (number of cells).
     *
     * @param width the width (must be positive)
     * @return this builder for chaining
     */
    public LevelBuilder width(int width) {
        this.width = width;
        return this;
    }

    /**
     * Sets the height of the level (number of cells).
     *
     * @param height the height (must be positive)
     * @return this builder for chaining
     */
    public LevelBuilder height(int height) {
        this.height = height;
        return this;
    }

    /**
     * Sets the maximum number of food items present simultaneously.
     *
     * @param foodAmount the food amount (must be non‑negative)
     * @return this builder for chaining
     */
    public LevelBuilder foodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
        return this;
    }

    /**
     * Sets the initial length of the player snake.
     *
     * @param snakeLengthInit the initial length (must be positive)
     * @return this builder for chaining
     */
    public LevelBuilder snakeLengthInit(int snakeLengthInit) {
        this.snakeLengthInit = snakeLengthInit;
        return this;
    }

    /**
     * Sets the movement speed of the player snake (delay in milliseconds).
     *
     * @param snakeSpeed the speed in milliseconds (must be positive)
     * @return this builder for chaining
     */
    public LevelBuilder snakeSpeed(int snakeSpeed) {
        this.snakeSpeed = snakeSpeed;
        return this;
    }

    /**
     * Sets the target length required to win the level.
     *
     * @param snakeLengthFinal the target length (must be greater than initial length)
     * @return this builder for chaining
     */
    public LevelBuilder snakeLengthFinal(int snakeLengthFinal) {
        this.snakeLengthFinal = snakeLengthFinal;
        return this;
    }

    /**
     * Adds a robot specification to the level.
     *
     * @param spec the robot specification
     * @return this builder for chaining
     */
    public LevelBuilder addRobotSpec(RobotSpec spec) {
        this.robotSpecs.add(spec);
        return this;
    }

    /**
     * Adds a food specification to the level.
     *
     * @param spec the food specification
     * @return this builder for chaining
     */
    public LevelBuilder addFoodSpec(FoodSpec spec) {
        this.foodSpecs.add(spec);
        return this;
    }

    /**
     * Constructs a {@link Level} instance with the configured parameters.
     *
     * @return a new Level instance
     */
    public Level build() {
        return new Level(width, height, foodAmount, snakeLengthInit, snakeSpeed, snakeLengthFinal,
            robotSpecs, foodSpecs);
    }
}