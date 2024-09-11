package ru.nsu.shadrina;

/**
 * Sample class to simulate 1.1 task functionality
 */

public class HeapSort {

        public void sort(int[] arr) {
            int n = arr.length;

            // Построение кучи
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(arr, n, i);
            }

            // Извлечение элементов из кучи
            for (int i = n - 1; i > 0; i--) {
                // Перемещаем текущий корень в конец
                int temp = arr[0];
                arr[0] = arr[i];
                arr[i] = temp;

                // Вызываем heapify на уменьшенной куче
                heapify(arr, i, 0);
            }
        }

        void heapify(int[] arr, int n, int i) {
            int largest = i; // Инициализируем наибольший элемент как корень
            int left = 2 * i + 1; // Левый = 2*i + 1
            int right = 2 * i + 2; // Правый = 2*i + 2

            // Если левый дочерний элемент больше корня
            if (left < n && arr[left] > arr[largest])
                largest = left;

            // Если правый дочерний элемент больше, чем самый большой элемент на данный момент
            if (right < n && arr[right] > arr[largest])
                largest = right;

            // Если самый большой элемент не корень
            if (largest != i) {
                int swap = arr[i];
                arr[i] = arr[largest];
                arr[largest] = swap;

                // Рекурсивно heapify поддерево
                heapify(arr, n, largest);
            }
        }

        public static void printArray(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n; ++i)
                System.out.print(arr[i] + " ");
            System.out.println();
        }

        public static void main(String[] args) {
            int[] arr = {12, 11, 13, 5, 6, 7};
            HeapSort heapSort = new HeapSort();
            System.out.println("Исходный массив:");
            printArray(arr);

            heapSort.sort(arr);

            System.out.println("Отсортированный массив:");
            printArray(arr);
        }
    }
