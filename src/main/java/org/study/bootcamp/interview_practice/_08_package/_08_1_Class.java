/*

Пакет (package) — это механизм организации классов и интерфейсов в логические группы.
По своей сути, это просто папка (директория) на вашем жёстком диске,
которая содержит сгруппированные файлы .java и скомпилированные файлы .class.

Зачем нужны пакеты?
- Избежание конфликтов имён (пространства имён)
- Организация кода по логическим группам
- Контроль доступа (видимость в пределах пакета)
- Сокрытие реализации (инкапсуляция)
- Повторное использование кода

Именование пакетов:
- Обратный домен
- Только строчные буквы
com.company.project
ещё ...
ещё ...
 */

package org.study.bootcamp.interview_practice._08_package;

/*
Импорты — это ...
import java.util.List;                                 конкретный класс — для ...
import java.util.*;                                    все классы пакета (не рекомендуется) — для ...
import static java.lang.Math.PI;                       статический импорт — для ...
java.util.ArrayList list = new java.util.ArrayList();  Без импорта (через полное имя) — для ...
 */

// В данном месте должны указываться используемые импорты

/*
Класс — это ...
модификатор доступа Класс имяКласса {                    Базовое объявление класса
                                                   В нём описываются поля и методы
}
 */

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ ЧЕГО... В JAVA С ИХ ОСОБЕННОСТЯМИ
 *
 * @author Sergey
 */
public class _08_1_Class {

/*
Поле — это ...

    Статическое поле                                 Статическое поле и константа
    Константное поле                                 совместно объявляются в начале
                                                     для ...

    модификаторДоступа тип имяПоля;                  Объявление поля внутри класса
                                                     для ...

    модификаторДоступа тип имяПоля = значение;       Объявление поля с инициализацией
                                                     для ...
 */

    private static class Person {
        private String name;
        private int age;

        public Person() {
            this("Без имени", 0);
        }

        public Person(String name, int age) {
            setName(name);
            setAge(age);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            if (name == null || name.isBlank()) {
                this.name = "Без имени";
                return;
            }
            this.name = name.trim();
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            if (age < 0) {
                this.age = 0;
                return;
            }
            this.age = age;
        }

        public void introduce() {
            System.out.println("Я " + name + ", мне " + age + " лет");
        }

        public int calculateBirthYear(int currentYear) {
            return currentYear - age;
        }
    }

    private static class Calculator {
        public int add(int firstValue, int secondValue) {
            return firstValue + secondValue;
        }

        public void printGreeting(String name) {
            System.out.println("Здравствуйте, " + name + "!");
        }
    }

/*
Конструктор — это ...
this — это ...

    модификаторДоступа ИмяКласса(тип параметр) {     Конструктор с параметром
        this.поле = параметр;                        Инициализация полей объекта
    }

                             Конструктор по умолчанию (без параметров)
                             — для ...
                             ...
                             Перегруженный конструктор (overloading)
                             — для ...
                             ...
                             Конструктор копирования (copy constructor)
                             — для ...
                             ...
 */

    private static void demonstrateConstructorsAndThis() {
        System.out.println("2) Конструкторы и this");
        Person anna = new Person("Анна", 28);
        anna.introduce();
        System.out.println("Год рождения Анны: " + anna.calculateBirthYear(2024));

        Person unknown = new Person();
        unknown.introduce();
        System.out.println();
    }

/*
Метод — это ...
Параметры метода — это ...
Аргументы метода — это ...

    Метод без параметров и возврата — для ...
    модификаторДоступа void имяМетода() {                             Отсутствие параметров
                                     ;                                Действия
                       ;                                              void - метод не возвращает значение
    }

    Метод с параметром — для ...
    ...

    Метод с возвратом — для ...
    ...

    Метод с параметрами и возвратом — для ...
    модификаторДоступа тип имяМетода(тип параметр1, тип параметр2) {  Использование параметров
                                    ;                                 Действия с параметрами
        return значение;                                              return - метод возвращает значение типа "тип"
    }

    Перегруженные методы (overloading) — для ...
    модификаторДоступа тип имяМетода(тип параметр) {                  Версия с одним параметром
        return значение;
    }

    модификаторДоступа тип имяМетода(тип п1, тип п2) {                Версия с двумя параметрами
        return другоеЗначение;                                        Такое же имя, но другие параметры
    }

    Рекурсивный метод — для ...
    модификаторДоступа тип имяМетода(параметр) {
        if (условиеЗавершения) {                                      Базовый случай
            return конечноеЗначение;
        }
        return имяМетода(изменённыйПараметр);                         Рекурсивный вызов
    }
 */

    private static void demonstrateMethodsParametersArguments() {
        System.out.println("3) Методы, параметры и аргументы");
        Calculator calculator = new Calculator();

        System.out.println("2 + 3 = " + calculator.add(2, 3));
        calculator.printGreeting("Алексей");
    }

    private static void demonstrateGettersSetters() {
        System.out.println("4) Геттеры и сеттеры");
        Person person = new Person("  ", -5);
        person.introduce();

        person.setName("Игорь");
        person.setAge(19);
        System.out.println("После обновления: " + person.getName() + ", " + person.getAge() + " лет");
        System.out.println();
    }

/*
    Класс имяОбъекта = new Класс();                                   Создание объекта через new (экземпляра класса)
    имяОбъекта.метод();                                               Вызов метода объекта через точку (.)
 */

    private static void demonstrateClassAndObject() {
        System.out.println("1) Класс и объект");
        Person person = new Person();
        person.introduce();
        System.out.println();
    }

    public static void main(String[] args) {
        demonstrateClassAndObject();
        demonstrateConstructorsAndThis();
        demonstrateGettersSetters();
        demonstrateMethodsParametersArguments();
    }
}
