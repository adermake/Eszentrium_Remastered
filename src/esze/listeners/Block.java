package esze.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Block implements Listener {
	
	@EventHandler
	public void onBuild(BlockPlaceEvent e){
		Player p = e.getPlayer();
		if(p.getGameMode() != GameMode.CREATIVE){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		if(p.getGameMode() != GameMode.CREATIVE){
			e.setCancelled(true);
		}
	}

}
