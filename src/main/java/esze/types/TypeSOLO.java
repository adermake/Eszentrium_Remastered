package esze.types;

import esze.analytics.SaveUtils;
import esze.enums.Gamestate;
import esze.main.GameRunnable;
import esze.main.LobbyBackgroundRunnable;
import esze.main.main;
import esze.menu.SoloSpellMenu;
import esze.scoreboards.SoloScoreboard;
import esze.utils.*;
import esze.voice.Discord;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.DamageCauseContainer;
import spells.spellcore.SilenceSelection;
import spells.spellcore.Spell;
import weapons.Damage;
import weapons.WeaponMenu;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TypeSOLO extends Type {

    boolean gameOver = false;
    public HashMap<Player, Integer> lives = new HashMap<Player, Integer>();
    public static HashMap<Player, Location> loc = new HashMap<Player, Location>();

    public TypeSOLO() {
        name = "SOLO";
    }

    @Override
    public void runEverySecond() {

    }

    @Override
    public void runEveryTick() {
        killInVoidCheck();
    }

    @Override
    public void gameStart() {
        won = false;
        gameOver = false;
        setupGame();
        scoreboard = new SoloScoreboard();
        scoreboard.showScoreboard();

        ArrayList<String> names = new ArrayList<>();

        for (Player p : players) {
            names.add(p.getName());
            setupPlayer(p);
            loc.put(p, p.getLocation());
            lives.put(p, 4);
            openSpellSelection(p);
        }

        SaveUtils.startSoloGame(names);

        WeaponMenu.deliverItems();
    }

    @Override
    public void death(PlayerDeathEvent event) {

        Player p = event.getEntity();
        if (deathCheck(p)) {

            p.setHealth(Damage.lastHealthTaken.get(p));
            return;
        }

        if (!spectator.contains(p)) {
            // Output death message
            String out = DamageCauseContainer.toMessage(Spell.damageCause.get(p), p.getName());
            for (Player rec : Bukkit.getOnlinePlayers()) {
                rec.sendMessage(out);
            }

            if (Spell.damageCause.get(p) == null) {
                Spell.damageCause.put(p, new DamageCauseContainer(null, null));
            }
            SaveUtils.addPlayerDeath(p.getName(), Spell.damageCause.get(p).getKiller(), Spell.damageCause.get(p).void_d, Spell.damageCause.get(p).getSpell()); // Analytics
        }

        Spell.damageCause.put(p, new DamageCauseContainer(null, null));

        p.setVelocity(new Vector(0, 0, 0));

        loseLife(p);
        checkWinner();
    }

    public void loseLife(Player p) {
        if (p == null)
            return;

        if (!lives.containsKey(p)) {
            p.teleport(nextLoc());
            return;
        }

        lives.put(p, lives.get(p) - 1);
        p.setGameMode(GameMode.ADVENTURE);
        p.setVelocity(new Vector(0, 0, 0));

        if (lives.get(p) < 1) {
            SaveUtils.setPlayerPlace(p.getName(), players.size());
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

                Player winner = players.getFirst();
                new Title(
                        PlayerHeadUtils.getHeadAsString(winner.getUniqueId().toString(), true) + " §a" + winner.getName(),
                        "§7hat gewonnen!"
                ).sendAll();
                won = true;

                SaveUtils.setPlayerPlace(winner.getName(), 1);
                endGame();
            }
        }
    }

    public void endGame() {
        Player winner = players.getFirst();
        postResult(winner);

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

        SaveUtils.endGame(); // Analytics
    }

    public void postResult(Player winner) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.addField("Gewinner", winner.getName(), false);
        String allPlayers = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.joining(" "));
        builder.addField("Teilnehmer", allPlayers, false);
        builder.setAuthor("Raiton-Game Info Service", null, "http://minel0l.lima-city.de/esze.jpg");

        builder.setColor(java.awt.Color.GREEN);
        builder.setTitle("Eszentrium SOLO");
        builder.setTimestamp(OffsetDateTime.now());

        builder.setThumbnail("http://minel0l.lima-city.de/solo.jpg");

        Discord.sendLog(builder.build());
    }

}
