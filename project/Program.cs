using System;
using System.Collections.Generic;

namespace ARLabApp
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Лабораторная работа с использованием дополненой реальности");
            Console.WriteLine("1. Проверить наличие AR-ресурсов");
            Console.WriteLine("2. Сбросить AR-сцену");
            Console.WriteLine("3. Просмотреть методичку");
            Console.WriteLine("4. Просмотреть инструктаж");
            Console.Write("Выберите действие (1-4): ");
            
            string choice = Console.ReadLine();

            if (choice == "1")
            {
                string[] requiredFiles = { "model_chemistry.obj", "texture_lab.png", "marker_ar.jpg" };
                string[] availableFiles = { "model_chemistry.obj", "texture_lab.png" };

                bool isComplete = CheckARAssets(requiredFiles, availableFiles);
                
                if (isComplete)
                    Console.WriteLine("Все AR-ресурсы загружены");
            }
            else if (choice == "2")
            {
                var sceneObjects = new List<Dictionary<string, string>>
                {
                    new Dictionary<string, string> { { "id", "obj_1" }, { "x", "10" }, { "y", "20" } },
                    new Dictionary<string, string> { { "id", "obj_2" }, { "x", "50" }, { "y", "60" } }
                };

                var configObjects = new List<Dictionary<string, string>>
                {
                    new Dictionary<string, string> { { "id", "obj_1" }, { "x", "0" }, { "y", "0" } },
                    new Dictionary<string, string> { { "id", "obj_2" }, { "x", "0" }, { "y", "0" } }
                };

                ResetARScene(sceneObjects, configObjects);
                
                Console.WriteLine("Объекты возвращены на исходные позиции.");
                foreach(var obj in sceneObjects)
                {
                    Console.WriteLine($"Объект {obj["id"]}: X={obj["x"]}, Y={obj["y"]}");
                }
            }
            else if (choice == "3")
            {
                GenerateMethodology("Название работы", "ФИО преподавателя");
            }
            else if (choice == "4")
            {
                ConductSafetyBriefing();
            }
            else
            {
                Console.WriteLine("Неверный ввод.");
            }
        }

        // 1. Проверка ресурсов
        static bool CheckARAssets(string[] required, string[] available)
        {
            var availableSet = new HashSet<string>(available);
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
        static void ResetARScene(List<Dictionary<string, string>> scene, List<Dictionary<string, string>> config)
        {
            for (int i = 0; i < scene.Count; i++)
            {
                for (int j = 0; j < config.Count; j++)
                {
                    if (scene[i]["id"] == config[j]["id"])
                    {
                        scene[i]["x"] = config[j]["x"];
                        scene[i]["y"] = config[j]["y"];
                        break; 
                    }
                }
            }
        }

        // 3. Генерация методички
        static void GenerateMethodology(string labName, string teacherName)
        {
            Console.WriteLine($"Генерация документа для лабораторной работы: \"{labName}\"");
            Console.WriteLine($"Автор: {teacherName}");
            Console.WriteLine("Текст методички");
        }

        // 4. Проведение инструктажа
        static void ConductSafetyBriefing()
        {
            Console.WriteLine("ИНСТРУКТАЖ ПО ТЕХНИКЕ БЕЗОПАСНОСТИ");
            Console.WriteLine("1. Запрещается работать с устройством во влажной среде и при повреждении корпуса.");
            Console.WriteLine("2. Запрещается оставлять устройство без присмотра на зарядке и в местах нагрева.");
            Console.WriteLine("3. Запрещается использование устройства более двух часов подряд.");
            Console.WriteLine("4. При поломке устройство не вскрывать.");
        }
    }
}