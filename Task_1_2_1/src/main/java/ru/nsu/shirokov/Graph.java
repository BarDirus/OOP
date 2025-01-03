package ru.nsu.shirokov;

import java.io.IOException;
import java.util.List;

/**
 *Класс Графов
 */
public interface Graph {
    void addVertex(int vertex);

    void removeVertex(int vertex);

    void addEdge(int from, int to);

    void removeEdge(int from, int to);

    List<Integer> getNeighbors(int vertex);

    void readFromFile(String filename) throws IOException;
    
    String toString();
    // Представление графа в строку
    boolean equals(Object obj);
    // Операция сравнения на равенство

    List<Integer> topologicalSort();
}
