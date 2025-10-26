package ru.ashemchuk.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

public class TopSort implements Sorter{
    @Override
    public List<Vertex> sorted(Graph graph) {
        return topSort(graph);
    }
    public List<Vertex> topSort(Graph g) {
        Stack<Vertex> stack = new Stack<>();

        boolean[] visited = new boolean[g.getVerticesCount()];
        List<Vertex> vertices = g.getVertices();
        for (Vertex v: vertices) {
            if (!visited[vertices.indexOf(v)]) {
                _topSort(g, v, visited, stack, vertices);
            }
        }
        List<Vertex> l = new ArrayList<>();
        while (!stack.empty()) {
            l.add(stack.pop());
        }
        return l;
    }

    private void _topSort(Graph g,Vertex v, boolean[] visited, Stack<Vertex> stack, List<Vertex> vertices) {
        visited[vertices.indexOf(v)] = true;
        for (Vertex n : g.getNeighbours(v)) {
            if (!visited[vertices.indexOf(n)]) {
                _topSort(g, n, visited, stack, vertices);
            }
        }
        stack.push(v);
    }
}
