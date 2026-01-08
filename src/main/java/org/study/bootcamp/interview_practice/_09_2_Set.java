package org.study.bootcamp.interview_practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ КОЛЛЕКЦИИ Set ИЗ JAVA И ЕЁ ОСОБЕННОСТЕЙ
 *
 * Set — это «множество»: коллекция уникальных элементов. Порядок элементов зависит от реализации.
 * equals для Set не зависит от порядка: важен только состав элементов.
 *
 * Примеры из “соцсети / банк”:
 * - HashSet (Хэш-множество):
 *   - проверка “уже лайкнул?” (быстрое contains по userId);
 *   - набор уникальных категорий/тегов (чтобы не было повторов).
 * - LinkedHashSet (Хэш-множество с порядком вставки):
 *   - уникальные элементы + нужен порядок вставки (например, “история последних уникальных запросов”).
 * - TreeSet (Множество на дереве поиска):
 *   - уникальные элементы + нужен отсортированный порядок и диапазоны
 *     (например, уникальные суммы/даты с быстрым получением ближайших значений).
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | HashSet (Хэш-множество)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок элементов          | Не гарантируется (может меняться)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Дубликаты                  | Запрещены (уникальность по equals/hashCode)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Допускается (не более одного null)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | Хэш-таблица (ключ — элемент; уникальность через equals/hashCode)
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | LinkedHashSet (Хэш-множество с порядком вставки)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок элементов          | Сохраняется (порядок вставки)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Дубликаты                  | Запрещены (уникальность по equals/hashCode)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Допускается (не более одного null)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | HashSet + связанный порядок (доп.ссылки для сохранения порядка)
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | TreeSet (Множество на дереве поиска)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок элементов          | Отсортирован (natural order или Comparator)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Дубликаты                  | Запрещены (уникальность по сравнению: compareTo/Comparator)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Обычно не допускается (для natural order будет NPE при add(null))
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | Сбалансированное дерево (Red-Black Tree)
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * @author Sergey
 */
public class _09_2_Set {

// O(1) — константно                    | O(n) — линейно                                  | O(log n) — логарифмически
// ≈ “один шаг”; размер почти не влияет | ≈ “пройтись по всем”; рост прямо пропорционален | ≈ “дерево”; рост медленный

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | HashSet
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(value)                 | в среднем O(1) (быстро); зависит от качества hashCode и количества коллизий
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(value)              | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | contains(value)            | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (средне) — порядок не гарантируется
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Быстрая проверка наличия элемента (contains) и операции add/remove в среднем.
     *  |                            | 2) Естественный выбор для “набора уникальных элементов”, когда порядок не важен.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Для своих объектов критичны корректные equals/hashCode.
     *  |                            | 2) Порядок не гарантируется.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | LinkedHashSet
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(value)                 | в среднем O(1) (быстро); обычно чуть медленнее HashSet из-за поддержки порядка
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(value)              | в среднем O(1) (быстро); обычно чуть медленнее HashSet
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | contains(value)            | в среднем O(1) (быстро); обычно чуть медленнее HashSet
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (средне) — в порядке вставки
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Уникальность как у HashSet + предсказуемый порядок вставки.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Чуть больше накладных расходов по памяти и времени (хранится порядок).
     *  |                            | 2) Для своих объектов критичны корректные equals/hashCode.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | TreeSet
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(value)                 | O(log n) (средне) — поддерживается сортировка
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(value)              | O(log n) (средне)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | contains(value)            | O(log n) (средне)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (средне) — в отсортированном порядке
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Всегда отсортированный порядок.
     *  |                            | 2) Удобны “диапазоны”, навигация: first/last/lower/higher/subSet/headSet/tailSet.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Когда нужна проверка наличия (contains), обычно медленнее HashSet на объёмах.
     *  |                            | 2) Требует корректного сравнения (Comparable/Comparator).
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /*
        1) HashSet (хэш-множество)
        Создание пустого множества:
        java.util.Set<Тип> имяМножества = new java.util.HashSet<>();

        Создание из другой коллекции (копирование элементов):
        java.util.Set<Тип> имяМножества = new java.util.HashSet<>(java.util.List.of(элемент1, элемент2, ...));

        2) LinkedHashSet (хэш-множество с порядком вставки)
        java.util.Set<Тип> имяМножества = new java.util.LinkedHashSet<>();

        Создание из другой коллекции (копирование элементов + сохранение порядка итерации, как в источнике):
        java.util.Set<Тип> имяМножества = new java.util.LinkedHashSet<>(sourceCollection);

        3) TreeSet (множество на дереве поиска; отсортированное)
        java.util.Set<Тип> имяМножества = new java.util.TreeSet<>();                 // элементы должны быть Comparable
        java.util.Set<Тип> имяМножества = new java.util.TreeSet<>(comparator);       // порядок через Comparator
        java.util.Set<Тип> имяМножества = new java.util.TreeSet<>(sourceCollection); // копирование + сортировка

        4) Неизменяемые множества (immutable)
        java.util.Set<Тип> readOnly = java.util.Set.of(элемент1, элемент2, ...);  // null запрещён, дубликаты запрещены
        java.util.Set<Тип> readOnlyCopy = java.util.Set.copyOf(sourceCollection); // null запрещён

        5) “Только чтение” как представление (view)
        java.util.Set<Тип> view = java.util.Collections.unmodifiableSet(mutableSet); // запрет модификаций через view
     */

    // =================================================================================================================
    // 1) Создание и базовые свойства: size / isEmpty / уникальность / порядок
    // =================================================================================================================
    private static void demonstrateCreationAndBasicProperties() {
        System.out.println("1) Создание Set и базовые свойства (size / isEmpty / уникальность / порядок)");

        java.util.Set<String> hashSetTags = new java.util.HashSet<>();
        java.util.Set<String> linkedHashSetTags = new java.util.LinkedHashSet<>();
        java.util.Set<String> treeSetTags = new java.util.TreeSet<>();

        System.out.println("   Создан HashSet:       " + hashSetTags
                + ", size=" + hashSetTags.size()       + ", isEmpty=" + hashSetTags.isEmpty());
        System.out.println("   Создан LinkedHashSet: " + linkedHashSetTags
                + ", size=" + linkedHashSetTags.size() + ", isEmpty=" + linkedHashSetTags.isEmpty());
        System.out.println("   Создан TreeSet:       " + treeSetTags
                + ", size=" + treeSetTags.size()       + ", isEmpty=" + treeSetTags.isEmpty());

        boolean isFirstAddedToHashSet = hashSetTags.add("первый");
        boolean isDuplicateAddedToHashSet = hashSetTags.add("первый");
        System.out.println("   HashSet.add(\"первый\")            -> "
                + isFirstAddedToHashSet + ", состояние=" + hashSetTags);
        System.out.println("   HashSet.add(\"первый\") (дубликат) -> "
                + isDuplicateAddedToHashSet + ", состояние=" + hashSetTags + " (размер не изменился)");

        linkedHashSetTags.add("третий");
        linkedHashSetTags.add("первый");
        linkedHashSetTags.add("второй");

        hashSetTags.add("третий");
        hashSetTags.add("второй");

        treeSetTags.add("третий");
        treeSetTags.add("первый");
        treeSetTags.add("второй");

        System.out.println("   Порядок (может отличаться):");
        System.out.println("   - HashSet:       " + hashSetTags       + " (порядок не гарантируется)");
        System.out.println("   - LinkedHashSet: " + linkedHashSetTags + " (порядок вставки)");
        System.out.println("   - TreeSet:       " + treeSetTags       + " (отсортированный порядок)");

        System.out.println();
    }

    // =================================================================================================================
    // 2) Добавление: add / addAll (без индексов; add возвращает признак изменения)
    // =================================================================================================================
    private static void demonstrateAddOperations(java.util.Set<String> targetSet, String setName) {
        System.out.println("2) Добавление элементов (" + setName + "): add / addAll");

        System.out.println("   Исходное множество: " + targetSet);

        boolean isSecondAdded = targetSet.add("второй");
        System.out.println("   add(\"второй\")                           -> изменено="
                + isSecondAdded + ", состояние=" + targetSet);

        boolean isFirstAdded = targetSet.add("первый");
        System.out.println("   add(\"первый\")                           -> изменено="
                + isFirstAdded + ", состояние=" + targetSet);

        boolean isDuplicateAdded = targetSet.add("первый");
        System.out.println("   add(\"первый\") (дубликат)                -> изменено="
                + isDuplicateAdded + ", состояние=" + targetSet);

        java.util.List<String> batch = java.util.List.of("третий", "четвертый", "второй");
        boolean isBatchAdded = targetSet.addAll(batch);
        System.out.println("   addAll([\"третий\",\"четвертый\",\"второй\"]) -> изменено="
                + isBatchAdded + ", состояние=" + targetSet + " (\"второй\" уже мог быть)");

        System.out.println();
    }

    // =================================================================================================================
    // 3) Поиск: contains / containsAll (важна корректность equals; для hash-based также hashCode)
    // =================================================================================================================
    private static void demonstrateContainsOperations(java.util.Set<String> targetSet, String setName) {
        System.out.println("3) Поиск (" + setName + "): contains / containsAll");

        System.out.println("   Текущее множество: " + targetSet);

        boolean isContainsSecond = targetSet.contains("второй");
        System.out.println("   contains(\"второй\")               -> " + isContainsSecond);

        boolean isContainsNotExisting = targetSet.contains("не-существует");
        System.out.println("   contains(\"не-существует\")        -> " + isContainsNotExisting);

        java.util.Set<String> required = java.util.Set.of("первый", "второй");
        boolean isContainsAllRequired = targetSet.containsAll(required);
        System.out.println("   containsAll([\"первый\",\"второй\"]) -> " + isContainsAllRequired);

        System.out.println("   Примечание: contains/containsAll используют equals().");
        System.out.println("   Для HashSet/LinkedHashSet дополнительно критичен hashCode().");
        System.out.println("   Для TreeSet уникальность/поиск опираются на сравнение (Comparable/Comparator).");

        System.out.println();
    }

    // =================================================================================================================
    // 4) Удаление: remove / removeAll / retainAll / clear / removeIf
    // =================================================================================================================
    private static void demonstrateRemoveOperations(java.util.Set<String> targetSet, String setName) {
        System.out.println("4) Удаление (" + setName + "): remove / removeAll / retainAll / clear / removeIf");

        System.out.println("   До удаления: " + targetSet);

        boolean isRemovedSecond = targetSet.remove("второй");
        System.out.println("   remove(\"второй\")                      -> удалено="
                + isRemovedSecond + ", состояние=" + targetSet);

        boolean isRemovedNotExisting = targetSet.remove("не-существует");
        System.out.println("   remove(\"не-существует\")               -> удалено="
                + isRemovedNotExisting + ", состояние=" + targetSet);

        java.util.Set<String> toRemove = java.util.Set.of("первый", "не-существует");
        boolean isRemovedBatch = targetSet.removeAll(toRemove);
        System.out.println("   removeAll([\"первый\",\"не-существует\"]) -> изменено="
                + isRemovedBatch + ", состояние=" + targetSet);

        targetSet.addAll(java.util.Set.of("a1", "a2", "b1", "b2"));
        System.out.println("   Добавили для примеров: " + targetSet);

        boolean isRetained = targetSet.retainAll(java.util.Set.of("a1", "b2", "x"));
        System.out.println("   retainAll([\"a1\",\"b2\",\"x\"])            -> изменено="
                + isRetained + ", состояние=" + targetSet);

        boolean isRemovedIf = targetSet.removeIf(value -> value.startsWith("a"));
        System.out.println("   removeIf(startsWith(\"a\"))             -> изменено="
                + isRemovedIf + ", состояние=" + targetSet);

        targetSet.clear();
        System.out.println("   clear()                               -> состояние="
                + targetSet + ", size=" + targetSet.size());

        System.out.println();
    }

    // =================================================================================================================
    // 5) Итерация: for-each / Iterator (Iterator.remove для безопасного удаления во время обхода)
    // =================================================================================================================
    private static void demonstrateIteration(java.util.Set<String> targetSet, String setName) {
        System.out.println("5) Итерация (" + setName + "): for-each / Iterator");

        targetSet.addAll(java.util.Set.of("alpha", "beta", "gamma"));
        System.out.println("   Текущее множество: " + targetSet);

        System.out.println("   Обход через for-each:");
        for (String value : targetSet) {
            System.out.println("   - value=\"" + value + "\"");
        }

        System.out.println("   Удаление во время обхода: через Iterator.remove()");
        java.util.Iterator<String> iterator = targetSet.iterator();
        while (iterator.hasNext()) {
            String value = iterator.next();
            if ("beta".equals(value)) {
                iterator.remove();
                System.out.println("   removed value=\"beta\" via Iterator.remove()");
            }
        }
        System.out.println("   Итог после удаления: " + targetSet);

        System.out.println();
    }

    // =================================================================================================================
    // 6) Преобразование и сравнение: toArray / копирование / equals
    // =================================================================================================================
    private static void demonstrateConversionsAndEquality() {
        System.out.println("6) Преобразование и сравнение: toArray / копирование / equals");

        java.util.Set<String> source = new java.util.LinkedHashSet<>(java.util.List.of("первый", "второй", "третий"));
        System.out.println("   Исходное множество (LinkedHashSet): " + source);

        Object[] asObjectArray = source.toArray();
        System.out.println("   toArray()                          -> Object[], длина="
                + asObjectArray.length + ", первый=\"" + asObjectArray[0] + "\" (порядок зависит от реализации)");

        String[] asStringArray = source.toArray(new String[0]);
        System.out.println("   toArray(new String[0])             -> String[], длина="
                + asStringArray.length + ", последний=\"" + asStringArray[asStringArray.length - 1] + "\"");

        java.util.Set<String> copyAsHashSet = new java.util.HashSet<>(source);
        System.out.println("   Копирование: new HashSet<>(source) -> "
                + copyAsHashSet);

        boolean isEquals = source.equals(copyAsHashSet);
        System.out.println("   source.equals(copyAsHashSet)       -> "
                + isEquals + " (для Set порядок не влияет)");

        copyAsHashSet.add("четвертый");
        System.out.println("   copyAsHashSet.add(\"четвертый\")     -> source="
                + source + ", copyAsHashSet=" + copyAsHashSet);

        System.out.println();
    }

    // =================================================================================================================
    // 7) null в Set: HashSet/LinkedHashSet допускают один null; TreeSet зависит от Comparator
    // =================================================================================================================
    private static void demonstrateNullTheoryAndPractice() {
        System.out.println("7) null в Set: теория + практика");
        /*
            HashSet/LinkedHashSet допускают null (как элемент), но не более одного (Set остаётся уникальным).
            TreeSet с natural order не умеет сравнивать null с другими значениями -> NullPointerException.
            TreeSet может поддерживать null, если Comparator умеет сравнивать null (например, nullsFirst/nullsLast).
         */

        java.util.Set<String> hashSet = new java.util.HashSet<>();
        boolean isNullAddedFirst = hashSet.add(null);
        boolean isNullAddedSecond = hashSet.add(null);

        System.out.println("   HashSet.add(null) (первый раз) -> изменено="
                + isNullAddedFirst + ", состояние=" + hashSet);
        System.out.println("   HashSet.add(null) (второй раз) -> изменено="
                + isNullAddedSecond + ", состояние=" + hashSet + " (дубликат null не добавился)");

        java.util.Set<String> treeSetNaturalOrder = new java.util.TreeSet<>();
        try {
            treeSetNaturalOrder.add(null);
            System.out.println("   Неожиданно: TreeSet.add(null) выполнен без ошибки");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException для TreeSet.add(null) при natural order");
        }

        java.util.Comparator<String> nullFriendlyComparator =
                java.util.Comparator.nullsFirst(java.util.Comparator.naturalOrder());
        java.util.Set<String> treeSetWithNulls = new java.util.TreeSet<>(nullFriendlyComparator);
        treeSetWithNulls.add("b");
        treeSetWithNulls.add(null);
        treeSetWithNulls.add("a");
        System.out.println(
                "   TreeSet с Comparator.nullsFirst: " + treeSetWithNulls + " (null допустим и стоит первым)"
        );

        System.out.println();
    }

    // =================================================================================================================
    // 8) equals/hashCode: критично для HashSet/LinkedHashSet (поиск/уникальность)
    // =================================================================================================================
    private static void demonstrateHashCodeEqualsTheoryAndPractice() {
        System.out.println("8) equals/hashCode: теория + практика (на примере HashSet)");
        /*
            Для HashSet/LinkedHashSet:
            - уникальность определяется через hashCode + equals;
            - contains/remove также используют hashCode + equals.
            Следствие: если equals/hashCode описаны неверно, Set будет “ломаться” (дубликаты/поиск).
         */

        java.util.Set<DemoUser> users = new java.util.HashSet<>();
        users.add(new DemoUser(10, "Сергей"));
        users.add(new DemoUser(20, "Иван"));

        System.out.println("   Множество пользователей: " + users);

        DemoUser lookupSameIdDifferentName = new DemoUser(10, "ДругоеИмя");
        boolean containsUser = users.contains(lookupSameIdDifferentName);

        System.out.println("   Поиск: " + lookupSameIdDifferentName);
        System.out.println("   users.contains(lookup)        -> "
                + containsUser + " (результат зависит от equals/hashCode; здесь равенство по userId)");

        boolean isAddedDuplicate = users.add(new DemoUser(10, "ЕщёОдноИмя"));
        System.out.println("   users.add(дубликат по userId) -> изменено=" + isAddedDuplicate + ", состояние=" + users);

        System.out.println();
    }

    // =================================================================================================================
    // 9) Set.of / Set.copyOf: неизменяемость + запрет null + запрет дубликатов на этапе создания
    // =================================================================================================================
    private static void demonstrateSetFactoriesTheoryAndPractice() {
        System.out.println("9) Set.of / Set.copyOf: теория + практика");
        /*
            Set.of(...) создаёт неизменяемое (immutable) множество.
            - add/remove/clear → UnsupportedOperationException
            - null запрещён → NullPointerException
            - дубликаты запрещены → IllegalArgumentException

            Set.copyOf(collection) — тоже неизменяемое множество;
            - если collection уже immutable-set подходящего типа, может вернуть его же.
         */

        java.util.Set<String> readOnlyRoles = java.util.Set.of("USER", "ADMIN");
        System.out.println("   Set.of(\"USER\", \"ADMIN\") -> " + readOnlyRoles);

        try {
            readOnlyRoles.add("MANAGER");
            System.out.println("   Неожиданно: readOnlyRoles.add выполнен без ошибки");
        } catch (UnsupportedOperationException exception) {
            System.out.println("   Ожидаемо: UnsupportedOperationException для readOnlyRoles.add(.)");
        }

        try {
            java.util.Set.of("ok", null);
            System.out.println("   Неожиданно: Set.of с null выполнен без ошибки");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException для Set.of(.) при наличии null-элемента");
        }

        try {
            java.util.Set.of("dup", "dup");
            System.out.println("   Неожиданно: Set.of с дубликатами выполнен без ошибки");
        } catch (IllegalArgumentException exception) {
            System.out.println("   Ожидаемо: IllegalArgumentException для Set.of(.) при наличии дубликатов");
        }

        java.util.Set<String> source = new java.util.HashSet<>(java.util.List.of("alpha", "beta", "beta"));
        java.util.Set<String> readOnlyCopy = java.util.Set.copyOf(source);
        System.out.println("   Set.copyOf(source) -> " + readOnlyCopy + " (дубликаты отбрасываются на уровне Set)");

        System.out.println();
    }

    // =================================================================================================================
    // 10) TreeSet / NavigableSet: навигация и диапазоны; subSet/headSet/tailSet возвращают view
    // =================================================================================================================
    private static void demonstrateNavigableSetTheoryAndPractice() {
        System.out.println("10) TreeSet / NavigableSet: теория + практика");
        /*
            TreeSet реализует NavigableSet.
            Важно:
            - порядок отсортирован;
            - методы навигации дают “соседние” элементы относительно ключа;
            - subSet/headSet/tailSet возвращают представления (view), а не независимые копии:
              изменения через view отражаются на исходном TreeSet.
         */

        java.util.NavigableSet<Integer> scores = new java.util.TreeSet<>();
        scores.addAll(java.util.List.of(40, 10, 30, 20, 50));
        System.out.println("   scores: " + scores);

        System.out.println("   first() -> " + scores.first());
        System.out.println("   last()  -> " + scores.last());

        System.out.println("   lower(30)  -> " + scores.lower(30) + " (строго меньше)");
        System.out.println("   higher(30) -> " + scores.higher(30) + " (строго больше)");

        java.util.NavigableSet<Integer> rangeView = scores.subSet(20, true, 40, true);
        System.out.println("   subSet(20..40, inclusive) view -> " + rangeView);

        rangeView.remove(30);
        System.out.println("   rangeView.remove(30) выполнен");
        System.out.println("   - rangeView: " + rangeView);
        System.out.println("   - scores:    " + scores + " (изменение видно в исходном TreeSet)");

        java.util.NavigableSet<Integer> headView = scores.headSet(40, true);
        java.util.NavigableSet<Integer> tailView = scores.tailSet(20, true);

        System.out.println("   headSet(<=40) view -> " + headView);
        System.out.println("   tailSet(>=20) view -> " + tailView);

        System.out.println();
    }

    // =================================================================================================================
    // 11) Уникальность в TreeSet определяется сравнением: compareTo/Comparator
    // =================================================================================================================
    private static void demonstrateTreeSetUniquenessByComparator() {
        System.out.println("11) TreeSet: уникальность определяется compareTo/Comparator");
        /*
            В TreeSet элемент считается “дубликатом”, если сравнение даёт 0.
            Это может отличаться от equals(). Практическое правило:
            - критерий сравнения должен быть согласован с equals, если ожидается одинаковая логика “уникальности”.
         */

        java.util.Comparator<DemoUser> byUserId = java.util.Comparator.comparingInt(DemoUser::getUserId);
        java.util.Set<DemoUser> treeUsers = new java.util.TreeSet<>(byUserId);

        DemoUser first = new DemoUser(10, "Сергей");
        DemoUser sameIdDifferentName = new DemoUser(10, "ДругоеИмя");

        treeUsers.add(first);
        boolean isSecondAdded = treeUsers.add(sameIdDifferentName);

        System.out.println("   treeUsers после add: " + treeUsers);
        System.out.println("   add(тот же userId, другое имя) -> изменено="
                + isSecondAdded + " (Comparator сравнил как 0, поэтому элемент не добавился)");

        System.out.println();
    }

    // =================================================================================================================
    // 12) Выбор реализации: скорость contains/add/remove vs порядок вставки vs сортировка/диапазоны
    // =================================================================================================================
    private static void demonstrateChoosingImplementation() {
        System.out.println("12) Выбор реализации Set: краткая памятка");
        /*
            Выбор зависит от требований:
            1) Нужны максимально быстрые contains/add/remove и порядок не важен -> HashSet.
            2) Нужны уникальные элементы и важен порядок вставки -> LinkedHashSet.
            3) Нужна сортировка и диапазоны/навигация -> TreeSet.
         */

        System.out.println("   HashSet       -> хэш-множество: быстрые contains/add/remove, порядок не гарантирован");
        System.out.println("   LinkedHashSet -> хэш-множество: contains/add/remove + порядок вставки");
        System.out.println("   TreeSet       -> множество на дереве поиска: сортировка + навигация/диапазоны");
        System.out.println();
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

        private int getUserId() {
            return userId;
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
        java.util.Set<String> hashSetDemo = new java.util.HashSet<>();
        demonstrateAddOperations(hashSetDemo, "HashSet");
        demonstrateContainsOperations(hashSetDemo, "HashSet");
        demonstrateRemoveOperations(hashSetDemo, "HashSet");
        demonstrateIteration(hashSetDemo, "HashSet");
        java.util.Set<String> linkedHashSetDemo = new java.util.LinkedHashSet<>();
        demonstrateAddOperations(linkedHashSetDemo, "LinkedHashSet");
        demonstrateContainsOperations(linkedHashSetDemo, "LinkedHashSet");
        demonstrateRemoveOperations(linkedHashSetDemo, "LinkedHashSet");
        demonstrateIteration(linkedHashSetDemo, "LinkedHashSet");
        java.util.Set<String> treeSetDemo = new java.util.TreeSet<>();
        demonstrateAddOperations(treeSetDemo, "TreeSet");
        demonstrateContainsOperations(treeSetDemo, "TreeSet");
        demonstrateRemoveOperations(treeSetDemo, "TreeSet");
        demonstrateIteration(treeSetDemo, "TreeSet");
        demonstrateConversionsAndEquality();
        demonstrateNullTheoryAndPractice();
        demonstrateHashCodeEqualsTheoryAndPractice();
        demonstrateSetFactoriesTheoryAndPractice();
        demonstrateNavigableSetTheoryAndPractice();
        demonstrateTreeSetUniquenessByComparator();
        demonstrateChoosingImplementation();
    }
}
