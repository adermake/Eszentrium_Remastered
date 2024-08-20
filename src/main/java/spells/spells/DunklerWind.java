package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;

import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.RepulsionDirectional;

public class DunklerWind extends Spell {


    public DunklerWind() {
        name = "§cDunkler Wind";

        cooldown = 20 * 50;
        speed = 8F;
        steprange = 5 * 20 * 8 * 2;
        multihit = true;

        hitboxSize = 8;
        hitPlayer = false;
        hitEntity = false;
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.PROJECTILE);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.SELFCAST);
        setLore("§7Beschwört ein Projektil, das dem Mauszeiger folgt. Nach kurzer Verzögerung werden alle getroffenen Gegner, die mit dem Projektil in Kontakt kommen, weggeschleudert." +
                "F: Der Spieler springt Richtung Projektil und zieht alle Gegner in der Nähe mit sich.");
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
        playSound(Sound.BLOCK_CONDUIT_ACTIVATE, loc, 2, 0.5F);
    }

    boolean stopped = false;
    int maxrange = 40;
    Vector vec;
    Vector nextDir = null;

    int jumpcooldown = 0;
    boolean activate = false;
    int knockdownCooldown = 0;

    @Override
    public void move() {
        knockdownCooldown++;

        if (step > speed * 20 * 2 && knockdownCooldown > 20) {
            hitPlayer = true;
            hitEntity = true;
        }

        if (vec != null && stopped) {
            Vector newV = loc.toVector().subtract(caster.getLocation().toVector());

            if (newV.length() <= vec.length()) {

                vec = newV;

            } else {

                ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 1, 1);
                stopped = false;
            }

        }

        if (!stopped) {


            if (nextDir != null) {

                loc.add(nextDir);
                nextDir = null;
            } else {
                loc.add(caster.getLocation().getDirection().multiply(0.3));
                if (loc.distance(caster.getLocation()) > maxrange) {
                    Vector pos = loc.toVector().subtract(caster.getLocation().toVector()).normalize().multiply(maxrange);
                    loc = caster.getLocation().add(pos);
                }
            }

        }


        if (jumpcooldown <= 0 && swap()) {
            playSound(Sound.BLOCK_CONDUIT_ACTIVATE, loc, 2, 2F);
            ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 1, 1);
            jumpcooldown = 5 * 10;
            ParUtils.parLineRedstone(caster.getLocation(), loc.clone(), Color.PURPLE, 1, 2);
            caster.setVelocity(loc.toVector().subtract(caster.getLocation().toVector()).multiply(0.1));
            stopped = true;
            vec = loc.toVector().subtract(caster.getLocation().toVector());
        }
        clearswap();

        if (stopped) {
            Vector v = caster.getVelocity().add(loc.toVector().subtract(caster.getLocation().toVector()).multiply(0.006).add(new Vector(0, 0.006, 0)));

            caster.setVelocity(v);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getGameMode() != GameMode.ADVENTURE) {
                    if (checkHit(p, caster.getLocation(), caster, 7)) {

                        doPin(p, loc.clone());
                    }
                }
            }
        }

        jumpcooldown--;


        if (jumpcooldown <= 0)
            jumpcooldown = 0;
    }

    @Override
    public void display() {
        if (step % 5 == 0) {
            if (step < steprange - 40 * 8) {
                ParUtils.parLineRedstone(caster.getLocation(), loc.clone(), Color.BLACK, 0.3F, 1);
            } else {
                ParUtils.parLineRedstone(caster.getLocation(), loc.clone(), Color.GRAY, 0.3F, 1);
            }

        }
        // TODO Auto-generated method stub
        //ParUtils.createParticle(Particle.WATER_BUBBLE, loc, 0.1F, 0.1F, 0.1F, 1, 1);
        double lf = (double) step / (speed * 20 * 2);
        if (lf > 1)
            lf = 1;

        if (lf >= 1) {
            if (activate == false) {
                playSound(Sound.ENTITY_ELDER_GUARDIAN_DEATH, loc, 10, 1F);
                ParUtils.createParticle(Particle.LARGE_SMOKE, loc, 0, 0, 0, 60, 0.2);
            }
            activate = true;


            ParUtils.createParticle(Particle.LARGE_SMOKE, loc, 0, 0, 0, 2, 0.05);
        }


        //ParUtils.createRedstoneParticle(loc, 0.1F, 0.1F, 0.1F, 1, Color.fromBGR((int)(254*lf), 0, (int)(254*lf)), 0.1F+22*(float)lf);
        if (step == steprange - 40 * 8) {
            playSingleSound(Sound.BLOCK_REDSTONE_TORCH_BURNOUT, caster, 11, 0.2F);
        }
        if (step < steprange - 40 * 8) {
            ParUtils.createRedstoneParticle(loc, 0.1F, 0.1F, 0.1F, 1, Color.fromBGR((int) (254 * lf), 0, (int) (254 * lf)), 0.1F + 2 * (float) lf);
        } else {
            ParUtils.createRedstoneParticle(loc, 0.1F, 0.1F, 0.1F, 1, Color.RED, 1);
        }


    }

    @Override
    public void onPlayerHit(Player p) {

        if (p.isOnGround())
            return;
        //ParUtils.parKreisDir(Particle.SMOKE_LARGE, loc, 4, 0, 12, new Vector(0, -1, 0), new Vector(0, -1, 0));
        // TODO Auto-generated method stub
        ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 1, 1);
        playSound(Sound.ENTITY_ELDER_GUARDIAN_DEATH, loc, 2, 1.5F);
        p.setVelocity(p.getVelocity().normalize().multiply(2));

        hitEntity = false;
        hitPlayer = false;
        knockdownCooldown = 0;

    }

    @Override
    public void onEntityHit(LivingEntity ent) {

        if (ent.isOnGround())
            return;
        // TODO Auto-generated method stub
        ParUtils.parKreisDir(Particle.LARGE_SMOKE, loc, 4, 0, 12, new Vector(0, -1, 0), new Vector(0, -1, 0));
        playSound(Sound.ENTITY_ELDER_GUARDIAN_DEATH, loc, 2, 1.5F);
        ent.setVelocity(ent.getVelocity().normalize().multiply(2));
        hitEntity = false;
        hitPlayer = false;
        knockdownCooldown = 0;
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
		/*
		// TODO Auto-generated method stub
		Vector back = caster.getLocation().getDirection().multiply(0.3);
		loc.add(caster.getLocation().getDirection().multiply(-0.3));
		for (BlockFace bf : slideDir(loc.clone())) {
			
			if (bf == BlockFace.DOWN && back.getY()<0) {
				back.setY(0);
			}
			
			if (bf == BlockFace.UP && back.getY()>0) {
				back.setY(0);
			}
			if (bf == BlockFace.SOUTH && back.getZ()>0) {
				back.setZ(0);
			}
			if (bf == BlockFace.NORTH && back.getZ()<0) {
				back.setZ(0);
			}
			if (bf == BlockFace.EAST && back.getX()>0) {
				back.setX(0);
			}
			if (bf == BlockFace.WEST && back.getX()<0) {
				back.setX(0);
			}
			
			
		}
	
		
		loc.add(back);
		*/
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

}
