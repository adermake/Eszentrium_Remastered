package esze.utils;

import esze.main.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class PlayerUtils {


    public static ArrayList<Player> snared = new ArrayList<Player>();


    public static void hidePlayer(Player p, int ammount) {

        hidePlayer(p);
        new BukkitRunnable() {
            public void run() {
                showPlayer(p);
            }
        }.runTaskLater(main.plugin, ammount);
    }


    public static void hidePlayer(Player p) {


        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.hidePlayer(main.plugin, p);
        }

    }

    public static void showPlayer(Player p) {

        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.showPlayer(main.plugin, p);
        }
    }

    public static void showAllPlayers() {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                pl.showPlayer(main.plugin, p);
            }

        }
    }

    public static void stopVelocity() {
        new BukkitRunnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for (Player p : snared) {

                    p.setVelocity(new Vector(0, 0, 0));

                }


            }
        }.runTaskTimer(main.plugin, 1, 1);
    }

    public static void snare(Player p, boolean b) {
        if (b) {
            if (!snared.contains(p))
                snared.add(p);
        } else {
            if (snared.contains(p))
                snared.remove(p);
        }
    }
}
