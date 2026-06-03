package ru.ashemchuk.model.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;

class PlayerSnakeTest {

    @Test
    void snakeStartsWithSingleSegment() {
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        assertEquals(1, snake.getBody().size());
        assertEquals(new Point(5, 5), snake.getBody().get(0));
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    void moveForward() {
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        snake.move();
        assertEquals(1, snake.getBody().size());
        assertEquals(new Point(6, 5), snake.getBody().get(0));
    }

    @Test
    void turnAndMove() {
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        snake.turn(Direction.DOWN);
        snake.move();
        assertEquals(new Point(5, 6), snake.getBody().get(0));
    }

    @Test
    void cannotTurnOpposite() {
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        snake.turn(Direction.LEFT); // opposite of RIGHT should be ignored
        assertEquals(Direction.RIGHT, snake.getDirection());
        snake.move();
        assertEquals(new Point(6, 5), snake.getBody().get(0));
    }

    @Test
    void growAfterEating() {
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        // simulate eating (call eat with a dummy food that triggers grow)
        snake.eat(new ru.ashemchuk.model.food.AppleFood(new Point(0, 0)));
        assertTrue(snake.isGrowing());
        snake.move(); // should grow
        assertEquals(2, snake.getBody().size());
        assertEquals(new Point(6, 5), snake.getBody().get(0));
        assertEquals(new Point(5, 5), snake.getBody().get(1));
        // after moving, grow flag should reset
        assertFalse(snake.isGrowing());
        snake.move(); // normal move without growing
        assertEquals(2, snake.getBody().size());
        assertEquals(new Point(7, 5), snake.getBody().get(0));
        assertEquals(new Point(6, 5), snake.getBody().get(1));
    }

    @Test
    void shrink() {
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        snake.eat(new ru.ashemchuk.model.food.AppleFood(new Point(0, 0)));
        snake.move(); // length 2
        snake.shrink();
        assertTrue(snake.isShrinking());
        snake.move(); // should shrink (remove extra segment)
        assertEquals(1, snake.getBody().size());
        assertEquals(new Point(7, 5), snake.getBody().get(0));
        assertFalse(snake.isShrinking());
    }

    @Test
    void die() {
        PlayerSnake snake = new PlayerSnake(new Point(5, 5), 100);
        assertTrue(snake.isAlive());
        snake.die();
        assertFalse(snake.isAlive());
        snake.move(); // should not move because dead
        assertEquals(new Point(5, 5), snake.getBody().get(0));
    }
}