package ru.ashemchuk.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.snake.Snake;

/**
 * Unit tests for {@link Food} (abstract class) using a concrete stub.
 */
class FoodTest {
    private static class ConcreteFood extends Food {
        ConcreteFood(Point position, FoodSpec.FoodType type) {
            super(position, type);
        }

        @Override
        public void effect(Snake snake) {
            // dummy implementation
        }
    }

    @Test
    void constructor_initializesPositionAndType() {
        Point pos = new Point(3, 4);
        Food food = new ConcreteFood(pos, FoodSpec.FoodType.APPLE);
        assertEquals(pos, food.getPosition());
        assertEquals(FoodSpec.FoodType.APPLE, food.getType());
    }

    @Test
    void setPosition_updatesPosition() {
        Food food = new ConcreteFood(new Point(0, 0), FoodSpec.FoodType.GRAPES);
        Point newPos = new Point(5, 5);
        food.setPosition(newPos);
        assertEquals(newPos, food.getPosition());
    }

    @Test
    void eat_defaultImplementationDoesNothing() {
        // Ensure no exception is thrown
        Food food = new ConcreteFood(new Point(0, 0), FoodSpec.FoodType.POISON);
        food.eat();
        // If we reach here, test passes
    }

    @Test
    void post_defaultImplementationDoesNothing() {
        Food food = new ConcreteFood(new Point(0, 0), FoodSpec.FoodType.APPLE);
        food.post();
        // No exception expected
    }

    @Test
    void effect_callsSubclassImplementation() {
        // Use a mock snake to verify interaction
        Snake mockSnake = mock(Snake.class);
        Food food = new ConcreteFood(new Point(1, 1), FoodSpec.FoodType.APPLE) {
            @Override
            public void effect(Snake snake) {
                snake.die(); // just to have an effect
            }
        };
        food.effect(mockSnake);
        verify(mockSnake).die();
    }
}