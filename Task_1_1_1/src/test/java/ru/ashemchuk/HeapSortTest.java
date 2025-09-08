package ru.ashemchuk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    /*
    * sorted array
    */

    @Test
    void sorted() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, HeapSort.sort(new int[] {1, 2, 3, 4, 5}));
    }

    /*
    * simple case - unsorted array
    */

    @Test
    void unsorted() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, HeapSort.sort(new int[] {2, 3, 1, 4, 5}));
    }

    /*
    * array with a single values
    */

    @Test
    void identical() {
        assertArrayEquals(new int[] {1, 1, 1, 1, 1}, HeapSort.sort(new int[] {1, 1, 1, 1, 1}));
    }

    /*
    * array with positive and negative values
    */

    @Test
    void negative() {
        assertArrayEquals(new int[] {-3, -2, -1, 1, 2, 3}, HeapSort.sort(new int[] {3, 1, -2, -1, -3, 2}));
    }

    /*
    * empty array
    */

    @Test
    void empty() {
        assertArrayEquals(new int[] {}, HeapSort.sort(new int[] {}));
    }
}