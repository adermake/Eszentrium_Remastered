package spells.stagespells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class LamaturmProjectile extends Spell {

	Location origin;
	Location realLoc;
	public LamaturmProjectile(Player p,Location origin,Entity nohit, String namae) {
		name = namae;
		hitEntity = true;
		hitPlayer = true;
		hitSpell = true;
		steprange = 200;
		speed = 80;
		realLoc = origin.clone();
		this.origin = origin.clone();
		noTargetEntitys.add(nohit);
		castSpell(p, name);
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = realLoc;
		
		playSound(Sound.ENTITY_LLAMA_SPIT,loc,4,1);
		playSound(Sound.ENTITY_LLAMA_SPIT,loc,4,1.5F);
		playSound(Sound.ENTITY_LLAMA_SPIT,loc,4,0.5F);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		ParUtils.createFlyingParticle(Particles.CLOUD, loc, 0, 0, 0, 1, 5, loc.getDirection());
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		loc.add(loc.getDirection().multiply(0.5));
		
		
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		double flsteprange = steprange;
		double flstep = step;
		float sFlot = (float) (flstep/flsteprange);
		//ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.WHITE, sFlot*9);
		ParUtils.createFlyingParticle(Particles.CLOUD, loc, 0, 0, 0, 1, 5, loc.getDirection());
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		ParUtils.parKreisDot(Particles.CLOUD, p.getLocation(), 2, 0, 2, loc.getDirection());
		doKnockback(p, origin, 4);
		damage(p,5,caster);
		playSound(Sound.ENTITY_GUARDIAN_FLOP, loc, 3, 1);
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		ParUtils.parKreisDot(Particles.CLOUD, ent.getLocation(), 2, 0, 2, loc.getDirection());
		doKnockback(ent, origin, 4);
		ent.damage(5);
		playSound(Sound.ENTITY_GUARDIAN_FLOP, loc, 3, 1);
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_GUARDIAN_FLOP, loc, 3, 1);
		dead = true;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

	

}
