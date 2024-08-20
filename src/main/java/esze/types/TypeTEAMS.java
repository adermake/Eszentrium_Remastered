package esze.types;

import esze.analytics.SaveUtils;
import esze.enums.Gamestate;
import esze.main.GameRunnable;
import esze.main.LobbyBackgroundRunnable;
import esze.main.main;
import esze.menu.TeamSpellMenu;
import esze.scoreboards.TeamsScoreboard;
import esze.utils.*;
import org.bukkit.*;
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

import java.util.ArrayList;
import java.util.HashMap;

public class TypeTEAMS extends TypeTeamBased {


    public ArrayList<EszeTeam> allTeamsAlive = new ArrayList<EszeTeam>();
    public static HashMap<Player, Location> loc = new HashMap<Player, Location>();
    public HashMap<EszeTeam, Integer> lives = new HashMap<EszeTeam, Integer>();
    public boolean won = false;
    public boolean gameOver = false;

    public TypeTEAMS() {
        name = "TEAMS";


        allTeams.add(new EszeTeam("§cRot", ChatColor.RED, Color.RED, Material.RED_WOOL));
        allTeams.add(new EszeTeam("§9Blau", ChatColor.BLUE, Color.BLUE, Material.BLUE_WOOL));
        allTeams.add(new EszeTeam("§aGrün", ChatColor.GREEN, Color.GREEN, Material.GREEN_WOOL));
        allTeams.add(new EszeTeam("§eGelb", ChatColor.YELLOW, Color.YELLOW, Material.YELLOW_WOOL));

        for (Player p : Bukkit.getOnlinePlayers()) {
            givePlayerLobbyItems(p);
        }

    }


    @Override
    public void endGame() {
        // TODO Auto-generated method stub

        SaveUtils.endGame();

        for (Entity e : Bukkit.getWorld("world").getEntities()) {
            if (e.getType() != EntityType.PLAYER) {
                e.remove();
            }
        }


        new BukkitRunnable() {
            public void run() {
                if (Music.sp != null)
                    Music.sp.destroy();
            }
        }.runTaskLater(main.plugin, 5);
        Music.sp.destroy();
        GameRunnable.stop();
        Gamestate.setGameState(Gamestate.LOBBY);
        LobbyBackgroundRunnable.start();
        LobbyUtils.recallAll();
        scoreboard.hide = true;
        players.clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setGlowing(true);
        }
    }

    @Override
    public void runEverySecond() {

    }

    @Override
    public void runEveryTick() {
        killInVoidCheck();

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
        } else {
            // p.sendMessage("STOP DIEING!");
        }

        Spell.damageCause.put(p, new DamageCauseContainer(null, null));

        p.setVelocity(new Vector(0, 0, 0));

        loseLife(p);
        checkWinner();
    }

    public void loseLife(Player p) {
        if (p == null)
            return;

        if (!lives.containsKey(getTeamOfPlayer(p))) {
            //Bukkit.broadcastMessage("§c[Error] Live List Bug detected!");
            //Bukkit.broadcastMessage("§c[Error] Canceling Lifeloss!");
            p.teleport(nextLoc());
            return;
        }
        if (players.contains(p))
            lives.put(getTeamOfPlayer(p), lives.get(getTeamOfPlayer(p)) - 1);
        p.setGameMode(GameMode.ADVENTURE);
        p.setVelocity(new Vector(0, 0, 0));

        if (lives.get(getTeamOfPlayer(p)) < 1) {
            out(p);
            checkTeamOut(p);
            checkWinner();
        } else {
            Spell.silenced.put(p, new SilenceSelection());
            new BukkitRunnable() {
                public void run() {
                    Spell.silenced.remove(p);
                }

            }.runTaskLater(main.plugin, 10);

            p.teleport(nextLoc());
            TeamSpellMenu s;
            if (lives.get(getTeamOfPlayer(p)) < getTeamOfPlayer(p).players.size() + 1) {
                s = new TeamSpellMenu(true);
            } else {
                s = new TeamSpellMenu();
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

    public boolean teamSillHasPlayers(EszeTeam et) {
        boolean has = false;
        for (Player p : et.players) {
            if (players.contains(p)) {
                has = true;
            }
        }
        return has;
    }

    public void checkWinner() {

        if (!won) {


            if (allTeamsAlive.size() <= 1 && !gameOver) {

                SaveUtils.setPlayerPlace(allTeamsAlive.get(0).players.get(0).getName(), 1); //Analytics

                scoreboard.hideScoreboard();
                gameOver = true;

                for (Player p : Bukkit.getOnlinePlayers()) {

                    for (EszeTeam team : allTeamsAlive) {
                        Title t = new Title("§7" + team.teamName + " §7hat gewonnen!");
                        won = true;
                        t.send(p);

                    }

                }


                if (won) {

                    endGame();

                }

            }
        }
    }

    public void checkTeamOut(Player p) {
        if (!teamSillHasPlayers(getTeamOfPlayer(p))) {
            SaveUtils.setPlayerPlace(p.getName(), allTeamsAlive.size());
            allTeamsAlive.remove(getTeamOfPlayer(p));
        }
    }

    @Override
    public void gameStart() {

        won = false;
        gameOver = false;
        loc.clear();
        lives.clear();
        allTeamsAlive.clear();
        autoFillPlayers();


        allTeamsAlive.clear();
        // TODO Auto-generated method stub
        for (EszeTeam et : allTeams) {
            if (teamSillHasPlayers(et)) {
                allTeamsAlive.add(et);
            }
        }
        won = false;
        gameOver = false;
        setupGame();

        scoreboard = new TeamsScoreboard();
        scoreboard.showScoreboard();
        for (Player p : players) {

            setupPlayer(p);


            openSpellSelection(p);

            if (!lives.containsKey(getTeamOfPlayer(p))) {
                lives.put(getTeamOfPlayer(p), 5);
            } else {
                lives.put(getTeamOfPlayer(p), lives.get(getTeamOfPlayer(p)) + 3);
            }
        }

        //Analytics
        ArrayList<ArrayList<String>> teams = new ArrayList<ArrayList<String>>();
        for (EszeTeam team : allTeams) {
            ArrayList<String> names = new ArrayList<>();
            for (Player p : team.players) {
                names.add(p.getName());
            }
            teams.add(names);
        }
        SaveUtils.startTeamGame(teams);

        WeaponMenu.deliverItems();

    }

    public void openSpellSelection(Player p) {
        PlayerUtils.hidePlayer(p, 200);
        loc.put(p, p.getLocation());
        TeamSpellMenu s = new TeamSpellMenu();
        s.open(p);
        new BukkitRunnable() {
            int t = 0;

            public void run() {
                t++;
                p.setNoDamageTicks(4);
                if (t > 10 * 10) {
                    p.setGameMode(GameMode.SURVIVAL);
                }
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    this.cancel();
                }
            }
        }.runTaskTimer(main.plugin, 2, 2);
    }

    @Override
    public void givePlayerLobbyItems(Player p) {
        if (p.getGameMode().equals(GameMode.SURVIVAL)) {
            p.getInventory().clear();
        }
        if (!p.getName().equals("adermake") || p.getGameMode() != GameMode.CREATIVE) {
            if (p.isOp()) {
                p.getInventory().setItem(0, ItemStackUtils.createItemStack(Material.COMMAND_BLOCK, 1, 0, "§3Modifikatoren", null, true));
            }

            p.getInventory().setItem(8, ItemStackUtils.createItemStack(Material.MAP, 1, 0, "§3Map wählen", null, true));
            p.getInventory().setItem(7, ItemStackUtils.createItemStack(Material.ENDER_CHEST, 1, 0, "§3Spellsammlung", null, true));
            p.getInventory().setItem(6, ItemStackUtils.createItemStack(Material.DIAMOND, 1, 0, "§3Georg", null, true));
            p.getInventory().setItem(5, ItemStackUtils.createItemStack(Material.NETHER_STAR, 1, 0, "§3Kosmetik", null, true));
            p.getInventory().setItem(4, ItemStackUtils.createItemStack(Material.NAME_TAG, 1, 0, "§3Teamauswahl", null, true));

            resendScorboardTeams(p);
        }
    }


    public EszeTeam getTeamOfPlayer(Player p) {
        for (EszeTeam t : allTeams) {
            if (t.players.contains(p)) {
                return t;
            }
        }
        return null;
    }


    public void resendScorboardTeams(Player p) {


        for (EszeTeam team : allTeams) {
            for (Player pl : team.players) {
                ScoreboardTeamUtils.colorPlayer(pl, p, team.color);
            }

        }
    }

    public void resetMode() {
        for (EszeTeam t : allTeams) {
            ArrayList<Player> pList = new ArrayList<Player>();
            for (Player p : t.players) {
                pList.add(p);
            }
            for (Player p : pList) {
                t.removePlayer(p);
            }
        }
    }

}
