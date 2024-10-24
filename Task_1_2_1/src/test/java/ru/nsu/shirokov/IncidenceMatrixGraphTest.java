package ru.nsu.shirokov;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph graph;

    @BeforeEach
    public void setUp() {
        graph = new IncidenceMatrixGraph(5, 5);
    }

    @Test
    public void testAddEdge() {
        graph.addEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(List.of(1), neighbors);
    }

    @Test
    public void testRemoveEdge() {
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    public void testGetNeighbors() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(List.of(1, 2), neighbors);
    }

    @Test
    public void testReadFromFile() throws IOException {
        Path file = Files.createTempFile("graph", ".txt");
        try (FileWriter writer = new FileWriter(file.toFile())) {
            writer.write("3 2\n0 1\n1 2\n");
        }

        graph.readFromFile(file.toString());
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(0,2), graph.getNeighbors(1));
    }

    @Test
    public void testToString() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        String expected =
                "0 1 0 0 0 \n" +
                        "1 0 1 0 0 \n" +
                        "0 1 0 0 0 \n" +
                        "0 0 0 0 0 \n" +
                        "0 0 0 0 0 \n";
        assertEquals(expected, graph.toString());
    }
}
