package com.example;

public class NumberStats {
    private final double sum;
    private final double average;
    private final int count;

    public NumberStats(double sum, double average, int count) {
        this.sum = sum;
        this.average = average;
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format("Статистика:\nКоличество чисел: %d\nСумма: %.6f\nСреднее значение: %.6f",
                count, sum, average);
    }
}