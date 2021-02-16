package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class DimensionCutSeg extends Spell {

	Location spawn;
	boolean top = false;
	Vector v;
	public DimensionCutSeg(Player caster,Location loca,String name,boolean refined,boolean top,Vector v) {
		// TODO Auto-generated constructor stub
		this.caster = caster;
		this.name = name;
		this.v = v;
		this.refined = refined;
		this.top = top;
		hitEntity = false;
		hitSpell = false;
		hitPlayer = false;
		hitboxSize = 2.5;
		spawn = loca;
		steprange = 160;
		if (refined) {
			steprange = 320;
		}
		castSpell(caster, name);
		addSpellType(SpellType.MULTIHIT);
		
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.KNOCKBACK);
		
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		if (top) {
			loc = getCieling(spawn.clone(),30).add(0,1,0);
			loc.add(0,-1,0);
		}
		else {
			loc = getFloor(spawn.clone(),30).add(0,-1,0);
			loc.add(0,1,0);
		}
	
		if (loc.distance(spawn)<1.1F) {
			//Bukkit.broadcastMessage("spell is kill");
			dead = true;
			return;
		}
		
		if (top) {
			ParUtils.createFlyingParticle(Particles.SMOKE,  loc.clone().add(0,-0.3,0), 0, 4, 0, 1, 0.5, v.clone().add(new Vector(0,-1,0)));
			ParUtils.createFlyingParticle(Particles.SQUID_INK,  loc.clone().add(0,-0.3,0), 0, 0, 0, 1, 0.6, v.clone().add(new Vector(0,-0.8,0)).add(randVector().normalize().multiply(0.5)));
			ParUtils.createFlyingParticle(Particles.PORTAL, loc.clone().add(0,-0.3,0), 1, 1, 1, 1,1, v.clone().add(new Vector(0,-1,0)).multiply(-1));
			//ParUtils.createFlyingParticle(Particles.DRAGON_BREATH, loc.clone().add(0,-0.3,0), 0, 0, 0, 1,0, new Vector(0,-1,0));
		}
		else {
			ParUtils.createFlyingParticle(Particles.SMOKE,  loc.clone().add(0,0.7,0), 0, 4, 0, 1, 0.5, v.clone().add(new Vector(0,1,0)));
			ParUtils.createFlyingParticle(Particles.SQUID_INK,  loc.clone().add(0,0.7,0), 0, 0, 0, 1, 0.6, v.clone().add(new Vector(0,0.8,0)).add(randVector().normalize().multiply(0.5)));
			ParUtils.createFlyingParticle(Particles.PORTAL, loc.clone().add(0,0.7,0), 1, 1, 1, 1,1, v.clone().add(new Vector(0,0.3,0)).multiply(-1));
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	boolean active = false;

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (step == 10) {
			
			hitPlayer = true;
			hitEntity = true;
			hitSpell = true;
			active = true;
		}
		//ParUtils.createParticle(Particles.LARGE_SMOKE, loc, 0, 0, 0, 0, 0);
		if (active) {
			if (step % 60 == 0) {
				playSound(Sound.AMBIENT_CAVE,loc,0.15F,2F);
			}
			if (step % 3 == 0) {
				
			if (top) {
				ParUtils.createFlyingParticle(Particles.SMOKE,  loc.clone().add(0,-0.3,0), 0, 4, 0, 1, 0.5, v.clone().add(new Vector(0,-1,0)));
				ParUtils.createFlyingParticle(Particles.SQUID_INK,  loc.clone().add(0,-0.3,0), 0, 0, 0, 1, 0.3, v.clone().add(new Vector(0,-0.8,0)).add(randVector().normalize().multiply(0.5)));
				
				if (refined && step % 8 == 0) {
					ParUtils.createFlyingParticle(Particles.SNEEZE, loc.clone().add(0,-0.3,0), 1, 1, 1, 2,0, v.clone().add(new Vector(0,-1,0)).multiply(-1));
				}
				
					ParUtils.createFlyingParticle(Particles.PORTAL, loc.clone().add(0,-0.3,0), 8, 8, 8, 1,2, v.clone().add(new Vector(0,-1,0)).multiply(-1));
				
				
				//ParUtils.createFlyingParticle(Particles.DRAGON_BREATH, loc.clone().add(0,-0.3,0), 0, 0, 0, 1,0, new Vector(0,-1,0));
			}
			else {
				if (refined && step % 8 == 0) {
					ParUtils.createFlyingParticle(Particles.SNEEZE, loc.clone().add(0,0.7,0), 1,1, 1, 2,0, v.clone().add(new Vector(0,0.3,0)).multiply(-1));
				}
				
					ParUtils.createFlyingParticle(Particles.PORTAL, loc.clone().add(0,0.7,0), 8,8, 8, 1,2, v.clone().add(new Vector(0,0.3,0)).multiply(-1));
				
				ParUtils.createFlyingParticle(Particles.SMOKE,  loc.clone().add(0,0.7,0), 0, 4, 0, 1, 0.5, v.clone().add(new Vector(0,1,0)));
				ParUtils.createFlyingParticle(Particles.SQUID_INK,  loc.clone().add(0,0.7,0), 0, 0, 0, 1, 0.3, v.clone().add(new Vector(0,0.8,0)).add(randVector().normalize().multiply(0.5)));
				
			}
			}
		}else {
			
			
		}
		
		
		
		//ParUtils.createFlyingParticle(Particles.DRAGON_BREATH, loc.clone().add(0,-0.7,0), 0, 0, 0, 3, 0.1, new Vector(0,1,0));
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
	int tpdelay = 10;
	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
		if (step < 15) {
			if (refined) {
				p.damage(12);
			}
			else {
				p.damage(7);
			}
		}
		swapTp(p);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		if (step < 15) {
			if (refined) {
				ent.damage(9);
			}
			else {
				ent.damage(7);
			}
			
		}
		swapTp(ent);
	}

	
	public void swapTp(LivingEntity e) {
		ParUtils.createParticle(Particles.FLASH, e.getLocation(), 0, 0, 0, 1, 1);
		playSound(Sound.ITEM_CHORUS_FRUIT_TELEPORT,loc,1,0.6F);
		//hitEntitys.add(e);
		Location l1 = loc.clone();
		l1.setY(e.getLocation().getY());
		Vector x = l1.toVector().subtract(e.getLocation().toVector());
		Vector vcl = v.clone();
		vcl.setY(0);
		vcl =vcl.rotateAroundAxis(new Vector(0,1,0),90);
		Vector dir = vcl.clone().normalize().multiply(-1);
		if (Math.abs(vcl.angle(x)) <= Math.abs(vcl.clone().multiply(-1).angle(x))) {
			//Bukkit.broadcastMessage("LEFT");
			dir = vcl.clone().normalize();
		}
		dir = dir.setY(0);
		dir = dir.normalize().multiply(hitboxSize*2+3);
		
		Location lend = getTop(e.getLocation().add(dir));
		lend.setDirection(e.getLocation().getDirection());
		e.teleport(lend);
		playSound(Sound.ITEM_CHORUS_FRUIT_TELEPORT,lend,1,0.6F);
		new BukkitRunnable() {
			public void run() {
				hitEntitys.remove(e);
			}
		}.runTaskLater(main.plugin,tpdelay);
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
