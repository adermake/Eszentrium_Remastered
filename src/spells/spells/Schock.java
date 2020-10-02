package spells.spells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.stagespells.SchockLaser;

public class Schock extends Spell {

	
	public Schock() {
		cooldown = 20 * 35;
		name = "§eSchock";
		casttime =  3;
		aim = null;
		
		
		
	}
	Location aim;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		if (refined) {
			
		
		aim = block(caster);
		if (aim == null) {
			refund = true;
		}
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		if (refined) {
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined);
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined);
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined);
			
		}
		else {
			new SchockLaser(caster,name,refined);
			new SchockLaser(caster,name,refined);
			new SchockLaser(caster,name,refined);
		}
		
		playSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER,caster.getLocation(),8F,2f);
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		dead = true;
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
