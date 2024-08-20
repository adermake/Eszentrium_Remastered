package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import spells.spellcore.Spell;

public class Disk extends Spell {
//s
	
	
	ArrayList<ArmorStand> stands = new ArrayList<ArmorStand>();
	
	float radius = 1;
	float offset = -1;
	float aDist = 8F;
	int aCount = 42;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		//int count =(int)( 2*Math.PI*radius/aDist);
		for (int i = 0;i<aCount;i++) {
			
			Location aLoc = ParUtils.stepCalcCircle(caster.getLocation(), radius, caster.getLocation().getDirection(), offset, step+i*aDist/22*Math.PI);
			//Location aTo = ParUtils.stepCalcCircle(caster.getLocation(), radius, caster.getLocation().getDirection(), offset, step+(i*aDist+1)/22*Math.PI);
			ArmorStand a = createArmorStand(aLoc);
			a.setSmall(true);
			a.getEquipment().setHelmet(new ItemStack(Material.PRISMARINE_WALL));
			//a.setMarker(true);
			a.setGravity(true);
			//caster.addPassenger(a);
			stands.add(a);
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

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
		Bukkit.broadcastMessage(""+step);
		
		float i = 0;
		float l = stands.size();
		/*
		for (ArmorStand a : stands) {
			
			a.teleport(a.getLocation().setDirection(caster.getLocation().getDirection()));
			caster.addPassenger(a);
			a.setHeadPose(new EulerAngle(0,0, Math.PI*2/l*(i+step)));
			i++;
		}
		*/
		
		float aPlace = 0;
		for (ArmorStand a : stands) {
			aPlace += 1;
			Location aLoc = ParUtils.stepCalcCircle(caster.getLocation(), radius, caster.getLocation().getDirection(), offset, step+aPlace*aDist/22*Math.PI);
			Location aTo = ParUtils.stepCalcCircle(caster.getLocation(), radius, caster.getLocation().getDirection(), offset, step+(aPlace*aDist+1)/22*Math.PI);
			Vector dir = aTo.toVector().subtract(aLoc.toVector());
			aLoc.setDirection(dir);
			//aLoc.setDirection(aTo.toVector().subtract(aLoc.toVector()));
			//a = createArmorStand(caster.getLocation());
			//aLoc.setPitch(aLoc.getPitch()-90);
			//doPin(a,aLoc);
			
			a.teleport(aLoc.clone());
			
			armorStandPitch(dir, a);
		
			//a.setHeadPose(new EulerAngle( 0,0,step+aPlace/22*Math.PI));
		}
		
		
		
		
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
