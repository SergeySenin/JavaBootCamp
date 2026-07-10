package org.study.bootcamp.mishustin.task46.witcher.magic.map.v1.application.service;

import org.study.bootcamp.mishustin.task46.witcher.magic.map.v1.domain.model.City;
import org.study.bootcamp.mishustin.task46.witcher.magic.map.v1.domain.model.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class WitcherRoutePlanner {

    // NEW: таймаут ожидания завершения пула, чтобы не зависнуть навсегда при баге/зависании задач.
    private static final long TERMINATION_TIMEOUT = 1;
    private static final TimeUnit TERMINATION_TIME_UNIT = TimeUnit.MINUTES;

    public RouteResult findFastestRouteSingleThread(
            List<City> cities,
            List<Monster> monsters,
            boolean isLoggingEnabled
    ) {
        validateInput(cities, monsters);

        List<CityWorker> workers = new ArrayList<>(cities.size());
        for (City city : cities) {
            CityWorker worker = new CityWorker(city, monsters, isLoggingEnabled);
            workers.add(worker);
            worker.run();
        }

        return selectBestRouteOrThrow(workers);
    }

    public RouteResult findFastestRouteWithThreadPool(
            List<City> cities,
            List<Monster> monsters,
            int threadsCount,
            boolean isLoggingEnabled
    ) {
        validateInput(cities, monsters);
        if (threadsCount <= 0) {
            throw new IllegalArgumentException("Количество потоков должно быть больше 0");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<CityWorker> workers = new ArrayList<>(cities.size());
        List<Future<?>> futures = new ArrayList<>(cities.size());

        try {
            for (City city : cities) {
                CityWorker worker = new CityWorker(city, monsters, isLoggingEnabled);
                workers.add(worker);
                futures.add(executorService.submit(worker));
            }

            // NEW: закрываем пул и ждём завершения с таймаутом (предусмотрительно).
            shutdownAndAwaitOrFail(executorService);

            // NEW: после awaitTermination проверяем Future, чтобы не потерять исключения из задач.
            validateFuturesOrFail(executorService, futures);

            return selectBestRouteOrThrow(workers);

        } finally {
            executorService.shutdown();
        }
    }

    private void validateInput(List<City> cities, List<Monster> monsters) {
        if (cities == null || cities.isEmpty()) {
            throw new IllegalArgumentException("Список городов не должен быть пустым");
        }
        if (monsters == null || monsters.isEmpty()) {
            throw new IllegalArgumentException("Список монстров не должен быть пустым");
        }
    }

    private void shutdownAndAwaitOrFail(ExecutorService executorService) {
        executorService.shutdown();

        try {
            boolean finished = executorService.awaitTermination(TERMINATION_TIMEOUT, TERMINATION_TIME_UNIT);
            if (!finished) {
                executorService.shutdownNow(); // NEW: принудительное завершение при таймауте.
                throw new IllegalStateException("Задачи не завершились за отведённое время");
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt(); // NEW: не теряем факт прерывания.
            executorService.shutdownNow();
            throw new IllegalStateException("Ожидание завершения задач прервано", interruptedException);
        }
    }

    private void validateFuturesOrFail(ExecutorService executorService, List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                executorService.shutdownNow();
                throw new IllegalStateException("Проверка результатов прервана", interruptedException);
            } catch (ExecutionException executionException) {
                executorService.shutdownNow();
                throw new RuntimeException("Ошибка во время вычислений", executionException.getCause());
            }
        }
    }

    private RouteResult selectBestRouteOrThrow(List<CityWorker> workers) {
        RouteResult bestRoute = null;

        for (CityWorker worker : workers) {
            RouteResult routeResult = worker.getRouteResult();
            if (routeResult == null) {
                throw new IllegalStateException("Результат маршрута не рассчитан");
            }

            if (bestRoute == null || routeResult.getTotalDistance() < bestRoute.getTotalDistance()) {
                bestRoute = routeResult;
            }
        }

        return bestRoute;
    }
}
