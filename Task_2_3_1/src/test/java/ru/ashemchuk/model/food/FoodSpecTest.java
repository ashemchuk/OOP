package ru.ashemchuk.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FoodSpecTest {

    @Test
    void foodSpecRecord() {
        FoodSpec spec = new FoodSpec(FoodSpec.FoodType.APPLE, 3);
        assertEquals(FoodSpec.FoodType.APPLE, spec.type());
        assertEquals(3, spec.weight());
    }

    @Test
    void foodTypeValues() {
        FoodSpec.FoodType[] types = FoodSpec.FoodType.values();
        assertEquals(3, types.length);
        assertEquals(FoodSpec.FoodType.APPLE, types[0]);
        assertEquals(FoodSpec.FoodType.GRAPES, types[1]);
        assertEquals(FoodSpec.FoodType.POISON, types[2]);
    }
}