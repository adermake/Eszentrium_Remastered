package monuments;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.scoreboards.TeamsScoreboard;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spells.Aufwind;

public class AirMonument extends Monument{

	Location ori;
	public AirMonument(Player p) {
		super(p,"§eAir Monument",null);
		range = 6;
		ori = loc.clone();
		
	}
	
	@Override
	public void onConstruct() {
		
	
		addBlock(Material.POLISHED_ANDESITE, new Vector(0,6,0));
		// Layer ---
		addBlock(Material.POLISHED_ANDESITE, new Vector(0.5,5,0.5));
		addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5,5,-0.5));
		addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5,5,0.5));
		addBlock(Material.STONE, new Vector(0.5,5,-0.5));
		// Layer ---
		addBlock(Material.STONE, new Vector(0.5,4,0.5));
		addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5,4,-0.5));
		addBlock(Material.STONE, new Vector(-0.5,4,0.5));
		addBlock(Material.STONE, new Vector(0.5,4,-0.5));
		// Layer ---
		addBlock(Material.POLISHED_ANDESITE, new Vector(0.5,3,0.5));
		addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5,3,-0.5));
		addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5,3,0.5));
		addBlock(Material.STONE, new Vector(0.5,3,-0.5));
		// Layer ---
		addBlock(Material.STONE, new Vector(0.5,1,0.5));
		addBlock(Material.STONE, new Vector(-0.5,1,-0.5));
		addBlock(Material.STONE, new Vector(-0.5,1,0.5));
		addBlock(Material.STONE, new Vector(0.5,1,-0.5));
		// Layer ---
		addBlock(Material.POLISHED_ANDESITE, new Vector(0.5,0,0.5));
		addBlock(Material.STONE, new Vector(-0.5,0,-0.5));
		addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5,0,0.5));
		addBlock(Material.STONE, new Vector(0.5,0,-0.5));
		
		
		addBlock(Material.POLISHED_ANDESITE, new Vector(0,-1,0));
		addBlock(Material.POLISHED_ANDESITE, new Vector(0,-2,0));

		addCore(Material.GLASS, new Vector(0,2,0));
	}
	@Override
	public void onEnter(Player p) {
		Aufwind a = new Aufwind();
		a.castSpell(p, "wui");
		target = p;
	}

	@Override
	public void onLeave(Player p) {
		// TODO Auto-generated method stub
		if (p == target) {
			target = null;
		}
	}

	@Override
	public void onEnterEnemy(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveEnemy(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActivate(Player p) {
		// TODO Auto-generated method stub
		Bukkit.broadcastMessage("Activate");
	}

	@Override
	public void coreAnimation(ArmorStand ar,Vector dir) {
		// TODO Auto-generated method stub
		//ParUtils.createFlyingParticle(Particle.END_ROD, ar.getEyeLocation(), 0, 0,0, 1, 5.5F, dir);
		ParUtils.createParticle(Particle.CLOUD, ar.getLocation().add(0,1.5,0), 0, 0, 0, 0, 0);
	}

	@Override
	public void onTick() {
		// TODO Auto-generated method stub
		setMonumentOffset(0,Math.sin(step/15),0);
	}


	

}
