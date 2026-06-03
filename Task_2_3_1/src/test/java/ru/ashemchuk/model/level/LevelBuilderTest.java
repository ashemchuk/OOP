package ru.ashemchuk.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.food.FoodSpec;

class LevelBuilderTest {

    @Test
    void buildDefault() {
        Level level = new LevelBuilder().build();
        assertEquals(12, level.width());
        assertEquals(12, level.height());
        assertEquals(1, level.foodAmount());
        assertEquals(1, level.snakeLengthInit());
        assertEquals(250, level.snakeSpeed());
        assertEquals(8, level.snakeLengthFinal());
        assertTrue(level.robotSpecs().isEmpty());
        assertTrue(level.foodSpecs().isEmpty());
    }

    @Test
    void buildCustom() {
        Level level = new LevelBuilder()
            .width(20)
            .height(15)
            .foodAmount(3)
            .snakeLengthInit(2)
            .snakeSpeed(150)
            .snakeLengthFinal(12)
            .addRobotSpec(new RobotSpec(RobotSpec.RobotType.RANDOM, 0, 0))
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.APPLE, 1))
            .build();
        assertEquals(20, level.width());
        assertEquals(15, level.height());
        assertEquals(3, level.foodAmount());
        assertEquals(2, level.snakeLengthInit());
        assertEquals(150, level.snakeSpeed());
        assertEquals(12, level.snakeLengthFinal());
        assertEquals(1, level.robotSpecs().size());
        assertEquals(1, level.foodSpecs().size());
    }
}