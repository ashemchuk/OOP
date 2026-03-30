package ru.ashemchuk.config;

/**
 * Configuration record for a baker in the pizzeria system.
 * This record encapsulates all settings needed to initialize and configure a baker worker.
 *
 * <p>Baker configuration includes:
 * <ul>
 *   <li><b>id</b> - Unique identifier for the baker</li>
 *   <li><b>workingTime</b> - Time in milliseconds that the baker takes to cook a single order</li>
 * </ul>
 *
 * <p>This record is typically created from JSON/YAML configuration files
 * and used by the {@link ru.ashemchuk.Pizzeria} class during initialization.
 *
 * @param id          the unique identifier for this baker
 * @param workingTime the cooking time in milliseconds for a single order
 * @see ru.ashemchuk.worker.Baker
 * @see PizzeriaConfig
 */
public record BakerConfig(int id, int workingTime) {
}