
using System;
using System.Collections.Generic;
using System.Linq;

namespace ARLabApp
{
    internal static class Program
    {
        private static void Main(string[] args)
        {
            var students = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string>
                {
                    { "id", "ST_01" }, { "full_name", "Горовенко Варвара Дмитриевна" },
                    { "group", "ИКБО-31-24" }, { "login", "gorovenko" }
                }
            };

            var teachers = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string>
                {
                    { "id", "T_01" }, { "full_name", "Синицын Анатолий Васильевич" },
                    { "position", "Старший преподаватель" }, { "department", "Кафедра игровой индустрии" }
                }
            };

            var labWorks = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string>
                {
                    { "id", "LAB_01" }, { "teacher_id", "T_01" },
                    { "title", "Тема" },
                    { "description", "Описание" }
                }
            };

            var equipment = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string> { { "id", "EQ_01" }, { "name", "Микроскоп" }, { "type", "Прибор" } },
                new Dictionary<string, string> { { "id", "EQ_02" }, { "name", "Предметные стёкла" }, { "type", "Расходный материал" } },
                new Dictionary<string, string> { { "id", "EQ_03" }, { "name", "Реактивы" }, { "type", "Реактив" } }
            };

            var kits = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string> { { "id", "KIT_01" }, { "lab_work_id", "LAB_01" }, { "equipment_id", "EQ_01" }, { "quantity", "1" } },
                new Dictionary<string, string> { { "id", "KIT_02" }, { "lab_work_id", "LAB_01" }, { "equipment_id", "EQ_02" }, { "quantity", "5" } },
                new Dictionary<string, string> { { "id", "KIT_03" }, { "lab_work_id", "LAB_01" }, { "equipment_id", "EQ_03" }, { "quantity", "1" } }
            };

            var reports = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string>
                {
                    { "id", "01" }, { "student_id", "ST_01" },
                    { "lab_work_id", "LAB_01" }, { "submission_date", "2026-11-21" },
                    { "grade", "5" }
                }
            };

            var arResources = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string> { { "id", "AR_01" }, { "lab_work_id", "LAB_01" }, { "file_name", "microscope.glb" }, { "resource_type", "3D-модель" } },
                new Dictionary<string, string> { { "id", "AR_02" }, { "lab_work_id", "LAB_01" }, { "file_name", "slides.glb" }, { "resource_type", "3D-модель" } },
                new Dictionary<string, string> { { "id", "AR_03" }, { "lab_work_id", "LAB_01" }, { "file_name", "marker.png" }, { "resource_type", "AR-маркер" } }
            };

            var scene = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string>
                {
                    { "id", "OBJ_01" }, { "lab_work_id", "LAB_01" }, { "equipment_id", "EQ_01" },
                    { "pos_x", "15" }, { "pos_y", "45" }, { "initial_x", "0" }, { "initial_y", "0" }
                },
                new Dictionary<string, string>
                {
                    { "id", "OBJ_02" }, { "lab_work_id", "LAB_01" }, { "equipment_id", "EQ_02" },
                    { "pos_x", "80" }, { "pos_y", "90" }, { "initial_x", "50" }, { "initial_y", "0" }
                }
            };

            var standardPositions = new List<Dictionary<string, string>>
            {
                new Dictionary<string, string> { { "id", "OBJ_01" }, { "pos_x", "0" }, { "pos_y", "0" } },
                new Dictionary<string, string> { { "id", "OBJ_02" }, { "pos_x", "50" }, { "pos_y", "0" } }
            };

            string[] availableFiles = { "microscope.glb", "slides.glb" };
            const string selectedLabId = "LAB_01";

            while (true)
            {
                Console.WriteLine("Информационная система поддержки лабораторных работ");
                Console.WriteLine("1. Проверить комплектность оборудования для лабораторной работы");
                Console.WriteLine("2. Восстановить стандартную расстановку оборудования");
                Console.WriteLine("3. Просмотреть методические указания");
                Console.WriteLine("4. Просмотреть инструктаж");
                Console.WriteLine("5. Покинуть программу");
                Console.Write("Выберите действие (1-4): ");
                string choice = Console.ReadLine();

                if (choice == "1")
                {
                    var requiredFiles = GetRequiredARFiles(arResources, selectedLabId);
                    bool isComplete = CheckARAssets(requiredFiles, availableFiles);
                    if (isComplete)
                    {
                        Console.WriteLine("Все необходимое оборудование присутствует в лаборатории.");
                    }
                    else
                    {
                        Console.WriteLine("Комплектность не полная. Проведение работы невозможно.");
                    }
                }
                else if (choice == "2")
                {
                    Console.WriteLine("Исходные координаты объектов перед восстановлением:");
                    PrintScene(scene, equipment, selectedLabId);
                    int restoredCount = ResetARScene(scene, standardPositions, selectedLabId);
                    Console.WriteLine($"Восстановлено объектов: {restoredCount}.");
                    Console.WriteLine("Координаты после восстановления:");
                    PrintScene(scene, equipment, selectedLabId);
                    Console.WriteLine("Сцена восстановлена (для найденных объектов конфигурации).");
                }
                else if (choice == "3")
                {
                    GenerateMethodology(selectedLabId, labWorks, teachers, kits, equipment,
                        students, reports);
                }
                else if (choice == "4")
                {
                            ConductSafetyBriefing();
                }
                else if (choice == "5")
                {
                    break;
                }
                else
                {
                    Console.WriteLine("Неверный ввод.");
                }
            }
        }

        // Список требуемых файлов строится из записей AR-ресурсов
        private static string[] GetRequiredARFiles(
            List<Dictionary<string, string>> resources, string labWorkId)
        {
            var names = new List<string>();
            foreach (var resource in resources)
            {
                if (resource["lab_work_id"] == labWorkId)
                    names.Add(resource["file_name"]);
            }
            return names.ToArray();
        }

        // 1. Проверка ресурсов
        private static bool CheckARAssets(string[] required, string[] available)
        {
            var availableSet = new HashSet<string>(available, StringComparer.OrdinalIgnoreCase);
            bool isComplete = true;

            for (int i = 0; i < required.Length; i++)
            {
                if (!availableSet.Contains(required[i]))
                {
                    Console.WriteLine($"Отсутствуют необходимые файлы: {required[i]}");
                    isComplete = false;
                }
            }

            return isComplete;
        }

        // 2. Сброс сцены
        private static int ResetARScene(
            List<Dictionary<string, string>> scene,
            List<Dictionary<string, string>> standardPositions,
            string labWorkId)
        {
            int restoredCount = 0;
            for (int i = 0; i < scene.Count; i++)
            {
                if (scene[i]["lab_work_id"] != labWorkId)
                    continue;

                bool found = false;
                for (int j = 0; j < standardPositions.Count; j++)
                {
                    if (scene[i]["id"] == standardPositions[j]["id"])
                    {
                        scene[i]["pos_x"] = standardPositions[j]["pos_x"];
                        scene[i]["pos_y"] = standardPositions[j]["pos_y"];
                        found = true;
                        restoredCount++;
                        break;
                    }
                }

                if (!found)
                    Console.WriteLine($"Нет исходной позиции для объекта {scene[i]["id"]}.");
            }
            return restoredCount;
        }

        private static void PrintScene(
            List<Dictionary<string, string>> scene,
            List<Dictionary<string, string>> equipment,
            string labWorkId)
        {
            foreach (var obj in scene)
            {
                if (obj["lab_work_id"] != labWorkId)
                    continue;

                var item = equipment.Find(e => e["id"] == obj["equipment_id"]);
                string equipmentName = item != null ? item["name"] : "Неизвестное оборудование";
                Console.WriteLine($"{obj["id"]}: {equipmentName}, X={obj["pos_x"]}, Y={obj["pos_y"]}");
            }
        }

        // 3. Генерация методички
        private static void GenerateMethodology(
            string labWorkId,
            List<Dictionary<string, string>> labWorks,
            List<Dictionary<string, string>> teachers,
            List<Dictionary<string, string>> kits,
            List<Dictionary<string, string>> equipment,
            List<Dictionary<string, string>> students,
            List<Dictionary<string, string>> reports)
        {
            var lab = labWorks.Find(x => x["id"] == labWorkId);
            if (lab == null)
            {
                Console.WriteLine("Лабораторная работа не найдена.");
                return;
            }

            var teacher = teachers.Find(x => x["id"] == lab["teacher_id"]);
            Console.WriteLine("\nМЕТОДИЧЕСКИЕ СВЕДЕНИЯ");
            Console.WriteLine($"Работа: {lab["title"]} ({lab["id"]})");
            Console.WriteLine($"Цель и описание: {lab["description"]}");
            if (teacher != null)
                Console.WriteLine($"Преподаватель: {teacher["full_name"]}, {teacher["position"]}; {teacher["department"]}");

            Console.WriteLine("Необходимое оборудование и материалы:");
            foreach (var kit in kits)
            {
                if (kit["lab_work_id"] != labWorkId)
                    continue;
                var item = equipment.Find(x => x["id"] == kit["equipment_id"]);
                if (item != null)
                    Console.WriteLine($"- {item["name"]} ({item["type"]}): {kit["quantity"]} шт.");
            }

            Console.WriteLine("Пример данных о результате выполнения:");
            foreach (var report in reports)
            {
                if (report["lab_work_id"] != labWorkId)
                    continue;
                var student = students.Find(x => x["id"] == report["student_id"]);
                if (student != null)
                    Console.WriteLine($"{student["full_name"]}, группа {student["group"]}: " +
                        $"отчёт от {report["submission_date"]}, оценка {report["grade"]}.");
            }
        }

        // 4. Проведение инструктажа
        private static void ConductSafetyBriefing()
        {
            Console.WriteLine("ИНСТРУКТАЖ ПО ТЕХНИКЕ БЕЗОПАСНОСТИ");
            Console.WriteLine("1. Запрещается работать с устройством во влажной среде и при повреждении корпуса.");
            Console.WriteLine("2. Запрещается оставлять устройство без присмотра на зарядке и в местах нагрева.");
            Console.WriteLine("3. Запрещается использование устройства более двух часов подряд.");
            Console.WriteLine("4. При поломке устройство не вскрывать.");
        }
    }
}
