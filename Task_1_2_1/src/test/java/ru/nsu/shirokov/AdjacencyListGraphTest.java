package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AdjacencyListGraphTest {
    private AdjacencyListGraph graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Test
    public void testAddEdge() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(List.of(1), neighbors);
    }

    @Test
    public void testRemoveEdge() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    public void testGetNeighbors() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
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
        assertEquals(List.of(2), graph.getNeighbors(1));
    }

    @Test
    public void testToString() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        graph.addVertex(2);
        graph.addEdge(0, 2);
        String expected = "{0=[1, 2], 1=[], 2=[]}";
        assertEquals(expected, graph.toString());
    }
}
