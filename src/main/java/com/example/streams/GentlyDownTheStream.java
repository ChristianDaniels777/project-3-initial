package com.example.streams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Enhanced coding kata on the Stream API with exception handling, generics, and advanced concepts.
 */
public class GentlyDownTheStream {

    protected List<String> fruits;
    protected List<String> veggies;
    protected List<Integer> integerValues;

    public GentlyDownTheStream() {
        fruits = Arrays.asList("Apple", "Orange", "Banana", "Pear", "Peach", "Tomato");
        veggies = Arrays.asList("Corn", "Potato", "Carrot", "Pea", "Tomato");
        integerValues = new Random().ints(0, 1001)
                .boxed()
                .limit(1000)
                .collect(Collectors.toList());
    }

    /**
     * Returns sorted fruits with proper validation
     */
    public List<String> sortedFruits() throws InvalidDataException, EmptyCollectionException {
        validateCollection(fruits, "Fruits collection");

        return fruits.stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Filters out fruits starting with "A" then sorts
     */
    public List<String> sortedFruitsException() throws InvalidDataException {
        return sortedFruitsWithFilter(fruit -> !fruit.startsWith("A"));
    }

    /**
     * First 2 elements of sorted fruits
     */
    public List<String> sortedFruitsFirstTwo() throws InvalidDataException {
        try {
            validateCollection(fruits, "Fruits collection");

            return fruits.stream()
                    .filter(Objects::nonNull)
                    .sorted()
                    .limit(2)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new InvalidDataException("Failed to get first two sorted fruits: " + e.getMessage());
        }
    }

    /**
     * Comma-separated sorted fruits
     */
    public String commaSeparatedListOfFruits() throws InvalidDataException {
        try {
            validateCollection(fruits, "Fruits collection");

            return fruits.stream()
                    .filter(Objects::nonNull)
                    .sorted()
                    .collect(Collectors.joining(", "));

        } catch (Exception e) {
            throw new InvalidDataException("Failed to create comma separated fruit list: " + e.getMessage());
        }
    }

    /**
     * Reverse sorted veggies
     */
    public List<String> reverseSortedVeggies() throws InvalidDataException {
        try {
            validateCollection(veggies, "Veggies collection");

            return veggies.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new InvalidDataException("Failed to reverse sort veggies: " + e.getMessage());
        }
    }

    /**
     * Reverse sorted veggies in uppercase
     */
    public List<String> reverseSortedVeggiesInUpperCase() throws InvalidDataException {
        try {
            validateCollection(veggies, "Veggies collection");

            return veggies.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new InvalidDataException("Failed to reverse sort uppercase veggies: " + e.getMessage());
        }
    }

    /**
     * Top 10 values
     */
    public List<Integer> topTen() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");

            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .limit(10)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new InvalidDataException("Failed to get top ten values: " + e.getMessage());
        }
    }

    /**
     * Top 10 unique values
     */
    public List<Integer> topTenUnique() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");

            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted(Comparator.reverseOrder())
                    .limit(10)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new InvalidDataException("Failed to get top ten unique values: " + e.getMessage());
        }
    }

    /**
     * Top 10 unique odd values
     */
    public List<Integer> topTenUniqueOdd() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");

            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .filter(n -> n % 2 != 0)
                    .distinct()
                    .sorted(Comparator.reverseOrder())
                    .limit(10)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new InvalidDataException("Failed to get top ten unique odd values: " + e.getMessage());
        }
    }

    /**
     * Average of all numbers
     */
    public Double average() throws InvalidDataException {
        try {
            validateCollection(integerValues, "Integer values collection");

            return safeAverage(integerValues)
                    .orElseThrow(() -> new InvalidDataException("No valid numbers available for average"));

        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Failed to calculate average: " + e.getMessage());
        }
    }

    /**
     * Validation helper
     */
    private <T> void validateCollection(Collection<T> collection, String name)
            throws EmptyCollectionException {

        if (collection == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }

        if (collection.isEmpty()) {
            throw new EmptyCollectionException(name + " cannot be empty");
        }
    }

    /**
     * Generic helper for sorting + filtering
     */
    private <T> List<T> sortedWithFilter(Collection<T> collection,
                                         Predicate<T> filter,
                                         Comparator<T> comparator) throws InvalidDataException {
        try {
            validateCollection(collection, "Input collection");

            return collection.stream()
                    .filter(Objects::nonNull)
                    .filter(filter)
                    .sorted(comparator)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new InvalidDataException("Failed to sort and filter collection: " + e.getMessage());
        }
    }

    private List<String> sortedFruitsWithFilter(Predicate<String> filter) throws InvalidDataException {
        return sortedWithFilter(fruits, filter, String::compareTo);
    }

    /**
     * Safe average helper
     */
    private OptionalDouble safeAverage(Collection<Integer> numbers) {
        return numbers.stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average();
    }
}
