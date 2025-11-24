package org.study.bootcamp.task_14.lambda_spellcasting.v1.domain.contract;

@FunctionalInterface
public interface SpellAction {
    String castSpell(String spellName);
}
