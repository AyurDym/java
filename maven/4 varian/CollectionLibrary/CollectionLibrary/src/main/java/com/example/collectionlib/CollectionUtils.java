package com.example.collectionlib;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtils {

    /**
     * Фильтрация коллекции по заданному условию
     */
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (collection == null || predicate == null) {
            throw new IllegalArgumentException("Collection and predicate cannot be null");
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Сортировка коллекции (естественный порядок)
     */
    public static <T extends Comparable<T>> List<T> sort(Collection<T> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection cannot be null");
        }
        List<T> sortedList = new ArrayList<>(collection);
        Collections.sort(sortedList);
        return sortedList;
    }

    /**
     * Сортировка коллекции с компаратором
     */
    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        if (collection == null || comparator == null) {
            throw new IllegalArgumentException("Collection and comparator cannot be null");
        }
        List<T> sortedList = new ArrayList<>(collection);
        sortedList.sort(comparator);
        return sortedList;
    }

    /**
     * Поиск максимального элемента
     */
    public static <T extends Comparable<T>> T findMax(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection cannot be null or empty");
        }
        return Collections.max(collection);
    }

    /**
     * Поиск минимального элемента
     */
    public static <T extends Comparable<T>> T findMin(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection cannot be null or empty");
        }
        return Collections.min(collection);
    }

    /**
     * Объединение двух коллекций
     */
    public static <T> List<T> union(Collection<T> collection1, Collection<T> collection2) {
        if (collection1 == null || collection2 == null) {
            throw new IllegalArgumentException("Collections cannot be null");
        }
        List<T> result = new ArrayList<>();
        result.addAll(collection1);
        result.addAll(collection2);
        return result;
    }

    /**
     * Разбиение коллекции на части указанного размера
     */
    public static <T> List<List<T>> partition(Collection<T> collection, int chunkSize) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection cannot be null");
        }
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("Chunk size must be positive");
        }

        List<List<T>> partitions = new ArrayList<>();
        List<T> items = new ArrayList<>(collection);
        int size = items.size();

        for (int i = 0; i < size; i += chunkSize) {
            partitions.add(new ArrayList<>(
                    items.subList(i, Math.min(size, i + chunkSize))
            ));
        }

        return partitions;
    }

    /**
     * Разбиение коллекции на две части по условию
     */
    public static <T> Map<Boolean, List<T>> splitByPredicate(Collection<T> collection, Predicate<T> predicate) {
        if (collection == null || predicate == null) {
            throw new IllegalArgumentException("Collection and predicate cannot be null");
        }

        return collection.stream()
                .collect(Collectors.partitioningBy(predicate));
    }
}