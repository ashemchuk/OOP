package ru.ashemchuk;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
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
    private PizzeriaConfig cfg;
    private int workingTime;

    private final List<Thread> bakerThreads;
    private final List<Thread> courierThreads;
    private Thread customerThread;

    public Pizzeria() {
        this.orderQueue = new OrderQueue(new ThreadSafeQueue<>());
        this.warehouse = new Warehouse(new ThreadSafeQueue<>());

        this.bakers = new ArrayList<>();
        this.couriers = new ArrayList<>();
        this.bakerThreads = new ArrayList<>();
        this.courierThreads = new ArrayList<>();
    }
    public void configure(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File json = new File(path);
            this.cfg = mapper.readValue(json, PizzeriaConfig.class);

            // ВАЖНО: установить capacity ДО создания работников
            this.warehouse.setCapacity(cfg.warehouseCapacity());

            // Проверка, что capacity установился
            System.out.println("=== Вместимость склада установлена: " + cfg.warehouseCapacity() + " ===");

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
        // Запускаем потоки пекарей
        for (Baker baker : bakers) {
            Thread thread = new Thread(baker, "Baker-" + baker.getId());
            bakerThreads.add(thread);
            thread.start();
        }

        // Запускаем потоки курьеров
        for (Courier courier : couriers) {
            Thread thread = new Thread(courier, "Courier-" + courier.getId());
            courierThreads.add(thread);
            thread.start();
        }

        // Запускаем поток клиента (генерация заказов)
        customerThread = new Thread(customer, "Customer");
        customerThread.start();

        // Работаем в течение заданного времени
        try {
            Thread.sleep(workingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Завершаем работу пиццерии
        shutdownPizzeria();
    }

    private void shutdownPizzeria() {
        System.out.println("\n=== Пиццерия закрывается. Завершаем оставшиеся заказы... ===");

        // 1. Останавливаем прием новых заказов (прерываем поток customer)
        customerThread.interrupt();

        // 2. Прерываем всех пекарей и курьеров
        for (Thread thread : bakerThreads) {
            thread.interrupt();
        }
        for (Thread thread : courierThreads) {
            thread.interrupt();
        }

        // 3. Ждем завершения всех потоков
        try {
            // Ждем завершения customer
            customerThread.join(5000);

            // Ждем завершения пекарей
            for (Thread thread : bakerThreads) {
                thread.join(5000);
            }

            // Ждем завершения курьеров
            for (Thread thread : courierThreads) {
                thread.join(5000);
            }

        } catch (InterruptedException e) {
            System.err.println("Прерывание при ожидании завершения потоков");
            Thread.currentThread().interrupt();
        }

        // Проверяем остатки
    }
}