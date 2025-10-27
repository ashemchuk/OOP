package ru.ashemchuk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.ashemchuk.graph.Edge;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

/**
 * Represents a graph using an incidence matrix data structure.
 * The graph is directed and uses a matrix where rows represent edges and columns represent vertices.
 * Values of -1 indicate an outgoing edge, 1 indicates an incoming edge, and 0 indicates no incidence.
 */
public class IncidenceMatrix implements Graph {
    private final ArrayList<ArrayList<Integer>> matrix;
    private final ArrayList<Vertex> vertices;
    private final ArrayList<Edge> edges;

    /**
     * Constructs an empty incidence matrix graph.
     */
    public IncidenceMatrix() {
        this.matrix = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Adds a directed edge to the graph.
     * Creates a new row in the incidence matrix representing the edge.
     *
     * @param e the edge to be added
     */
    @Override
    public void addEdge(Edge e) {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            return;
        }
        edges.add(e);
        int edgeIdx = edges.indexOf(e);
        matrix.add(edgeIdx, new ArrayList<>(Collections.nCopies(vertices.size(), 0)));
        matrix.get(edgeIdx).set(vertices.indexOf(e.start()), -1);
        matrix.get(edgeIdx).set(vertices.indexOf(e.end()), 1);
    }

    /**
     * Removes an edge from the graph.
     * Removes the corresponding row from the incidence matrix.
     *
     * @param e the edge to be removed
     */
    @Override
    public void deleteEdge(Edge e) {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            return;
        }
        if (!edges.contains(e)) {
            return;
        }
        matrix.remove(edges.indexOf(e));
    }

    /**
     * Adds a vertex to the graph.
     * If the vertex already exists, the method does nothing.
     * Expands each row of the incidence matrix to accommodate the new vertex.
     *
     * @param v the vertex to be added
     */
    @Override
    public void addVertex(Vertex v) {
        if (vertices.contains(v)) {
            return;
        }
        vertices.add(v);
        for (ArrayList<Integer> row : matrix) {
            row.add(0);
        }
    }

    /**
     * Removes a vertex and all incident edges from the graph.
     *
     * @param v the vertex to be removed
     */
    @Override
    public void deleteVertex(Vertex v) {
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

    /**
     * Returns a list of all vertices that are directly reachable from the given vertex.
     *
     * @param v the source vertex
     * @return a list of neighboring vertices, or empty list if the vertex is not in the graph
     */
    @Override
    public List<Vertex> getNeighbours(Vertex v) {
        if (!vertices.contains(v)) {
            return Collections.emptyList();
        }
        List<Vertex> neighbours = new ArrayList<>();
        int idx = vertices.indexOf(v);
        for (ArrayList<Integer> row : matrix) {
            if (row.get(idx) == -1) {
                int n = row.indexOf(1);
                neighbours.add(vertices.get(n));
            }
        }
        return neighbours;
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices
     */
    @Override
    public int getVerticesCount() {
        return vertices.size();
    }

    /**
     * Returns a list of all vertices in the graph.
     *
     * @return a list of vertices
     */
    @Override
    public List<Vertex> getVertices() {
        return vertices;
    }
}