package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Eggsplosive extends Spell {

	Location overrideLoc;
	public Eggsplosive(Player p,Location loca,String namae) {
		name = namae;
		overrideLoc = loca;
		castSpell(p,name);
		steprange = 200;
		addSpellType(SpellType.DAMAGE);
		
		addSpellType(SpellType.PROJECTILE);
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = overrideLoc;
		bindEntity(caster.getWorld().spawnEntity(loc, EntityType.EGG));
		
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
		loc = spellEnt.getLocation();
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
		new Explosion(2,9,1,2,caster,loc, name);
		
	}
	

}
