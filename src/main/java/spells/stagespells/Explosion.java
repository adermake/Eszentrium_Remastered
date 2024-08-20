package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;

import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Explosion extends Spell {

	double damage = 0;
	double knockback = 0;
	float pitch = 1;
	Location overrideLoc;
	
	public Explosion(double size,double damage,double knockback,float pitch,Player caster,Location loca, String namae) {
		hitboxSize = size;
		steprange = 1;
		hitEntity = true;
		hitSpell = true;
		this.damage = damage;
		this.pitch = pitch;
		this.knockback = knockback;
		overrideLoc = loca;
		name = namae;
		castSpell(caster, name);
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.AURA);
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
		playSound(Sound.ENTITY_GENERIC_EXPLODE, loc,5, pitch);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void display() {
		if (hitboxSize>2) {
			ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, hitboxSize/3, hitboxSize/3, hitboxSize/3, (int)hitboxSize/2, 0);
		}
		ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, 0, 0, 0, 3, 0);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p,damage,caster);
		doKnockback(p, loc, knockback);
		
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		damage(ent,damage,caster);
		doKnockback(ent, loc, knockback);
		
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
