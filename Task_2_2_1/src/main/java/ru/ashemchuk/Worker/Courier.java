package ru.ashemchuk.Worker;

import java.util.List;
import ru.ashemchuk.Config.CourierConfig;
import ru.ashemchuk.OrderQueue.Order.Order;
import ru.ashemchuk.OrderQueue.Order.OrderState;
import ru.ashemchuk.Warehouse;

public class Courier extends Worker {
    private final CourierConfig cfg;
    private final Warehouse warehouse;
    private List<Order> trunk;

    public Courier(CourierConfig cfg, Warehouse warehouse) {
        this.cfg = cfg;
        this.warehouse = warehouse;
    }

    public int getId() {
        return cfg.id();
    }

    @Override
    public void work() throws InterruptedException {
        trunk = warehouse.takeOrders(cfg.trunkCapacity());

        if (trunk.isEmpty()) {
            return;
        }

        for (Order o : trunk) {
            o.setState(OrderState.DELIVERING);
            log(o);
        }

        Thread.sleep(cfg.deliveryTime());

        for (Order o : trunk) {
            o.setState(OrderState.DONE);
            log(o);
        }
    }
}