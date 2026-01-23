package org.study.bootcamp.task_47.spacex_program.v1.domain.model;

public class RocketLaunch {

    private final String name;
    private final long launchTimeMillis;

    public RocketLaunch(String name, long launchTimeMillis) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название запуска не должно быть пустым");
        }
        if (launchTimeMillis < 0) {
            throw new IllegalArgumentException("Время запуска не может быть отрицательным");
        }

        this.name = name;
        this.launchTimeMillis = launchTimeMillis;
    }

    public String getName() {
        return name;
    }

    public long getLaunchTimeMillis() {
        return launchTimeMillis;
    }

    public void launch() throws InterruptedException {
        Thread.sleep(1000L);
        System.out.printf("Ракета запускается: %s%n", name);
    }
}
