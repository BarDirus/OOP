package ru.nsu.shirokov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test.
 * */
public class SubstringSearchTest {

    private static final String TEST_FILE_NAME = "testFile.txt";
    /**
     * Запуск перед тестами.
     * */
    @BeforeEach
    public void setup() throws IOException {
        File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSingleOccurrence() throws IOException {
        String content = "This is a test string containing the word 'test'.";
        writeToFile(TEST_FILE_NAME, content);

        List<Long> indices = SubstringSearch.searchSubstringInFile(TEST_FILE_NAME, "test");
        assertEquals(List.of(10L, 43L), indices);
    }

    @Test
    public void testMultipleOccurrences() throws IOException {
        String content = "test here, test there, and another test.";
        writeToFile(TEST_FILE_NAME, content);

        List<Long> indices = SubstringSearch.searchSubstringInFile(TEST_FILE_NAME, "test");
        assertEquals(List.of(0L, 11L, 35L), indices);
    }

    @Test
    public void testNoOccurrences() throws IOException {
        String content = "This string does not contain the search word.";
        writeToFile(TEST_FILE_NAME, content);

        List<Long> indices = SubstringSearch.searchSubstringInFile(TEST_FILE_NAME, "missing");
        assertTrue(indices.isEmpty(), "No occurrences of 'missing' should be found.");
    }

    @Test
    public void testEmptyFile() throws IOException {
        writeToFile(TEST_FILE_NAME, "");

        List<Long> indices = SubstringSearch.searchSubstringInFile(TEST_FILE_NAME, "anything");
        assertTrue(indices.isEmpty());
    }

    @Test
    public void testSubstringAtFileEnd() throws IOException {
        String content = "End of file contains the word test";
        writeToFile(TEST_FILE_NAME, content);

        List<Long> indices = SubstringSearch.searchSubstringInFile(TEST_FILE_NAME, "test");
        assertEquals(List.of(30L), indices);
    }

    @Test
    public void testLargeFile() throws IOException {
        // Generate a large file with repeated patterns
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            for (int i = 0; i < 10000; i++) {
                writer.write("Lorem ipsum dolor sit amet test ");
            }
        }

        List<Long> indices = SubstringSearch.searchSubstringInFile(TEST_FILE_NAME, "test");

        // Check that there are exactly 10,000 occurrences of "test" in the file
        assertEquals(10000, indices.size());
    }

    private void writeToFile(String fileName, String content) throws IOException {
        Files.write(new File(fileName).toPath(), content.getBytes());
    }
}
