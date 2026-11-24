package app;

import model.Equipment;
import model.LabWork;
import model.Report;
import model.Student;
import model.Teacher;
import rules.AssetCheckRule;
import rules.ByFileNameRule;
import scene.ArScene;
import scene.MethodologyService;
import scene.SafetyBriefingService;
import scene.SceneAsset;
import scene.SceneObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        // Участники процесса
        Student student = new Student("ST_01", "Горовенко Варвара Дмитриевна",
                "ИКБО-31-24", "gorovenko");
        Teacher teacher = new Teacher("T_01", "Синицын Анатолий Васильевич",
                "Старший преподаватель", "Кафедра игровой индустрии");

        // Лабораторная работа
        LabWork labWork = teacher.authorLabWork("LAB_01", "Тема", "Описание");

        // Оборудование
        Equipment microscope = new Equipment("EQ_01", "Микроскоп", Equipment.Type.INSTRUMENT);
        Equipment slides = new Equipment("EQ_02", "Предметные стёкла", Equipment.Type.CONSUMABLE);
        Equipment reagents = new Equipment("EQ_03", "Реактивы", Equipment.Type.REAGENT);

        // Комплектация
        labWork.addEquipment("KIT_01", microscope, 1);
        labWork.addEquipment("KIT_02", slides, 5);
        labWork.addEquipment("KIT_03", reagents, 1);

        // AR-ресурсы
        List<SceneAsset> arResources = new ArrayList<>();
        arResources.add(new SceneAsset("AR_01", "LAB_01", "microscope.glb",
                SceneAsset.ResourceType.MODEL_3D));
        arResources.add(new SceneAsset("AR_02", "LAB_01", "slides.glb",
                SceneAsset.ResourceType.MODEL_3D));
        arResources.add(new SceneAsset("AR_03", "LAB_01", "marker.png",
                SceneAsset.ResourceType.AR_MARKER));

        // Сцена
        List<SceneObject> objects = new ArrayList<>();
        objects.add(new SceneObject("obj_1", "LAB_01", "EQ_01", 15, 45, 0, 0));
        objects.add(new SceneObject("obj_2", "LAB_01", "EQ_02", 80, 90, 0, 0));

        Map<String, int[]> standardPositions = new TreeMap<>();
        standardPositions.put("obj_1", new int[]{0, 0});
        standardPositions.put("obj_2", new int[]{0, 0});

        ArScene scene = new ArScene(objects, standardPositions);

        // Доступные файлы
        scene.registerAvailableFile("microscope.glb");
        scene.registerAvailableFile("slides.glb");

        // Отчёт
        List<Report> reports = new ArrayList<>();
        reports.add(new Report("01", student, labWork, "2026-11-21", "5"));

        // Каталог оборудования
        Map<String, String> equipmentNames = new LinkedHashMap<>();
        equipmentNames.put(microscope.getId(), microscope.getName());
        equipmentNames.put(slides.getId(), slides.getName());
        equipmentNames.put(reagents.getId(), reagents.getName());

        // Меню
        final String selectedLabId = "LAB_01";
        MethodologyService methodology = new MethodologyService();
        SafetyBriefingService briefing = new SafetyBriefingService();
        Scanner scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8);

        while (true) {
            System.out.println("Информационная система поддержки лабораторных работ");
            System.out.println("1. Проверить комплектность оборудования для лабораторной работы");
            System.out.println("2. Восстановить стандартную расстановку оборудования");
            System.out.println("3. Просмотреть методические указания");
            System.out.println("4. Просмотреть инструктаж");
            System.out.println("5. Покинуть программу");
            System.out.print("Выберите действие (1-4): ");
            String choice;
            if (!scanner.hasNextLine()) {
                System.out.println();
                break;
            }
            choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                // Проверка ресурсов
                List<SceneAsset> required = scene.getRequiredFiles(arResources, selectedLabId);
                AssetCheckRule rule = new ByFileNameRule();
                boolean isComplete = scene.checkAssets(required, rule);
                if (isComplete) {
                    System.out.println("Все необходимое оборудование присутствует в лаборатории.");
                }
            } else if ("2".equals(choice)) {
                // Сброс сцены
                scene.resetScene(selectedLabId);
                System.out.println("Объекты возвращены на исходные позиции.");
                scene.printScene(selectedLabId);
            } else if ("3".equals(choice)) {
                methodology.generate(labWork, reports);
            } else if ("4".equals(choice)) {
                briefing.conduct();
            } else if ("5".equals(choice)) {
                break;
            } else {
                System.out.println("Неверный ввод.");
            }
        }
    }
}