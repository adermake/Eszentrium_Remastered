package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class Meteor extends Spell {

	Location overrideLoc;
	Vector direction;
	public Meteor(Location loca,Player caster, String namae) {
		overrideLoc = loca;
		hitboxSize = 2;
		name = namae;
		castSpell(caster, name);
	}
	
	FallingBlock fb;
	@Override
	public void setUp() {
		loc = overrideLoc;
		fb = caster.getWorld().spawnFallingBlock(loc, Material.MAGMA_BLOCK, (byte)0);
		FallingBlock fb2 = caster.getWorld().spawnFallingBlock(loc.clone().add(0,1,0), Material.FIRE,(byte)0);
		fb.addPassenger(fb2);
		fb.setVelocity(fb.getVelocity().setY(-4));
		loc.setDirection(new Vector(0, -1, 0));
		fb.setFireTicks(100);
		bindEntity(fb);
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
		Vector dir = loc.getDirection();
		loc = fb.getLocation();
		loc = loc.setDirection(dir.add(new Vector(0, -0.1, 0)));
		if (caster.isSneaking()) {
			Vector v = fb.getVelocity();
			v.setX(caster.getLocation().getDirection().getX());
			v.setZ(caster.getLocation().getDirection().getZ());
			fb.setVelocity(v);
		}
		fb.setVelocity(loc.getDirection().normalize().multiply(4));
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.EXPLOSION, loc, 0, -1, 0, 0, 2);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (!boundOnGround) {
			p.setVelocity(p.getVelocity().setY(-3));
			damage(p,4,caster);
		}
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		if (!boundOnGround) {
			ent.setVelocity(ent.getVelocity().setY(-3));
			damage(ent,4,caster);
		}
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		//new ExplosionDamage(4, 8, caster, loc);
		
		new Explosion(6, 9, 0.5F, 1, caster, loc, name);
		dead = true;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

	

}
