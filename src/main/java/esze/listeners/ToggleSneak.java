package esze.listeners;

import esze.configs.PlayerSettingsGuy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ToggleSneak implements Listener {

    @EventHandler
    public void sneakToggle(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if(e.isSneaking()) {
            if (PlayerSettingsGuy.getPlayerSettingsGuyLocation(p) != null) {
                if (PlayerSettingsGuy.getPlayerSettingsGuyLocation(p).distance(p.getLocation()) < 10) {
                    PlayerSettingsGuy.hitAnimation(p);
                }
            }
        }
    }
}
