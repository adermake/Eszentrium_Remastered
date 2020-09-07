package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class LamaturmProjectile extends Spell {

	Location origin;
	Location realLoc;
	public LamaturmProjectile(Player p,Location origin,Entity nohit, String namae) {
		name = namae;
		hitEntity = true;
		hitPlayer = true;
		hitSpell = true;
		steprange = 20;
		speed = 2;
		realLoc = origin;
		this.origin = origin;
		noTargetEntitys.add(nohit);
		castSpell(p, name);
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = realLoc;
		playSound(Sound.ENTITY_LLAMA_SPIT,loc,4,1);
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
		loc.add(loc.getDirection().multiply(0.5));
		loc.add(0,-0.1,0);
		
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.SPIT, loc, 0, 0, 0, 1, 0);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		doKnockback(p, origin, 4);
		damage(p,3,caster);
		playSound(Sound.ENTITY_GUARDIAN_FLOP, loc, 3, 1);
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		doKnockback(ent, origin, 4);
		ent.damage(3);
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
