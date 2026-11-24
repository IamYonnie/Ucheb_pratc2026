package model;

import java.util.Objects;

// Базовый класс для всех участников

public abstract class Person {

    private final String id;
    private final String fullName;
    private String contact;

    protected Person(String id, String fullName, String contact) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор участника обязателен");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("ФИО участника обязательно для заполнения");
        }
        this.id = id.trim();
        this.fullName = fullName.trim();
        this.contact = contact == null ? "" : contact.trim();
    }

    public final String getId() {
        return id;
    }

    public final String getFullName() {
        return fullName;
    }

    public String getContact() {
        return contact;
    }

    protected final void setContact(String contact) {
        this.contact = contact == null ? "" : contact.trim();
    }

    // Роль участника
    public abstract String describeRole();

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person p)) return false;
        return id.equals(p.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return describeRole() + " «" + fullName + "» (" + id + ")";
    }
}
