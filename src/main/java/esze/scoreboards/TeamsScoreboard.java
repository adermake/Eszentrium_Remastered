package esze.scoreboards;

import easyscoreboards.ScoreboardUtil;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeTEAMS;
import esze.utils.EszeTeam;
import esze.utils.NBTUtils;
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
                            String li = "";
                            int totalLives = 0;
                            double maxBars = 40;

                            for (EszeTeam et : teams.allTeamsAlive) {

                                totalLives += teams.lives.get(et);

                            }

                            for (EszeTeam et : teams.allTeamsAlive) {
                                li += (et.color);

                                //Bukkit.broadcastMessage("teams.lives.get(et)"+teams.lives.get(et));
                                //Bukkit.broadcastMessage("(double)totalLives;"+(double)totalLives);
                                //Bukkit.broadcastMessage("BARS"+bars);

                                li += teams.lives.get(et) + " ";

                            }

                            //li = "|||||||||||||||||||";
                            score.add(li);
                            score.add("- - - -");
                            for (EszeTeam et : teams.allTeamsAlive) {
                                String ct = et.color + "";
                                for (Player p : et.players) {
                                    if (teams.players.contains(p)) {
                                        if (et.players.contains(scoreBoarder) && et.players.contains(p)) {
                                            score.add(ct + p.getName() + " " + (int) p.getHealth());
                                        } else {
                                            score.add(ct + p.getName());
                                        }


                                        if (teams.getTeamOfPlayer(scoreBoarder) == et && p != scoreBoarder) {

                                            for (String s : getSpells(p)) {
                                                score.add(s);
                                            }


                                        }
                                    }
                                }

                            }

                            String[] list = new String[score.size()];

                            for (int i = 0; i < list.length; i++) {
                                list[i] = score.get(i);
                                //Bukkit.broadcastMessage(""+list[i]);
                            }

                            ScoreboardUtil.unrankedSidebarDisplay(scoreBoarder, list);

                        }
                    }
                }
            }

        }.runTaskTimer(main.plugin, 0, 10);
		/*
		new BukkitRunnable() {
			
			public void run() {
				
				if(GameType.getType().name.equals("TEAMS")){
					if(Gamestate.getGameState() == Gamestate.INGAME){
						
						ArrayList<Player> living = new ArrayList<Player>();
						TypeTEAMS team = ((TypeTEAMS)GameType.getType());
						for(EszeTeam et : team.allTeamsAlive){
						
							String ct = "§"+et.color+"";
							for (Player p : et.players) {
								HashMap<String, Integer> lives = new HashMap<String, Integer>();
								
								
								for (int i = 0;i < 9 ;i++) {
									ItemStack is = p.getInventory().getItem(i);
									if (is != null && NBTUtils.getNBT("Spell",is) == "true") {
										String spellname = NBTUtils.getNBT("Spellname", is);
										String cd = NBTUtils.getNBT("Cooldown", is);
										int cooldown = 0;
										if (cd != "") {
											
										
										cooldown = (int) Double.parseDouble(cd)/20;
										}
										lives.put(spellname, cooldown);
									}
									
								}
								
								
								lives.put("§"+ct+p.getName(), team.lives.get(et));
								String[] str = new String[lives.size()];
								int i = 0;
								for (String pa : lives.keySet()) {
									
									str[i]= pa +" "+ lives.get(pa);
									
									i++;
								}
								ScoreboardUtil.unrankedSidebarDisplay(p, str);
							}
							
							
						}
						
						
						
						
						
						if (hide) {
							this.cancel();
							hide = false;
						}
					}
				}
				
				
			}
		}.runTaskTimer(main.plugin, 0, 10);
		
				
		*/
    }


    public ArrayList<String> getSpells(Player p) {
        ArrayList<String> spells = new ArrayList<String>();


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
