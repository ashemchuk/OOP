package ru.ashemchuk.queue;

import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.utils.ThreadSafeQueue;

/**
 * A thread-safe wrapper for the order queue that manages the flow of orders
 * in the pizzeria system. This class provides basic operations for adding
 * and retrieving orders, delegating the actual thread-safe operations to
 * the underlying ThreadSafeQueue.
 * Orders are stored in FIFO (First-In-First-Out) order, ensuring that
 * older orders are processed before newer ones.
 *
 * @see ThreadSafeQueue
 * @see Order
 */
public class OrderQueue {

    /**
     * The underlying thread-safe queue that stores the orders.
     */
    private final ThreadSafeQueue<Order> queue;

    /**
     * Constructs a new OrderQueue with the specified thread-safe queue.
     *
     * @param queue the thread-safe queue instance to use for order storage
     * @throws NullPointerException if the provided queue is null
     */
    public OrderQueue(ThreadSafeQueue<Order> queue) {
        if (queue == null) {
            throw new NullPointerException("Queue cannot be null");
        }
        this.queue = queue;
    }

    /**
     * Adds a new order to the end of the queue.
     * This operation is thread-safe and non-blocking.
     *
     * @param order the order to be added to the queue
     * @throws NullPointerException if the provided order is null
     */
    public void addOrder(Order order) {
        if (order == null) {
            throw new NullPointerException("Order cannot be null");
        }
        queue.add(order);
    }

    /**
     * Retrieves and removes the oldest order from the queue.
     * If the queue is empty, this method blocks until an order becomes available.
     * This operation is thread-safe.
     *
     * @return the oldest order from the queue
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public Order takeOrder() throws InterruptedException {
        return queue.pop();
    }
}