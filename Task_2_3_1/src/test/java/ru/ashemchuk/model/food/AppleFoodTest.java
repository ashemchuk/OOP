package ru.ashemchuk.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.snake.PlayerSnake;

class AppleFoodTest {

    @Test
    void appleEffectDoesNotChangeSpeed() {
        AppleFood apple = new AppleFood(new Point(0, 0));
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        int initialSpeed = snake.getSpeed();
        apple.effect(snake);
        assertEquals(initialSpeed, snake.getSpeed());
        // Apple does not affect speed, only triggers grow flag via snake.eat()
    }

    @Test
    void appleType() {
        AppleFood apple = new AppleFood(new Point(0, 0));
        assertEquals(FoodSpec.FoodType.APPLE, apple.getType());
    }
}