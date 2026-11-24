package scene;

import rules.AssetCheckRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// AR-сцена
public class ArScene {
    // Доступные на устройстве AR-ресурсы
    private final List<String> availableFiles = new ArrayList<>();
    // Объекты на сцене
    private final List<SceneObject> objects;

    // Эталонные позиции для сброса
    private final Map<String, int[]> standardPositions;

    public ArScene(List<SceneObject> objects, Map<String, int[]> standardPositions) {
        this.objects = new ArrayList<>(objects);
        this.standardPositions = new TreeMap<>();
        for (var e : standardPositions.entrySet()) {
            this.standardPositions.put(e.getKey(), e.getValue().clone());
        }
    }

    public void registerAvailableFile(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Имя файла обязательно");
        }
        availableFiles.add(fileName.trim());
    }

    public List<String> getAvailableFiles() {
        return Collections.unmodifiableList(availableFiles);
    }

    public List<String> getRequiredFiles(List<SceneAsset> resources, String labWorkId) {
        List<String> names = new ArrayList<>();
        for (SceneAsset r : resources) {
            if (r.getLabWorkId().equals(labWorkId)) {
                names.add(r.getFileName());
            }
        }
        return names;
    }

    // 1. Проверка наличия AR-ресурсов
    public boolean checkAssets(List<SceneAsset> required, AssetCheckRule rule) {
        boolean isComplete = true;
        for (SceneAsset req : required) {
            AssetCheckRule.CheckResult res = rule.check(req, asAssets(availableFiles));
            if (!res.isOk()) {
                System.out.println("Отсутствуют необходимые файлы: " + req.getFileName());
                isComplete = false;
            }
        }
        return isComplete;
    }

    // 2. Сброс сцены
    public int resetScene(String labWorkId) {
        int restoredCount = 0;
        for (SceneObject obj : objects) {
            if (!obj.getLabWorkId().equals(labWorkId)) {
                continue;
            }
            int[] reference = standardPositions.get(obj.getId());
            if (reference == null) {
                System.out.println("Нет исходной позиции для объекта " + obj.getId() + ".");
                continue;
            }
            obj.restoreTo(reference[0], reference[1]);
            restoredCount++;
        }
        return restoredCount;
    }

    public List<SceneObject> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    public void printScene(String labWorkId) {
        for (SceneObject obj : objects) {
            if (!obj.getLabWorkId().equals(labWorkId)) {
                continue;
            }
            System.out.println("Объект " + obj.getId() + ": X=" + obj.getPosX() + ", Y=" + obj.getPosY());
        }
    }

    private static Collection<SceneAsset> asAssets(List<String> fileNames) {
        List<SceneAsset> result = new ArrayList<>();
        for (String name : fileNames) {
            result.add(new SceneAsset("available", "LAB_01", name, inferType(name)));
        }
        return result;
    }

    private static SceneAsset.ResourceType inferType(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
        if ("png".equalsIgnoreCase(ext)) {
            return SceneAsset.ResourceType.AR_MARKER;
        }
        return SceneAsset.ResourceType.MODEL_3D;
    }
}
