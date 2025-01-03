package ru.nsu.shirokov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *Граф на основе матрицы смежности.
 */
public class AdjacencyMatrixGraph implements Graph {
    public int[][] matrix;
    private int size;

    public AdjacencyMatrixGraph(int initialSize) {
        matrix = new int[initialSize][initialSize];
        size = initialSize;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= size) {
            resizeMatrix(vertex + 1);
        }
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < size) {
            for (int i = 0; i < size; i++) {
                matrix[vertex][i] = 0;
                matrix[i][vertex] = 0;
            }
            size--;
        }
    }

    @Override
    public void addEdge(int from, int to) {
        matrix[from][to] = 1;
    }

    @Override
    public void removeEdge(int from, int to) {
        matrix[from][to] = 0;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (matrix[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int vertices = Integer.parseInt(firstLine[0]);

            for (int i = 0; i < vertices; i++) {
                if (i > size) {
                    addVertex(i);
                }
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] edge = line.split(" ");
                int from = Integer.parseInt(edge[0]);
                int to = Integer.parseInt(edge[1]);
                addEdge(from, to);
            }
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdjacencyMatrixGraph other = (AdjacencyMatrixGraph) obj;
        return Arrays.deepEquals(matrix, other.matrix);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(matrix);
    }

    public void resizeMatrix(int newSize) {
        int[][] newMatrix = new int[newSize][newSize];
        for (int i = 0; i < matrix.length; i++) {
            newMatrix[i] = Arrays.copyOf(matrix[i], newSize);
        }
        matrix = newMatrix;
        size = newSize;
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> sorted = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();

        for (int vertex = 0; vertex < size; vertex++) {
            if (!visited.contains(vertex)) {
                if (!dfs(vertex, visited, visiting, sorted)) {
                    throw new IllegalStateException("Граф содержит цикл, "
                            + "топологическая сортировка невозможна");
                }
            }
        }

        Collections.reverse(sorted);
        return sorted;
    }

    private boolean dfs(int vertex, Set<Integer> visited, Set<Integer> visiting,
                        List<Integer> sorted) {
        visiting.add(vertex);
        for (int neighbor = 0; neighbor < size; neighbor++) {
            if (matrix[vertex][neighbor] == 1) {
                if (visiting.contains(neighbor)) {
                    return false;
                }
                if (!visited.contains(neighbor)) {
                    if (!dfs(neighbor, visited, visiting, sorted)) {
                        return false;
                    }
                }
            }
        }
        visiting.remove(vertex);
        visited.add(vertex);
        sorted.add(vertex);
        return true;
    }
}
