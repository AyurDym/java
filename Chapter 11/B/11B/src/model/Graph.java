package model;

import exception.GraphException;
import java.io.Serializable;
import java.util.*;

public class Graph implements Serializable {
    private static final long serialVersionUID = 1L;

    private int verticesCount;          // Количество вершин
    private List<Set<Integer>> adjacencyList;  // Список смежности для O(1) проверки
    private Set<Edge> edges;            // Множество ребер для быстрого удаления
    private Map<String, Edge> edgeMap;   // Быстрый доступ к ребрам по паре вершин

    /**
     * Конструктор графа
     * @param verticesCount количество вершин (вершины нумеруются от 0 до verticesCount-1)
     */
    public Graph(int verticesCount) {
        if (verticesCount <= 0) {
            throw new IllegalArgumentException("Количество вершин должно быть положительным");
        }

        this.verticesCount = verticesCount;
        this.adjacencyList = new ArrayList<>(verticesCount);
        this.edges = new HashSet<>();
        this.edgeMap = new HashMap<>();

        for (int i = 0; i < verticesCount; i++) {
            adjacencyList.add(new HashSet<>());
        }
    }

    /**
     * Добавление ребра между вершинами
     * @param v первая вершина
     * @param w вторая вершина
     */
    public void addEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        if (v == w) {
            throw new IllegalArgumentException("Петли не разрешены");
        }

        // Проверяем, существует ли уже ребро
        Edge edge = new Edge(v, w);
        if (edges.contains(edge)) {
            return; // Ребро уже существует
        }

        // Добавляем ребро
        edges.add(edge);
        edgeMap.put(getKey(v, w), edge);
        adjacencyList.get(v).add(w);
        adjacencyList.get(w).add(v);
    }

    /**
     * Добавление ребра с весом
     * @param v первая вершина
     * @param w вторая вершина
     * @param weight вес ребра
     */
    public void addEdge(int v, int w, double weight) {
        checkVertex(v);
        checkVertex(w);

        if (v == w) {
            throw new IllegalArgumentException("Петли не разрешены");
        }

        Edge edge = new Edge(v, w, weight);

        // Если ребро существует, обновляем вес
        if (edges.contains(edge)) {
            Edge existing = edgeMap.get(getKey(v, w));
            if (existing != null) {
                existing.setWeight(weight);
            }
            return;
        }

        edges.add(edge);
        edgeMap.put(getKey(v, w), edge);
        adjacencyList.get(v).add(w);
        adjacencyList.get(w).add(v);
    }

    /**
     * Удаление ребра между вершинами
     * @param v первая вершина
     * @param w вторая вершина
     * @return true если ребро было удалено
     */
    public boolean removeEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        String key = getKey(v, w);
        Edge edge = edgeMap.get(key);

        if (edge == null) {
            return false;
        }

        edges.remove(edge);
        edgeMap.remove(key);
        adjacencyList.get(v).remove(w);
        adjacencyList.get(w).remove(v);

        return true;
    }

    /**
     * Проверка существования ребра
     * @param v первая вершина
     * @param w вторая вершина
     * @return true если ребро существует
     */
    public boolean hasEdge(int v, int w) {
        checkVertex(v);
        checkVertex(w);
        return adjacencyList.get(v).contains(w);
    }

    /**
     * Получение веса ребра
     * @param v первая вершина
     * @param w вторая вершина
     * @return вес ребра или -1 если ребра нет
     */
    public double getEdgeWeight(int v, int w) {
        if (!hasEdge(v, w)) {
            return -1;
        }
        Edge edge = edgeMap.get(getKey(v, w));
        return edge != null ? edge.getWeight() : 1.0;
    }

    /**
     * Получение списка смежных вершин
     * @param v вершина
     * @return множество смежных вершин (неизменяемое)
     */
    public Set<Integer> getAdjacentVertices(int v) {
        checkVertex(v);
        return Collections.unmodifiableSet(adjacencyList.get(v));
    }

    /**
     * Получение степени вершины
     * @param v вершина
     * @return степень вершины
     */
    public int getDegree(int v) {
        checkVertex(v);
        return adjacencyList.get(v).size();
    }

    /**
     * Получение всех ребер графа
     * @return множество всех ребер
     */
    public Set<Edge> getAllEdges() {
        return Collections.unmodifiableSet(edges);
    }

    /**
     * Получение количества вершин
     */
    public int getVerticesCount() {
        return verticesCount;
    }

    /**
     * Получение количества ребер
     */
    public int getEdgesCount() {
        return edges.size();
    }

    /**
     * Проверка, является ли граф полным
     */
    public boolean isComplete() {
        int maxEdges = verticesCount * (verticesCount - 1) / 2;
        return edges.size() == maxEdges;
    }

    /**
     * Очистка графа (удаление всех ребер)
     */
    public void clear() {
        edges.clear();
        edgeMap.clear();
        for (int i = 0; i < verticesCount; i++) {
            adjacencyList.get(i).clear();
        }
    }

    /**
     * Создание копии графа
     */
    public Graph copy() {
        Graph copy = new Graph(verticesCount);
        for (Edge edge : edges) {
            copy.addEdge(edge.getVertex1(), edge.getVertex2(), edge.getWeight());
        }
        return copy;
    }

    private void checkVertex(int v) {
        if (v < 0 || v >= verticesCount) {
            throw new IllegalArgumentException("Вершина " + v + " вне диапазона [0, " + (verticesCount - 1) + "]");
        }
    }

    private String getKey(int v, int w) {
        int min = Math.min(v, w);
        int max = Math.max(v, w);
        return min + "," + max;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Граф с ").append(verticesCount).append(" вершинами и ").append(edges.size()).append(" ребрами:\n");

        for (int i = 0; i < verticesCount; i++) {
            sb.append(i).append(" -> ");
            if (adjacencyList.get(i).isEmpty()) {
                sb.append("нет смежных вершин");
            } else {
                List<Integer> neighbors = new ArrayList<>(adjacencyList.get(i));
                Collections.sort(neighbors);
                sb.append(neighbors);
            }
            sb.append("\n");
        }

        if (!edges.isEmpty()) {
            sb.append("\nРебра:\n");
            List<Edge> sortedEdges = new ArrayList<>(edges);
            sortedEdges.sort(Comparator.comparingInt(Edge::getVertex1)
                    .thenComparingInt(Edge::getVertex2));
            for (Edge edge : sortedEdges) {
                sb.append("  ").append(edge).append("\n");
            }
        }

        return sb.toString();
    }
}
