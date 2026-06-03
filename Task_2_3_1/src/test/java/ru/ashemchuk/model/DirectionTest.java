package ru.ashemchuk.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void values() {
        Direction[] directions = Direction.values();
        assertEquals(4, directions.length);
        assertArrayEquals(
            new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT},
            directions);
    }
}