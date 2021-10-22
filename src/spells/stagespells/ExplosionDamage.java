package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;

import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class ExplosionDamage extends Spell {

	double damage = 0;
	
	Location overrideLoc;
	public ExplosionDamage(double size,double damage,Player caster,Location loca, String namae) {
		this.caster = caster;
		name = namae;
		hitboxSize = size;
		steprange = 1;
		this.damage = damage;
		overrideLoc = loca;
		hitSpell = true;
		canHitSelf = false;
		castSpell(caster, name);
		
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.DAMAGE);

	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = overrideLoc;
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
		
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p,damage,caster);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		damage(ent,damage,caster);
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
