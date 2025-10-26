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
import ru.ashemchuk.sort.TopSort;

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
        List<Vertex> vertices = Arrays.asList(
            new Vertex("0"),
            new Vertex("1"),
            new Vertex("2"),
            new Vertex("3"),
            new Vertex("4"),
            new Vertex("5")
        );
        when(graph.getVertices()).thenReturn(vertices);

        when(graph.getNeighbours(vertices.get(0))).thenReturn(new ArrayList<>());
        when(graph.getNeighbours(vertices.get(1))).thenReturn(new ArrayList<>());
        when(graph.getNeighbours(vertices.get(2))).thenReturn(Arrays.asList(vertices.get(3)));
        when(graph.getNeighbours(vertices.get(3))).thenReturn(Arrays.asList(vertices.get(1)));
        when(graph.getNeighbours(vertices.get(4))).thenReturn(Arrays.asList(vertices.get(0), vertices.get(1)));
        when(graph.getNeighbours(vertices.get(5))).thenReturn(Arrays.asList(vertices.get(0), vertices.get(2)));

        List<Vertex> result = graph.sort(new TopSort());

        assertNotNull(result);
        assertEquals(6, result.size());

        assertTrue(result.indexOf(vertices.get(5)) < result.indexOf(vertices.get(0)));
        assertTrue(result.indexOf(vertices.get(5)) < result.indexOf(vertices.get(2)));

        assertTrue(result.indexOf(vertices.get(4)) < result.indexOf(vertices.get(0)));
        assertTrue(result.indexOf(vertices.get(4)) < result.indexOf(vertices.get(1)));

        assertTrue(result.indexOf(vertices.get(2)) < result.indexOf(vertices.get(3)));

        assertTrue(result.indexOf(vertices.get(3)) < result.indexOf(vertices.get(1)));
    }

    @Test
    void testTopSortEmptyGraph() {
        when(graph.getVerticesCount()).thenReturn(0);
        when(graph.getVertices()).thenReturn(new ArrayList<>());

        List<Vertex> result = graph.sort(new TopSort());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testTopSortSingleVertex() {
        when(graph.getVerticesCount()).thenReturn(1);
        List<Vertex> vertices = Arrays.asList(new Vertex("0"));
        when(graph.getVertices()).thenReturn(vertices);
        when(graph.getNeighbours(vertices.get(0))).thenReturn(new ArrayList<>());

        List<Vertex> result = graph.sort(new TopSort());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(vertices.get(0), result.get(0));
    }

    @Test
    void testTopSortLinearGraph() {
        when(graph.getVerticesCount()).thenReturn(3);
        List<Vertex> vertices = Arrays.asList(
            new Vertex("0"),
            new Vertex("1"),
            new Vertex("2")
        );
        when(graph.getVertices()).thenReturn(vertices);
        when(graph.getNeighbours(vertices.get(0))).thenReturn(Arrays.asList(vertices.get(1)));
        when(graph.getNeighbours(vertices.get(1))).thenReturn(Arrays.asList(vertices.get(2)));
        when(graph.getNeighbours(vertices.get(2))).thenReturn(new ArrayList<>());

        List<Vertex> result = graph.sort(new TopSort());

        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals(vertices.get(0), result.get(0));
        assertEquals(vertices.get(1), result.get(1));
        assertEquals(vertices.get(2), result.get(2));
    }


}