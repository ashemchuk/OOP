package ru.ashemchuk.sort;

import java.util.List;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

/**
 * Interface for graph sorting algorithms.
 * Defines the contract for classes that implement graph sorting functionality.
 */
public interface Sorter {
    /**
     * Sorts the vertices of a graph according to a specific algorithm.
     *
     * @param graph the graph to be sorted
     * @return a list of vertices in sorted order
     */
    List<Vertex> sorted(Graph graph);
}