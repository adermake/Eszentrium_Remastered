package spells.stagespells;

import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class SchockLaser extends Spell {

    public SchockLaser(Player p, String namae, boolean refined, double height) {
        name = namae;
        this.refined = refined;
        hitBlock = true;
        steprange = 500;
        speed = 12;
        hitSpell = true;
        castSpell(p, name);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);
		/*
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				speed*=1.00001F*speed;
				
				if (speed > 20) {
					speed = 20;
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin, 1, 1);
		*/
    }

    Location saveOrigin;
    Location saveAim;

    public SchockLaser(Location origin, Location aim, Player p, String namae, boolean refined, double height) {
        name = namae;
        this.refined = refined;
        hitBlock = true;
        steprange = 500;
        speed = 15;
        hitSpell = true;
        saveOrigin = origin.clone();
        saveAim = aim.clone();
        castSpell(p, name);
		
		/*
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				speed*=1.00001F*speed;
				
				if (speed > 20) {
					speed = 20;
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin, 1, 1);
		*/
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        if (refined) {
            loc = saveOrigin.clone();
            loc.setDirection(saveAim.toVector().subtract(saveOrigin.toVector()));
        }
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    Location hitLoc;

    boolean spiking = false;
    boolean reverseSpiking = false;
    Vector spike;
    int spikeLength = 1;
    int maxSpikeLength = 35;
    Location phaseLoc;
    double antiFocus = 0;

    @Override
    public void move() {


        if (!spiking && randInt(1, 6) == 2) {
            spikeLength = randInt(0, clamp(31 - (int) (step / 10), 1, 31));

            maxSpikeLength = spikeLength;
            spike = randVector().normalize();
            spiking = true;
            reverseSpiking = false;
            phaseLoc = loc.clone();
        }

        if (caster.isSneaking()) {
            maxSpikeLength -= 1;
            antiFocus += 1;
            speed = 8;
            if (refined) {
                speed = 32;
            }

        }
        if (phaseLoc != null)
            phaseLoc.add(phaseLoc.getDirection().multiply(0.5));
        if (spiking) {
            spikeLength--;
            if (spikeLength <= 0) {
                spiking = false;

            }
            if (spikeLength < maxSpikeLength / 1.5) {
                loc.add((phaseLoc.clone().toVector().subtract(loc.toVector()).normalize()).multiply(0.5));
            } else {
                loc.add(spike.clone().add(loc.getDirection()).normalize().multiply(0.5));
            }


        } else {
            loc.add(loc.getDirection().multiply(0.5));
        }


    }

    public int clamp(int i, int min, int max) {
        return (i < min) ? min : (Math.min(i, max));
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

        ParUtils.createParticle(Particle.END_ROD, loc, 0, 0, 0, 1, 0);
        //ParUtils.createParticle(Particle.FIREWORKS_SPARK, loc, 0,1, 0, 0, 0.05);
    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        hitLoc = p.getLocation();
        onHit();
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        hitLoc = ent.getLocation();
        onHit();
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub
        hitLoc = block.getLocation();
        onHit();
    }


    public void onHit() {
        double x = (caster.getLocation().getY() - hitLoc.getY());


        double dmg = 4 + 15 / (1 + Math.exp(-0.06 * x) * 15);
        //Bukkit.broadcastMessage("DMG "+dmg);
        new Explosion(6, dmg, 1, 1, caster, loc, name);
        ParUtils.parKreisDot(Particle.CLOUD, loc, 5, 0, 0.05, loc.getDirection().multiply(-1));
        dead = true;
        playSound(Sound.ENTITY_LIGHTNING_BOLT_IMPACT, loc, 4, 0.3F);


    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub
    }


}
