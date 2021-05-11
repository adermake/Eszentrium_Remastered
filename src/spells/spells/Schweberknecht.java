package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.stagespells.WebTrail;

public class Schweberknecht extends Spell {

	
	public Schweberknecht() {
		steprange = 20 * 6;
		name = "§6Schweberknecht";
		cooldown = 20 * 35;
	}
	Spider s;
	boolean bounced = false;
	boolean hitbounced = false;
	@Override
	public void setUp() {
		
		// TODO Auto-generated method stub
		s = (Spider) spawnEntity(EntityType.SPIDER);
		unHittable.add(s);
		vel = caster.getLocation().getDirection().multiply(1);
		s.setInvulnerable(true);
		addNoTarget(s);
		playSound(Sound.ENTITY_SPIDER_DEATH,loc,2,1);
		
		if (refined) {
			Spell chase = this;
			for(float i = 0;i<15;i++) {
				 Spell s = new WebTrail(chase, caster, name,-1,yLevel);
				 chase = s;
				 trails.add(s);
			}
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
	double yLevel = 0;
	Vector vel;
	
	ArrayList<Spell> trails = new ArrayList<Spell>();
	@Override
	public void move() {
		loc.add(vel);
		doPin(s,loc,3);
		vel.add(new Vector(0,-0.1,0));
		
		if (bounced) {
			//playSound(Sound.ENTITY_LEASH_KNOT_PLACE,loc,1,0.1F);
		}
		
		if(hitbounced && !bounced) {
			if (refined) {
				/*
				for (Spell s : trails) {
					s.kill();
				}
				*/
				if (s instanceof WebTrail) {
					WebTrail w = (WebTrail) s;
					w.height = loc.clone().getY();
					w.power = 0;
				}
				
			}
			speed = 2;
			bounced = true;
			playSound(Sound.ENTITY_MAGMA_CUBE_JUMP,loc,3,0.5F);
			vel = vel.setY(Math.abs(vel.getY()));
			double y = vel.getY();
			double cap = 3F;
			if (y > cap) {
				y = cap;
			}
			step = 0;
			steprange = 20 * 4;
			//vel = vel.normalize().multiply(y/1.2);
			vel = vel.setY(0);
			vel.normalize().multiply(y/2);
			vel.setY(y);
			hitbounced = false;
			yLevel = loc.getY();
			Spell chase = this;
			//new WebTrail(chase, caster, name);
			loc = s.getLocation();
			for(float i = 0;i<15;i++) {
				 Spell s = new WebTrail(chase, caster, name,y,yLevel);
				 chase = s;
			}
			
			
			//ParUtils.parKreisDirSolid(Particles.CLOUD, loc, 3, 0, yLevel/80, new Vector(0,1,0), vel.clone().multiply(-1));
		}
		if (loc.getY() < yLevel) {
			dead = true;
		}
		
		
		
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.CLOUD, loc, 0, 0, 0, 1, 0);
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
	public void onBlockHit(Block block) {
		if (hitbounced) {
			dead = true;
		}
		hitbounced = true;
		
	}

	@Override
	public void onDeath() {
		playSound(Sound.ENTITY_SPIDER_DEATH,loc,2,0.5);
		// TODO Auto-generated method stub
		s.remove();
	}
	


}
