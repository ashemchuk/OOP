package ru.ashemchuk.OrderQueue;

import ru.ashemchuk.OrderQueue.Order.Order;
import ru.ashemchuk.Utils.ThreadSafeQueue;

public class OrderQueue{
    ThreadSafeQueue<Order> queue;
    public OrderQueue(ThreadSafeQueue<Order> queue) {
        this.queue = queue;
    }

    public void addOrder(Order order) {
        queue.add(order);
    }

    public Order takeOrder() throws InterruptedException{
        return queue.pop();
    }
}
