package ru.ashemchuk.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.Warehouse;
import ru.ashemchuk.config.BakerConfig;
import ru.ashemchuk.queue.OrderQueue;
import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.queue.order.OrderState;
import ru.ashemchuk.utils.ThreadSafeQueue;

class BakerTest {
    private Baker baker;
    private BakerConfig config;
    private OrderQueue orderQueue;
    private Warehouse warehouse;
    private ThreadSafeQueue<Order> queue;

    @BeforeEach
    void setUp() {
        config = mock(BakerConfig.class);
        when(config.id()).thenReturn(1);
        when(config.workingTime()).thenReturn(100);

        queue = new ThreadSafeQueue<>();
        orderQueue = new OrderQueue(queue);
        warehouse = new Warehouse(new ThreadSafeQueue<>());
        warehouse.setCapacity(5);

        baker = new Baker(config, orderQueue, warehouse);
    }

    @Test
    void testBakerProcessesOrder() throws InterruptedException {
        Order order = new Order(1, OrderState.WAITING);
        orderQueue.addOrder(order);

        Thread bakerThread = new Thread(baker);
        bakerThread.start();

        Thread.sleep(200);

        bakerThread.interrupt();
        bakerThread.join();

        assertEquals(OrderState.COOKED, order.getState());
    }

    @Test
    void testBakerId() {
        assertEquals(1, baker.getId());
    }

    @Test
    void testBakerInterruption() throws InterruptedException {
        orderQueue.addOrder(new Order(1, OrderState.WAITING));

        Thread bakerThread = new Thread(baker);
        bakerThread.start();

        Thread.sleep(50);
        bakerThread.interrupt();
        bakerThread.join();

        assertFalse(bakerThread.isAlive());
    }

    @Test
    void testBakerWaitsForOrders() throws InterruptedException {
        Thread bakerThread = new Thread(baker);
        bakerThread.start();

        Thread.sleep(200);

        assertTrue(bakerThread.isAlive());

        bakerThread.interrupt();
        bakerThread.join();
    }
}