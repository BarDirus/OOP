package ru.nsu.shirokov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class SubstringSearch {
    public static List<Long> searchSubstringInFile(String filename, String substring) throws IOException {
        List<Long> indices = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            int bufferSize = 4096;
            char[] buffer = new char[bufferSize];
            int substringLength = substring.codePointCount(0, substring.length());
            int[] pattern = substring.codePoints().toArray();
            int[] prefixTable = computePrefixFunction(pattern);
            StringBuilder textBlock = new StringBuilder();
            long bufferOffset = 0;
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                textBlock.append(buffer, 0, bytesRead);
                int[] textCodePoints = textBlock.codePoints().toArray();
                int position = kmpSearch(textCodePoints, pattern, prefixTable,0);
                while (position != -1) {
                    indices.add(bufferOffset + position);
                    position = kmpSearch(textCodePoints, pattern, prefixTable, position + 1);
                }

                bufferOffset += textCodePoints.length;
                if (textCodePoints.length > substringLength - 1) {
                    int[] suffix = new int[substringLength - 1];
                    System.arraycopy(
                            textCodePoints,
                            textCodePoints.length - (substringLength - 1),
                            suffix,
                            0,
                            substringLength - 1
                    );
                    textBlock = new StringBuilder(new String(suffix, 0, suffix.length));
                } else {
                    textBlock = new StringBuilder(new String(textCodePoints, 0, textCodePoints.length));
                }
            }
        }
        return indices;
    }
    private static int[] computePrefixFunction(int[] pattern) {
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

    private static int kmpSearch(int[] text, int[] pattern, int[] prefixTable, int startIndex) {
        int j = 0;

        for (int i = startIndex; i < text.length; i++) {
            while (j > 0 && text[i] != pattern[j]) {
                j = prefixTable[j - 1];
            }
            if (text[i] == pattern[j]) {
                j++;
            }
            if (j == pattern.length) {
                return i - pattern.length + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        String filename = "largeTestFile3.txt";
        String substring = "𒀱";
        List<Long> indices = SubstringSearch.searchSubstringInFile(filename, substring);
        System.out.println("Indices of substring '" + substring + "': " + indices);
    }
}
