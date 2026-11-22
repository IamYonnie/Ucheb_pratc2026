package rules;

import scene.SceneAsset;

import java.util.Collection;

// Правило проверки по имени файла.

public class ByFileNameRule implements AssetCheckRule {

    public CheckResult check(SceneAsset required, Collection<SceneAsset> available) {
        if (required == null) {
            return CheckResult.fail("требуемый ресурс не задан");
        }
        if (available == null || available.isEmpty()) {
            return CheckResult.fail("нет ни одного доступного ресурса");
        }
        for (SceneAsset candidate : available) {
            if (candidate != null
                    && candidate.getFileName().equalsIgnoreCase(required.getFileName())) {
                return CheckResult.ok();
            }
        }
        return CheckResult.fail("отсутствует файл " + required.getFileName());
    }

    public String name() {
        return "Проверка по имени файла";
    }
}
