package org.study.bootcamp.interview_practice._05_package;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ КЛАССОВ В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * - class          — ...;
 *                    ... (...);
 *                    ... (...)
 * - final          — ...;
 *                    ... (...);
 *                    ... (...)
 * - abstract class — ...;
 *                    ... (...);
 *                    ... (...)
 * - interface      — ...;
 *                    ... (...);
 *                    ... (...)
 * - enum           — ...;
 *                    ... (...);
 *                    ... (...)
 * - record         — ...;
 *                    ... (...);
 *                    ... (...)
 * - nested classes — ...;
 *                    ... (...);
 *                    ... (...)
 *
 * @author Sergey
 */
public class _05_2_TypesOfClasses {

    private static void demonstrateRegularClass() {
        System.out.println("1) ...");
        System.out.println("Сценарий: ...");

        UserAccount account = new UserAccount("alice", 100);
        System.out.println("..." + account);
        account.deposit(50);
        System.out.println("..." + account);

        System.out.println(
                "Вывод: ..."
        );
        System.out.println();
    }

    private static void demonstrateFinalClassAndFinalMethod() {
        System.out.println("2) ...");
        System.out.println("Сценарий: ...");

        String normalized = UserNameNormalizer.normalize("  Alice  ");
        System.out.println("...: normalize(\"  Alice  \") → \"" + normalized + "\"");

        System.out.println(
                "Вывод: ..."
        );
        System.out.println();
    }

    private static void demonstrateAbstractClass() {
        System.out.println("3) ...");

        /*
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
         */

        ReportExporter csv = new CsvReportExporter();
        ReportExporter json = new JsonReportExporter();

        System.out.println("Сценарий: ...");
        csv.export("users");
        json.export("users");

        System.out.println(
                "Вывод: ..."
        );
        System.out.println();
    }

    private static void demonstrateInterface() {
        System.out.println("4) ...");

        /*
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
         */

        Notifier emailNotifier = new EmailNotifier();
        Notifier smsNotifier = new SmsNotifier();

        System.out.println("Сценарий: ...");
        emailNotifier.notify("user@example.com", "Welcome");
        smsNotifier.notify("+79991234567", "Code: 1234");

        System.out.println();
        System.out.println("A) ...");
        System.out.println("Notifier.format(\"Hello\") → " + Notifier.format("Hello"));

        System.out.println();
        System.out.println("B) ...");
        System.out.println("emailNotifier.channelName() → " + emailNotifier.channelName());
        System.out.println("smsNotifier.channelName()   → " + smsNotifier.channelName());

        System.out.println();
        System.out.println("C) ...");
        System.out.println("emailNotifier.preview(\"  hi  \") → " + emailNotifier.preview("  hi  "));

        System.out.println("Вывод: ...");
        System.out.println();
    }

    private static void demonstrateEnum() {
        System.out.println("5) ...");

        /*
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
         */

        System.out.println("Сценарий: ...");
        AccessLevel level = AccessLevel.ADMIN;

        System.out.println("...");
        System.out.println(
                "level = " + level + ", priority=" + level.getPriority() + ", isDangerous=" + level.isDangerous()
        );

        System.out.println("...");
        for (AccessLevel item : AccessLevel.values()) {
            System.out.println(" - " + item + " (priority=" + item.getPriority() + ")");
        }

        System.out.println("Вывод: ...");
        System.out.println();
    }

    private static void demonstrateRecord() {
        System.out.println("6) ...");

        Money price = new Money("RUB", 199);
        Money samePrice = new Money("RUB", 199);

        System.out.println("Сценарий: ...");
        System.out.println("...");
        System.out.println("price                   → " + price);
        System.out.println("price.currency()        → " + price.currency());
        System.out.println("price.amount()          → " + price.amount());
        System.out.println("price.equals(samePrice) → " + price.equals(samePrice) + " (...)");

        System.out.println("Вывод: ...");
        System.out.println();
    }

    private static void demonstrateNestedTypes() {
        System.out.println("7) ...");

        /*
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
        ...
         */

        Outer outer = new Outer("outer-state");

        Outer.StaticNested staticNested = new Outer.StaticNested("static-nested");
        System.out.println("A) static nested: " + staticNested.describe());
        System.out.println("   ...");

        Outer.Inner inner = outer.new Inner("inner");
        System.out.println("B) inner: " + inner.describe());
        System.out.println("   ...");

        // Красиво прежний нижний вид метода напиши, а то налепил без пробелов...
    }

    private static final class UserNameNormalizer {
        private UserNameNormalizer() {
        }

        public static String normalize(String rawUserName) {
            if (rawUserName == null || rawUserName.isBlank()) {
                throw new IllegalArgumentException("rawUserName must be not blank; rawUserName=" + rawUserName);
            }
            return rawUserName.trim().toLowerCase();
        }
    }

    private static final class UserAccount {
        private final String userName;
        private int balance;

        public UserAccount(String userName, int balance) {
            if (userName == null || userName.isBlank()) {
                throw new IllegalArgumentException("userName must be not blank; userName=" + userName);
            }
            if (balance < 0) {
                throw new IllegalArgumentException("balance must be >= 0; balance=" + balance);
            }
            this.userName = userName.trim();
            this.balance = balance;
        }

        public final void deposit(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("amount must be positive; amount=" + amount);
            }
            balance += amount;
        }

        @Override
        public String toString() {
            return "UserAccount{userName=\"" + userName + "\", balance=" + balance + "}";
        }
    }

    private abstract static class ReportExporter {
        public final void export(String entityName) {
            validate(entityName);
            String payload = buildPayload(entityName);
            write(payload);
        }

        protected void validate(String entityName) {
            if (entityName == null || entityName.isBlank()) {
                throw new IllegalArgumentException("entityName must be not blank; entityName=" + entityName);
            }
        }

        protected abstract String buildPayload(String entityName);

        protected void write(String payload) {
            System.out.println("WRITE: " + payload);
        }
    }

    private static final class CsvReportExporter extends ReportExporter {
        @Override
        protected String buildPayload(String entityName) {
            return "CSV export for entity=\"" + entityName + "\"";
        }
    }

    private static final class JsonReportExporter extends ReportExporter {
        @Override
        protected String buildPayload(String entityName) {
            return "{\"export\":\"" + entityName + "\"}";
        }
    }

    private interface Notifier {
        void notify(String destination, String message);

        default String channelName() {
            return "generic-channel";
        }

        default String preview(String message) {
            return channelName() + " preview: " + format(message);
        }

        static String format(String message) {
            if (message == null || message.isBlank()) {
                return "[empty message]";
            }
            return "[" + message.trim() + "]";
        }
    }

    private static final class EmailNotifier implements Notifier {
        @Override
        public void notify(String destination, String message) {
            if (destination == null || destination.isBlank()) {
                throw new IllegalArgumentException("destination must be not blank; destination=" + destination);
            }
            System.out.println("Email → " + destination.trim() + ": " + Notifier.format(message));
        }

        @Override
        public String channelName() {
            return "email";
        }
    }

    private static final class SmsNotifier implements Notifier {
        @Override
        public void notify(String destination, String message) {
            if (destination == null || destination.isBlank()) {
                throw new IllegalArgumentException("destination must be not blank; destination=" + destination);
            }
            System.out.println("SMS → " + destination.trim() + ": " + Notifier.format(message));
        }

        @Override
        public String channelName() {
            return "sms";
        }
    }

    private enum AccessLevel {
        GUEST(1, false),
        USER(2, false),
        MODERATOR(3, true),
        ADMIN(4, true);

        private final int priority;
        private final boolean dangerous;

        AccessLevel(int priority, boolean dangerous) {
            this.priority = priority;
            this.dangerous = dangerous;
        }

        public int getPriority() {
            return priority;
        }

        public boolean isDangerous() {
            return dangerous;
        }
    }

    private record Money(String currency, int amount) {
        public Money {
            if (currency == null || currency.isBlank()) {
                throw new IllegalArgumentException("currency must be not blank; currency=" + currency);
            }
            if (amount < 0) {
                throw new IllegalArgumentException("amount must be >= 0; amount=" + amount);
            }
            currency = currency.trim().toUpperCase();
        }
    }

    private static final class Outer {
        private final String outerState;

        private Outer(String outerState) {
            if (outerState == null || outerState.isBlank()) {
                throw new IllegalArgumentException("outerState must be not blank; outerState=" + outerState);
            }
            this.outerState = outerState;
        }

        private static final class StaticNested {
            private final String name;

            private StaticNested(String name) {
                if (name == null || name.isBlank()) {
                    throw new IllegalArgumentException("name must be not blank; name=" + name);
                }
                this.name = name;
            }

            public String describe() {
                return "StaticNested{name=\"" + name + "\"}";
            }
        }

        private final class Inner {
            private final String name;

            private Inner(String name) {
                if (name == null || name.isBlank()) {
                    throw new IllegalArgumentException("name must be not blank; name=" + name);
                }
                this.name = name;
            }

            public String describe() {
                return "Inner{name=\"" + name + "\", outerState=\"" + outerState + "\"}";
            }
        }
    }

    private interface Task {
        String run();
    }

    public static void main(String[] args) {
        demonstrateRegularClass();
        demonstrateFinalClassAndFinalMethod();
        demonstrateAbstractClass();
        demonstrateInterface();
        demonstrateEnum();
        demonstrateRecord();
        demonstrateNestedTypes();
    }
}
