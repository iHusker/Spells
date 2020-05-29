package com.ihusker.spells.spells.types;

import com.ihusker.spells.spells.Spell;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Episky extends Spell {

    public Episky() {
        super(2.0f, 0.0f);
    }

    @Override
    public void update(Player player) {
        location = player.getEyeLocation().toVector();
        direction = player.getLocation().getDirection().normalize();

        float radius = 2.0f;

        for(int i = 0; i < 5; i++) {
            origin = new Vector(
                    location.getX() + (Math.cos(i) * radius),
                    location.getY() * radius,
                    location.getZ() + (Math.sin(i) * radius)
            );
        }
    }

    @Override
    public void onCollide(Object object) {

    }

    @Override
    public void onParticle(World world) {
        world.spawnParticle(Particle.REDSTONE, location.toLocation(world), 1, new Particle.DustOptions(Color.fromRGB(2, 124, 207), 2.0f));
        world.spawnParticle(Particle.REDSTONE, location.toLocation(world), 2, new Particle.DustOptions(Color.fromRGB(73, 167, 230), 1.0f));
    }

}
