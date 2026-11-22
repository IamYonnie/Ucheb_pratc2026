package rules;

import scene.SceneAsset;

import java.util.Collection;

// Правило проверки по соответствию типа ресурса расширению файла

public class ByResourceTypeRule implements AssetCheckRule {

    public CheckResult check(SceneAsset required, Collection<SceneAsset> available) {
        if (required == null) {
            return CheckResult.fail("требуемый ресурс не задан");
        }
        if (available == null || available.isEmpty()) {
            return CheckResult.fail("нет ни одного доступного ресурса");
        }
        // Проверка соответствия расширения типу ресурса
        String expectedExt = extensionFor(required.getResourceType());
        String actualExt = extensionOf(required.getFileName());
        if (!expectedExt.equalsIgnoreCase(actualExt)) {
            return CheckResult.fail("тип ресурса " + required.getResourceType().getLabel()
                    + " не соответствует расширению файла " + required.getFileName()
                    + " (ожидается ." + expectedExt + ")");
        }
        // Поиск доступного ресурса с тем же именем файла
        for (SceneAsset candidate : available) {
            if (candidate != null
                    && candidate.getFileName().equalsIgnoreCase(required.getFileName())) {
                return CheckResult.ok();
            }
        }
        return CheckResult.fail("отсутствует файл " + required.getFileName());
    }

    private static String extensionFor(SceneAsset.ResourceType type) {
        return switch (type) {
            case MODEL_3D -> "glb";
            case AR_MARKER -> "png";
        };
    }

    private static String extensionOf(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dot = fileName.lastIndexOf('.');
        return dot < 0 ? "" : fileName.substring(dot + 1);
    }

    public String name() {
        return "Проверка по соответствию типа и расширения";
    }
}
