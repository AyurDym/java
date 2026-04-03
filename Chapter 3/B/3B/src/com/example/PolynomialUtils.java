package com.example;

import java.util.*;

public class PolynomialUtils {

    // Сумма массива полиномов
    public static Polynomial sumOfPolynomials(Polynomial[] polynomials) {
        if (polynomials == null || polynomials.length == 0) {
            return new Polynomial(new double[]{0});
        }

        Polynomial sum = new Polynomial(polynomials[0]);
        for (int i = 1; i < polynomials.length; i++) {
            sum = sum.add(polynomials[i]);
        }
        return sum;
    }

    // Сумма списка полиномов
    public static Polynomial sumOfPolynomials(List<Polynomial> polynomials) {
        if (polynomials == null || polynomials.isEmpty()) {
            return new Polynomial(new double[]{0});
        }

        Polynomial sum = new Polynomial(polynomials.get(0));
        for (int i = 1; i < polynomials.size(); i++) {
            sum = sum.add(polynomials.get(i));
        }
        return sum;
    }

    // Сумма множества полиномов
    public static Polynomial sumOfPolynomials(Set<Polynomial> polynomials) {
        if (polynomials == null || polynomials.isEmpty()) {
            return new Polynomial(new double[]{0});
        }

        Iterator<Polynomial> iterator = polynomials.iterator();
        Polynomial sum = new Polynomial(iterator.next());
        while (iterator.hasNext()) {
            sum = sum.add(iterator.next());
        }
        return sum;
    }

    // Произведение массива полиномов
    public static Polynomial productOfPolynomials(Polynomial[] polynomials) {
        if (polynomials == null || polynomials.length == 0) {
            return new Polynomial(new double[]{1});
        }

        Polynomial product = new Polynomial(polynomials[0]);
        for (int i = 1; i < polynomials.length; i++) {
            product = product.multiply(polynomials[i]);
        }
        return product;
    }

    // Создание полинома с заданными корнями
    public static Polynomial fromRoots(double... roots) {
        Polynomial result = new Polynomial(new double[]{1});
        for (double root : roots) {
            Polynomial linear = new Polynomial(new double[]{-root, 1});
            result = result.multiply(linear);
        }
        return result;
    }

    // Проверка, являются ли два полинома равными с заданной точностью
    public static boolean equalsWithPrecision(Polynomial p1, Polynomial p2, double epsilon) {
        if (p1.getDegree() != p2.getDegree()) return false;

        for (int i = 0; i <= p1.getDegree(); i++) {
            if (Math.abs(p1.getCoefficient(i) - p2.getCoefficient(i)) > epsilon) {
                return false;
            }
        }
        return true;
    }

    // Печать массива полиномов
    public static void printPolynomials(Polynomial[] polynomials, String title) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(title);
        System.out.println("=".repeat(80));

        for (int i = 0; i < polynomials.length; i++) {
            System.out.printf("P%d(x) = %s%n", i + 1, polynomials[i]);
        }
    }

    // Печать списка полиномов
    public static void printPolynomials(List<Polynomial> polynomials, String title) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(title);
        System.out.println("=".repeat(80));

        for (int i = 0; i < polynomials.size(); i++) {
            System.out.printf("P%d(x) = %s%n", i + 1, polynomials.get(i));
        }
    }
}