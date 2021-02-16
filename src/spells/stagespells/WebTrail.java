package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import spells.spellcore.Spell;
import spells.spells.Schweberknecht;

public class WebTrail extends Spell {

	
	Spell trail;
	FallingBlock web;
	public double power = 0;
	public double height;
	public WebTrail(Spell trail,Player caster,String name,double power,double height) {
		this.trail = trail;
		this.height = height;
		this.power = power;
		hitboxSize = 3;
		castSpell(caster, name);
		count = (int) (power *11);
		for (Entity ent : trail.noTargetEntitys) {
			noTargetEntitys.add(ent);
		}
		
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = trail.getLocation();
		web = trail.getLocation().getWorld().spawnFallingBlock(trail.getLocation(), Material.COBWEB,(byte)0);
		last = web.getLocation();
		web.setGravity(false);
		
	}
	public void kill() {
		web.remove();
		dead = true;
	}
	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		lastweb = web.getLocation().add(0,-1,0);
		h = trail.getLocation().getY();
	}
	Location lastweb;
	Location last;
	double h = 0;
	int count = 0;
	boolean desc = false;
	@Override
	public void move() {
		boolean webded = false;
		if (web.getLocation().distance(lastweb)<= 0.01) {
			webded = true;
		}
		// TODO Auto-generated method stub
		// 
		/*
		if (isInGround()&& webded && !trail.isDead()) {
			
			
			dead = true;
			//Bukkit.broadcastMessage("RESPAWN");
			ParUtils.debug(trail.getLocation().add(0,0,0));
			//caster.teleport(trail.getLocation());
			web = trail.getLocation().getWorld().spawnFallingBlock(trail.getLocation(), Material.COBWEB,(byte)0);
			web.setGravity(false);
		}
		*/
		if (h > trail.getLocation().getY() || step > 20) {
			desc = true;
		}
		//Bukkit.broadcastMessage(
		//Bukkit.broadcastMessage(""+webded);
		//Bukkit.broadcastMessage(""+last.getY() +" --- " +height+ " --- "+power +" --- desc" +desc + webded);
		
			
		
		if ((((loc.clone().add(0,-1,0).getBlock().getType().isSolid() ||webded|| last.getY()-1-2*power<= height) && desc)) ) {
				web.teleport(trail.getLocation());
				dead = true;
				web.remove();
				loc = last;
			//Bukkit.broadcastMessage("KILLED WEB");
			
		}
		else {
			loc = web.getLocation();
			doPin(web,trail.getLocation(),2.7f);
		}
		if (!trail.isDead()) {
			last = trail.getLocation();
		}else if ( trail instanceof WebTrail){
			WebTrail wt = (WebTrail) trail;
			trail = wt.trail;
		}
		else {
			
		}
		lastweb = web.getLocation();
		h = trail.getLocation().getY();
		
		for (Entity ent : hitEntitys) {
			if (!dead)
			doPin(ent,loc,3);
			
		}
		
	}
	
	public boolean isInGround() {
		return web.getLocation().getBlock().getType().isSolid() || web.getLocation().add(0,1,0).getBlock().getType().isSolid();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_GUARDIAN_FLOP,loc,1,0.4);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_GUARDIAN_FLOP,loc,1,0.4);
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
