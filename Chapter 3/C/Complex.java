public class Complex {
    double real;
    double imag;

    // Конструкторы
    Complex() {
        this.real = 0;
        this.imag = 0;
    }

    Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    // Сложение
    Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imag + other.imag);
    }

    // Вывод
    public String toString() {
        if (Math.abs(imag) < 0.001) {
            return String.format("%.2f", real);
        } else if (Math.abs(real) < 0.001) {
            return String.format("%.2fi", imag);
        } else if (imag > 0) {
            return String.format("%.2f + %.2fi", real, imag);
        } else {
            return String.format("%.2f - %.2fi", real, -imag);
        }
    }
}
