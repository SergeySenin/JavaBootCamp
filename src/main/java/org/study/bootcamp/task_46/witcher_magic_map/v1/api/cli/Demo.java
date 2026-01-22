package org.study.bootcamp.task_46.witcher_magic_map.v1.api.cli;

import org.study.bootcamp.task_46.witcher_magic_map.v1.application.service.RouteResult;
import org.study.bootcamp.task_46.witcher_magic_map.v1.application.service.WitcherRoutePlanner;
import org.study.bootcamp.task_46.witcher_magic_map.v1.domain.model.City;
import org.study.bootcamp.task_46.witcher_magic_map.v1.domain.model.Location;
import org.study.bootcamp.task_46.witcher_magic_map.v1.domain.model.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Demo {

    public static void main(String[] args) {
        List<City> citiesForDemo = List.of(
                new City("Новиград", new Location(10, 6)),
                new City("Оксенфурт", new Location(18, 3)),
                new City("Каэр Трольде", new Location(-7, 14))
        );

        List<Monster> monstersForDemo = List.of(
                new Monster("Утопец", new Location(12, 8)),
                new Monster("Накер", new Location(20, 2)),
                new Monster("Грифон", new Location(-9, 13)),
                new Monster("Полуденница", new Location(9, 5))
        );

        WitcherRoutePlanner planner = new WitcherRoutePlanner();

        System.out.println("---- Демонстрация маршрутов (с выводом) ----");
        RouteResult bestDemoRoute = planner.findFastestRouteWithThreadPool(citiesForDemo, monstersForDemo, 3, true);
        printBestRoute(bestDemoRoute);

        System.out.println("---- Сравнение времени (без вывода) ----");
        List<City> benchmarkCities = generateCities(300);
        List<Monster> benchmarkMonsters = generateMonsters(20_000);

        // NEW: замер времени через System.currentTimeMillis().
        benchmark(planner, benchmarkCities, benchmarkMonsters, 1);
        benchmark(planner, benchmarkCities, benchmarkMonsters, 2);
        benchmark(planner, benchmarkCities, benchmarkMonsters, 4);
        benchmark(planner, benchmarkCities, benchmarkMonsters, 8);
    }

    private static void benchmark(
            WitcherRoutePlanner planner,
            List<City> cities,
            List<Monster> monsters,
            int threadsCount
    ) {
        long startTimeMillis = System.currentTimeMillis(); // NEW

        if (threadsCount == 1) {
            planner.findFastestRouteSingleThread(cities, monsters, false);
        } else {
            planner.findFastestRouteWithThreadPool(cities, monsters, threadsCount, false);
        }

        long endTimeMillis = System.currentTimeMillis(); // NEW
        System.out.printf("%d поток(а/ов): %d мс%n", threadsCount, (endTimeMillis - startTimeMillis));
    }

    private static void printBestRoute(RouteResult routeResult) {
        System.out.printf(
                "Самый быстрый заказ: %s → %s (итого %.2f)%n",
                routeResult.getCity().getName(),
                routeResult.getMonster().getName(),
                routeResult.getTotalDistance()
        );
    }

    private static List<City> generateCities(int count) {
        List<City> cities = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            int x = ThreadLocalRandom.current().nextInt(-1000, 1001);
            int y = ThreadLocalRandom.current().nextInt(-1000, 1001);
            cities.add(new City("Город-" + (index + 1), new Location(x, y)));
        }
        return cities;
    }

    private static List<Monster> generateMonsters(int count) {
        List<Monster> monsters = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            int x = ThreadLocalRandom.current().nextInt(-1000, 1001);
            int y = ThreadLocalRandom.current().nextInt(-1000, 1001);
            monsters.add(new Monster("Монстр-" + (index + 1), new Location(x, y)));
        }
        return monsters;
    }
}
