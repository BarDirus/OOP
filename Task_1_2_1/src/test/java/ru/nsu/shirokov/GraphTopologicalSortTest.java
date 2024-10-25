package ru.nsu.shirokov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

public class GraphTopologicalSortTest {
    @Test
    public void testTopologicalSortAdjacencyListGraph() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> sorted = graph.topologicalSort();
        assertEquals(List.of(0, 1, 2, 3), sorted, "Сортировка не соответствует ожидаемому порядку");
    }

    @Test
    public void testTopologicalSortAdjacencyMatrixGraph() {
        Graph graph = new AdjacencyMatrixGraph(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> sorted = graph.topologicalSort();
        assertEquals(List.of(0, 1, 2, 3), sorted, "Сортировка не соответствует ожидаемому порядку");
    }
    
    @Test
    public void testTopologicalSortWithCycleThrowsException() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);

        assertThrows(IllegalStateException.class, graph::topologicalSort, "Циклический граф должен выбросить исключение");
    }
}
