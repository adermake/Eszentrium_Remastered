package spells.spells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class Pfeilsucher extends Spell{
	
	Location a;
	public Pfeilsucher(Player p,String name,Location g) {
		
		a=g;
		hitboxSize = 2;
		hitPlayer = true;
		hitSpell = true;
		
		steprange = 140;
		castSpell(p, name);
	}
	
	Arrow mave;
	Vector velocity = new Vector(0,4,0);
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc=a;
		mave = (Arrow) spawnEntity(EntityType.ARROW);
		mave.setGravity(false);
		mave.setDamage(0);
		playSound(Sound.ENTITY_ARROW_SHOOT,loc,0.1f,1f);
		
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
		mave.setVelocity(velocity);
		LivingEntity target = getNearestEntity(caster, loc, 50);
		if (target != null) {
			Vector s = target.getLocation().toVector().subtract(loc.toVector()).normalize().multiply(0.1);
			velocity.add(s);
		}
		if(velocity.length()>0.5)
			velocity.normalize().multiply(0.5);
		loc = mave.getLocation();
		if (mave.isOnGround())
			dead = true;
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particle.WITCH, loc, 0, 0, 0, 1, 0);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p, 2,caster);
		playSound(Sound.ENTITY_ARROW_HIT,loc,0.1f,1f);
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_ARROW_HIT,loc,0.1f,1f);
		damage(ent, 2,caster);
		dead = true;
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
		mave.remove();
	}

}
