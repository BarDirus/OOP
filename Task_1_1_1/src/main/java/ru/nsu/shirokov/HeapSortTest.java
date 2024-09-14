package ru.nsu.shirokov;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class HeapSortTest {

    @Test
    public void testHeapSort() {
        HeapSort heapSort = new HeapSort();
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortWithDuplicates() {
        HeapSort heapSort = new HeapSort();
        int[] input = {3, 1, 2, 3, 1};
        int[] expected = {1, 1, 2, 3, 3};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortWithEmptyArray() {
        HeapSort heapSort = new HeapSort();
        int[] input = {};
        int[] expected = {};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }
}