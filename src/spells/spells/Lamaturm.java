package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Cooldowns;
import spells.spellcore.Spell;
import spells.stagespells.LamaturmProjectile;

public class Lamaturm extends Spell {

	Player target;
	Llama turret;
	public Lamaturm() {
		name = "§6Lamaturm";
		cooldown = 20 * 55;
		steprange = 20 * 50;
		hitboxSize = 10;
		multihit = true;
		
	}
	
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = null;
		
		loc = block(caster,10);
		if (loc == null) {
			
			refund = true;
			dead = true;
		}
		else {
			loc = getTop(loc).add(0,-0.5,0);
			turret = (Llama) caster.getWorld().spawnEntity(loc, EntityType.LLAMA);
			bindEntity(turret);
			turret.setJumpStrength(0);
			turret.setTamed(false);
			turret.setAdult();
			turret.setCollidable(false);
			turret.setCarryingChest(true);
			noTargetEntitys.add(turret);
			playSound(Sound.ENTITY_LLAMA_HURT,loc,4,1);
		}
		
		if (refined) {
			maxShots = 16;
		}
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		if (turret != null)
		loc = turret.getLocation();
	}
	int shootDelay = 0;
	int shots = 1;
	int realDelay = 0;
	int maxShots = 5;
	@Override
	public void move() {
		if (turret == null)
			return;
		realDelay++;
		//realDelay = /*(realDelay == -1) ? -1 : */realDelay++;
		shootDelay++;
		if (refined) {
			shootDelay+=10;
		}
		if (shootDelay > 20 && shots < maxShots) {
			shots++;
			playSound(Sound.ENTITY_LLAMA_ANGRY,loc,4,1);
			
			shootDelay = 0;
		}
		if (realDelay>5) {
			realDelay = 0;
		}
		
		if (target != null && target.getLocation().distance(loc)<10) {
			
			loc.setDirection(target.getLocation().toVector().subtract(loc.toVector()));
			if (realDelay == 0 && shots>0) {
				shots--;
				realDelay++;
				new LamaturmProjectile(caster,turret.getEyeLocation(),turret,name);
			}
			
			
		}
		else {
			loc.setYaw(0);
			loc.setPitch(loc.getPitch()+8);
		}
		
		if (turret.getVelocity().length()<0.1) {
			turret.teleport(loc);
		}
		else {
			loc =turret.getLocation();
		}
		
		
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void display() {
		if (turret == null)
			return;
		// TODO Auto-generated method stub
		for (double i = 0;i<shots;i++) {
			
			Location l = ParUtils.stepCalcCircle(turret.getEyeLocation().clone(), 2, new Vector(0,1,0), -1, step+(i*44/maxShots));
			ParUtils.createParticle(Particles.BUBBLE, l.clone().add(0,-1,0), 0, 0, 0, 5, 0);
		}
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (target == null) {
			target = p;
			
		}
		else {
			if (p.getLocation().distance(loc)< target.getLocation().distance(loc)) {
				target = p;
			}
		}
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
		if (turret != null)
			turret.remove();
	}


	
	/*
	public void addShot(Entity e) {
		
		ArmorStand a = (ArmorStand) e.getWorld().spawnEntity(e.getLocation(), EntityType.ARMOR_STAND);
		a.setInvulnerable(true);
		a.setVisible(false);
		a.setSmall(true);
		a.setHelmet(new ItemStack(Material.SNOW_BLOCK));
		while (e.getPassengers().size()>0) {
			e = e.getPassengers().get(0);
		}
		e.addPassenger(a);
	}
	
	public void removeShot(Entity e) {
		Entity ent = e;
		
		while (e.getPassengers().size()>0) {
			e = e.getPassengers().get(0);
		}
		if (ent != e)
		e.remove();
	}
	*/
}
