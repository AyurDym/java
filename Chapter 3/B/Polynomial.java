import java.util.Arrays;

public class Polynomial {
    private double[] coefficients; // коэффициенты: [a0, a1, a2, ..., an]
    private int degree; // степень полинома

    // Конструктор
    public Polynomial(int degree) {
        this.degree = degree;
        this.coefficients = new double[degree + 1];
        Arrays.fill(coefficients, 0);
    }

    // Конструктор с массивом коэффициентов
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients.clone();
        this.degree = coefficients.length - 1;
        // Убираем ведущие нули
        while (degree > 0 && Math.abs(coefficients[degree]) < 1e-10) {
            degree--;
        }
    }

    // Установка коэффициента
    public void setCoefficient(int power, double value) {
        if (power <= degree) {
            coefficients[power] = value;
        }
    }

    // Получение коэффициента
    public double getCoefficient(int power) {
        if (power <= degree) {
            return coefficients[power];
        }
        return 0;
    }

    // Получение степени
    public int getDegree() {
        return degree;
    }

    // Сложение полиномов
    public Polynomial add(Polynomial other) {
        int maxDegree = Math.max(this.degree, other.degree);
        double[] resultCoef = new double[maxDegree + 1];

        for (int i = 0; i <= this.degree; i++) {
            resultCoef[i] += this.coefficients[i];
        }

        for (int i = 0; i <= other.degree; i++) {
            resultCoef[i] += other.coefficients[i];
        }

        return new Polynomial(resultCoef);
    }

    // Вычисление значения полинома в точке x
    public double evaluate(double x) {
        double result = 0;
        for (int i = degree; i >= 0; i--) {
            result = result * x + coefficients[i];
        }
        return result;
    }

    // Вывод полинома в виде строки
    @Override
    public String toString() {
        if (degree == 0 && Math.abs(coefficients[0]) < 1e-10) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = degree; i >= 0; i--) {
            double coef = coefficients[i];
            if (Math.abs(coef) < 1e-10) continue;

            if (sb.length() > 0 && coef > 0) {
                sb.append(" + ");
            } else if (coef < 0) {
                sb.append(" - ");
                coef = -coef;
            }

            if (i == 0) {
                sb.append(String.format("%.2f", coef));
            } else if (i == 1) {
                sb.append(String.format("%.2f", coef)).append("x");
            } else {
                sb.append(String.format("%.2f", coef)).append("x^").append(i);
            }
        }

        return sb.toString();
    }
}
