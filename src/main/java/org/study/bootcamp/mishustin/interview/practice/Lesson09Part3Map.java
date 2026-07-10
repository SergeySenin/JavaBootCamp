package org.study.bootcamp.mishustin.interview.practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ СТРУКТУРЫ Map ИЗ JAVA И ЕЁ ОСОБЕННОСТЕЙ
 *
 * Map — это «отображение»: хранит пары ключ -> значение.
 * Важно: Map НЕ является Collection (не расширяет Collection), у него отдельная ветка в Java Collections Framework.
 *
 * Ключевые свойства Map:
 * - Ключи уникальны (по equals/hashCode для HashMap/LinkedHashMap, по сравнению Comparator/compareTo для TreeMap).
 * - Значения могут повторяться.
 * - Доступ к значению выполняется по ключу.
 *
 * Примеры из “соцсети / банк”:
 * - HashMap:
 *   - задача: быстрый доступ по ключу (O(1) в среднем);
 *   - пример (соцсеть): userId -> статус пользователя (ONLINE/OFFLINE) для быстрого получения по идентификатору;
 *   - пример (банк): accountId -> текущий баланс/состояние счёта для быстрого чтения при обработке операций.
 * - LinkedHashMap:
 *   - задача: быстрый доступ по ключу + предсказуемый порядок итерации (в порядке вставки),
 *     либо «LRU-порядок» (по обращению) для кеша;
 *   - пример (соцсеть): кеш профилей по userId (последние открытые профили; вытеснение старых при превышении лимита);
 *   - пример (банк): кеш курсов валют по коду валюты (быстрый доступ + ограничение размера кеша).
 * - TreeMap:
 *   - задача: ключи отсортированы + диапазоны/навигация;
 *   - пример (соцсеть): события по timestamp (метка времени) -> описание события с выборкой диапазона “с t1 до t2”;
 *   - пример (банк): операции по LocalDate -> сводка/список операций, выписка за диапазон дат (subMap/headMap/tailMap).
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | HashMap (хэш-отображение)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок пар                | Не гарантируется (может меняться)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Уникальность ключей        | Да (equals/hashCode у ключа)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Допускается: 1 null-ключ, значения null допускаются
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | Хранит пары ключ->значение в “таблице” (массиве ячеек). По ключу вычисляется число
 * |                            | (hashCode), по нему выбирается ячейка; это даёт быстрый доступ в среднем. Если разные
 * |                            | ключи попали в одну и ту же ячейку (коллизия), то внутри ячейки будет хранится цепочка
 * |                            | элементов, а при большом количестве коллизий Java может автоматически заменить цепочку
 * |                            | на дерево, чтобы поиск в этой ячейке оставался быстрым. При росте размера таблица >.
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | LinkedHashMap (хэш-отображение с порядком)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок пар                | Сохраняется (порядок вставки) или порядок обращений (accessOrder=true)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Уникальность ключей        | Да (equals/hashCode у ключа)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Допускается: 1 null-ключ, значения null допускаются
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | По скорости и устройству похож на HashMap (“таблица” для быстрого поиска по ключу).
 * |                            | Дополнительно хранит порядок элементов: каждый элемент “связан” с предыдущим и
 * |                            | следующим, поэтому перебор идёт в предсказуемом порядке (обычно в порядке добавления).
 * |                            | При режиме accessOrder=true порядок меняется при чтении (get): недавно использованные
 * |                            | записи идут в конец, что удобно для LRU-кеша. Цена — немного больше памяти и нак. р-в.
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | TreeMap (отображение на дереве поиска)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок пар                | Отсортирован по ключу (natural order или Comparator)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Уникальность ключей        | Да (уникальность по сравнению: compareTo/Comparator)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Обычно null-ключ не допускается (для natural order будет NPE при put(null, ...))
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | Хранит пары ключ->значение в самобалансирующемся дереве. Дерево поддерживает
 * |                            | сортировку ключей: при добавлении/поиске/удалении оно перестраивается так, чтобы
 * |                            | высота оставалась небольшой, поэтому операции обычно O(log n). За счёт дерева доступны
 * |                            | “соседние” ключи и диапазоны: ближайший меньший/больший ключ, подкарты
 * |                            | “от ... до ...”, “всё меньше ...”, “всё больше ...”.
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * @author Sergey
 */
public class Lesson09Part3Map {

// O(1) — константно                    | O(n) — линейно                                  | O(log n) — логарифмически
// ≈ “один шаг”; размер почти не влияет | ≈ “пройтись по всем”; рост прямо пропорционален | ≈ “дерево”; рост медленный

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | HashMap
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | put(key, value)            | в среднем O(1) (быстро); зависит от качества hashCode и количества коллизий
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | get(key)                   | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(key)                | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | containsKey(key)           | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (средне) — порядок не гарантируется
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Быстрый доступ по ключу в среднем.
     *  |                            | 2) Универсальный выбор “по умолчанию”, когда порядок не важен.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Для своих ключей критичны корректные equals/hashCode.
     *  |                            | 2) Порядок итерации не гарантируется.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | LinkedHashMap
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | put(key, value)            | в среднем O(1) (быстро); обычно чуть медленнее HashMap из-за поддержки порядка
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | get(key)                   | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(key)                | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | containsKey(key)           | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (средне) — в порядке вставки (или в порядке обращений при accessOrder=true)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Предсказуемый порядок итерации (вставка или обращения).
     *  |                            | 2) Удобен для “последних N” и LRU-кеша
     *  |                            |    (accessOrder=true + контроль удаления старых записей).
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Чуть больше накладных расходов по памяти и времени (хранится порядок).
     *  |                            | 2) Для своих ключей критичны корректные equals/hashCode.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | TreeMap
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | put(key, value)            | O(log n) (средне) — поддерживается сортировка ключей
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | get(key)                   | O(log n) (средне)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(key)                | O(log n) (средне)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | containsKey(key)           | O(log n) (средне)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (средне) — в отсортированном порядке ключей
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Всегда отсортированный порядок по ключу.
     *  |                            | 2) Удобны диапазоны и навигация: first/last/lower/higher/subMap/headMap/tailMap.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Когда нужен только быстрый доступ по ключу, обычно медленнее HashMap
     *  |                            |    на объёмах (O(log n) вместо O(1) в среднем).
     *  |                            | 2) Требует корректного сравнения (Comparable/Comparator).
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /*
    Map — необходимый набор вариантов создания (популярные случаи)

    1) HashMap (хэш-отображение) — базовый выбор “по умолчанию”
    Создание пустой map:
    java.util.Map<Ключ, Значение> имяMap = new java.util.HashMap<>();

    Создание из другой map (независимая копия пар):
    java.util.Map<Ключ, Значение> имяMap = new java.util.HashMap<>(sourceMap);

    2) LinkedHashMap (хэш-отображение с порядком) — когда нужен предсказуемый порядок итерации
    Создание пустой map:
    java.util.Map<Ключ, Значение> имяMap = new java.util.LinkedHashMap<>();

    LRU-поведение по обращениям:
    java.util.Map<Ключ, Значение> имяMap = new java.util.LinkedHashMap<>(16, 0.75f, true);

    3) TreeMap (отсортированное отображение) — когда нужна сортировка, диапазоны и навигация
    java.util.NavigableMap<Ключ, Значение> имяMap = new java.util.TreeMap<>();           // ключи Comparable
    java.util.NavigableMap<Ключ, Значение> имяMap = new java.util.TreeMap<>(comparator); // порядок через Comparator

    Неизменяемая map (константа/фиксированный набор пар):
    java.util.Map<Ключ, Значение> имяMap = java.util.Map.of(ключ1, значение1, ключ2, значение2, ...);
    java.util.Map<Ключ, Значение> имяMap = java.util.Map.copyOf(sourceMap);                  // “снимок”; null запрещён
    java.util.Map<Ключ, Значение> имяMap = java.util.Collections.unmodifiableMap(sourceMap); // view “только чтение”
     */

    // =================================================================================================================
    // 1) Создание и базовые свойства: size / isEmpty / put / get / null-особенности
    // =================================================================================================================
    private static void demonstrateCreationAndBasicProperties() {
        System.out.println("1) Создание Map и базовые свойства (size / isEmpty / put / get)");

        java.util.Map<String, Integer> hashMapCounters = new java.util.HashMap<>();
        java.util.Map<String, Integer> linkedHashMapCounters = new java.util.LinkedHashMap<>();
        java.util.NavigableMap<String, Integer> treeMapCounters = new java.util.TreeMap<>();

        System.out.println("   Создан HashMap:       "
                + hashMapCounters
                + ", size=" + hashMapCounters.size() + ", isEmpty=" + hashMapCounters.isEmpty());
        System.out.println("   Создан LinkedHashMap: "
                + linkedHashMapCounters
                + ", size=" + linkedHashMapCounters.size() + ", isEmpty=" + linkedHashMapCounters.isEmpty());
        System.out.println("   Создан TreeMap:       "
                + treeMapCounters
                + ", size=" + treeMapCounters.size() + ", isEmpty=" + treeMapCounters.isEmpty());

        hashMapCounters.put("просмотры", 10);
        hashMapCounters.put("лайки", 3);
        System.out.println(
                "   После put в HashMap:  " + hashMapCounters + ", get(\"лайки\")=" + hashMapCounters.get("лайки")
        );

        linkedHashMapCounters.put("просмотры", 10);
        linkedHashMapCounters.put("лайки", 3);
        System.out.println("   После put в LinkedHashMap: "
                + linkedHashMapCounters + ", get(\"лайки\")=" + linkedHashMapCounters.get("лайки"));

        treeMapCounters.put("просмотры", 10);
        treeMapCounters.put("лайки", 3);
        System.out.println("   После put в TreeMap (отсортировано по ключу): " + treeMapCounters);

        hashMapCounters.put(null, 999);
        hashMapCounters.put("ошибки", null);
        System.out.println("   HashMap допускает null: ключ=null -> " + hashMapCounters.get(null)
                + ", ключ=\"ошибки\" -> " + hashMapCounters.get("ошибки"));

        try {
            treeMapCounters.put(null, 1);
            System.out.println("   Неожиданно: TreeMap.put(null, ...) выполнен без ошибки");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException для TreeMap.put(null, ...) при natural order");
        }

        System.out.println();
    }

    // =================================================================================================================
    // 2) Запись и обновление: put / putIfAbsent / replace / computeIfAbsent / computeIfPresent / merge
    // =================================================================================================================
    private static void demonstratePutAndUpdateOperations(java.util.Map<String, Integer> targetMap, String mapName) {
        System.out.println("2) Запись и обновление (" + mapName + "): put / putIfAbsent / replace / compute / merge");

        System.out.println("   Стартовое состояние: " + targetMap);

        Integer previousValue = targetMap.put("user-1", 10);
        System.out.println("   put(\"user-1\", 10)               " +
                "-> previousValue=" + previousValue + ", map=" + targetMap);

        previousValue = targetMap.put("user-2", 5);
        System.out.println("   put(\"user-2\", 5)                " +
                "-> previousValue=" + previousValue + ", map=" + targetMap);

        previousValue = targetMap.put("user-1", 11);
        System.out.println("   put(\"user-1\", 11) (перезапись)  " +
                "-> previousValue=" + previousValue + ", map=" + targetMap);

        Integer existingValue = targetMap.putIfAbsent("user-1", 100);
        System.out.println("   putIfAbsent(\"user-1\", 100)      " +
                "-> existingValue=" + existingValue + ", map=" + targetMap);

        existingValue = targetMap.putIfAbsent("user-3", 1);
        System.out.println("   putIfAbsent(\"user-3\", 1)        " +
                "-> existingValue=" + existingValue + ", map=" + targetMap);

        boolean isReplaced = targetMap.replace("user-2", 5, 6);
        System.out.println("   replace(\"user-2\", 5, 6)         -> replaced=" + isReplaced + ", map=" + targetMap);

        isReplaced = targetMap.replace("user-2", 5, 7);
        System.out.println("   replace(\"user-2\", 5, 7)         -> replaced=" + isReplaced + ", map=" + targetMap);

        Integer computedValue = targetMap.computeIfAbsent("user-4", key -> {
            System.out.println("      computeIfAbsent вызван для key=\"" + key + "\"");
            return 1;
        });
        System.out.println(
                "   computeIfAbsent(\"user-4\", ...)  -> computedValue=" + computedValue + ", map=" + targetMap
        );

        computedValue = targetMap.computeIfPresent("user-1", (key, value) -> {
            System.out.println("      computeIfPresent вызван для key=\"" + key + "\", value=" + value);
            return value + 1;
        });
        System.out.println(
                "   computeIfPresent(\"user-1\", ...) -> computedValue=" + computedValue + ", map=" + targetMap)
        ;

        Integer mergedValue = targetMap.merge("user-2", 10, (currentValue, newValue) -> {
            System.out.println("      merge вызван: currentValue=" + currentValue + ", newValue=" + newValue);
            return currentValue + newValue;
        });
        System.out.println("   merge(\"user-2\", 10, ...)        -> mergedValue=" + mergedValue + ", map=" + targetMap);

        mergedValue = targetMap.merge("user-5", 7, (currentValue, newValue) -> currentValue + newValue);
        System.out.println("   merge(\"user-5\", 7, ...)         -> mergedValue=" + mergedValue + ", map=" + targetMap);

        System.out.println();
    }

    // =================================================================================================================
    // 3) Чтение: get / getOrDefault / containsKey / containsValue (и стоимость containsValue)
    // =================================================================================================================
    private static void demonstrateReadOperations(java.util.Map<String, Integer> targetMap, String mapName) {
        System.out.println("3) Чтение (" + mapName + "): get / getOrDefault / containsKey / containsValue");

        System.out.println("   Текущее состояние: " + targetMap);

        Integer user1Value = targetMap.get("user-1");
        Integer missingValue = targetMap.get("missing-user");

        System.out.println("   get(\"user-1\")                   -> " + user1Value);
        System.out.println("   get(\"missing-user\")             -> " + missingValue);

        Integer defaultedValue = targetMap.getOrDefault("missing-user", 0);
        System.out.println("   getOrDefault(\"missing-user\", 0) -> " + defaultedValue);

        boolean hasUser3 = targetMap.containsKey("user-3");
        boolean hasUser100 = targetMap.containsKey("user-100");
        System.out.println("   containsKey(\"user-3\")           -> " + hasUser3);
        System.out.println("   containsKey(\"user-100\")         -> " + hasUser100);

        boolean hasValue6 = targetMap.containsValue(6);
        boolean hasValue999 = targetMap.containsValue(999);
        System.out.println("   containsValue(6)                -> " + hasValue6);
        System.out.println("   containsValue(999)              -> " + hasValue999);

        System.out.println(
                "   Примечание: containsValue(...) почти всегда O(n), потому что нужно пройтись по всем значениям"
        );

        System.out.println();
    }

    // =================================================================================================================
    // 4) Удаление: remove(key) / remove(key, value) / clear
    // =================================================================================================================
    private static void demonstrateRemoveOperations(java.util.Map<String, Integer> targetMap, String mapName) {
        System.out.println("4) Удаление (" + mapName + "): remove / clear");

        System.out.println("   До удаления: " + targetMap);

        Integer removedValue = targetMap.remove("user-5");
        System.out.println("   remove(\"user-5\")    -> removedValue=" + removedValue + ", map=" + targetMap);

        boolean isRemovedPair = targetMap.remove("user-4", 2);
        System.out.println("   remove(\"user-4\", 2) -> removed=" + isRemovedPair + ", map=" + targetMap);

        isRemovedPair = targetMap.remove("user-4", 1);
        System.out.println("   remove(\"user-4\", 1) -> removed=" + isRemovedPair + ", map=" + targetMap);

        targetMap.clear();
        System.out.println("   clear()             -> map=" + targetMap);

        System.out.println();
    }

    // =================================================================================================================
    // 5) Обход: keySet / values / entrySet / forEach / Iterator по entrySet
    // =================================================================================================================
    private static void demonstrateIteration(java.util.Map<String, Integer> targetMap, String mapName) {
        System.out.println("5) Обход (" + mapName + "): keySet / values / entrySet / forEach");

        targetMap.put("user-1", 11);
        targetMap.put("user-2", 16);
        targetMap.put("user-3", 1);
        targetMap.put("user-4", 1);

        System.out.println("   Текущее состояние: " + targetMap);

        System.out.println("   1) Обход keySet:");
        for (String userKey : targetMap.keySet()) {
            Integer value = targetMap.get(userKey);
            System.out.println("      key=\"" + userKey + "\" -> value=" + value);
        }

        System.out.println("   2) Обход values:");
        for (Integer value : targetMap.values()) {
            System.out.println("      value=" + value);
        }

        System.out.println("   3) Обход entrySet (предпочтительно, если нужны и ключ, и значение):");
        for (java.util.Map.Entry<String, Integer> entry : targetMap.entrySet()) {
            System.out.println("      key=\"" + entry.getKey() + "\", value=" + entry.getValue());
        }

        System.out.println("   4) Map.forEach:");
        targetMap.forEach((key, value) -> System.out.println("      key=\"" + key + "\", value=" + value));

        System.out.println("   5) Iterator по entrySet с безопасным remove:");
        java.util.Iterator<java.util.Map.Entry<String, Integer>> entryIterator = targetMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            java.util.Map.Entry<String, Integer> entry = entryIterator.next();
            if (entry.getValue() != null && entry.getValue() <= 1) {
                System.out.println(
                        "      iterator.remove() для key=\"" + entry.getKey() + "\" (value=" + entry.getValue() + ")"
                );
                entryIterator.remove();
            }
        }
        System.out.println("      После iterator.remove: " + targetMap);

        System.out.println();
    }

    // =================================================================================================================
    // 6) Представления (view) над Map: keySet / values / entrySet — живые и связаны с исходной Map
    // =================================================================================================================
    private static void demonstrateViewsAreBackedByMap() {
        System.out.println("6) View-представления над Map: keySet / values / entrySet связаны с исходной Map");

        java.util.Map<String, String> userIdToRoleMap = new java.util.HashMap<>();
        userIdToRoleMap.put("user-1", "ПОЛЬЗОВАТЕЛЬ");
        userIdToRoleMap.put("user-2", "АДМИНИСТРАТОР");
        userIdToRoleMap.put("user-3", "АУДИТОР");

        System.out.println("   Исходная map: " + userIdToRoleMap);

        java.util.Set<String> userIdsView = userIdToRoleMap.keySet();
        java.util.Collection<String> rolesView = userIdToRoleMap.values();
        java.util.Set<java.util.Map.Entry<String, String>> entriesView = userIdToRoleMap.entrySet();

        System.out.println("   keySet view:  " + userIdsView);
        System.out.println("   values view:  " + rolesView);
        System.out.println("   entrySet view:" + entriesView);

        boolean isRemovedByKeySet = userIdsView.remove("user-2");
        System.out.println("   keySet.remove(\"user-2\")  " +
                "-> removed=" + isRemovedByKeySet + ", map=" + userIdToRoleMap);

        boolean isRemovedByValues = rolesView.remove("АУДИТОР");
        System.out.println("   values.remove(\"АУДИТОР\") " +
                "-> removed=" + isRemovedByValues + ", map=" + userIdToRoleMap);

        boolean isRemovedEntry = entriesView.remove(java.util.Map.entry("user-1", "ПОЛЬЗОВАТЕЛЬ"));
        System.out.println("   entrySet.remove(entry(\"user-1\",\"ПОЛЬЗОВАТЕЛЬ\")) -> removed=" + isRemovedEntry
                + ", map=" + userIdToRoleMap);

        try {
            userIdsView.add("user-100");
            System.out.println("   Неожиданно: keySet.add выполнен без ошибки");
        } catch (UnsupportedOperationException exception) {
            System.out.println("   Ожидаемо: UnsupportedOperationException" +
                    " для keySet.add(...) (добавлять ключи можно только через map)");
        }

        System.out.println();
    }

    // =================================================================================================================
    // 7) equals / hashCode для Map: зависит от набора пар, а не от порядка
    // =================================================================================================================
    private static void demonstrateEquality() {
        System.out.println("7) equals / hashCode для Map: важны пары key->value, порядок не важен");

        java.util.Map<String, Integer> firstMap = new java.util.HashMap<>();
        firstMap.put("a", 1);
        firstMap.put("b", 2);

        java.util.Map<String, Integer> secondMap = new java.util.LinkedHashMap<>();
        secondMap.put("b", 2);
        secondMap.put("a", 1);

        System.out.println("   firstMap:  " + firstMap);
        System.out.println("   secondMap: " + secondMap);

        System.out.println("   firstMap.equals(secondMap)            -> " + firstMap.equals(secondMap));
        System.out.println(
                "   firstMap.hashCode==secondMap.hashCode -> " + (firstMap.hashCode() == secondMap.hashCode())
        );

        System.out.println();
    }

    // =================================================================================================================
    // 8) Map.of / Map.copyOf / Collections.unmodifiableMap: неизменяемость и ограничения по null
    // =================================================================================================================
    private static void demonstrateMapOfAndUnmodifiableMap() {
        System.out.println("8) Неизменяемые Map: Map.of / Map.copyOf / Collections.unmodifiableMap");

        java.util.Map<String, Integer> constantMap = java.util.Map.of(
                "user-1", 10,
                "user-2", 5
        );
        System.out.println("   Map.of -> " + constantMap);

        try {
            constantMap.put("user-3", 1);
            System.out.println("   Неожиданно: Map.of.put выполнен без ошибки");
        } catch (UnsupportedOperationException exception) {
            System.out.println("   Ожидаемо: UnsupportedOperationException для Map.of(...) при попытке модификации");
        }

        try {
            java.util.Map.of("user-1", null);
            System.out.println("   Неожиданно: Map.of с null выполнен без ошибки");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException для Map.of(...) при наличии null ключа или значения");
        }

        java.util.Map<String, Integer> mutableMap = new java.util.HashMap<>();
        mutableMap.put("user-1", 10);
        mutableMap.put("user-2", 5);

        java.util.Map<String, Integer> snapshotCopy = java.util.Map.copyOf(mutableMap);
        System.out.println("   Map.copyOf (снимок): " + snapshotCopy);

        java.util.Map<String, Integer> readOnlyView = java.util.Collections.unmodifiableMap(mutableMap);
        System.out.println("   unmodifiableMap (view): " + readOnlyView);

        mutableMap.put("user-3", 1);
        System.out.println("   После изменения mutableMap: mutableMap=" + mutableMap);
        System.out.println("   snapshotCopy НЕ меняется:   snapshotCopy=" + snapshotCopy);
        System.out.println("   readOnlyView меняется:      readOnlyView=" + readOnlyView);

        try {
            readOnlyView.put("user-4", 100);
            System.out.println("   Неожиданно: readOnlyView.put выполнен без ошибки");
        } catch (UnsupportedOperationException exception) {
            System.out.println("   Ожидаемо: UnsupportedOperationException для unmodifiableMap.put(...)");
        }

        System.out.println();
    }

    // =================================================================================================================
    // 9) LinkedHashMap: порядок вставки и LRU (accessOrder=true + removeEldestEntry)
    // =================================================================================================================
    private static void demonstrateLinkedHashMapOrderAndLru() {
        System.out.println("9) LinkedHashMap: порядок вставки и LRU-кеш (accessOrder=true)");

        java.util.Map<String, Integer> insertionOrderMap = new java.util.LinkedHashMap<>();
        insertionOrderMap.put("A", 1);
        insertionOrderMap.put("B", 2);
        insertionOrderMap.put("C", 3);
        System.out.println("   LinkedHashMap (порядок вставки): " + insertionOrderMap);

        java.util.Map<String, Integer> accessOrderMap = new java.util.LinkedHashMap<>(16, 0.75f, true);
        accessOrderMap.put("A", 1);
        accessOrderMap.put("B", 2);
        accessOrderMap.put("C", 3);

        System.out.println("   До обращений (accessOrder=true): " + accessOrderMap);
        accessOrderMap.get("A");
        accessOrderMap.get("B");
        System.out.println("   После get(\"A\"), get(\"B\"):        " + accessOrderMap);

        LruCache<String, Integer> lruCache = new LruCache<>(3);
        lruCache.put("user-1", 10);
        lruCache.put("user-2", 20);
        lruCache.put("user-3", 30);
        System.out.println("   LRU cache (capacity=3): " + lruCache);

        lruCache.get("user-1");
        lruCache.put("user-4", 40);
        System.out.println("   После get(\"user-1\") и put(\"user-4\",40): " + lruCache);

        System.out.println();
    }

    // =================================================================================================================
    // 10) TreeMap / NavigableMap: сортировка, диапазоны (subMap/headMap/tailMap), навигация (floor/ceiling)
    // =================================================================================================================
    private static void demonstrateTreeMapSortingAndNavigation() {
        System.out.println("10) TreeMap / NavigableMap: сортировка, диапазоны и навигация");

        java.util.NavigableMap<Integer, String> eventsByTimestamp = new java.util.TreeMap<>();
        eventsByTimestamp.put(100, "создан аккаунт");
        eventsByTimestamp.put(105, "установлен аватар");
        eventsByTimestamp.put(110, "сделан первый пост");
        eventsByTimestamp.put(120, "получен лайк");

        System.out.println("   eventsByTimestamp (sorted): " + eventsByTimestamp);
        System.out.println("   firstKey=" + eventsByTimestamp.firstKey() + ", lastKey=" + eventsByTimestamp.lastKey());

        Integer floorKey = eventsByTimestamp.floorKey(112);
        Integer ceilingKey = eventsByTimestamp.ceilingKey(112);
        System.out.println("   floorKey(112)   -> " + floorKey);
        System.out.println("   ceilingKey(112) -> " + ceilingKey);

        java.util.NavigableMap<Integer, String> headMapInclusive = eventsByTimestamp.headMap(110, true);
        java.util.NavigableMap<Integer, String> tailMapExclusive = eventsByTimestamp.tailMap(110, false);
        System.out.println("   headMap(to=110, включительно) -> " + headMapInclusive);
        System.out.println("   tailMap(from=110, не включая) -> " + tailMapExclusive);

        java.util.NavigableMap<Integer, String> subMap = eventsByTimestamp.subMap(105, true, 120, false);
        System.out.println("   subMap(105 включительно .. 120 не включая) -> " + subMap);

        System.out.println("   Важно: headMap/tailMap/subMap — это view, изменения отражаются в исходной map");
        subMap.put(108, "изменено имя");
        System.out.println("   После subMap.put(108, ...): subMap=" + subMap);
        System.out.println("   Исходная map изменилась:    eventsByTimestamp=" + eventsByTimestamp);

        java.util.Map.Entry<Integer, String> firstEntry = eventsByTimestamp.pollFirstEntry();
        System.out.println("   pollFirstEntry() -> " + firstEntry + ", осталось=" + eventsByTimestamp);

        System.out.println();
    }

    // =================================================================================================================
    // 11) TreeMap + null: как разрешить null-ключ через Comparator.nullsFirst (и почему это нужно делать явно)
    // =================================================================================================================
    private static void demonstrateTreeMapNullKeyWithComparator() {
        System.out.println("11) TreeMap и null-ключ: Comparator.nullsFirst как явное решение");

        java.util.Comparator<String> nullFriendlyComparator =
                java.util.Comparator.nullsFirst(java.util.Comparator.naturalOrder());
        java.util.NavigableMap<String, Integer> treeMapWithNullKey =
                new java.util.TreeMap<>(nullFriendlyComparator);

        treeMapWithNullKey.put(null, 1);
        treeMapWithNullKey.put("A", 2);
        treeMapWithNullKey.put("B", 3);

        System.out.println("   TreeMap(nullsFirst(naturalOrder)): " + treeMapWithNullKey);

        System.out.println();
    }

    // =================================================================================================================
    // 12) Типичные ошибки: ключи без equals/hashCode, изменяемые ключи, ConcurrentModificationException
    // =================================================================================================================
    private static void demonstrateCommonPitfalls() {
        System.out.println("12) Типичные ошибки Map: equals/hashCode у ключа, изменяемые ключи, модификация при обходе");

        java.util.Map<DemoUser, String> userToStatusMap = new java.util.HashMap<>();
        DemoUser user1 = new DemoUser(1, "Иван");
        DemoUser user1DuplicateKey = new DemoUser(1, "Иван (дубликат по userId)");

        userToStatusMap.put(user1, "ONLINE");

        System.out.println("   userToStatusMap: " + userToStatusMap);
        System.out.println("   get(user1DuplicateKey) работает, если equals/hashCode определены по ключевому полю:");
        System.out.println("   get(new DemoUser(1,...)) -> " + userToStatusMap.get(user1DuplicateKey));

        java.util.Map<String, Integer> mapForFailFast = new java.util.HashMap<>();
        mapForFailFast.put("A", 1);
        mapForFailFast.put("B", 2);
        mapForFailFast.put("C", 3);

        try {
            for (String key : mapForFailFast.keySet()) {
                if ("B".equals(key)) {
                    mapForFailFast.remove("B");
                }
            }
            System.out.println("   Неожиданно: удаление при for-each выполнено без ошибки");
        } catch (java.util.ConcurrentModificationException exception) {
            System.out.println(
                    "   Ожидаемо: ConcurrentModificationException при структурной модификации Map во время for-each"
            );
        }

        System.out.println("   Правильный способ — Iterator.remove():");
        java.util.Iterator<java.util.Map.Entry<String, Integer>> iterator = mapForFailFast.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry<String, Integer> entry = iterator.next();
            if ("B".equals(entry.getKey())) {
                iterator.remove();
            }
        }
        System.out.println("   После iterator.remove: " + mapForFailFast);

        System.out.println();
    }

    private static final class LruCache<K, V> extends java.util.LinkedHashMap<K, V> {
        private final int capacity;

        private LruCache(int capacity) {
            super(16, 0.75f, true);
            if (capacity <= 0) {
                throw new IllegalArgumentException("capacity должен быть положительным: capacity=" + capacity);
            }
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    private static final class DemoUser {
        private final int userId;
        private final String displayName;

        private DemoUser(int userId, String displayName) {
            if (userId <= 0) {
                throw new IllegalArgumentException("userId должен быть положительным: userId=" + userId);
            }
            if (displayName == null || displayName.isBlank()) {
                throw new IllegalArgumentException("displayName обязателен и не должен быть пустым");
            }
            this.userId = userId;
            this.displayName = displayName.trim();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DemoUser)) {
                return false;
            }
            DemoUser that = (DemoUser) other;
            return userId == that.userId;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(userId);
        }

        @Override
        public String toString() {
            return "DemoUser{userId=" + userId + ", displayName=\"" + displayName + "\"}";
        }
    }

    public static void main(String[] args) {
        demonstrateCreationAndBasicProperties();
        java.util.Map<String, Integer> hashMapDemo = new java.util.HashMap<>();
        demonstratePutAndUpdateOperations(hashMapDemo, "HashMap");
        demonstrateReadOperations(hashMapDemo, "HashMap");
        demonstrateRemoveOperations(hashMapDemo, "HashMap");
        demonstrateIteration(hashMapDemo, "HashMap");
        java.util.Map<String, Integer> linkedHashMapDemo = new java.util.LinkedHashMap<>();
        demonstratePutAndUpdateOperations(linkedHashMapDemo, "LinkedHashMap");
        demonstrateReadOperations(linkedHashMapDemo, "LinkedHashMap");
        demonstrateRemoveOperations(linkedHashMapDemo, "LinkedHashMap");
        demonstrateIteration(linkedHashMapDemo, "LinkedHashMap");
        demonstrateViewsAreBackedByMap();
        demonstrateEquality();
        demonstrateMapOfAndUnmodifiableMap();
        demonstrateLinkedHashMapOrderAndLru();
        demonstrateTreeMapSortingAndNavigation();
        demonstrateTreeMapNullKeyWithComparator();
        demonstrateCommonPitfalls();
    }
}
