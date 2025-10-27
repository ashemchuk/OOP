package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.ashemchuk.graph.Edge;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;
import ru.ashemchuk.sort.TopSort;

abstract class GraphTest {

    protected Graph graph;
    protected Graph graph1, graph2, graph3, emptyGraph;

    protected abstract Graph create();

    @BeforeEach
    void setUp() {
        graph = create();

        // for equals test
        graph1 = create();
        graph2 = create();
        graph3 = create();
        emptyGraph = create();

        // Graph 1: A -> B -> C
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        graph1.addVertex(a);
        graph1.addVertex(b);
        graph1.addVertex(c);
        graph1.addEdge(new Edge(a, b));
        graph1.addEdge(new Edge(b, c));

        // Graph 2: same structure as graph1
        Vertex a2 = new Vertex("A");
        Vertex b2 = new Vertex("B");
        Vertex c2 = new Vertex("C");

        graph2.addVertex(a2);
        graph2.addVertex(b2);
        graph2.addVertex(c2);
        graph2.addEdge(new Edge(a2, b2));
        graph2.addEdge(new Edge(b2, c2));

        // Graph 3: different structure A -> C -> B
        Vertex a3 = new Vertex("A");
        Vertex b3 = new Vertex("B");
        Vertex c3 = new Vertex("C");

        graph3.addVertex(a3);
        graph3.addVertex(b3);
        graph3.addVertex(c3);
        graph3.addEdge(new Edge(a3, c3));
        graph3.addEdge(new Edge(c3, b3));
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

        graph.addEdge(new Edge(v5, v0));
        graph.addEdge(new Edge(v5, v2));
        graph.addEdge(new Edge(v4, v0));
        graph.addEdge(new Edge(v4, v1));
        graph.addEdge(new Edge(v2, v3));
        graph.addEdge(new Edge(v3, v1));

        List<Vertex> result = graph.sort(new TopSort());

        assertNotNull(result);
        assertEquals(6, result.size());

        assertTrue(result.indexOf(v5) < result.indexOf(v0));
        assertTrue(result.indexOf(v5) < result.indexOf(v2));
        assertTrue(result.indexOf(v4) < result.indexOf(v0));
        assertTrue(result.indexOf(v4) < result.indexOf(v1));
        assertTrue(result.indexOf(v2) < result.indexOf(v3));
        assertTrue(result.indexOf(v3) < result.indexOf(v1));
    }

    @Test
    void testEquals_SameGraphs_ReturnsTrue() {
        Graph graph1 = this.create();
        Graph graph2 = this.create();
        assertTrue(graph1.equalsToGraph(graph2));
    }

    @Test
    void testEquals_DifferentGraphs_ReturnsFalse() {
        assertFalse(graph1.equalsToGraph(graph3));
    }

    @Test
    void testEquals_DifferentSizes_ReturnsFalse() {
        Graph smallGraph = create();
        smallGraph.addVertex(new Vertex("A"));

        assertFalse(graph1.equalsToGraph(smallGraph));
    }

    @Test
    void testEquals_EmptyGraphs_ReturnsTrue() {
        Graph anotherEmptyGraph = create();
        assertTrue(emptyGraph.equalsToGraph(anotherEmptyGraph));
    }

    @Test
    void testEquals_OneEmptyOneNotEmpty_ReturnsFalse() {
        assertFalse(emptyGraph.equalsToGraph(graph1));
    }

    @Test
    void testEquals_SameVerticesDifferentEdges_ReturnsFalse() {
        Graph graphWithExtraEdge = create();
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        graphWithExtraEdge.addVertex(a);
        graphWithExtraEdge.addVertex(b);
        graphWithExtraEdge.addVertex(c);
        graphWithExtraEdge.addEdge(new Edge(a, b));
        graphWithExtraEdge.addEdge(new Edge(b, c));
        graphWithExtraEdge.addEdge(new Edge(a, c)); // Extra edge

        assertFalse(graph1.equalsToGraph(graphWithExtraEdge));
    }

    @Test
    void testEquals_SameStructureDifferentVertexNames_ReturnsFalse() {
        Graph differentNamesGraph = create();
        Vertex x = new Vertex("X");
        Vertex y = new Vertex("Y");
        Vertex z = new Vertex("Z");

        differentNamesGraph.addVertex(x);
        differentNamesGraph.addVertex(y);
        differentNamesGraph.addVertex(z);
        differentNamesGraph.addEdge(new Edge(x, y));
        differentNamesGraph.addEdge(new Edge(y, z));

        assertFalse(graph1.equalsToGraph(differentNamesGraph));
    }

    @Test
    void testEquals_WithCycles_SameStructure() {
        Graph cyclicGraph1 = create();
        Graph cyclicGraph2 = create();

        Vertex a1 = new Vertex("A");
        Vertex b1 = new Vertex("B");
        Vertex c1 = new Vertex("C");

        cyclicGraph1.addVertex(a1);
        cyclicGraph1.addVertex(b1);
        cyclicGraph1.addVertex(c1);
        cyclicGraph1.addEdge(new Edge(a1, b1));
        cyclicGraph1.addEdge(new Edge(b1, c1));
        cyclicGraph1.addEdge(new Edge(c1, a1));

        Vertex a2 = new Vertex("A");
        Vertex b2 = new Vertex("B");
        Vertex c2 = new Vertex("C");

        cyclicGraph2.addVertex(a2);
        cyclicGraph2.addVertex(b2);
        cyclicGraph2.addVertex(c2);
        cyclicGraph2.addEdge(new Edge(a2, b2));
        cyclicGraph2.addEdge(new Edge(b2, c2));
        cyclicGraph2.addEdge(new Edge(c2, a2));

        assertTrue(cyclicGraph1.equalsToGraph(cyclicGraph2));
    }

    @Test
    void testEquals_IsolatedVertices_SameStructure() {
        Graph isolatedGraph1 = create();
        Graph isolatedGraph2 = create();

        Vertex a1 = new Vertex("A");
        Vertex b1 = new Vertex("B");

        isolatedGraph1.addVertex(a1);
        isolatedGraph1.addVertex(b1);
        // No edges

        Vertex a2 = new Vertex("A");
        Vertex b2 = new Vertex("B");

        isolatedGraph2.addVertex(a2);
        isolatedGraph2.addVertex(b2);
        // No edges

        assertTrue(isolatedGraph1.equalsToGraph(isolatedGraph2));
    }

    @Test
    void testEquals_NullGraph_ReturnsFalse() {
        assertFalse(graph1.equalsToGraph(null));
    }

    @Test
    void testEquals_Reflexivity() {
        assertTrue(graph1.equalsToGraph(graph1));
    }

    @Test
    void testEquals_Symmetry() {
        // If graph1 equals graph2, then graph2 should equal graph1
        boolean firstComparison = graph1.equalsToGraph(graph2);
        boolean secondComparison = graph2.equalsToGraph(graph1);

        assertEquals(firstComparison, secondComparison);
    }

    @TempDir
    Path tempDir;

    @Test
    void testFromFile_graph1() throws IOException {
        File testFile = tempDir.resolve("test_file.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("A B\n");
            writer.write("B C\n");
        }
        Graph g = create();
        g.fromFile(testFile.getPath());

        assertTrue(g.equalsToGraph(graph1));
    }

    @Test
    void testFromFile_InvalidLines() throws IOException {
        File testFile = tempDir.resolve("invalid_lines.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("A B\n");           // Valid
            writer.write("SingleVertex\n");  // Invalid - only one vertex
            writer.write("Too Many Vertices Here\n"); // Invalid - more than 2
            writer.write("B C\n");           // Valid
            writer.write("\n");              // Empty line
            writer.write("   \n");           // Whitespace line
        }

        Graph graph = create();
        graph.fromFile(testFile.getAbsolutePath());

        assertTrue(graph.equalsToGraph(graph1));
    }
}