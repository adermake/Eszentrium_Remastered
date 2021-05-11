package spells.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Chaoswelle extends Spell{
	
	boolean inair = false;
	public Chaoswelle() {
		name = "§cChaoswelle";
		casttime = 20;
		speed = 1;
		cooldown = 20*52;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.PROJECTILE);
		setLore("§7Der Spieler fliegt hoch in die Luft#§7und gleitet danach langsam herunter. Sobald#§7der Spieler den Boden berührt, wird eine#§7Schockwelle in alle Richtungen freigesetzt,#§7die allen getroffenen Gegnern schadet und sie#§7zurückwirft.");
	}
	

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}
	double up = 0.3;
	double height = 0;
	@Override
	public void cast() {
		
		if (cast == 1) {
			playSound(Sound.ENTITY_ELDER_GUARDIAN_CURSE,caster.getLocation(), 3.0F, 2F);
		}
		Vector v = caster.getVelocity();
		v.setY(v.getY()+up);
		height = caster.getLocation().getY();
		caster.setVelocity(v);
		
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	int tl = 0;
	boolean doStuff = false;
	@Override
	public void move() {
		
		if (height + 50 > caster.getHeight()) {
			doStuff = true;
		}
		
		tl++;
		
		if (tl == 11) {
			playSound(Sound.ENTITY_ELDER_GUARDIAN_CURSE,caster.getLocation(), 3.0F, 2F);
			inair = true;
			setGliding(caster,true);
		}
		if (tl == 41) {
			playSound(Sound.ENTITY_ELDER_GUARDIAN_CURSE,caster.getLocation(), 3.0F, 2F);
		}
		
		
		if (caster.getVelocity().getY()>-0.5 && tl > 61) {
			caster.setVelocity(caster.getVelocity().setY(-1));
		}
		if (checkSilence()) {
			setGliding(caster,false);
			dead =true;
		}
		if (caster.getLocation().add(0,-1,0).getBlock().getType().isSolid() && inair && !dead) {
			
			
			setGliding(caster,false);
			int pow = 70 - tl/5;
			if (pow<0) 
				pow = 0;
				
			if (doStuff) {
				doChaoswelle(caster,pow,false);
			}
			
			dead = true;
		}
		
		
	}
	
	public void doChaoswelle(final Player p, final int Wellenstärke,final boolean dira) {
		double ta = 0;
		playSound(Sound.ENTITY_GENERIC_EXPLODE,p.getLocation(),10,(float) 0.1);
		if (dira == true) {
			ta = Wellenstärke;
		}else {
			ta = Math.PI / 4;
		}
		final double tas = ta;
		new BukkitRunnable() {
			double t = tas;
			Location loc = p.getLocation();

			public void run() {
				if (dira == true) {
					t = -t - 0.3 * Math.PI;
				}
				if (dira == false) {
					t = t + 0.3 * Math.PI;
				}
				
				for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / (32+step)) {
					double x = t * Math.cos(theta);
					double y = 0;
					double z = t * Math.sin(theta);
					loc.add(x, y, z);
					int minus = 0;
					while (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
						loc.add(0, -1, 0);
						minus++;
						if (minus >= 256) {
							break;
						}
					}

					// ParticleEffect.FIREWORKS_SPARK.display(loc,0,0,0,0,1);

					
					ParUtils.createParticle(Particles.LARGE_SMOKE, loc.clone().add(0,1,0), 0, 0, 0, 1, 0);
					ParUtils.createParticle(Particles.EXPLOSION, loc.clone().add(0,1,0), 0, 0, 0, 1, 0);
					
					for (LivingEntity le : p.getWorld().getLivingEntities()) {
						if (checkHit(le,loc,p,2)) {
							
							doKnockback(le,p.getLocation(),4);
							damage(le,8-theta,caster);
						}
					}
					loc.subtract(x, y, z);
					loc.add(0, minus, 0);
					theta = theta + Math.PI / 64;

					x = t * Math.cos(theta);
					y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
					z = t * Math.sin(theta);
					loc.add(x, y, z);

					loc.subtract(x, y, z);
				}
				if (t > Wellenstärke || t <= 0) {
					this.cancel();
				}
			}

		}.runTaskTimer(main.plugin, 0, 1);
	}

	@Override
	public void display() {
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
		if (spell.getName().contains("Antlitz der Göttin")) {
			setGliding(originalCaster, false);
		}
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
