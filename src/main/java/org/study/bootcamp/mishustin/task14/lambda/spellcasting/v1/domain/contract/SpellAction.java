package org.study.bootcamp.mishustin.task14.lambda.spellcasting.v1.domain.contract;

@FunctionalInterface
public interface SpellAction {
    String castSpell(String spellName);
}
