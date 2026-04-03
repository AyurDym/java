package com.example;

import java.util.*;
import java.util.stream.Collectors;

public class Polynomial {
    private Map<Integer, Double> coefficients; // степень -> коэффициент
    private int degree;

    // Конструктор 1: создание полинома из массива коэффициентов
    public Polynomial(double[] coefficients) {
        this.coefficients = new TreeMap<>(Collections.reverseOrder());
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                this.coefficients.put(i, coefficients[i]);
            }
        }
        updateDegree();
    }

    // Конструктор 2: создание полинома из Map
    public Polynomial(Map<Integer, Double> coefficients) {
        this.coefficients = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Double> entry : coefficients.entrySet()) {
            if (entry.getValue() != 0) {
                this.coefficients.put(entry.getKey(), entry.getValue());
            }
        }
        updateDegree();
    }

    // Конструктор 3: создание полинома с одним членом
    public Polynomial(int degree, double coefficient) {
        this.coefficients = new TreeMap<>(Collections.reverseOrder());
        if (coefficient != 0) {
            this.coefficients.put(degree, coefficient);
        }
        updateDegree();
    }

    // Конструктор 4: копирование
    public Polynomial(Polynomial other) {
        this.coefficients = new TreeMap<>(Collections.reverseOrder());
        this.coefficients.putAll(other.coefficients);
        this.degree = other.degree;
    }

    // Конструктор 5: полином из строки (например: "2x^3 + 4x^2 - 3x + 5")
    public Polynomial(String polynomialString) {
        this.coefficients = new TreeMap<>(Collections.reverseOrder());
        if (polynomialString != null && !polynomialString.trim().isEmpty()) {
            parseFromString(polynomialString);
        }
        updateDegree();
    }

    private void parseFromString(String polynomialString) {
        String normalized = polynomialString.replaceAll("\\s+", "");
        if (normalized.isEmpty()) {
            return;
        }

        String[] terms = normalized.split("(?=[+-])");

        for (String term : terms) {
            if (term == null || term.isEmpty()) continue;

            int sign = 1;
            if (term.startsWith("-")) {
                sign = -1;
                term = term.substring(1);
            } else if (term.startsWith("+")) {
                term = term.substring(1);
            }

            if (term.isEmpty()) continue;

            if (term.contains("x")) {
                String[] parts = term.split("x\\^?");
                double coeff;
                int power;

                if (parts.length == 0 || (parts.length == 1 && parts[0].isEmpty())) {
                    // Просто x или -x
                    coeff = 1;
                    power = 1;
                } else if (parts.length == 1) {
                    // x или -x
                    if (parts[0].isEmpty() || parts[0].equals("+")) {
                        coeff = 1;
                    } else if (parts[0].equals("-")) {
                        coeff = -1;
                    } else {
                        try {
                            coeff = Double.parseDouble(parts[0]);
                        } catch (NumberFormatException e) {
                            coeff = 1;
                        }
                    }
                    power = 1;
                } else {
                    // x^2, 2x^3 и т.д.
                    try {
                        if (parts[0].isEmpty()) {
                            coeff = 1;
                        } else {
                            coeff = Double.parseDouble(parts[0]);
                        }
                        power = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        coeff = 1;
                        power = 1;
                    }
                }
                addTerm(power, sign * coeff);
            } else {
                // свободный член
                try {
                    double coeff = Double.parseDouble(term);
                    addTerm(0, sign * coeff);
                } catch (NumberFormatException e) {
                    // Игнорируем некорректные термины
                }
            }
        }
    }

    private void addTerm(int degree, double coefficient) {
        if (Math.abs(coefficient) < 1e-10) return;
        coefficients.merge(degree, coefficient, Double::sum);
        if (Math.abs(coefficients.get(degree)) < 1e-10) {
            coefficients.remove(degree);
        }
    }

    private void updateDegree() {
        this.degree = coefficients.keySet().stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    public int getDegree() {
        return degree;
    }

    public double getCoefficient(int degree) {
        return coefficients.getOrDefault(degree, 0.0);
    }

    public Map<Integer, Double> getCoefficients() {
        return new HashMap<>(coefficients);
    }

    // Сложение полиномов
    public Polynomial add(Polynomial other) {
        Map<Integer, Double> result = new HashMap<>(this.coefficients);

        for (Map.Entry<Integer, Double> entry : other.coefficients.entrySet()) {
            int degree = entry.getKey();
            double coeff = entry.getValue();
            result.merge(degree, coeff, Double::sum);
        }

        // Удаляем нулевые коэффициенты
        result.entrySet().removeIf(entry -> Math.abs(entry.getValue()) < 1e-10);

        return new Polynomial(result);
    }

    // Вычитание полиномов
    public Polynomial subtract(Polynomial other) {
        Map<Integer, Double> result = new HashMap<>(this.coefficients);

        for (Map.Entry<Integer, Double> entry : other.coefficients.entrySet()) {
            int degree = entry.getKey();
            double coeff = -entry.getValue();
            result.merge(degree, coeff, Double::sum);
        }

        result.entrySet().removeIf(entry -> Math.abs(entry.getValue()) < 1e-10);

        return new Polynomial(result);
    }

    // Умножение полиномов
    public Polynomial multiply(Polynomial other) {
        Map<Integer, Double> result = new HashMap<>();

        for (Map.Entry<Integer, Double> e1 : this.coefficients.entrySet()) {
            for (Map.Entry<Integer, Double> e2 : other.coefficients.entrySet()) {
                int newDegree = e1.getKey() + e2.getKey();
                double newCoeff = e1.getValue() * e2.getValue();
                result.merge(newDegree, newCoeff, Double::sum);
            }
        }

        result.entrySet().removeIf(entry -> Math.abs(entry.getValue()) < 1e-10);

        return new Polynomial(result);
    }

    // Деление полиномов (возвращает частное и остаток)
    public Polynomial[] divide(Polynomial divisor) {
        if (divisor.coefficients.isEmpty()) {
            throw new ArithmeticException("Деление на нулевой полином");
        }

        Polynomial remainder = new Polynomial(this);
        Polynomial quotient = new Polynomial(new double[]{0});

        while (remainder.degree >= divisor.degree && !remainder.coefficients.isEmpty()) {
            int degreeDiff = remainder.degree - divisor.degree;
            double coeffRatio = remainder.getCoefficient(remainder.degree) /
                    divisor.getCoefficient(divisor.degree);

            Polynomial term = new Polynomial(degreeDiff, coeffRatio);
            quotient = quotient.add(term);
            remainder = remainder.subtract(divisor.multiply(term));
        }

        return new Polynomial[]{quotient, remainder};
    }

    // Умножение на скаляр
    public Polynomial multiply(double scalar) {
        Map<Integer, Double> result = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : coefficients.entrySet()) {
            result.put(entry.getKey(), entry.getValue() * scalar);
        }
        return new Polynomial(result);
    }

    // Сложение с числом
    public Polynomial add(double number) {
        return this.add(new Polynomial(0, number));
    }

    // Вычисление значения полинома в точке x
    public double evaluate(double x) {
        double result = 0;
        for (Map.Entry<Integer, Double> entry : coefficients.entrySet()) {
            result += entry.getValue() * Math.pow(x, entry.getKey());
        }
        return result;
    }

    @Override
    public String toString() {
        if (coefficients.isEmpty()) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        List<Map.Entry<Integer, Double>> sortedTerms = coefficients.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getKey(), e1.getKey()))
                .collect(Collectors.toList());

        for (Map.Entry<Integer, Double> entry : sortedTerms) {
            int degree = entry.getKey();
            double coeff = entry.getValue();

            if (Math.abs(coeff) < 1e-10) continue;

            if (!first) {
                sb.append(coeff > 0 ? " + " : " - ");
            } else if (coeff < 0) {
                sb.append("-");
            }

            double absCoeff = Math.abs(coeff);

            if (degree == 0) {
                if (Math.abs(absCoeff - Math.round(absCoeff)) < 1e-10) {
                    sb.append((long) absCoeff);
                } else {
                    sb.append(String.format("%.2f", absCoeff));
                }
            } else if (degree == 1) {
                if (Math.abs(absCoeff - 1) < 1e-10) {
                    sb.append("x");
                } else if (Math.abs(absCoeff - Math.round(absCoeff)) < 1e-10) {
                    sb.append((long) absCoeff).append("x");
                } else {
                    sb.append(String.format("%.2f", absCoeff)).append("x");
                }
            } else {
                if (Math.abs(absCoeff - 1) < 1e-10) {
                    sb.append("x^").append(degree);
                } else if (Math.abs(absCoeff - Math.round(absCoeff)) < 1e-10) {
                    sb.append((long) absCoeff).append("x^").append(degree);
                } else {
                    sb.append(String.format("%.2f", absCoeff)).append("x^").append(degree);
                }
            }

            first = false;
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Polynomial other = (Polynomial) obj;
        return Objects.equals(this.coefficients, other.coefficients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficients);
    }
}