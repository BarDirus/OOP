package ru.nsu.shirokov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
/**
 *Граф на основе списка смежности.
 */
public class AdjacencyListGraph implements Graph {
    private final Map<Integer, List<Integer>> adjList = new HashMap<>();

    @Override
    public void addVertex(int vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        adjList.remove(vertex);
        adjList.values().forEach(list -> list.remove(Integer.valueOf(vertex)));
    }

    @Override
    public void addEdge(int from, int to) {
        addVertex(from);
        addVertex(to);
        adjList.get(from).add(to);
    }

    @Override
    public void removeEdge(int from, int to) {
        if (adjList.containsKey(from)) {
            adjList.get(from).remove(Integer.valueOf(to));
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjList.getOrDefault(vertex, Collections.emptyList());
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int vertices = Integer.parseInt(firstLine[0]);

            for (int i = 0; i < vertices; i++) {
                addVertex(i);
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
        AdjacencyListGraph other = (AdjacencyListGraph) obj;
        return adjList.equals(other.adjList);
    }

    @Override
    public String toString() {
        return adjList.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> sorted = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();


        for (Integer vertex : adjList.keySet()) {
            if (!visited.contains(vertex)) {
                if (!dfs(vertex, visited, visiting, sorted)) {
                    throw new IllegalStateException("Граф содержит цикл, топологическая сортировка невозможна");
                }
            }
        }

        Collections.reverse(sorted); // Переворачиваем порядок для корректной сортировки
        return sorted;
    }

    private boolean dfs(Integer vertex, Set<Integer> visited, Set<Integer> visiting, List<Integer> sorted) {
        visiting.add(vertex);
        for (Integer neighbor : adjList.getOrDefault(vertex, Collections.emptyList())) {
            if (visiting.contains(neighbor)) return false;
            if (!visited.contains(neighbor)) {
                if (!dfs(neighbor, visited, visiting, sorted)) return false;
            }
        }
        visiting.remove(vertex);
        visited.add(vertex);
        sorted.add(vertex);
        return true;
    }

}
