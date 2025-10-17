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
    Vertex[] getVertices();

    public default List<Vertex> topSort() {
        Stack<Vertex> stack = new Stack<>();

        boolean[] visited = new boolean[getVerticesCount()];

        for (Vertex v: getVertices()) {
            if (!visited[v.num()]) {
                    _topSort(v, visited, stack);
            }
        }
        List<Vertex> l = new ArrayList<>();
        while (!stack.empty()) {
            l.add(stack.pop());
        }
        return l;
    }

    private void _topSort(Vertex v, boolean[] visited, Stack<Vertex> stack) {
        visited[v.num()] = true;
        for (Vertex n : getNeighbours(v)) {
            if (!visited[n.num()]) {
                _topSort(n, visited, stack);
            }
        }
        stack.push(v);
    }
}
