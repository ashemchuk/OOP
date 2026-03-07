package ru.ashemchuk;

import java.util.Random;
import ru.ashemchuk.OrderQueue.Order.Order;
import ru.ashemchuk.OrderQueue.Order.OrderState;
import ru.ashemchuk.OrderQueue.OrderQueue;

public class Customer implements Runnable {
    private final OrderQueue queue;
    private final Random random = new Random();
    private int ordersCreated = 0;

    public Customer(OrderQueue queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                queue.addOrder(new Order(ordersCreated++, OrderState.WAITING));
                Thread.sleep(50 + random.nextInt(150));
            }
        } catch (InterruptedException e) {
            System.out.println("Прием заказов остановлен. Всего создано заказов: " + ordersCreated);
            Thread.currentThread().interrupt();
        }
    }
}