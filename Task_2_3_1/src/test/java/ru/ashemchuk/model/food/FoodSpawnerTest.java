package ru.ashemchuk.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.level.Level;
import ru.ashemchuk.model.level.LevelBuilder;

/**
 * Unit tests for {@link FoodSpawner}.
 */
class FoodSpawnerTest {

    @Test
    void spawn_withEmptySpecs_returnsApple() throws Exception {
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .build();
        FoodSpawner spawner = new FoodSpawner();
        Food food = spawner.spawn(new Point(5, 5), level);
        assertInstanceOf(AppleFood.class, food);
    }

    @Test
    void spawn_withSingleSpec_returnsThatFood() throws Exception {
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.GRAPES, 100))
            .build();
        // Inject deterministic random that returns 0 (so first spec selected)
        FoodSpawner spawner = new FoodSpawner();
        Field randomField = FoodSpawner.class.getDeclaredField("random");
        randomField.setAccessible(true);
        Random mockRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
        randomField.set(spawner, mockRandom);

        Food food = spawner.spawn(new Point(5, 5), level);
        assertInstanceOf(GrapesFood.class, food);
    }

    @Test
    void spawn_withWeightedSpecs_selectsCorrectly() throws Exception {
        // Two specs: APPLE weight 1, GRAPES weight 99,
        // random returns 50 (between 1 and 100) -> GRAPES
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.APPLE, 1))
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.GRAPES, 99))
            .build();

        FoodSpawner spawner = new FoodSpawner();
        Field randomField = FoodSpawner.class.getDeclaredField("random");
        randomField.setAccessible(true);
        Random mockRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 50; // > 1, so should select GRAPES
            }
        };
        randomField.set(spawner, mockRandom);

        Food food = spawner.spawn(new Point(5, 5), level);
        assertInstanceOf(GrapesFood.class, food);
    }

    @Test
    void spawn_withWeightedSpecs_selectsFirstIfRandomZero() throws Exception {
        Level level = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.POISON, 10))
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.APPLE, 90))
            .build();

        FoodSpawner spawner = new FoodSpawner();
        Field randomField = FoodSpawner.class.getDeclaredField("random");
        randomField.setAccessible(true);
        Random mockRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0; // less than first weight (10) -> POISON
            }
        };
        randomField.set(spawner, mockRandom);

        Food food = spawner.spawn(new Point(5, 5), level);
        assertInstanceOf(PoisonFood.class, food);
    }

    @Test
    void spawn_createsCorrectFoodTypes() {
        FoodSpawner spawner = new FoodSpawner();
        Point pos = new Point(1, 1);

        // Use reflection to call private createFood method
        // Instead we can test via spawn with a level that has a single spec
        Level appleLevel = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.APPLE, 100))
            .build();
        Food apple = spawner.spawn(pos, appleLevel);
        assertInstanceOf(AppleFood.class, apple);
        assertEquals(pos, apple.getPosition());

        Level grapesLevel = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.GRAPES, 100))
            .build();
        Food grapes = spawner.spawn(pos, grapesLevel);
        assertInstanceOf(GrapesFood.class, grapes);
        assertEquals(pos, grapes.getPosition());

        Level poisonLevel = new LevelBuilder()
            .width(20)
            .height(20)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .addFoodSpec(new FoodSpec(FoodSpec.FoodType.POISON, 100))
            .build();
        Food poison = spawner.spawn(pos, poisonLevel);
        assertInstanceOf(PoisonFood.class, poison);
        assertEquals(pos, poison.getPosition());
    }
}