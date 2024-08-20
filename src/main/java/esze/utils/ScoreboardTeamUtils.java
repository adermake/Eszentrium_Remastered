package esze.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardTeamUtils {

	public static HashMap<Player,Scoreboard> playerBoards = new HashMap<Player,Scoreboard>();
	
	public static void giveScoreboard(Player p) {
		//Bukkit.broadcastMessage("giveScoreboard->"+p.getName());
		Player rem = null;
		for (Player pl : playerBoards.keySet()) {
			if (pl.getName() == p.getName()) {
				rem = pl;
			}
		}
		if (rem != null) {
			//Bukkit.broadcastMessage("removingScoreboard->"+p.getName());
			playerBoards.remove(rem);
		}
		if (!playerBoards.containsKey(p) ) {
			//Bukkit.broadcastMessage("newgScoreboard->"+p.getName());
			Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
			p.setScoreboard(sb);
			playerBoards.put(p, sb);
			
		}
	}
	
	
	public static Scoreboard getBoard(Player p) {
		//Bukkit.broadcastMessage("getBoard->"+p.getName());
		if (playerBoards.get(p) == null || playerBoards.get(p) == null || playerBoards.get(p).getEntries().isEmpty()) {
			giveScoreboard(p);
		}
		
		p.setScoreboard( playerBoards.get(p));
		return playerBoards.get(p);
	}
	
	
	
	public static Team getOrCreateTeam(Scoreboard sb,String name) {
		
	    Team team = sb.getTeam(name);
	    if (team == null)
	      team = sb.registerNewTeam(name); 
	    return team;
	  }
	public static void colorEntity(Entity e,Player p,ChatColor color) {
		Scoreboard sb = getBoard(p);
		Team t = getOrCreateTeam(sb,color.toString());
		t.addEntry(e.getUniqueId().toString());
		
		t.setColor(color);
		
	}
	
	public static void colorPlayer(Player e,Player p,ChatColor color) {
		
		Scoreboard sb = getBoard(p);
		Team t = getOrCreateTeam(sb,color.toString());
		t.addPlayer(e);
		
		t.setColor(color);
		t.setAllowFriendlyFire(false);
		
		
	}
	public static void clearColor(Player p,Entity ent) {
		for (Team t : getBoard(p).getTeams()) {
			t.removeEntry(ent.getUniqueId().toString());
		}
	}
	
	public static void clearColor(Player p,Player pl) {
		for (Team t : getBoard(p).getTeams()) {
			t.removePlayer(pl);
		}
	}
	public static void colorEntity(Entity e,ChatColor c) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			colorEntity(e,p,c);
		}
		
	}
	public static void colorPlayer(Player e,ChatColor c) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			colorPlayer(e,p,c);
		}
		
	}
	public static void clearColor(Player ent) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			clearColor(p,ent);
		}
		
	}
	public static void clearColor(Entity ent) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			clearColor(p,ent);
		}
		
	}
}
