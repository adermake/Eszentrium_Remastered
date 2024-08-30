package esze.utils;

import org.bukkit.Bukkit;

public class DebugUtils {

    private static long startTime = 0;

    public static void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public static void stopTimer(String text) {
        long endTime = System.currentTimeMillis();
        Bukkit.broadcastMessage(text + ": " + (endTime - startTime) + "ms");
    }

}
