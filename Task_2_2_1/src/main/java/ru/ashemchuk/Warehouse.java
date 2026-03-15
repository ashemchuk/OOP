package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.OrderQueue.Order.Order;
import ru.ashemchuk.Utils.ThreadSafeQueue;

public class Warehouse {
    private final ThreadSafeQueue<Order> queue;
    private int capacity;
    private final Object monitor = new Object();

    public Warehouse(ThreadSafeQueue<Order> queue) {
        this.queue = queue;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be more than 0");
        }
        this.capacity = capacity;
        System.out.println("Warehouse capacity set to " + capacity);
    }

    public void addOrder(Order order) throws InterruptedException {
        synchronized (monitor) {
            if (capacity <= 0) {
                throw new IllegalStateException("Warehouse capacity isn't set");
            }

            while (queue.size() >= capacity) {
                System.out.printf(
                    "[%s][is waiting for place][busy %d / %d]\n",
                    Thread.currentThread().getName(),queue.size(), capacity);
                monitor.wait();
            }
            queue.add(order);
            System.out.printf(
                "[%s][put order %d][busy %d / %d]\n",
                Thread.currentThread().getName(),order.getId(), queue.size(), capacity);
            monitor.notifyAll();
        }
    }

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