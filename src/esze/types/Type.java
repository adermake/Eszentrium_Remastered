package esze.types;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import esze.main.main;
import esze.map.JumpPadHandler;
import esze.scoreboards.Scoreboard;
import esze.utils.PlayerUtils;
import io.netty.util.internal.ThreadLocalRandom;
import spells.spells.AntlitzderGöttin;


public class Type {
	
	public String name;
	public String currentmap;
	public ArrayList<Player> startplayers = new ArrayList<Player>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Player> spectator = new ArrayList<Player>();
 	public Scoreboard scoreboard;
	public int spawnloc = 1;
	
	public void runEverySecond(){
		
	}
	
	public void runEveryTick(){
		
	}
	
	public void death(PlayerDeathEvent event){
	}
	
	public void gameStart(){
		
	}
	
	public Location nextLoc(){
		Location loc = null;
		
		
		
		if(main.plugin.getConfig().contains("maps."+currentmap+"."+spawnloc)){
			
			loc = (Location) main.plugin.getConfig().get("maps."+currentmap+"."+spawnloc);
			spawnloc++;
		}else{
			spawnloc = 1;
			return nextLoc();
		}
		
		
		return loc;
	}
	public void setupJumpPad(String map) {
		JumpPadHandler.loadJumpPads(map);
		//setup();
	}
	public void setup() {
		for (int i = randInt(0,10);i>0;i++) {
			nextLoc();
		}
			
	}
	
	
	public static int randInt(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	public void out(Player p) {
	
		p.getInventory().clear();
		p.setGameMode(GameMode.ADVENTURE);
		p.setAllowFlight(true);
		p.setFlying(true);
		if (p.getLocation().getY()<60) {
			p.teleport(nextLoc());
		}
		PlayerUtils.hidePlayer(p);
		p.setVelocity(p.getVelocity().multiply(0));
		p.setHealth(p.getMaxHealth());
		players.remove(p);
		spectator.add(p);
		
		
	}
	
	public boolean deathCheck(Player p) {
		if (p.getLocation().getY()<=60) {
			return false;
		}
		if (p.getKiller() != null) {
			if(!(p.getKiller() instanceof Player)) {
				return true;
			}
		}
		if (p.getLastDamageCause() != null && p.getLastDamageCause().getCause() != null && p.getLastDamageCause().getCause() == DamageCause.FLY_INTO_WALL) {
			return true;
		}
		
		
	
		
		return false;
	}
	
}
