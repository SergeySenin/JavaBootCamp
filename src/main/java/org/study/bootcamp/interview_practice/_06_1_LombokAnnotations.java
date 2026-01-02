package org.study.bootcamp.interview_practice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Cleanup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ Lombok-АННОТАЦИЙ И ИХ "РЕАЛИЗАЦИИ"
 *
 * Lombok — библиотека, которая через annotation processing (аннотационный процессор на этапе компиляции)
 * генерирует "шаблонный" код: getters/setters/constructors/toString/equals&hashCode/builder/loggers и т.д.
 *
 * Важно:
 * - Lombok НЕ "магия" runtime: он генерирует методы при компиляции (в .class появляются обычные методы)
 * - IDE должна понимать Lombok (плагин) и должна быть включена обработка аннотаций (Annotation Processing)
 *
 * Практика:
 * - Lombok экономит время на бойлерплейте, но требует дисциплины:
 *   не прятать важную логику в авто-сгенерированных методах и не забывать про контракты equals/hashCode
 *
 * @author Sergey
 */
@Slf4j
public class _06_1_LombokAnnotations {

    /*
        0) "Как это работает"
        - javac запускает аннотационные процессоры (annotation processors)
        - Lombok-processors анализируют AST и добавляют/изменяют узлы (методы/конструкторы/поля)
        - На выходе получается обычный .class с обычными методами
        - В Gradle/Maven Lombok подключается как compileOnly + annotationProcessor
        - В IDE: включить обработку аннотаций, иначе код будет "красным" и/или не скомпилируется
     */

    private static void demonstrateMechanismAndVisibilityInBytecode() {
        System.out.println("1) Механизм Lombok: код генерируется при компиляции (в .class появляются обычные методы)");

        LombokUserAccount account = new LombokUserAccount("user-1", "sergey", Instant.now());
        System.out.println("Объект создан через @RequiredArgsConstructor + @NonNull поля");
        System.out.println("account.getUserId() → " + account.getUserId());
        System.out.println("account.getUsername() → " + account.getUsername());

        System.out.println();
        System.out.println("Проверка через reflection: метод getUsername() реально существует в runtime:");
        boolean hasGetUsername = hasPublicMethod(account.getClass(), "getUsername");
        System.out.println("hasPublicMethod(LombokUserAccount, \"getUsername\") → " + hasGetUsername);

        System.out.println();
    }

    /*
        2) @Getter / @Setter
        - Генерируют getX()/setX(value) для полей
        - Можно задавать уровень доступа: @Getter(AccessLevel.PROTECTED)
        - Опасность: @Setter не делает валидацию/нормализацию автоматически
          Для важных полей часто предпочтительнее явные методы изменения (changeEmail(...)) с проверками
     */
    private static void demonstrateGetterSetter() {
        System.out.println("2) @Getter/@Setter: генерация accessor-методов");

        LombokProfile profile = new LombokProfile();
        profile.setDisplayName("  Sergey  ");
        profile.setAge(21);

        System.out.println("profile.getDisplayName() → \"" + profile.getDisplayName() + "\"");
        System.out.println("profile.getAge() → " + profile.getAge());
        System.out.println("Факт: @Setter не делает trim/валидацию — пробелы сохранились как есть");

        System.out.println();
    }

    /*
        3) @ToString
        - Генерирует toString() по полям
        - Можно исключать поля: @ToString.Exclude (например, токены/пароли/секреты)
        - Для больших графов объектов toString может стать дорогим или рекурсивным
     */
    private static void demonstrateToString() {
        System.out.println("3) @ToString: удобное логирование состояния объекта");

        LombokSecretContainer container = new LombokSecretContainer("PublicName", "token-123");
        System.out.println("container.toString() → " + container);

        System.out.println();
    }

    /*
        4) @EqualsAndHashCode
        - Генерирует equals/hashCode по полям
        - Контракт: если equals(a,b)=true, то hashCode(a)==hashCode(b)
        - Наследование: осторожно (может потребоваться callSuper=true или запрет наследования)
        - Примечание: @Value включает equals/hashCode, поэтому пример ниже демонстрирует тот же контракт
     */
    private static void demonstrateEqualsAndHashCode() {
        System.out.println("4) equals/hashCode: контракт и пример (на @Value, который генерирует equals/hashCode)");

        LombokUserId first = new LombokUserId(10);
        LombokUserId second = new LombokUserId(10);

        System.out.println("first == second → " + (first == second) + " (разные объекты)");
        System.out.println("first.equals(second) → " + first.equals(second) + " (сравнение по value)");
        System.out.println("first.hashCode() == second.hashCode() → " +
                (first.hashCode() == second.hashCode()) + " (должно совпадать при equals=true)");

        System.out.println();
    }

    /*
        5) Конструкторы: @RequiredArgsConstructor (+ @NonNull)
        - @RequiredArgsConstructor: конструктор по final и @NonNull полям
        - @NonNull: добавляет fail-fast проверку null (обычно NullPointerException с сообщением поля)
     */
    private static void demonstrateConstructorsAndNonNull() {
        System.out.println("5) Конструкторы + @NonNull: генерация и fail-fast");

        LombokUserAccount ok = new LombokUserAccount("user-2", "anna", Instant.now());
        System.out.println("Создан ok → " + ok);

        System.out.println("Пробуем создать с username=null → ожидаем fail-fast из @NonNull");
        try {
            new LombokUserAccount("user-3", null, Instant.now());
            System.out.println("Неожиданно: объект создан (не должно случиться)");
        } catch (NullPointerException exception) {
            System.out.println("Ожидаемо: NullPointerException → " + exception.getMessage());
        }

        System.out.println();
    }

    /*
        6) @Builder
        - Генерирует builder-API для удобного создания объектов
        - @Singular для коллекций: item(...) + items(list)
        - @Builder.Default для значений по умолчанию (иначе default может быть потерян)
     */
    private static void demonstrateBuilder() {
        System.out.println("6) @Builder: читаемое создание объектов со множеством полей");

        LombokOrderRequest request = LombokOrderRequest.builder()
                .orderId("ORD-100")
                .createdAt(Instant.now())
                .item("Keyboard")
                .item("Mouse")
                .priority(LombokOrderPriority.HIGH)
                .build();

        System.out.println("request → " + request);
        System.out.println("request.getItems().size() → " + request.getItems().size());

        System.out.println();
    }

    /*
        7) @Value / @Data (идея различия)
        - @Value: иммутабельный объект (final-поля, getters, equals/hashCode/toString, all-args ctor), без setter
        - @Data: мутабельный объект (getters+setters, equals/hashCode/toString и т.д.)
        Практика:
        - DTO часто подходят под @Value или record (Java 16+), доменная модель — осторожно с @Data
     */
    private static void demonstrateValueVsDataIdea() {
        System.out.println("7) @Value: иммутабельный объект (без сеттеров) + инварианты в конструкторе");

        LombokMoney money = LombokMoney.ofValidated("RUB", 100);
        System.out.println("money.getCurrency() → " + money.getCurrency());
        System.out.println("money.getAmount() → " + money.getAmount());
        System.out.println("money → " + money);

        System.out.println();
    }

    /*
        8) @Slf4j
        - Генерирует поле: private static final Logger log = LoggerFactory.getLogger(Класс.class);
        - Работает через SLF4J; нужна реализация логирования (в Spring Boot обычно уже есть)
     */
    private static void demonstrateSlf4j() {
        System.out.println("8) @Slf4j: логгер генерируется Lombok'ом (плюс остаётся обычный System.out)");
        log.info("Lombok @Slf4j demo: event=DEMO message=\"Logger field generated\"");
        System.out.println("Лог через log.info(...) отправлен (в зависимости от окружения может быть виден/скрыт)");

        System.out.println();
    }

    /*
        9) @SneakyThrows (осторожно)
        - Позволяет "пробросить" checked exception без объявления throws
        - Удобно в тестах/демо; в проде ухудшает читаемость контракта и скрывает обработку ошибок
     */
    private static void demonstrateSneakyThrows() {
        System.out.println("9) @SneakyThrows: скрывает checked-exception из сигнатуры метода (использовать осторожно)");

        String line = readFirstLineSneaky("first\nsecond\nthird");
        System.out.println("readFirstLineSneaky(...) → \"" + line + "\"");

        System.out.println();
    }

    /*
        10) @Cleanup (ресурсы)
        - Генерирует try/finally и вызывает close() автоматически
        - В проде чаще предпочтителен try-with-resources как стандартный механизм Java
     */
    @SneakyThrows
    private static void demonstrateCleanup() {
        System.out.println("10) @Cleanup: автоматическое закрытие ресурса (аналог try-with-resources)");

        @Cleanup BufferedReader reader = new BufferedReader(new StringReader("A\nB\nC"));
        System.out.println("reader.readLine() → " + reader.readLine());

        System.out.println();
    }

    // =================================
    // Lombok-модели для демонстрации
    // =================================

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

        public LombokSecretContainer(String name, String token) {
            this.name = name;
            this.token = token;
        }
    }

    @Value
    private static class LombokUserId {
        int value;

        public LombokUserId(int value) {
            if (value <= 0) {
                throw new IllegalArgumentException("value должен быть положительным: value=" + value);
            }
            this.value = value;
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

    // =================================
    // Вспомогательное
    // =================================

    private static boolean hasPublicMethod(Class<?> type, String methodName) {
        for (Method method : type.getMethods()) {
            if (method.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    private static String readFirstLineSneaky(String input) {
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            return reader.readLine();
        }
    }

    public static void main(String[] args) {
        demonstrateMechanismAndVisibilityInBytecode();
        demonstrateGetterSetter();
        demonstrateToString();
        demonstrateEqualsAndHashCode();
        demonstrateConstructorsAndNonNull();
        demonstrateBuilder();
        demonstrateValueVsDataIdea();
        demonstrateSlf4j();
        demonstrateSneakyThrows();
        demonstrateCleanup();
    }
}
