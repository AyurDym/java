public class Abiturient {
    int id;
    String lastName;
    String firstName;
    String patronymic;
    String address;
    String phone;
    int[] grades;

    // Конструктор
    Abiturient(int id, String lastName, String firstName, String patronymic,
               String address, String phone, int[] grades) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.address = address;
        this.phone = phone;
        this.grades = grades;
    }

    // Проверка на неудовлетворительные оценки (2 и ниже)
    boolean hasUnsatisfactory() {
        for (int g : grades) {
            if (g <= 2) return true;
        }
        return false;
    }

    // Сумма баллов
    int getSum() {
        int sum = 0;
        for (int g : grades) sum += g;
        return sum;
    }

    // Вывод информации
    void print() {
        System.out.print(id + " | " + lastName + " " + firstName + " " + patronymic);
        System.out.print(" | " + address + " | " + phone + " | Оценки: ");
        for (int g : grades) System.out.print(g + " ");
        System.out.println("| Сумма: " + getSum());
    }
}
