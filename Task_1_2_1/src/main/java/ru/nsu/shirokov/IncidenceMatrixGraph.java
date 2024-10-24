package ru.nsu.shirokov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IncidenceMatrixGraph implements Graph {
    private boolean[][] matrix;
    private int edgeCount;

    public IncidenceMatrixGraph(int vertices, int edges) {
        this.edgeCount = edges;
        this.matrix = new boolean[vertices][edges];
    }

    @Override
    public void addVertex(int vertex) {
        // Фиксированный размер
    }

    @Override
    public void addEdge(int from, int to) {
        matrix[from][to] = true;
        matrix[to][from] = true;
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
}
