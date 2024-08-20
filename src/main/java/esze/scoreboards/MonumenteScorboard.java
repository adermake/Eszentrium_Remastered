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
                            if (e.color == ChatColor.RED) {
                                souls.add("§cRoter §rNexus: " + gamemode.redNexus.getSoulCount());
                                souls.add("§7-----------------");
                            }
                            if (e.color == ChatColor.BLUE) {
                                souls.add("§9Blauer §rNexus: " + gamemode.blueNexus.getSoulCount());
                                souls.add("§7-----------------");
                            }
                            for (Player p : e.players) {
                                String tag = e.teamName.substring(0, 2);
                                souls.add(tag + "" + p.getName() + " §r: " + gamemode.soulCount.get(p));
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
