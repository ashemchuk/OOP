package ru.ashemchuk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import ru.ashemchuk.graph.Edge;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

public class IncidenceMatrix implements Graph{
    private final ArrayList<ArrayList<Integer>> matrix;
    private final ArrayList<Vertex> vertices;
    private final ArrayList<Edge> edges;

    public IncidenceMatrix () {
        this.matrix = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
    @Override
    public void addEdge(Edge e) throws NoSuchElementException {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            throw new NoSuchElementException("There is no start/end of edge in graph");
        }
        edges.add(e);
        int edgeIdx = edges.indexOf(e);
        matrix.add(edgeIdx, new ArrayList<>(Collections.nCopies(vertices.size(), 0)));
        matrix.get(edgeIdx).set(vertices.indexOf(e.start()), -1);
        matrix.get(edgeIdx).set(vertices.indexOf(e.end()), 1);
    }

    @Override
    public void deleteEdge(Edge e) throws NoSuchElementException {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            throw new NoSuchElementException("There is no start/end of edge in graph");
        }
        if (!edges.contains(e)) {
            return;
        }
        matrix.remove(edges.indexOf(e));
    }

    @Override
    public void addVertex(Vertex v) {
        //FIXME
        if (vertices.contains(v)) {
            return;
        }
        vertices.add(v);
        for (ArrayList<Integer> row : matrix) {
            row.add(0);
        }
    }

    @Override
    public void deleteVertex(Vertex v) {
        // FIXME
        if (!vertices.contains(v)) {
            return;
        }
        int vertexIdx = vertices.indexOf(v);
        vertices.remove(vertexIdx);
        for (int i = 0; i < matrix.size(); i++) {
            ArrayList<Integer> row = matrix.get(i);
            if (row.get(vertexIdx) != 0) {
                matrix.remove(row);
            } else {
                row.remove(vertexIdx);
            }
        }
    }

    @Override
    public List<Vertex> getNeighbours(Vertex v) {
        //FIXME
        if (!vertices.contains(v)) {
            return null;
        }
        List<Vertex> neighbours = new ArrayList<>();
        int idx = vertices.indexOf(v);
        for (ArrayList<Integer> row: matrix) {
            if (row.get(idx) == -1) {
                int n = row.indexOf(1);
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
