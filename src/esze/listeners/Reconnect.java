package esze.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.types.TypeTTT;

public class Reconnect implements Listener{


	public static HashMap<String,String> disconnected = new HashMap<String,String>();
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (Gamestate.getGameState() == Gamestate.INGAME) {
			if (GameType.getType() instanceof TypeTTT) {
				TypeTTT ttt = (TypeTTT) GameType.getType();
				
				if (ttt.players.contains(p)) {
					
					ttt.players.remove(p);
					
					if (ttt.startInnocent.contains(p)) {
						ttt.startInnocent.remove(p);
						ttt.innocent.remove(p);
						ttt.players.remove(p);
						disconnected.put(p.getName(), "ino");
					}
					else {
						ttt.startTraitor.remove(p);
						ttt.traitor.remove(p);
						ttt.players.remove(p);
						disconnected.put(p.getName(), "tra");
					}
					
					
				}
				
				
				
			}
			
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		if (Gamestate.getGameState() == Gamestate.INGAME) {
			
			for (String s : disconnected.keySet()) {
				if (s.equals(e.getPlayer().getName())) {
					
					if (GameType.getType() instanceof TypeTTT) {
						TypeTTT ttt = (TypeTTT) GameType.getType();
						
						if (disconnected.get(ttt).equals("ino")) {
							ttt.startInnocent.add(p);
							ttt.innocent.add(p);
							ttt.players.add(p);
						}
						else {
							ttt.startTraitor.add(p);
							ttt.traitor.add(p);
							ttt.players.add(p);
						}
						disconnected.remove(s);
						
						
					}
					
				}
			}
		}
	}
	
	public Player checkForPlayername(ArrayList<Player> list,String s) {
		
		for(Player p : list) {
			if (p.getName().equals(s)) {
				return p;
			}
		}
		return null;
	}
}
