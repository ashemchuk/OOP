package ru.ashemchuk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.ashemchuk.OrderQueue.Order.Order;
import ru.ashemchuk.OrderQueue.Order.OrderState;
import ru.ashemchuk.OrderQueue.OrderQueue;
import ru.ashemchuk.Utils.ThreadSafeQueue;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private OrderQueue orderQueue;
    private Customer customer;
    private Thread customerThread;

    @BeforeEach
    void setUp() {
        orderQueue = new OrderQueue(new ThreadSafeQueue<>());
        customer = new Customer(orderQueue);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testCustomerCreatesOrders() throws InterruptedException {
        customerThread = new Thread(customer);
        customerThread.start();

        Thread.sleep(500);

        customerThread.interrupt();
        customerThread.join();

        Order order = orderQueue.takeOrder();
        assertNotNull(order);
        assertEquals(OrderState.WAITING, order.getState());
    }

    @Test
    void testCustomerInterruption() throws InterruptedException {
        customerThread = new Thread(customer);
        customerThread.start();

        Thread.sleep(100);
        customerThread.interrupt();
        customerThread.join();

        assertFalse(customerThread.isAlive());
    }
}