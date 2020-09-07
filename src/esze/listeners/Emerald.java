package esze.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import esze.menu.TraitorshopMenu;
import esze.utils.TTTFusion;

public class Emerald implements Listener
{
	
	
	@EventHandler
	public void onOpen(PlayerInteractEvent e){
		Player p = e.getPlayer();
		ItemStack i = e.getItem();
		
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
		
		if(i != null && i.getType() == Material.EMERALD){
			if(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("§cSchwarzmarkt")){
				e.setCancelled(true);
				new TraitorshopMenu().open(p);
			}
		}
		
		if(i != null && i.getType() == Material.EMERALD){
			if(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("§eWeltenkatalysator")){
				e.setCancelled(true);
				TTTFusion.open(p);
			}
		}
		}
	}
	

}
