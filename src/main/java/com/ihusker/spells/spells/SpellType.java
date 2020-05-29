package com.ihusker.spells.spells;


import com.ihusker.spells.spells.types.Aguamenti;
import com.ihusker.spells.spells.types.Bombarda;
import com.ihusker.spells.spells.types.Episky;

public enum SpellType {
    AGUAMENTI(10, new Aguamenti()),
    BOMBARDA(3, new Bombarda()),
    EPISKY(8, new Episky());

    private final int duration;
    private final Spell spell;

    SpellType(int duration, Spell spell) {
        this.duration = duration;
        this.spell = spell;

    }

    public int getDuration() {
        return duration;
    }

    public Spell getSpell() {
        return spell;
    }
}
