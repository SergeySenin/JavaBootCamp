package org.study.bootcamp.interview_practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ МАССИВОВ В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * @author Sergey
 */
public class _08_Arrays {

/*
    === МАССИВЫ ===
    Это структура данных для хранения фиксированного количества элементов одного типа

    Короткая памятка:
    - индексация массива всегда начинается с нуля
    - пустой массив примитивов заполняется нулями, а массив ссылочных типов — null
    - размер массива фиксирован: его нельзя "растянуть" после создания
    - чтобы получить другой размер, создайте новый массив или используйте коллекции (например, ArrayList)
 */

/*
    Объявление с указанием размера -> создание массива заданного размера
    тип[] имяМассива = new тип[размер];

    Литеральная инициализация -> короткая запись, размер вычисляется автоматически
    тип[] имяМассива = {элемент1, элемент2, ...};

    Инициализация через new -> явное создание с готовыми значениями
    тип[] имяМассива = new тип[]{эл1, эл2, ...};

    Многоуровневые массивы
    тип[][] имяМассива = new тип[строк][столбцов]; // Двумерный массив (матрица)
    тип[][] имяМассива = {{ряд1}, {ряд2}};         // Инициализация вложенных массивов
 */

    private static void declareAndInitializeArrays() {
        System.out.println("1) Объявление и инициализация");

        int[] numbersWithSize = new int[4];
        System.out.println("new int[4] → " + Arrays.toString(numbersWithSize)
                + " (примитивы по умолчанию заполняются нулями)");

        String[] namesInline = {"Ivan", "Maria", "Chen"};
        System.out.println("{\"Ivan\", \"Maria\", \"Chen\"} → " + Arrays.toString(namesInline)
                + " (литеральная инициализация: коротко и читаемо)");

        double[] pricesWithNew = new double[]{9.99, 19.99, 29.99};
        System.out.println("new double[]{...} → " + Arrays.toString(pricesWithNew)
                + " (явное создание с готовыми значениями)");

        System.out.println("Ключевое: размер массива фиксирован; для динамического размера используйте ArrayList");

        System.out.println();
    }

    private static void accessAndMutateElements() {
        System.out.println("2) Доступ к элементам и изменение");

        int[] dailyScores = {10, 20, 30};
        System.out.println("Исходный массив dailyScores → " + Arrays.toString(dailyScores));

        System.out.println("Чтение по индексу: dailyScores[0] → " + dailyScores[0]);

        dailyScores[1] = 50;
        System.out.println("Запись по индексу: dailyScores[1] = 50 → " + Arrays.toString(dailyScores));

        System.out.println("Доступ за пределы массива приводит к исключению:");
        try {
            int invalid = dailyScores[5];
            System.out.println(invalid);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("dailyScores[5] → ArrayIndexOutOfBoundsException (индекс вне диапазона 0.."
                    + (dailyScores.length - 1) + ")");
        }

        System.out.println();
    }

    private static void iterateArraysSafely() {
        System.out.println("3) Перебор массива");

        String[] frameworks = {"Spring", "Hibernate", "Micronaut"};
        System.out.println("Массив frameworks → " + Arrays.toString(frameworks));

        System.out.println("3.1) for (есть индекс):");
        for (int index = 0; index < frameworks.length; index++) {
            System.out.println("frameworks[" + index + "] = " + frameworks[index]);
        }

        System.out.println("3.2) for-each (без индекса):");
        System.out.println("Важно: переменная цикла получает копию значения (для объектов — копию ссылки)");
        for (String framework : frameworks) {
            System.out.println("framework = " + framework);
            framework = framework + "?"; // не меняется исходный массив, так как меняется локальная переменная
        }
        System.out.println("После for-each исходный массив не изменился → " + Arrays.toString(frameworks));

        int[] ratings = {1, 2, 3};
        System.out.println("3.3) for-each по примитивам тоже даёт копии:");
        System.out.println("ratings до цикла → " + Arrays.toString(ratings));
        for (int rating : ratings) {
            rating += 10; // меняется только локальная переменная
        }
        System.out.println("ratings после цикла → " + Arrays.toString(ratings) + " (массив не изменился)");

        System.out.println();
    }

    private static void useArraysUtilityMethods() {
        System.out.println("4) Полезные методы java.util.Arrays");

        int[] metrics = {5, 2, 9, 1, 5};
        System.out.println("Исходный metrics → " + Arrays.toString(metrics));

        Arrays.sort(metrics);
        System.out.println("Arrays.sort(metrics) → " + Arrays.toString(metrics)
                + " (сортировка по возрастанию)");

        int[] filled = new int[3];
        Arrays.fill(filled, 7);
        System.out.println("Arrays.fill(new int[3], 7) → " + Arrays.toString(filled)
                + " (одно значение для всех элементов)");

        int[] copiedPrefix = Arrays.copyOf(metrics, 3);
        System.out.println("Arrays.copyOf(metrics, 3) → " + Arrays.toString(copiedPrefix)
                + " (новый массив длиной 3)");

        System.out.println("binarySearch требует отсортированный массив:");
        System.out.println("metrics (отсортирован) → " + Arrays.toString(metrics));
        int foundIndex = Arrays.binarySearch(metrics, 5);
        System.out.println("Arrays.binarySearch(metrics, 5) → " + foundIndex
                + " (индекс одного из найденных вхождений)");

        int[] unsorted = {3, 1, 2};
        System.out.println("Пример нарушения контракта (массив не отсортирован) → " + Arrays.toString(unsorted));
        int incorrectResult = Arrays.binarySearch(unsorted, 3);
        System.out.println("Arrays.binarySearch(unsorted, 3) → " + incorrectResult
                + " (результат нельзя интерпретировать, т.к. массив не отсортирован)");

        int[] sameMetrics = {1, 2, 5, 5, 9};
        System.out.println("Arrays.equals(metrics, sameMetrics) → " + Arrays.equals(metrics, sameMetrics)
                + " (сравнение по значениям элементов)");

        int[] sameReference = metrics;
        System.out.println("metrics == sameReference → " + (metrics == sameReference)
                + " (сравнение ссылок: true, потому что это один и тот же объект)");

        int[] newButEqual = Arrays.copyOf(metrics, metrics.length);
        System.out.println("metrics == newButEqual → " + (metrics == newButEqual)
                + " (ссылки разные)");
        System.out.println("Arrays.equals(metrics, newButEqual) → " + Arrays.equals(metrics, newButEqual)
                + " (значения одинаковые)");

        String[] textValues = {"zeta", "alpha", "beta"};
        System.out.println("Строки до sort → " + Arrays.toString(textValues));
        Arrays.sort(textValues);
        System.out.println("Arrays.sort(String[]) → " + Arrays.toString(textValues)
                + " (String реализует Comparable, поэтому сортировка работает)");

        System.out.println();
    }

    private static void workWithMultidimensionalArrays() {
        System.out.println("5) Двумерные массивы (матрица)");

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("matrix → " + Arrays.deepToString(matrix)
                + " (deepToString показывает вложенность)");

        System.out.println("Доступ по двум индексам: matrix[1][2] → " + matrix[1][2]
                + " (строка 1, столбец 2; индексация с 0)");

        System.out.println("Перебор по строкам и столбцам:");
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                System.out.print(matrix[row][column] + " ");
            }
            System.out.println();
        }

        int[][] emptyGrid = new int[2][3];
        System.out.println("new int[2][3] → " + Arrays.deepToString(emptyGrid) + " (примитивы заполнены нулями)");

        emptyGrid[0][0] = 42;
        System.out.println("После emptyGrid[0][0] = 42 → " + Arrays.deepToString(emptyGrid));

        System.out.println();
    }

    private static void handleJaggedArrays() {
        System.out.println("6) Неровные (jagged) массивы");

        int[][] jagged = {
                {1, 2, 3},
                {4, 5},
                {6, 7, 8, 9}
        };

        System.out.println("jagged → " + Arrays.deepToString(jagged)
                + " (строки могут иметь разную длину)");

        System.out.println("Доступ: jagged[1][1] → " + jagged[1][1]
                + " (во второй строке только 2 элемента)");

        System.out.println("Перебор учитывает длину каждой строки:");
        for (int row = 0; row < jagged.length; row++) {
            for (int column = 0; column < jagged[row].length; column++) {
                System.out.print(jagged[row][column] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    private static void compareArrayAndArrayList() {
        System.out.println("7) Когда массив, а когда ArrayList");

        String[] stableUsers = new String[3];
        System.out.println(
                "Массив (фиксированный размер, элементы по умолчанию null) → "
                + Arrays.toString(stableUsers)
        );
        stableUsers[0] = "Alice";
        stableUsers[1] = "Bob";
        System.out.println("После заполнения → " + Arrays.toString(stableUsers));

        List<String> dynamicUsers = new ArrayList<>();
        System.out.println("ArrayList (динамический размер) старт → " + dynamicUsers);

        dynamicUsers.add("Alice");
        dynamicUsers.add("Bob");
        System.out.println("После add → " + dynamicUsers);

        dynamicUsers.remove("Alice");
        System.out.println("После remove(\"Alice\") → " + dynamicUsers);

        System.out.println("Вывод:");
        System.out.println("- массив: быстрее и компактнее, но размер фиксирован");
        System.out.println("- ArrayList: удобный API и изменяемый размер; вставки/удаления в середине могут быть O(n)");

        System.out.println();
    }

    private static void highlightArrayPitfalls() {
        System.out.println("8) Подводные камни");

        System.out.println("8.1) length — поле, а не метод: array.length, а не array.length()");

        int[] original = {1, 2, 3};
        int[] alias = original;
        System.out.println("original → " + Arrays.toString(original));
        System.out.println("alias = original (копируется ссылка, а не элементы)");

        alias[0] = 99;
        System.out.println("После alias[0] = 99:");
        System.out.println("original → " + Arrays.toString(original) + " (изменился, потому что ссылка одна)");
        System.out.println("alias    → " + Arrays.toString(alias));

        int[] safeCopyViaCopyOf = Arrays.copyOf(original, original.length);
        safeCopyViaCopyOf[1] = 55;
        System.out.println("Arrays.copyOf делает независимую копию:");
        System.out.println("safeCopyViaCopyOf → " + Arrays.toString(safeCopyViaCopyOf));
        System.out.println("original          → " + Arrays.toString(original) + " (не изменился)");

        int[] safeCopyViaArrayCopy = new int[original.length];
        System.arraycopy(original, 0, safeCopyViaArrayCopy, 0, original.length);
        safeCopyViaArrayCopy[2] = 77;
        System.out.println("System.arraycopy тоже делает независимую копию:");
        System.out.println("safeCopyViaArrayCopy → " + Arrays.toString(safeCopyViaArrayCopy));
        System.out.println("original             → " + Arrays.toString(original) + " (не изменился)");

        System.out.println("8.2) Для многомерных массивов сравнение:");
        int[][] a = {{1, 2}, {3, 4}};
        int[][] b = {{1, 2}, {3, 4}};
        System.out.println(
                "Arrays.equals(a, b) → " + Arrays.equals(a, b)
                + " (false: сравниваются ссылки вложенных массивов)"
        );
        System.out.println("Arrays.deepEquals(a, b) → " + Arrays.deepEquals(a, b) + " (true: сравнение по значениям)");

        System.out.println();
    }

    public static void main(String[] args) {
        declareAndInitializeArrays();
        accessAndMutateElements();
        iterateArraysSafely();
        useArraysUtilityMethods();
        workWithMultidimensionalArrays();
        handleJaggedArrays();
        compareArrayAndArrayList();
        highlightArrayPitfalls();
    }
}
