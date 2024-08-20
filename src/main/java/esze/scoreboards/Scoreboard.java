package esze.scoreboards;

import org.bukkit.Bukkit;

public abstract class Scoreboard {

    public boolean hide = false;

    public void showScoreboard() {

    }

    public void hideScoreboard() {
        Bukkit.broadcastMessage("Hiding scoreboard");
        hide = true;
    }
}
