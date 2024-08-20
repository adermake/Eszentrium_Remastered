package esze.utils;

import org.bukkit.entity.Player;

public class TabList {

    String header;
    String footer;

    public TabList(String header, String footer) {
        this.header = header;
        this.footer = footer;
    }

    public void send(Player player) {
        player.setPlayerListHeaderFooter(header, footer);
    }


}
