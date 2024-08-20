package esze.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

public class NoCollision {

	public static Team noCollision;
	public static Scoreboard scoreboard;
	 
	public static Team getOrCreateTeam(String name) {
	    Team team = scoreboard.getTeam(name);
	    if (team == null)
	      team = scoreboard.registerNewTeam(name); 
	    return team;
	  }
	  
	  
	  public static void setUpCollsionStopper() {
		 scoreboard =  Bukkit.getScoreboardManager().getMainScoreboard();
		 noCollision = getOrCreateTeam("noCollision");
		 noCollision.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
	  }
	  
	  public static void dontCollide(Entity ent) {
		  if (ent instanceof Player) {
			  Player p = (Player)ent;
			  noCollision.addEntry(""+p.getName());
			  p.setScoreboard(scoreboard);
		  }
		  noCollision.addEntry(""+ent.getUniqueId());
	  }
	
}
