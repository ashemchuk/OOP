package ru.ashemchuk.Utils;

import java.util.*;

public class ThreadSafeQueue<T> {
    private final Queue<T> queue = new LinkedList<>();

    public synchronized void add(T item) {
        queue.add(item);
        notifyAll();
    }

    public synchronized T pop() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }

    public synchronized T poll() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized void clear() {
        queue.clear();
    }
}