package monuments;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.EszeTeam;
import esze.utils.ParUtils;
import spells.spellcore.EszeParticle;
import spells.spellcore.Spell;

public abstract class Monument extends Spell {

	public static ArrayList<Monument> monuments = new ArrayList<Monument>();

	ArrayList<Player> inside = new ArrayList<Player>();
	HashMap<ArmorStand, Material> cores = new HashMap<ArmorStand, Material>();
	HashMap<ArmorStand, Vector> blocks = new HashMap<ArmorStand, Vector>();
	HashMap<ArmorStand, Double> rotation = new HashMap<ArmorStand, Double>();
	HashMap<ArmorStand, Vector> lastHeadDir = new HashMap<ArmorStand, Vector>();

	public double range = 30;
	public int radiusPoints = 2;
	public double refreshSpeed = 1;
	Entity target;

	Vector monumentOffset = new Vector(0, 0, 0);
	EszeTeam team;

	// Platings
	ArrayList<Plating> platings = new ArrayList<Plating>();
	int platingCount = 3;
	double platingMaxHealth = 40; 
	double platingOrbit = 3;
	double platingOrbitSpeed = 0.2F;
	Material pState1 = Material.STONE_BRICKS;
	Material pState2 = Material.CRACKED_STONE_BRICKS;
	Material pState3 = Material.COBBLESTONE;
	int currentNoDamageTicks = 0;
	int noDamageTicks = 20* 5;
	// True Health
	double monumentMaxHealth = 20;
	double monumentHealth = 20;
	
	// Iron Golem Hitboxes
	ArrayList<IronGolem> hitBoxes = new ArrayList<IronGolem>();
	public static HashMap<IronGolem, Monument> hitBoxesMonument = new HashMap<IronGolem, Monument>();

	public Monument(Player caster, String name, EszeTeam team) {
		castSpell(caster, name);
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		hitSpell = false;
		canHitSelf = true;
		multihit = true;
		monuments.add(this);
		onConstruct();
		casttime = 20 * 2;
		refreshSpeed = 0.2;
		radiusPoints = (int) range;
		

		Location gLoc = loc.clone().add(0, -1, 0);
		for (double i = 0; i < 5; i += 1.5) {
			IronGolem gol = (IronGolem) spawnEntity(EntityType.IRON_GOLEM, gLoc.clone().add(0, i, 0));
			gol.setAI(false);
			gol.setSilent(true);
			gol.setInvisible(true);
			hitBoxes.add(gol);
			hitBoxesMonument.put(gol,this);
		}
		for (int i = 0; i < platingCount; i++) {
		
			new Plating(this);
		}
	}

	@Override
	public void cast() {
		for (ArmorStand ar : blocks.keySet()) {
			doPin(ar, loc.clone().add(blocks.get(ar)).add(monumentOffset));

		}
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		Location floor = getFloor(loc.clone()).add(0, -1, 0);
		for (Location l : ParUtils.preCalcCircle(floor, range, new Vector(0, 1, 0), 0)) {
			if (l.getBlock().getType().isSolid()) {

				spawnPhantomblock(l.getBlock(), Material.RED_CONCRETE);
			}

		}
	}

	@Override
	public void move() {
		if (dead)
			return;
		
		// Player On Leave
		hitboxSize = range;
		ArrayList<Player> removeLater = new ArrayList<Player>();
		for (Player p : inside) {
			if (!(p.getEyeLocation().distance(loc) < 0.6 + hitboxSize
					|| p.getLocation().distance(loc) < 0.6 + hitboxSize)) {
				onLeave(p);
				removeLater.add(p);
			}
		}
		for (Player p : removeLater) {
			inside.remove(p);
		}

		// Monement Body Animation
		for (ArmorStand ar : blocks.keySet()) {
			if (cores.keySet().contains(ar))
				continue;

			Location rot = rotatedPosition(ar);
			doPin(ar, rot.clone().add(monumentOffset), 3);
			setArmorstandHeadPos(ar, rot.getDirection().setY(0), 0, 0);
			lastHeadDir.put(ar, rot.getDirection());
		}

		// Core Animation
		for (ArmorStand core : cores.keySet()) {
			doPin(core, loc.clone().add(blocks.get(core)).clone().add(monumentOffset), 3);
			Entity p = target;
			if (p != null) {
				Vector dir = p.getLocation().subtract(core.getLocation()).toVector().normalize();
				setArmorstandHeadPos(core, dir, 0, 0);
				lastHeadDir.put(core, dir);
			}

			coreAnimation(core, lastHeadDir.get(core));
		}

		// Platings
		if (platings.size() > 0) {
			double stepOffset = 44D / (double)platings.size();
			int index = 0;
			for (Plating plating : platings) {
				
				plating.checkState();
	
				Location pLoc = ParUtils.stepCalcCircle(getOffsetLoc(), platingOrbit, new Vector(0, 1, 0), 0,
						((double)index * (double)stepOffset) -(double)(step*platingOrbitSpeed));
				
				Vector v = doPin(plating.getArmorStand(), pLoc, 3);
				setArmorstandHeadPos(plating.getArmorStand(), v, 0, 0);
				for (Player p : Bukkit.getOnlinePlayers()) 
				ParUtils.createRedstoneParticle(plating.getLocation().add(0,0.5,0), 0, 0, 0, 1, getParticleColor(p), 1.6F, p);
				index++;
			}
		}
		
		currentNoDamageTicks--;
	
		
		//No Damage Animation Disable
		if (currentNoDamageTicks <= 0) {
			for (ArmorStand core : cores.keySet()) {
			
				if (core.getEquipment().getHelmet().getType() == Material.BEDROCK) {
					core.getEquipment().setHelmet(new ItemStack(cores.get(core)));
				}
				currentNoDamageTicks = 0;
			}
		}
		
		onTick();
		

	}
	
	public void onHitGolem(Entity damager,double damage) {
		if (currentNoDamageTicks > 0) {
			playSound(Sound.ITEM_SHIELD_BLOCK,loc,1,1);
			return;
		}
		
			
		
		
		if (platings.size() > 0) {
			Plating plating = platings.get(0);
			playSound(Sound.ENTITY_IRON_GOLEM_HURT,loc,1,1);
			if (plating.damage(damage)) {			
				plating.destroy();
				for (ArmorStand core : cores.keySet()) {
					if (core.getEquipment().getHelmet().getType() != Material.BEDROCK) {
						core.getEquipment().setHelmet(new ItemStack(Material.BEDROCK));
					}
				}
				currentNoDamageTicks = noDamageTicks;
			}
			
			
		}
		else {
			monumentHealth -= damage;
			if (monumentHealth <= 0) {
				playSound(Sound.ENTITY_IRON_GOLEM_DAMAGE,loc,1,0.5);
				playSound(Sound.BLOCK_CHEST_LOCKED,loc,1,0.5F);
				explode();
			}	
		}
		
	}
	
	public void explode() {
		for (ArmorStand ar : blocks.keySet()) {
			doKnockback(ar, loc.clone().add(0,0,0), randDouble(1, 3));
			//ar.setVelocity(new Vector(0,5,0));
		}
		playSound(Sound.ENTITY_GENERIC_EXPLODE,loc,1,1);
		ParUtils.createParticle(Particle.EXPLOSION_HUGE, getOffsetLoc(), 0, 0, 0, 1, 1);
		dead = true;
	}
	
	@Override
	public void display() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (!inside.contains(p)) {
			inside.add(p);
			onEnter(p);
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
		for (IronGolem golem : hitBoxes) {
			golem.remove();
		}
		new BukkitRunnable() {
			int t = 0;
			public void run() {
				for (ArmorStand ar : blocks.keySet()) {
					ParUtils.createParticle(Particle.CLOUD,ar.getEyeLocation(), 0, 0, 0, 1, 0);
					setArmorstandHeadPos(ar, ar.getVelocity(), 0, 0);
				}
				t++;
				if (t > 20* 4) {
					for (ArmorStand ar : blocks.keySet()) {
						ar.remove();
					}
				}
			}
		}.runTaskTimer(main.plugin,1,1);
		monuments.remove(this);
	}

	public Location getOffsetLoc() {
		return loc.clone().add(monumentOffset);
	}

	float armorStandHeadConstant = 0.6152F;

	public ArmorStand addBlock(Material m, Vector gridPos) {

		return addBlock(m, gridPos, 0.05D);
	}
	public ArmorStand addBlock(Material m, Vector gridPos,double rot) {

		Vector v = new Vector(gridPos.getX() * armorStandHeadConstant, gridPos.getY() * armorStandHeadConstant,
				gridPos.getZ() * armorStandHeadConstant);
		ArmorStand ar = createArmorStand(loc.clone().setDirection(new Vector(1, 0, 0)));
		ar.getEquipment().setHelmet(new ItemStack(m));
		ar.setGravity(true);
		noTargetEntitys.add(ar);
		disableEntityHitbox(ar);

		blocks.put(ar, v);
		lastHeadDir.put(ar, ar.getLocation().getDirection());
		// rotation.put(ar, 0.18D);
		rotation.put(ar, rot);
		return ar;
	}
	public void addCore(Material m, Vector gridPos) {

		Vector v = new Vector(gridPos.getX() * armorStandHeadConstant, gridPos.getY() * armorStandHeadConstant,
				gridPos.getZ() * armorStandHeadConstant);
		ArmorStand ar = createArmorStand(loc.clone().setDirection(new Vector(1, 0, 0)));
		ar.getEquipment().setHelmet(new ItemStack(m));
		ar.setGravity(true);
		noTargetEntitys.add(ar);
		disableEntityHitbox(ar);
		cores.put(ar,m);
		lastHeadDir.put(ar, ar.getLocation().getDirection());
		blocks.put(ar, v);
	}

	public Location rotatedPosition(ArmorStand ar) {
		if (rotation.containsKey(ar)) {
			Vector currentV = blocks.get(ar);
			Vector nextPoint = currentV.clone().rotateAroundY(rotation.get(ar));
			Vector dir = lastHeadDir.get(ar).clone().rotateAroundY(rotation.get(ar));
			blocks.put(ar, nextPoint.clone());
			return loc.clone().add(nextPoint.clone()).setDirection(dir.clone()); //
		}
		return loc.clone().add(blocks.get(ar));
	}

	public void setMonumentOffset(double x, double y, double z) {
		monumentOffset = new Vector(x, y, z);
	}

	public Color getParticleColor(Player p) {
		if (team == null) {
			return Color.BLACK;
		}
		if (team.players.contains(p)) {
			return Color.LIME;
		} else {
			return Color.RED;
		}
	}

	public abstract void onEnter(Player p);

	public abstract void onLeave(Player p);

	public abstract void onEnterEnemy(Player p);

	public abstract void onLeaveEnemy(Player p);

	public abstract void onActivate(Player p);

	public abstract void onConstruct();

	public abstract void onTick();

	public abstract void coreAnimation(ArmorStand ar, Vector dir);
}
