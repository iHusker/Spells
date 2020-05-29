package com.ihusker.spells;

import com.ihusker.spells.listeners.WandListener;
import com.ihusker.spells.spells.SpellManager;
import com.ihusker.spells.wands.WandManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Source extends JavaPlugin {

    private final SpellManager spellManager = new SpellManager(this);
    private final WandManager wandManager = new WandManager(this);

    @Override
    public void onEnable() {
        spellManager.clean();
        registerListeners(new WandListener(this));
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public SpellManager getSpellManager() {
        return spellManager;
    }

    public WandManager getWandManager() {
        return wandManager;
    }
}
