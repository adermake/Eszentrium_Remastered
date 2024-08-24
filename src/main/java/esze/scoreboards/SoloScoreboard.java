package esze.scoreboards;

import easyscoreboards.ScoreboardUtil;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeSOLO;
import esze.utils.PlayerHeadUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;


public class SoloScoreboard extends Scoreboard {

    @Override
    public void showScoreboard() {

        new BukkitRunnable() {
            public void run() {

                if (GameType.getType().name.equals("SOLO")) {
                    if (Gamestate.getGameState() == Gamestate.INGAME) {
                        HashMap<String, Integer> lives = new HashMap<String, Integer>();
                        TypeSOLO solo = ((TypeSOLO) GameType.getType());
                        for (Player p : solo.lives.keySet()) {
                            int live = solo.lives.get(p);
                            String output = PlayerHeadUtils.getHeadAsString(p.getUniqueId().toString(), true) + " ";
                            output += switch (live) {
                                case 4 -> "§a";
                                case 3 -> "§e";
                                case 2 -> "§6";
                                case 1 -> "§c";
                                case 0 -> "§4";
                                default -> throw new IllegalStateException("Unexpected value: " + live);
                            };
                            output += p.getName();
                            lives.put(output, live);
                        }

                        ScoreboardUtil.rankedSidebarDisplay((Collection<Player>) Bukkit.getOnlinePlayers(), "Leben", lives);


                        if (hide) {
                            this.cancel();
                            hide = false;
                        }
                    }
                }


            }
        }.runTaskTimer(main.plugin, 0, 10);


    }


}
