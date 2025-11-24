package org.study.bootcamp.task_1.group_users_by_age.v6.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static class User {
        String name;
        int age;
        String workplace;
        String address;

        public User(String name, int age, String workplace, String address) {
            this.name = name;
            this.age = age;
            this.workplace = workplace;
            this.address = address;
        }

        public static Map<Integer, List<User>> groupUsers(List<User>users) {
            Map<Integer, List<User>> result = new HashMap<>();

            for (User user : users) {
                result.computeIfAbsent(user.age, key -> new ArrayList<>()).add(user);
            }

            return result;
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("AB", 25, "ABC", "ABCD"),
                new User("BC", 30, "BCD", "BCDE"),
                new User("CD", 30, "CDE", "CDEF"),
                new User("DE", 30, "DEF", "DEFG"),
                new User("EF", 25, "EFG", "EFGH"),
                new User("FG", 45, "FGH", "FGHI")
        );

        Map<Integer, List<User>> grouped = User.groupUsers(users);
        System.out.println(grouped);
    }
}



/*
1. Символ стрелочки -> для лямбды
На клавиатуре это дефис и знак "больше":
Дефис - (рядом с цифрой 0)
Знак "больше" > (клавиша с точкой, с Shift)
Набираете последовательно: - и >

2. new HashMap<>() и new ArrayList<>()
new HashMap<>() - создаёт новую пустую хэш-карту (словарь) для хранения пар "ключ-значение"
new ArrayList<>() - создаёт новый пустой динамический массив
Упрощённая аналогия:
java
HashMap<>()    // как пустая коробка для пар "возраст → список пользователей"
ArrayList<>()  // как пустой список для хранения объектов User

3. Цикл for (User user : users)
Это enhanced for loop (улучшенный цикл for). Читается как:
"Для каждого пользователя в списке users"
Эквивалент обычного цикла:
java
// Упрощённый вариант
for (User user : users) {
    // делаем что-то с user
}

// То же самое обычным циклом
for (int i = 0; i < users.size(); i++) {
    User user = users.get(i);
    // делаем что-то с user
}

4. Метод computeIfAbsent()
Работает по принципу:
"Если ключа нет - создай новый список, если есть - верни существующий"
Пример:
java
Map<Integer, List<User>> result = new HashMap<>();

// Без computeIfAbsent (длинный вариант)
if (!result.containsKey(user.age)) {
    result.put(user.age, new ArrayList<>());
}
result.get(user.age).add(user);

// С computeIfAbsent (короткий вариант)
result.computeIfAbsent(user.age, k -> new ArrayList<>()).add(user);

5. Как работает лямбда k -> new ArrayList<>()
Лямбда - это сокращённая запись анонимного класса.
До лямбд (Java 7):
java
result.computeIfAbsent(user.age, new Function<Integer, List<User>>() {
    @Override
    public List<User> apply(Integer k) {
        return new ArrayList<>();
    }
});

С лямбдами (Java 8+):
java
result.computeIfAbsent(user.age, k -> new ArrayList<>());

Где:
k - параметр (возраст)
-> - стрелка "входит в"
new ArrayList<>() - возвращаемое значение
 */
