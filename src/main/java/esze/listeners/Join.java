package esze.listeners;

import esze.configs.PlayerSettingsGuy;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.players.PlayerAPI;
import esze.players.PlayerInfo;
import esze.types.TypeTEAMS;
import esze.utils.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(20D);
        p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(0.5D);
        p.setExp(0F);
        p.setLevel(0);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.setMaxHealth(20);
        p.setWalkSpeed(0.2F);

        TextComponent component = new TextComponent();
        component.setText(CharRepo.ESZE_LOGO + "  ");
        component.setFont("minecraft:default");
        new TabList(TextComponent.toLegacyText(component)+"\n\n\n\n", "\n§7a §3knet§bzwerk §7exclusive").send(p);

        //Clears Inventory of Players
        GameType.getType().givePlayerLobbyItems(p);
        e.setJoinMessage("");
        if (Gamestate.getGameState() == Gamestate.LOBBY) {
            Bukkit.getOnlinePlayers().stream().filter(player -> player != p).forEach(player -> player.sendMessage("§8> §3" + p.getName() + " §7ist beigetreten."));
            LobbyUtils.recall(p);
            Bukkit.getScheduler().scheduleSyncDelayedTask(main.plugin, () -> {
                p.sendMessage(TextComponent.toLegacyText(component));
                p.sendMessage("");
                p.sendMessage("");
                p.sendMessage("");
                p.sendMessage("");
                p.sendMessage("§8| §7Willkommen auf §6Esze§7! §7Viel Spaß beim Spielen!");
            }, 10);
        } else if (Gamestate.getGameState() == Gamestate.INGAME) {
            p.teleport(GameType.getType().nextLoc());
            PlayerUtils.hidePlayer(p);
            p.getInventory().clear();
            p.setGameMode(GameMode.ADVENTURE);
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage("§8| §7Die Runde läuft. Du bist Zuschauer!");

            if (PlayerAPI.getPlayerInfo(p) == null) {
                PlayerInfo pi = new PlayerInfo(p);
                pi.isAlive = false;
                pi.isInRound = false;
            }
        }

        PlayerSettingsGuy.spawnPlayerSettingsGuy(p);
        PacketReceiver.addPacketReceiver(p);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage("§8< §6" + p.getName() + " §7hat das Spiel verlassen");
        ScoreboardTeamUtils.giveScoreboard(p);

        if (PlayerAPI.getPlayerInfo(p) != null) {
            PlayerAPI.removePlayer(p);
        }

        if(Gamestate.getGameState() == Gamestate.INGAME) {
            GameType.getType().out(p, false);
        }
        if (GameType.getType() instanceof TypeTEAMS tt) {
            tt.removePlayerFromAllTeams(p);
        }
    }

}
