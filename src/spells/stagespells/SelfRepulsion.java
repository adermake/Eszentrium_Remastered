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

public class SelfRepulsion extends Spell {

	double damage = 0;
	double knockback = 0;
	float pitch = 1;
	Location overrideLoc;
	public SelfRepulsion(double size,double knockback,Player caster,Location loca, String namae) {
		hitboxSize = size;
		steprange = 1;
		this.pitch = pitch;
		this.knockback = knockback;
		overrideLoc = loca;
		name = namae;
		canHitSelf = true;
		castSpell(caster, name);
		

		addSpellType(SpellType.AURA);
		addSpellType(SpellType.MOBILITY);
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
		if (p != caster)
			return;
		// TODO Auto-generated method stub
		tagPlayer(p);
		
		doKnockback(p, loc, knockback);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		
		
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
