package ru.ashemchuk.Worker;

import ru.ashemchuk.OrderQueue.Order.Order;

public abstract class Worker implements Runnable{
    //FIXME: state for logging
    protected Order currentOrder;

    public abstract void work() throws InterruptedException;
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                work();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    synchronized void log() {
        if (currentOrder != null) {
            System.out.printf("[%s][order %d][%s]%n",
                Thread.currentThread().getName(),
                currentOrder.getId(),
                currentOrder.getState().name());
        }
    }
}
