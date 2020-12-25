package esze.scoreboards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import easyscoreboards.ScoreboardUtil;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeSOLO;
import esze.types.TypeTEAMS;
import esze.utils.EszeTeam;

public class TeamsScoreboard extends Scoreboard {

	
	@Override
	public void showScoreboard(){
		
		
		new BukkitRunnable() {
			
			public void run() {
				
				if(GameType.getType().name.equals("TEAMS")){
					if(Gamestate.getGameState() == Gamestate.INGAME){
						HashMap<String, Integer> lives = new HashMap<String, Integer>();
						ArrayList<Player> living = new ArrayList<Player>();
						TypeTEAMS team = ((TypeTEAMS)GameType.getType());
						for(EszeTeam et : team.allTeamsAlive){
						
							String ct = "§"+et.color+"";
							for (Player p : et.players) {
								lives.put("§"+ct+p.getName(), team.lives.get(et));
							}
							
							
						}
						
						
						ScoreboardUtil.rankedSidebarDisplay((Collection<Player>) Bukkit.getOnlinePlayers(), "Leben", lives);
						
						
						if (hide) {
							this.cancel();
							hide = false;
						}
					}
				}
				
				
			}
		}.runTaskTimer(main.plugin, 0, 10);
		
				
		
	}
	
}
