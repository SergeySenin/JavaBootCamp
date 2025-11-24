package org.study.bootcamp.task_7.tracking_magical_events.v1.api.cli;

import org.study.bootcamp.task_7.tracking_magical_events.v1.application.service.SpellCatalogService;

public class Demo {
    public static void main(String[] args) {
        SpellCatalogService spells = new SpellCatalogService();

        spells.addSpellEvent("Aa", "Aaa");
        spells.addSpellEvent("Bb", "Bbb");
        spells.addSpellEvent("Cc", "Ccc");
        spells.addSpellEvent("Aa", "Aab");

        System.out.println("\nAll events:");
        spells.printAllSpellEvents();

        System.out.println("\nBy type 'Aa':");
        spells.getSpellEventsByType("Aa").forEach(System.out::println);

        System.out.println("\nBy ID=2:");
        System.out.println(spells.getSpellEventById(2));

        System.out.println("\nDeleting ID=1 and printing again:");
        spells.deleteSpellEvent(1);
        spells.printAllSpellEvents();
    }
}
