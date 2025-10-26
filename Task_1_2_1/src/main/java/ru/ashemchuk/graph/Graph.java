package ru.ashemchuk.graph;

import java.util.List;
import ru.ashemchuk.sort.Sorter;
import ru.ashemchuk.sort.TopSort;

public interface Graph {
    void addEdge(Edge e);
    void deleteEdge(Edge e);
    void addVertex(Vertex v);
    void deleteVertex(Vertex v);
    List<Vertex> getNeighbours(Vertex v);
    int getVerticesCount();
    List<Vertex> getVertices();
    default List<Vertex> sort(Sorter algo) {
        TopSort s = new TopSort();
        return s.sorted(this);
    }
}
