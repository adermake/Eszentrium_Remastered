package spells.spells;

import esze.utils.Matrix;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;

public class TridentHover extends Spell {

    Trident tri;

    @Override
    public void setUp() {
        double x = 1;
        double y = 0;
        double z = 1;
        loc = caster.getLocation().add(0, -3, 0);
        Vector v = new Vector(x, y, z);
        Vector vec = Matrix.rotateMatrixVectorFunktion(v, loc.clone());
        Bukkit.broadcastMessage("" + vec);
        loc.setDirection(vec);
        tri = (Trident) spawnEntity(EntityType.TRIDENT, loc);
        tri.setGravity(false);
        tri.teleport(loc);
    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {
        loc.setYaw(loc.getYaw() + 11);
        tri.setVelocity(loc.getDirection());

    }

    @Override
    public void display() {

    }

    @Override
    public void onPlayerHit(Player p) {

    }

    @Override
    public void onEntityHit(LivingEntity ent) {

    }

    @Override
    public void onSpellHit(Spell spell) {

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {

    }

}
