package ru.nsu.shirokov;

public class HeapSort {

    public void sort(int[] arr) {
        int n = arr.length;

        // Построение кучи (перегруппировка массива)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Один за другим извлекаем элементы из кучи
        for (int i = n - 1; i > 0; i--) {
            // Перемещаем текущий корень в конец
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Вызываем heapify на уменьшенной куче
            heapify(arr, i, 0);
        }
    }

    // Вспомогательная функция для преобразования поддерева в двоичную кучу
    void heapify(int[] arr, int n, int i) {
        int largest = i;  // Инициализируем наибольший элемент как корень
        int left = 2 * i + 1;  // Левый = 2*i + 1
        int right = 2 * i + 2;  // Правый = 2*i + 2

        // Если левый дочерний элемент больше корня
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // Если правый дочерний элемент больше, чем наибольший элемент на данный момент
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // Если наибольший элемент не корень
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Рекурсивно преобразуем затронутое поддерево
            heapify(arr, n, largest);
        }
    }

    // Утилита для вывода массива
    public void printArray(int[] arr) {
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(arr);
        System.out.println("Отсортированный массив:");
        heapSort.printArray(arr);
    }
}
