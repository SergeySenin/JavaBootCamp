package org.study.bootcamp.interview_practice;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ СТРОК В JAVA И ИХ ОСОБЕННОСТЕЙ
 *
 * @author Sergey
 */
public class _04_Strings {

    public static void main(String[] args) {
        showStringPoolScheme();
        showImmutability();
        compareValuesAndReferences();
        iterateCharacters();
        concatenateProperly();
        useStringBuilderOperations();
        reviewCoreStringMethods();
        formatStrings();
        useRegularExpressions();
        convertEncodingsSafely();
        useTextBlocks();
        highlightPitfalls();
    }

    private static void showStringPoolScheme() {
        System.out.println("1) Где живут строки");
        // Литерал берёт готовый объект из String Pool — удобно, когда нужны повторяющиеся неизменяемые строки
        String literalFromPool = "Hello";
        // Создание через new всегда добавляет объект в кучу — так делают редко, если нужен отдельный экземпляр
        String createdWithNew = new String("Hello");
        // intern() возвращает ссылку из пула строк, если строка там есть
        String fromPoolAgain = createdWithNew.intern();

        System.out.println("literalFromPool == createdWithNew → " + (literalFromPool == createdWithNew));
        System.out.println("literalFromPool == fromPoolAgain → " + (literalFromPool == fromPoolAgain));
        System.out.println("literalFromPool.equals(createdWithNew) → " + literalFromPool.equals(createdWithNew));
        System.out.println();
    }

    private static void showImmutability() {
        System.out.println("2) Иммутабельность на примере");
        String productName = "Notebook";
        String upperCased = productName.toUpperCase();
        String trimmed = ("  " + productName + "  ").trim();

        System.out.println("Оригинал: " + productName + " — объект не изменился");
        System.out.println("Верхний регистр: " + upperCased + " — создан новый объект");
        System.out.println("trim() возвращает новый объект: " + trimmed);
        System.out.println();
    }

    private static void compareValuesAndReferences() {
        System.out.println("3) Сравнение ссылок и содержимого");
        String greetingLiteral = "hi";
        String greetingNew = new String("hi");

        boolean sameReference = greetingLiteral == greetingNew; // == сравнивает адреса объектов в памяти
        boolean sameText = greetingLiteral.equals(greetingNew); // equals проверяет, совпадают ли символы
        int lexicalOrder = "abc".compareTo("abd"); // возвращает отрицательное, если первая строка идёт раньше
        int ignoreCaseOrder = "abc".compareToIgnoreCase("ABC"); // сравнение без учёта регистра

        System.out.println("== проверяет ссылки → " + sameReference);
        System.out.println("equals проверяет текст → " + sameText);
        System.out.println("compareTo(\"abc\", \"abd\") → " + lexicalOrder + " (отрицательное — abc раньше)");
        System.out.println(
                "compareToIgnoreCase(\"abc\", \"ABC\") → " +
                        ignoreCaseOrder +
                        " (0 — равны без учёта регистра)"
        );
        System.out.println();
    }

    private static void iterateCharacters() {
        System.out.println("4) Работа с отдельными символами");
        String welcome = "Привет";
        char firstLetter = welcome.charAt(0); // charAt берёт первый 16-битный элемент строки
        int lengthInCodeUnits = welcome.length(); // length возвращает количество таких элементов, а не букв алфавита
        int firstCodePoint = welcome.codePointAt(0); // codePointAt читает полный код символа в Unicode

        System.out.println("Первый символ через charAt: " + firstLetter);
        System.out.println("Длина строки как количества UTF-16 элементов: " + lengthInCodeUnits);
        System.out.println(
                "Полный код первого символа (code point): " +
                        firstCodePoint +
                        " — подходит для эмодзи и редких знаков"
        );

        System.out.println("Корректный обход строки по символам:");
        welcome.codePoints().forEach(codePoint -> {
            String symbol = new String(Character.toChars(codePoint));
            System.out.println("символ '" + symbol + "' имеет код " + codePoint + " в Unicode");
        });
        System.out.println();
    }

    // Конкатенация — соединение нескольких строк в одну
    private static void concatenateProperly() {
        System.out.println("5) Конкатенация: выбираем подход");
        String firstName = "Ivan";
        String lastName = "Petrov";
        String combinedName = "Имя: " + firstName + " " + lastName;
        System.out.println(combinedName + " — короткая конкатенация через + понятна и быстра для пары частей");

        List<String> tags = List.of("spring", "database", "rest");
        StringBuilder tagBuilder = new StringBuilder("Теги: ");
        for (String tag : tags) {
            tagBuilder.append("[").append(tag).append("] ");
        }
        String preparedTags = tagBuilder.toString();
        System.out.println(preparedTags + " — StringBuilder лучше для циклов и большого текста");
        System.out.println("StringBuffer работает так же, но синхронизирован для многопоточности");
        System.out.println();
    }

    /*
     * StringBuilder и StringBuffer — изменяемые классы для сборки строк
     * StringBuilder — быстрый и несинхронизированный, подходит для однопоточных задач и циклов
     * StringBuffer — потокобезопасный (synchronized) аналог, медленнее, но безопасен в нескольких потоках
     * Оба поддерживают append/insert/replace/delete/reverse/toString для пошагового изменения текста
     */

    private static void useStringBuilderOperations() {
        System.out.println("6) Частые методы StringBuilder");
        StringBuilder scenario = new StringBuilder("Base");
        scenario.append(" + tail");
        scenario.insert(4, "#");
        scenario.replace(0, 4, "Head");
        scenario.delete(4, 5);
        scenario.reverse();

        System.out.println("Результат цепочки операций: " + scenario);
        System.out.println("Получить итоговую строку можно через toString()");
        System.out.println();
    }

    private static void reviewCoreStringMethods() {
        System.out.println("7) Частые методы String");
        String text = "Java rocks!  ";
        System.out.println("isBlank() → " + text.isBlank() + " — false, потому что внутри есть текст");
        System.out.println("strip() → '" + text.strip() + "' — удаляет пробелы включая Unicode");
        System.out.println("repeat(2) → '" + text.repeat(2) + "'");
        System.out.println("startsWith('Java') → " + text.startsWith("Java"));
        System.out.println("endsWith('rocks!') → " + text.endsWith("rocks!"));
        System.out.println("replaceFirst('a', '@') → '" + text.replaceFirst("a", "@") + "'");
        System.out.println("contains('rock') → " + text.contains("rock") + " — ищет подстроку");
        System.out.println("substring(2, 6) → '" + text.substring(2, 6) + "' — берёт часть строки по индексам");
        System.out.println("toLowerCase() → '" + text.toLowerCase() + "' — часто нужно перед сравнением");
        System.out.println();
    }

    /*
     * Дополнительная памятка по поиску и замене:
     * - indexOf / contains — находят подстроку или говорят, что её нет (возвращают позицию или -1, либо boolean)
     * - replace / replaceAll — заменяют текст; replaceAll интерпретирует первый аргумент как регулярное выражение
     * - substring(left, right) — берёт подстроку: левая граница включительно, правая не включительно.
     * - String.join(delimiter, parts...) и String.valueOf(value) —
     * безопасные способы склеить текст и числа без конкатенации в цикле
     */

    private static void formatStrings() {
        System.out.println("8) Форматирование строк");
        String template = "Точка: (%d, %d)";
        String formatted = String.format(template, 10, 20);
        System.out.println(formatted + " — String.format возвращает новую строку по шаблону");
        System.out.printf("printf печатает сразу: число %.2f и строка %s%n", 3.14159, "demo");
        System.out.println("Плейсхолдеры: %d — целое, %f — дробь, %s — строка, %n — перенос строки");
        System.out.println("Удобно для логов и формирования SQL/HTTP сообщений без конкатенации");
        System.out.println();
    }

    private static void useRegularExpressions() {
        System.out.println("9) Регулярные выражения через Pattern и Matcher");
        Pattern phonePattern = Pattern.compile("\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}");
        Matcher matcher = phonePattern.matcher("+7-999-123-45-67");
        boolean matches = matcher.matches();
        System.out.println("Совпадает ли номер? " + matches + " — matches требует совпадения всей строки");
        if (matches) {
            String operatorCode = matcher.group().substring(3, 6);
            System.out.println("Код оператора: " + operatorCode + " — можно извлекать части найденной строки");
        }
        String[] parts = "a1b2c3".split("\\d");
        System.out.println("split по цифрам → " + Arrays.toString(parts) + " — делит строку по шаблону");
        System.out.println(
                "replaceAll можно использовать для маскировки: '4111-1111-1111-1111' → "
                + "4111-****-****-1111".replaceAll("1", "*")
        );
        System.out.println();
    }

    private static void convertEncodingsSafely() {
        System.out.println("10) Кодировки: явная всегда лучше");
        String greeting = "Привет";
        byte[] utf8Bytes = greeting.getBytes(StandardCharsets.UTF_8);
        String restored = new String(utf8Bytes, StandardCharsets.UTF_8);
        System.out.println("UTF-8 байты: " + Arrays.toString(utf8Bytes));
        System.out.println("Восстановленная строка: " + restored);

        byte[] platformBytes = greeting.getBytes();
        String platformRestored = new String(platformBytes, Charset.defaultCharset());
        System.out.println("Зависимость от системной кодировки может исказить данные: " + platformRestored);
        System.out.println();
    }

    private static void useTextBlocks() {
        System.out.println("11) Текстовые блоки (Java 15+)");
        String jsonPayload = """
                {
                  "title": "Demo",
                  "items": [1, 2, 3]
                }
                """;
        System.out.println("JSON в текстовом блоке сохраняет отступы и переносы:");
        System.out.println(jsonPayload);
        System.out.println();
    }

    private static void highlightPitfalls() {
        System.out.println("12) Подводные камни");
        System.out.println("Objects.equals(a, b) безопаснее при возможных null");
        System.out.println("В цикле лучше собирать строки через StringBuilder, а не через +");
        System.out.println("substring/replace/toUpperCase создают новые объекты " +
                "— сохраняйте результат (переприсваивайте переменную)");
        System.out.println("Для эмодзи и суррогатных (составных) пар используйте " +
                "codePointAt/offsetByCodePoints вместо charAt");
        System.out.println();
    }
}
