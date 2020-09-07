package esze.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import esze.players.PlayerAPI;

public class FBoost implements Listener{
	
	@EventHandler
	public void onF(PlayerSwapHandItemsEvent e){
		Player p = e.getPlayer();
		if((p.isOp() && p.getGameMode() == GameMode.CREATIVE) || !PlayerAPI.getPlayerInfo(p).isAlive){
			p.setVelocity(p.getLocation().getDirection().multiply(2));
		}
		e.setCancelled(true);
	}
	
	

}
