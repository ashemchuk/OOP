package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.utils.ThreadSafeQueue;

/**
 * Represents a warehouse for storing finished orders before courier pickup.
 * Provides thread-safe operations for adding and taking orders with capacity limits.
 * Uses wait-notify mechanism to manage producer-consumer relationships between
 * bakers (producers) and couriers (consumers).
 */
public class Warehouse {
    /**
     * Thread-safe queue for storing orders.
     */
    private final ThreadSafeQueue<Order> queue;

    /**
     * Maximum number of orders the warehouse can hold.
     */
    private int capacity;

    /**
     * Monitor object for synchronization.
     */
    private final Object monitor = new Object();

    /**
     * Constructs a new Warehouse with the specified queue.
     *
     * @param queue the thread-safe queue to use for order storage
     */
    public Warehouse(ThreadSafeQueue<Order> queue) {
        this.queue = queue;
    }

    /**
     * Sets the maximum capacity of the warehouse.
     * Capacity must be positive.
     *
     * @param capacity the maximum number of orders the warehouse can hold
     * @throws IllegalArgumentException if capacity is less than or equal to 0
     */
    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be more than 0");
        }
        this.capacity = capacity;
        System.out.println("Warehouse capacity set to " + capacity);
    }

    /**
     * Adds an order to the warehouse.
     * If the warehouse is full, the calling thread waits until space becomes available.
     * This method is thread-safe and uses wait-notify for synchronization.
     *
     * @param order the order to add to the warehouse
     * @throws InterruptedException  if the thread is interrupted while waiting
     * @throws IllegalStateException if warehouse capacity hasn't been set
     */
    public void addOrder(Order order) throws InterruptedException {
        synchronized (monitor) {
            if (capacity <= 0) {
                throw new IllegalStateException("Warehouse capacity isn't set");
            }

            while (queue.size() >= capacity) {
                System.out.printf(
                    "[%s][is waiting for place][busy %d / %d]\n",
                    Thread.currentThread().getName(), queue.size(), capacity);
                monitor.wait();
            }
            queue.add(order);
            System.out.printf(
                "[%s][put order %d][busy %d / %d]\n",
                Thread.currentThread().getName(), order.getId(), queue.size(), capacity);
            monitor.notifyAll();
        }
    }

    /**
     * Takes up to maxCount orders from the warehouse.
     * If the warehouse is empty, the calling thread waits until orders become available.
     * Always takes at least one order (blocks until one is available).
     * Then attempts to take additional orders up to maxCount without blocking.
     *
     * @param maxCount the maximum number of orders to take
     * @return a list containing the taken orders (size between 1 and maxCount)
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public List<Order> takeOrders(int maxCount) throws InterruptedException {
        List<Order> orders = new ArrayList<>();

        synchronized (monitor) {
            while (queue.isEmpty()) {
                System.out.printf("[%s][is waiting for orders][warehouse is empty]\n",
                    Thread.currentThread().getName());
                monitor.wait();
            }

            Order first = queue.pop();
            orders.add(first);

            for (int i = 1; i < maxCount; i++) {
                Order order = queue.poll();
                if (order != null) {
                    orders.add(order);
                } else {
                    break;
                }
            }
            var ordersEnum = orders
                .stream()
                .map(o -> String.valueOf(o.getId()))
                .reduce((a, b) -> a + "," + b)
                .orElse("");
            System.out.printf("[%s][take %d orders: %s][busy %d / %d]\n",
                Thread.currentThread().getName(),
                orders.size(),
                ordersEnum,
                queue.size(),
                capacity);

            monitor.notifyAll();
        }

        return orders;
    }
}