package esze.scoreboards;

import easyscoreboards.ScoreboardUtil;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeSOLO;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
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
                        ArrayList<Player> living = new ArrayList<Player>();
                        TypeSOLO solo = ((TypeSOLO) GameType.getType());
                        for (Player p : solo.lives.keySet()) {
                            int live = solo.lives.get(p);
                            if (live == 4) {
                                lives.put("§a" + p.getName(), live);
                            } else if (live == 3) {
                                lives.put("§e" + p.getName(), live);
                            } else if (live == 2) {
                                lives.put("§6" + p.getName(), live);
                            } else if (live == 1) {
                                lives.put("§c" + p.getName(), live);
                            } else if (live == 0) {
                                lives.put("§4" + p.getName(), live);
                            }


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
