package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyMatrixGraph(5);
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
        assertEquals(List.of(2), graph.getNeighbors(1));
    }

    @Test
    public void testToString() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        String expected =
                "[[0, 1, 0, 0, 0], "
                        +"[0, 0, 1, 0, 0], "
                        +"[0, 0, 0, 0, 0], "
                        +"[0, 0, 0, 0, 0], "
                        +"[0, 0, 0, 0, 0]]";
        assertEquals(expected, graph.toString());
    }

    @Test
    public void testAddVertex() {
        graph.addVertex(5);
        assertEquals(List.of(), graph.getNeighbors(5), "Добавленная вершина не должна иметь соседей");
    }

    @Test
    public void testRemoveVertex() {
        graph.addEdge(0, 1);
        graph.addVertex(2);
        graph.addEdge(0, 2);
        graph.removeVertex(2);

        assertTrue(graph.getNeighbors(0).contains(1), "Вершина 1 должна оставаться соседней");
        assertFalse(graph.getNeighbors(0).contains(2), "Вершина 2 должна быть удалена");
    }

    @Test
    public void testResizeMatrix() {
        graph.addEdge(0, 1);
        graph.resizeMatrix(6);

        assertTrue(graph.getNeighbors(0).contains(1), "Старые вершины и рёбра должны остаться после изменения размера");
        assertTrue(graph.getNeighbors(1).isEmpty(), "Вершина 1 не должна иметь соседей");
    }

    @Test
    public void testEquals() {
        AdjacencyMatrixGraph otherGraph = new AdjacencyMatrixGraph(5);
        graph.addEdge(0, 1);
        otherGraph.addEdge(0, 1);

        assertEquals(graph, otherGraph, "Графы с одинаковыми рёбрами должны быть равны");
    }

    @Test
    public void testNotEquals() {
        AdjacencyMatrixGraph otherGraph = new AdjacencyMatrixGraph(5);
        graph.addEdge(0, 1);
        otherGraph.addEdge(1, 2);

        assertNotEquals(graph, otherGraph, "Графы с разными рёбрами не должны быть равны");
    }

}
