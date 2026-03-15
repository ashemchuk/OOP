package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.queue.order.OrderState;
import ru.ashemchuk.utils.ThreadSafeQueue;

class WarehouseTest {
    private Warehouse warehouse;
    private ThreadSafeQueue<Order> queue;

    @BeforeEach
    void setUp() {
        queue = new ThreadSafeQueue<>();
        warehouse = new Warehouse(queue);
        warehouse.setCapacity(5);
    }

    @Test
    void testSetCapacity() {
        warehouse.setCapacity(10);
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 10; i++) {
                warehouse.addOrder(new Order(i, OrderState.COOKED));
            }
        });
    }

    @Test
    void testSetInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> warehouse.setCapacity(0));
        assertThrows(IllegalArgumentException.class, () -> warehouse.setCapacity(-5));
    }

    @Test
    void testAddOrder() throws InterruptedException {
        Order order = new Order(1, OrderState.COOKED);
        warehouse.addOrder(order);

        assertEquals(1, queue.size());
    }

    @Test
    void testAddOrderWhenFull() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            warehouse.addOrder(new Order(i, OrderState.COOKED));
        }

        Thread adder = new Thread(() -> {
            try {
                warehouse.addOrder(new Order(5, OrderState.COOKED));
            } catch (InterruptedException e) {
                fail("Should not be interrupted");
            }
        });

        adder.start();
        Thread.sleep(100);

        warehouse.takeOrders(1);

        adder.join(1000);
        assertFalse(adder.isAlive());
        assertEquals(5, queue.size());
    }

    @Test
    void testTakeOrders() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            warehouse.addOrder(new Order(i, OrderState.COOKED));
        }

        List<Order> orders = warehouse.takeOrders(2);

        assertEquals(2, orders.size());
        assertEquals(0, orders.get(0).getId());
        assertEquals(1, orders.get(1).getId());
        assertEquals(1, queue.size());
    }

    @Test
    void testTakeOrdersMoreThanAvailable() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            warehouse.addOrder(new Order(i, OrderState.COOKED));
        }

        List<Order> orders = warehouse.takeOrders(5);

        assertEquals(2, orders.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testTakeOrdersWhenEmpty() {
        Thread taker = new Thread(() -> {
            try {
                warehouse.takeOrders(1);
                fail("Should block on empty warehouse");
            } catch (InterruptedException e) {
                // Expected when interrupted
            }
        });

        taker.start();
        taker.interrupt();

        try {
            taker.join();
        } catch (InterruptedException e) {
            fail("Test interrupted");
        }
    }
}