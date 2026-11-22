package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Преподаватель

public class Teacher extends Person {

    private final String position;
    private final String department;
    private final List<LabWork> authoredWorks = new ArrayList<>();

    public Teacher(String id, String fullName, String position, String department) {
        super(id, fullName, "");
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("Должность обязательна");
        }
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Кафедра обязательна");
        }
        this.position = position.trim();
        this.department = department.trim();
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public List<LabWork> getAuthoredWorks() {
        return Collections.unmodifiableList(authoredWorks);
    }

    public LabWork authorLabWork(String workId, String title, String description) {
        LabWork work = new LabWork(workId, title, description, this);
        authoredWorks.add(work);
        return work;
    }

    public String describeRole() {
        return position + ", " + department;
    }
}
