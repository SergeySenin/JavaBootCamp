package org.study.bootcamp.task_54.tamagotchi_vlad.v1.domain.model;

public class TamagotchiVlad {

    private final String name;

    private int satiety;
    private int mood;
    private int hygiene;
    private int energy;

    public TamagotchiVlad(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя тамагочи не должно быть пустым");
        }

        this.name = name;
        this.satiety = 60;
        this.mood = 60;
        this.hygiene = 60;
        this.energy = 60;
    }

    public String getName() {
        return name;
    }

    public synchronized void feed() {
        satiety = clamp(satiety + 25);
        mood = clamp(mood + 5);
        hygiene = clamp(hygiene - 5);

        printActionWithState("ест");
    }

    public synchronized void play() {
        mood = clamp(mood + 25);
        energy = clamp(energy - 20);
        satiety = clamp(satiety - 10);
        hygiene = clamp(hygiene - 10);

        printActionWithState("играет");
    }

    public synchronized void clean() {
        hygiene = clamp(hygiene + 35);
        mood = clamp(mood - 5);

        printActionWithState("моется");
    }

    public synchronized void sleep() {
        energy = clamp(energy + 35);
        satiety = clamp(satiety - 5);
        mood = clamp(mood - 5);

        printActionWithState("спит");
    }

    private void printActionWithState(String action) {
        System.out.printf(
                "%s %s. Состояние: %s%n",
                name,
                action,
                buildStateMessage()
        );
    }

    private String buildStateMessage() {
        return String.format(
                "%s, %s, %s, %s (сытость=%d, настроение=%d, чистота=%d, энергия=%d)",
                satietyStatus(),
                moodStatus(),
                hygieneStatus(),
                energyStatus(),
                satiety,
                mood,
                hygiene,
                energy
        );
    }

    private String satietyStatus() {
        if (satiety >= 70) return "сыт";
        if (satiety >= 40) return "нормально поел";
        return "голоден";
    }

    private String moodStatus() {
        if (mood >= 70) return "весёл";
        if (mood >= 40) return "спокоен";
        return "в стрессе";
    }

    private String hygieneStatus() {
        if (hygiene >= 70) return "чистый";
        if (hygiene >= 40) return "терпимо";
        return "грязный";
    }

    private String energyStatus() {
        if (energy >= 70) return "бодр";
        if (energy >= 40) return "нормально";
        return "устал";
    }

    private int clamp(int value) {
        if (value < 0) return 0;
        return Math.min(value, 100);
    }
}
