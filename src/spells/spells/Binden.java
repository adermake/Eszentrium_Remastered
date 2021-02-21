package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;

public class Binden extends Spell {

	public Binden() {
		hitSpell = false;
		steprange = 20 * 5;
	}
	
	Spell chase = null;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		chase = pointSpell(caster);
		Bukkit.broadcastMessage(""+chase.getName());
		if (chase == null) 
			dead = true;
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
		if (chase.isDead()) {
			dead = true;
			return;
		}
		doPin(caster,chase.getLocation(),1);
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
