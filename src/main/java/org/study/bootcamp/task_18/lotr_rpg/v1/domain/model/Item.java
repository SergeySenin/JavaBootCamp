package org.study.bootcamp.task_18.lotr_rpg.v1.domain.model;

import org.apache.commons.lang3.Validate;

public record Item(String name, int value) {

    public Item(String name, int value) {
        Validate.notBlank(name, "name: must not be null/blank");
        if (value < 0) {
            throw new IllegalArgumentException("value: must be >= 0");
        }

        this.name = name.trim();
        this.value = value;
    }
}
