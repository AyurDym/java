package com.epam.gradebook.model;

public class Student {
    private String name;
    private String studentId;
    private String faculty;
    private String group;

    public Student(String name, String studentId, String faculty) {
        this.name = name;
        this.studentId = studentId;
        this.faculty = faculty;
    }

    public Student(String name, String studentId, String faculty, String group) {
        this(name, studentId, faculty);
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Студент: ").append(name)
                .append("\nНомер студенческого: ").append(studentId)
                .append("\nФакультет: ").append(faculty);
        if (group != null && !group.isEmpty()) {
            sb.append("\nГруппа: ").append(group);
        }
        return sb.toString();
    }
}