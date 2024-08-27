package esze.main;

import esze.analytics.SaveUtils;
import esze.app.AppServer;
import esze.app.AppUserPasswordUtils;
import esze.enums.GameType;
import esze.enums.GameType.TypeEnum;
import esze.enums.Gamestate;
import esze.listeners.*;
import esze.map.JumpPad;
import esze.map.JumpPadHandler;
import esze.map.MapSelect;
import esze.menu.Menu;
import esze.neural.NeuralNetworks;
import esze.utils.*;
import esze.voice.Discord;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import spells.spellcore.*;
import weapons.BuffHandler;
import weapons.Damage;
import weapons.WeaponAbilitys;
import weapons.WeaponList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main extends JavaPlugin {

    public static main plugin;
    public static String discord_TOKEN = "";
    public static String mapname;

    //public static HashMap<Player, String> damageCause = new HashMap<Player, String>();
    public AppServer appServer;

    public static List<String> colorTags = new ArrayList<String>();

    @Override
    public void onEnable() {
        plugin = this;
        NoCollision.setUpCollsionStopper();
        colorTags = Arrays.asList("§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a", "§b", "§c", "§d", "§e", "§f");

        this.getServer().getPluginManager().registerEvents(new EventCollector(), this);
        Cooldowns.startCooldownHandler();

        ConfigurationSerialization.registerClass(JumpPad.class);
        this.getCommand("setmonumap").setExecutor(new CommandReceiver());
        this.getCommand("playrandomsound").setExecutor(new CommandReceiver());
        this.getCommand("showpads").setExecutor(new CommandReceiver());
        this.getCommand("loadpads").setExecutor(new CommandReceiver());
        this.getCommand("unload").setExecutor(new CommandReceiver());
        this.getCommand("spell").setExecutor(new CommandReceiver());
        this.getCommand("game").setExecutor(new CommandReceiver());
        this.getCommand("maps").setExecutor(new CommandReceiver());
        this.getCommand("setspawn").setExecutor(new CommandReceiver());
        this.getCommand("setpassword").setExecutor(new CommandReceiver());
        this.getCommand("setitem").setExecutor(new CommandReceiver());
        this.getCommand("setdiscordtoken").setExecutor(new CommandReceiver());
        this.getCommand("setlobby").setExecutor(new CommandReceiver());
        this.getCommand("downloadfile").setExecutor(new CommandReceiver());
        this.getCommand("ping").setExecutor(new CommandReceiver());
        this.getCommand("setmode").setExecutor(new CommandReceiver());
        this.getCommand("removemap").setExecutor(new CommandReceiver());
        this.getCommand("itemname").setExecutor(new CommandReceiver());
        this.getCommand("setjumppad").setExecutor(new CommandReceiver());
        this.getCommand("removepads").setExecutor(new CommandReceiver());
        this.getCommand("music").setExecutor(new CommandReceiver());

        this.getCommand("nofboost").setExecutor(new CommandReceiver());
        this.getCommand("testinv").setExecutor(new CommandReceiver());
        this.getCommand("analytics").setExecutor(new CommandReceiver());
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Move(), this);
        getServer().getPluginManager().registerEvents(new Death(), this);
        getServer().getPluginManager().registerEvents(new Damage(), this);
        getServer().getPluginManager().registerEvents(new Spawn(), this);
        getServer().getPluginManager().registerEvents(new Hunger(), this);
        getServer().getPluginManager().registerEvents(new Interact(), this);
        getServer().getPluginManager().registerEvents(new WeaponAbilitys(), this);
        getServer().getPluginManager().registerEvents(new FBoost(), this);
        getServer().getPluginManager().registerEvents(new CancelClick(), this);
        getServer().getPluginManager().registerEvents(new Block(), this);
        getServer().getPluginManager().registerEvents(new DropPickup(), this);
        getServer().getPluginManager().registerEvents(new JumpPadHandler(), this);
        getServer().getPluginManager().registerEvents(new Emerald(), this);
        getServer().getPluginManager().registerEvents(new MapSelect(), this);
        getServer().getPluginManager().registerEvents(new Menu(), this);
        getServer().getPluginManager().registerEvents(new Spelldrop(), this);
        getServer().getPluginManager().registerEvents(new TTTFusion(), this);
        getServer().getPluginManager().registerEvents(new TTTTrade(), this);
        getServer().getPluginManager().registerEvents(new Music(), this);
        getServer().getPluginManager().registerEvents(new Chat(), this);
        getServer().getPluginManager().registerEvents(new Reconnect(), this);
        getServer().getPluginManager().registerEvents(new Launch(), this);
        getServer().getPluginManager().registerEvents(new HandSlotChange(), this);

        TTTFusion.start();

        NeuralNetworks.loadNeuralNetworks();
        PlayerUtils.showAllPlayers();
        PlayerUtils.stopVelocity();
        JumpPadHandler.start();
        Gamestate.setGameState(Gamestate.LOBBY);

        // SpellList.sortSpells();
        WeaponList.setUpWeapons();
        BuffHandler.tickMethod();
        if (getConfig().contains("settings.mode")) {
            GameType.setTypeByName(getConfig().getString("settings.mode"));
        } else {
            GameType.setTypeByEnum(TypeEnum.SOLO);
        }

        LobbyBackgroundRunnable.start();

        Bukkit.getServer().setMotd(ChatUtils.centerMotD("§cEsze§3Remastered").substring(2) + "\n§8"
                + ChatUtils.centerMotD("Der Klassiker neu aufgelegt!").substring(3));

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setExp(0F);
            p.setLevel(0);
            p.setFoodLevel(20);
            p.setHealth(20);
            p.setMaxHealth(20);
            p.setWalkSpeed(0.2F);
            p.setGlowing(false);
            // Clears Inventory of Players
            if (p.getGameMode().equals(GameMode.SURVIVAL)) {
                p.getInventory().clear();
                p.teleport(new Location(Bukkit.getWorld("world"), 0, 105, 0));// teleport into Lobby
            }

            GameType.getType().givePlayerLobbyItems(p);
        }

        if (getConfig().contains("settings.dcToken")) {
            discord_TOKEN = getConfig().getString("settings.dcToken");
            new BukkitRunnable() {
                @Override
                public void run() {
                    Discord.run();
                }
            }.runTaskAsynchronously(main.plugin);
        } else {
            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(Player::isOp)
                    .forEach(p -> p.sendMessage("Der Discord Token wurde nicht in der Config gefunden! (/setdiscordtoken <TOKEN>)"));
        }

        //Analytics

        if (getConfig().contains("settings.sqlPass")) {
            SaveUtils.setPassword(getConfig().getString("settings.sqlPass"));
            SaveUtils.update();
            SaveUtils.checkConnection();
        } else {
            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(Player::isOp)
                    .forEach(p -> p.sendMessage("Kein SQL Password eingetragen! (/analytics setPassword <Password>"));
        }


        SpellList.registerSpells();


        AppUserPasswordUtils.createPasswordConfig();

        System.out.println("Esze | Fahre App-Server hoch.");
        appServer = new AppServer();
        appServer.startServer();
        System.out.println("Esze | App-Server hochgefahren.");
        BossbarSpellHUD.removeAllBossbars();
    }

    @Override
    public void onDisable() {
        // SaveUtils.backup();
        //PlayerConfig.save();

        for (Spell spell : Spell.spell) {
            spell.instaKill();

        }

        try {
            CorpseUtils.resetAllCorpses();
        } catch (Error e) {
            System.out.println("Esze | Fehler beim Löschen der Leichen");
        }
        for (Entity e : Bukkit.getWorld("world").getEntities()) {
            if (e.getType() != EntityType.PLAYER) {
                e.remove();
            }
        }

        System.out.println("Esze | Fahre App-Server herunter.");
        try {
            appServer.shutdownServer();
            System.out.println("Esze | App-Server heruntergefahren.");
        } catch (Exception e) {
            System.out.println("Esze | App-Server herunterfahren fehlgeschlagen.");
        }

        try {
            Discord.unMuteAll();
            Discord.logout();
            System.out.println("Esze | Discord heruntergefahren.");
        } catch (Error e) {
            System.out.println("Esze | Discord herunterfahren fehlgeschlagen.");
        }

    }

}
