package esze.listeners;

import esze.players.PlayerAPI;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.ArrayList;

public class FBoost implements Listener {
    public static ArrayList<Player> noFboost = new ArrayList<Player>();

    @EventHandler
    public void onF(PlayerSwapHandItemsEvent e) {

        Player p = e.getPlayer();
        if (noFboost.contains(p))
            return;
        if ((p.isOp() && p.getGameMode() == GameMode.CREATIVE) || !PlayerAPI.getPlayerInfo(p).isAlive) {
            p.setVelocity(p.getLocation().getDirection().multiply(2));
        }
        e.setCancelled(true);
    }


}
