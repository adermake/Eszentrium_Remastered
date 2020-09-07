package esze.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.types.TypeTTT;

public class Chat implements Listener{
	
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		if (GameType.getType() instanceof TypeTTT) {
			
			TypeTTT type = (TypeTTT) GameType.getType();
			if (!type.players.contains(e.getPlayer()) && e.getPlayer().getGameMode() != GameMode.CREATIVE && Gamestate.getGameState() == Gamestate.INGAME) {
				e.setCancelled(true);
			}
			
		}
	}

}
