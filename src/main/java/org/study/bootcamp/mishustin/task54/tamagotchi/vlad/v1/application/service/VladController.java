package org.study.bootcamp.mishustin.task54.tamagotchi.vlad.v1.application.service;

import org.study.bootcamp.mishustin.task54.tamagotchi.vlad.v1.domain.model.TamagotchiVlad;

import java.util.ArrayList;
import java.util.List;

public class VladController {

    private final Object lock = new Object();
    private final List<TamagotchiVlad> vlads = new ArrayList<>();

    public void addVlad(TamagotchiVlad vlad) {
        if (vlad == null) {
            throw new IllegalArgumentException("vlad не должен быть null");
        }

        synchronized (lock) {
            for (TamagotchiVlad existingVlad : vlads) {
                if (existingVlad.getName().equals(vlad.getName())) {
                    throw new IllegalStateException("Влад с таким именем уже добавлен: " + vlad.getName());
                }
            }
            vlads.add(vlad);
        }
    }

    public void removeVladByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя для удаления не должно быть пустым");
        }

        synchronized (lock) {
            boolean removed = vlads.removeIf(vlad -> vlad.getName().equals(name));
            if (!removed) {
                throw new IllegalStateException("Влад для удаления не найден: " + name);
            }
        }
    }

    public void feedAll() {
        List<TamagotchiVlad> snapshot = snapshotVlads();
        for (TamagotchiVlad vlad : snapshot) {
            vlad.feed();
        }
    }

    public void playAll() {
        List<TamagotchiVlad> snapshot = snapshotVlads();
        for (TamagotchiVlad vlad : snapshot) {
            vlad.play();
        }
    }

    public void cleanAll() {
        List<TamagotchiVlad> snapshot = snapshotVlads();
        for (TamagotchiVlad vlad : snapshot) {
            vlad.clean();
        }
    }

    public void sleepAll() {
        List<TamagotchiVlad> snapshot = snapshotVlads();
        for (TamagotchiVlad vlad : snapshot) {
            vlad.sleep();
        }
    }

    private List<TamagotchiVlad> snapshotVlads() {
        synchronized (lock) {
            return new ArrayList<>(vlads);
        }
    }
}
