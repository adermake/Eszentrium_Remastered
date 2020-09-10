package esze.types;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.analytics.solo.SaveUtils;
import esze.enums.Gamestate;
import esze.main.GameRunnable;
import esze.main.LobbyBackgroundRunnable;
import esze.main.main;
import esze.menu.SoloSpellMenu;
import esze.scoreboards.SoloScoreboard;
import esze.utils.ItemStackUtils;
import esze.utils.LobbyUtils;
import esze.utils.Music;
import esze.utils.PlayerUtils;
import esze.utils.Title;
import esze.voice.Discord;
import spells.spellcore.Spell;
import spells.spells.AntlitzderGöttin;
import weapons.WeaponMenu;


public class TypeSOLO extends Type {
	boolean gameOver = false;
	public HashMap<Player, Integer> lives = new HashMap<Player,Integer>();
	public static HashMap<Player, Location> loc= new HashMap<Player,Location>();
	public TypeSOLO() {
		name = "SOLO";
	}
	
	@Override
	public void runEverySecond() {
		for (Player p : players) {
			if (p.getLocation().getY()<60 && p.getGameMode() == GameMode.SURVIVAL) {
				if (main.damageCause.get(p) == null) {
					main.damageCause.put(p, main.unknownDamage);
				}
				if (main.damageCause.get(p).equals("") || main.damageCause.get(p).equals(main.unknownDamage)) {
					main.damageCause.put(p, main.voiddamage);
				} else if (!main.damageCause.get(p).endsWith(main.voiddamage)){
					main.damageCause.put(p, main.damageCause.get(p) + "-" + main.voiddamage);
				}
				p.damage(p.getHealth());
			}
			
			
		}
	}
	
	@Override
	public void runEveryTick() {
		
	}
	
	@Override
	public void gameStart() {
		
		won = false;
		gameOver = false;
		SaveUtils.startGame(); //Analytics
		scoreboard = new SoloScoreboard();
		scoreboard.showScoreboard();
		for (int i = 0;i<16;i++) {
			Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(nextLoc()));
		}
		Music.startRandomMusic();
		
		for (Player p : players) {
				main.damageCause.remove(p);
				main.damageCause.put(p, main.unknownDamage); //Reset damage Cause
				SaveUtils.addPlayer(p.getName()); //Analytics
				p.teleport(nextLoc());
				p.setGameMode(GameMode.SURVIVAL);
				p.getInventory().clear();
			
				if (!WeaponMenu.items.containsKey(p))
				p.getInventory().addItem(ItemStackUtils.attackSpeedify(ItemStackUtils.createItemStack(Material.WOODEN_SWORD, 1, 0, "§cSchwert", null, true)));
				
				PlayerUtils.hidePlayer(p,200);
				p.setNoDamageTicks(200);
				SoloSpellMenu s = new SoloSpellMenu();
				s.open(p);
				loc.put(p, p.getLocation());
				lives.put(p, 4);
				
				// GameMode SURV after time
				new BukkitRunnable() {
					int t =0;
					public void run() {
						t++;
						if (t > 10 * 10) {
							p.setGameMode(GameMode.SURVIVAL);
						}
						if (p.getGameMode() == GameMode.SURVIVAL) {
							this.cancel();
						}
					}
				}.runTaskLater(main.plugin, 2);
			}
		WeaponMenu.deliverItems();
		spectator.clear();
		setupJumpPad(currentmap);
		new SoloScoreboard();
		
	}
	
	@Override
	public void death(PlayerDeathEvent event) {
		Player p = event.getEntity();
		if (deathCheck(p)) {
			return;
		}
		
		if (main.damageCause.get(p) == null) {
			main.damageCause.remove(p);
			main.damageCause.put(p, main.unknownDamage);
		}
		
		if(!spectator.contains(p)) {
			//Output death message
			String out = main.toStringCause(p);
			for (Player rec : Bukkit.getOnlinePlayers()) {
				rec.sendMessage(out);
			}
			SaveUtils.addPlayerDeath(p.getName(), main.damageCause.get(p)); //Analytics 
		} else {
			//p.sendMessage("STOP DIEING!");
		}
		
		main.damageCause.put(p, main.unknownDamage);
		p.setVelocity(new Vector(0, 0, 0));
		
		loseLife(p);
		checkWinner();	
	}

	
	public void loseLife(Player p) {
		if (p == null)
			return;
		
		if (!lives.containsKey(p)) {
			Bukkit.broadcastMessage("§c[Error] Live List Bug detected!"); 
			Bukkit.broadcastMessage("§c[Error] Canceling Lifeloss!"); 
			
			p.teleport(nextLoc());
			return;
		}
			
		lives.put(p, lives.get(p)-1);
		p.setGameMode(GameMode.ADVENTURE);
		p.setVelocity(new Vector(0,0,0));
		if (lives.get(p) < 1) {
			out(p);
			checkWinner();
		}
		else {
			Spell.silenced.add(p);
			new BukkitRunnable() {
				public void run() {
					Spell.silenced.remove(p);
				}
				
			}.runTaskLater(main.plugin, 10);
			
			PlayerUtils.hidePlayer(p,200);
			p.setNoDamageTicks(200);
			p.teleport(nextLoc());
			SoloSpellMenu s;
			if (lives.get(p) == 1) {
				s = new SoloSpellMenu(true);
			}
			else {
			 s = new SoloSpellMenu();
			}
			
			loc.put(p, p.getLocation());
			new BukkitRunnable() {
				public void run() {
					s.open(p);
				}
			}.runTaskLater(main.plugin, 2);
			PlayerUtils.snare(p, true);
		}
		new BukkitRunnable() {
			int t =0;
			public void run() {
				t++;
				if (t > 10 * 10) {
					p.setGameMode(GameMode.SURVIVAL);
				}
				if (p.getGameMode() == GameMode.SURVIVAL) {
					this.cancel();
				}
			}
		}.runTaskLater(main.plugin, 2);
		
		
	}
	boolean won = false;
	public void checkWinner() {
		if (!won) {
			
		
		if (players.size()<=1 && !gameOver) {
			
			scoreboard.hideScoreboard();
			gameOver = true;
			
			
			for (Player p : Bukkit.getOnlinePlayers()) {
			
			
			for (Player winner : players) {
				Title t = new Title("§a"+winner.getName()+" hat gewonnen!");
				won = true;
				t.send(p);
				postResult(winner);
			}
			
			}
			
			for (Player winner : players) {
				SaveUtils.setWinner(winner.getName());
			}
		if (won) {
			for (Player winner : players) {
				
				postResult(winner);
			}
			SaveUtils.endGame(); //Analytics //TODO Macht ERROR
			
			Music.sp.destroy();
			GameRunnable.stop();
			Gamestate.setGameState(Gamestate.LOBBY);
			LobbyBackgroundRunnable.start();
			LobbyUtils.recallAll();
			scoreboard.hide = true;
			players.clear();
		
			
		}
		
		}
	}
	}
	
	public void postResult(Player winner) {
		/*
	    EmbedBuilder builder = new EmbedBuilder();

	   
	   
	
	    builder.appendField("Gewinner", winner.getName(), false);
	    String allPlayers = "";
	    for (Player p : Bukkit.getOnlinePlayers()) {
	    	allPlayers += p.getName()+" ";
	    }
	    builder.appendField("Teilnehmer", allPlayers, false);
	    builder.withAuthorName("Raiton-Game Info Service");
	    builder.withAuthorIcon("http://minel0l.lima-city.de/esze.jpg");

	    builder.withColor(java.awt.Color.GREEN);
	    builder.withTitle("Eszentrium SOLO");
	    builder.withTimestamp(System.currentTimeMillis());
	    
	    
	  

	    builder.withThumbnail("http://minel0l.lima-city.de/solo.jpg");

	    RequestBuffer.request(() -> Discord.channel.getGuild().getChannelByID(621398787155558400L).sendMessage(builder.build()));
		
	    */
	}
}
