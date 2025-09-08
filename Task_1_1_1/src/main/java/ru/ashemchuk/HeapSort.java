package ru.ashemchuk;

/*
 * class for a heap sort of array
 */

public class HeapSort {

    /*
    * 1. make a heap
    * 2. swap last elem with first
    * 3. sift new first elem  down
    * @param array - array to sort
    */

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

    /*
    * fixing a heap (sifting)
    * @param array - array
    * @param n - array's length
    * @param i - index of elem we sift
    * */

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
