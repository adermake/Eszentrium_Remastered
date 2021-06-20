package spells.spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import spells.spellcore.Spell;

public class PotionSeller extends Spell {

	
	HashMap<Integer,ThrownPotion> potions = new HashMap<Integer,ThrownPotion>();
	
	public PotionSeller() {
		
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		potions.put(0,(ThrownPotion) spawnEntity(EntityType.SPLASH_POTION));
		potions.put(1,(ThrownPotion) spawnEntity(EntityType.SPLASH_POTION));
		potions.put(2,(ThrownPotion) spawnEntity(EntityType.SPLASH_POTION));
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
		
		
		for (float i = 0;i<3;i++) {
			Location holoLoc = caster.getLocation();
			holoLoc.setDirection(loc.getDirection());
			Location l1 = ParUtils.stepCalcCircle(holoLoc, 3, new Vector(0,1,0), 1,7+ i*5);
			if (potions.get((int) i).isDead()) {
				potions.put((int) i,(ThrownPotion) spawnEntity(EntityType.SPLASH_POTION,l1));
			}
			doPin(potions.get((int) i),l1);
		}
		
		
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
