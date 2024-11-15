package ru.nsu.shirokov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubstringSearch {

    public static List<Long> searchSubstringInFile(String filename, String substring) throws IOException {
        List<Long> indices = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            char[] buffer = new char[4096];
            int substringLength = substring.length();
            char[] prefix = substring.toCharArray();
            int[] prefixTable = computePrefixFunction(prefix);

            StringBuilder textBlock = new StringBuilder();
            long fileOffset = 0;
            int bytesRead;

            while ((bytesRead = reader.read(buffer)) != -1) {
                textBlock.append(buffer, 0, bytesRead);
                int position = kmpSearch(textBlock, prefix, prefixTable,0);
                while (position != -1) {
                    indices.add(fileOffset + position);
                    position = kmpSearch(textBlock, prefix, prefixTable, position + 1);
                }
                fileOffset += bytesRead;
                textBlock.delete(0, textBlock.length() - substringLength + 1);
            }
        }

        return indices;
    }

    private static int[] computePrefixFunction(char[] pattern) {
        int[] prefixTable = new int[pattern.length];
        int j = 0;

        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[i] != pattern[j]) {
                j = prefixTable[j - 1];
            }
            if (pattern[i] == pattern[j]) {
                j++;
            }
            prefixTable[i] = j;
        }

        return prefixTable;
    }

    private static int kmpSearch(StringBuilder text, char[] pattern, int[] prefixTable, int startIndex) {
        int j = 0;
        for (int i = startIndex; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern[j]) {
                j = prefixTable[j - 1];
            }
            if (text.charAt(i) == pattern[j]) {
                j++;
            }
            if (j == pattern.length) {
                return i - pattern.length + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        String filename = "largeTestFile.txt";
        String substring = "test";
        List<Long> indices = SubstringSearch.searchSubstringInFile(filename, substring);
        System.out.println("Indices of substring '" + substring + "': " + indices);
    }
}
