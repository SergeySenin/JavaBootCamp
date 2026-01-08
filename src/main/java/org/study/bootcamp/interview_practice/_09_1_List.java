package org.study.bootcamp.interview_practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ КОЛЛЕКЦИИ List ИЗ JAVA И ЕЁ ОСОБЕННОСТЕЙ
 *
 * List — это «список»: элементы идут в заданном порядке, и у каждого есть номер (индекс). Примеры из “соцсети / банк”:
 * - ArrayList:
 *   - страница ленты (посты уже отсортированы, нужно быстро взять 0..19 и отрисовать);
 *   - список рекомендаций “для тебя” (порядок уже посчитан, дальше в основном читаем);
 *   - в банке: история операций за период (получили список — дальше листаем/показываем).
 * - LinkedList:
 *   - в реальных приложениях как List встречается реже; обычно берут, только если часто нужно вставлять/удалять элемент
 *     “в процессе обхода” (прогон списка задач через фильтры и выкидывание неподходящих без пересборки списка).
 *   - как очередь/дек (работа с концами) чаще используют не LinkedList, а ArrayDeque
 *     (в документации прямо сказано, что ArrayDeque обычно быстрее LinkedList как очередь).
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | ArrayList
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок элементов          | Сохраняется (порядок вставки); индексация 0..size-1
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Дубликаты                  | Разрешены
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Допускается (в т.ч. несколько null)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | Внутри — массив, который «растёт» при необходимости.
 * |                            | Если места не хватает: создаётся массив побольше и элементы копируются.
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Критерий                   | LinkedList
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Порядок элементов          | Сохраняется (порядок вставки); индексация 0..size-1
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Дубликаты                  | Разрешены
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Допустимость null          | Допускается (в т.ч. несколько null)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Потокобезопасность         | Нет (не синхронизирован)
 * | -------------------------- | --------------------------------------------------------------------------------------
 * | Внутренняя структура       | Внутри — «цепочка» элементов: каждый хранит ссылки на предыдущий и следующий.
 * |                            | Поэтому по индексу обычно нужно дойти шагами до нужного места.
 * | -------------------------- | --------------------------------------------------------------------------------------
 *
 * @author Sergey
 */
public class _09_1_List {

// O(1) — константно                    | O(n) — линейно
// ≈ “один шаг”; размер почти не влияет | ≈ “пройтись по всем”; рост прямо пропорционален

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | ArrayList
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | get(index)                 | O(1) (быстро) — сразу берём элемент по номеру.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | set(index, value)          | O(1) (быстро) — сразу заменяем элемент по номеру.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(value) в конец         | обычно O(1) (быстро)   — кладём в конец;
     *  |                            | иногда O(n) (медленно) — когда «кончилось место» и нужно расшириться.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(index, value)          | O(n) (медленно) — нужно сдвинуть элементы вправо, чтобы освободить место.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(index)              | O(n) (медленно) — нужно сдвинуть элементы влево, чтобы закрыть “дыру”.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(value)              | O(n) (медленно) — сначала ищем по списку, потом сдвигаем элементы.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | contains(value) / indexOf  | O(n) (медленно) — ищем по очереди, пока не найдём.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (быстро) — идём подряд по элементам (обычно один из лучших сценариев).
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Удобно, когда часто нужен доступ «по номеру» (например, показать 20-й пост).
     *  |                            | 2) Хорошо подходит для чтения/перебора (прошлись и обработали).
     *  |                            | 3) Обычно экономнее по памяти, чем LinkedList.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Вставки/удаления в середине часто дорогие (приходится двигать элементы).
     *  |                            | 2) Иногда бывают “рывки” при росте (расширение + копирование).
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /**
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Скорость основных операций | LinkedList
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | get(index)                 | O(n) (медленно) — нужно «дойти шагами» до нужного номера.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | set(index, value)          | O(n) (медленно) — сначала дойти до позиции, потом заменить.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(value) в конец         | O(1) (быстро) — добавить элемент в хвост.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | add(index, value)          | O(n) (медленно) — нужно дойти до позиции; сама “вставка” уже простая.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(index)              | O(n) (медленно) — нужно дойти до позиции; удаление на месте простое.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | remove(value)              | O(n) (медленно) — нужно найти элемент обходом.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | contains(value) / indexOf  | O(n) (медленно) — поиск обходом.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Итерация for-each          | O(n) (средне/медленнее ArrayList) — идём по “цепочке” элементов.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Преимущества               | 1) Удобнее, когда работа идёт «на концах» (как очередь: добавлять/забирать).
     *  |                            | 2) Вставки/удаления рядом с текущим местом обхода могут быть удобными.
     *  | -------------------------- | ---------------------------------------------------------------------------------
     *  | Недостатки                 | 1) Почти всё “по индексу” — медленно (сначала нужно дойти).
     *  |                            | 2) Обычно тяжелее по памяти (на каждый элемент больше служебных данных).
     *  | -------------------------- | ---------------------------------------------------------------------------------
     */

    /*
        1) ArrayList (динамический массив)
        Создание пустого списка
        java.util.List<Тип> имяСписка = new java.util.ArrayList<>();

        Создание с ожидаемой вместимостью (актуально, если заранее известен примерный размер)
        java.util.List<Тип> имяСписка = new java.util.ArrayList<>(начальнаяВместимость);

        Создание из другой коллекции (копирование элементов)
        java.util.List<Тип> имяСписка = new java.util.ArrayList<>(java.util.List.of(элемент1, элемент2, ...));

        2) LinkedList (двусвязный список)
        Создание пустого списка
        java.util.List<Тип> имяСписка = new java.util.LinkedList<>();

        Создание из другой коллекции (копирование элементов)
        java.util.List<Тип> имяСписка = new java.util.LinkedList<>(java.util.List.of(элемент1, элемент2, ...));
     */

    // =================================================================================================================
    // 1) Создание и базовые свойства: size / isEmpty / getFirst / getLast (с оговоркой по интерфейсу)
    // =================================================================================================================
    private static void demonstrateCreationAndBasicProperties() {
        System.out.println("1) Создание List и базовые свойства (size / isEmpty)");

        java.util.List<String> arrayListTags = new java.util.ArrayList<>();
        java.util.List<String> linkedListTags = new java.util.LinkedList<>();

        System.out.println("   Создан ArrayList:  "
                + arrayListTags  + ", size=" + arrayListTags.size() + ", isEmpty=" + arrayListTags.isEmpty());
        System.out.println("   Создан LinkedList: "
                + linkedListTags + ", size=" + linkedListTags.size() + ", isEmpty=" + linkedListTags.isEmpty());

        arrayListTags.add("первый");
        arrayListTags.add("второй");
        linkedListTags.add("первый");
        linkedListTags.add("второй");

        System.out.println("   После add двух элементов:");
        System.out.println("   - ArrayList:  " + arrayListTags  + ", size=" + arrayListTags.size());
        System.out.println("   - LinkedList: " + linkedListTags + ", size=" + linkedListTags.size());

        System.out.println("   Примечание: методы getFirst()/getLast() существуют у Deque/LinkedList, но не у List.");
        System.out.println("   В демонстрациях ниже упор на контракт List + методы Collection.");
        System.out.println();
    }

    // =================================================================================================================
    // 2) Добавление: add / add(index, value) / addAll / addAll(index, collection)
    // =================================================================================================================
    private static void demonstrateAddOperations(java.util.List<String> targetList, String listName) {
        System.out.println("2) Добавление элементов (" + listName + "): add / add(index) / addAll");

        System.out.println("   Исходный список: " + targetList);

        targetList.add("второй");
        System.out.println("   add(\"второй\")               -> " + targetList);

        targetList.add(0, "первый");
        System.out.println("   add(index=0, \"первый\")      -> " + targetList + " (элементы справа сдвинулись)");

        java.util.List<String> prefix = java.util.List.of("третий");
        boolean isPrefixAdded = targetList.addAll(2, prefix);
        System.out.println("   addAll(index=2, [\"третий\"]) -> " + targetList + ", изменено=" + isPrefixAdded);

        java.util.List<String> batch = java.util.List.of("четвертый");
        boolean isBatchAdded = targetList.addAll(batch);
        System.out.println("   addAll([\"четвертый\"])       -> " + targetList + ", изменено=" + isBatchAdded);

        System.out.println();
    }

    // =================================================================================================================
    // 3) Чтение/обновление: get / set
    // =================================================================================================================
    private static void demonstrateGetAndSet(java.util.List<String> targetList, String listName) {
        System.out.println("3) Доступ по индексу (" + listName + "): get / set");

        System.out.println("   Текущий список: " + targetList);

        String firstElement = targetList.get(0);
        System.out.println("   get(0)                     -> \"" + firstElement + "\"");

        String previousValue = targetList.set(0, "первый-обновлено");
        System.out.println("   set(0, \"первый-обновлено\") -> предыдущееЗначение=\"" + previousValue + "\"");
        System.out.println("   После set: " + targetList);

        try {
            targetList.get(10_000);
            System.out.println("   Неожиданно: get(10000) выполнен без ошибки");
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("   Ожидаемо: IndexOutOfBoundsException для get(10000): " + exception.getMessage());
        }

        System.out.println();
    }

    // =================================================================================================================
    // 4) Поиск: contains / indexOf / lastIndexOf / containsAll
    // =================================================================================================================
    private static void demonstrateSearchOperations(java.util.List<String> targetList, String listName) {
        System.out.println("4) Поиск (" + listName + "): contains / indexOf / lastIndexOf / containsAll");

        targetList.set(2, "второй"); // создадим дубликат без увеличения размера списка
        System.out.println("   Подготовлен список с дубликатом \"второй\": " + targetList);

        boolean isContainsSecond = targetList.contains("второй");
        int firstSecondIndex = targetList.indexOf("второй");
        int lastSecondIndex = targetList.lastIndexOf("второй");

        System.out.println("   contains(\"второй\")                   -> " + isContainsSecond);
        System.out.println("   indexOf(\"второй\")                    -> " + firstSecondIndex + " (первое    вх-ние)");
        System.out.println("   lastIndexOf(\"второй\")                -> " + lastSecondIndex  + " (последнее вх-ние)");

        java.util.List<String> required = java.util.List.of("второй", "четвертый");
        boolean isContainsAll = targetList.containsAll(required);
        System.out.println("   containsAll([\"второй\", \"четвертый\"]) -> " + isContainsAll);

        System.out.println("   Примечание: contains/indexOf используют equals()." +
                " Для сложных объектов корректность equals критична.");
        System.out.println();
    }

    // =================================================================================================================
    // 5) Удаление: remove(index) / remove(value) / removeAll / retainAll / clear / removeIf
    // =================================================================================================================
    private static void demonstrateRemoveOperations(java.util.List<String> targetList, String listName) {
        System.out.println("5) Удаление (" + listName + "): remove / removeAll / retainAll / clear / removeIf");

        System.out.println("   До удаления: " + targetList);

        String removedByIndex = targetList.remove(0);
        System.out.println(
                "   remove(index=0)                    -> удалено=\"" + removedByIndex + "\", список=" + targetList
        );

        boolean isRemovedValue = targetList.remove("второй");
        System.out.println(
                "   remove(\"второй\")                   -> удалено=" + isRemovedValue + ", список=" + targetList
        );

        java.util.List<String> toRemove = java.util.List.of("не-существует");
        boolean isRemoveAllChanged = targetList.removeAll(toRemove);
        System.out.println(
                "   removeAll([\"не-существует\"])       -> изменено=" + isRemoveAllChanged + ", список=" + targetList
        );

        java.util.List<String> toKeep = java.util.List.of("второй", "четвертый");
        boolean isRetainAllChanged = targetList.retainAll(toKeep);
        System.out.println(
                "   retainAll([\"второй\", \"четвертый\"]) -> изменено=" + isRetainAllChanged + ", список=" + targetList
        );

        boolean isRemoveIfChanged = targetList.removeIf(value -> value.startsWith("ч"));
        System.out.println(
                "   removeIf(startsWith(\"ч\"))          -> изменено=" + isRemoveIfChanged + ", список=" + targetList
        );

        targetList.clear();
        System.out.println(
                "   clear()                            -> список="
                        + targetList + ", size=" + targetList.size() + ", isEmpty=" + targetList.isEmpty()
        );

        System.out.println();
    }

    // =================================================================================================================
    // 6) Итерация: for-each / Iterator / ListIterator
    // =================================================================================================================
    private static void demonstrateIteration(java.util.List<String> targetList, String listName) {
        System.out.println("6) Итерация (" + listName + "): for-each / Iterator / ListIterator");

        targetList.addAll(java.util.List.of("первый", "второй", "третий"));
        System.out.println("   Подготовлен список: " + targetList);

        System.out.println("   6.1) for-each:");
        for (String value : targetList) {
            System.out.println("   - элемент=\"" + value + "\"");
        }

        System.out.println("   6.2) Iterator (вручную):");
        java.util.Iterator<String> iterator = targetList.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println("   - элемент=\"" + element + "\"");
        }

        System.out.println("   6.3) ListIterator (вперёд/назад + set/add/remove в контексте итерации):");
        java.util.ListIterator<String> listIterator = targetList.listIterator();
        while (listIterator.hasNext()) {
            int nextIndex = listIterator.nextIndex();
            String element = listIterator.next();
            System.out.println("   - nextIndex=" + nextIndex + ", элемент=\"" + element + "\"");

            if ("второй".equals(element)) {
                listIterator.set("второй-обновлено");
                System.out.println("     set(\"второй-обновлено\") выполнен, index=" + (listIterator.previousIndex()));
            }
            if ("третий".equals(element)) {
                listIterator.add("четвертый");
                System.out.println("     add(\"четвертый\") выполнен, index=" + (listIterator.previousIndex()));
            }
        }

        System.out.println("   После изменений через ListIterator: " + targetList);

        System.out.println("   Обход назад:");
        while (listIterator.hasPrevious()) {
            int previousIndex = listIterator.previousIndex();
            String element = listIterator.previous();
            System.out.println("   - previousIndex=" + previousIndex + ", элемент=\"" + element + "\"");
        }

        System.out.println();
    }

    // =================================================================================================================
    // 7) Преобразование: toArray / copy / equals
    // =================================================================================================================
    private static void demonstrateConversionsAndEquality() {
        System.out.println("7) Преобразование и сравнение: toArray / копирование / equals");

        java.util.List<String> source = new java.util.ArrayList<>(java.util.List.of(
                "первый", "второй", "третий"
        ));
        System.out.println("   Исходный список: " + source);

        Object[] asObjectArray = source.toArray();
        System.out.println("   toArray()                            -> Object[], длина="
                + asObjectArray.length + ", первый=\"" + asObjectArray[0] + "\"");

        String[] asStringArray = source.toArray(new String[0]);
        System.out.println("   toArray(new String[0])               -> String[], длина="
                + asStringArray.length + ", последний=\"" + asStringArray[asStringArray.length - 1] + "\"");

        java.util.List<String> copy = new java.util.ArrayList<>(source);
        System.out.println("   Копирование: new ArrayList<>(source) -> " + copy);

        System.out.println("   source.equals(copy)                  -> " + source.equals(copy)
                + " (для List важен порядок элементов)");

        copy.add("четвертый");
        System.out.println("   copy.add(\"четвертый\")                -> source=" + source + ", copy=" + copy);

        System.out.println();
    }

    // =================================================================================================================
    // 8) Особые методы и важная теория: subList / sort / contains / List.of
    // =================================================================================================================
    private static void demonstrateSubListTheoryAndPractice() {
        System.out.println("8) subList: теория + практика");
        /*
            subList(fromIndex, toIndex) возвращает представление (view)
            на часть исходного списка, а не независимую копию. Следствия:
            1) Изменения через subList отражаются в исходном списке.
            2) Структурные изменения исходного списка (add/remove) могут инвалидировать subList и
            привести к ConcurrentModificationException при дальнейших операциях с subList (и наоборот).
            3) Границы: fromIndex включительно, toIndex исключительно.
         */

        java.util.List<String> original = new java.util.ArrayList<>(java.util.List.of(
                "первый", "второй", "третий", "четвертый")
        );
        System.out.println("   Исходный список: " + original);

        java.util.List<String> view = original.subList(1, 4);
        System.out.println("   subList(1, 4) представление -> " + view + " (ожидаемо: [второй, третий, четвертый])");

        view.set(0, "второй-обновлено");
        System.out.println("   view.set(0, \"второй-обновлено\") выполнен");
        System.out.println("   - представление: "  + view);
        System.out.println("   - исходныйСписок: " + original + " (изменение видно в исходном списке)");

        view.remove(1);
        System.out.println("   view.remove(index=1) выполнен");
        System.out.println("   - представление: "  + view);
        System.out.println("   - исходныйСписок: " + original + " (структурное удаление также отразилось)");

        try {
            original.add("пятый");
            System.out.println("   original.add(\"пятый\") выполнен (структурная модификация исходного списка)");
            System.out.println("   Попытка использовать представление после модификации исходного списка:");

            view.add("шестой");
            System.out.println("   Неожиданно: view.add(\"шестой\") выполнен без ошибки");
        } catch (java.util.ConcurrentModificationException exception) {
            System.out.println("   Ожидаемо: ConcurrentModificationException при работе" +
                    " с представлением после структурной модификации исходного списка");
        }

        System.out.println();
        System.out.println("   Практика: при независимой копии диапазона нужен новый ArrayList<>(subList(...)):");
        java.util.List<String> safeCopy = new java.util.ArrayList<>(original.subList(0, Math.min(3, original.size())));
        System.out.println("   new ArrayList<>(original.subList(...)) -> " + safeCopy);

        System.out.println();
    }

    private static void demonstrateSortTheoryAndPractice() {
        System.out.println("9) sort: теория + практика");
        /*
            sort(Comparator) сортирует список “на месте”.
            Варианты:
            - list.sort(Comparator.naturalOrder()) — естественный порядок (если элементы Comparable).
            - list.sort(Comparator.comparing(...)) — порядок по ключу.
            Замечания:
            - Это не “создание нового списка”, а изменение текущего.
            - Если в данных есть null, Comparator должен уметь с ним работать (иначе возможно NPE).
         */

        java.util.List<Integer> scores = new java.util.ArrayList<>(java.util.List.of(40, 10, 30, 20));
        System.out.println("   До sort scores: " + scores);

        scores.sort(java.util.Comparator.naturalOrder());
        System.out.println("   scores.sort(naturalOrder)         -> " + scores);

        java.util.List<DemoAccount> accounts = new java.util.ArrayList<>();
        accounts.add(new DemoAccount("u-3", 1200));
        accounts.add(new DemoAccount("u-1", 5000));
        accounts.add(new DemoAccount("u-2", 1500));

        System.out.println("   До sort accounts: " + accounts);
        accounts.sort(java.util.Comparator.comparing(DemoAccount::balance));
        System.out.println("   accounts.sort(comparing(balance)) -> " + accounts);

        System.out.println();
    }

    private static void demonstrateContainsTheoryAndPractice() {
        System.out.println("10) contains: теория + практика");
        /*
            contains(value) отвечает на вопрос “есть ли элемент”.
            Реализация:
            - Для List типично O(n): перебор по порядку до совпадения.
            Критично:
            - Сравнение делается через equals().
            - Для “своих” классов equals/hashCode должны описывать бизнес-равенство (по смыслу),
            иначе contains будет давать неожиданные результаты.
         */

        java.util.List<DemoUser> users = new java.util.ArrayList<>();
        users.add(new DemoUser(10, "Сергей"));
        users.add(new DemoUser(20, "Иван"));

        System.out.println("   Список пользователей: " + users);

        DemoUser lookupSameIdDifferentName = new DemoUser(10, "ДругоеИмя");
        boolean containsUser = users.contains(lookupSameIdDifferentName);

        System.out.println("   Поиск: " + lookupSameIdDifferentName);
        System.out.println("   users.contains(lookup) -> " + containsUser
                + " (результат зависит от equals; здесь equals по userId)");

        System.out.println();
    }

    private static void demonstrateListOfTheoryAndPractice() {
        System.out.println("11) List.of: теория + практика");
        /*
            List.of(...) создаёт неизменяемый (immutable) список.
            Свойства:
            1) Любые структурные изменения (add/remove/clear/set и т.п.) -> UnsupportedOperationException.
            2) null-элементы не допускаются -> NullPointerException при создании.
            Когда использовать:
            - Константные наборы данных “только на чтение” (конфиги, справочники, тестовые данные).
         */

        java.util.List<String> readOnlyRoles = java.util.List.of("USER", "ADMIN");
        System.out.println("   List.of(\"USER\", \"ADMIN\") -> " + readOnlyRoles);

        try {
            readOnlyRoles.add("AUDITOR");
            System.out.println("   Неожиданно: add выполнен без ошибки");
        } catch (UnsupportedOperationException exception) {
            System.out.println("   Ожидаемо: UnsupportedOperationException для readOnlyRoles.add(...)");
        }

        try {
            java.util.List.of("первый", null, "второй");
            System.out.println("   Неожиданно: List.of с null выполнен без ошибки");
        } catch (NullPointerException exception) {
            System.out.println("   Ожидаемо: NullPointerException для List.of(...) при наличии null-элемента");
        }

        System.out.println();
    }

    private static final class DemoAccount {
        private final String accountId;
        private final int balance;

        private DemoAccount(String accountId, int balance) {
            if (accountId == null || accountId.isBlank()) {
                throw new IllegalArgumentException("accountId обязателен и не должен быть пустым");
            }
            this.accountId = accountId.trim();
            this.balance = balance;
        }

        public String accountId() {
            return accountId;
        }

        public int balance() {
            return balance;
        }

        @Override
        public String toString() {
            return "DemoAccount{accountId=\"" + accountId + "\", balance=" + balance + "}";
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
        java.util.List<String> arrayListDemo = new java.util.ArrayList<>();
        demonstrateAddOperations(arrayListDemo, "ArrayList");
        demonstrateGetAndSet(arrayListDemo, "ArrayList");
        demonstrateSearchOperations(arrayListDemo, "ArrayList");
        demonstrateRemoveOperations(arrayListDemo, "ArrayList");
        demonstrateIteration(arrayListDemo, "ArrayList");
        java.util.List<String> linkedListDemo = new java.util.LinkedList<>();
        demonstrateAddOperations(linkedListDemo, "LinkedList");
        demonstrateGetAndSet(linkedListDemo, "LinkedList");
        demonstrateSearchOperations(linkedListDemo, "LinkedList");
        demonstrateRemoveOperations(linkedListDemo, "LinkedList");
        demonstrateIteration(linkedListDemo, "LinkedList");
        demonstrateConversionsAndEquality();
        demonstrateSubListTheoryAndPractice();
        demonstrateSortTheoryAndPractice();
        demonstrateContainsTheoryAndPractice();
        demonstrateListOfTheoryAndPractice();
    }
}
