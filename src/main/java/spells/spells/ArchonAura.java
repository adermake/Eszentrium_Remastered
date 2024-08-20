package spells.spells;

import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.ArchonRing;

public class ArchonAura extends Spell {
	Player overrideCaster;
	public ArchonAura(Player caster,String name) {
		name = "§3Auran";
		hitSpell = false;
		hitPlayer = true;
		hitBlock = false;
		hitEntity = true;
		
		
		speed = 1;
		hitboxSize = 1;
		multihit = true;
		castSpell(caster, name);
		overrideCaster = caster;
		addSpellType(SpellType.AURA);
	}
	ArchonRing ar;
	@Override
	public void setUp() {
		Particle pt = Particle.BUBBLE;
		ar = new ArchonRing(name, caster, caster.getLocation(), 1.3,1,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1,0.5F,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1,1,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1.3,1,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1,2,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1,0.5,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1.3,1,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1,0.5F,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1,1,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1.3,1,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1,2,pt);
		new ArchonRing(name, caster, caster.getLocation(), 1,0.5,pt);
		caster = overrideCaster;
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		caster = overrideCaster;
	}
	
	
	@Override
	public void move() {
		caster = overrideCaster;
		loc = caster.getLocation();
		
		hitboxSize = ar.mult* ar.radius;
		
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particle.CLOUD, loc, 0.03F, 0.03F, 0.03F, 2, 0F);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p, 1, caster);
		doKnockback(p, caster.getLocation(), 1);
		ParUtils.createParticle(Particle.FIREWORK, p.getLocation(), 0, 0, 0, 5, 1);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent, 1, caster);
		doKnockback(ent, caster.getLocation(), 1);
		ParUtils.createParticle(Particle.FIREWORK, ent.getLocation(), 0, 0, 0, 5, 1);
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
