package org.study.bootcamp.interview_practice;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ УПРАВЛЯЮЩИХ КОНСТРУКЦИЙ В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * @author Sergey
 */
public class _04_ControlStructures {

/*
    === УСЛОВНЫЕ ОПЕРАТОРЫ ===
    Это конструкции, позволяющие выполнять различные блоки кода в зависимости от условий

    1.0. Одностороннее (базовое) ветвление | if
        if (условие1) {
            если условие1 верно -> действия;
        }

    1.1. Двустороннее ветвление | if + else
        if (условие1) {
            если условие1 верно -> действия;
        } else {
            иначе -> действия;
        }

    1.2. Многостороннее ветвление | if + else if + else
        if (условие1) {
            если условие1 верно -> действия;
        } else if (условие2) {
            иначе если условие2 верно -> действия;
        } else {
            иначе -> действия;
        }

    Схема работы:
    1) Вычислить логическое условие
    2) Если true → выполнить блок действий if
    3) Если false → выполнить блок действий else if
    4) Если все условия false → выполнить блок действий else

    Особенности:
    • Условие в круглых скобках должно быть логическим выражением (boolean)
    • Блок else if может повторяться множество раз
    • Блок else всегда последний и не имеет условия
    • Фигурные скобки {} можно опустить, если блок состоит из одного оператора
      , в коде:
      if (x > 0)
          System.out.println("Положительное число");
      else
          System.out.println("Отрицательное число или ноль");

 */

    public void compareNumbers(int firstNumber, int secondNumber) {
        System.out.println("Сценарий: выбираем один из трёх путей (меньше / больше / равно) по результату сравнения");
        System.out.printf("Входные значения: firstNumber=%d, secondNumber=%d%n", firstNumber, secondNumber);

        System.out.printf("Результат сравнения: ");
        if (firstNumber < secondNumber) {
            System.out.printf("%d < %d (первое число меньше)%n", firstNumber, secondNumber);
        } else if (firstNumber > secondNumber) {
            System.out.printf("%d > %d (первое число больше)%n", firstNumber, secondNumber);
        } else {
            System.out.printf("%d == %d (числа равны)%n", firstNumber, secondNumber);
        }

        System.out.println();
    }

/*
    2.0. Множественный выбор через break  | switch + case (break)
        switch (выражение) {
            case значение1:
                выражение и значение1 совпадают -> действия;
                break;
            case значение2:
                выражение и значение2 совпадают -> действия;
                break;
            default:
                выражение без совпадений -> действия по умолчанию;
                break;
        }

    2.1. Множественный выбор через стрелочный синтаксис | switch + case (->)
        switch (выражение) {
            case значение1 -> выражение и значение1 совпадают -> действия;
            case значение2 -> выражение и значение2 совпадают -> действия;
            default -> выражение без совпадений -> действия по умолчанию;
        }

    Схема работы:
    1) Вычислить выражение (строку, и так далее)
    2) Сравнить данный результат с case-константами
    3) При совпадении → выполнить блок действий case
    4) break завершает выполнение switch
       (иначе проход дальше — fall-through)
    5) Если совпадений нет → выполнить блок действий default
       (необязательный блок для обработки неожиданных значений)

    Особенности:
    • Работает по точному совпадению значения
    • Не требует логических выражений                    (boolean)
    • Поддерживает выражения со стрелкой (->) без break  (Java 12+)
    • Можно группировать case значения через запятую (,) (Java 14+)
*/

    public void printDayOfWeek(int dayNumber) {
        System.out.println("Сценарий: по номеру дня выбираем один вариант из фиксированного набора через switch");
        System.out.printf("Входное значение: dayNumber=%d%n", dayNumber);
        System.out.print("Результат: ");

        switch (dayNumber) {
            case 1:
                System.out.println("понедельник (рабочий день)");
                break;
            case 2:
                System.out.println("вторник (рабочий день)");
                break;
            case 3:
                System.out.println("среда (рабочий день)");
                break;
            case 4:
                System.out.println("четверг (рабочий день)");
                break;
            case 5:
                System.out.println("пятница (рабочий день)");
                break;
            case 6:
                System.out.println("суббота (выходной)");
                break;
            case 7:
                System.out.println("воскресенье (выходной)");
                break;
            default:
                System.out.println("неверный номер дня (ожидается 1..7)");
                break;
        }

        System.out.println();
    }

    public void printDayType(int dayNumber) {
        System.out.println("Сценарий: определяем тип дня (рабочий/выходной) через switch со стрелочным синтаксисом");
        System.out.printf("Входное значение: dayNumber=%d%n", dayNumber);
        System.out.print("Результат: ");

        switch (dayNumber) {
            case 1, 2, 3, 4, 5 -> System.out.println("рабочий день");
            case 6, 7 -> System.out.println("выходной день");
            default -> System.out.println("не определён (ожидается 1..7)");
        }

        System.out.println();
    }

/*
    === ЦИКЛЫ ===
    Это конструкции для многократного выполнения блока кода

    3.0. Цикл с предусловием | while
        while (условие) {
            пока условие верно -> действия;
        }

    Схема работы:
    1) Перед каждой итерацией вычислить условие
    2) Если true → выполнить действия
    3) После выполнения снова проверить условие
    4) Если false → завершить цикл

    Особенности:
    • Может не выполниться ни разу
    • Используется, когда количество повторов заранее неизвестно

    3.1. Цикл с постусловием | do-while
        do {
            действия -> выполнить и сверить;
        } while (условие);

    Схема работы:
    1) Выполнить действия один раз
    2) Проверить условие
    3) Если true → повторить цикл
    4) Если false → завершить цикл

    Особенности:
    • Гарантированно выполняется минимум один раз
    • Используется, когда нужно выполнить действие хотя бы один раз
 */

    public void countdown(int seconds) {
        System.out.println("Сценарий: while выполняется, пока условие (seconds > 0) истинно; может выполниться 0 раз");
        System.out.printf("Начальное значение: seconds=%d%n", seconds);
        System.out.println("Ход выполнения:");

        while (seconds > 0) {
            System.out.printf("%d...%n", seconds);
            seconds--;
        }
        System.out.println("Старт");

        System.out.println();
    }

    public void outputFromStartToLimit(int start, int limit) {
        System.out.println("Сценарий: do-while выполняет тело минимум один раз, затем проверяет (value <= limit)");
        System.out.printf("Входные значения: start=%d, limit=%d%n", start, limit);
        System.out.println("Ход выполнения:");

        System.out.printf("Последовательность от %d до %d:%n", start, limit);
        int value = start;
        do {
            System.out.printf("Текущее: %d, осталось до %d: %d%n",
                    value, limit, limit - value);
            value++;
        } while (value <= limit);

        System.out.println("Завершение: условие value <= limit стало false");

        System.out.println();
    }

/*
    4.0. Цикл со счётчиком | for
        for (инициализация; условие; обновление) {
            действия -> выполнить для каждого счётчика;
        }

    Схема работы:
    1) Выполнить инициализацию (один раз)
    2) Проверить условие
    3) Если true → выполнить действия
    4) Выполнить обновление (итерацию)
    4) Проверять условие до false → завершить цикл

    Особенности:
    • Используется, когда известно количество обновлений
    • Все три части (инициализация, условие, обновление) необязательны
      , в коде:
      for (;;) { ... }                  // бесконечный цикл (все три части опущены)
      for (int i = 0; ; i++) { ... }    // без условия выхода (требуется break внутри цикла)
      for (int i = 0; i < 10; ) { ... } // без обновления счётчика (требуется обновление в теле)
      for ( ; i < 10; i++) { ... }      // без инициализации (счётчик объявлен ранее)

    4.1. Улучшенный цикл for | for + each
        for (тип элемент : коллекция_или_массив) {
            действия с элементом -> обработать каждый элемент коллекции;
        }

    Особенности:
    • Нет доступа к индексу
    • Нельзя изменять структуру коллекции
    • Перебор коллекций и массивов упрощается
 */

    public void printEvenNumbersInRange(int start, int end) {
        System.out.println("Сценарий: for перебирает диапазон; if внутри цикла отбирает только чётные значения");
        System.out.printf("Входные значения: start=%d, end=%d%n", start, end);
        System.out.printf("Чётные числа в диапазоне [%d, %d]:%n", start, end);

        int evenCount = 0;
        for (int value = start; value <= end; value++) {
            if (value % 2 == 0) {
                System.out.printf("(%d) ", value);
                evenCount++;
            }
        }
        System.out.printf("%nВсего чётных чисел: %d%n", evenCount);

        System.out.println();
    }


    public void printArrayElements(String[] array) {
        System.out.println(
                "Сценарий: for-each обходит элементы массива без доступа к индексу;" +
                " элемент читается как копия ссылки"
        );
        System.out.printf("Размер массива: %d%n", array.length);

        System.out.printf("Элементы массива (%d шт.):%n", array.length);
        int index = 1;
        for (String element : array) {
            System.out.printf(
                    "[%d/%d] '%s' (длина элемента: %d)%n",
                    index++, array.length, element, element.length()
            );
        }

        System.out.println();
    }

/*
    === Служебные операторы циклов ===
    Это ключевые слова, которые изменяют стандартный ход выполнения циклов

    5.0. break — завершает выполнение текущего цикла

    5.1. continue — пропускает оставшуюся часть тела цикла и переходит к следующему обновлению

    5.2. Метки (labels) — позволяют управлять вложенными циклами
 */

    public void demonstrateBreak() {
        System.out.println("Сценарий: break немедленно завершает цикл, даже если условие цикла ещё истинно");
        System.out.println("break — выход при достижении 5:");
        System.out.print("Ход выполнения: ");

        for (int i = 1; i <= 10; i++) {
            if (i == 5) {
                System.out.println("\nДостигли i=5, выполняем break и выходим из цикла");
                break;
            }
            System.out.print(i + " ");
        }
        System.out.println("Завершение: управление перешло на строку после цикла");

        System.out.println();
    }

    public void demonstrateContinue() {
        System.out.println("Сценарий: continue пропускает текущую итерацию и переходит к следующей");
        System.out.println("continue — пропуск чётных чисел:");
        System.out.print("Ход выполнения (печатаем только нечётные): ");

        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue;
            }
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Завершение: цикл отработал все итерации, но часть из них была пропущена через continue");

        System.out.println();
    }

    public void demonstrateLabels() {
        System.out.println(
                "Сценарий: метка позволяет выйти сразу из внешнего цикла," +
                        " когда условие выполнено во внутреннем цикле"
        );
        System.out.println("Метки — выход из вложенных циклов:");

        outerLoop:
        for (int row = 1; row <= 3; row++) {
            System.out.println("Строка " + row);

            for (int col = 1; col <= 3; col++) {
                System.out.println("Колонка " + col);

                if (row == 2 && col == 2) {
                    System.out.println("break outerLoop → Выход из обоих циклов (внешний цикл завершён)");
                    break outerLoop;
                }
            }
        }
        System.out.println("Выполнение продолжилось после циклов");

        System.out.println();
    }

    public static void main(String[] args) {

        _04_ControlStructures ifDemoV1 = new _04_ControlStructures();
        System.out.println(" === if + else if + else ===");
        System.out.println("Демонстрация: одно условие → один из вариантов выполнения");
        ifDemoV1.compareNumbers(1, 2);
        ifDemoV1.compareNumbers(4, 3);
        ifDemoV1.compareNumbers(5, 5);

        _04_ControlStructures switchDemoV1 = new _04_ControlStructures();
        System.out.println("\n === switch + case (break) ===");
        System.out.println("Демонстрация: точное совпадение значения с case; break предотвращает fall-through");
        switchDemoV1.printDayOfWeek(1);
        switchDemoV1.printDayOfWeek(6);
        switchDemoV1.printDayOfWeek(9);

        _04_ControlStructures switchDemoV2 = new _04_ControlStructures();
        System.out.println("\n === switch + case (->) ===");
        System.out.println("Демонстрация: стрелочный switch (->) без break; несколько case значений через запятую");
        switchDemoV2.printDayType(1);
        switchDemoV2.printDayType(6);
        switchDemoV2.printDayType(9);

        _04_ControlStructures whileDemoV1 = new _04_ControlStructures();
        System.out.println("\n === while ===");
        System.out.println("Демонстрация: цикл с предусловием; тело выполняется только при истинном условии");
        whileDemoV1.countdown(3);

        _04_ControlStructures doWhileDemoV1 = new _04_ControlStructures();
        System.out.println("\n === do-while ===");
        System.out.println("Демонстрация: цикл с постусловием; тело выполняется минимум один раз");
        doWhileDemoV1.outputFromStartToLimit(3, 7);

        _04_ControlStructures forV1 = new _04_ControlStructures();
        System.out.println("\n === for ===");
        System.out.println("Демонстрация: перебор диапазона со счётчиком; фильтрация значений через if");
        forV1.printEvenNumbersInRange(1, 10);

        _04_ControlStructures forEachV1 = new _04_ControlStructures();
        System.out.println("\n === for + each ===");
        System.out.println("Демонстрация: перебор массива без индекса (for-each)");
        String[] fruits = {"Яблоко", "Банан", "Апельсин"};
        forEachV1.printArrayElements(fruits);

        _04_ControlStructures serviceOperatorsV1 = new _04_ControlStructures();
        System.out.println("\n === Служебные операторы циклов ===");
        System.out.println("Демонстрация: break/continue/labels меняют стандартный ход выполнения цикла");
        serviceOperatorsV1.demonstrateBreak();
        serviceOperatorsV1.demonstrateContinue();
        serviceOperatorsV1.demonstrateLabels();
    }
}
