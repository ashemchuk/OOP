package ru.ashemchuk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.ashemchuk.graph.Edge;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

/**
 * Represents a graph using an adjacency list data structure.
 * The graph is directed and uses lists of integers to represent outgoing edges.
 * Vertices are stored in a list, and each vertex has a list of indices of its neighboring vertices.
 */
public class AdjacencyList implements Graph {
    private final ArrayList<ArrayList<Integer>> list;
    private final ArrayList<Vertex> vertices;

    /**
     * Constructs an empty adjacency list graph.
     */
    public AdjacencyList() {
        this.list = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    /**
     * Adds a directed edge between two vertices in the graph.
     * If either vertex is not in the graph, the method does nothing.
     *
     * @param e the edge to be added
     */
    @Override
    public void addEdge(Edge e) {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            return;
        }
        list.get(vertices.indexOf(e.start())).add(vertices.indexOf(e.end()));
    }

    /**
     * Removes a directed edge between two vertices in the graph.
     * If either vertex is not in the graph, the method does nothing.
     *
     * @param e the edge to be removed
     */
    @Override
    public void deleteEdge(Edge e) {
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            return;
        }
        list.get(vertices.indexOf(e.start())).remove((Object) vertices.indexOf(e.end()));
    }

    /**
     * Adds a vertex to the graph.
     * If the vertex already exists, the method does nothing.
     * Creates a new empty list for the vertex's outgoing edges.
     *
     * @param v the vertex to be added
     */
    @Override
    public void addVertex(Vertex v) {
        if (vertices.contains(v)) {
            return;
        }
        vertices.add(v);
        list.add(new ArrayList<>());
    }

    /**
     * Removes a vertex and all associated edges from the graph.
     * If the vertex is not in the graph, the method does nothing.
     *
     * @param v the vertex to be removed
     */
    @Override
    public void deleteVertex(Vertex v) {
        if (!vertices.contains(v)) {
            return;
        }
        for (ArrayList<Integer> row : list) {
            if (row.contains(vertices.indexOf(v))) {
                row.remove((Object) vertices.indexOf(v));
            }
        }
        vertices.remove(v);
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
        for (Integer n : list.get(vertices.indexOf(v))) {
            neighbours.add(vertices.get(n));
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