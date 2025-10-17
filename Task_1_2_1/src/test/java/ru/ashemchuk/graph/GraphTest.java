package ru.ashemchuk.graph;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GraphTest {
    @Spy
    private Graph graph;

    @Test
    void testTopSort() {
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
        when(graph.getVerticesCount()).thenReturn(6);
        Vertex[] vertices = {
            new Vertex(0),
            new Vertex(1),
            new Vertex(2),
            new Vertex(3),
            new Vertex(4),
            new Vertex(5)
        };
        when(graph.getVertices()).thenReturn(vertices);

        when(graph.getNeighbours(vertices[0])).thenReturn(new ArrayList<>());
        when(graph.getNeighbours(vertices[1])).thenReturn(new ArrayList<>());
        when(graph.getNeighbours(vertices[2])).thenReturn(Arrays.asList(vertices[3]));
        when(graph.getNeighbours(vertices[3])).thenReturn(Arrays.asList(vertices[1]));
        when(graph.getNeighbours(vertices[4])).thenReturn(Arrays.asList(vertices[0], vertices[1]));
        when(graph.getNeighbours(vertices[5])).thenReturn(Arrays.asList(vertices[0], vertices[2]));

        List<Vertex> result = graph.topSort();

        assertNotNull(result);
        assertEquals(6, result.size());

        assertTrue(result.indexOf(vertices[5]) < result.indexOf(vertices[0]));
        assertTrue(result.indexOf(vertices[5]) < result.indexOf(vertices[2]));

        assertTrue(result.indexOf(vertices[4]) < result.indexOf(vertices[0]));
        assertTrue(result.indexOf(vertices[4]) < result.indexOf(vertices[1]));

        assertTrue(result.indexOf(vertices[2]) < result.indexOf(vertices[3]));

        assertTrue(result.indexOf(vertices[3]) < result.indexOf(vertices[1]));
    }

    @Test
    void testTopSortEmptyGraph() {
        when(graph.getVerticesCount()).thenReturn(0);
        when(graph.getVertices()).thenReturn(new Vertex[0]);

        List<Vertex> result = graph.topSort();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testTopSortSingleVertex() {
        when(graph.getVerticesCount()).thenReturn(1);
        Vertex[] vertices = {new Vertex(0)};
        when(graph.getVertices()).thenReturn(vertices);
        when(graph.getNeighbours(vertices[0])).thenReturn(new ArrayList<>());

        List<Vertex> result = graph.topSort();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(vertices[0], result.get(0));
    }

    @Test
    void testTopSortLinearGraph() {
        when(graph.getVerticesCount()).thenReturn(3);
        Vertex[] vertices = {
            new Vertex(0),
            new Vertex(1),
            new Vertex(2)
        };
        when(graph.getVertices()).thenReturn(vertices);
        when(graph.getNeighbours(vertices[0])).thenReturn(Arrays.asList(vertices[1]));
        when(graph.getNeighbours(vertices[1])).thenReturn(Arrays.asList(vertices[2]));
        when(graph.getNeighbours(vertices[2])).thenReturn(new ArrayList<>());

        List<Vertex> result = graph.topSort();

        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals(vertices[0], result.get(0));
        assertEquals(vertices[1], result.get(1));
        assertEquals(vertices[2], result.get(2));
    }
}