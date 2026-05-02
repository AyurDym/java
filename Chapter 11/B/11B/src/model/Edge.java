package model;

import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable, Comparable<Edge> {
    private static final long serialVersionUID = 1L;

    private int vertex1;
    private int vertex2;
    private double weight;

    public Edge(int vertex1, int vertex2) {
        this(vertex1, vertex2, 1.0);
    }

    public Edge(int vertex1, int vertex2, double weight) {
        // Нормализуем ребро: меньшая вершина всегда первой
        if (vertex1 <= vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        } else {
            this.vertex1 = vertex2;
            this.vertex2 = vertex1;
        }
        this.weight = weight;
    }

    public int getVertex1() { return vertex1; }
    public int getVertex2() { return vertex2; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public boolean contains(int vertex) {
        return vertex1 == vertex || vertex2 == vertex;
    }

    public int getOther(int vertex) {
        if (vertex == vertex1) return vertex2;
        if (vertex == vertex2) return vertex1;
        throw new IllegalArgumentException("Вершина " + vertex + " не принадлежит этому ребру");
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return vertex1 == edge.vertex1 && vertex2 == edge.vertex2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex1, vertex2);
    }

    @Override
    public String toString() {
        return String.format("(%d - %d) вес: %.2f", vertex1, vertex2, weight);
    }
}
