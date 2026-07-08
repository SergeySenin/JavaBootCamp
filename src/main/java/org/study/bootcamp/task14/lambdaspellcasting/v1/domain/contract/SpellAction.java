package org.study.bootcamp.task14.lambdaspellcasting.v1.domain.contract;

@FunctionalInterface
public interface SpellAction {
    String castSpell(String spellName);
}
