package ru.ashemchuk;

import ru.ashemchuk.graph.Graph;

public class AdjacencyMatrixTest extends GraphTest {

    @Override
    protected Graph create() {
        return new AdjacencyMatrix();
    }
}
