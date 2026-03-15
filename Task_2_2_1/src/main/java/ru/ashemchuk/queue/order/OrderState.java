package ru.ashemchuk.queue.order;

/**
 * Defines the possible states of an order throughout its lifecycle in the pizzeria system.
 * The states represent the sequential progression of an order from creation to delivery.
 *
 * @see Order
 */
public enum OrderState {
    WAITING,
    COOKING,
    COOKED,
    DELIVERING,
    DONE
}