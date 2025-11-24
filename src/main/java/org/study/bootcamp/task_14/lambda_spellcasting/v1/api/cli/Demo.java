package org.study.bootcamp.task_14.lambda_spellcasting.v1.api.cli;

import org.study.bootcamp.task_14.lambda_spellcasting.v1.application.service.SpellCaster;

public class Demo {
    public static void main(String[] args) {

        SpellCaster spellCaster = new SpellCaster();

        String alohomora    = "Alohomora";
        String lumos        = "Lumos";
        String expelliarmus = "Expelliarmus";

        spellCaster.cast(alohomora,    spell -> "The door is unlocked by "       + spell);
        spellCaster.cast(lumos,        spell -> "A beam of light is created by " + spell);
        spellCaster.cast(expelliarmus, spell -> "The opponent is disarmed by "   + spell);

        spellCaster.cast("Wingardium Leviosa", spell -> spell + " levitates the feather gracefully");
        spellCaster.cast("Protego", spell -> String.format("%s summons a protective shield", spell));
    }
}
