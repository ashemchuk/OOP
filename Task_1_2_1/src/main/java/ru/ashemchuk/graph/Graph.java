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
        return algo.sorted(this);
    }

    default List<Vertex> sort() {
        return new TopSort().sorted(this);
    }

    default boolean equalsToGraph(Graph g) {
        if (g == null) {
            return false;
        }
        List<Vertex> v1 = this.sort();
        List<Vertex> v2 = g.sort();

        if (v1.size() != v2.size()) {
            return false;
        }
        for (int i = 0; i < v1.size(); i++) {
            if (!v1.get(i).name().equals(v2.get(i).name())) {
                return false;
            }
            List<Vertex> n1 = this.getNeighbours(v1.get(i));
            List<Vertex> n2 = g.getNeighbours(v2.get(i));

            if(!n1.equals(n2)) {
                return false;
            }
        }
        return true;
    }
}
