package org.study.bootcamp.interview_practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ МАССИВОВ В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * @author Sergey
 */
public class _05_Arrays {

    /*
     * Короткая памятка:
     * - индексация массива всегда начинается с нуля
     * - пустой массив примитивов заполняется нулями, а массив ссылочных типов — null
     * - его нельзя создать без указания точного размера и «растянуть» после создания
     * - чтобы получить другой размер, создайте новый массив или используйте коллекции (например, ArrayList)
     */

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

    private static void declareAndInitializeArrays() {
        System.out.println("1) Объявление и инициализация");
        int[] numbersWithSize = new int[4];
        System.out.println("int[4] по умолчанию → " + Arrays.toString(numbersWithSize) +
                " — примитивы заполняются нулями");

        String[] namesInline = {"Ivan", "Maria", "Chen"};
        System.out.println("Литеральная инициализация → " + Arrays.toString(namesInline) +
                " — читается проще и короче");

        double[] pricesWithNew = new double[]{9.99, 19.99, 29.99};
        System.out.println("Через new c готовыми значениями → " + Arrays.toString(pricesWithNew));
        System.out.println("Размер массива фиксирован — при необходимости роста переходите на ArrayList");
        System.out.println();
    }

    private static void accessAndMutateElements() {
        System.out.println("2) Доступ и изменение элементов");
        int[] dailyScores = {10, 20, 30};
        System.out.println("Первый элемент dailyScores[0] = " + dailyScores[0]);

        dailyScores[1] = 50; // перезапись по индексу
        System.out.println("После присваивания dailyScores[1] = 50 → " + Arrays.toString(dailyScores));

        try {
            int invalid = dailyScores[5];
            System.out.println(invalid); // не выполнится
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Обращение за пределы бросает ArrayIndexOutOfBoundsException — проверяйте индексы");
        }
        System.out.println();
    }

    private static void iterateArraysSafely() {
        System.out.println("3) Перебор массива разными способами");
        String[] frameworks = {"Spring", "Hibernate", "Micronaut"};

        System.out.println("Классический for с индексом — есть доступ к position");
        for (int index = 0; index < frameworks.length; index++) {
            System.out.println("  frameworks[" + index + "] = " + frameworks[index]);
        }

        System.out.println("for-each удобен для чтения, но переменная — копия значения");
        for (String framework : frameworks) {
            System.out.println("  framework = " + framework);
            framework = framework + "?"; // не меняет исходный массив
        }
        System.out.println("После for-each массив не изменился → " + Arrays.toString(frameworks));

        int[] ratings = {1, 2, 3};
        System.out.println("for-each по примитивам также отдаёт копии: попытка изменить rating не влияет на массив");
        for (int rating : ratings) {
            System.out.println("  rating до += 10 → " + rating);
            rating += 10; // копия, исходный элемент не затронут
        }
        System.out.println("После for-each ratings остался прежним → " + Arrays.toString(ratings));

        System.out.println("Arrays.toString быстро печатает содержимое → " + Arrays.toString(frameworks));
        System.out.println();
    }

    private static void useArraysUtilityMethods() {
        System.out.println("4) Полезные методы java.util.Arrays");
        int[] metrics = {5, 2, 9, 1, 5};
        Arrays.sort(metrics);
        System.out.println("sort → " + Arrays.toString(metrics) + " — сортирует по возрастанию");

        int[] filled = new int[3];
        Arrays.fill(filled, 7);
        System.out.println("fill(7) → " + Arrays.toString(filled) + " — быстро задать значение всем элементам");

        int[] copied = Arrays.copyOf(metrics, 3);
        System.out.println("copyOf(metrics, 3) → " + Arrays.toString(copied) + " — новая копия указанной длины");

        int foundIndex = Arrays.binarySearch(metrics, 5); // массив должен быть отсортирован
        System.out.println("binarySearch(metrics, 5) → индекс " + foundIndex + " в отсортированном массиве");

        int[] unsorted = {3, 1, 2};
        int unpredictable = Arrays.binarySearch(unsorted, 3);
        System.out.println("binarySearch по неотсортированному массиву → " + unpredictable
                + " — результат не определён, всегда сортируйте заранее");

        int[] sameMetrics = {1, 2, 5, 5, 9};
        System.out.println("equals(metrics, sameMetrics) → " + Arrays.equals(metrics, sameMetrics)
                + " — сравнение по значениям");

        int[] sameLinkLeft = metrics;
        System.out.println("metrics == sameLinkLeft → " + (metrics == sameLinkLeft)
                + " — проверка ссылки, а не содержимого (true, т.к. ссылка одна)");

        int[] newButEqual = Arrays.copyOf(metrics, metrics.length);
        System.out.println("metrics == newButEqual → " + (metrics == newButEqual)
                + " — разные ссылки");
        System.out.println("Arrays.equals(metrics, newButEqual) → " + Arrays.equals(metrics, newButEqual)
                + " — одинаковые значения, поэтому true");

        String[] textValues = {"zeta", "alpha", "beta"};
        Arrays.sort(textValues); // строки реализуют Comparable, поэтому сортировка работает из коробки
        System.out.println("sort для объектов требует Comparable: String уже реализует, результат → "
                + Arrays.toString(textValues));
        System.out.println();
    }

    private static void workWithMultidimensionalArrays() {
        System.out.println("5) Двумерные массивы");
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println("matrix[1][2] = " + matrix[1][2] + " — второй ряд, третий столбец");

        System.out.println("deepToString показывает вложенность без ручного перебора → " + Arrays.deepToString(matrix));

        System.out.println("Перебор по строкам и столбцам:");
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                System.out.print(matrix[row][column] + " ");
            }
            System.out.println();
        }

        int[][] emptyGrid = new int[2][3];
        emptyGrid[0][0] = 42;
        System.out.println("new int[2][3] заполняется нулями, можно перезаписывать по координатам:");
        for (int[] row : emptyGrid) {
            System.out.println("  " + Arrays.toString(row));
        }
        System.out.println();
    }

    private static void handleJaggedArrays() {
        System.out.println("6) Неправильные (jagged) массивы");
        int[][] jagged = {
                {1, 2, 3},
                {4, 5},
                {6, 7, 8, 9}
        };
        System.out.println("Доступ к разным длинам: jagged[1][1] = " + jagged[1][1]);

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
        System.out.println("Массив удобен, если размер известен заранее → " + Arrays.toString(stableUsers));

        List<String> dynamicUsers = new ArrayList<>();
        dynamicUsers.add("Alice");
        dynamicUsers.add("Bob");
        dynamicUsers.remove("Alice");
        System.out.println("ArrayList растёт и уменьшается по ходу работы → " + dynamicUsers);

        System.out.println("Массивы работают быстрее и экономнее по памяти, но не меняют размер");
        System.out.println("ArrayList удобен для частых insert/remove и работы с коллекциями в Spring");
        System.out.println();
    }

    private static void highlightArrayPitfalls() {
        System.out.println("8) Подводные камни");
        System.out.println("length — поле, а не метод: numbers.length, а не numbers.length()");
        int[] original = {1, 2, 3};
        int[] alias = original; // одна и та же ссылка
        alias[0] = 99; // изменит и original
        System.out.println("Присваивание другого массива копирует ссылку: alias[0]=99 → original = "
                + Arrays.toString(original));

        int[] safeCopy = Arrays.copyOf(original, original.length);
        System.arraycopy(original, 0, safeCopy, 0, original.length);
        safeCopy[1] = 55;
        System.out.println("copyOf/System.arraycopy создают независимые копии: safeCopy = " +
                Arrays.toString(safeCopy) + ", original остаётся " + Arrays.toString(original));
        System.out.println("Проверяйте индексы до доступа, особенно при чтении из БД или запросов");
        System.out.println("Для многомерных массивов используйте Arrays.deepEquals " +
                "для корректного сравнения вложенных структур");
        System.out.println();
    }
}
