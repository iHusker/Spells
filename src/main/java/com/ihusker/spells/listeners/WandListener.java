package com.ihusker.spells.listeners;

import com.ihusker.spells.Source;
import com.ihusker.spells.spells.SpellManager;
import com.ihusker.spells.spells.SpellType;
import com.ihusker.spells.wands.Wand;
import com.ihusker.spells.wands.WandManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandListener implements Listener {

    private final WandManager wandManager;
    private final SpellManager spellManager;

    public WandListener(Source source) {
        this.wandManager = source.getWandManager();
        this.spellManager = source.getSpellManager();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        wandManager.remove(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        wandManager.add(event.getPlayer());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack == null) return;
        if (!wandManager.isWand(event.getPlayer(), wandManager.wandMeta(itemStack))) return;

        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            SpellType spellType = wandManager.getWand(player).change();
        }

        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            spellManager.cast(player, wandManager.getWand(player));
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemMeta itemMeta = wandManager.wandMeta(event.getItemDrop().getItemStack());
        if(itemMeta == null) return;
        if(wandManager.isWand(event.getPlayer(), itemMeta)) event.setCancelled(true);
    }

    @EventHandler
    public void onMenu(PlayerSwapHandItemsEvent event) {
        if(event.getOffHandItem() == null) return;
        ItemMeta itemMeta = wandManager.wandMeta(event.getOffHandItem());
        if(wandManager.isWand(event.getPlayer(), itemMeta)) event.setCancelled(true);
    }
}
