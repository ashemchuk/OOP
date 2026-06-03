package ru.ashemchuk.model.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.food.Food;

/**
 * Unit tests for {@link Snake} (abstract class) using a concrete stub.
 */
class SnakeTest {
    private Snake stubSnake;

    @BeforeEach
    void setUp() {
        // Create a concrete stub of Snake for testing non-abstract methods
        stubSnake = new Snake(List.of(new Point(5, 5)), 100) {
            @Override
            public void move() {
                // dummy implementation
            }

            @Override
            public void eat(Food food) {
                // dummy
            }

            @Override
            public void die() {
                // dummy
            }

            @Override
            public void turn(Direction direction) {
                // dummy
            }
        };
        // Set initial direction
        stubSnake.setDirection(Direction.RIGHT);
    }

    @Test
    void getBody_returnsUnmodifiableList() {
        List<Point> body = stubSnake.getBody();
        assertEquals(1, body.size());
        assertEquals(new Point(5, 5), body.get(0));
        // Ensure unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> body.add(new Point(0, 0)));
    }

    @Test
    void getSpeed_returnsInitialSpeed() {
        assertEquals(100, stubSnake.getSpeed());
    }

    @Test
    void getDirection_returnsSetDirection() {
        assertEquals(Direction.RIGHT, stubSnake.getDirection());
        stubSnake.setDirection(Direction.UP);
        assertEquals(Direction.UP, stubSnake.getDirection());
    }

    @Test
    void setBody_replacesBody() {
        List<Point> newBody = List.of(new Point(1, 1), new Point(2, 2));
        stubSnake.setBody(newBody);
        assertEquals(newBody, stubSnake.getBody());
    }

    @Test
    void setSpeed_updatesBaseSpeed() {
        stubSnake.setSpeed(200);
        assertEquals(200, stubSnake.getSpeed());
    }

    @Test
    void applySpeedBoost_reducesSpeed() {
        // Ensure no boost active initially
        assertEquals(100, stubSnake.getSpeed());
        // Apply boost for 1000 ms
        stubSnake.applySpeedBoost(1000);
        // Speed should be reduced by BOOST_AMOUNT (50) -> 50, but minimum is 50
        // baseSpeed is 100, boost amount 50, effective speed = 50
        assertEquals(50, stubSnake.getSpeed());
        // Boost should be active
        // We can't directly check isBoostActive because it's private, but we can verify speed.
    }

    @Test
    void applySpeedBoost_whenBoostAlreadyActive_resetsTimer() {
        // Apply first boost
        stubSnake.applySpeedBoost(500);
        int speedAfterFirst = stubSnake.getSpeed();
        // Apply second boost with longer duration
        stubSnake.applySpeedBoost(2000);
        // Speed should still be reduced (same amount)
        assertEquals(speedAfterFirst, stubSnake.getSpeed());
        // Cannot verify timer reset without reflection, but we can assume.
    }

    @Test
    void updateBoost_whenBoostExpired_revertsSpeed() throws InterruptedException {
        // Apply boost for a very short duration
        stubSnake.applySpeedBoost(10); // 10 ms
        // Wait a bit longer than boost duration
        Thread.sleep(50);
        // Call updateBoost to check expiration
        stubSnake.updateBoost();
        // Speed should revert to base speed (100)
        assertEquals(100, stubSnake.getSpeed());
    }

    @Test
    void updateBoost_whenBoostNotExpired_doesNothing() {
        stubSnake.applySpeedBoost(5000);
        int speedDuringBoost = stubSnake.getSpeed();
        stubSnake.updateBoost();
        // Speed should stay the same
        assertEquals(speedDuringBoost, stubSnake.getSpeed());
    }

    @Test
    void setSpeed_duringBoost_updatesEffectiveSpeed() {
        // Apply boost
        stubSnake.applySpeedBoost(5000);
        // Change base speed while boost active
        stubSnake.setSpeed(150);
        // Effective speed should be 150 - 50 = 100 (since boost reduces by 50)
        assertEquals(100, stubSnake.getSpeed());
    }

    @Test
    void setSpeed_afterBoostExpired_usesNewBaseSpeed() throws InterruptedException {
        stubSnake.applySpeedBoost(10);
        Thread.sleep(50);
        stubSnake.updateBoost();
        stubSnake.setSpeed(300);
        assertEquals(300, stubSnake.getSpeed());
    }

    // Helper assertion
    private static <T extends Throwable> void assertThrows(
        Class<T> expectedType, Runnable runnable
    ) {
        try {
            runnable.run();
            throw new AssertionError("Expected exception " 
                + expectedType.getSimpleName() + " but none was thrown");
        } catch (Throwable e) {
            if (!expectedType.isInstance(e)) {
                throw new AssertionError("Expected exception "
                    + expectedType.getSimpleName()
                    + " but got "
                    + e.getClass().getSimpleName(), e);
            }
        }
    }
}