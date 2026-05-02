package model;

import java.io.Serializable;
import java.util.Objects;

public class Vertex implements Serializable, Comparable<Vertex> {
    private static final long serialVersionUID = 1L;

    private int id;
    private String label;
    private Object data;

    public Vertex(int id) {
        this(id, String.valueOf(id), null);
    }

    public Vertex(int id, String label) {
        this(id, label, null);
    }

    public Vertex(int id, String label, Object data) {
        this.id = id;
        this.label = label;
        this.data = data;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return label != null ? label : String.valueOf(id);
    }
}
