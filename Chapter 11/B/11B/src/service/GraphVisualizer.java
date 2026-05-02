package service;

import model.Graph;
import model.Edge;

import java.util.*;

public class GraphVisualizer {

    /**
     * Вывод графа в консоль в виде текстового представления
     */
    public void printGraph(Graph graph) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ГРАФ: " + graph.getVerticesCount() + " вершин, " + graph.getEdgesCount() + " ребер");
        System.out.println("=".repeat(60));

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            Set<Integer> neighbors = graph.getAdjacentVertices(i);
            if (!neighbors.isEmpty()) {
                System.out.printf("Вершина %2d | Степень: %2d | Связи: %s\n",
                        i, graph.getDegree(i), neighbors);
            } else {
                System.out.printf("Вершина %2d | Степень: %2d | Изолированная вершина\n",
                        i, graph.getDegree(i));
            }
        }
        System.out.println("=".repeat(60));
    }

    /**
     * Вывод матрицы смежности
     */
    public void printAdjacencyMatrix(Graph graph) {
        int n = graph.getVerticesCount();

        System.out.println("\nМАТРИЦА СМЕЖНОСТИ:");
        System.out.print("    ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%3d ", i);
        }
        System.out.println();
        System.out.print("    ");
        for (int i = 0; i < n; i++) {
            System.out.print("----");
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < n; j++) {
                if (graph.hasEdge(i, j)) {
                    System.out.printf("  %d ", 1);
                } else {
                    System.out.printf("  %d ", 0);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Вывод списка смежности
     */
    public void printAdjacencyList(Graph graph) {
        System.out.println("\nСПИСОК СМЕЖНОСТИ:");
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            Set<Integer> neighbors = graph.getAdjacentVertices(i);
            if (!neighbors.isEmpty()) {
                System.out.printf("%d -> %s\n", i, neighbors);
            } else {
                System.out.printf("%d -> (нет смежных вершин)\n", i);
            }
        }
        System.out.println();
    }

    /**
     * Визуализация графа в виде ASCII-арта (для небольших графов)
     */
    public void drawAsciiGraph(Graph graph) {
        System.out.println("\nASCII-ВИЗУАЛИЗАЦИЯ ГРАФА:");

        int n = graph.getVerticesCount();
        if (n > 10) {
            System.out.println("Граф слишком большой для ASCII-визуализации (макс. 10 вершин)");
            return;
        }

        // Создаем позиции вершин по кругу
        double radius = 10;
        double centerX = 20;
        double centerY = 10;

        double[][] positions = new double[n][2];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n - Math.PI / 2;
            positions[i][0] = centerX + radius * Math.cos(angle);
            positions[i][1] = centerY + radius * Math.sin(angle);
        }

        // Создаем сетку символов
        int width = 50;
        int height = 25;
        char[][] canvas = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(canvas[i], ' ');
        }

        // Рисуем ребра
        for (Edge edge : graph.getAllEdges()) {
            int v1 = edge.getVertex1();
            int v2 = edge.getVertex2();
            drawLine(canvas, positions[v1][0], positions[v1][1],
                    positions[v2][0], positions[v2][1]);
        }

        // Рисуем вершины
        for (int i = 0; i < n; i++) {
            int x = (int) Math.round(positions[i][0]);
            int y = (int) Math.round(positions[i][1]);
            if (x >= 0 && x < width && y >= 0 && y < height) {
                canvas[y][x] = (char) ('0' + i);
            }
        }

        // Выводим результат
        for (int i = 0; i < height; i++) {
            System.out.println(new String(canvas[i]));
        }
        System.out.println();
    }

    /**
     * Визуализация графа с использованием библиотеки Graphviz (если установлена)
     */
    public void generateGraphvizDot(Graph graph, String graphName) {
        System.out.println("\nGRA PHVIZ DOT КОД:");
        System.out.println("```dot");
        System.out.println("graph " + graphName + " {");
        System.out.println("    layout=neato;");
        System.out.println("    node [shape=circle, style=filled, fillcolor=lightblue];");

        // Вершины
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            System.out.println("    " + i + " [label=\"" + i + "\"];");
        }

        // Ребра
        for (Edge edge : graph.getAllEdges()) {
            double weight = edge.getWeight();
            if (weight != 1.0) {
                System.out.printf("    %d -- %d [label=\"%.2f\"];\n",
                        edge.getVertex1(), edge.getVertex2(), weight);
            } else {
                System.out.printf("    %d -- %d;\n", edge.getVertex1(), edge.getVertex2());
            }
        }

        System.out.println("}");
        System.out.println("```");
        System.out.println("Скопируйте этот код в https://dreampuf.github.io/GraphvizOnline/ для визуализации");
    }

    /**
     * Простая текстовая визуализация для дерева/цепочек
     */
    public void drawSimpleGraph(Graph graph) {
        System.out.println("\nПРОСТАЯ ВИЗУАЛИЗАЦИЯ:");

        int n = graph.getVerticesCount();
        boolean[][] matrix = new boolean[n][n];

        for (Edge edge : graph.getAllEdges()) {
            matrix[edge.getVertex1()][edge.getVertex2()] = true;
            matrix[edge.getVertex2()][edge.getVertex1()] = true;
        }

        // Находим компоненты связности
        GraphService service = new GraphService();
        List<Set<Integer>> components = service.findConnectedComponents(graph);

        for (int compIdx = 0; compIdx < components.size(); compIdx++) {
            System.out.println("\nКомпонента " + (compIdx + 1) + ":");
            Set<Integer> component = components.get(compIdx);
            List<Integer> vertices = new ArrayList<>(component);

            if (vertices.size() <= 6) {
                // Для маленьких компонент рисуем компактно
                drawCompactComponent(matrix, vertices);
            } else {
                // Для больших компонент показываем список
                System.out.println("  Вершины: " + vertices);
                System.out.println("  Ребра:");
                for (Edge edge : graph.getAllEdges()) {
                    if (component.contains(edge.getVertex1()) && component.contains(edge.getVertex2())) {
                        System.out.printf("    %d --- %d\n", edge.getVertex1(), edge.getVertex2());
                    }
                }
            }
        }
    }

    private void drawCompactComponent(boolean[][] matrix, List<Integer> vertices) {
        // Сортируем вершины
        Collections.sort(vertices);
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            indexMap.put(vertices.get(i), i);
        }

        // Создаем матрицу компоненты
        int size = vertices.size();
        char[][] display = new char[size * 2 + 1][size * 4];

        // Инициализация
        for (int i = 0; i < display.length; i++) {
            Arrays.fill(display[i], ' ');
        }

        // Рисуем вершины
        for (int i = 0; i < size; i++) {
            int x = i * 4;
            int y = size;
            display[y][x] = (char) ('0' + vertices.get(i));
        }

        // Рисуем ребра
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int v1 = vertices.get(i);
                int v2 = vertices.get(j);
                if (matrix[v1][v2]) {
                    int x1 = i * 4;
                    int x2 = j * 4;
                    int y1 = size;
                    int y2 = size;

                    if (Math.abs(x2 - x1) > 1) {
                        for (int x = x1 + 1; x < x2; x++) {
                            if (display[y1][x] == ' ') display[y1][x] = '-';
                        }
                    }
                    // Рисуем соединение
                    if (display[y1][x1 + 1] == ' ') display[y1][x1 + 1] = '-';
                    if (display[y1][x2 - 1] == ' ') display[y1][x2 - 1] = '-';
                }
            }
        }

        // Вывод
        for (char[] row : display) {
            System.out.println("  " + new String(row));
        }
    }

    /**
     * Рисование линии на ASCII-холсте (алгоритм Брезенхема)
     */
    private void drawLine(char[][] canvas, double x1, double y1, double x2, double y2) {
        int ix1 = (int) Math.round(x1);
        int iy1 = (int) Math.round(y1);
        int ix2 = (int) Math.round(x2);
        int iy2 = (int) Math.round(y2);

        int dx = Math.abs(ix2 - ix1);
        int dy = Math.abs(iy2 - iy1);
        int sx = ix1 < ix2 ? 1 : -1;
        int sy = iy1 < iy2 ? 1 : -1;
        int err = dx - dy;

        int x = ix1, y = iy1;

        while (true) {
            if (x >= 0 && x < canvas[0].length && y >= 0 && y < canvas.length) {
                if (canvas[y][x] == ' ') {
                    canvas[y][x] = '.';
                }
            }

            if (x == ix2 && y == iy2) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
    }

    /**
     * Вывод подробной информации о графе
     */
    public void printDetailedInfo(Graph graph) {
        GraphService service = new GraphService();
        GraphService.GraphStatistics stats = service.getStatistics(graph);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("ДЕТАЛЬНАЯ ИНФОРМАЦИЯ О ГРАФЕ");
        System.out.println("=".repeat(70));

        System.out.println("\n【ОСНОВНЫЕ ХАРАКТЕРИСТИКИ】");
        System.out.println(stats);

        System.out.println("\n【РАСПРЕДЕЛЕНИЕ СТЕПЕНЕЙ】");
        Map<Integer, Integer> degreeDistribution = new HashMap<>();
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            int degree = graph.getDegree(i);
            degreeDistribution.put(degree, degreeDistribution.getOrDefault(degree, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : degreeDistribution.entrySet()) {
            System.out.printf("  Степень %2d: %2d вершин(а)%s\n",
                    entry.getKey(), entry.getValue(),
                    entry.getKey() == 0 ? " (изолированные)" : "");
        }

        System.out.println("\n【СПИСОК ВЕРШИН】");
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            double avgNeighborDegree = 0;
            Set<Integer> neighbors = graph.getAdjacentVertices(i);
            if (!neighbors.isEmpty()) {
                avgNeighborDegree = neighbors.stream()
                        .mapToDouble(graph::getDegree)
                        .average()
                        .orElse(0);
            }
            System.out.printf("  Вершина %2d | Степень: %2d | Ср. степень соседей: %.2f\n",
                    i, graph.getDegree(i), avgNeighborDegree);
        }

        System.out.println("\n【РЕБРА】");
        List<Edge> sortedEdges = new ArrayList<>(graph.getAllEdges());
        sortedEdges.sort(Comparator.comparingInt(Edge::getVertex1)
                .thenComparingInt(Edge::getVertex2));

        for (Edge edge : sortedEdges) {
            System.out.printf("  (%2d, %2d) | Вес: %.2f\n",
                    edge.getVertex1(), edge.getVertex2(), edge.getWeight());
        }

        System.out.println("\n" + "=".repeat(70));
    }

    /**
     * Сравнение двух графов
     */
    public void compareGraphs(Graph g1, Graph g2) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("СРАВНЕНИЕ ГРАФОВ");
        System.out.println("=".repeat(60));

        System.out.printf("%-30s | %-30s\n", "ХАРАКТЕРИСТИКА", "ЗНАЧЕНИЕ");
        System.out.println("-".repeat(60));

        System.out.printf("%-30s | %-30s\n", "Граф 1:", "Граф 2:");
        System.out.printf("%-30s | %-30s\n", "Вершин: " + g1.getVerticesCount(),
                "Вершин: " + g2.getVerticesCount());
        System.out.printf("%-30s | %-30s\n", "Ребер: " + g1.getEdgesCount(),
                "Ребер: " + g2.getEdgesCount());

        GraphService service = new GraphService();
        boolean isConnected1 = service.isConnected(g1);
        boolean isConnected2 = service.isConnected(g2);
        System.out.printf("%-30s | %-30s\n", "Связный: " + (isConnected1 ? "да" : "нет"),
                "Связный: " + (isConnected2 ? "да" : "нет"));

        System.out.printf("%-30s | %-30s\n", "Полный: " + (g1.isComplete() ? "да" : "нет"),
                "Полный: " + (g2.isComplete() ? "да" : "нет"));

        // Вычисляем плотность
        double density1 = 2.0 * g1.getEdgesCount() / (g1.getVerticesCount() * (g1.getVerticesCount() - 1));
        double density2 = 2.0 * g2.getEdgesCount() / (g2.getVerticesCount() * (g2.getVerticesCount() - 1));
        System.out.printf("%-30s | %-30s\n",
                String.format("Плотность: %.3f", density1),
                String.format("Плотность: %.3f", density2));

        System.out.println("\n【РАСПРЕДЕЛЕНИЕ СТЕПЕНЕЙ】");
        System.out.printf("%-15s | %-20s | %-20s\n", "Степень", "Граф 1", "Граф 2");
        System.out.println("-".repeat(60));

        Map<Integer, Integer> dist1 = getDegreeDistribution(g1);
        Map<Integer, Integer> dist2 = getDegreeDistribution(g2);

        Set<Integer> allDegrees = new HashSet<>(dist1.keySet());
        allDegrees.addAll(dist2.keySet());

        for (int degree : allDegrees.stream().sorted().toArray(Integer[]::new)) {
            System.out.printf("%-15d | %-20s | %-20s\n",
                    degree,
                    dist1.getOrDefault(degree, 0) + " вершин",
                    dist2.getOrDefault(degree, 0) + " вершин");
        }

        System.out.println("=".repeat(60));
    }

    private Map<Integer, Integer> getDegreeDistribution(Graph graph) {
        Map<Integer, Integer> distribution = new HashMap<>();
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            int degree = graph.getDegree(i);
            distribution.put(degree, distribution.getOrDefault(degree, 0) + 1);
        }
        return distribution;
    }
}
