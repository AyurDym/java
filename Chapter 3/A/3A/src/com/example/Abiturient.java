package com.example;

import java.util.Arrays;
import java.util.Objects;

public class Abiturient {
    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String address;
    private String phone;
    private int[] grades;

    // Конструктор 1: все поля
    public Abiturient(int id, String lastName, String firstName, String patronymic,
                      String address, String phone, int[] grades) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.address = address;
        this.phone = phone;
        this.grades = Arrays.copyOf(grades, grades.length);
    }

    // Конструктор 2: без отчества и телефона
    public Abiturient(int id, String lastName, String firstName,
                      String address, int[] grades) {
        this(id, lastName, firstName, "", address, "не указан", grades);
    }

    // Конструктор 3: только обязательные поля
    public Abiturient(int id, String lastName, String firstName, int[] grades) {
        this(id, lastName, firstName, "", "", "не указан", grades);
    }

    // Конструктор 4: копирования
    public Abiturient(Abiturient other) {
        this(other.id, other.lastName, other.firstName, other.patronymic,
                other.address, other.phone, other.grades);
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int[] getGrades() {
        return Arrays.copyOf(grades, grades.length);
    }

    public void setGrades(int[] grades) {
        this.grades = Arrays.copyOf(grades, grades.length);
    }

    // Дополнительные методы
    public boolean hasUnsatisfactoryGrades() {
        for (int grade : grades) {
            if (grade < 3) {
                return true;
            }
        }
        return false;
    }

    public int getSumGrades() {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return sum;
    }

    public double getAverageGrade() {
        return (double) getSumGrades() / grades.length;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s %s %s | Адрес: %s | Тел: %s | Оценки: %s | Сумма: %d",
                id, lastName, firstName, patronymic, address, phone,
                Arrays.toString(grades), getSumGrades());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Abiturient that = (Abiturient) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}