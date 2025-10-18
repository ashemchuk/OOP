package ru.ashemchuk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.graph.Edge;
import ru.ashemchuk.graph.Vertex;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixTest {

    private AdjacencyMatrix graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrix();
    }

    @Test
    void testAddVertex() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");

        graph.addVertex(v1);
        graph.addVertex(v2);

        assertEquals(2, graph.getVerticesCount());
        assertTrue(graph.getVertices().contains(v1));
        assertTrue(graph.getVertices().contains(v2));
    }

    @Test
    void testDeleteVertex() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        graph.deleteVertex(v2);

        assertEquals(2, graph.getVerticesCount());
        assertTrue(graph.getVertices().contains(v1));
        assertFalse(graph.getVertices().contains(v2));
        assertTrue(graph.getVertices().contains(v3));
    }

    @Test
    void testAddEdge() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        Edge edge1 = new Edge(v1, v2);
        Edge edge2 = new Edge(v1, v3);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        List<Vertex> neighbours = graph.getNeighbours(v1);
        assertEquals(2, neighbours.size());
        assertTrue(neighbours.contains(v2));
        assertTrue(neighbours.contains(v3));
    }

    @Test
    void testAddEdgeThrowsExceptionWhenVertexNotFound() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C"); // Not added to graph

        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v3);

        assertThrows(NoSuchElementException.class, () -> graph.addEdge(edge));
    }

    @Test
    void testDeleteEdge() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        Edge edge1 = new Edge(v1, v2);
        Edge edge2 = new Edge(v1, v3);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        // Delete one edge
        graph.deleteEdge(edge1);

        List<Vertex> neighbours = graph.getNeighbours(v1);
        assertEquals(1, neighbours.size());
        assertFalse(neighbours.contains(v2));
        assertTrue(neighbours.contains(v3));
    }

    @Test
    void testDeleteEdgeThrowsExceptionWhenVertexNotFound() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C"); // Not added to graph

        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v3);

        assertThrows(NoSuchElementException.class, () -> graph.deleteEdge(edge));
    }

    @Test
    void testGetNeighbours() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C");
        Vertex v4 = new Vertex("D");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        graph.addEdge(new Edge(v1, v2));
        graph.addEdge(new Edge(v1, v3));
        graph.addEdge(new Edge(v2, v4));

        List<Vertex> neighboursV1 = graph.getNeighbours(v1);
        assertEquals(2, neighboursV1.size());
        assertTrue(neighboursV1.contains(v2));
        assertTrue(neighboursV1.contains(v3));

        List<Vertex> neighboursV2 = graph.getNeighbours(v2);
        assertEquals(1, neighboursV2.size());
        assertTrue(neighboursV2.contains(v4));

        List<Vertex> neighboursV3 = graph.getNeighbours(v3);
        assertTrue(neighboursV3.isEmpty());

        List<Vertex> neighboursV4 = graph.getNeighbours(v4);
        assertTrue(neighboursV4.isEmpty());
    }

    @Test
    void testGetNeighboursForIsolatedVertex() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");

        graph.addVertex(v1);
        graph.addVertex(v2);

        List<Vertex> neighbours = graph.getNeighbours(v1);
        assertTrue(neighbours.isEmpty());
    }

    @Test
    void testGetVerticesCount() {
        assertEquals(0, graph.getVerticesCount());

        graph.addVertex(new Vertex("A"));
        assertEquals(1, graph.getVerticesCount());

        graph.addVertex(new Vertex("B"));
        assertEquals(2, graph.getVerticesCount());

        graph.deleteVertex(new Vertex("A"));
        assertEquals(1, graph.getVerticesCount());
    }

    @Test
    void testGetVertices() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        List<Vertex> vertices = graph.getVertices();
        assertEquals(3, vertices.size());
        assertTrue(vertices.contains(v1));
        assertTrue(vertices.contains(v2));
        assertTrue(vertices.contains(v3));
    }

    @Test
    void testComplexGraphOperations() {
        Vertex v1 = new Vertex("1");
        Vertex v2 = new Vertex("2");
        Vertex v3 = new Vertex("3");
        Vertex v4 = new Vertex("4");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        // Create edges: 1 -> 2, 1 -> 3, 2 -> 4, 3 -> 4
        graph.addEdge(new Edge(v1, v2));
        graph.addEdge(new Edge(v1, v3));
        graph.addEdge(new Edge(v2, v4));
        graph.addEdge(new Edge(v3, v4));

        // Verify neighbours
        assertEquals(2, graph.getNeighbours(v1).size());
        assertEquals(1, graph.getNeighbours(v2).size());
        assertEquals(1, graph.getNeighbours(v3).size());
        assertEquals(0, graph.getNeighbours(v4).size());

        // Delete vertex and verify matrix is updated
        graph.deleteVertex(v3);

        assertEquals(3, graph.getVerticesCount());
        assertEquals(1, graph.getNeighbours(v1).size()); // Should only have v2 now
        assertTrue(graph.getNeighbours(v1).contains(v2));
    }

    @Test
    void testTopSortOnAdjacencyMatrix() {
        Vertex v0 = new Vertex("0");
        Vertex v1 = new Vertex("1");
        Vertex v2 = new Vertex("2");
        Vertex v3 = new Vertex("3");
        Vertex v4 = new Vertex("4");
        Vertex v5 = new Vertex("5");

        graph.addVertex(v0);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);

        /**
         *        -> 2
         *    /        \
         *  5           \
         *    \          \
         *     ->0        ->3
         *    /          /
         *  4           /
         *    \        /
         *      -> 1 <-
         */
        graph.addEdge(new Edge(v5, v0));
        graph.addEdge(new Edge(v5, v2));
        graph.addEdge(new Edge(v4, v0));
        graph.addEdge(new Edge(v4, v1));
        graph.addEdge(new Edge(v2, v3));
        graph.addEdge(new Edge(v3, v1));

        List<Vertex> result = graph.topSort();

        assertNotNull(result);
        assertEquals(6, result.size());

        assertTrue(result.indexOf(v5) < result.indexOf(v0));
        assertTrue(result.indexOf(v5) < result.indexOf(v2));
        assertTrue(result.indexOf(v4) < result.indexOf(v0));
        assertTrue(result.indexOf(v4) < result.indexOf(v1));
        assertTrue(result.indexOf(v2) < result.indexOf(v3));
        assertTrue(result.indexOf(v3) < result.indexOf(v1));
    }
}