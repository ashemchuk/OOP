package ru.ashemchuk;

import java.util.Random;
import ru.ashemchuk.queue.OrderQueue;
import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.queue.order.OrderState;

/**
 * Represents a customer that generates orders and adds them to the order queue.
 * This class implements Runnable to allow continuous order generation in a separate thread.
 * Orders are created at random intervals between 50 and 200 milliseconds.
 *
 */
public class Customer implements Runnable {
    /**
     * The queue where generated orders are placed.
     */
    private final OrderQueue queue;

    /**
     * Random number generator for creating variable order intervals.
     */
    private final Random random = new Random();

    /**
     * Counter for total number of orders created by this customer.
     */
    private int ordersCreated = 0;

    /**
     * Constructs a new Customer with the specified order queue.
     *
     * @param queue the order queue where generated orders will be added
     */
    public Customer(OrderQueue queue) {
        this.queue = queue;
    }

    /**
     * The main execution method for the customer thread.
     * Continuously generates new orders and adds them to the queue at random intervals.
     * Each order is initialized with WAITING state.
     * The loop continues until the thread is interrupted.
     * Order generation interval: 50-200 milliseconds (50 + random up to 150)
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                queue.addOrder(new Order(ordersCreated++, OrderState.WAITING));
                Thread.sleep(50 + random.nextInt(150));
            }
        } catch (InterruptedException e) {
            System.out.println("Orders have been created: " + ordersCreated);
            Thread.currentThread().interrupt();
        }
    }
}