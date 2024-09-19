package ru.nsu.shirokov;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {
    @Test
    public void testHeapSortWithUniqueElements() {
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
    public void testHeapSortWithAlreadySortedArray() {
        HeapSort heapSort = new HeapSort();
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
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
    @Test
    public void testHeapSortWithSingleElementArray() {
        HeapSort heapSort = new HeapSort();
        int[] input = {1};
        int[] expected = {1};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }
    @Test
    public void testHeapSortWithLargeArray() {
        HeapSort heapSort = new HeapSort();
        int[] input = new int[1000];
        for (int i = 0; i < input.length; i++) {
            input[i] = input.length - i;
        }
        heapSort.sort(input);
        for (int i = 1; i < input.length; i++) {
            assertTrue(input[i - 1] <= input[i]);
        }
    }
    @Test
    public void testPrintArray() {
        // Подготовка к перехвату вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        int[] input = {1, 2, 3, 4, 5};
        HeapSort heapSort = new HeapSort();
        heapSort.printArray(input);
        String expectedOutput = ("1 2 3 4 5 "+System.lineSeparator());
        String result = outputStream.toString();
        assertEquals(expectedOutput, result);
        System.setOut(originalOut);
    }
    @Test
    public void testMainMethod() {
        // Подготовка к перехвату вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        HeapSort.main(new String[]{});
        String expectedOutput = ("1 2 3 4 5 "+System.lineSeparator());
        assertEquals(expectedOutput, outputStream.toString());
        System.setOut(originalOut);
    }
}