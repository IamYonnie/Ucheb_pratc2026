package model;

import java.util.Objects;

// Отчёт

public class Report {

    private final String id;
    private final Student student;
    private final LabWork labWork;
    private final String submissionDate;
    private String grade;

    public Report(String id, Student student, LabWork labWork, String submissionDate, String grade) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор отчёта обязателен");
        }
        if (student == null) {
            throw new IllegalArgumentException("Студент должен быть указан");
        }
        if (labWork == null) {
            throw new IllegalArgumentException("Лабораторная работа должна быть указана");
        }
        if (submissionDate == null || submissionDate.isBlank()) {
            throw new IllegalArgumentException("Дата сдачи обязательна");
        }
        this.id = id.trim();
        this.student = student;
        this.labWork = labWork;
        this.submissionDate = submissionDate.trim();
        this.grade = grade == null ? "" : grade.trim();
    }

    public String getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public LabWork getLabWork() {
        return labWork;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? "" : grade.trim();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report r)) return false;
        return id.equals(r.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return student.getFullName() + ", группа " + student.getGroupNumber()
                + ": отчёт от " + submissionDate + ", оценка " + grade + ".";
    }
}
