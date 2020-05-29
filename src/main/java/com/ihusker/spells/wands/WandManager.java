package com.ihusker.spells.wands;

import com.ihusker.spells.spells.Spell;
import com.ihusker.spells.spells.SpellType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class WandManager implements Listener {

    private final Plugin plugin;
    private final Set<Wand> wands;

    public WandManager(Plugin plugin) {
        this.plugin = plugin;
        this.wands = new HashSet<>();
    }

    public void add(Player player) {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta != null) {
            NamespacedKey key = new NamespacedKey(plugin, "wand");
            itemMeta.setDisplayName("Wand");
            itemMeta.setLore(Collections.singletonList("Bound to " + player.getName()));
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, player.getUniqueId().toString());
            itemStack.setItemMeta(itemMeta);
        }
        player.getInventory().addItem(itemStack);

        Wand wand = new Wand(player.getUniqueId());
        for(SpellType spellType : SpellType.values()) {
            wand.add(spellType);
        }
        wands.add(wand);
    }

    public void remove(Player player) {
        Wand wand = getWand(player);
        wands.remove(wand);
    }

    public Wand getWand(Player player) {
        return wands.stream().filter(wand -> wand.getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public ItemMeta wandMeta(ItemStack itemStack) {
        if(itemStack.getType() != Material.STICK) return null;
        return itemStack.getItemMeta();
    }

    public boolean isWand(Player player, ItemMeta itemMeta) {
        if(itemMeta == null) return false;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "wand");
        String string = container.get(key, PersistentDataType.STRING);
        if(string == null) return false;
        UUID uuid = UUID.fromString(string);
        return uuid.equals(player.getUniqueId());
    }
}
