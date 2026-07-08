package org.study.bootcamp.task10.weathercache.v2.easy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Main {
    interface WeatherProvider {
        WeatherData fetchWeatherData(String city);
    }

    static class WeatherData {
        String city;
        double temperature;
        double humidity;
        long timestamp;

        public WeatherData(String city, double temperature, double humidity) {
            this.city = city;
            this.temperature = temperature;
            this.humidity = humidity;
            this.timestamp = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return city + ": " + temperature + "°C, влажность " + humidity + "%";
        }
    }

    static class WeatherService implements WeatherProvider {
        private Random random = new Random();

        @Override
        public WeatherData fetchWeatherData(String city) {
            double temp = 15 + random.nextInt(25);
            double humidity = 30 + random.nextInt(50);
            System.out.println("🔄 Запрошены новые данные для " + city);
            return new WeatherData(city, temp, humidity);
        }
    }

    abstract static class WeatherCacheTemplate {
        protected Map<String, WeatherData> cache = new HashMap<>();
        protected WeatherProvider provider;

        public WeatherCacheTemplate(WeatherProvider provider) {
            this.provider = provider;
        }

        public abstract boolean isCacheExpired(WeatherData data, long maxCacheAgeMillis);

        public WeatherData getWeatherData(String city, long maxCacheAgeMillis) {
            WeatherData cachedData = cache.get(city);

            if (cachedData != null && !isCacheExpired(cachedData, maxCacheAgeMillis)) {
                System.out.println("✅ Данные из кэша: " + cachedData);
                return cachedData;
            }

            WeatherData freshData = provider.fetchWeatherData(city);
            cache.put(city, freshData);
            System.out.println("🆕 Новые данные: " + freshData);
            return freshData;
        }

        public void forceUpdateWeather(String city) {
            WeatherData freshData = provider.fetchWeatherData(city);
            cache.put(city, freshData);
            System.out.println("🔁 Принудительно обновлено: " + freshData);
        }

        public void clearExpiredCache(long maxCacheAgeMillis) {
            Iterator<Map.Entry<String, WeatherData>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, WeatherData> entry = iterator.next();
                if (isCacheExpired(entry.getValue(), maxCacheAgeMillis)) {
                    System.out.println("🗑️ Удалены устаревшие данные для: " + entry.getKey());
                    iterator.remove();
                }
            }
        }
    }

    static class StandardWeatherCache extends WeatherCacheTemplate {
        public StandardWeatherCache(WeatherProvider provider) {
            super(provider);
        }

        @Override
        public boolean isCacheExpired(WeatherData data, long maxCacheAgeMillis) {
            long currentTime = System.currentTimeMillis();
            long dataAge = currentTime - data.timestamp;
            return dataAge > maxCacheAgeMillis;
        }
    }

    static class FrequentUpdateWeatherCache extends WeatherCacheTemplate {
        public FrequentUpdateWeatherCache(WeatherProvider provider) {
            super(provider);
        }

        @Override
        public boolean isCacheExpired(WeatherData data, long maxCacheAgeMillis) {
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WeatherService service = new WeatherService();

        System.out.println("=== СТАНДАРТНЫЙ КЭШ (5 секунд) ===");
        StandardWeatherCache standardCache = new StandardWeatherCache(service);

        standardCache.getWeatherData("Москва", 5000);

        Thread.sleep(1000);
        standardCache.getWeatherData("Москва", 5000);

        Thread.sleep(6000);
        standardCache.getWeatherData("Москва", 5000);

        System.out.println("\n=== ЧАСТОЕ ОБНОВЛЕНИЕ ===");
        FrequentUpdateWeatherCache frequentCache = new FrequentUpdateWeatherCache(service);

        frequentCache.getWeatherData("Лондон", 10000);
        frequentCache.getWeatherData("Лондон", 10000);

        System.out.println("\n=== ПРИНУДИТЕЛЬНОЕ ОБНОВЛЕНИЕ ===");
        standardCache.forceUpdateWeather("Париж");
        standardCache.forceUpdateWeather("Париж");

        System.out.println("\n=== ОЧИСТКА КЭША ===");
        standardCache.forceUpdateWeather("Берлин");
        Thread.sleep(6000);
        standardCache.clearExpiredCache(5000);
    }
}



/*
1. Чем отличается интерфейс от абстрактного класса?
Интерфейс WeatherProvider
java
interface WeatherProvider {
    WeatherData fetchWeatherData(String city);
}

Когда применять: Когда нужно определить контракт ("что делать"), но не "как делать"
Только объявление методов (без реализации)
Класс может реализовать много интерфейсов
В нашей задаче: Определяет, что любой поставщик погоды ДОЛЖЕН уметь получать данные

Абстрактный класс WeatherCacheTemplate
java
abstract class WeatherCacheTemplate {
    // Поля с состоянием
    protected Map<String, WeatherData> cache = new HashMap<>();
    protected WeatherProvider provider;

    // Реализованные методы
    public WeatherData getWeatherData(String city, long maxCacheAgeMillis) { ... }

    // Абстрактный метод (без реализации)
    public abstract boolean isCacheExpired(WeatherData data, long maxCacheAgeMillis);
}

Когда применять: Когда есть общая логика, но некоторые части должны быть разными
Может содержать поля и реализованные методы
Определяет шаблон алгоритма
В нашей задаче: Реализует общую логику кэширования, но политику устаревания оставляет на наследников.

2. Как работает итератор в этом коде:
1) Создание итератора:
java
Iterator<Map.Entry<String, WeatherData>> iterator = cache.entrySet().iterator();

Что происходит:
cache.entrySet() - возвращает множество всех пар "город → данные погоды"
.iterator() - создаёт итератор для обхода этого множества
Аналогия: Итератор как "указка", которая перемещается по элементам коллекции.

2. Цикл while с итератором:
java
while (iterator.hasNext()) {
    // Проверяем, есть ли следующий элемент
}

hasNext() - возвращает true, если в коллекции есть ещё элементы для обхода.

3. Получение текущего элемента:
java
Map.Entry<String, WeatherData> entry = iterator.next();

next() делает три вещи:
Перемещает итератор на следующий элемент
Возвращает текущий элемент
Запоминает, какой элемент сейчас "под курсором"

4. Безопасное удаление:
java
if (isCacheExpired(entry.getValue(), maxCacheAgeMillis)) {
    iterator.remove();  // ← Ключевой момент!
}

iterator.remove() - удаляет текущий элемент (тот, который был возвращён последним вызовом next())
 */
