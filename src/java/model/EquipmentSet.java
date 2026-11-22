package model;

// Комплектация 
public class EquipmentSet {

    private final String id;
    private final String labWorkId;
    private final Equipment equipment;
    private int quantity;

    public EquipmentSet(String id, String labWorkId, Equipment equipment, int quantity) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор комплекта обязателен");
        }
        if (labWorkId == null || labWorkId.isBlank()) {
            throw new IllegalArgumentException("Идентификатор работы обязателен");
        }
        if (equipment == null) {
            throw new IllegalArgumentException("Оборудование должно быть указано");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        this.id = id.trim();
        this.labWorkId = labWorkId.trim();
        this.equipment = equipment;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getLabWorkId() {
        return labWorkId;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        this.quantity = quantity;
    }

    public String toString() {
        return equipment + ": " + quantity + " шт.";
    }
}
