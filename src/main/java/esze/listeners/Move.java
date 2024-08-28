package esze.listeners;

import esze.configs.PlayerSettingsGuy;
import esze.enums.Gamestate;
import esze.main.main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Move implements Listener {

    @EventHandler
    public void onVoid(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (Gamestate.getGameState() != Gamestate.INGAME) {
            if (e.getTo().getBlockY() <= 60) {
                if (p.getGameMode() != GameMode.CREATIVE)
                    p.teleport((Location) main.plugin.getConfig().get("lobby.loc"));
            }
            if(PlayerSettingsGuy.getPlayerSettingsGuyLocation(p) != null) {
                if(PlayerSettingsGuy.getPlayerSettingsGuyLocation(p).distance(p.getLocation()) < 30) {
                    PlayerSettingsGuy.lookAtPlayer(p);
                }
            }
        }
    }

}
