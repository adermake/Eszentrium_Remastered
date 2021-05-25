package spells.stagespells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.spells.Kettenbrecher;

public class ChainSegment extends Spell {

	public ChainSegment last;
	public HashMap<Entity,Integer> sticked = new HashMap<Entity,Integer>();
	public ArrayList<Entity> cd = new ArrayList<Entity>();
	public Vector vel = new Vector(0,0,0);
	Kettenbrecher kb;
	int randId = 0;
	
	Item chain;
	ArmorStand a;
	boolean crit = false;
	public ChainSegment(Player p,String name,ChainSegment last,int i,Kettenbrecher k) {
		kb = k;
		if (last == null) {
			loc = p.getEyeLocation().add(0,-5,0);
		}
		else {
			loc = last.getLocation();
		}
		
		
		randId = i;
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.PROJECTILE);
		addSpellType(SpellType.KNOCKBACK);
		multihit = true;
		hitboxSize = 0.2F;
		//double dc = ((double)i/25);
		//steprange = (int)(20 * 6-(dc));
		
		/*
		 * 	ItemStack im = new ItemStack(Material.IRON_BARS);
		
		ItemMeta imet = im.getItemMeta();
		imet.setDisplayName(""+i);
		im.setItemMeta(imet);
		chain = loc.getWorld().dropItem(loc, im);
		chain.setGravity(false);
	
		
		chain.setPickupDelay(1000);
		 */
		
		
			
		
		/*
	
		*/
			
		a = createArmorStand(loc);
		//a.setVisible(true);
		a.setSmall(true);
		a.setSilent(true);
		a.setCollidable(false);
			a.setHelmet(new ItemStack(Material.ANVIL));
		
		
		
		
		a.setGravity(true);
		
		
		
		if (i % 4 != 0) {
			if (last != null)
				lazySpell = true;
		}
		this.last = last;
		castSpell(p, name);
	}
	
	@Override
	public void setUp() {
		if (last == null)
			hitboxSize = 2;
		// TODO Auto-generated method stub
		lastLoc = loc.clone();
		lastVel = new Vector(0,0,0);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	double drag = 0.01F;
	double space = 0.5F;
	Location lastLoc;
	Vector lastVel;
	double length = 16;
	double maxlength = 16;
	double lastLength;
	double speedGainFactor = 0.1F;
	
	int zeroCount = 0;
	@Override
	public void move() {
		
		// TODO Auto-generated method stub
		
		//Location lastloc = loc.clone();
		
		if (randId == -1) {
			if (caster.isSneaking()) {
				speed = 2;
				length--;
				if (length < 2) {
					length = 2;
				}
				hitPlayer = false;
				hitEntity = false;
			}
			else {
				hitPlayer = true;
				hitEntity = true;
				speed = 5;
				hitboxSize = 2;
				length+=1;
				if (length > maxlength) {
					length = maxlength;
					hitboxSize = 3;
					speed = 2;
				}
			}
			/*
			
			*/
			loc =  caster.getLocation().add(caster.getEyeLocation().getDirection().multiply(length));
			//loc = loc.add(new Vector(0,1,0));
			
			
			if (zeroCount % speed == 0) {
				vel = loc.toVector().subtract(lastLoc.toVector());
				zeroCount = 0;
			}
			
			zeroCount++;
			/*
			Bukkit.broadcastMessage("L "+loc.toVector());
			Bukkit.broadcastMessage("A "+lastLoc.toVector());
				
			if (vel.length() <= 0) {
				Bukkit.broadcastMessage("No Movement "+randId);
			}
			*/
			//Bukkit.broadcastMessage("LAST "+lastVel);
			//Bukkit.broadcastMessage("NEW "+vel);
			double length = vel.length();
			//Bukkit.broadcastMessage("V"+length);
			//Bukkit.broadcastMessage(""+(lastVel.subtract(vel).length()));
			
			if (swap()) {
				
				ArrayList<Entity> entList = new ArrayList<Entity>();
				for (Entity ent : sticked.keySet()) {
					entList.add(ent);
				}
				for (Entity ent : entList) {
					launchEntity(ent);
				}
				
			}
			else {
				
				for (Entity ent : sticked.keySet()) {
					//Bukkit.broadcastMessage("X");
					if (cd.contains(ent))
						Bukkit.broadcastMessage("ERROR IN CHAIN");
					doPin(ent,loc);
				}
			}
			
			//Bukkit.broadcastMessage("ABC"+ sticked.size());
			lastLength = length;
			lastLoc = loc.clone();
			lastVel = vel.clone();
			/*
			for (Entity all : sticked.keySet()) {
				sticked.put(all, sticked.get(all)+1);
				if (sticked.get(all)>40) {
					launchEntity(all);
				}
				if (all instanceof Player) {
					Player p = (Player) all;
					if (p.getGameMode() == GameMode.ADVENTURE) {
						launchEntity(p);
					}
				}
			}
			*/
			playSound(Sound.BLOCK_ANVIL_PLACE,loc,0.1F,1.5F);
			return;
		}
		
		
		loc = loc.add(vel);
		Vector cVec = loc.toVector().subtract(last.loc.toVector()).normalize().multiply(space);
		loc = last.loc.clone().add(cVec);
		vel.add(cVec.multiply(-speedGainFactor));
		
		vel = vel.multiply(1-drag);
		//ParUtils.createParticle(Particles.BARRIER, loc, 0, 0, 0, 1, 1);
		
	
	}
	
	public void launchEntity(Entity ent) {
		//ent.setVelocity(new Vector(4,0,0));
		//Bukkit.broadcastMessage("LAUNCHED ENTITY");
		kb.reduceStep(20*2);
		cd.add(ent);
		sticked.remove(ent);

		//ParUtils.parKreisDir(Particles.CLOUD, loc.clone(), 2, 0, 0.2, vel.clone().normalize(),vel.clone().normalize());
		ParUtils.parKreisDot(Particles.CLOUD, loc.clone(), 2, 0, 0.2, vel.clone().normalize());
		
		Location l = ent.getLocation();
		
		l.setDirection(vel);
		//ParUtils.debugRay(l);
		ent.setVelocity(vel.clone().multiply(2));
		//Bukkit.broadcastMessage("Launched At "+vel.clone().multiply(2));
		//Bukkit.broadcastMessage("Ent Launched At "+ent.getVelocity());
	
		playSound(Sound.ENTITY_IRON_GOLEM_HURT,loc,4F,1.5F);
		sticked.remove(ent);
		new BukkitRunnable() {
			public void run() {
				cd.remove(ent);
				//ent.setVelocity(vel.multiply(2));
			}
		}.runTaskLater(main.plugin, 20);
		
	}
	@Override
	public void display() {
		
		
		if ( a!= null && last != null) {
			a.setFallDistance(0);
			
			Location l1 = loc.clone();
			
			Vector v = last.getArmorStand().getLocation().toVector().subtract(getArmorStand().getLocation().toVector()).normalize();
			l1.setDirection(v);
			a.teleport(l1.clone().add(0,-1.3F,0));
			//Bukkit.broadcastMessage("v "+v);
			l1.setPitch(l1.getPitch()-90);
			a.setHeadPose(new EulerAngle(Math.PI*2*l1.getPitch()/360,0, 0));
			//a.setHeadPose(new EulerAngle(Math.PI*2*l1.getPitch()/360,Math.PI*2*l1.getYaw()/360, 0));
		}
		
		if (randId ==-1) {
		
			//ParUtils.createParticle(Particles.CRIT, getTop(loc.clone()), 0.1, 0.1, 0.1, 3, 0);
			//ParUtils.createParticle(Particles.ENTITY_EFFECT, loc, 0, 0., 0, 1, 0);
		}
		//doPin(a,loc.clone().add(0,-1,0),4);
		// TODO Auto-generated method stub
		//if (crit)
		//ParUtils.createParticle(Particles.BUBBLE, loc, 0, 0, 0, 0, 0);
		//ParUtils.dropItemEffectVector(loc, Material.IRON_BARS, 1, 1, 0.1F, loc.toVector().subtract(last.loc.toVector()).normalize(),);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (randId ==-1) {
			if (!cd.contains(p)&& !sticked.containsKey(p)) {
				playSound(Sound.ENTITY_IRON_GOLEM_HURT,loc,4F,2F);
				sticked.put(p,0);
				ParUtils.parKreisDot(Particles.CRIT, loc.clone(), 2, 0, 1, vel.clone().normalize());
				damage(p, 2, caster);
			}
			
		}
		/*
		else {
			if (!sticked.containsKey(p)) {
				p.setVelocity(p.getVelocity().add(vel.normalize().multiply(0.01F)));
				damage(p,1, caster);
			}
			
		}*/
		
				
	}

	
	public ArmorStand getArmorStand() {
		return a;
	}
	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		if (randId ==-1) {
			
		
		if (!cd.contains(ent) && !sticked.containsKey(ent)) {
			playSound(Sound.ENTITY_IRON_GOLEM_HURT,loc,4F,2F);
			sticked.put(ent,0);
			
			ParUtils.parKreisDot(Particles.CRIT, loc.clone(), 2, 0, 1, vel.clone().normalize());
			damage(ent, 2, caster);
		}
		}
	/*
		
		
		else {
			if (!sticked.containsKey(ent)) {
				ent.setVelocity(ent.getVelocity().add(vel.normalize().multiply(0.01F)));
				damage(ent,1, caster);
			}
		
		
		*/
	}
		

	/*
	public void knockbackChain(Entity ent) {
		if (last == null && !sticked.contains(ent)) {
			//if (vel.length() >= 0.1)
			//ent.setVelocity(vel.multiply(2));
			sticked.add(ent);
			new BukkitRunnable() {
				int launch = 0;
				boolean launched = false;
				Vector lastVel = vel.clone();
				public void run() {
					if (launched) {
						launch++;
						if (launch == 1) {
							ent.setVelocity(vel.multiply(4));
						}
						if (launch > 15) {
							sticked.remove(ent);
							this.cancel();
						}
					}
					else {
						doPin(ent,loc,2);
					}
					
					
					
					if ((vel.length() -lastVel.length() < -0.1) ) {
						Bukkit.broadcastMessage("QUIIIIIIIIIII DACORD");
						launched = true;
					}
					
					lastVel = vel.clone();
				}
			}.runTaskTimer(main.plugin, 1,1) ;
				
			
		}
		
		/*
		else {
			ent.setVelocity(ent.getVelocity().add(vel));
		}
		*/

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		boolean par = false;
		for (BlockFace bf : BlockFace.values()) {
			if (block.getRelative(bf).getType().isAir()) {
				par = true;
			}
		}
		if (par)
		ParUtils.createBlockcrackParticle(loc.clone(), 0.3, 0.3, 0.3, 3, block.getType());
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		if (a != null)
		a.remove();
	}
	
	
	

}
