package ru.ashemchuk.queue.order;

/**
 * Represents an order in the pizzeria system.
 * Each order has a unique identifier and a state that tracks its progress
 * through the order lifecycle from creation to delivery.
 *
 * <p>The order lifecycle follows these states:
 * <ul>
 *   <li>{@link OrderState#WAITING} - Order created, waiting to be picked up by baker
 *   <li>{@link OrderState#COOKING} - Order is being prepared by a baker
 *   <li>{@link OrderState#COOKED} - Order is cooked and waiting in warehouse
 *   <li>{@link OrderState#DELIVERING} - Order is picked up by courier for delivery
 *   <li>{@link OrderState#DONE} - Order successfully delivered to customer
 * </ul>
 *
 * @see OrderState
 */
public class Order {

    /**
     * Unique identifier for the order.
     */
    private final int id;

    /**
     * Current state of the order in its lifecycle.
     */
    private OrderState state;

    /**
     * Constructs a new Order with the specified ID and initial state.
     *
     * @param id    the unique identifier for this order
     * @param state the initial state of the order (typically {@link OrderState#WAITING})
     */
    public Order(int id, OrderState state) {
        this.id = id;
        this.state = state;
    }

    /**
     * Returns the unique identifier of this order.
     *
     * @return the order ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the current state of this order.
     *
     * @return the current {@link OrderState}
     */
    public OrderState getState() {
        return state;
    }

    /**
     * Updates the state of this order.
     * This method is typically called by bakers and couriers as the order
     * progresses through its lifecycle.
     *
     * @param state the new state for this order
     */
    public void setState(OrderState state) {
        this.state = state;
    }
}