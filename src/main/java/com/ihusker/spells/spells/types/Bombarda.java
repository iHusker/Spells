package com.ihusker.spells.spells.types;

import com.ihusker.spells.spells.Spell;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Bombarda extends Spell {

    public Bombarda() {
        super(20.0f, 2.0f);
    }

    @Override
    public void update(Player player) {
        if(time == 0) {
            location = player.getEyeLocation().toVector();
            direction = player.getLocation().getDirection().normalize();
        }

        origin = new Vector(
                direction.getX() * time,
                direction.getY() * time,
                direction.getZ() * time
        );
    }

    @Override
    public void onCollide(Object object) {
        if(object instanceof Player) {
            Player player = (Player) object;
            if(!player.getUniqueId().equals(uuid)) {
                player.damage(10.0f);
                player.getWorld().createExplosion(player.getLocation(), 3f, false, false);
                finished = true;
            }
        }

        if(object instanceof Block) {
            Block block = (Block) object;
            block.getWorld().createExplosion(block.getLocation(), 3f, false, false);
            finished = true;
        }
    }

    @Override
    public void onParticle(World world) {
        world.spawnParticle(Particle.REDSTONE, location.toLocation(world), 5, new Particle.DustOptions(Color.fromRGB(87, 28, 48), 0.5f));
    }

}
