package org.study.bootcamp.task_18.lotr_rpg.v1.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.Validate;

import java.util.*;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Character {

    @EqualsAndHashCode.Include
    private final String name;
    private final List<Item> inventory = new ArrayList<>();

    public Character(String name) {
        Validate.notBlank(name, "name: must not be null/blank");

        this.name = name.trim();
    }
}
