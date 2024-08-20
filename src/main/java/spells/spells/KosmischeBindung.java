package spells.spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class KosmischeBindung extends Spell{
	
	public KosmischeBindung() {
		name = "§3Kosmische Bindung";
		cooldown = 20*50;
		hitSpell = true;
		hitPlayer = true;
		hitEntity = true;
		casttime = 20*1;
		hitboxSize = range;
		steprange = 20 * 5;
		silencable = true;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.SELFCAST);
		setLore("§7Hält nach kurzer Verzögerung alle#§7Gegner in der Nähe fest und werden am Anwender#§7fixiert. Dementsprechend folgen sie jeder#§7Drehung und Bewegung des Spielers, solange#§7der Zauber aktiv ist.# #§eF:§7 Bricht den#§7Zauber sofort ab.");
		setBetterLore("§7Hält nach kurzer Verzögerung alle#§7Gegner in der Nähe fest und werden am Anwender#§7fixiert. Dementsprechend folgen sie jeder#§7Drehung und Bewegung des Spielers, solange#§7der Zauber aktiv ist.# #§eF:§7 Bricht den#§7Zauber sofort ab.");
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		kRotLoc = caster.getLocation();
		loc = caster.getLocation();
		if (refined) 
			range= 20;
	
	}
	float range = 13;
	int s = 0;
	Location kRotLoc;
	double power = 0;
	boolean swapped = false;
	@Override
	public void cast() {
		power++;
	
		// TODO Auto-generated method stub
		Vector dir = loc.getDirection();
		loc = caster.getLocation();
		loc.setDirection(dir);
		
		
		
		//ParUtils.auraParticle(Particle.SMOKE_LARGE, caster, 0.6F, 20*1);
		
		//ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, loc, 0.5, 1, 0.5, 10, dist, d);
		//ParUtils.createFlyingParticle(Particle.FLAME, loc, 0.5, 1, 0.5, 10, dist, d);
		//ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, loc, 0, 0, 0, 1, 1, new Vector(0,1,0));
		//ParUtils.createFlyingParticle(Particle.FLAME, loc, 0, 0, 0, 1, 1, new Vector(0,1,0));
		
		//kRotLoc.setPitch(kRotLoc.getPitch()+5);
		//ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, caster.getLocation(), 1, 1F, 1, 3, 0.2F,new Vector(0,1,0));
		float c = cast;
		float ct = casttime;
		float speed = 2*(c/ct);
		SoundUtils.playSound(Sound.BLOCK_BELL_USE, loc, speed, 0.5F);
		//SoundUtils.playSound(Sound.BLOCK_BELL_RESONATE, loc, speed, 0.5F);
		Location p1 = ParUtils.stepCalcCircle(loc, range*cast/casttime,new Vector(0,1,0), 0, cast*speed);
		Location p2 = ParUtils.stepCalcCircle(loc,range*cast/casttime, new Vector(0,1,0), 0, 21+cast*speed);
		//ParUtils.dropItemEffectVector(p1, Material.TNT, 1, 1, 0, new Vector(0,1,0));
		//ParUtils.dropItemEffectVector(p2, Material.TNT, 1, 1, 0, new Vector(0,1,0));
		if (lp1 != null && lp2 != null) {
			
			//ParUtils.dropItemEffectVector(lp1, Material.SNOWBALL, 1, 5, 1, p1.toVector().subtract(lp1.toVector()).normalize());
			//ParUtils.dropItemEffectVector(lp2, Material.SNOWBALL, 1, 5, 1, p2.toVector().subtract(lp2.toVector()).normalize());
			ParUtils.dropItemEffectVector(lp1, Material.NETHER_STAR, 1, 5, 1, new Vector(0,1,0));
			ParUtils.dropItemEffectVector(lp2, Material.NETHER_STAR, 1, 5, 1, new Vector(0,1,0));
			//ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, p2, 0.1, 0.1, 0.1, 10, 1, p2.toVector().subtract(lp2.toVector().add(new Vector(0,1,0))));
			//ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, p1, 0.1, 0.1, 0.1, 10, 1, p1.toVector().subtract(lp1.toVector().add(new Vector(0,1,0))));
			//ParUtils.createFlyingParticle(Particle.END_ROD, p1, 0.1, 0.1, 0.1, 10, 1, p1.toVector().subtract(lp1.toVector()).normalize());
			//ParUtils.createFlyingParticle(Particle.END_ROD, p2, 0.1, 0.1, 0.1, 10, 1, p2.toVector().subtract(lp2.toVector()).normalize());
		}
				lp1 = p1;
				lp2 = p2;
		/*		
		if (swap()) {
			cast = casttime;
			swapped = true;
		}
			*/
		caster.setVelocity(caster.getLocation().getDirection().multiply(0.2F).add(new Vector(0,0.1,0)));
	}
	Location lp1;
	Location lp2;
	float angle;
	float pitch;
	@Override
	public void launch() {
		pitch = caster.getLocation().getPitch();
		angle = caster.getLocation().getYaw();
		SoundUtils.playSound(Sound.BLOCK_BELL_RESONATE, loc, 2, 2F);
		SoundUtils.playSound(Sound.BLOCK_BELL_USE, loc, 0.2F, 2F);
		SoundUtils.playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET, loc, 0.2F, 0.4F);
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range,0, 0.3F, randVector().normalize());
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range,0, 0.3F, randVector().normalize());
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range,0, 0.3F, randVector().normalize());
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range,0, 0.3F, randVector().normalize());
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range,0, 0.3F, randVector().normalize());
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range,0, 0.3F, randVector().normalize());
	}

	@Override
	public void move() {
		
		if (swap() || caster.getGameMode() == GameMode.ADVENTURE) {
			dead = true;
		}
		// TODO Auto-generated method stub
		hitTime++;
		if (hitTime > 5) {
			hitPlayer = false;
			hitEntity = false;
			hitSpell = false;
		}
		
		ArrayList<Entity> rem = new ArrayList<Entity>();
		for (Entity ent : hitList.keySet()) {
			if (ent instanceof Player) {
				Player p  = (Player)ent;
				if (p.getGameMode() == GameMode.ADVENTURE) {
					rem.add(p);
				}
			}
		}
		for (Entity ent : rem) {
			hitList.remove(ent);
		}
		
		for (Entity ent : hitList.keySet()) {
			ParUtils.parLine(Particle.WATER_BUBBLE, caster.getLocation().add(0,1,0), ent.getLocation().add(0,1,0), 0, 0, 0, 1, 1, 0.5F);
			float angleOffset = caster.getLocation().getYaw()-angle;
			float pitchOffset = caster.getLocation().getPitch()-pitch;
			Location fLoc = caster.getLocation();
			fLoc.setDirection(hitList.get(ent));
			fLoc.setYaw(fLoc.getYaw()+angleOffset);
			fLoc.setPitch(fLoc.getPitch()+pitchOffset);
			Location to = caster.getLocation().add(fLoc.getDirection().multiply(hitList.get(ent).length()));
			
			doPull(ent,to , to.distance(ent.getLocation())/3);
			Vector vec = hitList.get(ent);
			
			vec = vec.add(vec.clone().normalize().multiply(1.5));
			double len = vec.length();
			if (len > range) {
				
				vec = vec.normalize().multiply(range);
			}
			hitList.put((LivingEntity) ent, vec);
		
		}
	}

	int hitTime = 0;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		hitList.put(p,p.getLocation().toVector().subtract(caster.getLocation().toVector()));
		
	}
	HashMap<LivingEntity,Vector> hitList = new HashMap<LivingEntity,Vector>();
	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		hitList.put(ent, ent.getLocation().toVector().subtract(caster.getLocation().toVector()));
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
		SoundUtils.playSound(Sound.BLOCK_BELL_RESONATE, loc, 2, 2F);
		SoundUtils.playSound(Sound.BLOCK_BELL_USE, loc,2F, 2F);
		SoundUtils.playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET, loc, 2F, 0.4F);
		// TODO Auto-generated method stub
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range/2,0, -0.3F, randVector().normalize());
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range/2,0, -0.3F, randVector().normalize());
		ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), range/2,0, -0.3F, randVector().normalize());
		
	}
	
	

	

}
