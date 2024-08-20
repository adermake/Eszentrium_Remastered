package esze.map;

import esze.main.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class JumpPadHandler implements Listener {


    public static ArrayList<JumpPad> jumpPads = new ArrayList<JumpPad>();
    public static ArrayList<Player> onCooldown = new ArrayList<Player>();

    public static void start() {
        Bukkit.getScheduler().runTaskTimer(main.plugin, new Runnable() {

            //LAGS??
            @Override
            public void run() {
                for (LivingEntity e : Bukkit.getWorlds().get(0).getLivingEntities()) {
                    if (!(e instanceof Player) && !(e instanceof ArmorStand) && !(e instanceof Slime)) {
                        checkJumpPads(e);
                    }
                }
            }
        }, 0, 1);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();
        if (onCooldown.contains(p))
            return;


        checkJumpPads(p);
    }


    public static void checkJumpPads(Entity p) {

        for (JumpPad pad : jumpPads) {

            if (p.getLocation().distance(pad.loc) < 1) {
                pad.launch(p);
            }
        }

    }


    public static void loadJumpPads(String map) {
        int padNumber = 1;

        while (main.plugin.getConfig().contains("jumppads." + map + "." + padNumber)) {

            JumpPad jp = (JumpPad) main.plugin.getConfig().get("jumppads." + map + "." + padNumber);
            padNumber++;

            jumpPads.add(jp);
        }


    }

}
