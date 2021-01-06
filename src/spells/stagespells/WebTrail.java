package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import spells.spellcore.Spell;

public class WebTrail extends Spell {

	
	Spell trail;
	FallingBlock web;
	double power = 0;
	public WebTrail(Spell trail,Player caster,String name,double power) {
		this.trail = trail;
		this.power = power;
		castSpell(caster, name);
		count = (int) (power *11);
		
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = trail.getLocation();
		web = trail.getLocation().getWorld().spawnFallingBlock(trail.getLocation(), Material.COBWEB,(byte)0);
		last = web.getLocation();
		web.setGravity(false);
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	Location last;
	int count = 0;
	@Override
	public void move() {
		boolean webded = false;
		if (web.getLocation().distance(last)<= 0.01) {
			webded = true;
		}
		// TODO Auto-generated method stub
		// 
		
		if (isInGround()&& webded && !trail.isDead()) {
			
			
			dead = true;
			//Bukkit.broadcastMessage("RESPAWN");
			ParUtils.debug(trail.getLocation().add(0,0,0));
			//caster.teleport(trail.getLocation());
			web = trail.getLocation().getWorld().spawnFallingBlock(trail.getLocation(), Material.COBWEB,(byte)0);
			web.setGravity(false);
		}
		
		
		Bukkit.broadcastMessage(""+webded);
		
		if (trail.isDead()) {
			count--;
			if (count <= 0) {
				dead = true;
				web.remove();
				loc = last;
			}
			
		}
		else {
			loc = web.getLocation();
			doPin(web,trail.getLocation(),2.7f);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
