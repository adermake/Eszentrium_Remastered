package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;

import org.bukkit.Particle;
import spells.spellcore.Spell;

public class ArchonRing extends Spell {
	Location overrideLoc;
	Particle p;
	public ArchonRing(String name,Player caster,Location loca,double radius,double speed,Particle p) {
		this.name = name;
		this.p = p;
		overrideLoc = loca;
		castSpell(caster,name);
		this.speed = speed;
		this.caster = caster;
		this.radius = radius;
	}
	public double radius = 3;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	float yaw = randInt(-180, 180);
	float pitch = randInt(-180, 180);
	public double mult = 1;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		yaw++;
		pitch++;
		loc.setPitch(pitch);
		loc.setYaw(yaw);
		Vector d = loc.getDirection();
		
		if (caster.isSneaking()) {
			mult += 0.3F;
			if (mult >5) {
				mult = 5;
				ParUtils.dropItemEffectRandomVector(ParUtils.stepCalcCircle(caster.getLocation().add(0,0.8,0), radius*mult, d, 0, step), Material.NETHER_STAR, 1, 3, 0);
			}
		}
		else {
			mult -= 0.3;
			if (mult <1) {
				mult = 1;
			}
		}
		
		ParUtils.createParticle(p, ParUtils.stepCalcCircle(caster.getLocation().add(0,1,0), radius*mult, d, 0, step), 0, 0, 0, 5, 0);
		ParUtils.createParticle(Particle.BUBBLE_POP, ParUtils.stepCalcCircle(caster.getLocation().add(0,1,0), radius*mult, d, 0, step), 0, 0, 0, 5, 0);
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
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
