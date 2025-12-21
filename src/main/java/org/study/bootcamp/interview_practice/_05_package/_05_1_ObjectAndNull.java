package org.study.bootcamp.interview_practice._05_package;

import java.util.Objects;

/**
 * КЛАСС ДЛЯ ДЕМОНСТРАЦИИ Object И null В JAVA И ИХ ОСОБЕННОСТЯМИ
 *
 * Object — базовый класс всех ссылочных типов: каждый объект имеет набор готовых методов по умолчанию
 *
 * null — специальное значение ссылки: "объекта нет"
 * Важно: null — не объект; у null нельзя вызвать методы и обратиться к полям
 * Где может появиться null:
 * 1) Любая переменная ссылочного типа
 * 2) Поля объектов ссылочного типа
 * 3) Элементы массивов ссылочных типов
 * 4) Значения в коллекциях (если реализация позволяет)
 * 5) Возвращаемое значение метода
 * 6) Параметры методов ссылочного типа
 * 7) Данные из внешних источников
 * 8) Авто-распаковка обёрток
 *
 * Работа с ID из БД (базовая практика)
 * - В entity:
 *   Long id (null до сохранения в БД, после сохранения — значение генерируется БД)
 * - В DTO / API:
 *   Long id (null допустим, если объект ещё не сохранён или ID отсутствует)
 * - В методах, где ID обязателен:
 *   long id (null невозможен, контракт метода жёсткий)
 * - В методах поиска:
 *   либо возвращать объект или null,
 *   либо (предпочтительно) возвращать Optional<T> для явного контракта отсутствия результата
 *
 * @author Sergey
 */
public class _05_1_ObjectAndNull {

    private static void demonstrateObjectBaseMethods() {
        System.out.println("1) Object: базовые методы (что делает метод, когда и зачем его применять)");

        Object plainObject = new Object();
        SampleCloneable sampleCloneable = new SampleCloneable("payload");
        SampleEqualsHashCode firstUserId = new SampleEqualsHashCode(10);
        SampleEqualsHashCode secondUserId = new SampleEqualsHashCode(10);

//======================================================================================================================

        System.out.println();
        System.out.println("A) getClass(): узнать реальный класс объекта во время выполнения");
        System.out.println("   Что значит «во время выполнения»:");
        System.out.println("   - код уже запущен, JVM исполняет программу;");
        System.out.println("   - переменная может иметь тип родителя, но внутри лежит объект наследника");
        System.out.println("   Зачем нужно:");
        System.out.println("   - логирование: понять, какой именно объект пришёл;");
        System.out.println("   - диагностика: быстро увидеть реальный тип в ошибках/логах;");
        System.out.println("   - reflection (будет дальше в курсе): получить метаданные класса по этому объекту");
        System.out.println();
        System.out.println("   Объект для примера: plainObject = new Object()");
        System.out.println("   plainObject.getClass().getName()       → " + plainObject.getClass().getName()
                + " (полное имя класса с пакетом)");
        System.out.println("   plainObject.getClass().getSimpleName() → " + plainObject.getClass().getSimpleName()
                + " (короткое имя класса без пакета)");

//======================================================================================================================

        System.out.println();
        System.out.println("B) toString(): получить текстовое представление объекта");
        System.out.println("   Что это такое:");
        System.out.println("   - метод возвращает строку, которую пишут в лог/консоль, чтобы понять состояние объекта");
        System.out.println("   Что делает версия «по умолчанию» (если не переопределять):");
        System.out.println("   - Object.toString() печатает имя класса и hashCode()");
        System.out.println("     в hex-формате (обычно выглядит как \"Class@1a2b3c\")");
        System.out.println("   Зачем переопределяют toString():");
        System.out.println("   - чтобы вместо технических данных видеть полезные поля объекта (например, id/статус)");
        System.out.println();
        System.out.println("   plainObject.toString()     → " + plainObject.toString()
                + " (дефолтный формат: ClassName@hexHash; hexHash — hashCode() объекта в hex)");
        System.out.println("   sampleCloneable.toString() → " + sampleCloneable
                + " (переопределённый toString: показывает payload; payload = «содержимое объекта»)");
        System.out.println("Подробнее про работу переопределения в Java: см. 06");

//======================================================================================================================

        System.out.println();
        System.out.println("C) equals(): проверить «равны ли» два объекта по смыслу");
        System.out.println("   Важно: есть два разных вопроса:");
        System.out.println("   1) «Это один и тот же объект?»                     → проверка: == (сравнение ссылок)");
        System.out.println("   2) «Это разные объекты, но с одинаковыми данными?» → проверка: equals()");
        System.out.println();
        System.out.println("   Сценарий: два разных объекта с одинаковым value=10");
        System.out.println("   value — это внутреннее поле объекта (int), которое хранит его значение");
        System.out.println("   firstUserId  = " + firstUserId);
        System.out.println("   secondUserId = " + secondUserId);

        System.out.println("   firstUserId == secondUserId      → " + (firstUserId == secondUserId)
                + " (false: ссылки разные, это два разных объекта)");
        System.out.println("   firstUserId.equals(secondUserId) → " + firstUserId.equals(secondUserId)
                + " (true: метод equals() в классе написан так, чтобы"
                + " сравнивать данные объекта — поле value; у обоих value=10)"
        );

        System.out.println("   Правила для equals():");
        System.out.println("   - должен давать одинаковый результат при повторном вызове, если данные объектов");
        System.out.println("     не менялись, иначе «плавающее» сравнение сломает работу коллекций (см. 09);");
        System.out.println("   - должен соблюдать базовые правила: reflexive, symmetric, transitive, consistent");
        System.out.println("   - обязан вернуть false при сравнении с null: x.equals(null) → false");
        System.out.println("   - если equals говорит «равны», то hashCode тоже обязан совпасть (см. следующий пункт)");

//======================================================================================================================

        System.out.println();
        System.out.println("D) hashCode(): получить целое число для быстрого поиска в хэш-структурах");
        System.out.println("   Что такое «хэш-структуры» (см. 09):");
        System.out.println("   - HashMap/HashSet используют hashCode(), чтобы быстро выбрать «корзину» (bucket),");
        System.out.println("     где искать элемент, вместо полного перебора");
        System.out.println("   Ключевой контракт (обязательное правило) (см. 09):");
        System.out.println("   - при a.equals(b) == true, то a.hashCode() == b.hashCode() тоже должен быть одинаковым");
        System.out.println("   - обратное неверно: одинаковый hashCode не гарантирует equals (возможны коллизии)");
        System.out.println();
        System.out.println("   firstUserId.hashCode()  → " + firstUserId.hashCode());
        System.out.println("   secondUserId.hashCode() → " + secondUserId.hashCode()
                + " (должен совпадать, потому что equals вернул true)");
        System.out.println("   Правило на практике (см. 09):");
        System.out.println("   - если equals() сравнивает поля X и Y, то hashCode() тоже обязан вычисляться по X и Y;");
        System.out.println("     иначе HashMap/HashSet могут «потерять» объект:");
        System.out.println("     equals говорит «тот же», но поиск идёт в другую корзину");

//======================================================================================================================

        System.out.println();
        System.out.println("E) clone(): получить копию объекта (обычно поверхностную копию)");
        System.out.println("   Что значит «поверхностная копия» (shallow copy):");
        System.out.println("   - примитивные поля копируются как значения;");
        System.out.println("   - ссылочные поля копируются как ссылки");
        System.out.println("     (оба объекта могут указывать на один вложенный объект)");
        System.out.println("   Важно для примера:");
        System.out.println("   - здесь payload — String (неизменяемая), поэтому проблема shallow copy не проявляется;");
        System.out.println("     если бы payload был изменяемым объектом, часто");
        System.out.println("     требовалась бы глубокая копия (deep copy) вручную");
        System.out.println("   Ограничения clone():");
        System.out.println("   - Object.clone() имеет модификатор protected: извне его нельзя вызвать напрямую;");
        System.out.println("   - класс должен поддерживать клонирование: implements Cloneable и override clone()");
        System.out.println();

        try {
            SampleCloneable cloned = sampleCloneable.clone();
            System.out.println("   Исходный объект: " + sampleCloneable);
            System.out.println("   Клон:            " + cloned);

            System.out.println("   sampleCloneable == cloned      → " + (sampleCloneable == cloned)
                    + " (false: это разные объекты в памяти)");

            System.out.println("   sampleCloneable.equals(cloned) → " + sampleCloneable.equals(cloned)
                    + " (true: содержимое payload совпадает)");

        } catch (CloneNotSupportedException exception) {
            System.out.println("   clone() → CloneNotSupportedException: " + exception.getMessage());
        }

//======================================================================================================================

        System.out.println();
        System.out.println("F) notify() и notifyAll(): пробуждение потоков, которые ждут на этом объекте");
        System.out.println("   Контекст: многопоточность (несколько потоков выполняются параллельно)");
        System.out.println("   Что такое monitor (монитор объекта):");
        System.out.println("   - это «встроенный замок» у каждого объекта, который используется в synchronized");
        System.out.println("   Правило безопасности:");
        System.out.println("   - notify()/notifyAll() можно вызывать только внутри synchronized(monitor),");
        System.out.println("     иначе будет IllegalMonitorStateException (замок не захвачен)");
        System.out.println("   Разница:");
        System.out.println("   - notify()  будит один ожидающий поток;");
        System.out.println("   - notifyAll() будит все ожидающие потоки");
        demonstrateNotifyAndNotifyAllSafely();

//======================================================================================================================

        System.out.println();
        System.out.println("G) wait(): перевести текущий поток в ожидание на мониторе объекта");
        System.out.println("   Что делает wait():");
        System.out.println("   - поток «засыпает» и освобождает монитор (замок),");
        System.out.println("     чтобы другие потоки могли войти в synchronized;");
        System.out.println("   - потом поток проснётся из-за notify/notifyAll или таймаута");
        System.out.println("   Перегрузки wait():");
        System.out.println("   - wait()                  : без таймаута (может ждать бесконечно);");
        System.out.println("   - wait(timeoutMillis)     : до N миллисекунд (верхняя граница ожидания);");
        System.out.println("   - wait(timeoutMillis, ns) : уточнение таймаута наносекундами");
        System.out.println("   Важный момент про «spurious wakeup» (ложное пробуждение):");
        System.out.println("   - поток иногда может проснуться без notify;");
        System.out.println("     поэтому условие ожидания проверяют в while, а не в if");
        demonstrateWaitOverloadsSafely();

//======================================================================================================================

        System.out.println();
        System.out.println("H) finalize(): устаревшая финализация объекта (не используйте в логике программы)");
        System.out.println("   Что это исторически было:");
        System.out.println("   - попытка выполнить код перед тем, как объект будет удалён сборщиком мусора (GC)");
        System.out.println("   Почему плохо:");
        System.out.println("   - нет гарантии, что finalize() вызовется вообще;");
        System.out.println("   - нет гарантии, когда именно это произойдёт;");
        System.out.println("   - может приводить к утечкам ресурсов и непредсказуемости");
        System.out.println("   Правильная замена:");
        System.out.println("   - try-with-resources + AutoCloseable/Closeable для файлов/сокетов/соединений и т.п.");
        demonstrateFinalizeAsConcept();

//======================================================================================================================

        System.out.println();
        System.out.println("I) null: «ссылки нет», безопасные приёмы работы с null");
        demonstrateNullHandling();

        System.out.println();
    }

//======================================================================================================================

    private static void demonstrateNotifyAndNotifyAllSafely() {
        Object monitor = new Object();

        System.out.println("   Демонстрация: показываем, что без synchronized вызов запрещён");

        try {
            monitor.notify();
            System.out.println("   monitor.notify() вне synchronized → выполнено (не должно случиться)");
        } catch (IllegalMonitorStateException exception) {
            System.out.println("   monitor.notify() вне synchronized → IllegalMonitorStateException"
                    + " (нельзя: монитор/замок не захвачен)");
        }

        synchronized (monitor) {
            System.out.println("   synchronized(monitor) { ... } → монитор захвачен, вызовы допустимы");
            monitor.notify();
            monitor.notifyAll();
            System.out.println("   Внутри synchronized: notify() и notifyAll() выполнены корректно");
        }
    }

    private static void demonstrateWaitOverloadsSafely() {
        Object monitor = new Object();

        try {
            monitor.wait(1);
            System.out.println("   monitor.wait(1) вне synchronized → выполнено (не должно случиться)");
        } catch (IllegalMonitorStateException exception) {
            System.out.println("   monitor.wait(1) вне synchronized → IllegalMonitorStateException"
                    + " (нельзя: монитор/замок не захвачен)");
        } catch (InterruptedException exception) {
            System.out.println("   monitor.wait(1) → InterruptedException: " + exception.getMessage());
        }

        System.out.println("   Демонстрация: используем таймауты, чтобы не зависнуть навсегда");
        synchronized (monitor) {
            try {
                long startMillis = System.currentTimeMillis();

                System.out.println("   1) wait() без таймаута не вызываем: без notify поток может ждать бесконечно");
                System.out.println("   2) wait(50): поток ждёт до 50 мс, затем продолжает выполнение");
                monitor.wait(50);

                long elapsedMillis = System.currentTimeMillis() - startMillis;
                System.out.println("      wait(50) завершился: прошло примерно " + elapsedMillis + " мс");

                System.out.println("   3) wait(20, 500_000): до ~20.5 мс (20 мс + 500_000 нс)");
                monitor.wait(20, 500_000);
                System.out.println("      wait(20, 500_000) завершился: поток продолжил выполнение");
            } catch (InterruptedException exception) {
                System.out.println("   wait(...) → InterruptedException: " + exception.getMessage());
                Thread.currentThread().interrupt();
                System.out.println("   Статус interrupted восстановлен: Thread.currentThread().interrupt()");
            }
        }
    }

    private static void demonstrateFinalizeAsConcept() {
        System.out.println("   Демонстрация: finalize() специально не форсируем через System.gc()");
        System.out.println("   Причина: GC может не запуститься прямо сейчас, а finalize может не вызваться вовсе");
    }

    private static void demonstrateNullHandling() {
        String value = null;

        System.out.println("   Что такое null:");
        System.out.println("   - это специальное значение ссылочной переменной: «ссылки нет»;");
        System.out.println("   - это НЕ объект: у null нет методов, полей и класса");
        System.out.println();
        System.out.println("   Сценарий: value = null (объекта нет, это «пустая ссылка»)");
        System.out.println("   Проверка: value == null → " + (value == null));
        System.out.println("   value instanceof String → " + (value instanceof String)
                + " (для null всегда false)");

        System.out.println();
        System.out.println("   A) Что будет, если вызвать метод у null:");
        try {
            int length = value.length();
            System.out.println("   value.length() → " + length);
        } catch (NullPointerException exception) {
            System.out.println("   value.length() → NullPointerException"
                    + " (нельзя вызывать методы у null-ссылки)");
        }

        System.out.println();
        System.out.println("   B) Грамотный порядок проверок, чтобы не получить NullPointerException:");
        System.out.println("   - сначала проверяем value == null;");
        System.out.println("   - затем работаем с объектом (value.length(), value.equals(...), и т.д.)");
        if (value == null) {
            System.out.println("   Пример: value == null → обрабатываем отсутствие значения (ветка if)");
        } else {
            System.out.println("   Пример: value != null → можно безопасно вызывать методы");
        }

        System.out.println();
        System.out.println("   C) Безопасное сравнение на равенство:");
        System.out.println("   - Нельзя: value.equals(\"demo\") (упадёт при value=null)");
        System.out.println("   - Можно: Objects.equals(value, \"demo\") (обрабатывает null)");
        System.out.println("   Objects.equals(value, \"demo\") → " + Objects.equals(value, "demo"));
        System.out.println("   Objects.equals(null, null) → " + Objects.equals(null, null)
                + " (true: оба значения отсутствуют)");

        System.out.println();
        System.out.println("   D) Fail-fast (быстрое падение): требуем, чтобы значение было не null");
        System.out.println("   Когда применять:");
        System.out.println("   - в аргументах методов и обязательных полях: лучше упасть сразу с понятным сообщением,");
        System.out.println("     чем получить ошибку позже в другом месте");
        System.out.println("   Objects.requireNonNull(x, message) → если x=null, бросает NullPointerException и текст");
        try {
            Objects.requireNonNull(value, "value обязателен: ссылка не должна быть null");
            System.out.println("   requireNonNull прошёл (не должно случиться)");
        } catch (NullPointerException exception) {
            System.out.println("   requireNonNull(value, ...) → NullPointerException: " + exception.getMessage());
        }

        System.out.println();
        System.out.println("   E) Примечание: null — это отдельный случай, который нужно явно обрабатывать");
        System.out.println("   - Если null допустим по смыслу → проверяем и обрабатываем как «значения нет»");
        System.out.println("   - Если null недопустим → запрещаем (requireNonNull / валидация / исключение)");
    }

/*
instanceof — оператор проверки типа объекта во время выполнения (runtime)

Синтаксис:
    reference instanceof Type

Что проверяет:
- Возвращает true, если reference НЕ равен null и объект, на который он указывает,
  является экземпляром Type (или его наследника / реализует интерфейс Type)
- Возвращает false, если reference == null

Зачем нужен:
- Безопасно проверить тип перед приведением (cast), чтобы не получить ClassCastException
- В ветвлении: выбрать логику в зависимости от реального типа объекта

Примеры:
    Object value = "text";
    value instanceof String        -> true
    value instanceof CharSequence  -> true (String реализует CharSequence)
    value instanceof Integer       -> false
    null instanceof String         -> false

Важно:
- instanceof не сравнивает объекты и не проверяет «равенство по данным»; отвечает только на вопрос: «объект этого типа?»

Ограничения:
- Для примитивов (int, boolean и т.д.) instanceof не применяется: только для ссылочных типов
*/

    private static final class SampleEqualsHashCode {
        private final int value;

        public SampleEqualsHashCode(int value) {
            if (value <= 0) {
                throw new IllegalArgumentException("value должен быть положительным: value=" + value);
            }
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SampleEqualsHashCode)) {
                return false;
            }
            SampleEqualsHashCode that = (SampleEqualsHashCode) other;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(value);
        }

        @Override
        public String toString() {
            return "SampleEqualsHashCode{value=" + value + "}";
        }
    }

    private static final class SampleCloneable implements Cloneable {
        private final String payload;

        public SampleCloneable(String payload) {
            if (payload == null || payload.isBlank()) {
                throw new IllegalArgumentException("payload не может быть пустым");
            }
            this.payload = payload;
        }

        @Override
        public SampleCloneable clone() throws CloneNotSupportedException {
            return (SampleCloneable) super.clone();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SampleCloneable)) {
                return false;
            }
            SampleCloneable that = (SampleCloneable) other;
            return payload.equals(that.payload);
        }

        @Override
        public int hashCode() {
            return payload.hashCode();
        }

        @Override
        public String toString() {
            return "SampleCloneable{payload=\"" + payload + "\"}";
        }
    }

    public static void main(String[] args) {
        demonstrateObjectBaseMethods();
    }
}
