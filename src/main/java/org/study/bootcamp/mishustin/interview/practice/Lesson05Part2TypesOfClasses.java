package org.study.bootcamp.mishustin.interview.practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ КЛАССОВ В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * class                 | Стандартные классы                | public class Person {
 *                                                                 // Код класса...
 *                                                             }
 *
 * — базовая форма объявляемого типа: описывает состояние (поля) и поведение (методы), задаёт конструкторы
 * Поддерживает наследование (extends один класс) и реализацию интерфейсов (implements несколько)
 * Top-level класс бывает public или package-private; вложенные классы могут быть с любыми модификаторами
 * Создание экземпляра обычно через new, тип используется как единица дизайна и API
 *
 *
 * final                 | Финальные классы                  | final class ImmutableClass {
 *                                                                 // Код класса...
 *                                                             }
 *
 * — класс закрыт для наследования: никто не может extends от него
 * Используется, когда иерархия запрещена по дизайну (безопасность API, неизменяемые/значимые типы, предсказуемость)
 * Запрет наследования означает запрет переопределения его instance-методов через наследников (наследников нет)
 * Пример в JDK: многие “value”-типы делаются final; record тоже implicitly final
 *
 *
 * abstract class        | Абстрактные классы                | abstract class Animal {
 *                                                                 // Код класса...
 *                                                             }
 *
 * — нельзя создать через new; может содержать abstract-методы без реализации
 * Может хранить состояние (поля), иметь конструкторы и обычные методы с общей логикой
 * Наследник обязан реализовать все abstract-методы или сам остаться abstract
 * Применение: общий каркас поведения + вариативные части в наследниках
 *
 *
 * interface             | Интерфейсы                        | interface Drawable {
 *                                                                 // Код класса...
 *                                                             }
 *
 * — “контракт роли”: задаёт набор методов/поведения, который обязуется предоставить реализация
 * Класс может реализовать много интерфейсов, что даёт множественную “типизацию” без множественного наследования классов
 * В современных версиях Java интерфейсы могут иметь default/static методы (и служебные private-методы внутри)
 * Применение: полиморфизм, DI, плагины, подмена реализаций без изменения клиентского кода
 *
 *
 * enum                  | Перечисления                      | enum Day {
 *                                                                 // Код класса...
 *                                                             }
 *
 * — специальный вид класса для фиксированного набора экземпляров (констант), заданного в коде
 * Каждая enum-константа — объект; enum может иметь поля, методы и приватный конструктор
 * Удобен для switch (в т.ч. исчерпывающие ветки по всем значениям), безопаснее “магических строк/чисел”
 * Применение: статусы, типы, режимы, ограниченные доменные значения
 *
 *
 * record                | Записи (Java 16+)                | record Point(int x, int y) {
 *                                                                 // Код класса...
 *                                                             }
 *
 * — компактный синтаксис для “data carrier”: объявляешь компоненты, остальное генерирует компилятор
 * Автоматически создаются: поля, canonical-конструктор, аксессоры, equals/hashCode/toString по компонентам
 * record наследуется от java.lang.Record и является implicitly final (расширять нельзя, но интерфейсы можно)
 * Применение: DTO, value objects, результаты запросов/парсинга, неизменяемые структуры данных
 *
 *
 * nested classes        | Вложенные классы
 * общее название для типов, объявленных внутри другого класса/интерфейса
 * Дают группировку “рядом по смыслу” и скрытие деталей реализации (инкапсуляция на уровне файла/класса)
 * Доступ к членам внешнего класса зависит от вида вложенности (static vs non-static)
 * Виды: static nested, inner, local, anonymous (и др. по месту объявления)
 *
 * static nested classes | Статические вложенные классы      | class Outer {
 *                                                                 static class StaticNested {
 *                                                                     // Код класса...
 *                                                                 }
 *                                                             }
 *
 * — вложенный класс с static: не привязан к экземпляру внешнего класса
 * Не имеет неявной ссылки Outer.this, создаётся без объекта внешнего класса
 * Может напрямую обращаться только к static-членам внешнего класса (остальное — через явные ссылки)
 * Применение: вспомогательный тип, тесно связанный с внешним, но без зависимости от его состояния
 *
 *
 * inner classes         | Внутренние классы (нестатические) | class Outer2 {
 *                                                                 class Inner {
 *                                                                     // Код класса...
 *                                                                 }
 *                                                             }
 *
 * — non-static вложенный класс, всегда связан с конкретным экземпляром внешнего
 * Имеет неявную ссылку на внешний объект и может читать/менять его члены (включая private)
 * Создание обычно через outer.new Inner() (нужен экземпляр outer)
 * Применение: когда логика внутреннего типа должна работать “на данных” конкретного внешнего объекта
 *
 *
 * local classes         | Локальные классы                  | void method() {
 *                                                                 class LocalClass {
 *                                                                     // Код класса...
 *                                                                 }
 *                                                                 new LocalClass();
 *                                                             }
 *
 * — класс, объявленный внутри блока (чаще внутри метода/конструктора/инициализатора)
 * Виден только в пределах этого блока; используется как “одноразовый” вспомогательный тип рядом с логикой
 * Может захватывать локальные переменные, которые являются effectively final
 * Применение: компактная инкапсуляция небольшой логики без выноса в отдельный файл/тип
 *
 *
 * anonymous classes     | Анонимные классы                  | Runnable runnable = new Runnable() {
 *                                                                 @Override
 *                                                                 public void run() {
 *                                                                     // Код класса...
 *                                                                 }
 *                                                             };
 *
 * — безымянный класс, который объявляется и создаётся “в одной точке” выражением new ... { ... }
 * Может реализовать интерфейс или расширить класс; обычно применяется для единичной реализации
 * Может захватывать effectively final переменные; конструктора с именем нет (используется конструктор базового типа)
 * Часто заменяется лямбда-выражением, если целевой тип — функциональный интерфейс
 *
 *
 * sealed classes        | Запечатанные классы (Java 17+)    | sealed class Shape permits Circle, Rectangle {
 *                                                                 // Код класса...
 *                                                             }
 *
 *                                                             final class Circle extends Shape {
 *                                                                 // Код класса...
 *                                                             }
 *
 *                                                             non-sealed class Rectangle extends Shape {
 *                                                                 // Код класса...
 *                                                             }
 *
 * — ограничивает наследование/реализацию: разрешены только указанные “permitted” типы
 * Разрешённые подтипы задаются через permits (или выводятся компилятором из того же файла), лежат в пакете/модуле
 * Каждый permitted subtype обязан объявить, как продолжать иерархию: final, sealed или non-sealed
 * Применение: “закрытые” доменные иерархии, исчерпывающая обработка вариантов (в т.ч. под pattern matching)
 *
 * @author Sergey
 */
public class Lesson05Part2TypesOfClasses {

    // 1) class — обычный класс
    private static final class Person {
        private final String name;
        private int age;

        public Person(String name, int age) {
            this.name = normalizeName(name);
            this.age = Math.max(age, 0);
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void haveBirthday() {
            age++;
        }

        private static String normalizeName(String name) {
            if (name == null || name.isBlank()) {
                return "Без имени";
            }
            return name.trim();
        }

        @Override
        public String toString() {
            return "Person{name=\"" + name + "\", age=" + age + "}";
        }
    }

    // 2) final class — запрет наследования
    private static final class ImmutableToken {
        private final String value;

        public ImmutableToken(String value) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("token.value обязателен");
            }
            this.value = value.trim();
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "ImmutableToken{value=\"" + value + "\"}";
        }
    }

    // 3) abstract class — общий каркас + вариативные части
    private static abstract class PaymentProcessor {
        public final String process(long amountMinorUnits) {
            validate(amountMinorUnits);
            return execute(amountMinorUnits);
        }

        protected void validate(long amountMinorUnits) {
            if (amountMinorUnits <= 0) {
                throw new IllegalArgumentException(
                        "amountMinorUnits должен быть > 0: amountMinorUnits=" + amountMinorUnits
                );
            }
        }

        protected abstract String execute(long amountMinorUnits);
    }

    private static final class CardPaymentProcessor extends PaymentProcessor {
        @Override
        protected String execute(long amountMinorUnits) {
            return "CARD: charged amountMinorUnits=" + amountMinorUnits;
        }
    }

    // 4) interface — роль/контракт + default/static/private методы
    private interface TextFormatter {
        String format(String input);

        default String formatOrEmpty(String input) {
            if (isBlank(input)) {
                return "";
            }
            return format(input);
        }

        static TextFormatter upperCase() {
            return new UpperCaseFormatter();
        }

        private static boolean isBlank(String input) {
            return input == null || input.isBlank();
        }
    }

    private static final class UpperCaseFormatter implements TextFormatter {
        @Override
        public String format(String input) {
            return input.trim().toUpperCase();
        }
    }

    // 5) enum — фиксированный набор экземпляров
    private enum Day {
        MON(false),
        TUE(false),
        WED(false),
        THU(false),
        FRI(false),
        SAT(true),
        SUN(true);

        private final boolean weekend;

        Day(boolean weekend) {
            this.weekend = weekend;
        }

        public boolean isWeekend() {
            return weekend;
        }
    }

    // 6) record — data carrier, компилятор генерирует equals/hashCode/toString/accessors
    private record Point(int x, int y) {
        public Point {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Point: координаты должны быть >= 0: x=" + x + ", y=" + y);
            }
        }
    }

    // 7) nested classes — static nested + inner
    private static final class Outer {
        private static final String STATIC_LABEL = "OUTER_STATIC";
        private final String instanceLabel;

        public Outer(String instanceLabel) {
            this.instanceLabel = instanceLabel;
        }

        public String getInstanceLabel() {
            return instanceLabel;
        }

        // static nested: без Outer.this
        static final class StaticNested {
            public String describe() {
                return "StaticNested: canReadOuterStatic=\"" + STATIC_LABEL + "\"";
            }
        }

        // inner: есть Outer.this
        final class Inner {
            public String describe() {
                return "Inner: canReadOuterStatic=\"" + STATIC_LABEL + "\", canReadOuterInstance=\""
                        + Outer.this.instanceLabel + "\"";
            }
        }
    }

    // 8) sealed classes (Java 17+) — ограничение иерархии
    private sealed abstract static class Shape permits Circle, Rectangle {
        public abstract double area();
    }

    private static final class Circle extends Shape {
        private final double radius;

        public Circle(double radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException("radius должен быть > 0: radius=" + radius);
            }
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    private static non-sealed class Rectangle extends Shape {
        private final double width;
        private final double height;

        public Rectangle(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException(
                        "width/height должны быть > 0: width=" + width + ", height=" + height
                );
            }
            this.width = width;
            this.height = height;
        }

        @Override
        public double area() {
            return width * height;
        }
    }

    // Разрешено, потому что Rectangle — non-sealed
    private static final class Square extends Rectangle {
        public Square(double side) {
            super(side, side);
        }
    }

    // ===========================
    // Демонстрации
    // ===========================

    private static void demonstrateClassAndFinal() {
        System.out.println("A) class + final class");

        Person person = new Person("  Sergey  ", 21);
        System.out.println("person → " + person);
        person.haveBirthday();
        System.out.println("after haveBirthday() → " + person);

        ImmutableToken token = new ImmutableToken("  token-123  ");
        System.out.println("token → " + token);

        System.out.println();
    }

    private static void demonstrateAbstractClass() {
        System.out.println("B) abstract class");

        PaymentProcessor processor = new CardPaymentProcessor();
        String result = processor.process(12_345);
        System.out.println("processor.process(12345) → " + result);

        System.out.println();
    }

    private static void demonstrateInterface() {
        System.out.println("C) interface");

        TextFormatter formatter = TextFormatter.upperCase();
        System.out.println("formatter.format(\"  hello  \") → \"" + formatter.format("  hello  ") + "\"");
        System.out.println("formatter.formatOrEmpty(null) → \"" + formatter.formatOrEmpty(null) + "\"");

        System.out.println();
    }

    private static void demonstrateEnum() {
        System.out.println("D) enum");

        Day day = Day.SAT;
        System.out.println("day=" + day + ", isWeekend=" + day.isWeekend());

        String type = switch (day) {
            case MON, TUE, WED, THU, FRI -> "WORKDAY";
            case SAT, SUN -> "WEEKEND";
        };
        System.out.println("switch(day) → " + type);

        System.out.println();
    }

    private static void demonstrateRecord() {
        System.out.println("E) record");

        Point point = new Point(10, 20);
        System.out.println("point → " + point);
        System.out.println("point.x()=" + point.x() + ", point.y()=" + point.y());

        System.out.println();
    }

    private static void demonstrateNestedClasses() {
        System.out.println("F) nested classes: static nested + inner");

        Outer.StaticNested staticNested = new Outer.StaticNested();
        System.out.println(staticNested.describe());

        Outer outer = new Outer("OUTER_INSTANCE");
        Outer.Inner inner = outer.new Inner();
        System.out.println(inner.describe());

        System.out.println();
    }

    private static void demonstrateLocalClass() {
        System.out.println("G) local class");

        String prefix = "LOCAL_PREFIX"; // effectively final

        class LocalPrinter {
            public void print(String value) {
                System.out.println(prefix + ": value=\"" + value + "\"");
            }
        }

        LocalPrinter printer = new LocalPrinter();
        printer.print("demo");

        System.out.println();
    }

    private static void demonstrateAnonymousClass() {
        System.out.println("H) anonymous class");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("anonymous Runnable.run(): executed");
            }
        };
        runnable.run();

        // Если целевой тип — функциональный интерфейс, часто заменяется лямбдой:
        Runnable lambda = () -> System.out.println("lambda Runnable.run(): executed");
        lambda.run();

        System.out.println();
    }

    private static void demonstrateSealedClasses() {
        System.out.println("I) sealed classes");

        Shape circle = new Circle(2.0);
        Shape rectangle = new Rectangle(3.0, 4.0);
        Shape square = new Square(5.0);

        System.out.println("circle.area() → " + circle.area());
        System.out.println("rectangle.area() → " + rectangle.area());
        System.out.println("square.area() → " + square.area());

        System.out.println();
    }

    public static void main(String[] args) {
        demonstrateClassAndFinal();
        demonstrateAbstractClass();
        demonstrateInterface();
        demonstrateEnum();
        demonstrateRecord();
        demonstrateNestedClasses();
        demonstrateLocalClass();
        demonstrateAnonymousClass();
        demonstrateSealedClasses();
    }
}
