import model.Graph;
import model.Edge;
import service.GraphService;
import service.PathFinder;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ РАБОТЫ ГРАФА ===\n");

        // Создаем граф с 6 вершинами
        Graph graph = new Graph(6);

        System.out.println("1. Создан пустой граф с 6 вершинами");
        System.out.println(graph);

        // Добавляем ребра
        System.out.println("\n2. Добавляем ребра:");
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        System.out.println("Добавлено 8 ребер");

        // Добавляем ребро с весом
        graph.addEdge(0, 3, 2.5);
        System.out.println("Добавлено ребро (0-3) с весом 2.5");

        System.out.println("\n3. Текущее состояние графа:");
        System.out.println(graph);

        // Проверка существования ребер
        System.out.println("\n4. Проверка существования ребер:");
        System.out.println("  Ребро (0-1) существует: " + graph.hasEdge(0, 1));
        System.out.println("  Ребро (0-5) существует: " + graph.hasEdge(0, 5));
        System.out.println("  Вес ребра (0-3): " + graph.getEdgeWeight(0, 3));

        // Степени вершин
        System.out.println("\n5. Степени вершин:");
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            System.out.println("  Вершина " + i + ": степень " + graph.getDegree(i));
        }

        // Удаление ребра
        System.out.println("\n6. Удаление ребра (1-3):");
        graph.removeEdge(1, 3);
        System.out.println("После удаления:");
        System.out.println("  Ребро (1-3) существует: " + graph.hasEdge(1, 3));
        System.out.println("  Степень вершины 1: " + graph.getDegree(1));
        System.out.println("  Степень вершины 3: " + graph.getDegree(3));

        // Поиск пути
        System.out.println("\n7. Поиск кратчайшего пути (BFS):");
        PathFinder pathFinder = new PathFinder();
        List<Integer> path = pathFinder.findShortestPath(graph, 0, 5);
        System.out.println("  Путь от 0 до 5: " + path);

        // Все пути
        System.out.println("\n8. Поиск всех путей от 0 до 5 (макс. длина 6):");
        List<List<Integer>> allPaths = pathFinder.findAllPaths(graph, 0, 5, 6);
        for (int i = 0; i < allPaths.size(); i++) {
            System.out.println("  Путь " + (i + 1) + ": " + allPaths.get(i));
        }

        // Статистика
        System.out.println("\n9. Статистика графа:");
        GraphService graphService = new GraphService();
        GraphService.GraphStatistics stats = graphService.getStatistics(graph);
        System.out.println(stats);

        // Компоненты связности
        System.out.println("\n10. Компоненты связности:");
        List<Set<Integer>> components = graphService.findConnectedComponents(graph);
        for (int i = 0; i < components.size(); i++) {
            System.out.println("  Компонента " + (i + 1) + ": " + components.get(i));
        }

        // Демонстрация быстрого добавления/удаления
        System.out.println("\n11. Тест производительности операций:");
        testPerformance();

        // Дополнительные операции
        System.out.println("\n12. Дополнительные операции:");
        System.out.println("  Количество вершин: " + graph.getVerticesCount());
        System.out.println("  Количество ребер: " + graph.getEdgesCount());
        System.out.println("  Полный граф? " + (graph.isComplete() ? "да" : "нет"));

        // Смежные вершины
        System.out.println("\n13. Смежные вершины:");
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            Set<Integer> neighbors = graph.getAdjacentVertices(i);
            if (!neighbors.isEmpty()) {
                System.out.println("  Вершина " + i + " смежна с: " + neighbors);
            }
        }

        // Копирование графа
        System.out.println("\n14. Копирование графа:");
        Graph copy = graph.copy();
        copy.addEdge(0, 5);
        System.out.println("  В копию добавлено ребро (0-5)");
        System.out.println("  Оригинал имеет ребро (0-5): " + graph.hasEdge(0, 5));
        System.out.println("  Копия имеет ребро (0-5): " + copy.hasEdge(0, 5));

        System.out.println("\n=== ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА ===");
    }

    private static void testPerformance() {
        int size = 1000;
        Graph largeGraph = new Graph(size);

        // Добавление ребер
        long startTime = System.nanoTime();
        for (int i = 0; i < size - 1; i++) {
            largeGraph.addEdge(i, i + 1);
        }
        long endTime = System.nanoTime();
        System.out.printf("  Добавление %d ребер: %.3f мс\n", size - 1, (endTime - startTime) / 1_000_000.0);

        // Проверка существования ребра
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            largeGraph.hasEdge(0, size - 1);
        }
        endTime = System.nanoTime();
        System.out.printf("  1000 проверок существования ребра: %.3f мс\n", (endTime - startTime) / 1_000_000.0);

        // Удаление ребер
        startTime = System.nanoTime();
        for (int i = 0; i < size / 2; i++) {
            largeGraph.removeEdge(i, i + 1);
        }
        endTime = System.nanoTime();
        System.out.printf("  Удаление %d ребер: %.3f мс\n", size / 2, (endTime - startTime) / 1_000_000.0);
    }
}