package ru.nsu.shirokov;
/**
 * Класс, реализующий пирамидальную сортировку.
 */
public class HeapSort {

    /**
     * Сортирует переданный массив чисел с использованием алгоритма пирамидальной сортировки.
     */
    public void sort(int[] arr) {
        int n = arr.length;


        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }


        for (int i = n - 1; i > 0; i--) {

            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }
/**
 * Преобразует поддерево с корневым узлом i в кучу, предполагая, что поддеревья этого узла уже являются кучами.
 */
 void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }

    /**
     * Вывод массива.
     */
    public void printArray(int[] arr) {
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
    /**
     * Проверка.
     */
    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(arr);
        heapSort.printArray(arr);
    }
}
