package monuments;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AirMonument extends Monument{

	
	public AirMonument() {
		range = 20;
	}
	
	@Override
	public void onConstruct() {
		
		// Layer ---
		addBlock(Material.STONE, new Vector(0.5,5,0.5));
		addBlock(Material.STONE, new Vector(-0.5,5,-0.5));
		addBlock(Material.STONE, new Vector(-0.5,5,0.5));
		addBlock(Material.STONE, new Vector(0.5,5,-0.5));
		// Layer ---
		addBlock(Material.STONE, new Vector(0.5,4,0.5));
		addBlock(Material.STONE, new Vector(-0.5,4,-0.5));
		addBlock(Material.STONE, new Vector(-0.5,4,0.5));
		addBlock(Material.STONE, new Vector(0.5,4,-0.5));
		// Layer ---
		addBlock(Material.STONE, new Vector(0.5,3,0.5));
		addBlock(Material.STONE, new Vector(-0.5,3,-0.5));
		addBlock(Material.STONE, new Vector(-0.5,3,0.5));
		addBlock(Material.STONE, new Vector(0.5,3,-0.5));
		// Layer ---
		addBlock(Material.STONE, new Vector(0.5,1,0.5));
		addBlock(Material.STONE, new Vector(-0.5,1,-0.5));
		addBlock(Material.STONE, new Vector(-0.5,1,0.5));
		addBlock(Material.STONE, new Vector(0.5,1,-0.5));
		// Layer ---
		addBlock(Material.STONE, new Vector(0.5,0,0.5));
		addBlock(Material.STONE, new Vector(-0.5,0,-0.5));
		addBlock(Material.STONE, new Vector(-0.5,0,0.5));
		addBlock(Material.STONE, new Vector(0.5,0,-0.5));
		addCore(Material.GLASS, new Vector(0,2,0));

	}
	@Override
	public void onEnter(Player p) {
		// TODO Auto-generated method stub
		Bukkit.broadcastMessage("IN");
	}

	@Override
	public void onLeave(Player p) {
		// TODO Auto-generated method stub
		Bukkit.broadcastMessage("OUT");
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
	

}
