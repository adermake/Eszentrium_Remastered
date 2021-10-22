package spells.spells;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class Creepermissle extends Spell {

	
	public Creepermissle() {
		name = "§eCreepermissle";
		steprange = 20 * 5;
	}
	Location ori;
	Creeper cre;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		cre = (Creeper) spawnEntity(EntityType.CREEPER);
		ori = loc.clone();
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
		cre.setVelocity(cre.getVelocity().add(new Vector(0,0.15,0)));
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createFlyingParticle(Particle.FLAME, cre.getLocation().add(0,-2,0), 0.2F, 0.1F, 0.2F, 5, 0.3F, cre.getVelocity().normalize().multiply(-1));
		
		if (step % 5 == 0) {
			ParUtils.parKreisDot(Particle.SMOKE_NORMAL, cre.getLocation(), 2, 0, 0.05, cre.getVelocity());
		}
		if (step < 30)
		ParUtils.createParticle(Particle.SMOKE_LARGE, loc, 0, 0, 0, 10, 1);
		//ParUtils.createFlyingParticle(Particle.SMOKE_NORMAL, cre.getLocation(), 0.1F, 0.1F, 0.1F, 5, 1, cre.getVelocity().normalize().multiply(-0.5));
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
		// TODO Auto-generated method stub
		
	}

}
