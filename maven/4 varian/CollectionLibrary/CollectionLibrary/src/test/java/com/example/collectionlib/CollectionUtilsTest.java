package com.example.collectionlib;

import com.example.collectionlib.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilsTest {

    private List<Integer> numbers;
    private List<String> strings;
    private List<Person> persons;
    private List<Integer> emptyList;

    @BeforeEach
    void setUp() {
        numbers = Arrays.asList(5, 2, 8, 1, 9, 3);
        strings = Arrays.asList("banana", "apple", "cherry", "date");
        persons = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 20)
        );
        emptyList = new ArrayList<>();
    }

    // Тесты для filter
    @Test
    void testFilter() {
        List<Integer> evenNumbers = CollectionUtils.filter(numbers, n -> n % 2 == 0);
        assertEquals(Arrays.asList(2, 8), evenNumbers);

        List<Integer> greaterThanFive = CollectionUtils.filter(numbers, n -> n > 5);
        assertEquals(Arrays.asList(8, 9), greaterThanFive);
    }

    @Test
    void testFilterWithNullCollection() {
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.filter(null, n -> true));
    }

    @Test
    void testFilterWithNullPredicate() {
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.filter(numbers, null));
    }

    // Тесты для sort
    @Test
    void testSort() {
        List<Integer> sortedNumbers = CollectionUtils.sort(numbers);
        assertEquals(Arrays.asList(1, 2, 3, 5, 8, 9), sortedNumbers);

        List<String> sortedStrings = CollectionUtils.sort(strings);
        assertEquals(Arrays.asList("apple", "banana", "cherry", "date"), sortedStrings);
    }

    @Test
    void testSortWithComparator() {
        List<String> sortedByLength = CollectionUtils.sort(strings,
                Comparator.comparingInt(String::length));
        assertEquals(Arrays.asList("date", "apple", "banana", "cherry"), sortedByLength);

        List<Person> sortedByAge = CollectionUtils.sort(persons,
                Comparator.comparingInt(Person::getAge));
        assertEquals(20, sortedByAge.get(0).getAge());
        assertEquals(25, sortedByAge.get(1).getAge());
        assertEquals(30, sortedByAge.get(2).getAge());
    }

    @Test
    void testSortWithNullCollection() {
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.sort(null));
    }

    // Тесты для findMax
    @Test
    void testFindMax() {
        assertEquals(Integer.valueOf(9), CollectionUtils.findMax(numbers));
        assertEquals("date", CollectionUtils.findMax(strings));
    }

    @Test
    void testFindMaxWithEmptyCollection() {
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.findMax(emptyList));
    }

    // Тесты для findMin
    @Test
    void testFindMin() {
        assertEquals(Integer.valueOf(1), CollectionUtils.findMin(numbers));
        assertEquals("apple", CollectionUtils.findMin(strings));
    }

    @Test
    void testFindMinWithEmptyCollection() {
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.findMin(emptyList));
    }

    // Тесты для union
    @Test
    void testUnion() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);

        List<Integer> union = CollectionUtils.union(list1, list2);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), union);
    }

    @Test
    void testUnionWithNull() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);

        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.union(list1, null));
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.union(null, list1));
    }

    // Тесты для partition
    @Test
    void testPartition() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        List<List<Integer>> partitions = CollectionUtils.partition(numbers, 3);
        assertEquals(3, partitions.size());
        assertEquals(Arrays.asList(1, 2, 3), partitions.get(0));
        assertEquals(Arrays.asList(4, 5, 6), partitions.get(1));
        assertEquals(Arrays.asList(7, 8), partitions.get(2));
    }

    @Test
    void testPartitionWithInvalidChunkSize() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.partition(numbers, 0));
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.partition(numbers, -1));
    }

    // Тесты для splitByPredicate
    @Test
    void testSplitByPredicate() {
        Map<Boolean, List<Integer>> split = CollectionUtils.splitByPredicate(
                numbers, n -> n % 2 == 0);

        assertEquals(Arrays.asList(2, 8), split.get(true));
        assertEquals(Arrays.asList(5, 1, 9, 3), split.get(false));
    }

    @Test
    void testSplitByPredicateWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.splitByPredicate(null, n -> true));
        assertThrows(IllegalArgumentException.class,
                () -> CollectionUtils.splitByPredicate(numbers, null));
    }
}
