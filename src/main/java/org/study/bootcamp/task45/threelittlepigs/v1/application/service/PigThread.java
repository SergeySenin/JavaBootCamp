package org.study.bootcamp.task45.threelittlepigs.v1.application.service;

public abstract class PigThread extends Thread {

    private final String pigName;
    private final String material;

    protected PigThread(String pigName, String material) {
        if (pigName == null || pigName.isBlank()) {
            throw new IllegalArgumentException("Имя поросёнка не должно быть пустым");
        }
        if (material == null || material.isBlank()) {
            throw new IllegalArgumentException("Материал строительства не должен быть пустым");
        }

        this.pigName = pigName;
        this.material = material;
        setName(pigName);
    }

    public String getPigName() {
        return pigName;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public final void run() {
        buildHouse();
    }

    protected abstract long getBuildDelayMillis();

    protected void buildHouse() {
        System.out.printf("%s строит дом из %s%n", pigName, material);

        try {
            Thread.sleep(getBuildDelayMillis());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.printf("%s прерван во время строительства (материал: %s)%n", pigName, material);
            return;
        }

        System.out.printf("%s закончил дом из %s%n", pigName, material);
    }
}
