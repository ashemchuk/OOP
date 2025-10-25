package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.graph.Edge;
import ru.ashemchuk.graph.Graph;
import ru.ashemchuk.graph.Vertex;

public class AdjacencyList implements Graph {
    private final ArrayList<ArrayList<Integer>> list;
    private final ArrayList<Vertex> vertices;

    public AdjacencyList() {
        this.list = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    @Override
    public void addEdge(Edge e) {
        //FIXME
        if (!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            return;
        }
        list.get(vertices.indexOf(e.start())).add(vertices.indexOf(e.end()));
    }

    @Override
    public void deleteEdge(Edge e) {
    //FIXME
        if(!vertices.contains(e.start()) || !vertices.contains(e.end())) {
            return;
        }
        list.get(vertices.indexOf(e.start())).remove((Object)vertices.indexOf(e.end()));
    }

    @Override
    public void addVertex(Vertex v) {
        if (vertices.contains(v)) {
            return;
        }
        vertices.add(v);
        list.add(new ArrayList<>());
    }

    @Override
    public void deleteVertex(Vertex v) {
        //FIXME
        if (!vertices.contains(v)) {
            return;
        }
        for (ArrayList<Integer> row: list) {
            if (row.contains(vertices.indexOf(v))) {
                row.remove((Object)vertices.indexOf(v));
            }
        }
        vertices.remove(v);
    }

    @Override
    public List<Vertex> getNeighbours(Vertex v) {
        //FIXME
        if (!vertices.contains(v)) {
            return null;
        }
        List<Vertex> neighbours = new ArrayList<>();
        for (Integer n : list.get(vertices.indexOf(v))) {
            neighbours.add(vertices.get(n));
        }
        return neighbours;
    }

    @Override
    public int getVerticesCount() {
        return vertices.size();
    }

    @Override
    public List<Vertex> getVertices() {
        return vertices;
    }
}
