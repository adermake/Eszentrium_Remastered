package spells.stagespells;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import spells.spellcore.Spell;

public class Tnt extends Spell {

	TNTPrimed tnt;
	
	
	public Tnt(Player c,String n) {
		name = n;
		caster = c;
		castSpell(c, n);
		steprange = 30;
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_TNT_PRIMED,loc,4,1);
		tnt  = (TNTPrimed) spawnEntity(EntityType.PRIMED_TNT);
		tnt.setFuseTicks(30);
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
		loc = tnt.getLocation();
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
		new Explosion(8, 4, 2, 1,caster, loc, name);
	}

}
