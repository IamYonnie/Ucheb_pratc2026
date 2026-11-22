package scene;

import java.util.Objects;

// Объект на сцене
public class SceneObject {

    private final String id;
    private final String labWorkId;
    private final String equipmentId;
    private int posX;
    private int posY;
    private final int initialX;
    private final int initialY;

    public SceneObject(String id, String labWorkId, String equipmentId,
                       int posX, int posY, int initialX, int initialY) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор объекта обязателен");
        }
        if (labWorkId == null || labWorkId.isBlank()) {
            throw new IllegalArgumentException("Идентификатор работы обязателен");
        }
        if (equipmentId == null || equipmentId.isBlank()) {
            throw new IllegalArgumentException("Идентификатор оборудования обязателен");
        }
        this.id = id.trim();
        this.labWorkId = labWorkId.trim();
        this.equipmentId = equipmentId.trim();
        this.posX = posX;
        this.posY = posY;
        this.initialX = initialX;
        this.initialY = initialY;
    }

    public String getId() {
        return id;
    }

    public String getLabWorkId() {
        return labWorkId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }
    // Восстановление координат
    public void restoreTo(int stdPosX, int stdPosY) {
        this.posX = stdPosX;
        this.posY = stdPosY;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SceneObject s)) return false;
        return id.equals(s.id) && labWorkId.equals(s.labWorkId);
    }

    public int hashCode() {
        return Objects.hash(id, labWorkId);
    }

    public String toString() {
        return id + ": X=" + posX + ", Y=" + posY;
    }
}
