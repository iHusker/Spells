package com.ihusker.spells.wands;

import com.ihusker.spells.spells.SpellType;

import java.util.*;

public class Wand {

    private final UUID uuid;
    private final LinkedList<SpellType> spellTypes;
    private final Map<SpellType, Long> spellNavigation;

    private SpellType activeSpell;

    public Wand(UUID uuid){
        this.uuid = uuid;

        this.spellTypes = new LinkedList<>();
        this.spellNavigation = new HashMap<>();
    }

    public void add(SpellType spellType) {
        if(activeSpell == null) activeSpell = spellType;
        spellTypes.add(spellType);
    }

    public SpellType change() {
        if(activeSpell == null) return null;
        if(spellTypes.getLast() == activeSpell) {
            activeSpell = spellTypes.getFirst();
        } else {
            int index = spellTypes.indexOf(activeSpell);
            activeSpell = spellTypes.get(index + 1);
        }

        return activeSpell;
    }

    public long getCooldown() {
        return spellNavigation.get(activeSpell) - (System.currentTimeMillis() / 1000);
    }

    public boolean cast() {
        if(activeSpell == null) return false;
        if(spellNavigation.containsKey(activeSpell)) {
            if(spellNavigation.get(activeSpell) > System.currentTimeMillis() / 1000) return true;
        }
        spellNavigation.put(activeSpell, activeSpell.getDuration() + (System.currentTimeMillis() / 1000));
        return false;
    }

    public SpellType getActiveSpell() {
        return activeSpell;
    }

    public UUID getUniqueId() {
        return uuid;
    }
}
