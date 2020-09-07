package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;

import spells.spellcore.Spell;

public class Spinnenkäfig extends Spell {
	
	Spider s;
	
	
	public Spinnenkäfig() {
		
		name = "§eSpinnenkäfig";
		cooldown = 20*25;
		steprange = 80;
		canHitSelf = false;
		
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		s = (Spider) spawnEntity(EntityType.SPIDER);
		noTargetEntitys.add(s);
		s.setSilent(true);
		s.setInvulnerable(true);
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		s.setVelocity(loc.getDirection().multiply(1.2));
		playSound(Sound.ENTITY_SPIDER_AMBIENT, loc, 10, 0.6);
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		loc = s.getLocation();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		p.addPassenger(s);
		playSound(Sound.ITEM_AXE_STRIP, s.getLocation(), 0.8, 1);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		ent.addPassenger(s);
		playSound(Sound.ITEM_AXE_STRIP, s.getLocation(), 0.8, 1);
		
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
		s.remove();
	}

	

	
	
}
