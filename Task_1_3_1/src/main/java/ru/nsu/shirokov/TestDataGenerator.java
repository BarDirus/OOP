package ru.nsu.shirokov;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TestDataGenerator {

    public static void generateLargeFile(String filename, int sizeInMB) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Random random = new Random();
            for (int i = 0; i < sizeInMB *  1024; i++) {
                writer.write((char) ('a' + random.nextInt(26)));  // Генерация случайных букв
            }
        }
    }

    public static void main(String[] args) throws IOException {
        generateLargeFile("largeTestFile.txt", 1024);  // Генерация файла размером 1 ГБ
    }
}
