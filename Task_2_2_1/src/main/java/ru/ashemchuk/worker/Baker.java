package ru.ashemchuk.worker;

import ru.ashemchuk.Warehouse;
import ru.ashemchuk.config.BakerConfig;
import ru.ashemchuk.queue.OrderQueue;
import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.queue.order.OrderState;

/**
 * Represents a baker worker in the pizzeria system.
 * Bakers are responsible for taking orders from the queue, cooking them,
 * and placing finished orders in the warehouse.
 *
 * <p>The baker's workflow:
 * <ol>
 *   <li>Take an order from the order queue (blocks if none available)</li>
 *   <li>Change order state to {@link OrderState#COOKING}</li>
 *   <li>Simulate cooking time by sleeping for configured duration</li>
 *   <li>Change order state to {@link OrderState#COOKED}</li>
 *   <li>Place the cooked order in the warehouse (blocks if warehouse is full)</li>
 * </ol>
 *
 * @see Worker
 * @see BakerConfig
 * @see OrderQueue
 * @see Warehouse
 */
public class Baker extends Worker {

    /**
     * Configuration settings for this baker (ID and cooking time).
     */
    private final BakerConfig cfg;

    /**
     * The queue from which orders are taken.
     */
    private final OrderQueue queue;

    /**
     * The warehouse where cooked orders are placed.
     */
    private final Warehouse warehouse;

    /**
     * Constructs a new Baker with the specified configuration and dependencies.
     *
     * @param cfg       the baker configuration containing ID and cooking time
     * @param queue     the order queue to take orders from
     * @param warehouse the warehouse to place cooked orders in
     */
    public Baker(BakerConfig cfg, OrderQueue queue, Warehouse warehouse) {
        this.cfg = cfg;
        this.queue = queue;
        this.warehouse = warehouse;
    }

    /**
     * Returns the unique identifier of this baker.
     *
     * @return the baker ID
     */
    public int getId() {
        return cfg.id();
    }

    /**
     * Performs a single work cycle for the baker.
     * Takes an order, cooks it, and places it in the warehouse.
     * This method is called repeatedly by the {@link #run()} method.
     *
     * @throws InterruptedException if the thread is interrupted during
     * cooking or warehouse operations
     */
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