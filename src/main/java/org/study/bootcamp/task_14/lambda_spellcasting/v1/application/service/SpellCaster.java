package org.study.bootcamp.task_14.lambda_spellcasting.v1.application.service;

import org.study.bootcamp.task_14.lambda_spellcasting.v1.domain.contract.SpellAction;

public final class SpellCaster {

    public void cast(String spellName, SpellAction action) {
        requireNonBlank(spellName, "spellName");
        if (action == null) {
            throw new IllegalArgumentException("action: must not be null");
        }
        String result = action.castSpell(spellName.trim());
        System.out.println(result);
    }

    private static void requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + ": must not be null/blank");
        }
    }
}
