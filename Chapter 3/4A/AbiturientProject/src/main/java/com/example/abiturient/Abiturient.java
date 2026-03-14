package com.example.abiturient;

import java.util.Arrays;

public class Abiturient {
    // Поля класса
    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String address;
    private String phone;
    private int[] grades;

    // Конструктор по умолчанию
    public Abiturient() {
        this.id = 0;
        this.lastName = "Не указано";
        this.firstName = "Не указано";
        this.patronymic = "Не указано";
        this.address = "Не указан";
        this.phone = "Не указан";
        this.grades = new int[0];
    }

    // Конструктор с основными параметрами
    public Abiturient(int id, String lastName, String firstName, String patronymic) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.address = "Не указан";
        this.phone = "Не указан";
        this.grades = new int[0];
    }

    // Полный конструктор
    public Abiturient(int id, String lastName, String firstName, String patronymic,
                      String address, String phone, int[] grades) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.address = address;
        this.phone = phone;
        this.grades = grades;
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
        return grades;
    }

    public void setGrades(int[] grades) {
        this.grades = grades;
    }

    // Метод для получения суммы баллов
    public int getSumGrades() {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return sum;
    }

    // Метод для проверки наличия неудовлетворительных оценок (< 4)
    public boolean hasUnsatisfactoryGrades() {
        for (int grade : grades) {
            if (grade < 4) {
                return true;
            }
        }
        return false;
    }

    // Метод toString
    @Override
    public String toString() {
        return String.format("ID: %d | %s %s %s | Адрес: %s | Тел: %s | Оценки: %s | Сумма: %d",
                id, lastName, firstName, patronymic, address, phone,
                Arrays.toString(grades), getSumGrades());
    }
}
