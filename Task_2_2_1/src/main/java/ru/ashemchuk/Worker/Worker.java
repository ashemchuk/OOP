package ru.ashemchuk.Worker;

import ru.ashemchuk.OrderQueue.Order.Order;

public abstract class Worker implements Runnable{

    public abstract void work() throws InterruptedException;
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
    void log(Order o) {
        if (o != null) {
            System.out.printf("[%s][order %d][%s]%n",
                Thread.currentThread().getName(),
                o.getId(),
                o.getState().name());
        }
    }
}
