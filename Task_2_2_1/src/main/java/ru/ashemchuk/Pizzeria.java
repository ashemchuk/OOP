package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.Config.PizzeriaConfig;
import ru.ashemchuk.OrderQueue.OrderQueue;
import ru.ashemchuk.Utils.ThreadSafeQueue;
import ru.ashemchuk.Worker.Baker;
import ru.ashemchuk.Worker.Courier;

public class Pizzeria {
    private final OrderQueue orderQueue;
    private final Warehouse warehouse;
    private final List<Baker> bakers;
    private final List<Courier> couriers;
    private Customer customer;
    private final PizzeriaConfig cfg;
    private int workingTime;

    private final List<Thread> bakerThreads;
    private final List<Thread> courierThreads;
    private Thread customerThread;

    public Pizzeria(PizzeriaConfig cfg) {
        this.cfg = cfg;
        this.orderQueue = new OrderQueue(new ThreadSafeQueue<>());
        this.warehouse = new Warehouse(new ThreadSafeQueue<>());

        this.bakers = new ArrayList<>();
        this.couriers = new ArrayList<>();
        this.bakerThreads = new ArrayList<>();
        this.courierThreads = new ArrayList<>();
    }
    public void configure() {
        try {


            this.warehouse.setCapacity(cfg.warehouseCapacity());

            System.out.println("Warehouse capacity is set: " + cfg.warehouseCapacity());

            for (var bakerCfg : cfg.bakers()) {
                Baker b = new Baker(bakerCfg, this.orderQueue, this.warehouse);
                bakers.add(b);
            }
            for (var courierCfg : cfg.couriers()) {
                Courier c = new Courier(courierCfg, this.warehouse);
                couriers.add(c);
            }
            this.workingTime = cfg.workingTime();

            this.customer = new Customer(orderQueue);

        } catch (Exception ex) {
            throw new ConfigurationLoadingException("Couldn't load configuration: " + ex.getMessage());
        }
    }

    public void startSimulate() {
        for (Baker baker : bakers) {
            Thread thread = new Thread(baker, "Baker-" + baker.getId());
            bakerThreads.add(thread);
            thread.start();
        }

        for (Courier courier : couriers) {
            Thread thread = new Thread(courier, "Courier-" + courier.getId());
            courierThreads.add(thread);
            thread.start();
        }

        customerThread = new Thread(customer, "Customer");
        customerThread.start();

        try {
            Thread.sleep(workingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        shutdownPizzeria();
    }

    private void shutdownPizzeria() {
        System.out.println("Pizzeria are closing, orders taken will be rejected");

        customerThread.interrupt();

        for (Thread thread : bakerThreads) {
            thread.interrupt();
        }
        for (Thread thread : courierThreads) {
            thread.interrupt();
        }


        try {
            customerThread.join(5000);

            for (Thread thread : bakerThreads) {
                thread.join(5000);
            }

            for (Thread thread : courierThreads) {
                thread.join(5000);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}