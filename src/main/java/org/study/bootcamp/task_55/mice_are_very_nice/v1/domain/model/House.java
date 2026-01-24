package org.study.bootcamp.task_55.mice_are_very_nice.v1.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class House {

    private final Object lock = new Object();

    private final List<Room> rooms;
    private final List<Food> collectedFoods = new ArrayList<>();

    private boolean isFinished;

    public House(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            throw new IllegalArgumentException("Список комнат не должен быть пустым");
        }
        if (rooms.size() < 2) {
            throw new IllegalArgumentException("Нужно минимум 2 комнаты");
        }

        this.rooms = new ArrayList<>(rooms);

        synchronized (lock) {
            this.isFinished = areAllRoomsEmpty();
        }
    }

    public void collectFood() {
        String logMessage;

        synchronized (lock) {
            if (isFinished) {
                return;
            }

            int firstIndex = ThreadLocalRandom.current().nextInt(rooms.size());
            int secondIndex = pickOtherIndex(firstIndex);

            Room firstRoom = rooms.get(firstIndex);
            Room secondRoom = rooms.get(secondIndex);

            int collectedFromFirst = firstRoom.drainFoodsTo(collectedFoods);
            int collectedFromSecond = secondRoom.drainFoodsTo(collectedFoods);

            int collectedNow = collectedFromFirst + collectedFromSecond;

            isFinished = areAllRoomsEmpty();

            logMessage = String.format(
                    "[%s] Собрано %d (комнаты '%s' и '%s'). Всего собрано: %d",
                    Thread.currentThread().getName(),
                    collectedNow,
                    firstRoom.getName(),
                    secondRoom.getName(),
                    collectedFoods.size()
            );
        }

        System.out.println(logMessage);
    }

    public boolean isFinished() {
        synchronized (lock) {
            return isFinished;
        }
    }

    public int getCollectedFoodsCount() {
        synchronized (lock) {
            return collectedFoods.size();
        }
    }

    private int pickOtherIndex(int firstIndex) {
        int index = ThreadLocalRandom.current().nextInt(rooms.size() - 1);
        if (index >= firstIndex) {
            index++;
        }
        return index;
    }

    private boolean areAllRoomsEmpty() {
        for (Room room : rooms) {
            if (!room.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
