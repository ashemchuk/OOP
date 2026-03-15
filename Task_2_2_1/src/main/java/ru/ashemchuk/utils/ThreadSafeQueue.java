package ru.ashemchuk.utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A thread-safe queue implementation that provides synchronized access to an underlying.
 * {@link LinkedList}. This class is designed for producer-consumer scenarios where
 * multiple threads need to safely add and remove elements.
 *
 * <p>Key features:
 * <ul>
 *   <li>Thread safety - All methods are synchronized to prevent concurrent modification</li>
 *   <li>Blocking pop - {@link #pop()} blocks until an element becomes available</li>
 *   <li>Non-blocking poll - {@link #poll()} returns null immediately if queue is empty</li>
 *   <li>Wait-notify - Uses {@link #wait()} and {@link #notifyAll()} for efficient blocking</li>
 * </ul>
 *
 * <p>This queue is used throughout the pizzeria system:
 * <ul>
 *   <li>{@link ru.ashemchuk.queue.OrderQueue} - For waiting orders</li>
 *   <li>{@link ru.ashemchuk.Warehouse} - For cooked orders storage</li>
 * </ul>
 *
 * @param <T> the type of elements held in this queue
 * @see java.util.Queue
 * @see java.util.LinkedList
 */
public class ThreadSafeQueue<T> {

    /**
     * The underlying non-thread-safe queue that is protected by synchronization.
     */
    private final Queue<T> queue = new LinkedList<>();

    /**
     * Adds an element to the end of the queue.
     * This method is synchronized and notifies any waiting threads that
     * a new element is available.
     *
     * @param item the element to add to the queue
     */
    public synchronized void add(T item) {
        queue.add(item);
        notifyAll();
    }

    /**
     * Retrieves and removes the head of the queue, waiting if necessary
     * until an element becomes available.
     *
     * <p>This method blocks indefinitely until:
     * <ul>
     *   <li>An element is added to the queue (by {@link #add})</li>
     *   <li>The thread is interrupted</li>
     * </ul>
     *
     * @return the head of the queue
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public synchronized T pop() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }

    /**
     * Retrieves and removes the head of the queue, or returns {@code null}
     * if the queue is empty. This method does not block.
     *
     * @return the head of the queue, or {@code null} if the queue is empty
     */
    public synchronized T poll() {
        return queue.poll();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return {@code true} if the queue contains no elements, {@code false} otherwise
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the current size of the queue
     */
    public synchronized int size() {
        return queue.size();
    }

    /**
     * Removes all elements from the queue.
     * The queue will be empty after this call returns.
     */
    public synchronized void clear() {
        queue.clear();
    }
}