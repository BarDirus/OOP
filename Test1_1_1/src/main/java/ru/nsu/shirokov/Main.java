import ru.nsu.shirokov.HeapSort;

import java.util.Arrays;
import java.util.Random;


    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        // Пример для сортировки фиксированного массива
        int[] arr = {12, 11, 13, 5, 6, 7};
        HeapSort heapSort = new HeapSort();
        System.out.println("Исходный массив:");
        printArray(arr);

        heapSort.sort(arr);
        System.out.println("Отсортированный массив:");
        printArray(arr);

        // Пример для сортировки случайно сгенерированного массива
        int n = 10; // размер массива
        int[] randomArr = new int[n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            randomArr[i] = rand.nextInt(100); // случайные числа от 0 до 99
        }

        System.out.println("\nСлучайный массив:");
        printArray(randomArr);

        heapSort.sort(randomArr);
        System.out.println("Отсортированный случайный массив:");
        printArray(randomArr);
    }

