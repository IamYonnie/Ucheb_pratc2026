package model;

// Студент

public class Student extends Person {

    private final String groupNumber;
    private final String login;

    public Student(String id, String fullName, String groupNumber, String login) {
        super(id, fullName, login);
        if (groupNumber == null || groupNumber.isBlank()) {
            throw new IllegalArgumentException("Номер группы обязателен");
        }
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Логин обязателен");
        }
        this.groupNumber = groupNumber.trim();
        this.login = login.trim();
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public String getContact() {
        return login;
    }

    public String describeRole() {
        return "Студент группы " + groupNumber;
    }
}
