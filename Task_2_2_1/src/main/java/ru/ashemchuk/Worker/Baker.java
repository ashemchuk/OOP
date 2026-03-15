package ru.ashemchuk.Worker;

import ru.ashemchuk.Config.BakerConfig;
import ru.ashemchuk.OrderQueue.Order.Order;
import ru.ashemchuk.OrderQueue.Order.OrderState;
import ru.ashemchuk.OrderQueue.OrderQueue;
import ru.ashemchuk.Warehouse;

public class Baker extends Worker {
    private final BakerConfig cfg;
    private final OrderQueue queue;
    private final Warehouse warehouse;

    public Baker(BakerConfig cfg, OrderQueue queue, Warehouse warehouse) {
        this.cfg = cfg;
        this.queue = queue;
        this.warehouse = warehouse;
    }

    public int getId() {
        return cfg.id();
    }

    @Override
    public void work() throws InterruptedException {
        Order o = queue.takeOrder();

        o.setState(OrderState.COOKING);
        log(o);

        Thread.sleep(cfg.workingTime());

        o.setState(OrderState.COOKED);
        log(o);

        warehouse.addOrder(o);
    }
}