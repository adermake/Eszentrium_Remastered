package spells.stagespells;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class ParrotTrail extends Spell {
	
	
	public ParrotTrail(Player c,String name,double traildistance) {
		
		trail = traildistance;
		caster = c;
		this.name = name;
		
		castSpell(caster, name);
		steprange = 40;
		speed = 4;
	
	}
	
	
	boolean activate = false;
	double trail;
	boolean hover = false;
	public void activate(int delay) {
		speed = 2;
		hover = true;
		steprange = 20*5 + delay;
		loc = caster.getLocation();
		
		new BukkitRunnable() {
			public void run() {
				
				activate = true;
				loc = par.getLocation();
				loc.setDirection(caster.getLocation().getDirection().add(randVector().multiply(0.2)));
				
			}
		}.runTaskLater(main.plugin,delay);
		
		step = 0;
	}
	
	Parrot par;
	@Override
	public void setUp() {
		
		
		par = (Parrot) spawnEntity(EntityType.PARROT);
		
		
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
		if (activate) {
			loc.add(loc.getDirection());
			doPin(par,loc,5);
		}
		if (!hover)
		doPin(par,caster.getLocation().add(randVector()),trail);
		
		if (hover && !activate) {
			doPin(par,caster.getLocation(),trail);
			loc = par.getEyeLocation();
		}
		
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
		ParUtils.createParticle(Particle.EXPLOSION_LARGE, loc, 0, 0, 0, 1, 1);
		dead = true;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		par.remove();
	}

}
