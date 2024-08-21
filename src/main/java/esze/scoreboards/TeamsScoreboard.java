package esze.scoreboards;

import easyscoreboards.ScoreboardUtil;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeTEAMS;
import esze.utils.EszeTeam;
import esze.utils.NBTUtils;
import esze.utils.PlayerHeadUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TeamsScoreboard extends Scoreboard {


    @Override
    public void showScoreboard() {
        new BukkitRunnable() {

            public void run() {

                if (GameType.getType().name.equals("TEAMS")) {
                    if (Gamestate.getGameState() == Gamestate.INGAME) {

                        for (Player scoreBoarder : Bukkit.getOnlinePlayers()) {

                            ArrayList<String> score = new ArrayList<String>();
                            score.add("Leben");
                            TypeTEAMS teams = ((TypeTEAMS) GameType.getType());
                            StringBuilder li = new StringBuilder();
                            for (EszeTeam et : teams.allTeamsAlive) {
                                li.append(et.getChatColor());
                                li.append(teams.lives.get(et)).append(" ");
                            }

                            score.add(li.toString());
                            score.add("- - - -");
                            for (EszeTeam et : teams.allTeamsAlive) {
                                String ct = et.getChatColor() + "";
                                for (Player p : et.getPlayers()) {
                                    if (teams.players.contains(p)) {
                                        if (et.containsPlayer(scoreBoarder) && et.containsPlayer(p)) {
                                            score.add(PlayerHeadUtils.getHeadAsString(p.getUniqueId().toString(), true) + " " + ct + p.getName() + " " + (int) p.getHealth());
                                        } else {
                                            score.add(PlayerHeadUtils.getHeadAsString(p.getUniqueId().toString(), true) + " " + ct + p.getName());
                                        }


                                        if (teams.getTeamOfPlayer(scoreBoarder) == et && p != scoreBoarder) {
                                            score.addAll(getSpells(p));
                                        }
                                    }
                                }

                            }

                            String[] list = new String[score.size()];

                            for (int i = 0; i < list.length; i++) {
                                list[i] = score.get(i);
                            }

                            ScoreboardUtil.unrankedSidebarDisplay(scoreBoarder, list);

                        }
                    }
                }
            }

        }.runTaskTimer(main.plugin, 0, 10);
    }


    public ArrayList<String> getSpells(Player p) {
        ArrayList<String> spells = new ArrayList<>();


        for (int i = 0; i < 9; i++) {

            ItemStack is = p.getInventory().getItem(i);
            if (is != null && NBTUtils.getNBT("Spell", is).equals("true")) {
                String spellname = NBTUtils.getNBT("OriginalName", is);
                String cd = NBTUtils.getNBT("Cooldown", is);
                int cooldown = 0;
                if (cd != "") {


                    cooldown = (int) Double.parseDouble(cd) / 20;
                }
                boolean refined = false;
                if (is.getItemMeta().getDisplayName().substring(0, 2).equals("§2")) {
                    refined = true;
                }

                String preTag = "";
                if (refined) {
                    preTag = "+";
                }
                if (cooldown == 0) {
                    if (spellname == "") {
                        spells.add("" + is.getItemMeta().getDisplayName().substring(2) + preTag);
                    } else {
                        spells.add("" + spellname.substring(2) + preTag);
                    }

                } else {
                    spells.add("§7" + spellname.substring(2) + preTag + " " + cooldown);
                }

            }


        }


        return spells;

    }
}
