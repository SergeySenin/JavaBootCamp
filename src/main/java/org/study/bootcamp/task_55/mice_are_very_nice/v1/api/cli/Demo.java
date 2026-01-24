package org.study.bootcamp.task_55.mice_are_very_nice.v1.api.cli;

import org.study.bootcamp.task_55.mice_are_very_nice.v1.application.service.HouseFoodCollector;
import org.study.bootcamp.task_55.mice_are_very_nice.v1.domain.model.Food;
import org.study.bootcamp.task_55.mice_are_very_nice.v1.domain.model.House;
import org.study.bootcamp.task_55.mice_are_very_nice.v1.domain.model.Room;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Demo {

    private static final int THREADS_COUNT = 5;
    private static final long COLLECT_INTERVAL_SECONDS = 30;

    private static final long AWAIT_TIMEOUT = 10;
    private static final TimeUnit AWAIT_TIME_UNIT = TimeUnit.MINUTES;

    public static void main(String[] args) {
        House house = createHouse();

        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(THREADS_COUNT);

        HouseFoodCollector collector = new HouseFoodCollector(
                THREADS_COUNT,
                COLLECT_INTERVAL_SECONDS,
                AWAIT_TIMEOUT,
                AWAIT_TIME_UNIT
        );

        collector.collectAllFood(house, scheduledExecutorService);

        System.out.printf(
                "Еда в доме собрана! Собрано всего: %d%n",
                house.getCollectedFoodsCount()
        );
    }

    private static House createHouse() {
        Room kitchen = new Room(
                "Кухня",
                List.of(new Food("Сыр"), new Food("Хлеб"), new Food("Яблоко"))
        );
        Room livingRoom = new Room(
                "Гостиная",
                List.of(new Food("Печенье"), new Food("Конфета"))
        );
        Room pantry = new Room(
                "Кладовка",
                List.of(new Food("Колбаса"), new Food("Сухарики"), new Food("Орехи"))
        );
        Room bedroom = new Room(
                "Спальня",
                List.of(new Food("Шоколад"))
        );

        return new House(List.of(kitchen, livingRoom, pantry, bedroom));
    }
}
