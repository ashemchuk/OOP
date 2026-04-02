package ru.ashemchuk.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.snake.PlayerSnake;

class PoisonFoodTest {

    @Test
    void poisonEffectTriggersShrink() {
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        // Make snake grow first
        snake.eat(new AppleFood(new Point(0, 0)));
        snake.move(); // length 2
        assertFalse(snake.isShrinking());

        PoisonFood poison = new PoisonFood(new Point(0, 0));
        poison.effect(snake);
        
        assertTrue(snake.isShrinking());
        // Next move will shrink
        snake.move();
        assertEquals(1, snake.getBody().size());
    }

    @Test
    void poisonType() {
        PoisonFood poison = new PoisonFood(new Point(0, 0));
        assertEquals(FoodSpec.FoodType.POISON, poison.getType());
    }
}