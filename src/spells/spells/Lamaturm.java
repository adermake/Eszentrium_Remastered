package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLlama;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.EntityLlama;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Cooldowns;
import spells.spellcore.EventCollector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.LamaturmProjectile;

public class Lamaturm extends Spell {

	Player target;
	Llama turret;
	public Lamaturm() {
		name = "§6Lamaturm";
		cooldown = 20 * 75;
		steprange = 20 * 50;
		hitboxSize = 10;
		multihit = true;
		
		
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.AURA);
		setLore("§7Beschwört ein Lama Blickrichtung, auf#§7das der Spieler sich setzen kann. Das Lama#§7kann von Gegnern getötet werden.# #§eF:§7#§7Falls der Spieler auf dem Lama sitzt, wird#§7ein Projektil in Blickrichtung geschossen,#§7dass Gegnern Schaden zufügt und sie#§7wegschleudert.Die Anzahl der Schüsse ist begrenzt,#§7regeneriert sich aber über Zeit.");
		setBetterLore("§7Beschwört ein Lama Blickrichtung, auf#§7das der Spieler sich setzen kann. Das Lama#§7kann von Gegnern getötet werden.# #§eF:§7#§7Falls der Spieler auf dem Lama sitzt, wird#§7ein Projektil in Blickrichtung geschossen,#§7dass Gegnern Schaden zufügt und sie#§7wegschleudert.Die Anzahl der Schüsse ist begrenzt,#§7regeneriert sich aber über Zeit.");
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
			turret.setMaxHealth(10);
			turret.setJumpStrength(0);
			turret.setTamed(true);
			turret.setAdult();
			turret.setCollidable(false);
			turret.setCarryingChest(true);
			noTargetEntitys.add(turret);
			playSound(Sound.ENTITY_LLAMA_HURT,loc,4,1);
		}
		
		if (refined) {
			maxShots = 10;
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
	int shots = 3;
	int realDelay = 0;
	int maxShots = 5;
	boolean shootnow = false;
	@Override
	public void move() {
		if (turret == null)
			return;
		realDelay++;
		//realDelay = /*(realDelay == -1) ? -1 : */realDelay++;
		shootDelay++;
		if (refined) {
			shootDelay+=1;
		}
		if ((shootDelay > 80 && shots < maxShots)) {
			shots++;
			playSound(Sound.ENTITY_LLAMA_ANGRY,loc,4,1);
			
			shootDelay = 0;
		}
		if (realDelay>3) {
			realDelay = 0;
		}
		
		if (turret.getPassengers().contains(caster)) {
			
			loc.setDirection(caster.getLocation().getDirection());
				
			if (swap() ) {
				
				if (shots>0 ) {
					
					
					
					shots--;
					
					
					
					shootnow = false;
					if (EventCollector.quickSwap.contains(caster)) {
						EventCollector.quickSwap.remove(caster);
					}
					realDelay++;
					Location ori = caster.getEyeLocation();
					ori.add(caster.getEyeLocation().getDirection().multiply(2));
					ori.setDirection(caster.getEyeLocation().getDirection());
					new LamaturmProjectile(caster,ori,turret,name);
					
				}
			}
			
			for (double i = 0;i<shots;i++) {
				Location l = ParUtils.stepCalcCircle(turret.getEyeLocation().clone(), 2, loc.getDirection(), 3, step+(i*44/maxShots));
				
				ParUtils.createParticle(Particles.BUBBLE, l.clone().add(0,1,0), 0, 0, 0, 5, 0);
			}
			
		}
		else {
			shootnow = false;
			loc.setDirection(caster.getLocation().getDirection());
			
			for (double i = 0;i<shots;i++) {
				Location l = ParUtils.stepCalcCircle(turret.getEyeLocation().clone(), 2, new Vector(0,1,0), -1, step+(i*44/maxShots));
				
				ParUtils.createParticle(Particles.BUBBLE, l.clone().add(0,-1,0), 0, 0, 0, 5, 0);
			}
			
		}
		
		if (turret.getVelocity().length()<0.1) {
			EntityLlama nmsEnt = ((CraftLlama)turret).getHandle();
			nmsEnt.setHeadRotation(loc.getYaw());
			
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
