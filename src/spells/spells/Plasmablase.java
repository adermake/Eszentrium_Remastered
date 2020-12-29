package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Plasmablase extends Spell {
	
	double rad = 12;
	Vector dir = new Vector(0,1,0);
	int animCooldown = 0;
	public Plasmablase() {
		cooldown = 20 * 40;
		
		name = "�3Plasmablase";
		hitSpell = true;
		canHitCastersSpells = false;
		hitboxSize = rad;
		
		multihit = true;
		steprange = 20 * 10;
		
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.SUPPORT);
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.MULTIHIT);
		
		setLore("Erzeugt f�r kurze Zeit eine Blase um den Spieler herum. Zauber und Spieler von au�erhalb k�nnen nicht in das Innere eindringen. Der Anwender kann innerhalb dieser Blase fliegen und wird bei Kontakt mit der Blasenwand in Blickrichtung katapultiert.");
		setBetterLore("Erzeugt f�r kurze Zeit eine Blase um den Spieler herum. Zauber und Spieler von au�erhalb k�nnen nicht in das Innere eindringen. Der Anwender kann innerhalb dieser Blase fliegen und wird bei Kontakt mit der Blasenwand in Blickrichtung katapultiert.");
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET, loc, 12,0.7F);
		playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,0.8F);
		bulletHitEffect(caster.getLocation().getDirection().multiply(-1),loc.clone());
		if (refined) {
			steprange*=2;
			rad = 18;
			hitboxSize = rad;
		}
		
		
		caster.setAllowFlight(true);
		caster.setFlying(true);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		ParUtils.auraParticle(Particles.ENCHANTED_HIT, caster, 1, 1);
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void move() {
		
	
		animCooldown--;
		if (animCooldown < 0) 
			animCooldown = 0;
		// TODO Auto-generated method stub
		/*for (Entity ent : hitEntitys) {
			if (ent.getLocation().distance(loc)> rad) {
				if (ent instanceof Player) {
					Player p = (Player)ent;
					if (p.getGameMode() == GameMode.ADVENTURE) {
						continue;
					}
				}
				Vector v = ent.getLocation().toVector().subtract(loc.toVector());
				ParUtils.parKreisDir(Particles.ENCHANTED_HIT, ent.getLocation(), 2, 0, 0, v,v);
				doPull(ent, loc.clone(), 1.5F);
				playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,1.8F);
			}
		}
		*/
		
		if (caster.getLocation().distance(loc) > rad) {
			dead = true;
			if (caster.getGameMode() == GameMode.SURVIVAL)
			caster.setAllowFlight(false);
			
			
			
			caster.setVelocity(caster.getVelocity().add(caster.getLocation().getDirection().multiply(3)));
			Vector d = loc.toVector().subtract(caster.getLocation().toVector());
			animCooldown = 0;
			bulletHitEffect(d,loc.clone());
			ParUtils.createFlyingParticle(Particles.END_ROD, caster.getLocation(), 2, 2, 2, 10, 3, caster.getLocation().getDirection());
			ParUtils.parKreisDir(Particles.END_ROD, caster.getLocation(), 3, 0, 5, caster.getLocation().getDirection(), caster.getLocation().getDirection());
		}
	}
	
	@Override
	public void display() {
		
		
		// TODO Auto-generated method stub
		for (int i = 0;i<3;i++) {
			loc.setPitch(loc.getPitch()+15);
			Location l = ParUtils.stepCalcCircle(loc, rad, loc.getDirection().add(new Vector(0,i,0)), 0, step*(i+1));
			//ParUtils.createParticle(Particles.FISHING, l, 0, 0, 0, 1, 0);
			float f = 3F;
			
			
			if (refined) {
				f = 10;
				Location l2 = ParUtils.stepCalcCircle(loc, rad, loc.getDirection().add(new Vector(0,i,0)), 0, step*(i+1));
				ParUtils.createRedstoneParticle(l, 1, 1, 1, 1, Color.fromBGR(230, 115, 25), f);
			}	
				ParUtils.createRedstoneParticle(l, 0, 0, 0, 1, Color.AQUA, f);
			
			
		}
	}

	public void bulletHitEffect(Vector ind,Location inl) {
		if (animCooldown <= 0) {
			
				playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,1.8F);
		animCooldown = 15;
		ParUtils.createParticle(Particles.FLASH, inl, 0, 0, 0,1, 1);
		Vector d = ind.clone();
		Location l = inl.clone();
		Location ori = inl.clone();
		l.add(d.normalize().multiply(-rad));
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (int i = 0;i<2;i++) {
					
				
				l.add(ind.clone().normalize().multiply(1.5));
				double distance = l.distance(ori);
				ParUtils.parKreisDir(Particles.ENCHANTED_HIT, l, calcWidthOfCircle(rad, distance+rad), 0, 0, d, d);
				
				//ParUtils.parKreisSolidRedstone(Color.AQUA, 3, l, calcWidthOfCircle(rad, distance+rad),0, 1, ind);
				if (distance > rad*2) {
					this.cancel();
				}
				}
			}
		}.runTaskTimer(main.plugin, 1,1);
		
		}
	}
	
	public double calcWidthOfCircle(double r,double h) {
		return Math.sqrt((r*r)-Math.pow((h-r),2));
	}
	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		Vector v = p.getLocation().toVector().subtract(loc.toVector());
		//ParUtils.parKreisDir(Particles.ENCHANTED_HIT, p.getLocation(), 2, 0, 0, v,v);
		bulletHitEffect(v.multiply(-1), loc.clone());
		doKnockback(p, loc.clone(), 1);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		Vector v = ent.getLocation().toVector().subtract(loc.toVector());
		//ParUtils.parKreisDir(Particles.ENCHANTED_HIT, ent.getLocation(), 2, 0, 0, v,v);
		bulletHitEffect(v.multiply(-1), loc.clone());
		doKnockback(ent, loc.clone(), 1);
		
	}

	@Override
	public void onSpellHit(Spell spell) {
		if (hitEntitys.contains(spell.caster)) {
			return;
		}
		playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET, loc, 12,0.7F);
		playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,0.8F);
		
		// TODO Auto-generated method stub
		Location l1 = spell.getLocation().clone();
		Vector dir = loc.toVector().subtract(l1.clone().toVector());
		bulletHitEffect(dir, loc.clone());
		spell.kill();
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath() {
		caster.setFlying(false);
		caster.setAllowFlight(false);
		// TODO Auto-generated method stub
		playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET, loc, 12,2F);
		playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,0.1F);
		bulletHitEffect(caster.getLocation().getDirection().multiply(-1),loc.clone());
	}

}
