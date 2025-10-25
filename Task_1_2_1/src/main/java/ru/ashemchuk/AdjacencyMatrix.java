package ru.ashemchuk;

import java.util.Collections;
import java.util.NoSuchElementException;
import ru.ashemchuk.graph.*;
import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.graph.Graph;

public class AdjacencyMatrix implements Graph {
    private final ArrayList<ArrayList<Boolean>> matrix;
    private final ArrayList<Vertex> vertices;

    public AdjacencyMatrix() {
        this.matrix = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    @Override
    public void addEdge(Edge e) throws NoSuchElementException {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            throw new NoSuchElementException("There is no start/end of edge in graph");
        }
        int start = vertices.indexOf(e.start());
        int end = vertices.indexOf(e.end());
        matrix.get(start).set(end, true);
    }

    @Override
    public void deleteEdge(Edge e) throws NoSuchElementException {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            throw new NoSuchElementException("There is no start/end of edge in graph");
        }
        int start = vertices.indexOf(e.start());
        int end = vertices.indexOf(e.end());
        matrix.get(start).set(end, false);
    }

    @Override
    public void addVertex(Vertex v) {
        if (vertices.contains(v)) {
            return;
        }
        vertices.add(v);
        matrix.add(new ArrayList<>(Collections.nCopies(vertices.size() - 1, false)));

        for (ArrayList<Boolean> row : matrix) {
            row.add(false);
        }
    }

    @Override
    public void deleteVertex(Vertex v) {
        //FIXME: there are not v in graph
        int idx = vertices.indexOf(v);
        for (ArrayList<Boolean> rows : matrix) {
            rows.remove(idx);
        }
        matrix.remove(idx);
        vertices.remove(v);
    }

    @Override
    public List<Vertex> getNeighbours(Vertex v) {
        // FIXME
        List<Vertex> neighbours = new ArrayList<>();
        for (int n = 0; n < matrix.get(vertices.indexOf(v)).size(); n++) {
            if (matrix.get(vertices.indexOf(v)).get(n)) {
                neighbours.add(vertices.get(n));
            }
        }
        return neighbours;
    }

    @Override
    public int getVerticesCount() {
        return vertices.size();
    }

    @Override
    public List<Vertex> getVertices() {
        return vertices;
    }
}