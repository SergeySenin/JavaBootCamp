package org.study.bootcamp.interview_practice;

import java.util.UUID;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ STATIC/FINAL, МОДИФИКАТОРОВ ДОСТУПА И АННОТАЦИЙ В JAVA С ИХ ОСОБЕННОСТЯМИ
 *
 * @author Sergey
 */
public class _06_AccessStaticAnnotations {

/*
    static — ключевое слово, которое делает член (поле/метод/блок/вложенный класс) принадлежащим классу, а не объекту;
    используется, когда: поведение не зависит от состояния конкретного объекта и данные общие для всех объектов
    1) static поле
    - в памяти JVM существует ровно один экземпляр static-поля на весь класс, а не на каждый объект
    - любое изменение static-поля видно всем объектам этого класса
    - инициализируется при загрузке класса и существует до выгрузки класса
    - типичный пример: константы (static final), счётчик созданных объектов, кеш
    2) static метод
    - вызывается без объекта: MathUtil.square(5); создавать new MathUtil() не нужно и нельзя (часто конструктор private)
    - так как нет конкретного объекта: нельзя использовать this, нельзя обращаться к нестатическим полям;
      решение проблемы: создать объект и обратиться через него или получить объект извне и работать с ним
    - напрямую имеет доступ только к static-полям/методам
    - типичный пример: утилитарные методы (Math), фабрики (of/from/valueOf), валидация
    3) static init блок
    - обычный блок кода, помеченный static: выполняется один раз, автоматически, при первой загрузке класса JVM
    - это не поле и не метод; его нельзя вызвать вручную; он нужен, когда инициализация сложнее, чем = значение
    - типичный пример: загрузка конфигурации, логирование факта загрузки класса, сложная инициализация static-данных
    4) static и наследование
    - static методы не переопределяются (override)
    - они скрываются (method hiding), то есть ...
    - выбор метода идёт по типу ссылки (compile-time), а не по типу объекта (runtime), то есть ...
 */

    private static void demonstrateStatic() {
        System.out.println("1) static: поле/метод принадлежат классу, а не объекту");

        System.out.println("Сценарий A: static-поле общее для всех объектов (счётчик созданных экземпляров)");
        System.out.println("Действие: создаём два объекта InstanceCounter");
        InstanceCounter first = new InstanceCounter();
        InstanceCounter second = new InstanceCounter();
        System.out.println(
                "Результат: InstanceCounter.getCreatedCount() → " + InstanceCounter.getCreatedCount()
                        + " (ожидаемо 2, потому что поле одно на класс)"
        );

        System.out.println();
        System.out.println("Сценарий B: static-метод вызывается без объекта (утилитарная логика)");
        System.out.println(
                "MathUtil.square(5) → " + MathUtil.square(5) + " (метод статический, объект создавать не нужно)"
        );

        System.out.println();
        System.out.println("Сценарий C: static init-блок выполняется один раз при загрузке класса");
        System.out.println("Строка \"[InstanceCounter] class loaded\" печатается один раз при первом обращении");

        System.out.println();
        System.out.println("Сценарий D: static-методы не полиморфны (не override, а hiding)");
        Parent parentRefToChild = new Child();
        System.out.println("Parent.describe() → " + Parent.describe() + " (вызов по имени класса: всегда Parent)");
        System.out.println("Child.describe() → " + Child.describe() + " (вызов по имени класса: всегда Child)");
        System.out.println(
                "parentRefToChild.describeInstance() → " + parentRefToChild.describeInstance()
                        + " (это уже не static: выбирается реализация по объекту)"
        );

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

    private static final class IdGenerator {
        private IdGenerator() {
        }

        static String nextId() {
            return UUID.randomUUID().toString();
        }
    }

    public static void main(String[] args) {
        demonstrateStatic();
    }
}
