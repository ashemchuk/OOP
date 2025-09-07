package ru.ashemchuk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {

    @Test
    void sorted() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, Heapsort.sort(new int[] {1, 2, 3, 4, 5}));
    }
    @Test
    void unsorted() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, Heapsort.sort(new int[] {2, 3, 1, 4, 5}));
    }
    @Test
    void identical() {
        assertArrayEquals(new int[] {1, 1, 1, 1, 1}, Heapsort.sort(new int[] {1, 1, 1, 1, 1}));
    }
    @Test
    void negative() {
        assertArrayEquals(new int[] {-3, -2, -1, 1, 2, 3}, Heapsort.sort(new int[] {3, 1, -2, -1, -3, 2}));
    }
}