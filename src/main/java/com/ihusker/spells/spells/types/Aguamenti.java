package com.ihusker.spells.spells.types;

import com.ihusker.spells.spells.Spell;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Aguamenti extends Spell {

    public Aguamenti() {
        super(20.0f, 0.2f);
    }

    @Override
    public void update(Player player) {
        location = player.getEyeLocation().toVector();
        direction = player.getLocation().getDirection().normalize();


        origin = new Vector(
                direction.getX() * 5.0f,
                direction.getY() * 5.0f,
                direction.getZ() * 5.0f
        );
    }

    @Override
    public void onCollide(Object object) {
        if(object instanceof Block) {
            Block block = (Block) object;
            for(BlockFace face : BlockFace.values()) {
                Block relativeBlock = block.getRelative(face);
                if(relativeBlock.getType() == Material.FIRE){
                    relativeBlock.setType(Material.AIR);
                }
            }
        }
    }

    @Override
    public void onParticle(World world) {
        world.spawnParticle(Particle.REDSTONE, location.toLocation(world), 1, new Particle.DustOptions(Color.fromRGB(2, 124, 207), 2.0f));
        world.spawnParticle(Particle.REDSTONE, location.toLocation(world), 2, new Particle.DustOptions(Color.fromRGB(73, 167, 230), 1.0f));
    }

}
