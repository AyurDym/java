package service;

import model.Graph;
import model.Edge;
import java.util.*;

public class GraphService {

    /**
     * Получение матрицы смежности
     */
    public int[][] getAdjacencyMatrix(Graph graph) {
        int n = graph.getVerticesCount();
        int[][] matrix = new int[n][n];

        for (Edge edge : graph.getAllEdges()) {
            matrix[edge.getVertex1()][edge.getVertex2()] = 1;
            matrix[edge.getVertex2()][edge.getVertex1()] = 1;
        }

        return matrix;
    }

    /**
     * Получение списка всех вершин в порядке возрастания степени
     */
    public List<Integer> getVerticesSortedByDegree(Graph graph) {
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            vertices.add(i);
        }

        vertices.sort((a, b) -> Integer.compare(graph.getDegree(b), graph.getDegree(a)));
        return vertices;
    }

    /**
     * Проверка, является ли граф связным
     */
    public boolean isConnected(Graph graph) {
        if (graph.getVerticesCount() == 0) return true;

        boolean[] visited = new boolean[graph.getVerticesCount()];
        dfs(graph, 0, visited);

        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }

    /**
     * Поиск компонент связности
     */
    public List<Set<Integer>> findConnectedComponents(Graph graph) {
        boolean[] visited = new boolean[graph.getVerticesCount()];
        List<Set<Integer>> components = new ArrayList<>();

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (!visited[i]) {
                Set<Integer> component = new HashSet<>();
                dfsCollect(graph, i, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private void dfs(Graph graph, int vertex, boolean[] visited) {
        visited[vertex] = true;
        for (int neighbor : graph.getAdjacentVertices(vertex)) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor, visited);
            }
        }
    }

    private void dfsCollect(Graph graph, int vertex, boolean[] visited, Set<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);
        for (int neighbor : graph.getAdjacentVertices(vertex)) {
            if (!visited[neighbor]) {
                dfsCollect(graph, neighbor, visited, component);
            }
        }
    }

    /**
     * Получение статистики графа
     */
    public GraphStatistics getStatistics(Graph graph) {
        GraphStatistics stats = new GraphStatistics();
        stats.verticesCount = graph.getVerticesCount();
        stats.edgesCount = graph.getEdgesCount();

        int maxDegree = 0;
        int minDegree = Integer.MAX_VALUE;
        int totalDegree = 0;

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            int degree = graph.getDegree(i);
            totalDegree += degree;
            maxDegree = Math.max(maxDegree, degree);
            minDegree = Math.min(minDegree, degree);
        }

        stats.averageDegree = (double) totalDegree / graph.getVerticesCount();
        stats.maxDegree = maxDegree;
        stats.minDegree = minDegree;
        stats.isConnected = isConnected(graph);
        stats.componentsCount = findConnectedComponents(graph).size();
        stats.isComplete = graph.isComplete();

        return stats;
    }

    public static class GraphStatistics {
        public int verticesCount;
        public int edgesCount;
        public double averageDegree;
        public int maxDegree;
        public int minDegree;
        public boolean isConnected;
        public int componentsCount;
        public boolean isComplete;

        @Override
        public String toString() {
            return String.format(
                    "Статистика графа:\n" +
                            "  Вершин: %d\n" +
                            "  Ребер: %d\n" +
                            "  Средняя степень: %.2f\n" +
                            "  Максимальная степень: %d\n" +
                            "  Минимальная степень: %d\n" +
                            "  Связный: %s\n" +
                            "  Компонент связности: %d\n" +
                            "  Полный: %s",
                    verticesCount, edgesCount, averageDegree, maxDegree, minDegree,
                    isConnected ? "да" : "нет", componentsCount, isComplete ? "да" : "нет"
            );
        }
    }
}
