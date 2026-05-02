package service;

import model.Graph;
import model.Edge;
import java.util.*;

public class PathFinder {

    /**
     * Поиск кратчайшего пути (BFS для невзвешенного графа)
     */
    public List<Integer> findShortestPath(Graph graph, int start, int end) {
        if (start == end) {
            return Collections.singletonList(start);
        }

        int n = graph.getVerticesCount();
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor : graph.getAdjacentVertices(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;

                    if (neighbor == end) {
                        return reconstructPath(parent, start, end);
                    }
                    queue.add(neighbor);
                }
            }
        }

        return Collections.emptyList(); // Путь не найден
    }

    /**
     * Поиск всех путей между вершинами (с ограничением по длине)
     */
    public List<List<Integer>> findAllPaths(Graph graph, int start, int end, int maxLength) {
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[graph.getVerticesCount()];

        currentPath.add(start);
        dfsFindPaths(graph, start, end, maxLength, visited, currentPath, paths);

        return paths;
    }

    private void dfsFindPaths(Graph graph, int current, int end, int maxLength,
                              boolean[] visited, List<Integer> currentPath,
                              List<List<Integer>> paths) {
        if (currentPath.size() > maxLength) {
            return;
        }

        if (current == end && currentPath.size() > 1) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        visited[current] = true;

        for (int neighbor : graph.getAdjacentVertices(current)) {
            if (!visited[neighbor]) {
                currentPath.add(neighbor);
                dfsFindPaths(graph, neighbor, end, maxLength, visited, currentPath, paths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited[current] = false;
    }

    /**
     * Поиск эйлерова цикла (если существует)
     */
    public List<Integer> findEulerianCycle(Graph graph) {
        // Проверка: все вершины должны иметь четную степень
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (graph.getDegree(i) % 2 != 0) {
                return null; // Эйлеров цикл не существует
            }
        }

        // Алгоритм Флери (упрощенная версия)
        Graph tempGraph = graph.copy();
        List<Integer> cycle = new ArrayList<>();
        int startVertex = 0;

        // Находим первую вершину со степенью > 0
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (graph.getDegree(i) > 0) {
                startVertex = i;
                break;
            }
        }

        findCycle(tempGraph, startVertex, cycle);
        return cycle;
    }

    private void findCycle(Graph graph, int start, List<Integer> cycle) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int vertex = stack.peek();

            if (graph.getDegree(vertex) > 0) {
                // Берем любого соседа
                int neighbor = graph.getAdjacentVertices(vertex).iterator().next();
                stack.push(neighbor);
                graph.removeEdge(vertex, neighbor);
            } else {
                cycle.add(stack.pop());
            }
        }
    }

    private List<Integer> reconstructPath(int[] parent, int start, int end) {
        List<Integer> path = new ArrayList<>();
        int current = end;

        while (current != -1) {
            path.add(current);
            current = parent[current];
        }

        Collections.reverse(path);
        return path;
    }
}