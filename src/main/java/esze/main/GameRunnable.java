package esze.main;

import esze.enums.GameType;
import esze.types.TypeTeamBased;
import org.bukkit.Bukkit;


public class GameRunnable {

    private static int runnableTickID;
    private static int runnableSecID;

    public static void start() {
        runnableTickID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {

            @Override
            public void run() {
                lagCheck();
                GameType.getType().runEveryTick();

            }
        }, 0, 1);

        runnableSecID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {

            @Override
            public void run() {

                GameType.getType().runEverySecond();

            }
        }, 0, 20);
    }

    public static void stop() {
        Bukkit.getScheduler().cancelTask(runnableTickID);
        Bukkit.getScheduler().cancelTask(runnableSecID);
        if (!(GameType.getType() instanceof TypeTeamBased))
            GameType.refreshGameType();
    }


    public static void lagCheck() {


    }
}
