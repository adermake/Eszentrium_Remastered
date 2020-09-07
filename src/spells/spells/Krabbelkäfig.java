package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;

import spells.spellcore.Spell;

public class Krabbelkäfig extends Spell {
	
	Spider s;
	ArmorStand a; 
	
	
	public Krabbelkäfig() {
		
		name = "§eKrabbelkäfig";
		cooldown = 20*1;
		steprange = 80;
		canHitSelf = false;
		multihit = false;
		
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		s = (Spider) spawnEntity(EntityType.SPIDER);
		noTargetEntitys.add(s);
		s.setSilent(true);
		s.setInvulnerable(true);
		a = (ArmorStand) spawnEntity(EntityType.ARMOR_STAND);
		noTargetEntitys.add(a);
		a.setInvulnerable(true);
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		s.setVelocity(loc.getDirection().multiply(1.2));
		a.setVelocity(loc.getDirection().multiply(1.2));
		
		playSound(Sound.ENTITY_SPIDER_AMBIENT, loc, 10, 0.7);
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		loc = a.getLocation();
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
		//ent.addPassenger(a);
		a.addPassenger(s);
		playSound(Sound.ITEM_AXE_STRIP, s.getLocation(), 0.8, 1);
		
		//while(dead != true && ent != s) {
		//	Bukkit.broadcastMessage("ayey");
		//	a.getLocation().setX(ent.getLocation().getX());
		//	a.getLocation().setY(ent.getLocation().getY());
		//	a.getLocation().setZ(ent.getLocation().getZ());
		//	Bukkit.broadcastMessage("ayy");
		//}
		
		Bukkit.broadcastMessage("ye");
		
		
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
		a.remove();
	}

	
	
	
}
