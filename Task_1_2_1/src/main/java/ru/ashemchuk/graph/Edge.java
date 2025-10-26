package ru.ashemchuk.graph;

/**
 * Represents a directed edge between two vertices in a graph.
 */
public record Edge(Vertex start, Vertex end) {
}