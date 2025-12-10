package org.study.bootcamp.interview_practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ МОДИФИКАТОРОВ ДОСТУПА, STATIC/FINAL И АННОТАЦИЙ В JAVA С ИХ ОСОБЕННОСТЯМИ
 *
 * @author Sergey
 */
public class _07_AccessStaticAnnotations {

    public static void main(String[] args) {
        demonstrateAccessModifiers();
        demonstrateStaticMembers();
        demonstrateFinalUsage();
        demonstrateAnnotations();
        summarizeTakeaways();
    }

    // 1) Модификаторы доступа: показываем, что видно из одного пакета и из наследника
    private static void demonstrateAccessModifiers() {
        System.out.println("1) Модификаторы доступа: public/private/protected/default");

        AccessSample sample = new AccessSample();
        sample.publicName = "Доступен всем"; // public — можно читать и изменять откуда угодно
        sample.city = "Виден в пакете"; // default (package-private) — виден только в пакете
        // sample.privateToken = "Секрет"; // private — раскомментируйте, чтобы увидеть ошибку доступа
        sample.setPrivateToken("Секрет задаём через метод"); // контроль через геттер/сеттер
        sample.protectedNote = "Виден в пакете и наследниках"; // protected допустим в этом пакете

        sample.printSummary();

        System.out.println("Наследник в том же пакете видит protected и default: (класс SupportEngineer)");
        SupportEngineer engineer = new SupportEngineer();
        engineer.fillContacts();
        engineer.printSummary();

        System.out.println("В другом пакете default недоступен, protected виден только через наследование");
        System.out.println("private всегда скрыт, поэтому поля меняем только методами класса");
        System.out.println();
    }

    // 2) static — общее состояние и методы, принадлежащие классу, а не объекту
    private static void demonstrateStaticMembers() {
        System.out.println("2) static: общее для всех экземпляров");

        StaticCounter first = new StaticCounter();
        StaticCounter second = new StaticCounter();
        System.out.println("Значение счётчика после создания двух объектов: " + StaticCounter.getCreatedCount());

        System.out.println("Статический метод доступен без объектов: MathHelper.square(5) = " + MathHelper.square(5));
        System.out.println("Статические блоки выполняются один раз при загрузке класса — (вывод при первом обращении)");

        System.out.println("Внутри static нельзя использовать this, так как метод не привязан к конкретному объекту");
        System.out.println();
    }

    // 3) final — фиксируем значение, запрет на наследование или переопределение
    private static void demonstrateFinalUsage() {
        System.out.println("3) final: закрепляем неизменяемость");

        System.out.println("Константа PI: " + Constants.PI); // static final — общая константа
        FinalExample example = new FinalExample();
        example.printConfig();

        System.out.println("Финальный метод нельзя переопределить: (BasePaymentService#describe())");
        System.out.println("Финальный класс нельзя наследовать: (class ImmutableComponent)");
        System.out.println();
    }

    // 4) Аннотации: подсказки для компилятора и инструментов, не меняют логику
    private static void demonstrateAnnotations() {
        System.out.println("4) Аннотации: метаданные для проверок и инструментов");

        AnnotationDemo demo = new AnnotationDemo();
        demo.safeCallDeprecated(); // вызов обёрнут, чтобы показать предупреждение @Deprecated

        System.out.println("SuppressWarnings позволяет локально скрыть предупреждение (реализация safeCallDeprecated)");
        System.out.println();
    }

    // 5) Итоговый блок-памятка
    private static void summarizeTakeaways() {
        System.out.println("5) Кратко:");
        System.out.println(
                "public — видно везде;" +
                " protected — в пакете и наследниках;" +
                " default — только в пакете;" +
                " private — только в классе"
        );
        System.out.println("static принадлежит классу, разделяется всеми экземплярами;" +
                " final фиксирует значение/метод/класс");
        System.out.println("Аннотации дают метаданные и помогают инструментам");
        System.out.println();
    }

    // Показ доступности полей с разными модификаторами
    private static class AccessSample {
        public String publicName = "";
        private String privateToken = "";
        protected String protectedNote = "";
        String city = ""; // default (package-private)

        public String getPrivateToken() {
            return privateToken;
        }

        public void setPrivateToken(String privateToken) {
            if (privateToken == null || privateToken.isBlank()) {
                throw new IllegalArgumentException("Токен не может быть пустым");
            }
            this.privateToken = privateToken;
        }

        public void printSummary() {
            System.out.println("publicName=" + publicName);
            System.out.println("privateToken=" + privateToken);
            System.out.println("protectedNote=" + protectedNote);
            System.out.println("city=" + city);
        }
    }

    // Наследник может обратиться к protected и default полям, если он в том же пакете
    private static class SupportEngineer extends AccessSample {
        public void fillContacts() {
            this.publicName = "Support"; // public
            this.protectedNote = "Доступ к protected в наследнике";
            this.city = "Москва"; // default доступен, так как класс в том же пакете
            setPrivateToken("Только через сеттер"); // private скрыт, но методы доступны
        }
    }

    // Демонстрация static: счётчик экземпляров и утилитарный класс
    private static class StaticCounter {
        private static int createdCount;

        static {
            createdCount = 0; // статический блок выполнится один раз
            System.out.println("[StaticCounter] Загрузка класса, инициализируем счётчик");
        }

        public StaticCounter() {
            createdCount++;
        }

        public static int getCreatedCount() {
            return createdCount;
        }
    }

    private static final class MathHelper { // final класс: не даём наследовать
        private MathHelper() {
        }

        public static int square(int value) {
            return value * value;
        }
    }

    // Константы принято объединять в классе со static final
    private static final class Constants {
        public static final double PI = 3.14159;
        public static final int MAX_USERS = 1000;

        private Constants() {
        }
    }

    // final переменные и методы
    private static class FinalExample {
        private final int instanceId;

        public FinalExample() {
            this.instanceId = (int) (Math.random() * 1000); // присваиваем только в конструкторе
        }

        public void printConfig() {
            System.out.println("Финальное поле instanceId неизменно: " + instanceId);
            BasePaymentService paymentService = new CardPaymentService();
            paymentService.describe(); // final метод из базового класса
        }
    }

    private static class BasePaymentService {
        public final void describe() {
            System.out.println("BasePaymentService: этот метод final, переопределить нельзя");
        }

        public void pay(int amount) {
            System.out.println("Оплата на сумму " + amount + " ₽");
        }
    }

    private static class CardPaymentService extends BasePaymentService {
        @Override
        public void pay(int amount) {
            System.out.println("Оплата картой на сумму " + amount + " ₽");
        }
    }

    // Демонстрация аннотаций
    private static class AnnotationDemo {
        public void safeCallDeprecated() {
            // Локально скрываем предупреждение, чтобы показать вызов устаревшего метода
            @SuppressWarnings("deprecation")
            String message = deprecatedApi();
            System.out.println(message);
        }

        @Deprecated
        public String deprecatedApi() {
            return "Этот метод помечен @Deprecated: в новом коде лучше избегать";
        }
    }

    // Финальный класс: нельзя наследовать, полезно для неизменяемых объектов
    private static final class ImmutableComponent {
        private final String name;

        private ImmutableComponent(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
