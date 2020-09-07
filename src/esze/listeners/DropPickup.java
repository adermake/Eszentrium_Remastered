package esze.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class DropPickup implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		//Player p = e.getPlayer();
		e.setCancelled(true);
		/*if(Gamestate.getGameState() == Gamestate.INGAME && Gametype.type == Gametype.TTT){
			p.getInventory().setItemInMainHand(null);
		}*/
	}
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		e.setCancelled(true);
	}
	

}
