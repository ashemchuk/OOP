package ru.ashemchuk.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import ru.ashemchuk.sort.Sorter;
import ru.ashemchuk.sort.TopSort;

public interface Graph {
    void addEdge(Edge e);
    void deleteEdge(Edge e);
    void addVertex(Vertex v);
    void deleteVertex(Vertex v);
    List<Vertex> getNeighbours(Vertex v);
    int getVerticesCount();
    List<Vertex> getVertices();

    default List<Vertex> sort(Sorter algo) {
        return algo.sorted(this);
    }

    default List<Vertex> sort() {
        return new TopSort().sorted(this);
    }

    default boolean equalsToGraph(Graph g) {
        if (g == null) {
            return false;
        }
        List<Vertex> v1 = this.sort();
        List<Vertex> v2 = g.sort();

        if (v1.size() != v2.size()) {
            return false;
        }
        for (int i = 0; i < v1.size(); i++) {
            if (!v1.get(i).name().equals(v2.get(i).name())) {
                return false;
            }
            List<Vertex> n1 = this.getNeighbours(v1.get(i));
            List<Vertex> n2 = g.getNeighbours(v2.get(i));

            if(!n1.equals(n2)) {
                return false;
            }
        }
        return true;
    }

    default void fromFile(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    String [] vertices = line.split(" ");
                    if (vertices.length != 2) {
//                        throw
                        continue;
                    }
                    Vertex v1 = new Vertex(vertices[0]);
                    Vertex v2 = new Vertex(vertices[1]);
                    if (!this.getVertices().contains(v1)) {
                        this.addVertex(v1);
                    }
                    if (!this.getVertices().contains(v2)) {
                        this.addVertex(v2);
                    }
                    this.addEdge(new Edge(v1, v2));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File " + path + " is not found");
        }
    }
}
