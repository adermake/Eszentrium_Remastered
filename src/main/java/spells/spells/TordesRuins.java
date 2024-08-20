package spells.spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.KompassEffect;

public class TordesRuins extends Spell {


    public TordesRuins() {
        hitboxSize = 1f;
        speed = 4;
        steprange = 100;
        name = "§eTor des Ruins";
        cooldown = 20 * 38;
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.PROJECTILE);
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        playSound(Sound.ITEM_TRIDENT_RETURN, caster.getLocation(), 1, 0.2F);
    }

    @Override
    public void move() {
        loc.setDirection(caster.getLocation().getDirection().multiply(0.5));
        loc.add(loc.getDirection().multiply(0.5));
        // TODO Auto-generated method stub
        //ParUtils.createParticle(Particle.END_ROD, loc, 0, 0, 0, 0, 1);


    }

    double gains = 0.01;

    @Override
    public void display() {
        // TODO Auto-generated method stub
        gains += 0.0003;
        hitboxSize += gains;
        Location l1 = ParUtils.stepCalcCircle(loc.clone(), hitboxSize, loc.getDirection(), 0, (step * 3) / (1 + 4 * getLerp()));
        ParUtils.dropItemEffectVector(l1, Material.COMPASS, 1, 3, 1, new Vector(0, 0, 0));
        Location l2 = ParUtils.stepCalcCircle(loc.clone(), hitboxSize, loc.getDirection(), 0, 22 + (step * 3) / (1 + 4 * getLerp()));
        ParUtils.dropItemEffectVector(l2, Material.COMPASS, 1, 3, 1, new Vector(0, 0, 0));

        ParUtils.parLineRedstone(l1, loc, Color.fromBGR(0, 0, randInt(0, 255)), 0.5F, 0.1F);
        ParUtils.parLineRedstone(l2, loc, Color.fromBGR(0, 0, randInt(0, 255)), 0.5F, 0.1F);
        ParUtils.createDustTransition(l1, 0, 0, 0, 1, Color.GRAY, Color.RED, 0.8F);
        ParUtils.createDustTransition(l2, 0, 0, 0, 1, Color.GRAY, Color.RED, 0.8F);
        //ParUtils.createRedstoneParticle(Color.RED, spreadX, spreadY, spreadZ, count, color, size);
    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        new KompassEffect(p, caster, name);
        if (refined) {
            new BukkitRunnable() {
                public void run() {
                    new KompassEffect(p, caster, name);
                }

            }.runTaskLater(main.plugin, 20);
        }
        dead = true;
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        new KompassEffect(ent, caster, name);
        if (refined) {
            new BukkitRunnable() {
                public void run() {
                    new KompassEffect(ent, caster, name);
                }

            }.runTaskLater(main.plugin, 20);
        }
        dead = true;
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

}
