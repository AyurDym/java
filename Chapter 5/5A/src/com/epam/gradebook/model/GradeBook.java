package com.epam.gradebook.model;

import java.util.ArrayList;
import java.util.List;

public class GradeBook {
    private Student student;
    private List<Session> sessions;
    private int currentSessionIndex;

    public GradeBook(Student student) {
        this.student = student;
        this.sessions = new ArrayList<>();
        this.currentSessionIndex = -1;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        sessions.add(session);
        currentSessionIndex = sessions.size() - 1;
    }

    public Session getCurrentSession() {
        if (currentSessionIndex >= 0 && currentSessionIndex < sessions.size()) {
            return sessions.get(currentSessionIndex);
        }
        return null;
    }

    public void setCurrentSession(int index) {
        if (index >= 0 && index < sessions.size()) {
            currentSessionIndex = index;
        }
    }

    public double calculateAverageScore() {
        int totalScore = 0;
        int examCount = 0;

        for (Session session : sessions) {
            for (Session.Record record : session.getRecords()) {
                if (record instanceof Session.Exam) {
                    totalScore += ((Session.Exam) record).getScore();
                    examCount++;
                }
            }
        }

        return examCount > 0 ? (double) totalScore / examCount : 0;
    }

    public List<Session.Record> getDebts() {
        List<Session.Record> debts = new ArrayList<>();

        for (Session session : sessions) {
            for (Session.Record record : session.getRecords()) {
                if (record instanceof Session.Exam) {
                    Session.Exam exam = (Session.Exam) record;
                    if (exam.getScore() < 3) {
                        debts.add(exam);
                    }
                } else if (record instanceof Session.Credit) {
                    Session.Credit credit = (Session.Credit) record;
                    if (!credit.isPassed()) {
                        debts.add(credit);
                    }
                }
            }
        }

        return debts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Зачетная книжка\n");
        sb.append(student.toString()).append("\n");
        sb.append("Количество сессий: ").append(sessions.size()).append("\n");

        for (int i = 0; i < sessions.size(); i++) {
            sb.append("\n").append(sessions.get(i).toString());
            if (i == currentSessionIndex) {
                sb.append(" (текущая)");
            }
        }

        return sb.toString();
    }

    // Внутренний класс Session
    public class Session {
        private int semester;
        private String academicYear;
        private List<Record> records;

        public Session(int semester, String academicYear) {
            this.semester = semester;
            this.academicYear = academicYear;
            this.records = new ArrayList<>();
        }

        public int getSemester() {
            return semester;
        }

        public String getAcademicYear() {
            return academicYear;
        }

        public List<Record> getRecords() {
            return records;
        }

        public void addRecord(Record record) {
            records.add(record);
        }

        public double getSessionAverageScore() {
            int totalScore = 0;
            int examCount = 0;

            for (Record record : records) {
                if (record instanceof Exam) {
                    totalScore += ((Exam) record).getScore();
                    examCount++;
                }
            }

            return examCount > 0 ? (double) totalScore / examCount : 0;
        }

        public int getExamCount() {
            int count = 0;
            for (Record record : records) {
                if (record instanceof Exam) {
                    count++;
                }
            }
            return count;
        }

        public int getCreditCount() {
            int count = 0;
            for (Record record : records) {
                if (record instanceof Credit) {
                    count++;
                }
            }
            return count;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Сессия ").append(semester).append(" семестр (")
                    .append(academicYear).append(")\n");
            sb.append("Экзаменов: ").append(getExamCount())
                    .append(", Зачетов: ").append(getCreditCount())
                    .append(", Средний балл: ").append(String.format("%.2f", getSessionAverageScore()))
                    .append("\n");

            for (Record record : records) {
                sb.append("  ").append(record.toString()).append("\n");
            }

            return sb.toString();
        }

        // Абстрактный внутренний класс Record
        public abstract class Record {
            protected String subject;

            public Record(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }

            public abstract String getType();
            public abstract boolean isPassed();

            @Override
            public abstract String toString();
        }

        // Внутренний класс Exam (экзамен)
        public class Exam extends Record {
            private int score; // 2-5

            public Exam(String subject, int score) {
                super(subject);
                this.score = score;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            @Override
            public String getType() {
                return "Экзамен";
            }

            @Override
            public boolean isPassed() {
                return score >= 3;
            }

            @Override
            public String toString() {
                return getType() + ": " + subject + " - оценка: " + score +
                        " (" + (isPassed() ? "сдан" : "не сдан") + ")";
            }
        }

        // Внутренний класс Credit (зачет)
        public class Credit extends Record {
            private boolean passed;

            public Credit(String subject, boolean passed) {
                super(subject);
                this.passed = passed;
            }

            public Credit(String subject, String status) {
                super(subject);
                this.passed = status.equalsIgnoreCase("зачет");
            }

            public boolean isPassed() {
                return passed;
            }

            public void setPassed(boolean passed) {
                this.passed = passed;
            }

            @Override
            public String getType() {
                return "Зачет";
            }

            @Override
            public String toString() {
                return getType() + ": " + subject + " - " +
                        (passed ? "ЗАЧЕТ" : "НЕЗАЧЕТ");
            }
        }
    }
}