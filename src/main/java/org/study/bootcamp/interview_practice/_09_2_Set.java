package org.study.bootcamp.interview_practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ КОЛЛЕКЦИИ Set ИЗ JAVA И ЕЁ ОСОБЕННОСТЕЙ
 *
 * Set — это «множество»: коллекция уникальных элементов (без дубликатов).
 * В отличие от List:
 * - нет индексов и методов get(index)/set(index,...);
 * - add(value) возвращает false, если элемент уже был (дубликат не добавляется);
 * - equals для Set не зависит от порядка, важен только состав элементов.
 *
 * Примеры из “соцсети / банк”:
 * - HashSet:
 *   - проверка “уже лайкнул?” (быстрое contains по userId);
 *   - набор уникальных категорий/тегов (чтобы не было повторов).
 * - LinkedHashSet:
 *   - уникальные элементы + нужен порядок вставки (например, “история последних уникальных запросов”).
 * - TreeSet:
 *   - уникальные элементы + нужен отсортированный порядок и диапазоны
 *     (например, уникальные суммы/даты с быстрым получением ближайших значений).
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | HashSet
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок элементов          | Не гарантируется
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Дубликаты                  | Запрещены (второй add вернёт false)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Допускается (не более одного null)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | Хеш-таблица (ключ — элемент; уникальность через equals/hashCode)
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | LinkedHashSet
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок элементов          | Сохраняется (порядок вставки)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Дубликаты                  | Запрещены (второй add вернёт false)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Допускается (не более одного null)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | HashSet + связанный порядок (доп.ссылки для сохранения порядка)
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | TreeSet
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок элементов          | Отсортирован (natural order или Comparator)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Дубликаты                  | Запрещены (уникальность по сравнению/Comparator)
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

    // O(1) — константно                    | O(n) — линейно              | O(log n) — логарифмически
    // ≈ “один шаг”; размер почти не влияет | ≈ “пройтись по всем”        | ≈ “дерево”; рост медленный

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | HashSet / LinkedHashSet
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(value)                 | в среднем O(1) (быстро); зависит от качества hashCode и количества коллизий
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(value)              | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | contains(value)            | в среднем O(1) (быстро)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (линейно) — но порядок в HashSet не гарантируется
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Быстрые операции membership (contains/add/remove) в среднем.
     *  |                            | 2) Естественное хранение “уникального набора”.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Для “своих” объектов критичны корректные equals/hashCode.
     *  |                            | 2) HashSet не гарантирует порядок.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | TreeSet
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(value)                 | O(log n) (стабильно)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(value)              | O(log n)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | contains(value)            | O(log n)
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (линейно) — в отсортированном порядке
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Всегда отсортированный порядок.
     *  |                            | 2) Навигация и диапазоны: first/last/higher/lower/subSet/headSet/tailSet.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Медленнее HashSet на больших объёмах, если нужна только membership.
     *  |                            | 2) Требует корректного сравнения (Comparable/Comparator).
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /*
        1) HashSet
        Создание пустого множества:
        java.util.Set<Тип> имяНабора = new java.util.HashSet<>();

        Создание из другой коллекции (копирование элементов):
        java.util.Set<Тип> имяНабора = new java.util.HashSet<>(java.util.List.of(элемент1, элемент2, ...));

        2) LinkedHashSet (сохранение порядка вставки):
        java.util.Set<Тип> имяНабора = new java.util.LinkedHashSet<>();

        3) TreeSet (отсортированное множество):
        java.util.Set<Тип> имяНабора = new java.util.TreeSet<>(); // элементы должны быть Comparable
        java.util.Set<Тип> имяНабора = new java.util.TreeSet<>(comparator); // порядок через Comparator
     */

    // =================================================================================================================
    // 1) Создание и базовые свойства: size / isEmpty / уникальность / порядок (HashSet vs LinkedHashSet vs TreeSet)
    // =================================================================================================================
    private static void demonstrateCreationAndBasicProperties() {
        System.out.println("1) Создание Set и базовые свойства (size / isEmpty / уникальность / порядок)");

        java.util.Set<String> hashSetTags = new java.util.HashSet<>();
        java.util.Set<String> linkedHashSetTags = new java.util.LinkedHashSet<>();
        java.util.Set<String> treeSetTags = new java.util.TreeSet<>();

        System.out.println("   Создан HashSet:       " + hashSetTags + ", size=" + hashSetTags.size()
                + ", isEmpty=" + hashSetTags.isEmpty());
        System.out.println("   Создан LinkedHashSet: " + linkedHashSetTags + ", size=" + linkedHashSetTags.size()
                + ", isEmpty=" + linkedHashSetTags.isEmpty());
        System.out.println("   Создан TreeSet:       " + treeSetTags + ", size=" + treeSetTags.size()
                + ", isEmpty=" + treeSetTags.isEmpty());

        boolean isFirstAddedToHashSet = hashSetTags.add("первый");
        boolean isDuplicateAddedToHashSet = hashSetTags.add("первый");
        System.out.println("   HashSet.add(\"первый\") -> " + isFirstAddedToHashSet + ", состояние=" + hashSetTags);
        System.out.println("   HashSet.add(\"первый\") (дубликат) -> " + isDuplicateAddedToHashSet
                + ", состояние=" + hashSetTags + " (размер не изменился)");

        linkedHashSetTags.add("третий");
        linkedHashSetTags.add("первый");
        linkedHashSetTags.add("второй");

        hashSetTags.add("третий");
        hashSetTags.add("второй");

        treeSetTags.add("третий");
        treeSetTags.add("первый");
        treeSetTags.add("второй");

        System.out.println("   Порядок (может отличаться):");
        System.out.println("   - HashSet:       " + hashSetTags + " (порядок не гарантируется)");
        System.out.println("   - LinkedHashSet: " + linkedHashSetTags + " (порядок вставки)");
        System.out.println("   - TreeSet:       " + treeSetTags + " (отсортированный порядок)");

        System.out.println();
    }

    // =================================================================================================================
    // 2) Добавление: add / addAll (без индексов; признак изменения)
    // =================================================================================================================
    private static void demonstrateAddOperations(java.util.Set<String> targetSet, String setName) {
        System.out.println("2) Добавление элементов (" + setName + "): add / addAll");

        System.out.println("   Исходное множество: " + targetSet);

        boolean isSecondAdded = targetSet.add("второй");
        System.out.println("   add(\"второй\")               -> изменено=" + isSecondAdded + ", состояние=" + targetSet);

        boolean isFirstAdded = targetSet.add("первый");
        System.out.println("   add(\"первый\")               -> изменено=" + isFirstAdded + ", состояние=" + targetSet);

        boolean isDuplicateAdded = targetSet.add("первый");
        System.out.println("   add(\"первый\") (дубликат)    -> изменено=" + isDuplicateAdded + ", состояние=" + targetSet);

        java.util.List<String> batch = java.util.List.of("третий", "четвертый", "второй");
        boolean isBatchAdded = targetSet.addAll(batch);
        System.out.println("   addAll([\"третий\",\"четвертый\",\"второй\"]) -> изменено=" + isBatchAdded
                + ", состояние=" + targetSet + " (\"второй\" уже мог быть)");

        System.out.println();
    }

    // =================================================================================================================
    // 3) Поиск: contains / containsAll (equals + hashCode критичны для hash-based реализаций)
    // =================================================================================================================
    private static void demonstrateContainsOperations(java.util.Set<String> targetSet, String setName) {
        System.out.println("3) Поиск (" + setName + "): contains / containsAll");

        System.out.println("   Текущее множество: " + targetSet);

        boolean isContainsSecond = targetSet.contains("второй");
        boolean isContainsMissing = targetSet.contains("не-существует");

        System.out.println("   contains(\"второй\")       -> " + isContainsSecond);
        System.out.println("   contains(\"не-существует\") -> " + isContainsMissing);

        java.util.List<String> required = java.util.List.of("второй", "четвертый");
        boolean isContainsAll = targetSet.containsAll(required);
        System.out.println("   containsAll([\"второй\", \"четвертый\"]) -> " + isContainsAll);

        System.out.println("   Примечание: для HashSet/LinkedHashSet contains использует hashCode+equals.");
        System.out.println();
    }

    // =================================================================================================================
    // 4) Удаление: remove(value) / removeAll / retainAll / clear / removeIf
    // =================================================================================================================
    private static void demonstrateRemoveOperations(java.util.Set<String> targetSet, String setName) {
        System.out.println("4) Удаление (" + setName + "): remove / removeAll / retainAll / clear / removeIf");

        System.out.println("   До удаления: " + targetSet);

        boolean isRemovedSecond = targetSet.remove("второй");
        System.out.println("   remove(\"второй\")                      -> удалено=" + isRemovedSecond + ", состояние=" + targetSet);

        java.util.List<String> toRemove = java.util.List.of("не-существует", "третий");
        boolean isRemoveAllChanged = targetSet.removeAll(toRemove);
        System.out.println("   removeAll([\"не-существует\",\"третий\"]) -> изменено=" + isRemoveAllChanged
                + ", состояние=" + targetSet);

        java.util.List<String> toKeep = java.util.List.of("первый", "четвертый");
        boolean isRetainAllChanged = targetSet.retainAll(toKeep);
        System.out.println("   retainAll([\"первый\",\"четвертый\"])     -> изменено=" + isRetainAllChanged
                + ", состояние=" + targetSet);

        boolean isRemoveIfChanged = targetSet.removeIf(value -> value.startsWith("ч"));
        System.out.println("   removeIf(startsWith(\"ч\"))              -> изменено=" + isRemoveIfChanged
                + ", состояние=" + targetSet);

        targetSet.clear();
        System.out.println("   clear()                                 -> состояние=" + targetSet
                + ", size=" + targetSet.size() + ", isEmpty=" + targetSet.isEmpty());

        System.out.println();
    }

    // =================================================================================================================
    // 5) Итерация: for-each / Iterator (порядок зависит от реализации; безопасное удаление через iterator.remove)
    // =================================================================================================================
    private static void demonstrateIteration(java.util.Set<String> targetSet, String setName) {
        System.out.println("5) Итерация (" + setName + "): for-each / Iterator");

        targetSet.addAll(java.util.List.of("первый", "второй", "третий"));
        System.out.println("   Подготовлено множество: " + targetSet);

        System.out.println("   5.1) for-each:");
        for (String value : targetSet) {
            System.out.println("   - элемент=\"" + value + "\"");
        }

        System.out.println("   5.2) Iterator + безопасное удаление элемента во время обхода:");
        java.util.Iterator<String> iterator = targetSet.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println("   - элемент=\"" + element + "\"");
            if ("второй".equals(element)) {
                iterator.remove();
                System.out.println("     iterator.remove() выполнен для element=\"второй\"");
            }
        }
        System.out.println("   После iterator.remove: " + targetSet);

        System.out.println();
    }

    // =================================================================================================================
    // 6) Преобразование и сравнение: toArray / копирование / equals (для Set порядок не важен)
    // =================================================================================================================
    private static void demonstrateConversionsAndEquality() {
        System.out.println("6) Преобразование и сравнение: toArray / копирование / equals");

        java.util.Set<String> source = new java.util.LinkedHashSet<>(java.util.List.of(
                "первый", "второй", "третий"
        ));
        System.out.println("   Исходное множество: " + source);

        Object[] asObjectArray = source.toArray();
        System.out.println("   toArray()                    -> Object[], длина=" + asObjectArray.length
                + ", первый=\"" + asObjectArray[0] + "\" (порядок зависит от реализации)");

        String[] asStringArray = source.toArray(new String[0]);
        System.out.println("   toArray(new String[0])       -> String[], длина=" + asStringArray.length
                + ", последний=\"" + asStringArray[asStringArray.length - 1] + "\"");

        java.util.Set<String> copyAsHashSet = new java.util.HashSet<>(source);
        System.out.println("   Копирование: new HashSet<>(source) -> " + copyAsHashSet);

        boolean isEquals = source.equals(copyAsHashSet);
        System.out.println("   source.equals(copyAsHashSet)      -> " + isEquals + " (для Set порядок не влияет)");

        copyAsHashSet.add("четвертый");
        System.out.println("   copyAsHashSet.add(\"четвертый\")   -> source=" + source + ", copyAsHashSet=" + copyAsHashSet);

        System.out.println();
    }

    // =================================================================================================================
    // 7) null в Set: HashSet/LinkedHashSet допускают один null; TreeSet обычно не допускает
    // =================================================================================================================
    private static void demonstrateNullTheoryAndPractice() {
        System.out.println("7) null в Set: теория + практика");
        /*
            HashSet/LinkedHashSet допускают null (как элемент), но не более одного (Set остаётся уникальным).
            TreeSet при natural order не умеет сравнивать null с другими значениями -> NullPointerException.
         */

        java.util.Set<String> hashSet = new java.util.HashSet<>();
        boolean isNullAddedFirst = hashSet.add(null);
        boolean isNullAddedSecond = hashSet.add(null);

        System.out.println("   HashSet.add(null) (первый раз) -> изменено=" + isNullAddedFirst + ", состояние=" + hashSet);
        System.out.println("   HashSet.add(null) (второй раз) -> изменено=" + isNullAddedSecond
                + ", состояние=" + hashSet + " (дубликат null не добавился)");

        java.util.Set<String> treeSet = new java.util.TreeSet<>();
        try {
            treeSet.add(null);
            System.out.println("   Неожиданно: TreeSet.add(null) выполнен без ошибки");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException для TreeSet.add(null) при natural order");
        }

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
        System.out.println("   users.contains(lookup) -> " + containsUser
                + " (результат зависит от equals/hashCode; здесь равенство по userId)");

        boolean isAddedDuplicate = users.add(new DemoUser(10, "ЕщёОдноИмя"));
        System.out.println("   users.add(дубликат по userId) -> изменено=" + isAddedDuplicate + ", состояние=" + users);

        System.out.println();
    }

    // =================================================================================================================
    // 9) Set.of: immutable + запрет null + запрет дубликатов при создании
    // =================================================================================================================
    private static void demonstrateSetOfTheoryAndPractice() {
        System.out.println("9) Set.of: теория + практика");
        /*
            Set.of(...) создаёт неизменяемое (immutable) множество.
            Свойства:
            1) Любые структурные изменения (add/remove/clear и т.п.) -> UnsupportedOperationException.
            2) null-элементы не допускаются -> NullPointerException при создании.
            3) Дубликаты не допускаются -> IllegalArgumentException при создании.
         */

        java.util.Set<String> readOnlyRoles = java.util.Set.of("USER", "ADMIN");
        System.out.println("   Set.of(\"USER\", \"ADMIN\") -> " + readOnlyRoles);

        try {
            readOnlyRoles.add("AUDITOR");
            System.out.println("   Неожиданно: add выполнен без ошибки");
        } catch (UnsupportedOperationException exception) {
            System.out.println("   Ожидаемо: UnsupportedOperationException для readOnlyRoles.add(...)");
        }

        try {
            java.util.Set.of("первый", null, "второй");
            System.out.println("   Неожиданно: Set.of с null выполнен без ошибки");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException для Set.of(...) при наличии null-элемента");
        }

        try {
            java.util.Set.of("первый", "первый");
            System.out.println("   Неожиданно: Set.of с дубликатами выполнен без ошибки");
        } catch (IllegalArgumentException exception) {
            System.out.println("   Ожидаемо: IllegalArgumentException для Set.of(...) при наличии дубликатов");
        }

        System.out.println();
    }

    // =================================================================================================================
    // 10) TreeSet / NavigableSet: first/last/higher/lower/subSet/headSet/tailSet (диапазоны как view)
    // =================================================================================================================
    private static void demonstrateTreeSetNavigableTheoryAndPractice() {
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
    // 11) Выбор реализации: быстрый membership / сохранение порядка вставки / отсортированность
    // =================================================================================================================
    private static void demonstrateChoosingImplementation() {
        System.out.println("11) Выбор реализации Set: краткая памятка");
        /*
            Выбор зависит от требований:
            1) Нужны максимально быстрые contains/add/remove и порядок не важен -> HashSet.
            2) Нужны уникальные элементы и важно сохранить порядок вставки -> LinkedHashSet.
            3) Нужна сортировка и диапазоны/навигация -> TreeSet.
         */

        System.out.println("   HashSet       -> быстрый membership, порядок не гарантирован");
        System.out.println("   LinkedHashSet -> membership + порядок вставки");
        System.out.println("   TreeSet       -> membership + отсортированность + диапазоны/навигация");
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
        demonstrateSetOfTheoryAndPractice();
        demonstrateTreeSetNavigableTheoryAndPractice();
        demonstrateChoosingImplementation();
    }
}
