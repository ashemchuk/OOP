package ru.ashemchuk.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import ru.ashemchuk.sort.Sorter;
import ru.ashemchuk.sort.TopSort;

/**
 * Represents a graph data structure with various operations.
 * Provides default implementations for graph sorting, equality comparison, and file loading.
 */
public interface Graph {
    /**
     * Adds a directed edge to the graph.
     * If start or end vertex is not in graph - edge won't be added.
     *
     * @param e the edge to be added
     */
    void addEdge(Edge e);

    /**
     * Removes a directed edge from the graph.
     * If edge, start or end of edge not in graph - nothing's going to happen.
     *
     * @param e the edge to be removed
     */
    void deleteEdge(Edge e);

    /**
     * Adds a vertex to the graph.
     * If v already in graph - nothing's going to happen.
     *
     * @param v the vertex to be added
     */
    void addVertex(Vertex v);

    /**
     * Removes a vertex from the graph and its incident edges.
     *
     * @param v the vertex to be removed
     */
    void deleteVertex(Vertex v);

    /**
     * Returns a list of all vertices that are directly reachable from the given vertex.
     *
     * @param v the source vertex
     * @return a list of neighboring vertices
     */
    List<Vertex> getNeighbours(Vertex v);

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices
     */
    int getVerticesCount();

    /**
     * Returns a list of all vertices in the graph.
     *
     * @return a list of vertices
     */
    List<Vertex> getVertices();

    /**
     * Sorts the graph using the specified sorting algorithm.
     *
     * @param algo the sorting algorithm to use
     * @return a sorted list of vertices
     */
    default List<Vertex> sort(Sorter algo) {
        return algo.sorted(this);
    }

    /**
     * Sorts the graph using the default topological sort algorithm.
     *
     * @return a topologically sorted list of vertices
     */
    default List<Vertex> sort() {
        return new TopSort().sorted(this);
    }

    /**
     * Compares this graph with another graph for equality.
     * Two graphs are considered equal if they have the same vertices with the same names
     * and the same adjacency relationships after topological sorting.
     *
     * @param g the graph to compare with
     * @return true if the graphs are equal, false otherwise
     */
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

            if (!n1.equals(n2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Loads graph data from a file.
     * The file should contain lines with pairs of vertex names separated by space,
     * representing directed edges from the first vertex to the second vertex.
     * Incorrect lines will be ignored.
     *
     * @param path the path to the file containing graph data
     */
    default void fromFile(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    String[] vertices = line.split(" ");
                    if (vertices.length != 2) {
                        continue;
                    }
                    Vertex v1 = new Vertex(vertices[0]);
                    Vertex v2 = new Vertex(vertices[1]);
                    if (!this.getVertices().contains(v1)) {
                        this.addVertex(v1);
                    }
                    if (!this.getVertices().contains(v2)) {
                        this.addVertex(v2);
                    }
                    this.addEdge(new Edge(v1, v2));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File " + path + " is not found");
        }
    }
}