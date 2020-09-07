package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;



import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class Bubble extends Spell {
	
	Location overrideLoc;
	Vector vel;
	
	public Bubble(Location l,Player c,String namae) {
		vel = randVector().normalize().multiply(0.4).add(l.getDirection()).normalize();
		
		caster = c;
		overrideLoc = l;
		
		
		hitSpell = true;
		steprange = 200;
		speed =2 ;
		hitboxSize = 0.3;
		name = namae;
		castSpell(caster,namae);
	}
	int swapTime = 100;
	@Override
	public void setUp() {
		swapTime += randInt(-10, 30);
		loc = overrideLoc;
		SoundUtils.playSound(Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, loc,2F,0.1F);
		
	}

	@Override
	public void cast() {
		
	}

	@Override
	public void launch() {
		Player nera = getNearestPlayer(caster,loc,10);
		if (nera != null) {
			target = nera;
			
			away = target.getLocation().toVector().subtract(loc.toVector()).normalize();
		}
	
	}
	Player target;
	float sp = 1;
	Vector away ;
	@Override
	public void move() {
		if (step % 10 == 0) {
			Player nera = getNearestPlayer(caster,loc,10);
			if (nera != null) {
				
				target = nera;
				
				away = target.getLocation().toVector().subtract(loc.toVector()).normalize();
			}
			
		}
		if (step == swapTime) {
			SoundUtils.playSound(Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, loc,2F,0.1F);
			ParUtils.createParticle(Particles.SWEEP_ATTACK, loc, 1, 1, 1, 1, 1);
		}
		if (step<swapTime) {
			loc.add(vel.clone().multiply(sp));
			loc.setDirection(vel);
			sp-= 0.04;
			if (sp<0.1) {
				sp = 0.1F;
			}
			if (target != null && away != null)
			loc.add(away.multiply(sp));
		}
		else {
			if (target == null) {
				Player nera = getNearestPlayer(caster,loc,10);
				if (nera != null) {
					if (target == null)
						
					target = nera;
					
				}
				else {
					dead = true;
				}
				
			}
			else {
				Vector toTarget = target.getLocation().toVector().subtract(loc.toVector()).normalize();;
				
				loc.add(toTarget.multiply(sp));
				loc.setDirection(toTarget);
				sp+= 0.01;
				if (sp>1) {
					sp = 1F;
				}
			}
			
			
		}
		
		
	}
	
	@Override
	public void display() {
		ParUtils.createParticle(Particles.BUBBLE, loc, 0, 0, 0, 5, 0);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		tagPlayer(p);
		hitEnt(p);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		hitEnt(ent);
		
	}
	
	public void hitEnt(LivingEntity ent) {
		ent.setVelocity(loc.getDirection().normalize().multiply(2));
		damage(ent, 1, caster);
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
	}

	@Override
	public void onDeath() {
		ParUtils.createParticle(Particles.EXPLOSION, loc, 0, 0, 0, 1, 1);
		SoundUtils.playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, loc,2F,0.1F);
	}
	
	public Vector lerp(Vector start, Vector end,double val) {
		return new Vector(lerp(start.getX(),end.getX(),val),lerp(start.getY(),end.getY(),val),lerp(start.getZ(),end.getZ(),val));
	}
	
	public double lerp(double start, double end,double val) {
		return ((end - start)*val)+ start;
	}
}
