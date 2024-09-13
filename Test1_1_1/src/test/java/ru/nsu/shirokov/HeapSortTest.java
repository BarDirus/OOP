package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class HeapSortTest {

    @Test
    public void testSort() {
        HeapSort heapSort = new HeapSort();
        int[] array = {12, 11, 13, 5, 6, 7};
        int[] sortedArray = {5, 6, 7, 11, 12, 13};

        heapSort.sort(array);

        assertArrayEquals(sortedArray, array);
    }

    @Test
    public void testSortEmptyArray() {
        HeapSort heapSort = new HeapSort();
        int[] array = {};
        int[] sortedArray = {};

        heapSort.sort(array);

        assertArrayEquals(sortedArray, array);
    }

    @Test
    public void testSortSingleElement() {
        HeapSort heapSort = new HeapSort();
        int[] array = {1};
        int[] sortedArray = {1};

        heapSort.sort(array);

        assertArrayEquals(sortedArray, array);
    }

    @Test
    public void testSortNegativeNumbers() {
        HeapSort heapSort = new HeapSort();
        int[] array = {0, -1, -3, 5, -6, 7};
        int[] sortedArray = {-6, -3, -1, 0, 5, 7};

        heapSort.sort(array);

        assertArrayEquals(sortedArray, array);
    }
}