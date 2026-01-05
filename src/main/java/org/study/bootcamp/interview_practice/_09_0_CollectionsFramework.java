package org.study.bootcamp.interview_practice;

import java.util.*;

//                      Иерархия Java Collections Framework: интерфейсы и стандартные реализации
//
//                                                  ┌────────────────┐
//                                                  │  Iterable [I]  │
//                                                  │  Итерируемый   │
//                                                  │  / Обходимый   │
//                                                  └────────▲───────┘
//                                                           │
//                                                           │  extends
//                                                           │ расширяет
//                                                           │
//                                                  ┌────────────────┐
//                     ┌───────────────────────────▶│ Collection [I] │◀────────────────────────────┐
//                     │                            │ Коллекция      │                             │
//                     │                            └────────▲───────┘                             │
//                     │                                     │                                     │
//                     │  extends                            │  extends                   extends  │
//                     │ расширяет                           │ расширяет                 расширяет │
//                     │                                     │                                     │
//            ┌────────────────┐                    ┌────────────────┐                    ┌────────────────┐
//      ┌─ ─ ▶│   Queue [I]    │               ┌─ ─▶│    List [I]    │◀─ ─┐             ┌▶│    Set [I]     │◀┐
//      │     │   Очередь      │                    │    Список      │                    │    Множество   │ │
//            └────────▲───────┘               │    └────────────────┘    │             │ └────────────────┘ │
//      │              │                                                                │                    │
//      │              │  extends              │ implements    implements │               implements         │  extends
//                     │ расширяет             │ реализует      реализует │             │ реализует          │ расширяет
//      │              │                                                                │                    │
//      │              │                       │                          │    ┌────────────────┐   ┌────────────────┐
//                     │                       │                          │    │  HashSet [C]   │   │ SortedSet [I]  │
//      │              │                                                       │  Хэш-множество │   │ Отсортированное│
//      │              │                       │                          │    │                │   │ множество      │
//                     │                       │                          │    └────────▲───────┘   └────────▲───────┘
//      │              │                                                                │                    │
//      │              │                       │                          │             │  extends           │  extends
//                     │                       │                          │             │ расширяет          │ расширяет
//      │              │                                                                │                    │
//      │              │                       │                          │    ┌─────────────────┐  ┌─────────────────┐
//                     │      implements       │                          │    │LinkedHashSet [C]│  │NavigableSet [I] │
//      │              │       реализует                                       │Хэш-множество с  │  │Навигационное    │
//      │              │                       │                          │    │порядком вставки │  │множество        │
//                     │    ┌─ ─ ─  ─ ─┐       │                          │    └─────────────────┘  └────────▲────────┘
//      │     ┌─────────────▼──┐      ┌────────────────┐         ┌────────────────┐
//      │     │   Deque [I]    │      │ LinkedList [C] │         │ ArrayList [C]  │               implements │
//            │   Двусторонняя │      │ Двусвязный     │         │ Список на      │                реализует
//      │     │   очередь      │      │ список         │         │ массиве        │                          │
//      │     └────────────────┘◀┐    └────────────────┘         │ / Динамический │                 ┌─────────────────┐
//                                                               │   массив       │                 │   TreeSet [C]   │
//      │                        │                               └────────────────┘                 │   Множество на  │
//        implements               implements                                                       │   дереве поиска │
//      │ реализует              │  реализует                                                       └─────────────────┘
//
//      │                        │
//      │                        │
// ┌─────────────────┐  ┌─────────────────┐             Менее популярные реализации:
// │PriorityQueue [C]│  │ ArrayDeque [C]  │             EnumSet         [C] Множество   перечисления (implements Set)
// │Приоритетная     │  │ Двусторонняя    │             EnumMap         [C] Отображение перечисления (implements Map)
// │очередь          │  │ очередь на      │             IdentityHashMap [C] Отображение идентичности (implements Map)
// │                 │  │ массиве         │             WeakHashMap     [C] Слабая      хэш-таблица  (implements Map)
// └─────────────────┘  └─────────────────┘
//                                                  ┌────────────────┐
//                                             ┌─ ─▶│    Map [I]     │◀───┐
//                                                  │    Отображение │    │
//                                             │    │    / Словарь   │    │
//                                                  └────────▲───────┘    │
//                                             │                          │
//                                  implements                            │  extends
//                                  реализует  │                          │ расширяет
//                                    ┌────────────────┐         ┌────────────────┐
//                                    │  HashMap [C]   │         │ SortedMap [I]  │
//                                    │  Хэш-таблица   │         │ Отсортированное│
//                                    │                │         │ отображение    │
//                                    └────────▲───────┘         └────────▲───────┘
//                                             │                          │
//                                    extends  │                          │  extends
//                                   расширяет │                          │ расширяет
//                                             │                          │
//                                    ┌─────────────────┐        ┌─────────────────┐
//                                    │LinkedHashMap [C]│        │NavigableMap [I] │
//                                    │Хэш-таблица с    │        │Навигационное    │
//                                    │порядком вставки │        │отображение      │
//                                    └─────────────────┘        └────────▲────────┘
//
//                                                                        │ implements
//                                                                           реализует
//                                                                        │
//                                                               ┌─────────────────┐
//                                                               │   TreeMap [C]   │
//                                                               │   Отображение на│
//                                                               │   дереве поиска │
//                                                               └─────────────────┘

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ КОЛЛЕКЦИЙ В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * Ключевое отличие коллекций от массивов
 * Массивы   — это статическая структура фиксированного размера, хранящая элементы одного типа (включая примитивы).
 * Коллекции — это динамические структуры, работающие исключительно с объектами (примитивы оборачиваются),
 * предоставляющие богатый API для манипуляций с данными и автоматически управляющие своей вместимостью.
 * Коллекции реализуют различные контракты поведения, тогда как массив — просто контейнер с индексацией.
 *
 * Collections Framework (фреймворк коллекций) — это унифицированная архитектура в Java, состоящая из набора классов,
 * интерфейсов и алгоритмов, предназначенных для хранения, обработки и управления группами объектов (элементов).
 * Это библиотека готовых структур данных, которая предоставляет высокоуровневые абстракции
 * для эффективной организации, добавления, удаления, поиска, сортировки и итерации по данным.
 *
 * Порядок элементов
 * Поведение коллекций относительно порядка элементов определяется их основным интерфейсом и реализацией.
 * Варианты:
 * 1) Порядок сохраняется              (в порядке вставки).
 * 2) Порядок определяется сортировкой (естественный порядок / компаратор).
 * 3) Порядок не гарантируется         (может меняться).
 * 4) Порядок определяется правилами   (например, FIFO/LIFO/приоритет).
 *
 * Дубликаты
 * Возможность хранения дублирующихся элементов — ещё одно ключевое различие, которое зависит от “роли” структуры.
 * Варианты:
 * 1) Дубликаты разрешены                                    (неограниченный повтор).
 * 2) Дубликаты запрещены                                    (уникальные элементы).
 * 3) Дубликаты запрещены по ключу, но разрешены по значению (ключи уникальны, значения могут повторяться).
 *
 * Допустимость null
 * Коллекции по-разному относятся к хранению null-значений.
 * Варианты:
 * 1) null допускается как элемент.
 * 2) null не допускается и приводит к исключению при добавлении/использовании.
 * 3) null допускается частично (разные правила для разных ролей данных, например для “идентификатора” и “значения”).
 *
 * Скорость основных операций
 * Эффективность операций (добавление, удаление, поиск, доступ по индексу) кардинально различается в зависимости от
 * выбранного интерфейса и его внутренней организации. Например, одни коллекции обеспечивают мгновенный доступ к
 * элементу по его позиции, но могут быть медленны при вставке в начало. Другие, наоборот, оптимизированы для
 * быстрой вставки/удаления, но поиск элемента в них может требовать полного обхода.
 *
 * Потокобезопасность
 * Подавляющее большинство стандартных коллекций не являются потокобезопасными (не синхронизированы). Это сделано для
 * максимальной производительности в однопоточных сценариях. Если коллекция используется несколькими потоками
 * одновременно, клиент должен самостоятельно обеспечить внешнюю синхронизацию или использовать специальные обёртки,
 * предоставляемые классом Collections, либо коллекции из пакета java.util.concurrent, которые спроектированы для
 * параллельного доступа.
 *
 * Внутренняя структура
 * За абстрактными интерфейсами коллекций скрываются различные внутренние структуры данных (например, на основе
 * массивов, связных списков, деревьев или хэш-таблиц), которые и определяют их поведение и производительность.
 * Детали реализации инкапсулированы, что позволяет легко заменять одну коллекцию на другую, если они реализуют
 * один и тот же интерфейс, не меняя остальной код, работающий через этот интерфейс.
 *
 * @author Sergey
 */

/**
 * Асимптотика (Big-O) — это “насколько хуже станет операция, если данных станет больше”. n — количество элементов.
 * Обозначения:
 * O(1) — константно
 * (≈ “один шаг”; размер почти не влияет)
 * Смысл: операция делает фиксированное количество действий.
 * Как читать:
 * “добавим/удаляем/получим элемент — и не важно, 10 там элементов или 10 000 000, объём работы примерно одинаковый”.
 *
 * O(log n) — логарифмически
 * (≈ “делим пополам”; шагов мало даже при большом n)
 * Смысл: каждый шаг резко уменьшает область поиска.
 * Интуиция: ищем “угадай число” — после каждого ответа остаётся половина вариантов.
 * Как читать:
 * “элементов стало в 2 раза больше — добавится примерно 1 шаг”.
 *
 * O(n) — линейно
 * (≈ “пройтись по всем”; рост прямо пропорционален)
 * Смысл: в худшем случае нужно посмотреть много элементов (иногда все).
 * Интуиция: поиск элемента “в лоб” в списке/массиве — пока не найдёшь.
 * Как читать:
 * “элементов стало в 10 раз больше — работы станет примерно в 10 раз больше”.
 *
 * O(n log n) — “линейно + умная надбавка” (часто сортировки)
 * (≈ “по всем + делим пополам”; чуть хуже O(n))
 * Смысл: нужно обработать все элементы, но при этом поддерживать структуру/порядок через “деление пополам” внутри.
 * Интуиция: сортировка сравнениями: элементы нужно не просмотреть, а ещё “встроить” в правильное место по правилам.
 * Как читать:
 * “примерно как O(n), но ощутимо тяжелее на больших данных; всё ещё нормально по сравнению с n²”.
 *
 * O(n²) — квадратично
 * (≈ “каждый с каждым”; быстро становится слишком медленно)
 * Смысл: для каждого элемента приходится делать работу почти со всеми остальными.
 * Интуиция: два вложенных цикла: внешний идёт по n, внутренний снова по n.
 * Как читать:
 * “элементов в 10 раз больше — работы примерно в 100 раз больше; это обычно первая причина ‘всё внезапно тормозит’”.
 */
public class _09_0_CollectionsFramework {

    // =================================================================================================================
    // 1) Iterable / for-each / Iterator
    // =================================================================================================================
    private static void demonstrateForEachAsUnifiedIteration() {
        System.out.println("1) Iterable: for-each работает единообразно, потому что использует iterator()");

        List<String> tags = new ArrayList<>(List.of("core", "collections", "iteration"));
        System.out.println("   Коллекция (как источник элементов): " + tags);

        System.out.println("   Обход через for-each:");
        for (String tag : tags) {
            System.out.println("   - element=\"" + tag + "\"");
        }

        System.out.println();
    }

    private static void demonstrateIteratorManualWalk() {
        System.out.println("2) Iterator: тот же обход вручную (for-each = синтаксический сахар)");

        List<String> tags = new ArrayList<>(List.of("iterator", "hasNext", "next"));
        Iterator<String> iterator = tags.iterator();

        System.out.println("   Ручной обход:");
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println("   - element=\"" + element + "\"");
        }

        System.out.println();
    }

    // =================================================================================================================
    // 2) Модификация во время обхода: fail-fast и безопасное удаление
    // =================================================================================================================
    private static void demonstrateFailFastOnStructuralModification() {
        System.out.println("3) Fail-fast: структурная модификация коллекции во время обхода обычно запрещена");

        List<String> values = new ArrayList<>(List.of("alpha", "beta", "gamma"));
        System.out.println("   Исходная коллекция: " + values);
        System.out.println("   Сценарий: в процессе for-each добавляем элемент → ожидаем исключение");

        try {
            for (String value : values) {
                System.out.println("   - iterate value=\"" + value + "\"");
                if ("beta".equals(value)) {
                    values.add("delta");
                    System.out.println("     added element=\"delta\" (структурная модификация)");
                }
            }
            System.out.println("   Неожиданно: обход завершился без ошибки (редко/зависит от структуры)");
        } catch (ConcurrentModificationException exception) {
            System.out.println(
                    "   Ожидаемо: ConcurrentModificationException (fail-fast защитил от некорректного обхода)"
            );
        }

        System.out.println("   Состояние коллекции после попытки: " + values);
        System.out.println();
    }

    private static void demonstrateSafeRemovalViaIterator() {
        System.out.println("4) Безопасное удаление во время обхода: через Iterator.remove()");

        List<String> values = new ArrayList<>(List.of("alpha", "beta", "gamma", "beta"));
        System.out.println("   Исходная коллекция: " + values);
        System.out.println("   Сценарий: удалить все элементы, равные \"beta\"");

        Iterator<String> iterator = values.iterator();
        while (iterator.hasNext()) {
            String value = iterator.next();
            if ("beta".equals(value)) {
                iterator.remove(); // корректное структурное удаление через текущий итератор
                System.out.println("   removed value=\"beta\" via Iterator.remove()");
            }
        }

        System.out.println("   Итоговая коллекция: " + values);
        System.out.println();
    }

    // =================================================================================================================
    // 3) Map: обход пар key+value (entry)
    // =================================================================================================================
    private static void demonstrateMapViewsAndIteration() {
        System.out.println("5) Map: данные логически представлены как пары (key + value)");

        Map<String, Integer> attemptsByUser = new HashMap<>();
        attemptsByUser.put("alice", 2);
        attemptsByUser.put("bob", 5);
        attemptsByUser.put("chen", 1);

        System.out.println("   Исходный Map: " + attemptsByUser);
        System.out.println("   Правильный способ обхода пар: entrySet()");

        for (Map.Entry<String, Integer> entry : attemptsByUser.entrySet()) {
            System.out.println("   - userId=\"" + entry.getKey() + "\", attempts=" + entry.getValue());
        }

        System.out.println("   Смысл: key и value читаются вместе как одна запись (entry), без лишней логики поиска.");
        System.out.println();
    }

    // =================================================================================================================
    // 4) equals/hashCode контракт + риск мутабельных ключей
    // =================================================================================================================
    private static void demonstrateEqualsHashCodeContractAsRequirement() {
        System.out.println("6) equals()/hashCode(): контракт обязателен для корректной работы структур поиска");

        System.out.println("   6.1) Ошибка: equals() переопределён, а hashCode() нет → логика \"равенства\" ломается");

        Set<BrokenKey> brokenKeySet = new HashSet<>();
        BrokenKey first = new BrokenKey(10);
        BrokenKey second = new BrokenKey(10);

        brokenKeySet.add(first);

        System.out.println("   added key=" + first);
        System.out.println("   first.equals(second)           → " + first.equals(second) + " (логически равны)");
        System.out.println("   set.contains(second)           → " + brokenKeySet.contains(second)
                + " (может быть false: hashCode не согласован с equals)");
        System.out.println(
                "   Вывод: если equals=true, то hashCode обязан совпадать, иначе структуры поиска работают некорректно."
        );

        System.out.println();
        System.out.println("   6.2) Ошибка: ключ мутабелен и его \"значимые\" поля меняются после вставки");

        Map<MutableKey, String> valuesByKey = new HashMap<>();
        MutableKey key = new MutableKey("A");
        valuesByKey.put(key, "stored-value");

        System.out.println("   put key=" + key + ", value=\"stored-value\"");
        System.out.println("   get(key) before mutation        → \"" + valuesByKey.get(key) + "\"");

        key.setValue("B"); // меняем поле, участвующее в equals/hashCode

        System.out.println("   key mutated: now key=" + key);
        System.out.println("   get(key) after mutation         → " + valuesByKey.get(key)
                + " (часто null: ключ \"переехал\" в другую корзину)");
        System.out.println("   containsKey(key) after mutation → " + valuesByKey.containsKey(key));

        System.out.println(
                "   Вывод: поля, участвующие в equals/hashCode ключа, должны быть неизменяемыми после вставки."
        );
        System.out.println();
    }

    private static final class BrokenKey {
        private final int id;

        private BrokenKey(int id) {
            if (id <= 0) {
                throw new IllegalArgumentException("id должен быть положительным: id=" + id);
            }
            this.id = id;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BrokenKey)) {
                return false;
            }
            BrokenKey that = (BrokenKey) other;
            return id == that.id;
        }

        // hashCode() намеренно НЕ переопределён: демонстрация ошибки контракта

        @Override
        public String toString() {
            return "BrokenKey{id=" + id + "}";
        }
    }

    private static final class MutableKey {
        private String value;

        private MutableKey(String value) {
            this.value = normalize(value);
        }

        public void setValue(String value) {
            this.value = normalize(value);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("key.value обязателен и не должен быть пустым");
            }
            return value.trim();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MutableKey)) {
                return false;
            }
            MutableKey that = (MutableKey) other;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "MutableKey{value=\"" + value + "\"}";
        }
    }

    // =================================================================================================================
    // 5) Big-O как “рост числа шагов”
    // =================================================================================================================
    private static void demonstrateBigOAsReadingRule() {
        System.out.println("7) Big-O: читаем как рост количества шагов при росте n (n = число элементов)");
        System.out.println("   Это не миллисекунды. Это модель: \"насколько хуже станет при увеличении данных\".");
        System.out.println();

        int[] sizes = {8, 16, 32, 64};

        for (int n : sizes) {
            long constantSteps = simulateO1();
            long logSteps = simulateOLogN(n);
            long linearSteps = simulateON(n);
            long nLogSteps = simulateONLogN(n);
            long squareSteps = simulateON2(n);

            System.out.println("   n=" + n + " → steps:"
                    + " O(1)=" + constantSteps
                    + ", O(log n)=" + logSteps
                    + ", O(n)=" + linearSteps
                    + ", O(n log n)=" + nLogSteps
                    + ", O(n^2)=" + squareSteps);
        }

        System.out.println();
        System.out.println("   Интерпретация (на бытовом уровне):");
        System.out.println("   - O(1)       ≈ один и тот же объём работы всегда");
        System.out.println("   - O(log n)   ≈ \"делим пополам\"; даже при росте n шагов немного");
        System.out.println("   - O(n)       ≈ \"проходим все элементы\"; в 2 раза больше данных → 2 раза больше шагов");
        System.out.println("   - O(n log n) ≈ \"все элементы\" + внутренняя \"делёжка пополам\"");
        System.out.println("   - O(n^2)     ≈ \"каждый с каждым\"; рост данных быстро делает операцию неприемлемой");
        System.out.println();
    }

    private static long simulateO1() {
        long steps = 0;
        steps++; // операция 1
        steps++; // операция 2
        steps++; // операция 3
        return steps;
    }

    private static long simulateOLogN(int n) {
        long steps = 0;
        int remaining = n;
        while (remaining > 1) {
            remaining = remaining / 2;
            steps++;
        }
        return steps;
    }

    private static long simulateON(int n) {
        long steps = 0;
        for (int index = 0; index < n; index++) {
            steps++;
        }
        return steps;
    }

    private static long simulateONLogN(int n) {
        long steps = 0;
        for (int index = 0; index < n; index++) {
            int remaining = n;
            while (remaining > 1) {
                remaining = remaining / 2;
                steps++;
            }
        }
        return steps;
    }

    private static long simulateON2(int n) {
        long steps = 0;
        for (int firstIndex = 0; firstIndex < n; firstIndex++) {
            for (int secondIndex = 0; secondIndex < n; secondIndex++) {
                steps++;
            }
        }
        return steps;
    }

    /*
    1. Iterable / for-each / Iterator
   Демонстрация показывает, что любой объект, который реализует `Iterable`, можно обходить единообразно
   через `for-each`, потому что компилятор внутри превращает это в работу с `Iterator` (`hasNext()/next()`).
   Затем тот же обход делается вручную, чтобы было понятно, что именно происходит “под капотом”.

   2. Fail-fast при модификации во время обхода
      Демонстрация намеренно меняет коллекцию (например, `add`) внутри `for-each`, чтобы получить типичную ошибку
      `ConcurrentModificationException`: это защита от некорректного обхода при структурных изменениях. Нельзя
      безопасно менять структуру коллекции, пока её итератор идёт по элементам, иначе итератор теряет корректность.

   3. Безопасное удаление через `Iterator.remove()`
      Демонстрация показывает корректный способ удаления элементов “на лету”:
      не через `collection.remove(...)`, а через `iterator.remove()` у того же итератора, который выполняет обход.
      Итератор сам синхронизирует своё состояние с удалением, поэтому обход не ломается.

   4. Map: обход пар через `entrySet()`
      Демонстрация показывает, что `Map` логически хранит не “элементы”, а пары `key/value`,
      и правильный общий способ обхода — `for (Map.Entry<K,V> : map.entrySet())`.
      Вы получаете ключ и значение сразу одной записью без лишних операций поиска.

   5. Контракт `equals()/hashCode()`
      Демонстрация сначала создаёт ключ с переопределённым `equals`, но без корректного `hashCode`,
      чтобы показать, что `contains`/поиск могут работать “не так”, хотя объекты “равны по смыслу”.
      Для хеш-структур обязательное правило — если `equals=true`, то `hashCode` тоже должен совпадать.

   6. Риск мутабельных ключей
      Демонстрация кладёт объект-ключ в `HashMap`, а потом меняет поле, участвующее в `equals/hashCode`,
      и показывает, что `get`/`containsKey` могут перестать находить запись. Ключ после вставки должен
      оставаться неизменяемым по тем полям, которые участвуют в вычислении `hashCode` и логике `equals`.

   7. Big-O как “рост числа шагов”
      Демонстрация не меряет миллисекунды, а считает “условные шаги” для
      моделей O(1), O(log n), O(n), O(n log n), O(n²) при разных `n`, чтобы увидеть характер роста.
     */

    public static void main(String[] args) {
        demonstrateForEachAsUnifiedIteration();
        demonstrateIteratorManualWalk();
        demonstrateFailFastOnStructuralModification();
        demonstrateSafeRemovalViaIterator();
        demonstrateMapViewsAndIteration();
        demonstrateEqualsHashCodeContractAsRequirement();
        demonstrateBigOAsReadingRule();
    }
}
