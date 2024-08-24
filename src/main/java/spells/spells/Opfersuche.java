package spells.spells;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

import java.util.ArrayList;

public class Opfersuche extends Spell {

    public Opfersuche() {

        name = "§6Opfersuche";
        steprange = 200;
        hitboxSize = 2.5;
        hitSpell = true;
        speed = 2;

        spellDescription = new SpellDescription(
                "Schießt ein Phantom in Blickrichtung, das getroffene Gegner festhält. Je weiter das Phantom fliegt, desto länger hält es fest.",
                "Schießt ein Phantom in Blickrichtung, das getroffene Gegner festhält. Je weiter das Phantom fliegt, desto länger hält es fest.",
                null,
                null,
                null,
                null,
                20*24
        );
        
        addSpellType(SpellType.LOCKDOWN);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);
    }


    Phantom ent;
    ArrayList<Entity> grabbed = new ArrayList<Entity>();

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        ent = (Phantom) caster.getWorld().spawnEntity(caster.getEyeLocation(), EntityType.PHANTOM);
        noTargetEntitys.add(ent);
        unHittable.add(ent);

        ent.setInvulnerable(true);
        ent.setCollidable(false);

        bindEntity(ent);
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        playSound(Sound.ENTITY_PHANTOM_BITE, loc, 5, 1.5F);
        loc = caster.getLocation();

    }

    int i = (int) step;
    int groundTime = 0;

    @Override
    public void move() {
        // TODO Auto-generated method stub
        ent.setFireTicks(-1000);
        playSound(Sound.ENTITY_PHANTOM_FLAP, ent.getLocation(), 1, 1.5F);

        if (refined) {
            ent.setSize((int) step);
            i = (int) step;
            if (i > 10)
                i = 10;

            hitboxSize = 10;
        }


        ParUtils.createParticle(Particle.LARGE_SMOKE, ent.getLocation().add(0, 2, 0), 0, 0, 0, 2, 0.01);
        ent.teleport(loc.add(loc.getDirection().multiply(2)));


    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        if (refined) {
            damage(p, 6, caster);
        } else {
            damage(p, 3, caster);
        }


        phantomSpin(p, 1, 40 + (int) step * 5);
        playSound(Sound.ENTITY_PHANTOM_HURT, loc, 5, 2F);
        new BukkitRunnable() {
            int t = 0;
            int time = (int) step * 6;

            public void run() {
                t++;
                p.setVelocity(new Vector(0, -0.6, 0));
                if (t > time) {
                    this.cancel();

                }

            }
        }.runTaskTimer(main.plugin, 1, 1);


        if (!refined)
            dead = true;
    }

    @Override
    public void onEntityHit(LivingEntity ent2) {

        playSound(Sound.ENTITY_PHANTOM_HURT, loc, 5, 2F);
        phantomSpin(ent2, 1, 40 + (int) step * 5);

        new BukkitRunnable() {
            int t = 0;
            int time = (int) step * 6;

            public void run() {
                t++;
                ent2.setVelocity(new Vector(0, -0.6, 0));
                if (t > time) {
                    this.cancel();

                }
            }
        }.runTaskTimer(main.plugin, 1, 1);
        if (refined) {
            damage(ent2, 6, caster);
        } else {
            damage(ent2, 3, caster);
        }

        if (!refined)
            dead = true;
    }

    public void phantomSpin(LivingEntity p, int spin, int time) {
        Phantom fa = (Phantom) spawnEntity(EntityType.PHANTOM);
        noTargetEntitys.add(fa);
        unHittable.add(fa);

        new BukkitRunnable() {
            int t = 0;
            Location lastPos = fa.getLocation();

            public void run() {
                t++;
                fa.setFireTicks(-1000);
                int u = t;
                if (u > 10)
                    u = 10;
                ParUtils.createParticle(Particle.LARGE_SMOKE, fa.getLocation(), 0, 0, 0, 2, 0.01);
                fa.setSize(u);
                Location pos = ParUtils.stepCalcCircle(p.getLocation(), 3, new Vector(0, 0.1, 0), 0, t * spin);
                pos.setDirection(pos.toVector().subtract(lastPos.toVector()));
                lastPos = pos.clone();
                fa.teleport(pos);
                fa.setTarget(p);
                fa.setRotation(pos.getYaw(), pos.getPitch());
                if (t > time) {
                    this.cancel();
                    fa.remove();
                }
                //fa.teleport(l.add(dir));
            }
        }.runTaskTimer(main.plugin, 1, 1);

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

        ent.remove();
    }


}
