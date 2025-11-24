package org.study.bootcamp.task_3.rpg_abstraction.v2.easy;

public class Main {
    abstract static class Character {
        String name;
        int strength;
        int agility;
        int intelligence;
        int health = 100;

        public Character(String name) {
            this.name = name;
            this.strength = 5;
            this.agility = 5;
            this.intelligence = 5;
        }

        public Character(String name, int strength, int agility, int intelligence) {
            this.name = name;
            this.strength = strength;
            this.agility = agility;
            this.intelligence = intelligence;
        }

        public abstract void attack(Character opponent);

        public void takeDamage(int damage) {
            health -= damage;
            if (health < 0) {
                health = 0;
            }
        }

        @Override
        public String toString() {
            return name + " (HP: " + health + ")";
        }

        static class Warrior extends Character {
            public Warrior(String name) {
                super(name, 10, 5, 3);
            }

            @Override
            public void attack(Character opponent) {
                opponent.takeDamage(strength);
                System.out.println(name + " атакует мечом! Урон: " + strength);
            }
        }

        static class Archer extends Character {
            public Archer(String name) {
                super(name, 3, 10, 5);
            }

            @Override
            public void attack(Character opponent) {
                opponent.takeDamage(agility);
                System.out.println(name + " стреляет из лука! Урон: " + agility);
            }
        }

        public static void main(String[] args) {
            Warrior warrior = new Warrior("Боромир");
            Archer archer = new Archer("Леголас");

            System.out.println("Начало боя!");
            System.out.println(warrior);
            System.out.println(archer);
            System.out.println();

            warrior.attack(archer);
            archer.attack(warrior);
            System.out.println();

            System.out.println("После атаки:");
            System.out.println(warrior);
            System.out.println(archer);
        }
    }
}



/*
1. Зачем на что-то вешать abstract?
abstract означает, что это "чертёж" или "шаблон", а не готовый объект.
Без abstract:
java
Character c = new Character("Иван"); // Можно создать объект

С abstract:
java
abstract class Character { }
// Character c = new Character("Иван"); // ОШИБКА! Нельзя создать
Зачем нужно: Чтобы заставить программиста создавать конкретные типы (Warrior, Archer), а не общие "персонажи".

2. Зачем может быть нужен конструктор по имени?
Это конструктор по умолчанию для быстрого создания персонажа:
java
// Длинный вариант:
Character c1 = new Character("Боромир", 5, 5, 5);

// Короткий вариант (использует конструктор только с именем):
Character c2 = new Character("Боромир"); // автоматически: сила=5, ловкость=5, интеллект=5
Аналогия: Как "быстрое меню" в ресторане - получаешь стандартный набор характеристик.

3. Что обозначает -=?
Это сокращённая запись вычитания:
java
// Полная запись:
health = health - damage;

// Сокращённая запись:
health -= damage;

Другие примеры:
java
int x = 10;
x -= 3;    // x = 7 (10 - 3)
x += 5;    // x = 12 (7 + 5)
x *= 2;    // x = 24 (12 * 2)

4. Что передаёт наследнику extends?
extends означает "наследует" или "получает в наследство":
java
class Warrior extends Character {
    // Warrior автоматически получает:
    // - поля: name, strength, agility, intelligence, health
    // - методы: takeDamage(), toString()
    // - конструкторы Character
}

Что наследуется:
Все не-private поля и методы
Возможность использовать конструктор родителя через super()

5. Зачем нужно слово super и в чём смысл от полей родителя?
super - это обращение к родительскому классу:
java
class Warrior extends Character {
    public Warrior(String name) {
        super(name, 10, 5, 3); // Вызов конструктора родителя
    }
}

Смысл полей родителя: Все наследники используют общие характеристики, но с разными значениями:
java
Warrior w = new Warrior("Боромир");
// w имеет: strength=10, agility=5, intelligence=3

Archer a = new Archer("Леголас");
// a имеет: strength=3, agility=10, intelligence=5

6. Почему метод takeDamage принял (agility)?
Потому что takeDamage ожидает любое целое число (int), а agility - это целое число:
java
public void takeDamage(int damage) { // принимает ЛЮБОЕ int
    health -= damage;
}

// Можно передать:
opponent.takeDamage(strength);  // число от силы
opponent.takeDamage(agility);   // число от ловкости
opponent.takeDamage(25);        // просто число 25
opponent.takeDamage(10 + 5);    // выражение = 15

Важно: Методу всё равно, откуда число - он просто вычитает его из здоровья!
 */
