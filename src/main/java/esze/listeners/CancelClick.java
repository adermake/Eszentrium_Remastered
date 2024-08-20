package esze.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CancelClick implements Listener {

    @EventHandler
    public void noUproot(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.WHEAT)
            event.setCancelled(true);

        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {

            if (event.getClickedBlock() != null) {


                if (event.getClickedBlock().getType() == Material.FLOWER_POT)
                    event.setCancelled(true);
            }
        }


    }


}
