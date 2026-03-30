package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.config.PizzeriaConfig;
import ru.ashemchuk.queue.OrderQueue;
import ru.ashemchuk.utils.ThreadSafeQueue;
import ru.ashemchuk.worker.Baker;
import ru.ashemchuk.worker.Courier;

/**
 * Main controller class for the pizzeria simulation.
 * Manages the configuration, initialization, and lifecycle of all pizzeria components.
 * Creates and coordinates threads for customers, bakers, and couriers.
 * Handles graceful shutdown of all worker threads when the simulation ends.
 */
public class Pizzeria {
    /**
     * Queue for managing incoming orders.
     */
    private final OrderQueue orderQueue;

    /**
     * Warehouse for storing completed orders.
     */
    private final Warehouse warehouse;

    /**
     * List of bakers working in the pizzeria.
     */
    private final List<Baker> bakers;

    /**
     * List of couriers delivering orders.
     */
    private final List<Courier> couriers;

    /**
     * Customer instance generating orders.
     */
    private Customer customer;

    /**
     * Pizzeria configuration settings.
     */
    private final PizzeriaConfig cfg;

    /**
     * Total working time for the pizzeria simulation (milliseconds).
     */
    private int workingTime;

    /**
     * Threads for each baker.
     */
    private final List<Thread> bakerThreads;

    /**
     * Threads for each courier.
     */
    private final List<Thread> courierThreads;

    /**
     * Thread for the customer.
     */
    private Thread customerThread;

    /**
     * Constructs a new Pizzeria with the specified configuration.
     * Initializes all required data structures but does not configure or start the simulation.
     *
     * @param cfg the configuration settings for the pizzeria
     */
    public Pizzeria(PizzeriaConfig cfg) {
        this.cfg = cfg;
        this.orderQueue = new OrderQueue(new ThreadSafeQueue<>());
        this.warehouse = new Warehouse(new ThreadSafeQueue<>());

        this.bakers = new ArrayList<>();
        this.couriers = new ArrayList<>();
        this.bakerThreads = new ArrayList<>();
        this.courierThreads = new ArrayList<>();
    }

    /**
     * Configures the pizzeria based on the provided configuration.
     * Sets up warehouse capacity, creates baker and courier instances,
     * and initializes the customer.
     * This method must be called before startSimulate().
     *
     * @throws ConfigurationLoadingException if configuration loading fails
     */
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
            throw new ConfigurationLoadingException(
                "Couldn't load configuration: " + ex.getMessage());
        }
    }

    /**
     * Starts the pizzeria simulation.
     * Launches threads for all bakers, couriers, and the customer.
     * Runs for the configured working time, then initiates shutdown.
     * This method blocks until the simulation completes.
     */
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

    /**
     * Performs graceful shutdown of the pizzeria.
     * Interrupts all worker threads and waits for their termination.
     * Provides a 5-second timeout for each thread to complete.
     * Order of shutdown:
     * 1. Stop customer from generating new orders
     * 2. Interrupt bakers
     * 3. Interrupt couriers
     * 4. Wait for all threads to finish
     */
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