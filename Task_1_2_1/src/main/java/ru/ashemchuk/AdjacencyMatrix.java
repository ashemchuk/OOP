package ru.ashemchuk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.ashemchuk.graph.Edge;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

/**
 * Represents a graph using an adjacency matrix data structure.
 * The graph is directed and uses a boolean matrix to indicate edge presence.
 * Vertices are stored in a list, and edges are represented by true values in the matrix.
 */
public class AdjacencyMatrix implements Graph {
    private final ArrayList<ArrayList<Boolean>> matrix;
    private final ArrayList<Vertex> vertices;

    /**
     * Constructs an empty adjacency matrix graph.
     */
    public AdjacencyMatrix() {
        this.matrix = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    /**
     * Adds a directed edge between two vertices in the graph.
     *
     * @param e the edge to be added
     */
    @Override
    public void addEdge(Edge e) {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            return;
        }
        int start = vertices.indexOf(e.start());
        int end = vertices.indexOf(e.end());
        matrix.get(start).set(end, true);
    }

    /**
     * Removes a directed edge between two vertices in the graph.
     *
     * @param e the edge to be removed
     */
    @Override
    public void deleteEdge(Edge e) {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            return;
        }
        int start = vertices.indexOf(e.start());
        int end = vertices.indexOf(e.end());
        matrix.get(start).set(end, false);
    }

    /**
     * Adds a vertex to the graph.
     * If the vertex already exists, the method does nothing.
     * Expands the adjacency matrix to accommodate the new vertex.
     *
     * @param v the vertex to be added
     */
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

    /**
     * Removes a vertex and all associated edges from the graph.
     *
     * @param v the vertex to be removed
     */
    @Override
    public void deleteVertex(Vertex v) {
        if (!vertices.contains(v)) {
            return;
        }
        int idx = vertices.indexOf(v);
        for (ArrayList<Boolean> rows : matrix) {
            rows.remove(idx);
        }
        matrix.remove(idx);
        vertices.remove(v);
    }

    /**
     * Returns a list of all vertices that are directly reachable from the given vertex.
     *
     * @param v the source vertex
     * @return a list of neighboring vertices
     */
    @Override
    public List<Vertex> getNeighbours(Vertex v) {
        if (!vertices.contains(v)) {
            return Collections.emptyList();
        }
        List<Vertex> neighbours = new ArrayList<>();
        for (int n = 0; n < matrix.get(vertices.indexOf(v)).size(); n++) {
            if (matrix.get(vertices.indexOf(v)).get(n)) {
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