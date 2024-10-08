package esze.main;

import esze.analytics.SaveUtils;
import esze.app.AppServer;
import esze.app.AppUserPasswordUtils;
import esze.configs.PlayerSettingsGuy;
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

import java.lang.reflect.Field;
import java.util.*;

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
        CommandReceiver commandReceiver = new CommandReceiver();
        this.getCommand("setmonumap").setExecutor(commandReceiver);
        this.getCommand("playrandomsound").setExecutor(commandReceiver);
        this.getCommand("showpads").setExecutor(commandReceiver);
        this.getCommand("loadpads").setExecutor(commandReceiver);
        this.getCommand("unload").setExecutor(commandReceiver);
        this.getCommand("spell").setExecutor(commandReceiver);
        this.getCommand("game").setExecutor(commandReceiver);
        this.getCommand("maps").setExecutor(commandReceiver);
        this.getCommand("setspawn").setExecutor(commandReceiver);
        this.getCommand("setpassword").setExecutor(commandReceiver);
        this.getCommand("setitem").setExecutor(commandReceiver);
        this.getCommand("setdiscordtoken").setExecutor(commandReceiver);
        this.getCommand("setlobby").setExecutor(commandReceiver);
        this.getCommand("downloadfile").setExecutor(commandReceiver);
        this.getCommand("ping").setExecutor(commandReceiver);
        this.getCommand("setmode").setExecutor(commandReceiver);
        this.getCommand("removemap").setExecutor(commandReceiver);
        this.getCommand("itemname").setExecutor(commandReceiver);
        this.getCommand("setjumppad").setExecutor(commandReceiver);
        this.getCommand("removepads").setExecutor(commandReceiver);
        this.getCommand("music").setExecutor(commandReceiver);

        this.getCommand("nofboost").setExecutor(commandReceiver);
        this.getCommand("testinv").setExecutor(commandReceiver);
        this.getCommand("analytics").setExecutor(commandReceiver);
        this.getCommand("disablepack").setExecutor(commandReceiver);
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
        getServer().getPluginManager().registerEvents(new ToggleSneak(), this);

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

        Bukkit.getServer().setMotd(ChatUtils.centerMotD("§cEsze§3Remastered §7| §a1.21+").substring(2) + "\n§8"
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
            PacketReceiver.addPacketReceiver(p);
            PlayerSettingsGuy.spawnPlayerSettingsGuy(p);
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
        PacketReceiver.removePacketReceivers();

        Spell.spell.forEach(Spell::instaKill);
        Bukkit.getOnlinePlayers().forEach(PlayerSettingsGuy::removePlayerSettingsGuy);

        try {
            CorpseUtils.resetAllCorpses();
        } catch (Error e) {
            System.out.println("Esze | Fehler beim Löschen der Leichen");
        }

        Bukkit.getWorld("world").getEntities()
                .stream()
                .filter(e -> e.getType() != EntityType.PLAYER)
                .forEach(Entity::remove);

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
        } catch (Error | Exception e) {
            System.out.println("Esze | Discord herunterfahren fehlgeschlagen.");
        }

    }

}
