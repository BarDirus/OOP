package ru.nsu.shirokov;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для проверки реализации пирамидальной сортировки.
 */
public class HeapSortTest {

    /**
     * Тест сортировки массива с уникальными элементами.
     */
    @Test
    public void testHeapSortWithUniqueElements() {
        HeapSort heapSort = new HeapSort();
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    /**
     * Тест сортировки массива с повторяющимися элементами.
     */
    @Test
    public void testHeapSortWithDuplicates() {
        HeapSort heapSort = new HeapSort();
        int[] input = {3, 1, 2, 3, 1};
        int[] expected = {1, 1, 2, 3, 3};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    /**
     * Тест сортировки уже отсортированного массива.
     */
    @Test
    public void testHeapSortWithAlreadySortedArray() {
        HeapSort heapSort = new HeapSort();
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    /**
     * Тест сортировки пустого массива.
     */
    @Test
    public void testHeapSortWithEmptyArray() {
        HeapSort heapSort = new HeapSort();
        int[] input = {};
        int[] expected = {};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    /**
     * Тест сортировки массива с одним элементом.
     */
    @Test
    public void testHeapSortWithSingleElementArray() {
        HeapSort heapSort = new HeapSort();
        int[] input = {1};
        int[] expected = {1};
        heapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    /**
     * Тест сортировки большого массива для проверки производительности и корректности.
     */
    @Test
    public void testHeapSortWithLargeArray() {
        HeapSort heapSort = new HeapSort();
        int[] input = new int[1000];
        for (int i = 0; i < input.length; i++) {
            input[i] = input.length - i;
        }

        heapSort.sort(input);

        // Проверка на корректность сортировки
        for (int i = 1; i < input.length; i++) {
            assertTrue(input[i - 1] <= input[i]);
        }
    }

    /**
     * Тест для проверки корректности вывода.
     */
    @Test
    public void testPrintArray() {
        // Подготовка к перехвату вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        int[] input = {1, 2, 3, 4, 5};
        HeapSort heapSort = new HeapSort();
        heapSort.printArray(input);
        String expectedOutput = ("1 2 3 4 5 " + System.lineSeparator());
        String result = outputStream.toString();
        PrintStream originalOut = System.out;
        assertEquals(expectedOutput, result);
        System.setOut(originalOut);
    }

    /**
     * Тест для проверки корректности main-a.
     */
    @Test
    public void testMainMethod() {
        // Подготовка к перехвату вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        HeapSort.main(new String[]{});
        String expectedOutput = ("1 2 3 4 5 " + System.lineSeparator());
        PrintStream originalOut = System.out;
        assertEquals(expectedOutput, outputStream.toString());
        System.setOut(originalOut);
    }
}