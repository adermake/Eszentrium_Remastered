package esze.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class Actionbar {

    String message;

    public Actionbar(String message) {
        this.message = message;
    }

    public Actionbar sendAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            send(p);
        }
        return this;
    }

    public Actionbar send(Player p) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

        return this;
    }

}
