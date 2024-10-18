package ru.nsu.shirokov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdjacencyMatrixGraph implements Graph {
    private int[][] matrix;
    private int numVertices;

    public AdjacencyMatrixGraph(int numVertices) {
        this.numVertices = numVertices;
        this.matrix = new int[numVertices][numVertices];
    }

    @Override
    public void addVertex(int vertex) {
    }

    @Override
    public void removeVertex(int vertex) {

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
        for (int i = 0; i < numVertices; i++) {
            if (matrix[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename) throws IOException {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                sb.append(matrix[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyMatrixGraph other = (AdjacencyMatrixGraph) obj;
        return Arrays.deepEquals(this.matrix, other.matrix);
    }
}
