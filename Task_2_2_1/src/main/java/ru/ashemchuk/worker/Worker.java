package ru.ashemchuk.worker;

import ru.ashemchuk.queue.order.Order;

/**
 * Abstract base class for all workers in the pizzeria system.
 * Provides common functionality for both bakers and couriers,
 * including thread management and logging capabilities.
 *
 * <p>This class implements {@link Runnable} to allow workers to run in separate threads.
 * The main execution loop continuously calls the abstract {@link #work()} method
 * until the thread is interrupted.
 *
 * <p>Concrete worker classes must implement the {@link #work()} method to define
 * their specific job responsibilities.
 *
 * @see Baker
 * @see Courier
 */
public abstract class Worker implements Runnable {

    /**
     * Performs a single unit of work specific to the worker type.
     * This method is called repeatedly by the {@link #run()} method.
     *
     * <p>Implementations should define the specific workflow for:
     * <ul>
     *   <li>{@link Baker} - Cooking orders</li>
     *   <li>{@link Courier} - Delivering orders</li>
     * </ul>
     *
     * @throws InterruptedException if the thread is interrupted during work operation
     */
    public abstract void work() throws InterruptedException;

    /**
     * The main execution loop for the worker thread.
     * Continuously calls {@link #work()} until the thread is interrupted.
     * Handles interruption gracefully by preserving the interrupt status
     * and logging the interruption event.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                work();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("[%s][was interrupted]\n", Thread.currentThread().getName());
        }
    }

    /**
     * Logs the current state of an order with the worker's thread information.
     * Provides consistent logging format across all worker types.
     *
     * <p>Log format: [{thread name}][order {order ID}][{order state}]
     *
     * @param o the order to log, can be null (method will safely ignore null orders)
     */
    void log(Order o) {
        if (o != null) {
            System.out.printf("[%s][order %d][%s]%n",
                Thread.currentThread().getName(),
                o.getId(),
                o.getState().name());
        }
    }
}