package org.study.bootcamp.mishustin.interview.practice;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ STATIC/FINAL, МОДИФИКАТОРОВ ДОСТУПА И АННОТАЦИЙ В JAVA С ИХ ОСОБЕННОСТЯМИ
 *
 * @author Sergey
 */
public class Lesson06Part0StaticAccessAnnotation {

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
        System.out.println("1) static — члены класса (общее), а не объекта (индивидуальное)");
        System.out.println("Ключевая мысль: static выбирается по имени класса/типу ссылки; instance — по объекту");

        System.out.println();
        System.out.println("A) static-поле: одно значение на весь класс");
        System.out.println("   Формула: InstanceCounter.createdCount — 1 штука на класс, видна всем объектам");

        System.out.println("   A1) Создаём первый объект → класс загружается, static init выполняется один раз");
        new InstanceCounter();
        System.out.println("       createdCount после 1-го объекта → " + InstanceCounter.getCreatedCount());

        System.out.println("   A2) Создаём второй объект → static init НЕ повторяется, меняется только счётчик");
        new InstanceCounter();
        System.out.println("       createdCount после 2-го объекта → " + InstanceCounter.getCreatedCount());

        System.out.println();
        System.out.println("B) static-метод: вызов без объекта");
        int input = 5;
        System.out.println("   Вызов: MathUtil.square(" + input + ")");
        System.out.println("   Результат: " + MathUtil.square(input) + " (квадрат числа; объект MathUtil не нужен)");

        System.out.println();
        System.out.println("C) static init-блок: подтверждаем, что выполняется ровно один раз");
        System.out.println("   Создаём ещё один объект InstanceCounter");
        new InstanceCounter();
        System.out.println("       createdCount после 3-го объекта → " + InstanceCounter.getCreatedCount());
        System.out.println("       Если строки \"class loaded\" больше нет — static init действительно одноразовый");

        System.out.println();
        System.out.println("D) static и наследование: method hiding (не override)");
        Parent parentRefToChild = new Child();

        System.out.println("   D1) Вызов static по имени класса (явно):");
        System.out.println("       Parent.describe() → " + Parent.describe());
        System.out.println("       Child.describe()  → " + Child.describe());

        System.out.println("   D2) Вызов static через ссылку Parent, внутри объект Child:");
        System.out.println("       Тип ссылки: Parent; объект: Child");
        System.out.println("       parentRefToChild.describe() → " + parentRefToChild.describe()
                + " (выбран static-метод по типу ссылки: Parent)");

        System.out.println("   D3) Для сравнения: instance-метод через ту же ссылку:");
        System.out.println("       parentRefToChild.describeInstance() → " + parentRefToChild.describeInstance()
                + " (выбран instance-метод по объекту: Child)");

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

/*
    Модификаторы доступа (access modifiers) — правила видимости членов класса.
    Зачем вообще была придумана эта концепция?
    - чтобы ограничивать точки изменения состояния (инкапсуляция) и уменьшать число ошибок;
    - чтобы отделять внешний контракт (API) от внутренней реализации (её можно менять, не ломая пользователей);
    - чтобы безопасно развивать код: меньше связности, меньше "случайных" зависимостей
    Почему нельзя сделать доступ ко всему публичным или наоборот приватным?
    - если всё public:
      * любой код может менять состояние как угодно → сложно гарантировать корректность (инварианты ломаются);
      * невозможно безопасно рефакторить внутренности: любая правка может сломать чужой код
    - если всё private:
      * класс становится непригодным к использованию извне (невозможен API);
      * нельзя расширять поведение наследованием там, где это осмысленно (protected нужен как "крючок")
    Всего модификаторов доступа 4: public / protected / default (package-private) / private
    С точки зрения "открытости": public → protected → default → private
    1) public
    - доступен отовсюду (любой пакет, любой класс);
    - применяется к классам, полям, методам, конструкторам;
    - использовать для публичного API: то, что действительно обязано быть доступно пользователям класса
    2) protected
    - доступен: внутри пакета + в наследниках (даже если наследник в другом пакете);
    - использовать, когда нужно дать "точку расширения" наследнику, но не открывать член всем подряд;
    - важная особенность (другой пакет):
      * protected в другом пакете нельзя трогать через "переменную типа родителя";
      * доступ появляется только в коде наследника и через ссылку типа наследника
      Пример идеи:
      Parent p = new Child();          // ссылка типа Parent
      p.protectedField = ...;          // НЕЛЬЗЯ в другом пакете
      Child c = new Child();           // ссылка типа Child
      c.protectedField = ...;          // МОЖНО (потому что это наследник)
    3) default (package-private) — когда модификатор не указан
    - доступен только внутри того же пакета;
    - использовать для "внутренней" связки классов внутри пакета (реализация, служебные детали),
      чтобы не тащить это в публичный API
    4) private
    - доступен только внутри текущего класса;
    - основной инструмент инкапсуляции: поля private, наружу — методы (валидация/нормализация/контроль)
    Дополнительно:
    - Классы верхнего уровня (top-level) могут быть только public или default
      (private/protected для top-level классов запрещены)
    - Вложенные классы (nested) могут иметь любые модификаторы, включая private
 */

    private static void demonstrateAccessModifiers() {
        System.out.println("2) Модификаторы доступа — управляем видимостью и точками изменения состояния");

        System.out.println();
        System.out.println(
                "Контекст: класс AccessSample хранит данные профиля и намеренно скрывает секрет (private)"
        );
        System.out.println("Поля примера:");
        System.out.println(" - publicName     (public)    : открытое поле (демо, в реальном коде так делают редко)");
        System.out.println(" - protectedNote  (protected) : доступно пакету и наследникам");
        System.out.println(" - city           (default)   : доступно только пакету");
        System.out.println(
                " - privateToken   (private)   : доступно только внутри AccessSample (меняем через setPrivateToken)"
        );

        System.out.println();
        System.out.println(
                "A) Доступ внутри того же класса/пакета: заполняем то, что разрешено напрямую, а private — через метод"
        );
        AccessSample sample = new AccessSample();
        sample.publicName = "Открытая часть: имя профиля";
        sample.protectedNote = "Заметка: видна наследникам/пакету";
        sample.city = "Город: виден только пакету";
        sample.setPrivateToken("Секрет: задаём только через метод");

        System.out.println("   Состояние объекта AccessSample после заполнения:");
        sample.printSummary();

        System.out.println();
        System.out.println("B) Наследование: SupportEngineer extends AccessSample");
        System.out.println("   Идея: наследник имеет доступ к protected;" +
                " default доступен только потому что наследник в том же пакете");
        SupportEngineer engineer = new SupportEngineer();
        engineer.fillContacts();

        System.out.println(
                "   Состояние объекта SupportEngineer (всё те же поля AccessSample, просто заполнены из наследника):"
        );
        engineer.printSummary();

        System.out.println();
        System.out.println("C) Особенность protected в другом пакете (кратко, на уровне правила):");
        System.out.println("   - В нём 'protected' недоступен через ссылку типа Parent (Parent p = new Child())");
        System.out.println(
                "   - Доступ появляется только в коде наследника и через ссылку типа наследника (Child c = new Child())"
        );

        System.out.println();
    }

    private static class AccessSample {
        public String publicName = "";
        protected String protectedNote = "";
        String city = ""; // default (package-private)
        private String privateToken = "";

        public void setPrivateToken(String privateToken) {
            if (privateToken == null || privateToken.isBlank()) {
                throw new IllegalArgumentException("privateToken не может быть пустым");
            }
            this.privateToken = privateToken;
        }

        public void printSummary() {
            System.out.println("       publicName     = " + publicName    + "  [public]");
            System.out.println("       protectedNote  = " + protectedNote + "  [protected]");
            System.out.println("       city           = " + city          + "  [default]");
            System.out.println("       privateToken   = " + privateToken  + "  [private]");
        }
    }

    private static class SupportEngineer extends AccessSample {
        public void fillContacts() {
            this.publicName = "Support-профиль (заполнено в наследнике)";
            this.protectedNote = "Заполнено в наследнике: protected доступен";
            this.city = "Москва (default доступен, потому что тот же пакет)";
            setPrivateToken("private выставлено через метод базового класса");
        }
    }

/*
    Аннотации (annotations) — метаданные, которые прикрепляются к коду через @
    Рефлексия (reflection) — механизм Java, который позволяет во время выполнения:
    - узнать, какие поля/методы/конструкторы есть у класса;
    - прочитать метаданные (в том числе аннотации);
    - при необходимости вызвать метод или создать объект динамически
    Как аннотация "заставляет" что-то работать:
    - сама по себе аннотация ничего не исполняет;
    - эффект из-за программы/инструмента, который анализирует её и делает выводы
      * компилятор/IDE: @Override, @Deprecated, @SuppressWarnings;
      * фреймворк: Spring/JPA/Validation читает аннотации через reflection и строит поведение
        (например, какие классы зарегистрировать, какие поля маппить в БД, как валидировать данные)
    Когда аннотация доступна:
    - Уровень хранения аннотации (RetentionPolicy) определяет, на каком этапе её можно "увидеть":
      * SOURCE  (только исходники): аннотация видна инструментам разработки, но в .class не записывается;
      * CLASS   (в .class): аннотация записывается компилятором в .class, но в runtime через reflection не читается;
      * RUNTIME (в runtime): аннотация и записывается в .class, и читается через reflection во время выполнения
    Дополнительные настройки аннотаций:
    - Target: на что аннотацию можно ставить (класс, поле, метод и т.д.);
      пример: @Target(ElementType.METHOD) означает "аннотацию можно ставить только на методы";
    - Inherited: наследуется ли аннотация дочерними классами (только для аннотаций на классах)
 */

    private static void demonstrateAnnotations() {
        System.out.println("3) Аннотации — метаданные через @; используются кодом, который читает и принимает решения");

        System.out.println();
        System.out.println("A) @Override: компилятор проверяет корректность переопределения");
        BaseRenderer baseRenderer = new BaseRenderer();
        BaseRenderer rendererRefToChild = new HtmlRenderer();
        System.out.println("   baseRenderer.render()       → " + baseRenderer.render());
        System.out.println("   rendererRefToChild.render() → " + rendererRefToChild.render()
                + " (instance-метод выбран по объекту)");

        System.out.println();
        System.out.println("B) @Deprecated + @SuppressWarnings: предупреждения инструментов (IDE/компилятор)");
        DeprecatedApiDemo deprecatedApiDemo = new DeprecatedApiDemo();
        deprecatedApiDemo.callDeprecatedSafely();

        System.out.println();
        System.out.println("C) Runtime-аннотация + reflection: читаем аннотацию и используем её для логики");
        FeatureGate featureGate = new FeatureGate();

        FeatureExportService exportService = new FeatureExportService();
        featureGate.runIfEnabled(exportService);

        FeatureImportService importService = new FeatureImportService();
        featureGate.runIfEnabled(importService);

        System.out.println();
    }

    private static class BaseRenderer {
        public String render() {
            return "BaseRenderer: базовая разметка";
        }
    }

    private static class HtmlRenderer extends BaseRenderer {
        @Override
        public String render() {
            return "HtmlRenderer: <html>...</html>";
        }
    }

    private static class DeprecatedApiDemo {

        public void callDeprecatedSafely() {
            System.out.println("   Вызывается legacyMethod() (предупреждение подавлено локально)");
            @SuppressWarnings("deprecation")
            String value = legacyMethod();
            System.out.println("   legacyMethod() → " + value);
        }

        @Deprecated
        public String legacyMethod() {
            return "legacy result (метод помечен @Deprecated)";
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface Feature {
        String key();
        boolean enabledByDefault();
    }

    private interface Job {
        void execute();
    }

    @Feature(key = "USER_EXPORT", enabledByDefault = true)
    private static class FeatureExportService implements Job {
        @Override
        public void execute() {
            System.out.println("   Выполнено: экспорт пользователей (логика сервиса)");
        }
    }

    @Feature(key = "USER_IMPORT", enabledByDefault = false)
    private static class FeatureImportService implements Job {
        @Override
        public void execute() {
            System.out.println("   Выполнено: импорт пользователей (логика сервиса)");
        }
    }

    private static class FeatureGate {

        public void runIfEnabled(Job job) {
            Class<?> jobClass = job.getClass();
            Feature feature = jobClass.getAnnotation(Feature.class);

            if (feature == null) {
                System.out.println("   " + jobClass.getSimpleName() + ": аннотации @Feature нет → запрет выполнения");
                return;
            }

            System.out.println("   " + jobClass.getSimpleName() + ": нашли @Feature через reflection");
            System.out.println("   - key=" + feature.key());
            System.out.println("   - enabledByDefault=" + feature.enabledByDefault());

            if (!feature.enabledByDefault()) {
                System.out.println("   Решение: enabledByDefault=false → выполнение пропущено");
                return;
            }

            System.out.println("   Решение: enabledByDefault=true → выполняем job.execute()");
            job.execute();
        }
    }

    public static void main(String[] args) {
        demonstrateStatic();
        demonstrateAccessModifiers();
        demonstrateAnnotations();
    }
}
