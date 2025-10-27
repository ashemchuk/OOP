package ru.ashemchuk;

import ru.ashemchuk.graph.Graph;

class AdjacencyListTest extends GraphTest {

    @Override
    protected Graph create() {
        return new AdjacencyList();
    }
}