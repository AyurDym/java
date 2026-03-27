package com.epam.gradebook.service;

import com.epam.gradebook.model.GradeBook;
import com.epam.gradebook.model.Student;

public class GradeBookService {
    private GradeBook gradeBook;

    public GradeBookService(GradeBook gradeBook) {
        this.gradeBook = gradeBook;
    }

    public void addNewSession(int semester, String academicYear) {
        GradeBook.Session session = gradeBook.new Session(semester, academicYear);
        gradeBook.addSession(session);
    }

    public boolean addExamToCurrentSession(String subject, int score) {
        GradeBook.Session currentSession = gradeBook.getCurrentSession();
        if (currentSession == null) {
            return false;
        }

        GradeBook.Session.Exam exam = currentSession.new Exam(subject, score);
        currentSession.addRecord(exam);
        return true;
    }

    public boolean addCreditToCurrentSession(String subject, String status) {
        GradeBook.Session currentSession = gradeBook.getCurrentSession();
        if (currentSession == null) {
            return false;
        }

        GradeBook.Session.Credit credit = currentSession.new Credit(subject, status);
        currentSession.addRecord(credit);
        return true;
    }

    public boolean addCreditToCurrentSession(String subject, boolean passed) {
        GradeBook.Session currentSession = gradeBook.getCurrentSession();
        if (currentSession == null) {
            return false;
        }

        GradeBook.Session.Credit credit = currentSession.new Credit(subject, passed);
        currentSession.addRecord(credit);
        return true;
    }

    public void displayAllSessions() {
        if (gradeBook.getSessions().isEmpty()) {
            System.out.println("Нет добавленных сессий");
            return;
        }

        System.out.println(gradeBook.toString());
    }

    public void displayCurrentSession() {
        GradeBook.Session currentSession = gradeBook.getCurrentSession();
        if (currentSession == null) {
            System.out.println("Нет активной сессии");
            return;
        }

        System.out.println(currentSession.toString());
    }

    public double calculateAverageScore() {
        return gradeBook.calculateAverageScore();
    }

    public void displayDebts() {
        var debts = gradeBook.getDebts();
        if (debts.isEmpty()) {
            System.out.println("Нет задолженностей! Отличная работа!");
            return;
        }

        System.out.println("Список задолженностей:");
        for (var debt : debts) {
            System.out.println("  " + debt.toString());
        }
        System.out.println("Всего задолженностей: " + debts.size());
    }

    public GradeBook getGradeBook() {
        return gradeBook;
    }

    public Student getStudent() {
        return gradeBook.getStudent();
    }
}
