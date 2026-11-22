package model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

// Лабораторная работа

public class LabWork {

    private final String id;
    private final String title;
    private final String description;
    private final Teacher author;

    // Ключ 
    private final Map<String, EquipmentSet> equipment = new LinkedHashMap<>();

    public LabWork(String id, String title, String description, Teacher author) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор работы обязателен");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название работы обязательно");
        }
        if (author == null) {
            throw new IllegalArgumentException("Автор работы должен быть указан");
        }
        this.id = id.trim();
        this.title = title.trim();
        this.description = description == null ? "" : description.trim();
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Teacher getAuthor() {
        return author;
    }

    // Добавляет оборудование к лабораторной работе в указанном количестве.
    public EquipmentSet addEquipment(String kitId, Equipment equipment, int quantity) {
        if (kitId == null || kitId.isBlank()) {
            throw new IllegalArgumentException("Идентификатор комплекта обязателен");
        }
        if (equipment == null) {
            throw new IllegalArgumentException("Оборудование должно быть указано");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        EquipmentSet set = new EquipmentSet(kitId, this.id, equipment, quantity);
        this.equipment.put(equipment.getId(), set);
        return set;
    }

    // Возвращает карту комплектов оборудования для работы.

    public Map<String, EquipmentSet> getEquipmentSets() {
        return Collections.unmodifiableMap(equipment);
    }

    public String toString() {
        return "Лабораторная работа " + id + " «" + title + "»";
    }
}
