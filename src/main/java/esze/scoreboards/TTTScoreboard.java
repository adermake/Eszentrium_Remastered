package esze.scoreboards;

import easyscoreboards.ScoreboardUtil;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeTTT;
import esze.utils.CorpseUtils;
import esze.utils.TTTCorpse;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;


public class TTTScoreboard extends Scoreboard {

    @Override
    public void showScoreboard() {


        new BukkitRunnable() {

            public void run() {

                if (GameType.getType().name.equals("TTT")) {
                    if (Gamestate.getGameState() == Gamestate.INGAME) {
                        TypeTTT game = ((TypeTTT) GameType.getType());

                        // TRAITOR BOARD CREATION
                        ArrayList<String> traitorBoard = new ArrayList<String>();
                        traitorBoard.add("§2Spielstand §e" + ((TypeTTT) GameType.getType()).secToMin(game.secondsLeft));
                        traitorBoard.add("§c§lVerräter");
                        for (Player p : game.startTraitor) {
                            if (p.getGameMode() == GameMode.ADVENTURE) {
                                traitorBoard.add("§m" + p.getName());
                            } else {
                                traitorBoard.add(p.getName());
                            }
                        }
                        traitorBoard.add(" ");
                        traitorBoard.add("§a§lUnschuldig");
                        for (Player p : game.startInnocent) {
                            if (p.getGameMode() == GameMode.ADVENTURE) {
                                traitorBoard.add("§m" + p.getName());
                            } else {
                                traitorBoard.add(p.getName());
                            }
                        }


                        // INNOCENT BOARD CREATION
                        ArrayList<String> innoBoard = new ArrayList<String>();
                        innoBoard.add("§2Spielstand §e" + ((TypeTTT) GameType.getType()).secToMin(game.secondsLeft));
                        innoBoard.add("§3§lSpieler");
                        for (Player p : game.startplayers) {
                            if (game.players.contains(p)) {
                                innoBoard.add(p.getName());
                            } else if (game.spectator.contains(p)) {
                                for (Integer corpseID : CorpseUtils.getAllCorpseIDs()) {
                                    if (CorpseUtils.getCorpseName(corpseID).equalsIgnoreCase(p.getName())) {
                                        TTTCorpse corpse = null;
                                        for (TTTCorpse _corpse : TTTCorpse.allCorpses) {
                                            if (_corpse.corpseID == corpseID) {
                                                corpse = _corpse;
                                            }
                                        }

                                        if (corpse != null) {
                                            if (corpse.isExposed) {
                                                innoBoard.add("§m" + p.getName());
                                            } else {
                                                innoBoard.add(p.getName());
                                            }
                                        } else {
                                            innoBoard.add(p.getName());
                                        }

                                    }
                                }
                            }
                        }

                        Bukkit.getOnlinePlayers().stream()
                                .filter(p -> game.spectator.contains(p))
                                .forEach(p -> ScoreboardUtil.unrankedSidebarDisplay(p, innoBoard.toArray(new String[0])));

                        ScoreboardUtil.unrankedSidebarDisplay(game.innocent, innoBoard.toArray(new String[0]));

                        ScoreboardUtil.unrankedSidebarDisplay(game.traitor, traitorBoard.toArray(new String[0]));

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
