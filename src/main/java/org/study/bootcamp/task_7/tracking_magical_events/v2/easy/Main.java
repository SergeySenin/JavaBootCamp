package org.study.bootcamp.task_7.tracking_magical_events.v2.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static class SpellEvent {
        int id;
        String eventType;
        String action;

        public SpellEvent(int id, String eventType, String action) {
            this.id = id;
            this.eventType = eventType;
            this.action = action;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Тип: " + eventType + ", Действие: " + action;
        }
    }

    static class HogwartsSpells {
        private Map<Integer, SpellEvent> spellById = new HashMap<>();
        private Map<String, List<SpellEvent>> spellsByType = new HashMap<>();
        private int nextId = 1;

        public void addSpellEvent(String eventType, String actionDescription) {
            SpellEvent spell = new SpellEvent(nextId, eventType, actionDescription);

            spellById.put(nextId, spell);

            spellsByType.computeIfAbsent(eventType, key -> new ArrayList<>()).add(spell);

            System.out.println("Добавлено заклинание: " + spell);
            nextId++;
        }

        public SpellEvent getSpellEventById(int id) {
            return spellById.get(id);
        }

        public List<SpellEvent> getSpellEventsByType(String eventType) {
            return spellsByType.getOrDefault(eventType, new ArrayList<>());
        }

        public void deleteSpellEvent(int id) {
            SpellEvent spell = spellById.remove(id);
            if (spell != null) {
                List<SpellEvent> spells = spellsByType.get(spell.eventType);
                if (spells != null) {
                    spells.remove(spell);
                    if (spells.isEmpty()) {
                        spellsByType.remove(spell.eventType);
                    }
                }
                System.out.println("Удалено заклинание: " + spell);
            } else {
                System.out.println("Заклинание с ID " + id + " не найдено");
            }
        }

        public void printAllSpellEvents() {
            System.out.println("\n=== ВСЕ ЗАКЛИНАНИЯ ХОГВАРТСА ===");
            for (Map.Entry<Integer, SpellEvent> entry : spellById.entrySet()) {
                System.out.println("✨ " + entry.getValue());
            }
        }

        public static void main(String[] args) {
            HogwartsSpells hogwarts = new HogwartsSpells();

            hogwarts.addSpellEvent("Защита", "Создает защитный барьер");
            hogwarts.addSpellEvent("Чар", "Подчиняет волю противника");
            hogwarts.addSpellEvent("Трансфигурация", "Превращает предмет в другой");
            hogwarts.addSpellEvent("Защита", "Отражает атаки противника");

            System.out.println("\n--- Поиск по ID ---");
            SpellEvent spell = hogwarts.getSpellEventById(2);
            System.out.println("Найдено: " + spell);

            System.out.println("\n--- Поиск по типу 'Защита' ---");
            List<SpellEvent> defenseSpells = hogwarts.getSpellEventsByType("Защита");
            for (SpellEvent s : defenseSpells) {
                System.out.println("🛡️ " + s);
            }

            System.out.println("\n--- Удаление заклинания ---");
            hogwarts.deleteSpellEvent(1);

            hogwarts.printAllSpellEvents();
        }
    }
}



/*
1. Зачем нужны две разные HashMap?
Первая мапа spellById: Быстрый поиск по ID
Вторая мапа spellsByType: Поиск всех заклинаний определённого типа

2. Как работает автоматическая генерация ID через nextId++?
nextId++ - это пост-инкремент: сначала использует значение, потом увеличивает на 1

java
private int nextId = 1; // Начинаем с 1

// При добавлении первого заклинания:
SpellEvent spell = new SpellEvent(nextId, ...); // nextId = 1
nextId++; // теперь nextId = 2

// При добавлении второго:
SpellEvent spell = new SpellEvent(nextId, ...); // nextId = 2
nextId++; // теперь nextId = 3

Результат: Каждое новое заклинание получает уникальный возрастающий ID: 1, 2, 3, 4...

3. Почему в deleteSpellEvent() нужно удалять из обеих мап?
Потому что заклинание хранится в двух разных местах:
java
// После добавления заклинания:
spellById: {1 → SpellEvent(1, "Защита", "Барьер")}
spellsByType: {"Защита" → [SpellEvent(1, "Защита", "Барьер")]}

// Если удалить только из spellById:
spellById: {} ← заклинание удалено
spellsByType: {"Защита" → [SpellEvent(1, "Защита", "Барьер")]} ← ОСТАЛОСЬ!

// Получим противоречивые данные:
hogwarts.getSpellEventById(1) // null - не найдено
hogwarts.getSpellEventsByType("Защита") // вернёт список с удалённым заклинанием!

Нужно удалять из обеих мап для согласованности данных.

4. Как работает getOrDefault() в getSpellEventsByType()?
getOrDefault() возвращает значение по ключу, или значение по умолчанию если ключа нет:
java
// Без getOrDefault:
List<SpellEvent> result = spellsByType.get(eventType);
if (result == null) {
    result = new ArrayList<>(); // создаём пустой список если типа нет
}

// С getOrDefault:
List<SpellEvent> result = spellsByType.getOrDefault(eventType, new ArrayList<>());

Преимущество: Избегаем проверки на null, всегда возвращаем валидный список.

5. Что проверяет условие if (spells != null) при удалении?
Проверяет, что для данного типа заклинаний вообще есть список:
java
SpellEvent spell = spellById.get(id); // нашли заклинание
List<SpellEvent> spells = spellsByType.get(spell.eventType); // ищем список этого типа

// spells может быть null если:
// - Заклинание типа "НеизвестныйТип"
// - Список этого типа уже был удалён ранее
if (spells != null) {
    spells.remove(spell); // удаляем только если список существует
}

6. Зачем нужна проверка if (spells.isEmpty())?
Чтобы очищать мапу от пустых списков:
java
// Было: {"Защита" → [SpellEvent1, SpellEvent2]}
spells.remove(spell); // удалили последнее заклинание
// Стало: {"Защита" → []} ← пустой список!

if (spells.isEmpty()) {
    spellsByType.remove(spell.eventType); // удаляем запись полностью
}
// Стало: {} ← мапа чистая

Зачем: Экономит память и избегает хранения пустых списков.

7. Почему spellById.get(id) может вернуть null?
Когда заклинания с таким ID не существует в мапе:
java
spellById.put(1, spell1);
spellById.put(2, spell2);

spellById.get(1) // вернёт spell1
spellById.get(3) // вернёт null - ID 3 нет в мапе
spellById.get(999) // вернёт null - ID 999 нет в мапе

8. Что будет, если добавить заклинание с уже существующим ID?
Перезапишется старое значение!

java
spellById.put(1, spell1); // Добавили заклинание #1
spellById.put(1, spell2); // Добавили другое заклинание с тем же ID #1

spellById.get(1) // вернёт spell2 - spell1 потеряно!

В нашей системе это невозможно, так как ID генерируется автоматически и всегда увеличивается.
 */
