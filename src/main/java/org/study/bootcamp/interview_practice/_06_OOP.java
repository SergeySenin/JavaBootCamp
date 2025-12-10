package org.study.bootcamp.interview_practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ ООП В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * @author Sergey
 */
public class _06_OOP {

    public static void main(String[] args) {
        demonstrateEncapsulation();
        demonstrateInheritance();
        demonstratePolymorphism();
        demonstrateOverloading();
        demonstrateAbstraction();
        summarizePrinciples();
    }

    // Инкапсуляция скрывает поля и отдаёт доступ через методы — так проще контролировать корректность данных
    private static void demonstrateEncapsulation() {
        System.out.println("1) Инкапсуляция: данные под контролем");
        UserProfile profile = new UserProfile();
        profile.setUsername("spring_student"); // setter проводит проверку и не даёт пустое значение
        profile.setAge(22); // можно добавить валидацию (например, ограничить возраст)
        System.out.println("Геттеры открывают только нужную информацию: username=" +
                profile.getUsername() + ", age=" + profile.getAge());
        System.out.println("Прямого доступа к полям нет: нельзя присвоить отрицательный возраст или null-логин");
        System.out.println();
    }

    // Наследование позволяет переиспользовать общий код и расширять поведение без копирования
    private static void demonstrateInheritance() {
        System.out.println("2) Наследование: базовый и дочерний классы");
        Animal animal = new Animal();
        animal.makeSound();

        Dog dog = new Dog();
        dog.makeSound(); // унаследованный метод
        dog.bark(); // метод конкретной собаки

        System.out.println("Дочерний класс получает public/protected члены родителя, но конструкторы не наследуются");
        System.out.println();
    }

    // Полиморфизм: обращаемся к объектам через ссылку родителя, а вызывается переопределённая версия
    private static void demonstratePolymorphism() {
        System.out.println("3) Полиморфизм: @Override выбирает реализацию");
        Animal catAsAnimal = new Cat(); // ссылка родительского типа
        catAsAnimal.makeSound(); // выполнится версия Cat, а не Animal

        Animal dogAsAnimal = new Dog();
        dogAsAnimal.makeSound();

        System.out.println("Аннотация @Override помогает заметить опечатки в сигнатуре и гарантирует переопределение");
        System.out.println("super.makeSound() позволяет дополнить поведение родителя вместо полной замены");
        System.out.println();
    }

    // Перегрузка методов: одна задача с разными параметрами — без наследования
    private static void demonstrateOverloading() {
        System.out.println("4) Перегрузка методов (overloading)");
        PaymentProcessor processor = new PaymentProcessor();
        processor.process(1500);
        processor.process(999.99, "RUB");
        processor.process(500, 2); // ещё одна сигнатура
        System.out.println("Перегрузка различается набором параметров: типы или количество аргументов");
        System.out.println();
    }

    // Абстракция: через абстрактный класс задаём общие методы и вынуждаем потомков реализовать детали
    private static void demonstrateAbstraction() {
        System.out.println("5) Абстракция: обязательные шаги задаются в базовом классе");
        NotificationService emailService = new EmailNotification();
        NotificationService smsService = new SmsNotification();

        emailService.send("user@example.com", "Добро пожаловать в приложение");
        smsService.send("+79991234567", "Код подтверждения: 1234");

        System.out.println("Экземпляры создаются от конкретных подклассов, но используются через абстракцию");
        System.out.println();
    }

    // Краткий итог по четырём принципам
    private static void summarizePrinciples() {
        System.out.println("6) Итог по четырём принципам ООП");
        System.out.println("Инкапсуляция — скрываем поля, выдаём доступ через методы, " +
                "чтобы держать данные в валидном состоянии");
        System.out.println("Наследование — разделяем общую логику в базовом классе и расширяем её в потомках");
        System.out.println("Полиморфизм — вызываем методы через ссылку родителя, получая конкретное поведение объекта");
        System.out.println("Абстракция — выделяем ключевые операции и заставляем потомков реализовать детали");
        System.out.println();
    }

    // Класс-демонстрация инкапсуляции
    private static class UserProfile {
        private String username;
        private int age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("Логин не может быть пустым");
            }
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Возраст не может быть отрицательным");
            }
            this.age = age;
        }
    }

    // Базовый класс для наследования
    private static class Animal {
        public void makeSound() {
            System.out.println("  Animal: издаю базовый звук");
        }
    }

    // Конкретная реализация Animal
    private static class Dog extends Animal {
        @Override
        public void makeSound() {
            System.out.println("  Dog: гав-гав!");
        }

        public void bark() {
            System.out.println("  Dog: лает на незнакомца");
        }
    }

    // Ещё один потомок Animal
    private static class Cat extends Animal {
        @Override
        public void makeSound() {
            System.out.println("  Cat: мяу!");
        }
    }

    // Перегруженный обработчик платежей показывает разные сигнатуры
    private static class PaymentProcessor {
        public void process(int amountInRubles) {
            System.out.println("  Обработка платежа на сумму " + amountInRubles + " ₽");
        }

        public void process(double amount, String currency) {
            System.out.println("  Обработка платежа: " + amount + " " + currency);
        }

        public void process(int amount, int installments) {
            System.out.println("  Обработка платежа " + amount + " ₽ в " + installments + " платежах");
        }
    }

    // Абстрактный класс: общее API и базовая реализация некоторых шагов
    private abstract static class NotificationService {
        public void send(String destination, String message) {
            validate(destination, message); // общий шаг проверки
            dispatch(destination, message); // конкретная отправка в подклассах
        }

        protected void validate(String destination, String message) {
            if (destination == null || destination.isBlank()) {
                throw new IllegalArgumentException("Адрес получателя не указан");
            }
            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("Сообщение не может быть пустым");
            }
        }

        protected abstract void dispatch(String destination, String message);
    }

    private static class EmailNotification extends NotificationService {
        @Override
        protected void dispatch(String destination, String message) {
            System.out.println("  Email → " + destination + ": " + message);
        }
    }

    private static class SmsNotification extends NotificationService {
        @Override
        protected void dispatch(String destination, String message) {
            System.out.println("  SMS → " + destination + ": " + message);
        }
    }
}
