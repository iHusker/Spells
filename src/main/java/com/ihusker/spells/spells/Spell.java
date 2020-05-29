package com.ihusker.spells.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public abstract class Spell implements Cloneable {

    protected UUID uuid;

    protected final float duration, speed;
    protected float time = 0.0f;

    protected boolean finished = false;

    protected Vector origin, location, direction;

    protected Spell(float duration, float speed) {
        this.duration = duration;
        this.speed = speed;
    }

    public void process(Player player) {
        update(player);
        time += speed;

        location.add(origin);

        World world = player.getWorld();
        Location worldLocation = location.toLocation(world);

        onParticle(world);

        Block block = worldLocation.getBlock();
        if(block.getType() != Material.AIR) onCollide(block);

        for (Entity entity : worldLocation.getChunk().getEntities()) {
            if (entity.getLocation().distanceSquared(worldLocation) <= 4.0f) onCollide(entity);
        }

        location.subtract(origin);

        if(time > duration) finished = true;
    }

    public Spell clone(UUID uuid) {
        try {
            Spell spell = (Spell) super.clone();
            spell.uuid = uuid;
            return spell;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public abstract void update(Player player);

    public abstract void onCollide(Object object);

    public abstract void onParticle(World world);
}
