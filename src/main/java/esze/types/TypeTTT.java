package esze.types;

import com.google.common.base.Preconditions;
import esze.enums.Gamestate;
import esze.main.GameRunnable;
import esze.main.LobbyBackgroundRunnable;
import esze.main.main;
import esze.scoreboards.TTTScoreboard;
import esze.utils.*;
import esze.voice.Discord;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import spells.spellcore.DamageCauseContainer;
import spells.spellcore.Spell;
import spells.spellcore.Spelldrop;
import weapons.WeaponMenu;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class TypeTTT extends Type {
    boolean gameOver = false;
    public ArrayList<Player> innocent = new ArrayList<Player>();
    public ArrayList<Player> traitor = new ArrayList<Player>();
    public ArrayList<Player> startInnocent = new ArrayList<Player>();
    public ArrayList<Player> startTraitor = new ArrayList<Player>();


    int gameLengthSeconds = 60 * 7;
    public int secondsLeft = 0;

    public TypeTTT() {
        name = "TTT";
    }

    int sec = 0;

    @Override
    public void runEverySecond() {

        killInVoidCheck();

        secondsLeft--;
        if (secondsLeft <= 0) {
            //
            traitor.clear();
            checkWinner();
            //
        }


        for (Player p : players) {

            //TabList.setPlayerlistHeader(p, ""+secondsLeft);
            if (p.getLocation().getY() < 60 && p.getGameMode() == GameMode.SURVIVAL) {

                //p.damage(40);
            }


        }
        sec++;
        int i = 20 - players.size() * 2;
        if (i < 5)
            i = 5;
        if (sec > i) {
            spawnNewSpell();
            sec = 0;
        }

    }


    public String secToMin(int seconds) {
        int sec = seconds;
        int min = sec / 60;
        sec = sec - min * 60;

        String ret = min + ":" + sec;
        if (sec < 10) {
            ret = min + ":0" + sec;
        }
        return ret;
    }

    @Override
    public void runEveryTick() {
        for (org.bukkit.entity.ArmorStand as : Spelldrop.items.keySet()) {
            as.setRightArmPose(as.getRightArmPose().add(0, 0.1, 0));
            ParUtils.createParticle(Particle.CLOUD, as.getLocation().clone().add(0, 0.5, 0), 0, 0, 0, 1, 0);
        }
    }


    @Override
    public void gameStart() {

        System.out.println("1");

        secondsLeft = gameLengthSeconds;

        innocent.clear();
        traitor.clear();

        startInnocent.clear();
        startTraitor.clear();


        scoreboard = new TTTScoreboard();
        scoreboard.showScoreboard();

        won = false;
        gameOver = false;

        int playerCount = players.size();
        int traitorCount = 1;


        scoreboard = new TTTScoreboard();
        scoreboard.showScoreboard();
        System.out.println("3");
        PlayerUtils.showAllPlayers();

        setupJumpPad(currentmap);

        Music.startRandomMusic();
        for (Player p : players) {
            p.setHealth(20);
            p.teleport(nextLoc());
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
            p.setLevel(0);
            if (!WeaponMenu.items.containsKey(p))
                p.getInventory().addItem(ItemStackUtils.createItemStack(Material.WOODEN_SWORD, 1, 0, "§cSchwert", null, true));

        }
        WeaponMenu.deliverItems();
        //<
        for (int i = 0; i < players.size(); i++) {
            spawnNewSpell();
        }

        if (((int) playerCount / 3) - 1 > 0)
            traitorCount += ((int) playerCount / 3) - 1;

        if (playerCount == 6)
            traitorCount = 1;

        for (int i = 0; i < traitorCount; i++) {
            int index = MathUtils.randInt(0, players.size() - 1);
            setTraitor(players.get(index));

        }

        for (Player p : players) {
            if (!traitor.contains(p)) {
                setInnocent(p);
            }
        }
        //
        for (Player p : innocent) {
            p.sendMessage("§8| §7Du bist §6unschuldig!");
            new Title("§a§lUNSCHULDIGER", "ist deine Rolle").send(p);
            p.getInventory().setItem(8, ItemStackUtils.createItemStack(Material.EMERALD, 1, 0, "§eWeltenkatalysator", null, true));
        }
        for (Player p : traitor) {

            p.setLevel(10);

            if (traitor.size() > 1) {
                String build = "§8| §7Du bist ein Traitor mit §6";
                for (Player p2 : traitor) {
                    if (!p2.equals(p)) {
                        build += p2.getName() + ", ";
                    }
                }
                build = build.substring(0, build.length() - 2) + ".";
                p.sendMessage(build);
            } else {
                p.sendMessage("§8| §7Du bist §6alleine §7Verräter!");
            }
            new Title("§c§lVERRÄTER", "ist deine Rolle").send(p);
            p.getInventory().setItem(8, ItemStackUtils.createItemStack(Material.EMERALD, 1, 0, "§cSchwarzmarkt", null, true));
        }
		
		/*
		scoreboard = new SoloScoreboard();
		scoreboard.showScoreboard();
		for (Player p : players) {
				p.teleport(nextLoc());
				p.setGameMode(GameMode.SURVIVAL);
				p.getInventory().clear();
			
					p.getInventory().addItem(ItemStackUtils.createItemStack(Material.WOODEN_SWORD, 1, 0, "§eHolz-Schwert", null, true));
				
				PlayerUtils.hidePlayer(p,100);
				p.setNoDamageTicks(100);
				SoloSpellMenu s = new SoloSpellMenu();
				s.open(p);
				lives.put(p, 4);
			}
		setupJumpPad(currentmap);
		new SoloScoreboard();
		*/

    }

    @Override
    public void death(PlayerDeathEvent event) {

        Player p = event.getEntity();
        if (deathCheck(p)) {
            return;
        }
        if (p.getGameMode() == GameMode.ADVENTURE)
            return;
        p.closeInventory();

        TTTCorpse corpse = new TTTCorpse(p, true);
        corpse.spawn();

        p.getInventory().clear();
        spectator.add(p);
        players.remove(p);
        p.setGameMode(GameMode.ADVENTURE);
        if (Spell.damageCause.containsKey(p)) {
            DamageCauseContainer dcc = Spell.damageCause.get(p);
            if (traitor.contains(dcc.killer)) {
                dcc.killer.setLevel(dcc.killer.getLevel() + 5);
            }
        }

        p.setHealth(20);
        if (p.getLocation().getY() < 60) {
            p.teleport(nextLoc());
        }


        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);

        PlayerUtils.hidePlayer(p);

        if (innocent.contains(p))
            innocent.remove(p);
        if (traitor.contains(p))
            traitor.remove(p);

        p.setNoDamageTicks(100);


        checkWinner();

        Discord.setMuted(p, true);
    }

    public void spawnNewSpell() {

        Location nLoc = nextLoc();

        Location a = getRandomLocation(nLoc.clone().add(30, 5, 30), nLoc.clone().add(-30, -40, -30));
        //int b =  Bukkit.getWorld("world").getHighestBlockYAt(a);
        //a.setY(b);

        int tries = 0;
        Location next = a.clone();
        while (true) {
            if (next.getBlock().getType() == Material.AIR
                    && next.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()
                    && next.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
                break;
            }

            if (tries >= 80) {
                break;
            } else {
                tries++;
                next.add(0, 1, 0);
            }
        }

        a = next.clone();
        a.setX(a.getBlockX());
        a.setZ(a.getBlockZ());
        a.setY(a.getBlockY());

        if (next.getBlock().getType() == Material.AIR
                && next.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()
                && next.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
            new Spelldrop(a);
        } else {
            spawnNewSpell();
        }


    }

    public static Location getRandomLocation(Location loc1, Location loc2) {
        Preconditions.checkArgument(loc1.getWorld() == loc2.getWorld());
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return new Location(loc1.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ));
    }

    public static double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }

    public void spawnNewSpellOLD() {

        Location loc = nextLoc();
        loc.add(MathUtils.randInt(-30, 30), MathUtils.randInt(-10, 30), MathUtils.randInt(-30, 30));

        while (!loc.getBlock().getType().isSolid()) {
            loc.add(0, -1, 0);
            if (loc.getY() < 60) {
                spawnNewSpellOLD();
                return;

            }
        }
        while (loc.clone().add(0, 1, 0).getBlock().getType().isSolid()) {
            loc.add(0, 1, 0);
            if (loc.getY() > 200) {
                spawnNewSpellOLD();
                return;

            }
        }
        new Spelldrop(loc);
    }

    public void setTraitor(Player p) {

        //p.sendMessage("§7Du bist der §4Verräter");
        traitor.add(p);
        startTraitor.add(p);
    }

    public void setInnocent(Player p) {
        //p.sendMessage("§7Du bist §aunschuldig");
        innocent.add(p);
        startInnocent.add(p);
    }

    boolean won = false;

    public void checkWinner() {
        if (!won) {
            if (innocent.isEmpty() && !gameOver) {
                scoreboard.hideScoreboard();

                for (Player p : Bukkit.getOnlinePlayers()) {
                    Title t = new Title("§cVerräter");
                    t.setSubtitle("§7haben gewonnen!");
                    won = true;

                    t.send(p);
                }

                postResult(false);

            } else if (traitor.isEmpty() && !gameOver) {

                scoreboard.hideScoreboard();


                for (Player p : Bukkit.getOnlinePlayers()) {
                    Title t = new Title("§7Die §aUnschuldigen");
                    t.setSubtitle("§7haben gewonnen!");
                    won = true;
                    t.send(p);
                }
                postResult(true);
            }

            if (won && !gameOver) {
                endGame();
            }
        }
    }


    public void postResult(boolean innoWin) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.addField("Gewinnerteam", innoWin ? "Die Unschuldigen" : "Die Verräter", true);
        String winners = (innoWin ? startInnocent : startTraitor).stream().map(Player::getName).collect(Collectors.joining(" "));

        builder.addField("Gewinner", winners, false);
        String allPlayers = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.joining(" "));
        builder.addField("Teilnehmer", allPlayers, false);
        builder.setAuthor("Raiton-Game Info Service", null, "http://minel0l.lima-city.de/esze.jpg");

        builder.setColor(java.awt.Color.BLUE);
        builder.setTitle("Eszentrium TTT");
        builder.setTimestamp(OffsetDateTime.now());

        builder.setDescription("Spielzeit: " + secToMin(gameLengthSeconds - secondsLeft));

        builder.setThumbnail("http://minel0l.lima-city.de/ttt.jpg");

        Discord.sendLog(builder.build());
    }


    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        new BukkitRunnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Discord.unMuteAll();
            }
        }.runTaskLater(main.plugin, 20);

        new BukkitRunnable() {
            public void run() {
                if (Music.sp != null)
                    Music.sp.destroy();
            }
        }.runTaskLater(main.plugin, 5);
        CorpseUtils.resetAllCorpses();
        for (Entity e : Bukkit.getWorld("world").getEntities()) {
            if (e.getType() != EntityType.PLAYER) {
                e.remove();
            }
        }

        Spelldrop.items.clear();
        GameRunnable.stop();
        Gamestate.setGameState(Gamestate.LOBBY);
        LobbyBackgroundRunnable.start();
        LobbyUtils.recallAll();
        scoreboard.hide = true;
        gameOver = true;
        players.clear();
    }

}
