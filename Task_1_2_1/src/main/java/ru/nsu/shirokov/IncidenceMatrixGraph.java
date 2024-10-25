package ru.nsu.shirokov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
/**
 *Граф на основе матрицы инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {
    private final boolean[][] matrix;
    private final int vertexCount;
    private final int edgeCount;

    public IncidenceMatrixGraph(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.matrix = new boolean[vertexCount][edgeCount];
    }

    @Override
    public void addVertex(int vertex) {
        // Фиксированный размер
    }

    @Override
    public void addEdge(int from, int to) {
        matrix[from][to]=true;
        matrix[to][from]=true;
    }



    @Override
    public void removeVertex(int vertex) {
        Arrays.fill(matrix[vertex], false);
    }

    @Override
    public void removeEdge(int from, int to) {
        matrix[from][to] = false;
        matrix[to][from] = false;
    }


    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            if (matrix[vertex][i]) {
                neighbors.add(i);
            }
        }

        return neighbors;
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (boolean[] row : matrix) {
            for (boolean val : row) {
                sb.append(val ? 1 : 0).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> sorted = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!visited.contains(vertex)) {
                if (!dfs(vertex, visited, visiting, sorted)) {
                    throw new IllegalStateException("Граф содержит цикл, топологическая сортировка невозможна");
                }
            }
        }

        Collections.reverse(sorted);
        return sorted;
    }

    private boolean dfs(int vertex, Set<Integer> visited, Set<Integer> visiting, List<Integer> sorted) {
        visiting.add(vertex);
        for (int edge = 0; edge < edgeCount; edge++) {
            if (matrix[vertex][edge] == true) {
                for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                    if (neighbor != vertex && matrix[neighbor][edge] == false) {
                        if (visiting.contains(neighbor)) return false;
                        if (!visited.contains(neighbor)) {
                            if (!dfs(neighbor, visited, visiting, sorted)) return false;
                        }
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
