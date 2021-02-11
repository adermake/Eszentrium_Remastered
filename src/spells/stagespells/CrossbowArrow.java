package spells.stagespells;

import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class CrossbowArrow extends Spell {
	Vector v;
	ArmorStand a;
	public CrossbowArrow(Player p,ArmorStand a,Vector v,String name,boolean refined) {
		this.a = a;
		speed = 10;
		this.v = v;
		hitboxSize = 1;
		steprange = 400;
		castSpell(p, name);
		
	}
	Arrow ar ;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = a.getLocation().add(0,1,0);
		loc.setDirection(v);
		ar = (Arrow) spawnEntity(EntityType.ARROW,a.getLocation().add(v.normalize()).add(new Vector(0,1,0)));
		ar.setVelocity(v.multiply(5));
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
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createFlyingParticle(Particles.CRIT, loc, 0, 0, 0, 1, 1, v);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		ParUtils.parKreisDot(Particles.CRIT, loc.clone(), 1, 0, 4, v);
		if (refined) {
			damage(p, 3, caster);
		}
		else {
			damage(p, 2, caster);
		}
		doKnockback(p, a.getLocation(), 3);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		ParUtils.parKreisDot(Particles.CRIT, loc.clone(), 1, 0, 2, v);
		
		if (refined) {
			damage(ent, 3, caster);
		}
		else {
			damage(ent, 2, caster);
		}
		doKnockback(ent, a.getLocation(), 3);
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		dead = true;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		ar.remove();
	}

}