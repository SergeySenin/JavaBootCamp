package org.study.bootcamp.mishustin.interview.practice;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ ФУНКЦИОНАЛЬНЫХ ИНТЕРФЕЙСОВ И ЛЯМБДА-ВЫРАЖЕНИЙ (Java 8+)
 *
 * Вводные тезисы:
 * 1) Лямбда в Java существует только вместе с "целевым типом" — функциональным интерфейсом (target typing).
 * 2) Функциональный интерфейс = ровно один абстрактный метод (SAM), при этом:
 *    - default/static методы не считаются абстрактными;
 *    - методы, совпадающие с public-методами Object, не считаются;
 *    - "один метод" может быть представлен несколькими override-equivalent методами из суперинтерфейсов.
 * 3) Захват локальных переменных из внешней области разрешён только если они final или effectively final.
 * 4) Ссылка на метод (method reference) — альтернативный синтаксис лямбды, когда реализация уже существует.
 *
 * =====================================================================================================================
 *
 * 0) Термины
 *
 * 0.1) SAM (Single Abstract Method)
 * Единственный абстрактный метод, который задаёт "форму" функции (аргументы + возвращаемый тип + checked exceptions).
 *
 * 0.2) Functional interface (функциональный интерфейс)
 * Интерфейс, который удовлетворяет определению: имеет один абстрактный метод (кроме методов Object)
 * и может выступать как целевой тип для лямбды/ссылки на метод.
 *
 * 0.3) @FunctionalInterface
 * Аннотация-маркер. Не делает интерфейс функциональным "магически", а просит компилятор проверить,
 * что он функциональный. Если нет — compile-time error.
 *
 * 0.4) Суперинтерфейс
 * Интерфейс-родитель, который расширяется другим интерфейсом через extends (B extends A: то A — суперинтерфейс для B).
 *
 * =====================================================================================================================
 *
 * 1) Функциональный интерфейс: правила и нюансы
 *
 * 1.1) Что можно иметь внутри функционального интерфейса:
 * - ровно один абстрактный метод, который "считается" SAM;
 * - сколько угодно default методов;
 * - сколько угодно static методов;
 * - абстрактные методы, совпадающие по сигнатуре с public методами Object (они не увеличивают "счётчик").
 *
 * 1.2) Inheritance-нюанс: "один SAM" может прийти из нескольких суперинтерфейсов
 * Если унаследованные абстрактные методы override-equivalent, они логически считаются одним SAM.
 *
 * 1.3) Примечание про Comparable
 * Comparable<T> структурно соответствует функциональному интерфейсу (один compareTo),
 * но исторически не помечен @FunctionalInterface; обсуждение есть в OpenJDK issue tracker.
 *
 * =====================================================================================================================
 *
 * 2) Лямбда-выражения: синтаксис и соответствие SAM
 *
 * 2.1) Базовые формы
 * - (a, b) -> a + b             // expression body (return подразумевается)
 * - (a, b) -> { return a + b; } // block body (return обязателен если тип не void)
 *
 * 2.2) Типы параметров
 * Обычно выводятся из target type. Можно писать явно:
 * - (int a, int b) -> a + b
 * Правило: либо все параметры с типами, либо ни одного.
 *
 * 2.3) Checked exceptions
 * Лямбда может выбрасывать checked exception только если SAM метод это объявляет.
 *
 * =====================================================================================================================
 *
 * 3) Target typing (Целевой тип)
 *
 * 3.1) Лямбда не имеет самостоятельного "типа выражения" как у обычных объектов.
 * Тип определяется контекстом: присваивание, аргумент вызова метода, явное приведение типа.
 *
 * 3.2) Перегрузка методов + лямбды
 * Возможна неоднозначность: одна и та же лямбда подходит к нескольким функциональным интерфейсам.
 * Решение: явное приведение типа или изменение сигнатуры/имени методов.
 *
 * =====================================================================================================================
 *
 * 4) Захват переменных: final / effectively final
 *
 * 4.1) Локальные переменные и параметры, захваченные из внешней области, должны быть final или effectively final.
 * "Effectively final" = переменная не меняется после инициализации.
 *
 * 4.2) Захват состояния объекта (поля) разрешён
 * Поля можно изменять, но это уже про потокобезопасность/гонки: лямбда не делает доступ к полям "безопасным".
 *
 * =====================================================================================================================
 *
 * 5) Отличия лямбды от анонимного класса
 *
 * 5.1) this и scope
 * - В лямбде this ссылается на внешний объект (экземпляр класса, где написана лямбда).
 * - В анонимном классе this ссылается на экземпляр анонимного класса.
 *
 * 5.2) Shadowing
 * В лямбде нельзя "перекрывать" имя локальной переменной из внешнего scope.
 * В анонимном классе правила другие (там отдельная область видимости).
 *
 * =====================================================================================================================
 *
 * 6) Method reference: формы и соответствия сигнатурам
 *
 * 6.1) Class::staticMethod
 * 6.2) object::instanceMethod
 * 6.3) Class::instanceMethod  (первый параметр SAM становится receiver-объектом)
 * 6.4) Class::new             (constructor reference)
 *
 * Ссылка на метод — это синтаксическая форма, которая также требует target typing.
 * В рантайме реализация лямбд/ссылок на методы обычно основана на invokedynamic/LambdaMetafactory.
 *
 * =====================================================================================================================
 *
 * 7) Базовые функциональные интерфейсы из java.util.function (минимум)
 *
 * Supplier<T>   : T get()                         — "даёт значение"
 * Consumer<T>   : void accept(T t)                — "потребляет"
 * Predicate<T>  : boolean test(T t)               — "проверяет"
 * Function<T,R> : R apply(T t)                    — "преобразует"
 *
 * Частые: BiFunction, BiConsumer, BiPredicate, UnaryOperator, BinaryOperator,
 * а также примитивные специализации (IntPredicate, LongSupplier, etc.) для уменьшения boxing.
 *
 * В сигнатурах часто встречаются wildcard-типы: Comparator<? super T>, Function<? super T, ? extends R> (PECS).
 * Wildcard ? — это "неизвестный тип" (placeholder), ограниченный сверху (? extends X) или снизу (? super X).
 *
 * ? extends X (upper bound) — когда объект "производит" значения типа X:
 * можно безопасно читать X (или его подтипы), но нельзя безопасно добавлять X (кроме null).
 * ? super X (lower bound) — когда объект "потребляет" X:
 * можно безопасно передавать X (или его подтипы) внутрь, а при чтении получаешь только Object.
 *
 * PECS: Producer Extends, Consumer Super — правило чтения сигнатур: "производитель" -> extends, "потребитель" -> super.
 *
 * =====================================================================================================================
 *
 * 8) Коротко про дженерики (generics)
 * Дженерики — механизм параметризации типов (обобщения), позволяющий писать классы/интерфейсы/методы,
 * работающие с разными типами, сохраняя статическую типобезопасность на этапе компиляции.
 *
 * Зачем нужны:
 * 1) Типобезопасность: ошибки несоответствия типов ловятся компилятором, а не в рантайме (меньше ClassCastException).
 * 2) Уменьшение кастов: не нужно постоянно приводить типы при чтении из коллекций/результатов функций.
 * 3) Переиспользуемость API: один и тот же код работает для разных типов без дублирования.
 *
 * Ограничения (границы применимости):
 * 1) Type erasure: в рантайме параметры типов стираются (нельзя узнать T через reflection “просто так”).
 * 2) Нельзя создавать new T(), new T[]; нельзя иметь static поле типа T (T — параметр типа экземпляра).
 * 3) Инвариантность: List<Integer> не является подтипом List<Number>;
 *    для гибкости применяются wildcards (? extends / ? super).
 *
 * =====================================================================================================================
 *
 * Практические выводы
 * 1) Выбор лямбда vs method reference делать по читаемости; "быстрее/меньше байткода" как правило не гарантируется.
 * 2) Comparator писать через Integer.compare/Comparator.comparing, а не через вычитание (избежать overflow).
 * 3) Захват локальных переменных использовать как "константы контекста"; изменяемое состояние выносить в объект.
 * 4) Лямбды и функциональные интерфейсы — способ передавать доп-тельную логику внутрь алгоритма (поведение как данные).
 *
 * @author Sergey
 */
public class Lesson11FunctionalInterfacesAndLambdas {

    /*
    Лямбда как сокращение записи "передаём поведение (код) внутрь метода"
    Идея: алгоритм фиксирован, "вставка" меняется (дополнительное действие посередине).

    0) Функциональный интерфейс (SAM)
    @FunctionalInterface
    interface Action {
        void act();
    }

    1) Отдельный класс (самый многословный, но явный и переиспользуемый)
    class BecomeBossAction implements Action {
        @Override
        public void act() {
            System.out.println("Стать боссом");
        }
    }
    planDay(new BecomeBossAction());

    2) Анонимный класс (без отдельного файла/класса, но многословно)
    planDay(new Action() {
        @Override
        public void act() {
            System.out.println("Стать боссом");
        }
    });

    3) Лямбда (короткая запись реализации SAM)
    planDay(() -> System.out.println("Стать боссом"));

    4) Method reference (когда реализация уже существует и её можно сослать)
    // допустим, есть готовый метод:
    // static void becomeBoss() { System.out.println("Стать боссом"); }
    planDay(Lesson11FunctionalInterfacesAndLambdas::becomeBoss);

    5) Обобщённая форма лямбды (по шаблону)
    (param1, param2) -> { ... }    // block body
    (param1, param2) -> expression // expression body (return подразумевается)

    6) Method reference: 4 формы
    ClassName::staticMethod        // статический метод
    objectRef::instanceMethod      // метод конкретного объекта
    ClassName::instanceMethod      // метод экземпляра; receiver берётся из 1-го аргумента SAM
    ClassName::new                 // конструктор
     */

    // =================================================================================================================
    // 1) Функциональный интерфейс: @FunctionalInterface и правила SAM
    // =================================================================================================================
    @FunctionalInterface
    interface IntBinaryOperation {
        int apply(int a, int b);

        // default/static разрешены
        default IntBinaryOperation reversed() {
            return (a, b) -> apply(b, a);
        }

        static IntBinaryOperation sum() {
            return Integer::sum;
        }

        // метод Object не ломает функциональность (не "считается" вторым абстрактным)
        @Override
        boolean equals(Object obj);
    }

    private static void demonstrateFunctionalInterfaceRules() {
        printSectionTitle("1) Functional interface: SAM, default/static, Object methods");

        IntBinaryOperation add = (a, b) -> a + b;
        System.out.println("[1.1] (a, b) -> a + b : add(2, 3) = " + add.apply(2, 3));

        IntBinaryOperation reversedAdd = add.reversed();
        System.out.println("[1.2] default method reversed: reversedAdd(2, 3) = " + reversedAdd.apply(2, 3));

        IntBinaryOperation sum = IntBinaryOperation.sum();
        System.out.println("[1.3] static factory + method reference Integer::sum : sum(5, 7) = " + sum.apply(5, 7));

        System.out.println();
    }

    // =================================================================================================================
    // 2) Лямбда-синтаксис: expression body vs block body, типы параметров, void/return
    // =================================================================================================================
    private static void demonstrateLambdaSyntax() {
        printSectionTitle("2) Lambda syntax: expression vs block, inferred types");

        Function<String, Integer> parse = s -> Integer.parseInt(s); // expression body, типы выводятся
        System.out.println("[2.1] Function<String,Integer>: parse(\"123\") = " + parse.apply("123"));

        Function<String, Integer> parseWithBlock = (String s) -> {  // block body, тип параметра указан явно
            return Integer.parseInt(s);
        };
        System.out.println("[2.2] block body: parseWithBlock(\"456\") = " + parseWithBlock.apply("456"));

        Consumer<String> printer = text -> System.out.println("[2.3] Consumer prints: " + text);
        printer.accept("hello");

        System.out.println();
    }

    // =================================================================================================================
    // 3) Target typing и неоднозначность перегрузки (шаблон)
    // =================================================================================================================
    /*
        Демонстрация принципа: одна и та же лямбда может подходить разным SAM типам, что даёт ambiguity.
        В реальном проекте это решается (а) явным cast, (б) разделением методов, (в) более точными сигнатурами.

        void process(Predicate<String> p) { ... }
        void process(Function<String, Boolean> f) { ... }

        process(s -> s.isEmpty()); // может стать неоднозначным при определённых перегрузках/контекстах
        process((Predicate<String>) s -> s.isEmpty()); // снимает ambiguity
     */
    private static void demonstrateTargetTypingTemplate() {
        printSectionTitle("3) Target typing: шаблон для перегрузок и приведения типа");

        System.out.println("[3.1] Смотри комментарий в коде: ambiguity решается явным приведением типа.");
        System.out.println();
    }

    // =================================================================================================================
    // 4) Захват переменных: effectively final
    // =================================================================================================================
    private static void demonstrateCaptureRules() {
        printSectionTitle("4) Capture: final/effectively final");

        int base = 10; // effectively final
        IntBinaryOperation plusBase = (a, b) -> a + b + base;
        System.out.println("[4.1] base=10 captured: plusBase(1, 2) = " + plusBase.apply(1, 2));

        System.out.println("[4.2] Правило: base нельзя изменять после захвата (иначе не скомпилируется).");
        System.out.println();
    }

    // =================================================================================================================
    // 5) Method references и сопоставление сигнатур
    // =================================================================================================================
    private static void demonstrateMethodReferences() {
        printSectionTitle("5) Method references: Class::static, obj::instance, Class::instance, Class::new");

        // Class::staticMethod
        IntBinaryOperation sum = Integer::sum;
        System.out.println("[5.1] Integer::sum -> sum(3, 4) = " + sum.apply(3, 4));

        // obj::instanceMethod
        String prefix = "id=";
        Function<String, String> addPrefix = prefix::concat;
        System.out.println("[5.2] prefix::concat -> addPrefix(\"42\") = " + addPrefix.apply("42"));

        // Class::instanceMethod (receiver берётся из 1-го параметра SAM)
        Predicate<String> isEmpty = String::isEmpty;
        System.out.println("[5.3] String::isEmpty -> test(\"\") = " + isEmpty.test(""));

        // Class::new
        Supplier<StringBuilder> sbFactory = StringBuilder::new;
        System.out.println("[5.4] StringBuilder::new -> length=" + sbFactory.get().length());

        System.out.println();
    }

    // =================================================================================================================
    // 6) Comparator: безопасное сравнение (без overflow)
    // =================================================================================================================
    private static void demonstrateComparatorSafeCompare() {
        printSectionTitle("6) Comparator: избегать вычитания, использовать compare/comparing");

        Comparator<Integer> descSafe = (a, b) -> Integer.compare(b, a); // безопасно
        System.out.println("[6.1] descSafe.compare(2, 100) = " + descSafe.compare(2, 100));

        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();
        System.out.println("[6.2] natural.compare(2, 100) = " + natural.compare(2, 100));
        System.out.println("[6.3] reverse.compare(2, 100) = " + reverse.compare(2, 100));

        System.out.println();
    }

    // =================================================================================================================
    // 7) java.util.function: минимальный набор + примитивная специализация
    // =================================================================================================================
    private static void demonstrateJavaUtilFunctionBasics() {
        printSectionTitle("7) java.util.function: Supplier/Consumer/Predicate/Function + IntPredicate");

        Supplier<String> supplier = () -> "value";
        System.out.println("[7.1] Supplier.get() = " + supplier.get());

        Consumer<String> consumer = v -> System.out.println("[7.2] Consumer.accept(): " + v);
        consumer.accept("payload");

        Predicate<String> nonBlank = s -> s != null && !s.isBlank();
        System.out.println("[7.3] Predicate.test(\"  \") = " + nonBlank.test("  "));
        System.out.println("[7.4] Predicate.test(\"abc\") = " + nonBlank.test("abc"));

        Function<String, Integer> length = String::length;
        System.out.println("[7.5] Function.apply(\"abcd\") = " + length.apply("abcd"));

        IntPredicate isPositive = x -> x > 0; // без boxing
        System.out.println("[7.6] IntPredicate.test(10) = " + isPositive.test(10));

        System.out.println();
    }

    public static void main(String[] args) {
        demonstrateFunctionalInterfaceRules();
        demonstrateLambdaSyntax();
        demonstrateTargetTypingTemplate();
        demonstrateCaptureRules();
        demonstrateMethodReferences();
        demonstrateComparatorSafeCompare();
        demonstrateJavaUtilFunctionBasics();

        // Мини-контроль: null-safety в лямбдах обычно делается явно.
        Objects.requireNonNull("ok");
    }

    private static void printSectionTitle(String title) {
        System.out.println();
        System.out.println("=====================================================================================");
        System.out.println(title);
        System.out.println("=====================================================================================");
    }
}
