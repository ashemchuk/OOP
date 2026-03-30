package ru.ashemchuk.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.Warehouse;
import ru.ashemchuk.config.CourierConfig;
import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.queue.order.OrderState;
import ru.ashemchuk.utils.ThreadSafeQueue;

class CourierTest {
    private Courier courier;
    private CourierConfig config;
    private Warehouse warehouse;
    private ThreadSafeQueue<Order> warehouseQueue;

    @BeforeEach
    void setUp() {
        config = mock(CourierConfig.class);
        when(config.id()).thenReturn(1);
        when(config.trunkCapacity()).thenReturn(3);
        when(config.deliveryTime()).thenReturn(100);

        warehouseQueue = new ThreadSafeQueue<>();
        warehouse = new Warehouse(warehouseQueue);
        warehouse.setCapacity(10);

        courier = new Courier(config, warehouse);
    }


    @Test
    void testCourierId() {
        assertEquals(1, courier.getId());
    }

    @Test
    void testCourierWaitsForOrders() throws InterruptedException {
        Thread courierThread = new Thread(courier);
        courierThread.start();

        Thread.sleep(200);

        assertTrue(courierThread.isAlive());

        warehouse.addOrder(new Order(1, OrderState.COOKED));
        Thread.sleep(200);

        courierThread.interrupt();
        courierThread.join();
    }

    @Test
    void testOrderStateTransitions() throws InterruptedException {
        Order order1 = new Order(1, OrderState.COOKED);
        Order order2 = new Order(2, OrderState.COOKED);

        warehouse.addOrder(order1);
        warehouse.addOrder(order2);

        Thread courierThread = new Thread(courier);
        courierThread.start();

        Thread.sleep(150);

        courierThread.interrupt();
        courierThread.join();

        assertEquals(OrderState.DONE, order1.getState());
        assertEquals(OrderState.DONE, order2.getState());
    }
}