package spells.spells;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;

public class Pyroschlag extends Spell {
    Location b;


    public Pyroschlag() {
        hitEntity = false;
        hitPlayer = false;
        hitSpell = false;
        casttime = 100;
        speed = 30;
        name = "§cPyroschlag";
        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*40
        );
    }

    int c = 0;

    @Override
    public void cast() {
        if (caster.getGameMode() == GameMode.ADVENTURE) {
            dead = true;
            return;
        }
		/*
		if (c== 0) {
			
			ParUtils.parKreisDot(Particle.FLAME, caster.getLocation(), 3, 0, 0.1, new Vector(0,1,0));
		}
			
		c++;
		if (c>10) {
			ParUtils.parKreisDir(Particle.FLAME, caster.getLocation(), 3, 0, 0.1, new Vector(0,1,0),new Vector(0,1,0));
			c=1;
		}
		caster.setVelocity(caster.getVelocity().multiply(0));
		caster.setVelocity(caster.getVelocity().setY(0.1));
		*/
        ParUtils.createRedstoneParticle(b.clone().add(0, 0.5, 0), 0, 0, 0, 1, Color.RED, 2);
    }


    @Override
    public void setUp() {
        playSound(Sound.ENTITY_SKELETON_HORSE_DEATH, caster.getLocation(), 200, 0.1F);
        loc = caster.getLocation();
        b = block(caster);
        if (b == null) {
            refund = true;
            dead = true;
        }

    }

    int t = 0;

    @Override
    public void move() {
        t++;

        if (t < 100) {


            loc.add(new Vector(0, 0.5, 0));
        }
        if (t == 100) {
            playSound(Sound.ENTITY_GUARDIAN_DEATH_LAND, b, 13, 1F);
            loc = b.clone().add(0, 100, 0);
        }
        if (t > 160) {
            loc.add(new Vector(0, -0.5, 0));
        }

        if (t > 400) {
            dead = true;
        }

    }

    int k = 0;

    @Override
    public void display() {
        k++;
        if (k > 30) {
            k = 0;
            ParUtils.parKreisDir(Particle.FLAME, loc, randInt(1, 5), 0, 0.1, new Vector(0, 1, 0), new Vector(0, -1, 0));
        }
        if (t < 100) {
            //ParUtils.parLine(Color.ORANGE, caster.getLocation(), loc);
            ParUtils.createParticle(Particle.END_ROD, loc, 0, 0, 0, 1, 0);
            ParUtils.createParticle(Particle.FLAME, loc, 0, 0, 0, 1, 0);
        }
        if (t > 100) {
            //ParUtils.parLine(Color.ORANGE, b, loc);
            ParUtils.createParticle(Particle.END_ROD, loc, 0, 0, 0, 1, 0);
            ParUtils.createParticle(Particle.FLAME, loc, 0, 0, 0, 1, 0);
        }

    }

    @Override
    public void onBlockHit(Block b) {
        dead = true;

        playSound(Sound.ENTITY_GENERIC_EXPLODE, loc, 10, 0.1F);
        ParUtils.createParticle(Particle.FLAME, loc, 0, 0, 0, 1, 100);
        new ExplosionDamage(12, 15, caster, loc, name);
        new Repulsion(12, 1, caster, loc, name);
        ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, 4, 4, 4, 22, 2);
        for (Location block : ParUtils.grabBlocks(loc, 134, 8)) {

            int t = 0;
            Location blockClone = block.clone();
            while (t < 5) {
                if (!blockClone.getBlock().getType().isSolid()) {
                    blockClone.add(0, -1, 0);
                } else {
                    break;
                }


                t++;
            }
            while (t < 5) {
                if (blockClone.getBlock().getType().isSolid()) {
                    blockClone.add(0, 1, 0);
                } else {
                    break;
                }


                t++;
            }

            if (!blockClone.getBlock().getType().isSolid()) {
                FallingBlock fb = caster.getWorld().spawnFallingBlock(blockClone, block.getBlock().getType(), block.getBlock().getData());


                fb.setHurtEntities(false);
                fb.setDropItem(false);
                doKnockback(fb, loc.clone().add(0, -6, 0), 2);

            }


        }

    }


    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }


}
