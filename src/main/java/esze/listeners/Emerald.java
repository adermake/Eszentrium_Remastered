package esze.listeners;

import esze.enums.GameType;
import esze.menu.TraitorshopMenu;
import esze.types.TypeTTT;
import esze.utils.TTTFusion;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Emerald implements Listener {

    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = e.getItem();


        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (GameType.getType() instanceof TypeTTT) {
                if (i != null && i.getType() == Material.EMERALD) {
                    if (i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("§cSchwarzmarkt")) {
                        e.setCancelled(true);
                        new TraitorshopMenu().open(p);
                    }
                }

                if (i != null && i.getType() == Material.EMERALD) {
                    if (i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("§eWeltenkatalysator")) {
                        e.setCancelled(true);
                        TTTFusion.open(p);
                    }
                }

            }

        }
    }
}

