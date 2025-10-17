package ru.ashemchuk;

import java.util.List;
import ru.ashemchuk.graph.Edge;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

public class AdjacencyMatrix implements Graph {

    @Override
    public void addEdge(Edge e) {

    }

    @Override
    public void deleteEdge(Edge e) {

    }

    @Override
    public void addVertex(Vertex v) {

    }

    @Override
    public void deleteVertex(Vertex v) {

    }

    @Override
    public List<Vertex> getNeighbours(Vertex v) {
        return List.of();
    }

    @Override
    public int getVerticesCount() {
        return 0;
    }

    @Override
    public Vertex[] getVertices() {
        return new Vertex[0];
    }
}
