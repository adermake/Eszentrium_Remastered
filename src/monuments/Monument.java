package monuments;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import esze.utils.EszeTeam;
import esze.utils.ParUtils;

import spells.spellcore.Spell;

public abstract class Monument extends Spell {
	
	public static ArrayList<Monument> monuments = new ArrayList<Monument>();
	
	ArrayList<Player> inside = new ArrayList<Player>();
	ArrayList<ArmorStand> cores = new ArrayList<ArmorStand>();
	HashMap<ArmorStand,Vector> blocks = new HashMap<ArmorStand,Vector>();
	HashMap<ArmorStand,Double> rotation = new HashMap<ArmorStand,Double>();
	HashMap<ArmorStand,Vector> lastHeadDir = new HashMap<ArmorStand,Vector>();
	public double range = 30;
	public int radiusPoints = 2;
	public double refreshSpeed = 1;
	Entity target;
	Vector monumentOffset = new Vector(0,0,0);
	EszeTeam team;
	
	public Monument(Player caster,String name,EszeTeam team) {
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
		Location floor = getFloor(loc.clone()).add(0,-1,0);
		for (Location l : ParUtils.preCalcCircle(floor, range, new Vector(0,1,0),0)) {
			if (l.getBlock().getType().isSolid()) {
				
				
				spawnPhantomblock(l.getBlock(),Material.RED_CONCRETE);
			}
			
		}
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		hitboxSize = range;
		ArrayList<Player> removeLater = new ArrayList<Player>();
		for (Player p : inside) {
			if (!(p.getEyeLocation().distance(loc)<0.6+hitboxSize ||p.getLocation().distance(loc)<0.6+hitboxSize )) {
				onLeave(p);
				inside.remove(p);
			}
		}
		for (Player p : removeLater) {	
				inside.remove(p);
		}
		
		for (ArmorStand ar : blocks.keySet()) {
			if (cores.contains(ar))
				continue;
			
			Location rot = rotatedPosition(ar);
			doPin(ar,rot.clone().add(monumentOffset),3);
			setArmorstandHeadPos(ar, rot.getDirection().setY(0), 0, 0);
			lastHeadDir.put(ar, rot.getDirection());
		}
		for (ArmorStand core : cores) {
			doPin(core, loc.clone().add(blocks.get(core)).clone().add(monumentOffset),3);
			Entity p = target;
			if (p != null) {
				Vector dir = p.getLocation().subtract(core.getLocation()).toVector().normalize();
				setArmorstandHeadPos(core, dir, 0, 0);
				lastHeadDir.put(core, dir);
			}
			
			coreAnimation(core,lastHeadDir.get(core));
		}
		onTick();
		
		
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
		monuments.remove(this);
	}
	
	float armorStandHeadConstant = 0.6152F;
	
	public ArmorStand addBlock(Material m,Vector gridPos) {
	
		Vector v = new Vector(gridPos.getX()*armorStandHeadConstant,gridPos.getY()*armorStandHeadConstant,gridPos.getZ()*armorStandHeadConstant);
		ArmorStand ar = createArmorStand(loc.clone().setDirection(new Vector(1,0,0)));
		ar.getEquipment().setHelmet(new ItemStack(m));
		ar.setGravity(true);
		noTargetEntitys.add(ar);
		disableEntityHitbox(ar);
		
		blocks.put(ar,v);
		lastHeadDir.put(ar, ar.getLocation().getDirection());
		//rotation.put(ar, 0.18D);
		rotation.put(ar, 0.05D);
		return ar;
	}
	public void addCore(Material m,Vector gridPos) {
	
		Vector v = new Vector(gridPos.getX()*armorStandHeadConstant,gridPos.getY()*armorStandHeadConstant,gridPos.getZ()*armorStandHeadConstant);
		ArmorStand ar = createArmorStand(loc.clone().setDirection(new Vector(1,0,0)));
		ar.getEquipment().setHelmet(new ItemStack(m));
		ar.setGravity(true);
		noTargetEntitys.add(ar);
		disableEntityHitbox(ar);
		cores.add(ar);
		lastHeadDir.put(ar, ar.getLocation().getDirection());
		blocks.put(ar,v);
	}
	
	public Location rotatedPosition(ArmorStand ar) {
		if (rotation.containsKey(ar) ) {
		Vector currentV = blocks.get(ar);
		Vector nextPoint = currentV.clone().rotateAroundY(rotation.get(ar));
		Vector dir = lastHeadDir.get(ar).clone().rotateAroundY(rotation.get(ar));
		blocks.put(ar,nextPoint.clone());
		return loc.clone().add(nextPoint.clone()).setDirection(dir.clone()); // 
		}
		return loc.clone().add(blocks.get(ar));
	}
	
	public void setMonumentOffset(double x,double y,double z) {
		monumentOffset = new Vector(x,y,z);
	}
	
	public Color getParticleColor(Player p) {
		if (team == null) {
			return Color.BLACK;
		}
		if (team.players.contains(p)) {
			return Color.LIME;
		}
		else {
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
	public abstract void coreAnimation(ArmorStand ar,Vector dir);
}
