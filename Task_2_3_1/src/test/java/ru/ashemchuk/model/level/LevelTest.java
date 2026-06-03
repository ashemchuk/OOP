package ru.ashemchuk.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.food.FoodSpec;

class LevelTest {

    @Test
    void levelRecord() {
        List<RobotSpec> robots = List.of(new RobotSpec(RobotSpec.RobotType.RANDOM, 0, 0));
        List<FoodSpec> foods = List.of(new FoodSpec(FoodSpec.FoodType.APPLE, 1));
        Level level = new Level(10, 20, 3, 1, 100, 5, robots, foods);
        assertEquals(10, level.width());
        assertEquals(20, level.height());
        assertEquals(3, level.foodAmount());
        assertEquals(1, level.snakeLengthInit());
        assertEquals(100, level.snakeSpeed());
        assertEquals(5, level.snakeLengthFinal());
        assertEquals(robots, level.robotSpecs());
        assertEquals(foods, level.foodSpecs());
    }

    @Test
    void defensiveCopies() {
        List<RobotSpec> robots = new java.util.ArrayList<>();
        robots.add(new RobotSpec(RobotSpec.RobotType.FOLLOW_PLAYER, -1, 0));
        List<FoodSpec> foods = new java.util.ArrayList<>();
        foods.add(new FoodSpec(FoodSpec.FoodType.GRAPES, 2));
        Level level = new Level(12, 12, 2, 1, 150, 8, robots, foods);
        // Modify original lists
        robots.clear();
        foods.clear();
        // Level's internal lists should be unchanged
        assertEquals(1, level.robotSpecs().size());
        assertEquals(1, level.foodSpecs().size());
    }
}