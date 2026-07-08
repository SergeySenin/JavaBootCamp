package org.study.bootcamp.interviewpractice;

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
public class Lesson03Strings {

    private static void showStringPoolScheme() {
        System.out.println("1) Где живут строки (String Pool и heap)");

        // Литерал "Hello" находится в String Pool (пул строк) и переиспользуется
        String literalFromPool = "Hello";

        // new String("Hello") создаёт НОВЫЙ объект в heap (куче),
        // используя значение литерала "Hello" (который при этом остаётся в пуле)
        String createdWithNew = new String("Hello");

        // intern() возвращает ссылку на экземпляр из пула строк
        String fromPoolAgain = createdWithNew.intern();


        System.out.println("literalFromPool == createdWithNew → " + (literalFromPool == createdWithNew)
                + " (разные ссылки: pool vs heap)");
        System.out.println("literalFromPool == fromPoolAgain → " + (literalFromPool == fromPoolAgain)
                + " (intern вернул ссылку из пула)");
        System.out.println("literalFromPool.equals(createdWithNew) → " + literalFromPool.equals(createdWithNew)
                + " (equals сравнивает содержимое)");

        System.out.println();
    }

    private static void showImmutability() {
        System.out.println("2) Иммутабельность на примере");

        String productName = "Notebook";
        String upperCased = productName.toUpperCase();
        String trimmed = ("  " + productName + "  ").trim();

        System.out.println("Оригинал: " + productName + " (исходная строка не меняется)");
        System.out.println("toUpperCase() → " + upperCased + " (создан новый объект)");
        System.out.println("trim() → " + trimmed + " (создан новый объект)");

        System.out.println();
    }

    private static void compareValuesAndReferences() {
        System.out.println("3) Сравнение ссылок и содержимого");

        String greetingLiteral = "hi";
        String greetingNew = new String("hi");

        boolean sameReference = greetingLiteral == greetingNew; // == сравнивает ссылки
        boolean sameText = greetingLiteral.equals(greetingNew); // equals сравнивает символы
        int lexicalOrder = "abc".compareTo("abd");
        int ignoreCaseOrder = "abc".compareToIgnoreCase("ABC");

        System.out.println("== проверяет ссылки → " + sameReference);
        System.out.println("equals проверяет текст → " + sameText);
        System.out.println(
                "compareTo(\"abc\", \"abd\") → "
                        + lexicalOrder + " (отрицательное — \"abc\" идёт раньше)"
        );
        System.out.println(
                "compareToIgnoreCase(\"abc\", \"ABC\") → "
                + ignoreCaseOrder + " (0 — равны без учёта регистра)"
        );

        System.out.println();
    }

    private static void iterateCharacters() {
        System.out.println("4) Работа с символами и Unicode");

        String welcome = "Привет";
        System.out.println("Исходная строка: " + welcome);

        char firstLetter = welcome.charAt(0);
        int lengthInCodeUnits = welcome.length();
        int firstCodePoint = welcome.codePointAt(0);
        // Unicode — стандарт кодирования символов, где каждому символу соответствует числовой код (code point)
        // UTF — семейство кодировок Unicode (UTF-8, UTF-16 и т.д.)

        System.out.println("charAt(0) → '" + firstLetter + "' (UTF-16 элемент, не всегда «символ»)");
        System.out.println("length() → " + lengthInCodeUnits + " (кол-во UTF-16 элементов)");
        System.out.println("codePointAt(0) → " + firstCodePoint + " (полный code point, важно для эмодзи)");

        System.out.println("Корректный обход по code point:");
        welcome.codePoints().forEach(codePoint -> {
            String symbol = new String(Character.toChars(codePoint));
            System.out.println("символ '" + symbol + "' имеет code point " + codePoint);
        });

        System.out.println();
    }

    private static void concatenateProperly() {
        System.out.println("5) Конкатенация: выбираем подход");

        String firstName = "Ivan";
        String lastName = "Petrov";
        String combinedName = "Имя: " + firstName + " " + lastName;
        System.out.println(combinedName + " — '+' уместен для 2–3 частей без циклов");

        List<String> tags = List.of("spring", "database", "rest");
        StringBuilder tagBuilder = new StringBuilder("Теги: ");
        for (String tag : tags) {
            tagBuilder.append("[").append(tag).append("] ");
        }

        String preparedTags = tagBuilder.toString();
        System.out.println(preparedTags + " — StringBuilder лучше в циклах и при большом тексте");
        System.out.println("StringBuffer похож, но синхронизирован (обычно медленнее; нужен редко)");

        System.out.println();
    }

/*
    StringBuilder и StringBuffer — изменяемые классы для сборки строк
    StringBuilder — быстрый и несинхронизированный
    StringBuffer — потокобезопасный (synchronized) аналог
    Оба поддерживают append/insert/replace/delete/reverse/toString
 */

    private static void useStringBuilderOperations() {
        System.out.println("6) Частые методы StringBuilder");

        StringBuilder scenario = new StringBuilder("Base");
        System.out.println("Исходный StringBuilder: \"" + scenario + "\"");

        scenario.append(" + tail");
        System.out.println("append(\" + tail\") → \"" + scenario + "\" (добавляет в конец)");

        scenario.insert(4, "#");
        System.out.println("insert(4, \"#\") → \"" + scenario + "\" (вставляет на позицию 4)");

        scenario.replace(0, 4, "Head");
        System.out.println("replace(0, 4, \"Head\") → \"" + scenario + "\" (заменяет диапазон [0..3])");

        scenario.delete(4, 5);
        System.out.println("delete(4, 5) → \"" + scenario + "\" (удаляет символ на позиции 4)");

        scenario.reverse();
        System.out.println("reverse() → \"" + scenario + "\" (переворачивает)");

        System.out.println("Итог: \"" + scenario + "\"");
        System.out.println("StringBuilder мутабельный: операции меняют исходный объект");
        System.out.println("Получить String: scenario.toString()");

        System.out.println();
    }

    private static void reviewCoreStringMethods() {
        System.out.println("7) Частые методы String");

        String text = "Java rocks!  ";
        System.out.println("Исходная строка: \"" + text + "\"");

        System.out.println("isBlank() → " + text.isBlank()
                + " (true, если строка пустая или состоит только из пробельных символов)");

        System.out.println("strip() → \"" + text.strip() + "\""
                + " (убирает пробелы в начале и конце, включая Unicode-пробелы)");

        System.out.println("startsWith(\"Java\") → " + text.startsWith("Java")
                + " (проверяет, начинается ли строка с заданного префикса)");

        System.out.println("endsWith(\"rocks!\") → " + text.endsWith("rocks!")
                + " (проверяет, заканчивается ли строка заданным суффиксом)");

        System.out.println("repeat(2) → \"" + text.repeat(2) + "\""
                + " (повторяет строку указанное число раз)");

        System.out.println("toLowerCase() → \"" + text.toLowerCase() + "\""
                + " (приводит все буквы к нижнему регистру)");

        System.out.println("contains(\"rock\") → " + text.contains("rock")
                + " (проверяет наличие подстроки)");

        System.out.println("substring(2, 6) → \"" + text.substring(2, 6) + "\""
                + " (берёт часть строки: индекс 2 включительно, 6 не включительно)");

        System.out.println("replaceFirst(\"a\", \"@\") → \"" + text.replaceFirst("a", "@") + "\""
                + " (заменяет первое совпадение по regex \"a\" на \"@\")");

        System.out.println();
    }

/*
    Дополнительная памятка по поиску и замене:
    - indexOf / contains — находят подстроку или говорят, что её нет (возвращают позицию или -1, либо boolean)
    - replace / replaceAll — заменяют текст; replaceAll интерпретирует первый аргумент как регулярное выражение
    - replace(...) — замена literal (обычная строка),
      replaceFirst/replaceAll(...) — замена по regex (регулярному выражению)
    - substring(left, right) — берёт подстроку: левая граница включительно, правая не включительно
    - String.join(delimiter, parts...) и String.valueOf(value) —
    безопасные способы склеить текст и числа без конкатенации в цикле
 */

    private static void formatStrings() {
        System.out.println("8) Форматирование строк");

        String template = "Точка: (%d, %d)";
        String formatted = String.format(template, 10, 20);

        System.out.println(formatted + " — String.format возвращает новую строку по шаблону");
        System.out.printf("printf печатает сразу: число %.2f и строка %s%n", 3.14159, "demo");
        System.out.println("Плейсхолдеры: %d — целое, %f — дробь, %s — строка, %n — перенос строки");

        System.out.println();
    }

    private static void useRegularExpressions() {
        System.out.println("9) Регулярные выражения (Pattern/Matcher, split, replaceAll)");

        String phoneRegex = "\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}";
        String phoneInput = "+7-999-123-45-67";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher matcher = phonePattern.matcher(phoneInput);

        System.out.println("Шаблон телефона: " + phoneRegex);
        System.out.println("Проверяем строку: " + phoneInput);

        boolean matches = matcher.matches();
        System.out.println("matches() → " + matches + " (true только если совпала ВСЯ строка целиком)");
        if (matches) {
            String operatorCode = matcher.group().substring(3, 6);
            System.out.println("Извлекаем код оператора через substring(3, 6) → " + operatorCode);
        }

        String splitRegex = "\\d";
        String splitInput = "a1b2c3";
        String[] parts = splitInput.split(splitRegex);
        System.out.println("split(\"" + splitRegex + "\") для строки \"" + splitInput
                + "\" → " + Arrays.toString(parts) + " (делит по цифрам)");

        String card = "4111-1111-1111-1111";
        String maskRegex = "\\d(?=\\d{4})";
        String masked = card.replaceAll(maskRegex, "*");
        System.out.println("replaceAll(\"" + maskRegex + "\", \"*\") для \"" + card
                + "\" → " + masked + " (маскирует все цифры, кроме последних 4)");

        System.out.println();
    }

    private static void convertEncodingsSafely() {
        System.out.println("10) Кодировки: указывайте Charset явно, чтобы не зависеть от платформы");

        String greeting = "Привет";
        System.out.println("Текущая defaultCharset → " + Charset.defaultCharset()
                + " (может отличаться на разных ОС/контейнерах)");

        // UTF — семейство кодировок Unicode (UTF-8, UTF-16 и т.д.)
        byte[] utf8Bytes = greeting.getBytes(StandardCharsets.UTF_8);
        String restored = new String(utf8Bytes, StandardCharsets.UTF_8);

        System.out.println("getBytes(UTF_8) → " + Arrays.toString(utf8Bytes) + " (байты строки в UTF-8)");
        System.out.println("new String(bytes, UTF_8) → " + restored + " (обратное преобразование тем же Charset)");

        byte[] platformBytes = greeting.getBytes(); // platform default charset
        String platformRestored = new String(platformBytes, Charset.defaultCharset());
        System.out.println("getBytes() без Charset → байты в defaultCharset;" +
                " при переносе между средами возможно искажение");

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
        System.out.println("JSON в текстовом блоке:");
        System.out.println(jsonPayload);

        System.out.println();
    }

    private static void highlightPitfalls() {
        System.out.println("12) Подводные камни");

        System.out.println("Сравнение с null: Objects.equals(a, b) безопаснее, чем a.equals(b)");
        System.out.println("В цикле не склеивайте строки через '+': используйте StringBuilder");
        System.out.println("substring/replace/toUpperCase создают новые объекты:" +
                " сохраняйте результат (переприсваивайте переменную)");
        System.out.println("Для эмодзи и суррогатных пар используйте codePointAt / codePoints вместо charAt");

        System.out.println();
    }

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
}
