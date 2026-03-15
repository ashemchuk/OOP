package ru.ashemchuk.worker;

import java.util.List;
import ru.ashemchuk.Warehouse;
import ru.ashemchuk.config.CourierConfig;
import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.queue.order.OrderState;

/**
 * Represents a courier worker in the pizzeria system.
 * Couriers are responsible for picking up cooked orders from the warehouse
 * and delivering them to customers.
 *
 * <p>The courier's workflow:
 * <ol>
 *   <li>Take up to trunk capacity orders from warehouse (blocks if no orders available)</li>
 *   <li>Change all picked orders state to {@link OrderState#DELIVERING}</li>
 *   <li>Simulate delivery time by sleeping for configured duration</li>
 *   <li>Change all delivered orders state to {@link OrderState#DONE}</li>
 * </ol>
 *
 * <p>Couriers can deliver multiple orders in one trip, up to their trunk capacity.
 *
 * @see Worker
 * @see CourierConfig
 * @see Warehouse
 */
public class Courier extends Worker {

    /**
     * Configuration settings for this courier (ID, trunk capacity, delivery time).
     */
    private final CourierConfig cfg;

    /**
     * The warehouse from which orders are picked up.
     */
    private final Warehouse warehouse;

    /**
     * Current list of orders being delivered (the courier's "trunk").
     */
    private List<Order> trunk;

    /**
     * Constructs a new Courier with the specified configuration and warehouse.
     *
     * @param cfg       the courier configuration containing ID, trunk capacity, and delivery time
     * @param warehouse the warehouse to pick up orders from
     */
    public Courier(CourierConfig cfg, Warehouse warehouse) {
        this.cfg = cfg;
        this.warehouse = warehouse;
    }

    /**
     * Returns the unique identifier of this courier.
     *
     * @return the courier ID
     */
    public int getId() {
        return cfg.id();
    }

    /**
     * Performs a single delivery cycle for the courier.
     * Takes orders from warehouse, changes their state to DELIVERING,
     * simulates delivery time, then marks them as DONE.
     *
     * <p>If no orders are taken from warehouse (empty list), the method returns early
     * without performing delivery simulation.
     *
     * @throws InterruptedException if the thread is interrupted during
     * delivery or warehouse operations
     */
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