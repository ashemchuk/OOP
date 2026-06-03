package ru.ashemchuk.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PointTest {

    @Test
    void pointEquals() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(3, 5);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }

    @Test
    void pointHashCode() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 4);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}