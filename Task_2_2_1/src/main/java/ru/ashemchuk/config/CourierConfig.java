package ru.ashemchuk.config;

/**
 * Configuration record for a courier in the pizzeria system.
 * This record encapsulates all settings needed to initialize and configure a courier worker.
 *
 * <p>Courier configuration includes:
 * <ul>
 *   <li>id - Unique identifier for the courier</li>
 *   <li>trunkCapacity - Maximum number of orders the courier can carry in one delivery trip</li>
 *   <li>deliveryTime - Time in milliseconds that the courier takes to complete a delivery</li>
 * </ul>
 *
 * <p>The trunk capacity determines how many orders a courier can pick up from the warehouse
 * in a single trip. The delivery time is applied to the entire batch of orders being delivered.
 *
 * @param id            the unique identifier for this courier
 * @param trunkCapacity the maximum number of orders this courier can deliver at once
 * @param deliveryTime  the delivery time in milliseconds for a single trip
 * @see ru.ashemchuk.worker.Courier
 * @see PizzeriaConfig
 */
public record CourierConfig(int id, int trunkCapacity, int deliveryTime) {
}