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
            throw new IllegalArgumentException("Вместимость склада должна быть положительной, а не " + capacity);
        }
        this.capacity = capacity;
        System.out.println("=== Warehouse capacity set to " + capacity + " ===");
    }

    public void addOrder(Order order) throws InterruptedException {
        synchronized (monitor) {
            // Защита от неправильной конфигурации
            if (capacity <= 0) {
                throw new IllegalStateException("Вместимость склада не установлена! capacity=" + capacity);
            }

            while (queue.size() >= capacity) {
                System.out.println(Thread.currentThread().getName() + " ждет место на складе. Текущий размер: " + queue.size() + "/" + capacity);
                monitor.wait();
            }
            queue.add(order);
            System.out.println(Thread.currentThread().getName() + " ПОЛОЖИЛ НА СКЛАД order " + order.getId() +
                ". Теперь на складе: " + queue.size() + "/" + capacity);
            monitor.notifyAll();
        }
    }

    public List<Order> takeOrders(int maxCount) throws InterruptedException {
        List<Order> orders = new ArrayList<>();

        synchronized (monitor) {
            while (queue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " ждет заказы на складе. Склад пуст");
                monitor.wait();
            }

            // Берем первый заказ
            Order first = queue.pop();
            orders.add(first);

            // Пытаемся взять еще
            for (int i = 1; i < maxCount; i++) {
                Order order = queue.poll();
                if (order != null) {
                    orders.add(order);
                } else {
                    break;
                }
            }

            System.out.println(Thread.currentThread().getName() + " ВЗЯЛ СО СКЛАДА " + orders.size() +
                " заказов: " + orders.stream().map(o -> String.valueOf(o.getId())).reduce((a, b) -> a + "," + b).orElse("") +
                ". Осталось на складе: " + queue.size() + "/" + capacity);

            monitor.notifyAll(); // Уведомляем пекарей, что освободилось место
        }

        return orders;
    }

}