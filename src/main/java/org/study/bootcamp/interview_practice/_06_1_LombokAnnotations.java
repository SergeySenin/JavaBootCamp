package org.study.bootcamp.interview_practice;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ Lombok-АННОТАЦИЙ И ИХ "РЕАЛИЗАЦИИ"
 *
 * Lombok — библиотека, которая через annotation processing (аннотационный процессор компилятора)
 * генерирует шаблонный код на этапе компиляции
 *
 * Ключевые особенности Lombok:
 * 1) Генерация происходит при компиляции, а не в runtime: в .class лежат обычные методы/конструкторы
 * 2) Аннотации Lombok читаются аннотационным процессором; IDE должна:
 *    - иметь Lombok plugin,
 *    - иметь включённый Annotation Processing
 * 3) Lombok не добавляет "скрытую" бизнес-логику: он генерирует стандартные вещи (геттеры/сеттеры/конструкторы)
 *    Инварианты и валидации лучше держать в явных методах/фабриках
 *
 * @author Sergey
 */
@Slf4j
public class _06_1_LombokAnnotations {

    // -----------------------------------------------------------------------------------------------------------------
    // 0) Механизм Lombok: generated methods существуют в runtime
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateMechanismVisibilityInRuntime() {
        System.out.println("0) Lombok: генерация на этапе компиляции, в runtime доступны обычные методы");
        System.out.println("   Проверка: reflection видит методы, которые были сгенерированы Lombok'ом");

        LombokUserAccount account = new LombokUserAccount("user-1", "sergey", Instant.now());

        System.out.println("   LombokUserAccount:");
        System.out.println("   - public getUsername() exists  → " + hasPublicMethod(account.getClass(), "getUsername"));
        System.out.println("   - declared setNote(...) exists → " + hasDeclaredMethod(account.getClass(), "setNote")
                + " (false: @Setter(AccessLevel.NONE) запретил генерацию сеттера)");

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 1) @Slf4j
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateSlf4j() {
        System.out.println("1) @Slf4j");
        System.out.println("   Что генерирует: private static final org.slf4j.Logger log");
        System.out.println("   Где полезно: единый логгер без ручного объявления поля");

        log.info("Lombok demo: annotation=@Slf4j event=DEMO at={}", Instant.now());

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 2) @Getter (+ AccessLevel)
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateGetter() {
        System.out.println("2) @Getter (+ AccessLevel)");
        System.out.println("   Что генерирует: getX() для поля/класса");
        System.out.println("   Уровни доступа (AccessLevel): PUBLIC, MODULE, PROTECTED, PACKAGE, PRIVATE, NONE");

        LombokGetterSample sample = new LombokGetterSample("internal-42");
        System.out.println("   readInternalIdFromInside()              → "     + sample.readInternalIdFromInside());

        Method getter = getDeclaredMethodOrNull(LombokGetterSample.class, "getInternalId");
        System.out.println("   Reflection: getInternalId() exists      → "     + (getter != null));
        if (getter != null) {
            System.out.println(
                    "   Reflection: getInternalId() isProtected → " + Modifier.isProtected(getter.getModifiers())
            );
        }

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 3) @Setter (+ AccessLevel)
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateSetter() {
        System.out.println("3) @Setter (+ AccessLevel)");
        System.out.println("   Что генерирует: setX(value) для поля/класса");
        System.out.println("   Важно: Lombok-сеттер не делает валидацию/trim автоматически");
        System.out.println("   Фишка AccessLevel:");
        System.out.println("   - PRIVATE/PROTECTED/PACKAGE/PUBLIC ограничивают вызов");
        System.out.println("   - NONE запрещает генерацию сеттера (частый приём для инвариантов)");

        LombokProfile profile = new LombokProfile();
        profile.setDisplayName("  Sergey  ");
        profile.setAge(21);
        System.out.println("   LombokProfile:");
        System.out.println("   - getDisplayName()        → \"" + profile.getDisplayName() + "\" (пробелы сохранены)");
        System.out.println("   - getAge()                → "   + profile.getAge());

        LombokSetterAccessSample accessSample = new LombokSetterAccessSample();
        accessSample.setPublicNote("public ok");
        accessSample.setProtectedNoteFromInside("protected ok (via wrapper)");
        accessSample.setPrivateNoteFromInside("private ok (via wrapper)");
        System.out.println("   LombokSetterAccessSample  → " + accessSample);

        System.out.println("   Reflection (declared setters exist even if not callable from outside):");
        System.out.println(
                "   - setPrivateNote exists   → "
                        + hasDeclaredMethod(LombokSetterAccessSample.class, "setPrivateNote")
        );
        System.out.println(
                "   - setProtectedNote exists → "
                        + hasDeclaredMethod(LombokSetterAccessSample.class, "setProtectedNote")
        );

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 4) @ToString
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateToString() {
        System.out.println("4) @ToString");
        System.out.println("   Что генерирует: toString() по полям");
        System.out.println("   Фишка: @ToString.Exclude исключает поле (секреты: токены/пароли)");

        LombokSecretContainer container = new LombokSecretContainer("PublicName", "token-123");
        System.out.println("   container → " + container + " (token скрыт через @ToString.Exclude)");

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 5) @EqualsAndHashCode
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateEqualsAndHashCode() {
        System.out.println("5) @EqualsAndHashCode");
        System.out.println("   Что генерирует: equals()/hashCode()");
        System.out.println("   Контракт: если equals=true          → hashCode обязан совпадать");

        Instant createdAt = Instant.now();
        LombokUserAccount first =  new LombokUserAccount("user-10", "alex", createdAt);
        LombokUserAccount second = new LombokUserAccount("user-10", "alex", createdAt);

        System.out.println("   first.equals(second)                → " + first.equals(second));
        System.out.println("   first.hashCode()==second.hashCode() → " + (first.hashCode() == second.hashCode()));

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 6) @RequiredArgsConstructor (+ @NonNull)
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateRequiredArgsConstructor() {
        System.out.println("6) @RequiredArgsConstructor (+ @NonNull)");
        System.out.println("   Что генерирует: конструктор по final-полям и полям с @NonNull");
        System.out.println("   Фишка @NonNull: fail-fast null-check в конструкторе (и сеттере, если он генерируется)");

        LombokUserAccount ok = new LombokUserAccount("user-2", "anna", Instant.now());
        System.out.println("   ok.getUserId()                 → " + ok.getUserId());

        try {
            new LombokUserAccount("user-3", null, Instant.now());
            System.out.println("   Неожиданно: объект создан (не должно случиться)");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException → " + exception.getMessage());
        }

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 7) @NoArgsConstructor
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateNoArgsConstructor() {
        System.out.println("7) @NoArgsConstructor");
        System.out.println("   Что генерирует: конструктор без параметров");
        System.out.println("   Где применяется: фреймворки/сериализация/ORM (нужен no-args для рефлексии)");

        LombokConstructorSample empty = new LombokConstructorSample();
        System.out.println("   new LombokConstructorSample() → " + empty);

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 8) @AllArgsConstructor
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateAllArgsConstructor() {
        System.out.println("8) @AllArgsConstructor");
        System.out.println("   Что генерирует: конструктор по всем полям");

        LombokConstructorSample full = new LombokConstructorSample("ticket-1", 3);
        System.out.println("   new LombokConstructorSample(\"ticket-1\", 3) → " + full);

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 9) @Builder (+ access + @Singular + @Builder.Default)
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateBuilder() {
        System.out.println("9) @Builder (+ access + @Singular + @Builder.Default)");
        System.out.println(
                "   Идея builder: создать объект пошагово, без длинного конструктора и без множества перегрузок"
        );
        System.out.println("   Lombok генерирует:");
        System.out.println("   - static builder() (точка входа)");
        System.out.println("   - вложенный класс <ИмяКласса>Builder (хранит временные значения полей)");
        System.out.println("   - методы-сеттеры билдера по полям (например, orderId(...), createdAt(...))");
        System.out.println("   - метод build() (создаёт готовый объект)");

        System.out.println();
        System.out.println("   9.1) access (доступ к builder())");
        System.out.println("   - @Builder(access = AccessLevel.PUBLIC)    → builder() доступен везде (по умолчанию)");
        System.out.println("   - @Builder(access = AccessLevel.PRIVATE)   → builder() виден только внутри класса");
        System.out.println("     Зачем PRIVATE: принудить создание через фабрику create(...),");
        System.out.println("     чтобы централизовать проверки/инварианты и не дать создавать «полу-валидные» объекты");

        System.out.println();
        System.out.println("   9.2) @Singular (коллекции в builder)");
        System.out.println("   Если поле: List<String> items;");
        System.out.println("   то Lombok делает два варианта заполнения:");
        System.out.println("   - item(\"Keyboard\")                         → добавить один элемент");
        System.out.println("   - items(list)                              → добавить сразу коллекцию");
        System.out.println("   Это удобнее, чем вручную собирать список до вызова build()");

        System.out.println();
        System.out.println("   9.3) @Builder.Default (значения по умолчанию)");
        System.out.println("   Проблема: builder НЕ использует инициализацию поля как «дефолт», если поле не задавали");
        System.out.println("   Пример: priority = NORMAL;");
        System.out.println("   - без @Builder.Default → при build() priority может стать null");
        System.out.println("   - с @Builder.Default   → если priority не задан, будет использовано NORMAL");

        System.out.println();
        System.out.println("   Демонстрация: обычный builder + @Singular + @Builder.Default");

        LombokOrderRequest request = LombokOrderRequest.builder()
                .orderId("ORD-100")
                .createdAt(Instant.now())
                .item("Keyboard")
                .item("Mouse")
                .build();

        System.out.println("   request            → " + request);
        System.out.println("   items.size         → " + request.getItems().size());
        System.out.println("   priority (default) → " + request.getPriority());

        System.out.println();
        System.out.println("   Демонстрация: закрытый builder (access=PRIVATE) + фабрика create(...)");

        LombokPrivateBuilderOrder order = LombokPrivateBuilderOrder.create("ORD-777");
        System.out.println("   order → " + order);

        Method builderMethod = getDeclaredMethodOrNull(LombokPrivateBuilderOrder.class, "builder");
        System.out.println("   Reflection: builder() существует → " + (builderMethod != null));
        if (builderMethod != null) {
            System.out.println(
                    "   Reflection: builder() private    → " + Modifier.isPrivate(builderMethod.getModifiers())
            );
        }

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 10) @Value
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateValue() {
        System.out.println("10) @Value");
        System.out.println("   Что генерирует (иммутабельный тип):");
        System.out.println("   - все поля становятся private final");
        System.out.println("   - getters для всех полей");
        System.out.println("   - equals/hashCode/toString");
        System.out.println("   - all-args constructor");
        System.out.println("   Следствие: нет сеттеров; состояние после создания не меняется");

        LombokMoney money = LombokMoney.ofValidated("RUB", 100);
        System.out.println("   money               → " + money);
        System.out.println("   money.getCurrency() → " + money.getCurrency());
        System.out.println("   money.getAmount()   → " + money.getAmount());

        System.out.println();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 11) @Data
    // -----------------------------------------------------------------------------------------------------------------
    private static void demonstrateData() {
        System.out.println("11) @Data");
        System.out.println("   Агрегатная аннотация:");
        System.out.println("   - @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor");
        System.out.println("   Важно: @Data делает тип мутабельным (есть сеттеры) — применять осознанно");

        LombokDataUser user = new LombokDataUser("alex", Instant.now());
        user.setAge(20);

        System.out.println("   user                               → " + user);
        System.out.println("   user.getUsername()                 → " + user.getUsername());
        System.out.println("   user.getCreatedAt()                → " + user.getCreatedAt());
        System.out.println("   user.getAge()                      → " + user.getAge());

        LombokDataUser same = new LombokDataUser("alex", user.getCreatedAt());
        same.setAge(20);

        System.out.println("   user.equals(same)                  → " + user.equals(same));
        System.out.println("   user.hashCode()==same.hashCode()   → " + (user.hashCode() == same.hashCode()));

        System.out.println("   Проверка @NonNull на сеттере (fail-fast): user.setUsername(null)");
        try {
            user.setUsername(null);
            System.out.println("   Неожиданно: setter принял null (не должно случиться)");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException → " + exception.getMessage());
        }

        System.out.println();
    }

    private static final class LombokGetterSample {
        @Getter(AccessLevel.PROTECTED)
        private final String internalId;

        private LombokGetterSample(String internalId) {
            this.internalId = Objects.requireNonNull(internalId, "internalId обязателен");
        }

        public String readInternalIdFromInside() {
            return getInternalId();
        }
    }

    @Getter
    @Setter
    private static class LombokProfile {
        private String displayName;
        private int age;
    }

    @ToString
    private static class LombokSecretContainer {
        private final String name;

        @ToString.Exclude
        private final String token;

        private LombokSecretContainer(String name, String token) {
            this.name = name;
            this.token = token;
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @RequiredArgsConstructor
    private static class LombokUserAccount {
        @NonNull
        private final String userId;

        @NonNull
        private final String username;

        @NonNull
        private final Instant createdAt;

        @Setter(AccessLevel.NONE)
        private String note;

        public void setNoteSafely(String note) {
            if (note == null || note.isBlank()) {
                this.note = null;
                return;
            }
            this.note = note.trim();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    private static class LombokConstructorSample {
        private String id;
        private int attempts;
    }

    private enum LombokOrderPriority {
        LOW, NORMAL, HIGH
    }

    @Getter
    @ToString
    @Builder
    private static class LombokOrderRequest {
        @NonNull
        private final String orderId;

        @NonNull
        private final Instant createdAt;

        @Singular
        private final List<String> items;

        @Builder.Default
        private final LombokOrderPriority priority = LombokOrderPriority.NORMAL;
    }

    @Getter
    @ToString
    @Builder(access = AccessLevel.PRIVATE)
    private static class LombokPrivateBuilderOrder {
        @NonNull
        private final String orderId;

        @NonNull
        private final Instant createdAt;

        public static LombokPrivateBuilderOrder create(String orderId) {
            return LombokPrivateBuilderOrder.builder()
                    .orderId(orderId)
                    .createdAt(Instant.now())
                    .build();
        }
    }

    @Value
    private static class LombokMoney {
        @NonNull
        String currency;

        long amount;

        public static LombokMoney ofValidated(String currency, long amount) {
            String normalizedCurrency = Objects.requireNonNull(currency, "currency обязателен").trim();
            if (normalizedCurrency.isBlank()) {
                throw new IllegalArgumentException("currency не должен быть пустым: currency=\"" + currency + "\"");
            }
            if (amount < 0) {
                throw new IllegalArgumentException("amount не может быть отрицательным: amount=" + amount);
            }
            return new LombokMoney(normalizedCurrency, amount);
        }
    }

    @Data
    private static class LombokDataUser {
        @NonNull
        private String username;

        private int age;

        @NonNull
        private final Instant createdAt;
    }

    @Getter
    @ToString
    private static class LombokSetterAccessSample {

        @Setter(AccessLevel.PRIVATE)
        private String privateNote;

        @Setter(AccessLevel.PROTECTED)
        private String protectedNote;

        @Setter
        private String publicNote;

        public void setPrivateNoteFromInside(String value) {
            setPrivateNote(value);
        }

        public void setProtectedNoteFromInside(String value) {
            setProtectedNote(value);
        }
    }

    private static boolean hasPublicMethod(Class<?> type, String methodName) {
        for (Method method : type.getMethods()) {
            if (method.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasDeclaredMethod(Class<?> type, String methodName) {
        for (Method method : type.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    private static Method getDeclaredMethodOrNull(Class<?> type, String methodName) {
        for (Method method : type.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        demonstrateMechanismVisibilityInRuntime();
        demonstrateSlf4j();
        demonstrateGetter();
        demonstrateSetter();
        demonstrateToString();
        demonstrateEqualsAndHashCode();
        demonstrateRequiredArgsConstructor();
        demonstrateNoArgsConstructor();
        demonstrateAllArgsConstructor();
        demonstrateBuilder();
        demonstrateValue();
        demonstrateData();
    }
}

/*
    === Как в IntelliJ IDEA посмотреть, что сгенерировал Lombok ===

    1) Быстро: "Структура" класса
       - Открыть окно "Структура" (Alt+7)
       - В списке членов увидеть сгенерированные методы (get*.../set*.../toString.../equals.../hashCode.../конструкторы)

    2) Наглядно: открыть скомпилированный .class (декомпиляция)
       - Собрать проект (Ctrl+F9)
       - Проект (Alt+1) → найти .class:
       * Gradle: build/classes/java/main/...
       * Maven : target/classes/...
       - Открыть .class → IDEA покажет декомпилированный код со всеми методами Lombok

    3) Байткод: Show Bytecode → Decompile
       - Найти действие (Ctrl+Shift+A) → "Show Bytecode"
       - В окне байткода нажать "Decompile"

       Если Lombok "красный" в IDE:
       - Открыть настройки (Ctrl+Alt+S) → Build, Execution, Deployment → Compiler → Annotation Processors
       → включить "Enable annotation processing"
       - Открыть настройки (Ctrl+Alt+S) → Plugins → установить/включить Lombok plugin
*/
