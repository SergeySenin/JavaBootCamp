package org.study.bootcamp.interview_practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ STATIC/FINAL, МОДИФИКАТОРОВ ДОСТУПА И АННОТАЦИЙ В JAVA С ИХ ОСОБЕННОСТЯМИ
 *
 * @author Sergey
 */
public class _06_StaticAccessAnnotation {

/*
    static — ключевое слово, которое делает член (поле/метод/блок/вложенный класс) принадлежащим классу, а не объекту;
    используется, когда: поведение не зависит от состояния конкретного объекта и данные общие для всех объектов
    1) static поле
    - в памяти JVM существует ровно один экземпляр static-поля на весь класс, а не на каждый объект;
    - любое изменение static-поля видно всем объектам этого класса;
    - инициализируется при загрузке класса и существует до выгрузки класса;
    - типичный пример: константы (static final), счётчик созданных объектов, кеш
    2) static метод
    - вызывается без объекта: MathUtil.square(5);
      создавать new MathUtil() не нужно и часто нельзя (в утилитарных классах конструктор обычно private);
    - так как нет конкретного объекта: нельзя использовать this и нельзя обращаться к нестатическим полям напрямую;
      решение: создать объект и работать через него или получить объект извне (параметром/полем) и работать с ним;
    - напрямую имеет доступ только к static-полям/методам (так как они тоже "на классе");
    - типичный пример: утилитарные методы (Math), фабрики (of/from/valueOf), валидация
    3) static init блок (static-инициализатор)
    - это блок кода "static { ... }";
    - выполняется автоматически один раз при первой инициализации класса JVM (когда класс реально понадобился);
    - это не поле и не метод: его нельзя вызвать вручную;
    - нужен, когда инициализация static-данных сложнее, чем просто "= значение";
    - типичный пример: загрузка конфигурации, логирование факта загрузки класса, сложная инициализация static-данных
    4) static и наследование
    - static методы не переопределяются (override), потому что override — это про полиморфизм объекта (runtime);
    - вместо override происходит method hiding (скрытие):
      в наследнике можно объявить static-метод с той же сигнатурой, но это будет ДРУГОЙ метод;
    - instance-методы полиморфны: какую реализацию вызвать, JVM выбирает по реальному объекту во время выполнения;
      а static-методы нет: какой static-метод будет вызван, решается по типу переменной (ссылки) и определяется заранее
 */

    private static void demonstrateStatic() {
        System.out.println("=== 1) static: принадлежит классу, не объекту ===");

        System.out.println();
        System.out.println("[A] static-поле: одно на класс (счётчик созданных объектов)");
        System.out.println("Шаг A1: создаём первый InstanceCounter → ожидаем: загрузка класса + static init один раз");
        InstanceCounter first = new InstanceCounter();
        System.out.println("Наблюдение: createdCount = " + InstanceCounter.getCreatedCount()
                + " (после первого объекта)");

        System.out.println("Шаг A2: создаём второй InstanceCounter → static init уже НЕ должен выполняться");
        InstanceCounter second = new InstanceCounter();
        System.out.println("Наблюдение: createdCount = " + InstanceCounter.getCreatedCount()
                + " (после второго объекта)");

        System.out.println();
        System.out.println("[B] static-метод: вызывается без объекта");
        int input = 5;
        int squared = MathUtil.square(input);
        System.out.println("Вызов: MathUtil.square(" + input + ") → " + squared
                + " (new MathUtil() не нужен; конструктор у утилиты обычно private)");

        System.out.println();
        System.out.println("[C] static init-блок: выполняется один раз при первой инициализации класса");
        System.out.println("Проверка: создаём третий InstanceCounter → строки про \"class loaded\" снова быть не должно");
        InstanceCounter third = new InstanceCounter();
        System.out.println("Наблюдение: createdCount = " + InstanceCounter.getCreatedCount()
                + " (static init не повторяется, а счётчик продолжает расти)");

        System.out.println();
        System.out.println("[D] static и наследование: method hiding + выбор по типу ссылки (compile-time)");
        Parent parentRefToChild = new Child();

        System.out.println("Вызов по имени класса:");
        System.out.println("  Parent.describe() → " + Parent.describe());
        System.out.println("  Child.describe()  → " + Child.describe());

        System.out.println("Вызов через ссылку типа Parent, но с объектом Child внутри:");
        System.out.println("  parentRefToChild.describe() → " + parentRefToChild.describe()
                + " (static: выбран Parent.describe, потому что тип ссылки Parent)");

        System.out.println("Сравнение: instance-метод через ту же ссылку:");
        System.out.println("  parentRefToChild.describeInstance() → " + parentRefToChild.describeInstance()
                + " (instance: выбран Child.describeInstance, потому что объект Child)");
        System.out.println();
    }

    private static class InstanceCounter {
        private static int createdCount;

        static {
            createdCount = 0;
            System.out.println("[InstanceCounter] class loaded: static init-блок выполнился один раз");
        }

        public InstanceCounter() {
            createdCount++;
        }

        public static int getCreatedCount() {
            return createdCount;
        }
    }

    private static final class MathUtil {
        private MathUtil() {
        }

        public static int square(int value) {
            return value * value;
        }
    }

    private static class Parent {
        public static String describe() {
            return "Parent.static describe()";
        }

        public String describeInstance() {
            return "Parent.instance describeInstance()";
        }
    }

    private static class Child extends Parent {
        public static String describe() {
            return "Child.static describe() (hiding, не override)";
        }

        @Override
        public String describeInstance() {
            return "Child.instance describeInstance() (override)";
        }
    }

    public static void main(String[] args) {
        demonstrateStatic();
    }
}
