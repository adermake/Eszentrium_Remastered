package spells.spells;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.stagespells.KaminchenEntity;

public class Kaminchen extends Spell {

	
	public Kaminchen() {
		cooldown = 20 * 25;
		name = "§6Kaminchen";
		
	}
	
	
	@Override
	public void setUp() {
		
		
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		new KaminchenEntity(caster,caster.getLocation().getDirection(), name);
		if (refined) {
			Location dirLoc = caster.getLocation();
			dirLoc.setYaw(dirLoc.getYaw()+45);
			new KaminchenEntity(caster,dirLoc.getDirection(), name);
			dirLoc.setYaw(dirLoc.getYaw()-90);
			new KaminchenEntity(caster,dirLoc.getDirection(), name);
		}
		
		dead = true;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
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
