# BootCamp

Репозиторий с обучающими задачами по Java.
Основная цель проекта: держать практику, справочные материалы и учебные датасеты в одной понятной структуре.

## Содержание

- [Быстрый старт](#быстрый-старт)
  - [Требования](#требования)
  - [Сборка](#сборка)
  - [Запуск демо-классов](#запуск-демо-классов)
- [Структура проекта](#структура-проекта)
  - [Нейминг и слои](#нейминг-и-слои)
  - [Учебные датасеты](#учебные-датасеты)
- [Зависимости](#зависимости)
- [Тестирование](#тестирование)
- [Полезные материалы](#полезные-материалы)

## Быстрый старт

### Требования

- Установленный JDK 17+.
- Доступ в интернет для скачивания зависимостей при первой сборке (используется Gradle Wrapper).

### Сборка

1. Перейдите в корень проекта.

2. Выполните сборку:
  - Linux/macOS:
  `./gradlew clean build`
  - Windows (PowerShell):
  `./gradlew.bat clean build`

### Запуск демо-классов

1. После сборки скомпилированные классы располагаются в каталоге `build/classes/java/main`.

2. Запускайте команды из корня проекта.
   После параметра `-cp` указывается каталог со скомпилированными классами,
   а затем — полное имя запускаемого класса без расширения `.java`.

   Полное имя класса определяется по пути к исходному файлу после каталога `src/main/java`:

   ```text
   src/main/java/org/study/bootcamp/mishustin/task14/lambda/spellcasting/v1/api/cli/Demo.java
   ```

   В пути замените разделители каталогов на точки и удалите расширение `.java`:

   ```text
   org.study.bootcamp.mishustin.task14.lambda.spellcasting.v1.api.cli.Demo
   ```

   **Linux/macOS:**
   ```bash
   java -cp build/classes/java/main org.study.bootcamp.mishustin.task14.lambda.spellcasting.v1.api.cli.Demo
   ```

   **Windows (PowerShell):**

   ```powershell
   java -cp "./build/classes/java/main" org.study.bootcamp.mishustin.task14.lambda.spellcasting.v1.api.cli.Demo
   ```

3. Для запуска другой задачи замените полное имя класса, сохранив путь к каталогу со скомпилированными классами:

   **Linux/macOS:**

   ```bash
   java -cp build/classes/java/main org.study.bootcamp.mishustin.task<номер>.<пакеты>.Demo
   ```

   **Windows (PowerShell):**

   ```powershell
   java -cp "./build/classes/java/main" org.study.bootcamp.mishustin.task<номер>.<пакеты>.Demo
   ```

4. Если точкой входа является упрощенная версия в пакете `easy`, запускайте класс `Main`. Например:

   **Linux/macOS:**

   ```bash
   java -cp build/classes/java/main org.study.bootcamp.mishustin.task1.group.users.by.age.v6.easy.Main
   ```

   **Windows (PowerShell):**

   ```powershell
   java -cp "./build/classes/java/main" org.study.bootcamp.mishustin.task1.group.users.by.age.v6.easy.Main
   ```

## Структура проекта

### Нейминг и слои

Основной учебный код лежит в `src/main/java/org/study/bootcamp/mishustin`.
Пакеты задач имеют формат `taskN/topic/words/vX/...`, где:

- `taskN` - номер учебной задачи;
- `topic/words` - человекочитаемое имя задачи в пакетном стиле;
- `vX` - версия решения или итерация упражнения;
- `api/cli` - консольная точка входа, обычно `Demo`;
- `application` - сервисы, стратегии, сценарии и прикладная логика;
- `domain` - модели и контракты предметной области;
- `infrastructure` - работа с внешними источниками, файлами, консолью;
- `easy` - упрощенная учебная версия с точкой входа `Main`.

## Учебные датасеты

В `src/main/resources` лежат вспомогательные данные для задач:

- `user_profiles.json` - профили пользователей;
- `products.json` - каталог товаров;
- `product_orders.json` - заказы товаров;
- `vacancies.json` - вакансии для аналитики;
- `students_test_data.json` - тестовые данные студентов;
- `environmental_impact_100.csv` - экологические показатели компаний.

Эти файлы используются для упражнений по JSON/CSV-загрузке, фильтрации, агрегации, рекомендациям и отчетам.

## Зависимости

Проект собирается Gradle-плагином `java` и использует:

- SLF4J и Logback для логирования;
- Lombok для генерации шаблонного кода;
- Apache Commons Lang3 для утилит;
- Gson для JSON;
- JUnit Jupiter и Mockito для тестирования.

Версии указаны в `build.gradle` и подтягиваются из Maven Central.

## Тестирование

Структура `src/test` заведена, но тест-кейсы могут добавляться постепенно.
Команду тестов можно использовать как проверку компиляции и готовую точку для будущих unit-тестов:

- Linux/macOS:        `./gradlew test`
- Windows PowerShell: `./gradlew.bat test`

## Полезные материалы

В `src/main/java/org/study/bootcamp/mishustin/info/HELPME.md` собрана
памятка по темам буткемпа, ссылкам на видео и советам по решению задач.
Используйте ее как чек-лист при выполнении практики и повторении тем.


# JavaRush

...