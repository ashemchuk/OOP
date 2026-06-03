package ru.ashemchuk.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.snake.PlayerSnake;

class GrapesFoodTest {

    @Test
    void grapesEffectDecreasesSpeed() {
        GrapesFood grapes = new GrapesFood(new Point(0, 0));
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        int initialSpeed = snake.getSpeed();
        grapes.effect(snake);
        assertEquals(initialSpeed - 50, snake.getSpeed());
    }

    @Test
    void grapesEffectDoesNotGoBelowMinimum() {
        GrapesFood grapes = new GrapesFood(new Point(0, 0));
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 80); // speed 80
        grapes.effect(snake);
        assertEquals(50, snake.getSpeed()); // 80 - 50 = 30, but min is 50
        // Apply again
        grapes.effect(snake);
        assertEquals(50, snake.getSpeed()); // still 50
    }

    @Test
    void grapesType() {
        GrapesFood grapes = new GrapesFood(new Point(0, 0));
        assertEquals(FoodSpec.FoodType.GRAPES, grapes.getType());
    }
}