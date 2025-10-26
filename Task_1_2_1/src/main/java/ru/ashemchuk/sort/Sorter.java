package ru.ashemchuk.sort;

import java.util.List;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

public interface Sorter {
    List<Vertex> sorted(Graph graph);
}
