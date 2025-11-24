package org.study.bootcamp.task_7.tracking_magical_events.v1.application.service;

import org.study.bootcamp.task_7.tracking_magical_events.v1.domain.model.SpellEvent;

import java.util.*;

public class SpellCatalogService {

    private final Map<Integer, SpellEvent> spellById = new HashMap<>();
    private final Map<String, List<SpellEvent>> spellsByType = new HashMap<>();
    private int nextId = 1;

    public void addSpellEvent(String eventType, String actionDescription) {
        validate(eventType, actionDescription);

        int id = nextId++;
        SpellEvent spellEvent = new SpellEvent(id, eventType.trim(), actionDescription.trim());

        spellById.put(id, spellEvent);
        spellsByType.computeIfAbsent(eventType, key -> new ArrayList<>()).add(spellEvent);
    }

    public SpellEvent getSpellEventById(int id) {
        return spellById.get(id);
    }

    public List<SpellEvent> getSpellEventsByType(String eventType) {
        if (eventType == null) return List.of();
        List<SpellEvent> list = spellsByType.get(eventType.trim());
        return (list == null) ? List.of() : new ArrayList<>(list);
    }

    public void deleteSpellEvent(int id) {
        SpellEvent removed = spellById.remove(id);
        if (removed == null) return;

        List<SpellEvent> list = spellsByType.get(removed.eventType());
        if (list != null) {
            list.remove(removed);
            if (list.isEmpty()) {
                spellsByType.remove(removed.eventType());
            }
        }
    }

    public void printAllSpellEvents() {
        if (spellById.isEmpty()) {
            System.out.println("No spell events yet!");
            return;
        }

        for (Map.Entry<Integer, SpellEvent> entry : spellById.entrySet()) {
            Integer id = entry.getKey();
            SpellEvent event = entry.getValue();
            System.out.printf("ID=%d | type=%s | action=%s%n",
                    id, event.eventType(), event.action());
        }
    }

    private static void validate(String eventType, String actionDescription) {
        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException("eventType: must not be null/blank");
        }
        if (actionDescription == null || actionDescription.isBlank()) {
            throw new IllegalArgumentException("actionDescription: must not be null/blank");
        }
    }
}
