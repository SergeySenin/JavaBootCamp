package org.study.bootcamp.mishustin.interview.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ ИСКЛЮЧЕНИЙ В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * Исключение в Java — это “исключительное событие”, которое происходит
 * во время выполнения программы и нарушает нормальный ход инструкций.
 * Оно представлено объектом: выбрасывать и перехватывать можно только экземпляры `Throwable`
 * и его подклассов, потому что `Throwable` является общим предком всех ошибок и исключений.
 *
 * =====================================================================================================================
 *
 * Исключение может быть выброшено явно оператором `throw` или возникнуть
 * по решению JVM, когда она фиксирует “ненормальное условие” исполнения.
 *
 * Пример-схема 1: явный `throw` (исключение создаёт и выбрасывает код)
 *
 *     if (stackSize == 0) {
 *         throw new EmptyStackException();
 *     }
 *     // эта строка выполнится только если stackSize != 0
 *
 * Пример-схема 2: неявное исключение от JVM (исключение выбрасывается при выполнении операции)
 *
 *     int divisor = 0;
 *     int result = 10 / divisor; // JVM выбрасывает ArithmeticException
 *     // эта строка не выполнится
 *
 * =====================================================================================================================
 *
 * С момента возникновения исключения текущий метод не продолжает выполнение
 * “со следующей строки”: управление передаётся механизму обработки, который
 * ищет `catch` по правилу `instanceof` — блок подходит, если тип параметра
 * `catch` является классом исключения или его суперклассом; поиск начинается
 * в текущем `try`, затем продолжается выше по стеку вызовов.
 *
 * Пример-схема: поиск обработчика по типу (упрощённо)
 *
 *     try {
 *         risky();                        // здесь выброшен ArithmeticException
 *     } catch (NumberFormatException e) { // НЕ подходит: ArithmeticException !instanceof NumberFormatException
 *         ...
 *     } catch (RuntimeException e) {      // подходит: ArithmeticException instanceof RuntimeException == true
 *         ...
 *     }
 *
 * Пример-схема: если в текущем методе не найдено, идём вверх по стеку
 *
 *     main() -> service() -> risky()
 *                      ^        |
 *                      |  throw |
 *                      +--------+
 *     JVM ищет `catch`: сначала в risky(), затем в service(), затем в main()
 *
 * =====================================================================================================================
 *
 * Если обработчик найден, выполняется код `catch`, после чего выполнение
 * продолжается уже за пределами соответствующей конструкции `try/catch`.
 *
 * Пример-схема: обработчик найден
 *
 *     try {
 *         throw new IllegalArgumentException("bad input");
 *     } catch (RuntimeException e) {
 *         // обработка (лог/сообщение/альтернатива)
 *     }
 *     // выполнение продолжается здесь (после try/catch)
 *
 * =====================================================================================================================
 *
 * Если обработчика нет, исключение остаётся неперехваченным, и поток завершается; в
 * типичном случае для `main` это приводит к завершению приложения с выводом трассировки.
 *
 * Пример-схема: обработчика нет
 *
 *     main() {
 *         service();
 *     }
 *
 *     service() {
 *         risky(); // выбросили, но не поймали здесь
 *     }
 *
 *     risky() {
 *         throw new IllegalStateException("broken state");
 *     }
 *
 * Результат: нет подходящего `catch` в main/service/risky -> исключение неперехвачено -> поток завершается.
 *
 * =====================================================================================================================
 *
 * Важная практическая ценность исключения в том, что оно несёт диагностику:
 * сообщение и “снимок” стека выполнения на момент создания, что позволяет
 * локализовать источник проблемы и сохранить причинно-следственную цепочку при оборачивании.
 *
 * =====================================================================================================================
 *
 * С точки зрения языка все выбрасываемые сущности образуют иерархию от `Throwable`.
 * `Throwable` — общий предок всего, что можно `throw` и что может быть перехвачено в `catch`.
 * У `Throwable` две ключевые ветки: `Error` и `Exception`.
 *                                                                     Object
 * Error описывает сбои среды выполнения (JVM/окружение), которые        |
 * приложение обычно не может разумно предвидеть или восстановить.     Throwable
 *                                                                       |
 * Exception описывает исключительные ситуации уровня приложения,        +---------------+
 * которые в принципе могут быть обработаны.                             |               |
 *                                                                     Error       Exception
 * RuntimeException — подветка `Exception`, относящаяся к                |               |
 * UNCHECKED-категории; методы не обязаны объявлять её в                 +               +---------------+
 * `throws` и не обязаны перехватывать её для компиляции.                |               |               |
 *                                                                     UNCHECKED   прочие Exception    RuntimeException
 * CHECKED   (проверяемые   исключения) — все `Exception`, кроме                         |               |
 * `RuntimeException`; компилятор требует `try/catch` или `throws`.                CHECKED             UNCHECKED
 * UNCHECKED (непроверяемые исключения) — `RuntimeException`, `Error`
 * и их подклассы; компилятор не требует `catch` или `throws`.
 *
 * =====================================================================================================================
 *
 * @author Sergey
 */
public class Lesson10Part0Exceptions {

    // =================================================================================================================
    // 1) Базовый синтаксис: try / catch / finally
    // =================================================================================================================
    /*
        Пояснения к сценариям:
        [1.1] Возникает, когда в try происходит исключение, которое соответствует одному из catch (по типу).
              Это нормальная ситуация: исключение используется как сигнал, а catch — как точка обработки.

        [1.2] Возникает, когда в try исключения не было. Это тоже нормальная ситуация: catch пропускается,
              но finally выполняется, потому что finally — механизм гарантированной "уборки" при любом исходе.

        [1.3] Возникает, когда исключение было, но текущий метод не должен/не может его обработать:
              (а) catch слишком узкий и не подходит по типу, либо (б) обработку сознательно делегируют выше.
              Это хорошо, если решение действительно принимается на верхнем уровне;
              плохо, если это случайная "утечка" из-за неверного типа catch или отсутствия стратегии.
     */
    private static void demonstrateTryCatchFinally() {
        printSectionTitle("1) try / catch / finally: что выполняется и в каком порядке");

        System.out.println(
                "[1.1] Сценарий A: исключение в try -> выполняется catch -> затем finally -> затем код после try/catch"
        );
        try {
            System.out.println("  try: шаг 1 — до опасной операции");
            int divisor = 0;
            System.out.println("  try: шаг 2 — выполняем 10 / " + divisor);
            int result = 10 / divisor; // ArithmeticException
            System.out.println("  try: шаг 3 — result=" + result + " (не выполнится)");
        } catch (ArithmeticException exception) {
            System.out.println("  catch(ArithmeticException): перехватили -> "
                    + exception.getClass().getSimpleName() + ", message=\"" + exception.getMessage() + "\"");
        } finally {
            System.out.println("  finally: выполняется всегда (здесь обычно освобождают ресурсы)");
        }
        System.out.println("  after: выполнение продолжается после try/catch/finally");
        System.out.println();

        System.out.println(
                "[1.2] Сценарий B: исключения нет -> catch пропускается" +
                        " -> выполняется finally -> затем код после try/catch"
        );
        try {
            System.out.println("  try: шаг 1 — нормальная логика");
            int divisor = 2;
            int result = 10 / divisor;
            System.out.println("  try: шаг 2 — result=" + result);
        } catch (ArithmeticException exception) {
            System.out.println("  catch(ArithmeticException): (не выполнится)");
        } finally {
            System.out.println("  finally: выполняется даже при успешном завершении try");
        }
        System.out.println("  after: выполнение продолжается после try/catch/finally");
        System.out.println();

        System.out.println(
                "[1.3] Сценарий C: исключение в try, но catch не подходит" +
                        " -> finally выполняется -> исключение уходит выше по стеку"
        );
        try {
            callMethodThatThrowsButIsNotHandledHere();
            System.out.println("  after-call: (не выполнится)");
        } catch (IllegalStateException exception) {
            System.out.println("  catch(IllegalStateException): перехватили на уровне вызывающего метода -> "
                    + exception.getClass().getSimpleName() + ", message=\"" + exception.getMessage() + "\"");
        }
        System.out.println();
    }

    private static void callMethodThatThrowsButIsNotHandledHere() {
        try {
            System.out.println("  inner try: шаг 1 — сейчас выбросим IllegalStateException");
            throw new IllegalStateException("Невозможное состояние для продолжения выполнения: state=\"BROKEN\"");
        } catch (NumberFormatException exception) {
            System.out.println("  inner catch(NumberFormatException): (не подходит, не выполнится)");
        } finally {
            System.out.println("  inner finally: выполнится, даже если исключение не перехвачено здесь");
        }
        System.out.println("  inner after: (не выполнится, т.к. исключение ушло наружу)");
    }

    // =================================================================================================================
    // 2) Порядок catch, multi-catch, rethrow
    // =================================================================================================================
    /*
        Зачем нужна механика [2.1]:
        Выбор обработчика основан на типе; если сначала поставить общий тип (например, RuntimeException),
        то более конкретный обработчик ниже станет недостижимым (unreachable code). Поэтому компилятор
        запрещает порядок "Parent выше Child". Это защищает от логических ошибок в обработке.

        Смысл [2.2] Multi-catch:
        Когда реакция одинакова для нескольких исключений, можно избежать дублирования catch-блоков и уменьшить
        соблазн ловить слишком широкий тип (например, Exception). Параметр multi-catch является implicitly final.

        Смысл [2.3] Rethrow:
        Перехват на промежуточном уровне нужен, чтобы добавить контекст/логирование и при этом не терять тип
        исключения для верхнего уровня. Важно пробрасывать исходное исключение или сохранять его как cause.
     */
    private static void demonstrateCatchOrderMultiCatchRethrow() {
        printSectionTitle("2) Порядок catch, multi-catch, rethrow");

        System.out.println("[2.1] Порядок catch: сначала более конкретные типы, затем более общие");
        System.out.println("  Правило: catch(Parent) выше catch(Child) делать нельзя — код не скомпилируется.");
        System.out.println("  Демонстрация выполнения: выбрасываем IllegalArgumentException и ловим его узким catch.");
        try {
            System.out.println("  try: выбрасываем IllegalArgumentException");
            throw new IllegalArgumentException("Некорректный аргумент: argumentName=\"age\", value=-1");
        } catch (IllegalArgumentException exception) {
            System.out.println("  catch(IllegalArgumentException): перехват узким типом -> " + exception.getMessage());
        } catch (RuntimeException exception) {
            System.out.println("  catch(RuntimeException): (не выполнится, т.к. выше уже перехвачено)");
        }
        System.out.println();

        System.out.println("[2.2] Multi-catch: один catch для нескольких типов, когда обработка одинаковая");
        String number = "12O3";
        try {
            System.out.println("  try: парсим число: number=\"" + number + "\"");
            int parsed = Integer.parseInt(number); // NumberFormatException
            System.out.println("  try: parsed=" + parsed + " (не выполнится)");
        } catch (NumberFormatException | IllegalStateException exception) {
            System.out.println("  catch(NumberFormatException | IllegalStateException): общий обработчик -> "
                    + exception.getClass().getSimpleName()
                    + ", problemValue=\"" + number + "\"");
        }
        System.out.println();

        System.out.println("[2.3] Rethrow: перехватили для логирования/контекста и пробросили выше");
        try {
            rethrowWithPreservedTypes(true);
            System.out.println("  after: (не выполнится)");
        } catch (FirstBusinessCheckedException exception) {
            System.out.println("  catch(FirstBusinessCheckedException): перехватили на верхнем уровне -> "
                    + exception.getMessage());
        } catch (SecondBusinessCheckedException exception) {
            System.out.println("  catch(SecondBusinessCheckedException): перехватили на верхнем уровне -> "
                    + exception.getMessage());
        }
        System.out.println();
    }

    private static void rethrowWithPreservedTypes(boolean first)
            throws FirstBusinessCheckedException, SecondBusinessCheckedException {
        try {
            System.out.println("  rethrowWithPreservedTypes: try — выбираем, какое CHECKED-исключение выбросить");
            if (first) {
                throw new FirstBusinessCheckedException("Первый сценарий ошибки: code=\"FIRST\", input=\"true\"");
            }
            throw new SecondBusinessCheckedException("Второй сценарий ошибки: code=\"SECOND\", input=\"false\"");
        } catch (Exception exception) {
            System.out.println("  rethrowWithPreservedTypes: catch(Exception) — добавили лог и пробрасываем дальше: "
                    + exception.getClass().getSimpleName());
            throw exception; // проброс того же объекта; типы указаны в throws метода
        }
    }

    private static final class FirstBusinessCheckedException extends Exception {
        private FirstBusinessCheckedException(String message) {
            super(message);
        }
    }

    private static final class SecondBusinessCheckedException extends Exception {
        private SecondBusinessCheckedException(String message) {
            super(message);
        }
    }

    // =================================================================================================================
    // 3) throw vs throws и контракт метода
    // =================================================================================================================
    /*
        Выжимка "throw vs throws":

        `throw`:
        - Оператор в теле метода: "прямо сейчас выбросить исключение".
        - Используется, когда обнаружено нарушение контракта/состояния или невозможно продолжать текущую операцию.

        `throws`:
        - Часть сигнатуры: "этот метод может выбросить такие исключения".
        - В первую очередь важен для CHECKED: действует правило "catch or declare".

        Зачем пробрасывать выше:
        - Низкий уровень (утилита/парсер/репозиторий) часто не знает, что делать "по политике"; верхний уровень
          (граница сценария) знает, как реагировать (остановить, повторить, показать сообщение и т.п.).
        - Для CHECKED проброс фиксируется в API через `throws`, заставляя вызывающего принять решение.

        Опасности и практики:
        - Опасность: "throws Exception" или ловля "catch(Exception)" без стратегии размывают контракт и скрывают смысл.
        - Практика: ловить только те исключения, которые реально умеешь обработать;
          если добавляешь контекст — сохраняй исходное исключение как cause (не теряй первопричину).
        - Практика: не превращать всё в UNCHECKED ради отсутствия compiler требований
          (Oracle отдельно предупреждает о таком соблазне для `RuntimeException`).
     */
    private static void demonstrateThrowVsThrowsContract() {
        printSectionTitle("3) throw vs throws: отличие и влияние на контракт");

        System.out.println("[3.1] throw: выбросили исключение прямо здесь и сейчас");
        try {
            System.out.println("  before: вызываем validatePositiveAmount(-10)");
            validatePositiveAmount(-10);
            System.out.println("  after: (не выполнится)");
        } catch (IllegalArgumentException exception) {
            System.out.println("  catch(IllegalArgumentException): перехватили -> " + exception.getMessage());
        }
        System.out.println();

        System.out.println(
                "[3.2] throws (CHECKED): метод объявляет, что может выбросить исключение, и вызывающий обязан решить"
        );
        System.out.println("  Вариант A: вызывающий перехватывает CHECKED-исключение через try/catch");
        try {
            String line = readFirstLineChecked("first line\nsecond line");
            System.out.println("  readFirstLineChecked: success -> line=\"" + line + "\"");
        } catch (IOException exception) {
            System.out.println("  catch(IOException): обработали CHECKED-исключение -> " + exception.getMessage());
        }
        System.out.println();

        System.out.println("  Вариант B: вызывающий пробрасывает дальше (в демонстрации — через отдельный метод)");
        try {
            callMethodThatDeclaresThrows();
            System.out.println("  after: выполнение продолжается после успешного callMethodThatDeclaresThrows()");
        } catch (IOException exception) {
            System.out.println(
                    "  catch(IOException): перехватили уже на более верхнем уровне -> " + exception.getMessage()
            );
        }
        System.out.println();
    }

    private static void validatePositiveAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной: amount=" + amount);
        }
    }

    /*
     * Пример CHECKED-контракта: метод объявляет `throws IOException`.
     * Даже если источник строковый, сигнатура демонстрирует сам принцип "catch or declare".
     */
    private static String readFirstLineChecked(String source) throws IOException {
        Objects.requireNonNull(source, "source обязателен: source=null");
        try (BufferedReader reader = new BufferedReader(new StringReader(source))) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Источник пуст: expectedFirstLine=true, actualFirstLine=null");
            }
            return line;
        }
    }

    /*
     * Этот метод демонстративно "делегирует" решение наверх: он сам не ловит IOException и объявляет throws.
     */
    private static void callMethodThatDeclaresThrows() throws IOException {
        System.out.println("  callMethodThatDeclaresThrows: внутри метода, который объявляет throws IOException");
        String line = readFirstLineChecked("config.version=1\nconfig.name=demo");
        System.out.println("  callMethodThatDeclaresThrows: прочитали line=\"" + line + "\"");
    }

    // =================================================================================================================
    // 4) Создание собственных исключений: naming, поля, сообщения, cause
    // =================================================================================================================
    /*
        Задача пункта:
        - Показать разницу между "доменной проблемой" (CHECKED) и "ошибкой контракта" (UNCHECKED).
        - Показать, что сообщение исключения обязано нести контекст (ключевые значения).
        - Показать, как хранить и передавать первопричину через cause (цепочка причин).
     */
    private static void demonstrateCustomExceptions() {
        printSectionTitle("4) Собственные исключения: naming, поля, сообщения, cause");

        System.out.println("[4.1] CHECKED-доменное исключение: вызывающий обязан принять решение (catch/throws)");
        try {
            simulateCheckedBusinessRuleViolation("RU-0000123", -50);
            System.out.println("  after: (не выполнится)");
        } catch (InsufficientFundsCheckedException exception) {
            System.out.println("  catch(InsufficientFundsCheckedException): " + exception.getMessage());
            System.out.println("  details: accountId=\"" + exception.accountId + "\", deficit=" + exception.deficit);
        }
        System.out.println();

        System.out.println("[4.2] UNCHECKED-исключение контракта: ошибка использования API (IllegalArgumentException)");
        try {
            createUserUnchecked("   ");
            System.out.println("  after: (не выполнится)");
        } catch (InvalidUsernameRuntimeException exception) {
            System.out.println("  catch(InvalidUsernameRuntimeException): " + exception.getMessage());
        }
        System.out.println();

        System.out.println("[4.3] Cause: оборачиваем первопричину и сохраняем её как cause");
        try {
            parsePortOrThrowDomainException("port=ABC");
            System.out.println("  after: (не выполнится)");
        } catch (ConfigurationCheckedException exception) {
            System.out.println("  catch(ConfigurationCheckedException): " + exception.getMessage());
            Throwable cause = exception.getCause();
            System.out.println("  cause: "
                    + (cause == null ? "null" : cause.getClass().getSimpleName() + ": " + cause.getMessage()));
        }
        System.out.println();
    }

    private static void simulateCheckedBusinessRuleViolation(String accountId, int currentBalance)
            throws InsufficientFundsCheckedException {
        int paymentAmount = 100;
        if (currentBalance - paymentAmount < 0) {
            int deficit = (paymentAmount - currentBalance);
            throw new InsufficientFundsCheckedException(
                    "Недостаточно средств для оплаты: accountId=\"" + accountId + "\", balance=" + currentBalance
                            + ", paymentAmount=" + paymentAmount + ", deficit=" + deficit, accountId, deficit
            );
        }
    }

    private static void createUserUnchecked(String username) {
        if (username == null || username.isBlank()) {
            throw new InvalidUsernameRuntimeException(
                    "username обязателен и не должен быть пустым: username=\"" + username + "\""
            );
        }
        System.out.println("  createUserUnchecked: created username=\"" + username.trim() + "\"");
    }

    private static void parsePortOrThrowDomainException(String rawConfigLine) throws ConfigurationCheckedException {
        try {
            String rawValue = rawConfigLine.substring(rawConfigLine.indexOf('=') + 1);
            Integer.parseInt(rawValue);
        } catch (RuntimeException exception) {
            throw new ConfigurationCheckedException(
                    "Некорректная конфигурация порта: line=\"" + rawConfigLine + "\"",
                    exception
            );
        }
    }

    private static final class InsufficientFundsCheckedException extends Exception {
        private final String accountId;
        private final int deficit;

        private InsufficientFundsCheckedException(String message, String accountId, int deficit) {
            super(message);
            this.accountId = accountId;
            this.deficit = deficit;
        }
    }

    private static final class InvalidUsernameRuntimeException extends RuntimeException {
        private InvalidUsernameRuntimeException(String message) {
            super(message);
        }
    }

    private static final class ConfigurationCheckedException extends Exception {
        private ConfigurationCheckedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // =================================================================================================================
    // 5) Оборачивание исключений (wrapping): контекст, цепочка причин (getCause)
    // =================================================================================================================
    /*
        Смысл wrapping:
        - Добавить контекст, понятный на текущем уровне абстракции.
        - Не терять первопричину: передать её как cause, чтобы цепочка была доступна через getCause().
     */
    private static void demonstrateWrappingAndCause() {
        printSectionTitle("5) Wrapping: контекст + цепочка причин (cause)");

        try {
            repositoryParseInteger("orderId=12O3"); // 'O' вместо '0'
            System.out.println("  after: (не выполнится)");
        } catch (OrderReadRuntimeException exception) {
            System.out.println("  catch(OrderReadRuntimeException): " + exception.getMessage());

            Throwable firstCause = exception.getCause();
            System.out.println("  cause level 1: " + (firstCause == null ? "null" :
                    firstCause.getClass().getSimpleName() + ": " + firstCause.getMessage()));

            Throwable secondCause = firstCause == null ? null : firstCause.getCause();
            System.out.println("  cause level 2: " + (secondCause == null ? "null" :
                    secondCause.getClass().getSimpleName() + ": " + secondCause.getMessage()));
        }
        System.out.println();
    }

    private static void repositoryParseInteger(String rawLine) {
        try {
            String rawValue = rawLine.substring(rawLine.indexOf('=') + 1);
            Integer.parseInt(rawValue);
        } catch (RuntimeException exception) {
            // На "репозиторном" уровне делаем сообщение про входные данные и сохраняем первопричину.
            throw new OrderReadRuntimeException(
                    "Не удалось прочитать числовое значение: line=\"" + rawLine + "\"", exception
            );
        }
    }

    private static final class OrderReadRuntimeException extends RuntimeException {
        private OrderReadRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // =================================================================================================================
    // 6) try-with-resources: AutoCloseable, suppressed исключения, порядок закрытия
    // =================================================================================================================
    /*
        Ключевое:
        - try-with-resources автоматически вызывает close() у ресурсов.
        - Если исключение было в try-блоке и в close(), то исключение из close()
          становится suppressed, и доступно через Throwable.getSuppressed().
        - Ресурсы закрываются в обратном порядке объявления (LIFO). (Ниже демонстрация по выводу close().)
     */
    private static void demonstrateTryWithResourcesSuppressed() {
        printSectionTitle("6) try-with-resources: suppressed + порядок закрытия");

        System.out.println("[6.1] Исключение в try и исключение в close(): close-исключение попадает в suppressed");
        try (DemoClosableResource resource = new DemoClosableResource("R1", true)) {
            System.out.println("  try: выполняем работу и бросаем исключение");
            throw new IllegalStateException("Ошибка в try: operation=\"write\", resource=\"R1\"");
        } catch (Exception exception) {
            System.out.println("  catch(Exception): thrown=" + exception.getClass().getSimpleName()
                    + ", message=\"" + exception.getMessage() + "\"");

            Throwable[] suppressed = exception.getSuppressed();
            System.out.println("  suppressed.length=" + suppressed.length);
            for (Throwable suppressedException : suppressed) {
                System.out.println("  suppressed -> " + suppressedException.getClass().getSimpleName()
                        + ": " + suppressedException.getMessage());
            }
        }
        System.out.println();

        System.out.println("[6.2] Два ресурса: закрываются в обратном порядке объявления");
        try (DemoClosableResource first = new DemoClosableResource("R1", false);
             DemoClosableResource second = new DemoClosableResource("R2", false)) {
            System.out.println("  try: внутри блока; ожидаем close сначала для R2, потом для R1");
        } catch (Exception exception) {
            System.out.println("  catch(Exception): " + exception.getMessage());
        }
        System.out.println();
    }

    private static final class DemoClosableResource implements AutoCloseable {
        private final String name;
        private final boolean closeThrows;

        private DemoClosableResource(String name, boolean closeThrows) {
            this.name = name;
            this.closeThrows = closeThrows;
            System.out.println("  open: name=\"" + name + "\", closeThrows=" + closeThrows);
        }

        @Override
        public void close() throws Exception {
            System.out.println("  close: name=\"" + name + "\"");
            if (closeThrows) {
                throw new IOException("Ошибка при закрытии ресурса: name=\"" + name + "\"");
            }
        }
    }

    // =================================================================================================================
    // 7) Частые runtime-исключения и типовые причины
    // =================================================================================================================
    private static void demonstrateCommonRuntimeExceptions() {
        printSectionTitle("7) Частые RuntimeException: причины и примеры");

        System.out.println("[7.1] NullPointerException: обращение к null (часто из-за отсутствия валидации)");
        try {
            Objects.requireNonNull(null, "input обязателен: input=null");
        } catch (NullPointerException exception) {
            System.out.println("  catch(NullPointerException): " + exception.getMessage());
        }
        System.out.println();

        System.out.println("[7.2] IllegalArgumentException: аргумент нарушает контракт метода");
        try {
            validatePortRange(70000);
        } catch (IllegalArgumentException exception) {
            System.out.println("  catch(IllegalArgumentException): " + exception.getMessage());
        }
        System.out.println();

        System.out.println("[7.3] IllegalStateException: состояние объекта/системы не позволяет выполнить операцию");
        try {
            simulateStateViolation(false);
        } catch (IllegalStateException exception) {
            System.out.println("  catch(IllegalStateException): " + exception.getMessage());
        }
        System.out.println();

        System.out.println("[7.4] IndexOutOfBoundsException: индекс вне диапазона");
        try {
            int[] values = new int[] { 1, 2, 3 };
            int value = values[5];
            System.out.println("  value=" + value + " (не выполнится)");
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println(
                    "  catch(ArrayIndexOutOfBoundsException): indexOutOfBounds -> " + exception.getMessage()
            );
        }
        System.out.println();

        System.out.println("[7.5] ClassCastException: неверное приведение типов");
        try {
            Object text = "abc";
            Integer number = (Integer) text;
            System.out.println("  number=" + number + " (не выполнится)");
        } catch (ClassCastException exception) {
            System.out.println("  catch(ClassCastException): " + exception.getMessage());
        }
        System.out.println();
    }

    private static void validatePortRange(int port) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("port вне диапазона [1..65535]: port=" + port);
        }
    }

    private static void simulateStateViolation(boolean isInitialized) {
        if (!isInitialized) {
            throw new IllegalStateException("Операция недоступна: isInitialized=false");
        }
    }

    // =================================================================================================================
    // 8) Практика обработки: где ловить (слои), где пробрасывать
    // =================================================================================================================
    /*
        Идея:
        - Нижние уровни (утилиты/доступ к данным) фиксируют факт сбоя и поднимают исключение.
        - Средний уровень добавляет доменный контекст и может обернуть причину.
        - Верхний уровень (граница сценария) принимает решение: как завершить операцию и что показать пользователю.
     */
    private static void demonstrateWhereToCatchAndWhereToThrow() {
        printSectionTitle("8) (план 11) Где ловить и где пробрасывать: модель \"repo -> service -> controller\"");

        System.out.println(
                "[8.1] Репозиторий бросает IOException (CHECKED) -> сервис оборачивает -> контроллер ловит и мапит"
        );
        ApiResult result = controllerGetUserDisplayName("userId=123");
        System.out.println("  controller result: status=" + result.statusCode + ", message=\"" + result.message + "\"");
        System.out.println();
    }

    private static ApiResult controllerGetUserDisplayName(String userId) {
        try {
            String displayName = serviceLoadUserDisplayName(userId);
            return new ApiResult(200, "OK: displayName=\"" + displayName + "\"");
        } catch (UserReadException exception) {
            // На границе сценария скрываем детали реализации, но сохраняем трассировку в логах (в демо — просто текст).
            return new ApiResult(503, "Ошибка чтения пользователя: userId=\"" + userId + "\"");
        }
    }

    private static String serviceLoadUserDisplayName(String userId) {
        try {
            return repositoryReadUserDisplayName(userId);
        } catch (IOException exception) {
            // Добавляем доменный контекст и сохраняем cause.
            throw new UserReadException("Сервис не смог загрузить пользователя: userId=\"" + userId + "\"", exception);
        }
    }

    private static String repositoryReadUserDisplayName(String userId) throws IOException {
        // Демо: имитируем внешний сбой (например, I/O/сеть/файл)
        throw new IOException("I/O ошибка чтения: source=\"user-storage\", userId=\"" + userId + "\"");
    }

    private static final class ApiResult {
        private final int statusCode;
        private final String message;

        private ApiResult(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }
    }

    private static final class UserReadException extends RuntimeException {
        private UserReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // =================================================================================================================
    // 9) Антипаттерны: “глотание”, ловля Throwable/Exception без стратегии, потеря stack trace
    // =================================================================================================================
    private static void demonstrateAntipatterns() {
        printSectionTitle("9) Антипаттерны обработки исключений");

        System.out.println("[9.1] Антипаттерн: пустой catch (исключение потеряно, причина не диагностируется)");
        swallowExceptionAntipattern();
        System.out.println("  result: выполнение продолжилось, но ошибка была скрыта");
        System.out.println();

        System.out.println("[9.2] Антипаттерн: потеря первопричины (throw new X без cause)");
        try {
            loseCauseAntipattern();
        } catch (RuntimeException exception) {
            System.out.println(
                    "  caught: " + exception.getClass().getSimpleName() + ", message=\"" + exception.getMessage() + "\""
            );
            System.out.println("  cause: " + (exception.getCause() == null ? "null (потеряно)" :
                            exception.getCause().getClass().getSimpleName()));
        }
        System.out.println();
    }

    private static void swallowExceptionAntipattern() {
        try {
            Integer.parseInt("12O3");
        } catch (NumberFormatException exception) {
            // ПЛОХО: исключение потеряно.
            // НЕТ: контекста, лога, метрики, проброса.
        }
    }

    private static void loseCauseAntipattern() {
        try {
            Integer.parseInt("12O3");
        } catch (NumberFormatException exception) {
            // ПЛОХО: первопричина не сохранена как cause.
            throw new RuntimeException("Не удалось обработать число: value=\"12O3\"");
        }
    }

    // =================================================================================================================
    // 10) Логирование и сообщения: что писать, что не писать, уровень, корреляция/контекст
    // =================================================================================================================
    private static void demonstrateLoggingGuidelines() {
        printSectionTitle("10) (план 13) Логирование: контекст и исключение");

        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Lesson10Part0Exceptions.class.getName());

        System.out.println("[10.1] Пример: логируем событие с контекстом и исключением (stack trace сохраняется)");
        try {
            Integer.parseInt("12O3");
        } catch (NumberFormatException exception) {
            String message = "Невозможно распарсить число: field=\"orderId\", rawValue=\"12O3\"";
            logger.log(java.util.logging.Level.WARNING, message, exception);
            System.out.println("  logged: level=WARNING, message=\"" + message
                    + "\", exception=" + exception.getClass().getSimpleName());
        }
        System.out.println();

        System.out.println("[10.2] Правило: не дублировать одно и то же исключение на каждом уровне без причины");
        System.out.println("  Хорошая практика: логировать один раз на границе сценария или там, где принято решение.");
        System.out.println("  Хорошая практика: добавлять контекст в сообщение и сохранять cause при wrapping.");
        System.out.println();
    }

    // =================================================================================================================
    // 11) Маппинг исключений в API/CLI: коды/ответы, пользовательские сообщения
    // =================================================================================================================
    private static void demonstrateMappingToApiOrCli() {
        printSectionTitle("11) (план 14) Маппинг исключений в ответы API/CLI");

        System.out.println("[11.1] Пример: доменное исключение -> 400, операционный сбой -> 503, неизвестное -> 500");
        System.out.println("  input case A: invalid user input");
        System.out.println(
                "  mapped: " + mapExceptionToApiResponse(new InvalidUsernameRuntimeException("username пуст"))
        );

        System.out.println("  input case B: external dependency failed");
        System.out.println("  mapped: " + mapExceptionToApiResponse(
                new UserReadException("storage unavailable", new IOException("timeout"))));

        System.out.println("  input case C: unexpected");
        System.out.println("  mapped: " + mapExceptionToApiResponse(new RuntimeException("unknown")));
        System.out.println();
    }

    private static String mapExceptionToApiResponse(RuntimeException exception) {
        if (exception instanceof InvalidUsernameRuntimeException) {
            return "status=400, message=\"Некорректный запрос\"";
        }
        if (exception instanceof UserReadException) {
            return "status=503, message=\"Сервис временно недоступен\"";
        }
        return "status=500, message=\"Внутренняя ошибка\"";
    }

    // =================================================================================================================
    // 12) Тестирование исключений: assertThrows, проверка сообщения/cause, негативные сценарии
    // =================================================================================================================
    /*
        В этом файле JUnit не подключён, поэтому ниже — демонстрация как "шаблон кода" через вывод строк.
        Смысл assertThrows: проверить тип исключения и затем проверить message/cause.
     */
    private static void demonstrateTestingExceptionsTemplates() {
        printSectionTitle("12) Тестирование исключений: шаблоны (assertThrows)");

        System.out.println("[12.1] Шаблон JUnit 5 assertThrows (проверка типа + сообщения)");
        System.out.println("""
            // import static org.junit.jupiter.api.Assertions.assertThrows;
            //
            // @Test
            // void shouldThrowIllegalArgumentExceptionWhenAmountIsNotPositive() {
            //     IllegalArgumentException exception =
            //             assertThrows(IllegalArgumentException.class, () -> validatePositiveAmount(0));
            //     assertEquals("Сумма должна быть положительной: amount=0", exception.getMessage());
            // }
            """);

        System.out.println("[12.2] Шаблон JUnit 5 assertThrows (проверка cause при wrapping)");
        System.out.println("""
            // @Test
            // void shouldKeepCauseWhenWrapping() {
            //     OrderReadRuntimeException exception =
            //             assertThrows(OrderReadRuntimeException.class, () -> repositoryParseInteger("orderId=12O3"));
            //     assertNotNull(exception.getCause());
            //     assertTrue(exception.getCause() instanceof NumberFormatException);
            // }
            """);
        System.out.println();
    }

    public static void main(String[] args) {
        demonstrateTryCatchFinally();
        demonstrateCatchOrderMultiCatchRethrow();
        demonstrateThrowVsThrowsContract();
        demonstrateCustomExceptions();
        demonstrateWrappingAndCause();
        demonstrateTryWithResourcesSuppressed();
        demonstrateCommonRuntimeExceptions();
        demonstrateWhereToCatchAndWhereToThrow();
        demonstrateAntipatterns();
        demonstrateLoggingGuidelines();
        demonstrateMappingToApiOrCli();
        demonstrateTestingExceptionsTemplates();
    }

    private static void printSectionTitle(String title) {
        System.out.println();
        System.out.println("=====================================================================================");
        System.out.println(title);
        System.out.println("=====================================================================================");
    }
}
