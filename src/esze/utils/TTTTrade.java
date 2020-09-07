package esze.utils;



import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import esze.enums.Gamestate;
import esze.main.main;

public class TTTTrade implements Listener{
	
	public static HashMap<Player, Player> herausfordern = new HashMap<Player, Player>();
	
	
	@EventHandler
	public void interact2(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
			if(e.getCause() == DamageCause.ENTITY_ATTACK){
				Player clicked = (Player) e.getEntity();
				Player p = (Player) e.getDamager();
				if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() == Material.EMERALD){
					e.setCancelled(true);
					if(herausfordern.containsValue(p)){
						Inventory inv = Bukkit.createInventory(null, 9*3, "Handel...");
						p.openInventory(inv);
						clicked.openInventory(inv);
						herausfordern.remove(p);
					}else{
						herausfordern.put(p, clicked);
						clicked.sendMessage("§8| §6"+p.getName()+" §7hat dich zum Handeln eingeladen!");
						p.sendMessage("§8| Du hast §6"+clicked.getName()+ " zum Handeln geladen!");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void invblock(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equals("Handel...")){
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.WOODEN_SWORD){
				e.setCancelled(true);
			}
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.EMERALD){
				e.setCancelled(true);
			}
		}
	}


}
