package rules;

import scene.SceneAsset;

import java.util.Collection;

// Правило проверки по имени файла.

public class ByFileNameRule implements AssetCheckRule {

    public CheckResult check(SceneAsset required, Collection<String> available) {
        if (required == null) {
            return CheckResult.fail("требуемый ресурс не задан");
        }
        if (available == null || available.isEmpty()) {
            return CheckResult.fail("нет ни одного доступного ресурса");
        }
        for (String candidate : available) {
            if (candidate != null && candidate.equalsIgnoreCase(required.getFileName())) {
                return CheckResult.ok();
            }
        }
        return CheckResult.fail("отсутствует файл " + required.getFileName());
    }

    public String name() {
        return "Проверка по имени файла";
    }
}
