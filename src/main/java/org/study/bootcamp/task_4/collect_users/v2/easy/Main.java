package org.study.bootcamp.task_4.collect_users.v2.easy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    static class User {
        int id;
        String name;
        int age;
        Set<String> activities;

        public User(int id, String name, int age, Set<String> activities) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.activities = activities;
        }

        public static Map<User, String> findHobbyLovers(List<User> users, Set<String> targetActivities) {
            Map<User, String> result = new HashMap<>();

            for (User user : users) {
                for (String activity : user.activities) {
                    if (targetActivities.contains(activity)) {
                        result.put(user, activity);
                        break;
                    }
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return name + " (id: " + id + ", age: " + age + ")";
        }
    }

    public static void main(String[] args) {
        User user1 = new User(1, "Анна", 25, Set.of("чтение", "плавание", "рисование"));
        User user2 = new User(2, "Иван", 30, Set.of("футбол", "программирование"));
        User user3 = new User(3, "Мария", 28, Set.of("танцы", "кулинария"));

        List<User> users = List.of(user1, user2, user3);

        Set<String> searchActivities = Set.of("плавание", "программирование", "шитье");

        Map<User, String> hobbyLovers = User.findHobbyLovers(users, searchActivities);

        System.out.println("Найденные пользователи:");
        for (Map.Entry<User, String> entry : hobbyLovers.entrySet()) {
            System.out.println(entry.getKey() + " - увлечение: " + entry.getValue());
        }
    }
}



/*
1. Первый код (поиск увлечений):
java
for (User user : users) {                     // ВНЕШНИЙ цикл
    for (String activity : user.activities) { // ВНУТРЕННИЙ цикл
        // проверка совпадения активности
    }
}

for (User user : users) {                          // ДЛЯ КАЖДОГО пользователя в списке
    for (String activity : user.activities) {      // ДЛЯ КАЖДОГО увлечения пользователя
        if (targetActivities.contains(activity)) { // ЕСЛИ увлечение есть в искомых
            result.put(user, activity);            // ДОБАВИТЬ в результат (пользователь → увлечение)
            break;                                 // ПРЕРВАТЬ поиск увлечений этого пользователя
        }
    }
}
return result;                                     // ВЕРНУТЬ готовый результат

2. Второй код (вывод результатов):
for (Map.Entry<User, String> entry : hobbyLovers.entrySet()) { // ДЛЯ КАЖДОЙ пары в результатах
    System.out.println(entry.getKey() + " - увлечение: " + entry.getValue());
    // ВЫВЕСТИ: пользователь - увлечение: найденное_увлечение
}

Разбор по частям:
for - начало цикла "для каждого"
( - открывающая скобка параметров цикла
Map.Entry<User, String> entry - объявление переменной:
Map.Entry - тип "запись в словаре" (пара ключ-значение)
<User, String> - конкретные типы: ключ=User, значение=String
entry - имя переменной для текущей записи
: - разделитель ("в" или "из")
hobbyLovers.entrySet() - что перебираем:
hobbyLovers - наша Map с результатами
. - оператор "взять у"
entrySet() - метод "дай все записи словаря"
) - закрывающая скобка параметров цикла

Буквальный перевод:
"Для каждой записи entry (где ключ-User, значение-String) из всех записей словаря hobbyLovers"

Альтернативные способы обхода Map:
java
// Способ 1: через entrySet() (лучший)
for (Map.Entry<User, String> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}

// Способ 2: через keySet() + get()
for (User user : map.keySet()) {
    System.out.println(user + " -> " + map.get(user));
}

// Способ 3: через forEach + лямбду
map.forEach((user, activity) -> {
    System.out.println(user + " -> " + activity);
});

Почему entrySet() лучше? Он сразу даёт доступ и к ключу и к значению, не требуя дополнительного поиска в Map.
 */
