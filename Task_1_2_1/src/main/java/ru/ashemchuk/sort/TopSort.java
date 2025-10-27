package ru.ashemchuk.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

/**
 * Implementation of topological sort algorithm for directed acyclic graphs (DAGs).
 * Uses depth-first search (DFS) to traverse the graph and produce a topological ordering.
 * The algorithm ensures that for every directed edge from vertex u to vertex v,
 * u comes before v in the ordering.
 */
public class TopSort implements Sorter {
    /**
     * Performs topological sort on the given graph.
     *
     * @param g the graph to be sorted
     * @return a list of vertices in topologically sorted order
     */
    @Override
    public List<Vertex> sorted(Graph g) {
        Stack<Vertex> stack = new Stack<>();

        boolean[] visited = new boolean[g.getVerticesCount()];
        List<Vertex> vertices = g.getVertices();
        for (Vertex v : vertices) {
            if (!visited[vertices.indexOf(v)]) {
                dfs(g, v, visited, stack, vertices);
            }
        }
        List<Vertex> l = new ArrayList<>();
        while (!stack.empty()) {
            l.add(stack.pop());
        }
        return l;
    }

    /**
     * Performs depth-first search traversal of the graph.
     * Recursively visits all unvisited neighbors and pushes vertices to stack
     * in post-order (after all neighbors have been visited).
     *
     * @param g        the graph being traversed
     * @param v        the current vertex being visited
     * @param visited  array tracking visited vertices
     * @param stack    the stack used for storing vertices in reverse topological order
     * @param vertices list of all vertices in the graph
     */
    private void dfs(Graph g, Vertex v, boolean[] visited, Stack<Vertex> stack,
                     List<Vertex> vertices) {
        visited[vertices.indexOf(v)] = true;
        for (Vertex n : g.getNeighbours(v)) {
            if (!visited[vertices.indexOf(n)]) {
                dfs(g, n, visited, stack, vertices);
            }
        }
        stack.push(v);
    }
}