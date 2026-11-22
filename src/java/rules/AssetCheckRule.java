package rules;

import scene.SceneAsset;

import java.util.Collection;

// Правило проверки готовности AR-ресурса.

public interface AssetCheckRule {

    // Проверка, удовлетворяет ли требуемый ресурс доступному

    CheckResult check(SceneAsset required, Collection<SceneAsset> available);

    String name();

    final class CheckResult {

        private final boolean ok;
        private final String reason;

        private CheckResult(boolean ok, String reason) {
            this.ok = ok;
            this.reason = reason;
        }

        public static CheckResult ok() {
            return new CheckResult(true, "");
        }

        public static CheckResult fail(String reason) {
            return new CheckResult(false, reason == null ? "" : reason);
        }

        public boolean isOk() {
            return ok;
        }

        public String getReason() {
            return reason;
        }
    }
}
