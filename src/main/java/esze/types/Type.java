package esze.types;

import esze.main.main;
import esze.map.JumpPadHandler;
import esze.menu.GameModifier;
import esze.menu.ModifierMenu;
import esze.menu.SoloSpellMenu;
import esze.scoreboards.Scoreboard;
import esze.utils.ItemStackUtils;
import esze.utils.Music;
import esze.utils.PlayerUtils;
import io.netty.util.internal.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.DamageCauseContainer;
import spells.spellcore.Spell;
import weapons.Damage;

import java.util.ArrayList;


public abstract class Type {

    public String name;
    public String currentmap;
    public ArrayList<Player> startplayers = new ArrayList<>();
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Player> spectator = new ArrayList<>();
    public Scoreboard scoreboard;
    public int spawnloc = 1;

    public abstract void runEverySecond();

    public abstract void runEveryTick();

    public void death(PlayerDeathEvent event) {
        Player p = event.getEntity();

        // CHECK IF DEATH WAS VALID IF NOT RESET HEALTH TO LAST DAMAGE TAKEN
        if (deathCheck(p)) {
            p.setHealth(Damage.lastHealthTaken.get(p));
            return;
        }

        // CHECK IF ENTITY HAS A DAMAGE CAUSE IF NOT FILL IT WITH UNKNOWN
        if (Spell.damageCause.get(p) == null) {
            Spell.damageCause.remove(p);
            Spell.damageCause.put(p, new DamageCauseContainer(null));
        }

        // DEATH MESSAGE
        if (!spectator.contains(p)) {
            String out = DamageCauseContainer.toMessage(Spell.damageCause.get(p), p.getName());
            for (Player rec : Bukkit.getOnlinePlayers()) {
                rec.sendMessage(out);
            }

        }

        Spell.damageCause.put(p, null);
        p.setVelocity(new Vector(0, 0, 0));

    }

    public abstract void gameStart();

    public Location nextLoc() {
        if (main.plugin.getConfig().contains("maps." + currentmap + "." + spawnloc)) {
            Location loc = (Location) main.plugin.getConfig().get("maps." + currentmap + "." + spawnloc);
            spawnloc++;
            return loc;
        } else {
            spawnloc = 1;
            return nextLoc();
        }
    }

    public Location getLoc(int id) {
        if (main.plugin.getConfig().contains("maps." + currentmap + "." + id)) {
            Location loc = (Location) main.plugin.getConfig().get("maps." + currentmap + "." + id);
            return loc.clone();
        }

        return null;
    }

    public void setupJumpPad(String map) {
        JumpPadHandler.loadJumpPads(map);
        //setup();
    }

    public void setup() {
        for (int i = randInt(0, 10); i > 0; i++) {
            nextLoc();
        }

    }


    public static int randInt(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    public void out(Player p, boolean showLightning) {
        if(showLightning) p.getWorld().strikeLightningEffect(p.getLocation());
        p.getInventory().clear();
        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);
        p.setFlying(true);
        if (p.getLocation().getY() < 60) {
            p.teleport(nextLoc());
        }
        PlayerUtils.hidePlayer(p);
        p.setVelocity(p.getVelocity().multiply(0));
        p.setHealth(p.getMaxHealth());
        players.remove(p);
        spectator.add(p);
    }


    public boolean deathCheck(Player p) {
        if (p.getLocation().getY() <= 60) {
            return false;
        }
        if (p.getKiller() != null) {
            if (!(p.getKiller() instanceof Player)) {
                return true;
            }
        }
        if (p.getLastDamageCause() != null && p.getLastDamageCause().getCause() != null && p.getLastDamageCause().getCause() == DamageCause.FLY_INTO_WALL) {
            return true;
        }


        return false;
    }


    public void killInVoidCheck() {
        ArrayList<Player> kill = new ArrayList<>();
        for (Player p : players) {
            if (p.getLocation().getY() < 60 && p.getGameMode() == GameMode.SURVIVAL) {

                if (Spell.damageCause.get(p) == null) {
                    Spell.damageCause.put(p, new DamageCauseContainer(null, null));
                }
                Spell.damageCause.get(p).voidDamage();
                kill.add(p);
            }
        }

        for (Player p : kill) {
            p.damage(p.getHealth());
        }
    }


    public void setupPlayer(Player p) {
        Spell.damageCause.remove(p);
        Spell.damageCause.put(p, null); //Reset damage Cause
        p.teleport(nextLoc());
        p.setGameMode(GameMode.ADVENTURE);
        p.getInventory().clear();
        //p.getInventory().addItem(ItemStackUtils.attackSpeedify(ItemStackUtils.createItemStack(Material.WOODEN_SWORD, 1, 0, "§cSchwert", null, true)));

        if (ModifierMenu.hasModifier(GameModifier.GESCHWINDIGKEIT)) {
            p.setWalkSpeed(0.6F);
            p.setFlySpeed(0.3F);
        }


    }

    public void openSpellSelection(Player p) {
        PlayerUtils.hidePlayer(p, 200);
        SoloSpellMenu s = new SoloSpellMenu();
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
	
    public void setupGame() {

        /*for (int i = 0; i < 16; i++) {
            Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(nextLoc()));
        }*/
        setupJumpPad(currentmap);
        Music.startRandomMusic();
        spectator.clear();
    }

    public abstract void endGame();

    public void givePlayerLobbyItems(Player p) {
        if (!p.getName().equals("adermake") || p.getGameMode() != GameMode.CREATIVE) {
            p.getInventory().clear();
            if (p.isOp()) {
                p.getInventory().setItem(0, ItemStackUtils.createItemStack(Material.COMMAND_BLOCK, 1, 0, "§3Modifikatoren", null, true));
            }
            p.getInventory().setItem(8, ItemStackUtils.createItemStack(Material.MAP, 1, 0, "§3Map wählen", null, true));
            p.getInventory().setItem(7, ItemStackUtils.createItemStack(Material.ENDER_CHEST, 1, 0, "§3Spellsammlung", null, true));
            p.getInventory().setItem(6, ItemStackUtils.createItemStack(Material.DIAMOND, 1, 0, "§3Georg", null, true));
        }
    }

    public void resetMode() {
    }

}
