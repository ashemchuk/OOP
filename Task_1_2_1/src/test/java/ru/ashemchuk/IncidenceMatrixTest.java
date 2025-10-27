package ru.ashemchuk;

import ru.ashemchuk.graph.Graph;

class IncidenceMatrixTest extends GraphTest {

    @Override
    protected Graph create() {
        return new IncidenceMatrix();
    }
}