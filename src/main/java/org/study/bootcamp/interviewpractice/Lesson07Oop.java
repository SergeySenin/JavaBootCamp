package org.study.bootcamp.interviewpractice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ ООП В JAVA И ЕГО ОСОБЕННОСТЕЙ
 *
 * ООП (объектно-ориентированное программирование) — это подход, в котором программа строится из объектов
 * Объект — это единица, которая одновременно хранит:
 * - данные (состояние) — значения полей, описывающие текущую «картину» объекта;
 * - операции (поведение) — методы, которые читают/изменяют эти данные и выполняют действия
 *
 * Зачем это нужно:
 * 1) Защитить данные от неконтролируемых изменений: объект сам проверяет свою корректность
 * 2) Делать код расширяемым: добавлять новые варианты поведения без дублирования существующей логики
 * 3) Делать код заменяемым: использовать разные реализации «одной роли» одинаковым способом
 * 4) Управлять сложностью: разбивать систему на независимые части с понятной ответственностью
 *
 * @author Sergey
 */
public class Lesson07Oop {

/*
    === ЧЕТЫРЕ ПРИНЦИПА ООП ===

    1) Инкапсуляция
       Что это: скрытие внутреннего состояния объекта (поля обычно private) и предоставление доступа через методы
       Для чего: объект сам защищает корректность своих данных (инварианты) и не даёт внешнему коду ломать состояние
       Как реализуется: private поля + методы (геттеры/сеттеры/операции), внутри которых есть проверки и правила

       Цель:
        - не допустить состояния, которое «по смыслу» недопустимо (например, пустой username, отрицательный age);
        - сократить число мест, где можно «сломать» объект: изменения проходят через проверенный код методов

        Что будет в противном случае (если поля всегда public):
        - любой код сможет записать что угодно в любой момент;
        - объект перестаёт быть надёжной единицей: его состояние нельзя гарантировать;
        - ошибки станут «размазанными» по коду: искать источник некорректных данных станет тяжелее

    2) Наследование
       Что это: создание нового класса на основе существующего (extends) с переиспользованием уже написанной логики
       Для чего: вынести общую логику в базовый класс и избежать потенциального копирования кода
       Как реализуется: extends + добавление новых методов/переопределение существующих (см. 3)

       Почему конструкторы не наследуются:
       - конструктор — это не «поведение объекта», а механизм создания/инициализации объекта;
       - у каждого класса свой набор полей → свой способ корректной инициализации;
       - поэтому у наследника конструкторы свои, но он обязан вызвать конструктор родителя через super(...)

    3) Полиморфизм
       Что это: один и тот же вызов метода (по одной ссылке) даёт разное поведение в зависимости от реального объекта
       Для чего: писать код «на уровне роли», а не «на уровне конкретного класса»
       Как реализуется: ссылка типа родителя/интерфейса + переопределённые instance-методы (не static) у наследников

       Механика:
       - компилятор проверяет, что у типа ссылки есть такой метод;
       - а во время выполнения JVM вызывает версию метода того класса, объект которого реально лежит в ссылке

    4) Абстракция
       Что это: выделение важного «что делаем» и сокрытие деталей «как именно делаем»
       Для чего: клиентский код не зависит от деталей, а значит детали можно менять без переписывания клиентов
       Как реализуется: абстрактные классы/интерфейсы, которые воспринимаются как «общая форма использования»
 */

    private static void demonstrateEncapsulation() {
        System.out.println("1) Инкапсуляция — объект охраняет своё состояние через методы");

        UserProfile profile = new UserProfile();

        System.out.println("Сценарий: задаём данные через сеттеры (внутри есть проверки)");
        profile.setUsername("spring_student");
        profile.setAge(22);

        System.out.println(
                "Чтение через геттеры → username="
                        + profile.getUsername()
                        + ", age=" + profile.getAge()
        );

        System.out.println(
                "Смысл: снаружи нельзя записать значение напрямую в поле;" +
                        " все изменения проходят через методы, где объект защищает корректность"
        );
        System.out.println();
    }

    private static void demonstrateInheritance() {
        System.out.println("2) Наследование — переиспользуем общий код и добавляем специализацию");

        Animal baseAnimal = new Animal();
        System.out.println("Базовый объект Animal вызывает свой метод:");
        baseAnimal.makeSound();

        Dog dog = new Dog();
        System.out.println("Наследник Dog вызывает переопределённый метод и свой дополнительный метод:");
        dog.makeSound();
        dog.bark();

        System.out.println();
    }

/*
        super — ключевое слово, которое обращается к части родителя

        Где применяется:
        - super(...) в конструкторе: вызов конструктора родителя (обязательная часть инициализации)
        - super.method() в методе: использовать реализацию родителя и дополнить её

        Зачем нужно:
        - переиспользовать код родителя вместо копирования;
        - сохранить общую часть поведения и добавить частную
 */

    private static void demonstrateSuperKeyword() {
        System.out.println("2.1) super — используем реализацию родителя и дополняем её");

        Dog dog = new Dog();
        System.out.println("Сценарий: Dog.makeSound() вызывает super.makeSound(), затем добавляет своё:");
        dog.makeSound();

        System.out.println(
                "Смысл: общая часть поведения остаётся в Animal, а Dog добавляет специализацию без дублирования"
        );
        System.out.println();
    }

    private static void demonstratePolymorphism() {
        System.out.println("3) Полиморфизм — один вызов, разные реализации в зависимости от объекта");

        Animal first = new Cat();
        Animal second = new Dog();

        System.out.println("Сценарий: одна переменная типа Animal хранит разные объекты");
        System.out.println("Animal first = new Cat();  → first.makeSound():");
        first.makeSound();

        System.out.println("Animal second = new Dog(); → second.makeSound():");
        second.makeSound();

        System.out.println(
                "Механика: тип ссылки гарантирует наличие метода," +
                        " а конкретная реализация выбирается по объекту во время выполнения"
        );
        System.out.println(
                "@Override — защита: если сигнатура не совпала, компилятор сообщит об ошибке"
        );
        System.out.println();
    }

    private static void demonstrateOverloadVsOverride() {
        System.out.println("3.1) Overload vs Override — разные механизмы выбора метода");

        Calculator calculator = new Calculator();

        System.out.println("Overload (перегрузка): один класс, одно имя, разные параметры; выбор в compile-time");
        System.out.println("calculator.sum(2, 3) → " + calculator.sum(2, 3));
        System.out.println("calculator.sum(2.5, 3.5) → " + calculator.sum(2.5, 3.5));

        System.out.println(
                "Override (переопределение): наследование, одна сигнатура, разная реализация; выбор в runtime"
        );
        Animal animal = new Dog();
        System.out.println("Animal animal = new Dog(); → animal.makeSound():");
        animal.makeSound();

        System.out.println("Критерий overload: список параметров (сигнатура) отличается");
        System.out.println("Критерий override: та же сигнатура + наследование + @Override");

        System.out.println();
    }

    private static void demonstrateAbstraction() {
        System.out.println("4) Абстракция — используем общий способ работы, не вникая в детали реализации");

        NotificationService email = new EmailNotification();
        NotificationService sms = new SmsNotification();

        System.out.println("Сценарий: вызываем один и тот же метод send(...) у разных реализаций");

        System.out.println("Отправка email:");
        email.send("user@example.com", "Добро пожаловать");

        System.out.println("Отправка SMS:");
        sms.send("+79991234567", "Код подтверждения: 1234");

        System.out.println(
                "Смысл: клиент использует один и тот же способ вызова send(...)," +
                        " а различия спрятаны внутри конкретных реализаций dispatch(...)"
        );
        System.out.println();
    }

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

    private static class Animal {
        public void makeSound() {
            System.out.println("Animal: базовый звук");
        }
    }

    private static class Dog extends Animal {
        @Override
        public void makeSound() {
            super.makeSound();
            System.out.println("Dog: гав-гав");
        }

        public void bark() {
            System.out.println("Dog: лает");
        }
    }

    private static class Cat extends Animal {
        @Override
        public void makeSound() {
            System.out.println("Cat: мяу");
        }
    }

    private static class Calculator {
        public int sum(int a, int b) {
            return a + b;
        }

        public double sum(double a, double b) {
            return a + b;
        }
    }

    private abstract static class NotificationService {
        public void send(String destination, String message) {
            validate(destination, message);
            dispatch(destination, message);
        }

        protected void validate(String destination, String message) {
            if (destination == null || destination.isBlank()) {
                throw new IllegalArgumentException("Адрес не указан");
            }
            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("Сообщение пустое");
            }
        }

        protected abstract void dispatch(String destination, String message);
    }

    private static class EmailNotification extends NotificationService {
        @Override
        protected void dispatch(String destination, String message) {
            System.out.println("Email → " + destination + ": " + message);
        }
    }

    private static class SmsNotification extends NotificationService {
        @Override
        protected void dispatch(String destination, String message) {
            System.out.println("SMS → " + destination + ": " + message);
        }
    }

    public static void main(String[] args) {
        demonstrateEncapsulation();
        demonstrateInheritance();
        demonstrateSuperKeyword();
        demonstratePolymorphism();
        demonstrateOverloadVsOverride();
        demonstrateAbstraction();
    }
}
