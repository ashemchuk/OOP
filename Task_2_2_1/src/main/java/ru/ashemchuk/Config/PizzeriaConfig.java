package ru.ashemchuk.Config;

import java.util.List;

public record PizzeriaConfig(int workingTime, int warehouseCapacity, List<BakerConfig> bakers, List<CourierConfig> couriers) {
}
