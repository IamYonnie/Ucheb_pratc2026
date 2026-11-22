package scene;

import model.EquipmentSet;
import model.LabWork;
import model.Report;

import java.util.List;
import java.util.Map;

// Методические указания
public class MethodologyService {
    public void generate(LabWork work, List<Report> reports) {
        System.out.println();
        System.out.println("МЕТОДИЧЕСКИЕ СВЕДЕНИЯ");
        System.out.println("Работа: " + work.getTitle() + " (" + work.getId() + ")");
        System.out.println("Цель и описание: " + work.getDescription());
        System.out.println("Преподаватель: " + work.getAuthor().getFullName()
                + ", " + work.getAuthor().getPosition()
                + "; " + work.getAuthor().getDepartment());

        System.out.println("Необходимое оборудование и материалы:");
        for (Map.Entry<String, EquipmentSet> e : work.getEquipmentSets().entrySet()) {
            EquipmentSet kit = e.getValue();
            System.out.println("- " + kit.getEquipment().getName()
                    + " (" + kit.getEquipment().getType().getLabel()
                    + "): " + kit.getQuantity() + " шт.");
        }

        System.out.println("Пример данных о результате выполнения:");
        for (Report report : reports) {
            if (!report.getLabWork().getId().equals(work.getId())) {
                continue;
            }
            System.out.println(report.getStudent().getFullName()
                    + ", группа " + report.getStudent().getGroupNumber()
                    + ": отчёт от " + report.getSubmissionDate()
                    + ", оценка " + report.getGrade() + ".");
        }
    }
}
