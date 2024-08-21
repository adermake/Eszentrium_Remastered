package esze.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class Menu implements Listener {


    @EventHandler
    public void onMenu(InventoryClickEvent e) {
        if (e == null)
            return;

        if (e.getClickedInventory() == e.getWhoClicked().getInventory() || e.getView().getTitle().equals("§0Reroll - Lege 3 Spells ab") || e.getView().getTitle().equals("Handel...")) {
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
            e.setCancelled(ItemMenuIcon.ditributeClicks(e.getCurrentItem().getItemMeta().getDisplayName(), e.getInventory(), p, e.getAction()));
        }
        TeamSelectionMenu.onClickEvent(e);




    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals("spellmenu")) {
            ItemMenuIcon.ditributeClicks(e.getInventory().getItem(0).getItemMeta().getDisplayName(), e.getInventory(), (Player) e.getPlayer(), InventoryAction.PICKUP_ONE);

        }
    }
}
