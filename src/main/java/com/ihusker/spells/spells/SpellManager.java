package com.ihusker.spells.spells;

import com.ihusker.spells.wands.Wand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SpellManager {

    private final Plugin plugin;
    private final Map<Integer, Spell> spellTask;

    public SpellManager(Plugin plugin) {
        this.plugin = plugin;
        this.spellTask = new HashMap<>();
    }

    public void clean() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, ()-> {
            Iterator<Map.Entry<Integer, Spell>> iterator = spellTask.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Spell> entry = iterator.next();
                if(entry.getValue().isFinished()) {
                    plugin.getServer().getScheduler().cancelTask(entry.getKey());
                    iterator.remove();
                }
            }
        }, 0L, 20L);
    }


    public void cast(Player player, Wand wand) {
        if(wand.cast()) {

        } else {
            Spell spell = wand.getActiveSpell().getSpell().clone(player.getUniqueId());
            int id = plugin.getServer().getScheduler().runTaskTimer(plugin, ()-> spell.process(player), 0L, 0L).getTaskId();
            spellTask.put(id, spell);
        }
    }
}
