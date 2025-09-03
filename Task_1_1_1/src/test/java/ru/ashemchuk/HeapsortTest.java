package ru.ashemchuk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {

    @Test
    void sort() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, Heapsort.sort(new int [] {3, 2, 1, 5, 4}));
        assertArrayEquals(new int[] {1, 1, 1, 1, 1}, Heapsort.sort(new int[] {1, 1, 1, 1, 1}));
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, Heapsort.sort(new int[] {5, 4, 3, 2, 1}));
    }
}