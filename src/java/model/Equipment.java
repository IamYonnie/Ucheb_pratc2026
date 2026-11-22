package model;

import java.util.Objects;

// Оборудование

public class Equipment {

    // Тип оборудования
    public enum Type {
        INSTRUMENT("Прибор"),
        CONSUMABLE("Расходный материал"),
        REAGENT("Реактив");

        private final String label;

        Type(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        // Возвращает тип
        public static Type fromLabel(String label) {
            if (label == null) {
                throw new IllegalArgumentException("Тип оборудования не задан");
            }
            for (Type t : values()) {
                if (t.label.equalsIgnoreCase(label.trim())) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Неизвестный тип оборудования: " + label);
        }
    }

    private final String id;
    private final String name;
    private final Type type;

    public Equipment(String id, String name, Type type) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор оборудования обязателен");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название оборудования обязательно");
        }
        if (type == null) {
            throw new IllegalArgumentException("Тип оборудования обязателен");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment e)) return false;
        return id.equals(e.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return name + " (" + type.getLabel() + ")";
    }
}
