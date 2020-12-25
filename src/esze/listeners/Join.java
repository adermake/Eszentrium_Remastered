package esze.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.players.PlayerAPI;
import esze.players.PlayerInfo;
import esze.types.TypeTEAMS;
import esze.utils.ItemStackUtils;
import esze.utils.LobbyUtils;
import esze.utils.ScoreboardTeamUtils;

public class Join implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		main.damageCause.put(p, ""); //Damage Cause
		p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(20D);
	    p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(0.5D);
		p.setExp(0F);
		p.setLevel(0);
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setMaxHealth(20);
		p.setWalkSpeed(0.2F);
		
		//Clears Inventory of Players
		GameType.getType().givePlayerLobbyItems(p);
		if(Gamestate.getGameState() == Gamestate.LOBBY){
			//p.teleport((Location) main.plugin.getConfig().get("lobby.loc"));
			e.setJoinMessage("§8> §3" + p.getName() + " §7ist beigetreten.");
			LobbyUtils.recall(p);
			
			
		}else if(Gamestate.getGameState() == Gamestate.INGAME){
			e.setJoinMessage("");
			if(PlayerAPI.getPlayerInfo(p) == null){
				PlayerInfo pi = new PlayerInfo(p);
				pi.isAlive = false;
				pi.isInRound = false;
			}
			
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		e.setQuitMessage("§8< §6"+p.getName()+" §7hat das Spiel verlassen");
		ScoreboardTeamUtils.giveScoreboard(p);
		
		if (GameType.getType() instanceof TypeTEAMS) {
			TypeTEAMS tt = (TypeTEAMS) GameType.getType();
			tt.removePlayerFromAllTeams(p);
		}
		
		
	}

	
	
}
