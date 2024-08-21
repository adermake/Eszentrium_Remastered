package esze.scoreboards;

import easyscoreboards.ScoreboardUtil;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeMONUMENTE;
import esze.utils.EszeTeam;
import esze.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;

public class MonumenteScorboard extends Scoreboard {


    @Override
    public void showScoreboard() {
        new BukkitRunnable() {
            public void run() {
                if (GameType.getType() instanceof TypeMONUMENTE) {
                    if (Gamestate.getGameState() == Gamestate.INGAME) {
                        ArrayList<String> souls = new ArrayList<String>();
                        souls.add("Seelen");
                        TypeMONUMENTE gamemode = ((TypeMONUMENTE) GameType.getType());
                        for (EszeTeam e : gamemode.allTeams) {
                            if (e.getChatColor() == ChatColor.RED) {
                                souls.add("§cRoter §rNexus: " + gamemode.redNexus.getSoulCount());
                                souls.add("§7-----------------");
                            }
                            if (e.getChatColor() == ChatColor.BLUE) {
                                souls.add("§9Blauer §rNexus: " + gamemode.blueNexus.getSoulCount());
                                souls.add("§7-----------------");
                            }
                            for (Player p : e.getPlayers()) {
                                String tag = e.getTeamName().substring(0, 2);
                                souls.add(tag + p.getName() + " §r: " + gamemode.soulCount.get(p));
                            }
                        }

                        String[] ls = new String[souls.size()];
                        for (int i = 0; i < souls.size(); i++) {

                            ls[i] = souls.get(i);
                        }
                        ScoreboardUtil.unrankedSidebarDisplay((Collection<Player>) Bukkit.getOnlinePlayers(), ls);

                    }
                }
            }

        }.runTaskTimer(main.plugin, 0, 10);
    }




}
