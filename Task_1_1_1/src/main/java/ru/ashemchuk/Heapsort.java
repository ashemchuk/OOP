package ru.ashemchuk;

/*
 * heap sort :)
 */

public class Heapsort {

    public static int[] sort(int [] array) {
        int len = array.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(array, len, i);
        }

        for (int i = len - 1; i >= 0; i--) {
            int tmp = array[0];
            array[0] = array[i];
            array[i] = tmp;

            heapify(array, i, 0);
        }
        return array;
    }

    private static void heapify(int[] array, int n, int i) {
        int max = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && array[l] > array[max]) {
            max = l;
        }
        if (r < n && array[r] > array[max]) {
            max = r;
        }

        if (max != i) {
            int tmp = array[i];
            array[i] = array[max];
            array[max] = tmp;

            heapify(array, n, max);
        }
    }
}
