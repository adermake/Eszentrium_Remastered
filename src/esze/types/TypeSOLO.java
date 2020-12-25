package esze.types;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
import spells.spellcore.SilenceSelection;
import spells.spellcore.Spell;
import spells.spells.AntlitzderGöttin;
import weapons.Damage;
import weapons.WeaponMenu;

public class TypeSOLO extends Type {

	boolean gameOver = false;
	public HashMap<Player, Integer> lives = new HashMap<Player, Integer>();
	public static HashMap<Player, Location> loc = new HashMap<Player, Location>();

	public TypeSOLO() {
		name = "SOLO";
	}

	@Override
	public void runEverySecond() {
		killInVoidCheck();
	}

	@Override
	public void runEveryTick() {

	}

	@Override
	public void gameStart() {
		won = false;
		gameOver = false;
		setupGame();
		SaveUtils.startGame(); // Analytics
		scoreboard = new SoloScoreboard();
		scoreboard.showScoreboard();
		for (Player p : players) {
			SaveUtils.addPlayer(p.getName());
			setupPlayer(p);
			loc.put(p, p.getLocation());
			lives.put(p, 4);
			openSpellSelection(p);
			
		}
		WeaponMenu.deliverItems(); 
	}

	@Override
	public void death(PlayerDeathEvent event) {

		Player p = event.getEntity();
		if (deathCheck(p)) {

			p.setHealth(Damage.lastHealthTaken.get(p));
			return;
		}

		if (main.damageCause.get(p) == null) {
			main.damageCause.remove(p);
			main.damageCause.put(p, unknownDamage);
		}

		if (!spectator.contains(p)) {
			// Output death message
			String out = toStringCause(p);
			for (Player rec : Bukkit.getOnlinePlayers()) {
				rec.sendMessage(out);
			}
			SaveUtils.addPlayerDeath(p.getName(), main.damageCause.get(p)); // Analytics
		} else {
			// p.sendMessage("STOP DIEING!");
		}

		main.damageCause.put(p, unknownDamage);
		p.setVelocity(new Vector(0, 0, 0));

		loseLife(p);
		checkWinner();
	}

	public void loseLife(Player p) {
		if (p == null)
			return;

		if (!lives.containsKey(p)) {
			//Bukkit.broadcastMessage("§c[Error] Live List Bug detected!");
			//Bukkit.broadcastMessage("§c[Error] Canceling Lifeloss!");
			p.teleport(nextLoc());
			return;
		}

		lives.put(p, lives.get(p) - 1);
		p.setGameMode(GameMode.ADVENTURE);
		p.setVelocity(new Vector(0, 0, 0));
		
		if (lives.get(p) < 1) {
			out(p);
			checkWinner();
		} else {
			Spell.silenced.put(p, new SilenceSelection());
			new BukkitRunnable() {
				public void run() {
					Spell.silenced.remove(p);
				}

			}.runTaskLater(main.plugin, 10);

			p.teleport(nextLoc());
			SoloSpellMenu s;
			if (lives.get(p) == 1) {
				s = new SoloSpellMenu(true);
			} else {
				s = new SoloSpellMenu();
			}

			loc.put(p, p.getLocation());
			new BukkitRunnable() {
				public void run() {
					s.open(p);

					PlayerUtils.snare(p, true);
					p.setGameMode(GameMode.ADVENTURE);
					new BukkitRunnable() {
						int t = 0;

						public void run() {
							t++;
							PlayerUtils.hidePlayer(p);
							p.setNoDamageTicks(4);
							if (t > 10 * 10) {

								p.setGameMode(GameMode.SURVIVAL);
								PlayerUtils.showPlayer(p);
							}
							if (p.getGameMode() == GameMode.SURVIVAL) {
								PlayerUtils.showPlayer(p);
								this.cancel();
							}
						}
					}.runTaskTimer(main.plugin, 2, 2);
				}
			}.runTaskLater(main.plugin, 2);

		}

	}

	boolean won = false;

	public void checkWinner() {
		if (!won) {

			if (players.size() <= 1 && !gameOver) {

				scoreboard.hideScoreboard();
				gameOver = true;

				for (Player p : Bukkit.getOnlinePlayers()) {

					for (Player winner : players) {
						Title t = new Title("§a" + winner.getName() + " hat gewonnen!");
						won = true;
						t.send(p);
						postResult(winner);
					}

				}

				for (Player winner : players) {
					SaveUtils.setWinner(winner.getName());
				}
				if (won) {

					endGame();

				}

			}
		}
	}

	public void endGame() {
		for (Player winner : players) {

			postResult(winner);
		}
		SaveUtils.endGame(); // Analytics //TODO Macht ERROR

		for (Entity e : Bukkit.getWorld("world").getEntities()) {
			if (e.getType() != EntityType.PLAYER) {
				e.remove();
			}
		}

		Music.sp.destroy();
		new BukkitRunnable() {
			public void run() {
				if (Music.sp != null)
					Music.sp.destroy();
			}
		}.runTaskLater(main.plugin, 5);
		GameRunnable.stop();
		Gamestate.setGameState(Gamestate.LOBBY);
		LobbyBackgroundRunnable.start();
		LobbyUtils.recallAll();
		scoreboard.hide = true;
		players.clear();
	}

	public void postResult(Player winner) {
		/*
		 * EmbedBuilder builder = new EmbedBuilder();
		 * 
		 * 
		 * 
		 * 
		 * builder.appendField("Gewinner", winner.getName(), false); String allPlayers =
		 * ""; for (Player p : Bukkit.getOnlinePlayers()) { allPlayers +=
		 * p.getName()+" "; } builder.appendField("Teilnehmer", allPlayers, false);
		 * builder.withAuthorName("Raiton-Game Info Service");
		 * builder.withAuthorIcon("http://minel0l.lima-city.de/esze.jpg");
		 * 
		 * builder.withColor(java.awt.Color.GREEN);
		 * builder.withTitle("Eszentrium SOLO");
		 * builder.withTimestamp(System.currentTimeMillis());
		 * 
		 * 
		 * 
		 * 
		 * builder.withThumbnail("http://minel0l.lima-city.de/solo.jpg");
		 * 
		 * RequestBuffer.request(() ->
		 * Discord.channel.getGuild().getChannelByID(621398787155558400L).sendMessage(
		 * builder.build()));
		 * 
		 */
	}

}
