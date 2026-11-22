package scene;

import java.util.Objects;

// AR-ресурс
public class SceneAsset {
    public enum ResourceType {
        MODEL_3D("3D-модель"),
        AR_MARKER("AR-маркер");

        private final String label;

        ResourceType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static ResourceType fromLabel(String label) {
            if (label == null) {
                throw new IllegalArgumentException("Тип ресурса не задан");
            }
            for (ResourceType t : values()) {
                if (t.label.equalsIgnoreCase(label.trim())) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Неизвестный тип ресурса: " + label);
        }
    }

    private final String id;
    private final String labWorkId;
    private final String fileName;
    private final ResourceType resourceType;

    public SceneAsset(String id, String labWorkId, String fileName, ResourceType resourceType) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Идентификатор ресурса обязателен");
        }
        if (labWorkId == null || labWorkId.isBlank()) {
            throw new IllegalArgumentException("Идентификатор работы обязателен");
        }
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Имя файла обязательно");
        }
        if (resourceType == null) {
            throw new IllegalArgumentException("Тип ресурса обязателен");
        }
        this.id = id.trim();
        this.labWorkId = labWorkId.trim();
        this.fileName = fileName.trim();
        this.resourceType = resourceType;
    }

    public String getId() {
        return id;
    }

    public String getLabWorkId() {
        return labWorkId;
    }

    public String getFileName() {
        return fileName;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SceneAsset a)) return false;
        return id.equalsIgnoreCase(a.id);
    }

    public int hashCode() {
        return Objects.hash(id.toLowerCase());
    }

    public String toString() {
        return fileName + " [" + resourceType.getLabel() + "]";
    }
}
