package ru.ashemchuk.config;

import java.util.List;

/**
 * Main configuration record for the entire pizzeria system.
 * This record aggregates all configuration parameters needed to set up and run
 * a pizzeria simulation instance.
 *
 * <p>The configuration includes:
 * <ul>
 *   <li>workingTime - Total simulation duration in milliseconds</li>
 *   <li>warehouseCapacity - Maximum number of cooked orders that can be stored</li>
 *   <li>bakers - List of individual baker configurations</li>
 *   <li>couriers - List of individual courier configurations</li>
 * </ul>
 *
 * <p>This configuration is typically loaded from an external source (JSON, YAML, properties file)
 * and used by {@link ru.ashemchuk.Pizzeria} to initialize all components of the system.
 *
 * <p>Example configuration:
 * <pre>
 * {
 *   "workingTime": 60000,
 *   "warehouseCapacity": 10,
 *   "bakers": [
 *     {"id": 1, "workingTime": 3000},
 *     {"id": 2, "workingTime": 4000}
 *   ],
 *   "couriers": [
 *     {"id": 1, "trunkCapacity": 3, "deliveryTime": 5000},
 *     {"id": 2, "trunkCapacity": 5, "deliveryTime": 7000}
 *   ]
 * }
 * </pre>
 *
 * @param workingTime       total simulation time in milliseconds
 * @param warehouseCapacity maximum capacity of the finished orders warehouse
 * @param bakers            list of configurations for all bakers
 * @param couriers          list of configurations for all couriers
 * @see BakerConfig
 * @see CourierConfig
 * @see ru.ashemchuk.Pizzeria
 * @see ru.ashemchuk.ConfigurationLoadingException
 */
public record PizzeriaConfig(int workingTime, int warehouseCapacity, List<BakerConfig> bakers,
                             List<CourierConfig> couriers) {
}