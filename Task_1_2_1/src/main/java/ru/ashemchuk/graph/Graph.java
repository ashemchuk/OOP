package ru.ashemchuk.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public interface Graph {
    void addEdge(Edge e);
    void deleteEdge(Edge e);
    void addVertex(Vertex v);
    void deleteVertex(Vertex v);
    List<Vertex> getNeighbours(Vertex v);
    int getVerticesCount();
    List<Vertex> getVertices();

    public default List<Vertex> topSort() {
        Stack<Vertex> stack = new Stack<>();

        boolean[] visited = new boolean[getVerticesCount()];
        List<Vertex> vertices = getVertices();
        for (Vertex v: vertices) {
            if (!visited[vertices.indexOf(v)]) {
                    _topSort(v, visited, stack, vertices);
            }
        }
        List<Vertex> l = new ArrayList<>();
        while (!stack.empty()) {
            l.add(stack.pop());
        }
        return l;
    }

    private void _topSort(Vertex v, boolean[] visited, Stack<Vertex> stack, List<Vertex> vertices) {
        visited[vertices.indexOf(v)] = true;
        for (Vertex n : getNeighbours(v)) {
            if (!visited[vertices.indexOf(n)]) {
                _topSort(n, visited, stack, vertices);
            }
        }
        stack.push(v);
    }
}
