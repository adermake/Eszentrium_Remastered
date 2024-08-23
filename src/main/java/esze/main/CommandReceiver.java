package esze.main;

import esze.analytics.SaveUtils;
import esze.app.AppUserPasswordUtils;
import esze.enums.GameType;
import esze.enums.GameType.TypeEnum;
import esze.enums.Gamestate;
import esze.listeners.FBoost;
import esze.map.JumpPad;
import esze.map.JumpPad.JumpPadType;
import esze.map.JumpPadHandler;
import esze.map.MapMenu;
import esze.menu.ColorTagSpellSelectionMenu;
import esze.menu.SoloAnalyticsMenu;
import esze.menu.SoloSelectionTopMenu;
import esze.menu.WeaponsAnalyticsMenu;
import esze.utils.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandReceiver implements CommandExecutor, TabCompleter {

    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String cmdlabel, @NotNull String[] args) {
        final Player p = (Player) sender;


        if (cmd.getName().equals("game")) {
            if (p.isOp()) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("start")) {
                        LobbyCountdownRunnable.start();
                    } else if (args[0].equalsIgnoreCase("stop")) {
                        GameType.getType().endGame();
                        new Title("§e" + p.getName(), "§ehat das Spiel abgebrochen!").sendAll();
                    } else {
                        p.sendMessage("§8| §c/game <start/stop>");
                    }
                } else {
                    p.sendMessage("§8| §c/game <start/stop>");
                }
            }
        }

        if (cmd.getName().startsWith("music")) {
            Music.toogleMusic(p);
        }

        if (cmd.getName().startsWith("maps")) {
            if (p.isOp()) {
                MapMenu.sendOverview(p);
            }
        }

        if (cmd.getName().startsWith("setpassword")) {
            if (args.length == 1) {
                AppUserPasswordUtils.changeUserPassword(p.getUniqueId().toString(), args[0]);
                p.sendMessage("§8| §aApp-Passwort geändert!");
            } else {
                p.sendMessage("§8| §c/setpassword <Neues Passwort>");
            }
        }

        if (cmd.getName().startsWith("setdiscordtoken")) {
            if (p.isOp()) {
                if (args.length == 1) {
                    main.plugin.getConfig().set("settings.dcToken", args[0]);
                    main.plugin.saveConfig();
                    p.sendMessage("§8| §aDiscord-Token geändert! Der Server muss reloaded werden, damit die Änderungen in Kraft treten.");
                } else {
                    p.sendMessage("§8| §c/setdiscordtoken <Token>");
                }
            }
        }

        if (cmd.getName().startsWith("setjumppad")) {
            if (p.isOp()) {
                if (args.length == 4) {
                    try {
                        String name = args[0];
                        int i = Integer.parseInt(args[1]);
                        double power = Double.parseDouble(args[3]);
                        if (args[2].equals("up")) {
                            JumpPad jp = new JumpPad(p.getLocation(), power, JumpPadType.UP);
                            Bukkit.broadcastMessage("S" + name + " " + i);
                            main.plugin.getConfig().set("jumppads." + name + "." + i, jp);
                            main.plugin.saveConfig();

                            JumpPadHandler.jumpPads.add(jp);

                        }
                        if (args[2].equals("dir")) {
                            JumpPad jp = new JumpPad(p.getLocation(), power, JumpPadType.DIRECTIONAL);
                            main.plugin.getConfig().set("jumppads." + name + "." + i, jp);
                            main.plugin.saveConfig();
                            JumpPadHandler.jumpPads.add(jp);
                        }

                        p.sendMessage("§8| §7Das Jumppad §6" + i + " §7für Map §6" + name + " §7wurde gesetzt!");
                    } catch (Exception e) {
                        p.sendMessage("§8| §cEin Fehler ist aufgetreten. Vielleicht ist deine Padnummer keine Zahl? " + e);
                    }
                } else {
                    p.sendMessage("§8| §c/setjumppad <Map> <Spawnnummer> <Type> <Power>");
                }
            }
        }
        if (cmd.getName().startsWith("setspawn")) {
            if (p.isOp()) {
                if (args.length == 2) {
                    try {
                        String name = args[0];
                        int i = Integer.parseInt(args[1]);
                        main.plugin.getConfig().set("maps." + name + "." + i, p.getLocation());
                        main.plugin.saveConfig();
                        p.sendMessage("§8| §7Der Spawn §6" + i + " §7für Map §6" + name + " §7wurde gesetzt!");
                    } catch (Exception e) {
                        p.sendMessage("§8| §cEin Fehler ist aufgetreten. Vielleicht ist deine Spawnnummer keine Zahl?");
                    }
                } else {
                    p.sendMessage("§8| §c/setspawn <Map> <Spawnnummer>");
                }
            }
        }

        //TODO REWRITE
        if (cmd.getName().startsWith("setitem")) {
            if (p.isOp()) {
                if (args.length == 2 || args.length == 3) {
                    String name = args[0];
                    String mat = args[1];
                    short dur = 0;
                    try {
                        dur = Short.parseShort(args[2]);
                    } catch (Exception e) {
                    }
                    main.plugin.getConfig().set("maps." + name + ".material", Material.getMaterial(mat).toString());
                    main.plugin.getConfig().set("maps." + name + ".durability", dur);
                    main.plugin.saveConfig();
                    p.sendMessage("§8| §7Das Item für Map §6" + name + " §7ist jetzt §3" + Material.getMaterial(mat).toString() + "§7(" + dur + ")!");

                } else {
                    p.sendMessage("§8| §c/setitem <Map> <Material (NICHT ID)> [Haltbarkeit]");
                }
            }
        }

        if (cmd.getName().startsWith("setlobby")) {
            if (p.isOp()) {
                main.plugin.getConfig().set("lobby.loc", p.getLocation());
                main.plugin.saveConfig();
                p.sendMessage("§8| §7Die Lobbyposition wurde gespeichert!");
            }
        }

        if (cmd.getName().startsWith("downloadfile")) {
            if (p.isOp()) {
                if (args.length == 2) {
                    String filename = args[0];
                    String urlstring = args[1];
                    Bukkit.getScheduler().runTaskAsynchronously(main.plugin, new Runnable() {

                        @Override
                        public void run() {
                            File f = new File(main.plugin.getDataFolder().getAbsolutePath() + "/downloads/");
                            if (!f.exists() || !f.isDirectory()) {
                                f.mkdirs();
                            }
                            try {
                                saveFile(new URL(urlstring), main.plugin.getDataFolder().getAbsolutePath() + "/downloads/" + filename, p);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        }

        if (cmd.getName().startsWith("removepads")) {
            if (args.length == 1) {
                String map = args[0];
                int padNumber = 0;
                while (main.plugin.getConfig().contains("jumppads." + map + "." + padNumber)) {
                    padNumber++;
                    main.plugin.getConfig().set("jumppads." + map + "." + padNumber, null);
                }
            } else {
                p.sendMessage("§8| §c/removepads <Map> ");
            }
        }

        if (cmd.getName().startsWith("ping")) {
            if (args.length == 1) {
                String name = args[0];
                if (Bukkit.getOfflinePlayer(name).isOnline()) {
                    Player stats = Bukkit.getPlayer(name);
                    p.sendMessage("§8| §7" + name + "'s Ping: §6" + getPing(stats));
                } else {
                    p.sendMessage("§8| §cSpieler nicht online!");
                }
            } else if (args.length == 0) {
                p.sendMessage("§8| §7Dein Ping: §6" + getPing(p));
            } else {
                p.sendMessage("§8| §cZu viele Parameter! /ping <Name>");
            }
        }

        if (cmd.getName().startsWith("unload")) {
            if (p.isOp()) {
                JumpPadHandler.jumpPads.clear();
                p.sendMessage("§8| §ePriuuuusch");
            }
        }

        if (cmd.getName().startsWith("nofboost")) {
            if (p.isOp()) {
                FBoost.noFboost.add(p);
                p.sendMessage("§8| §eFBoost disabled");
            }
        }
        if (cmd.getName().startsWith("loadpad")) {
            if (p.isOp()) {
                JumpPadHandler.loadJumpPads(args[0]);
                p.sendMessage("§8| §ePriuuuuschda");
            }
        }
        if (cmd.getName().startsWith("showpads")) {
            if (p.isOp()) {
                int number = 1;
                while (main.plugin.getConfig().contains("jumppads." + args[0] + "." + number)) {


                    JumpPad jp = (JumpPad) main.plugin.getConfig().get("jumppads." + args[0] + "." + number);
                    Slime s = (Slime) p.getWorld().spawnEntity(jp.loc, EntityType.SLIME);
                    s.setCustomName("" + number);
                    s.setCustomNameVisible(true);
                    s.setCollidable(false);
                    s.setSize(1);
                    s.setAI(false);
                    s.setGlowing(true);

                    new BukkitRunnable() {
                        public void run() {
                            s.remove();
                        }
                    }.runTaskLater(main.plugin, 20 * 10);
                    number++;
                }

                p.sendMessage("§8| " + (number - 1) + " revealed");
            }
        }
        if (cmd.getName().startsWith("setmode")) {
            if (p.isOp()) {
                if (Gamestate.getGameState() == Gamestate.LOBBY) {
                    if (args.length == 1) {
                        String name = args[0];
                        GameType.getType().resetMode();
                        if (!GameType.setTypeByName(name)) {
                            p.sendMessage("§8| §cUngültiger Modus!");
                            return false;
                        }
                        SpellList.registerSpells();
                        p.sendMessage("§8| §7Modus geändert!");
                    } else {
                        p.sendMessage("§8| §c/setmode <Modus>");
                    }
                } else {
                    p.sendMessage("§8| §cSpielmodus nicht während dem Spiel änderbar!");
                }
            }
        }

        if (cmd.getName().startsWith("setmonumap")) {
            if (p.isOp()) {
                p.sendMessage("§eMonument Settings Changed!");
                String name = args[0];
                if (args.length > 1 && args[1] == "false") {
                    main.plugin.getConfig().set("maps." + name + ".monuments", false);
                    main.plugin.saveConfig();
                }
                if (args.length > 1 && args[1] == "true") {
                    main.plugin.getConfig().set("maps." + name + ".monuments", true);
                    main.plugin.saveConfig();
                } else {
                    main.plugin.getConfig().set("maps." + name + ".monuments", true);
                    main.plugin.saveConfig();
                }

            }
        }
        if (cmd.getName().startsWith("removemap")) {
            if (p.isOp()) {
                if (args.length == 1) {
                    String name = args[0];
                    main.plugin.getConfig().set("maps." + name, null);
                    main.plugin.saveConfig();
                    MapMenu.sendOverview(p);
                    p.sendMessage("§8| §7Map entfernt!");
                } else {
                    p.sendMessage("§8| §c/removemap <Map>");
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase("itemname")) {
            if (p.isOp()) {
                ItemMeta im = p.getInventory().getItemInMainHand().getItemMeta();
                im.setDisplayName("§2" + im.getDisplayName().substring(2, im.getDisplayName().length()));
                p.getInventory().getItemInMainHand().setItemMeta(im);
                p.sendMessage("§8| §7Der Name wurde geändert!");
            }
        }

        if (cmd.getName().startsWith("playrandomsound")) {
            ArrayList<Sound> sound = new ArrayList<Sound>();
            for (Sound s : Sound.values()) {
                sound.add(s);
            }
            Sound s = sound.get(MathUtils.randInt(0, sound.size() - 1));
            SoundUtils.playSound(s, p.getLocation(), 1, 1);
            p.sendMessage("" + s.name());
        }
        if (cmd.getName().equalsIgnoreCase("analytics")) {
            if (args.length < 1) {
                p.sendMessage("ERROR");
                return false;
            }
            switch (args[0]) {
                case "wins":
                    if (args.length < 2) {
                        p.sendMessage("§7Du hast " + SaveUtils.getAnalytics().getPlayerVictories(p.getName()) + "§7 Runden gewonnen!");
                    } else {
                        p.sendMessage("§7" + args[1] + " hat " + SaveUtils.getAnalytics().getPlayerVictories(args[1]) + "§7 Runden gewonnen!");
                    }

                    return true;
                case "losses":
                    if (args.length < 2) {
                        p.sendMessage("§7Du hast " + SaveUtils.getAnalytics().getPlayerLosses(p.getName()) + "§7 Runden verloren!");
                    } else {
                        p.sendMessage("§7" + args[1] + " hat " + SaveUtils.getAnalytics().getPlayerLosses(args[1]) + "§7 Runden verloren!");
                    }
                    return true;
                case "worth":
                    if (args.length >= 2) {
                        String assembly = "";
                        for (int i = 1; i < args.length; i++) {
                            assembly += args[i] + " ";
                        }
                        assembly = assembly.substring(0, assembly.length() - 1);
                        p.sendMessage("§7The Spell " + assembly + "§7 has a worth of " + SaveUtils.getAnalytics().getWorth(assembly) + "§7%!");
                        return true;
                    }
                    return false;
                case "stats":
                    if (args.length >= 2) {
                        new SoloAnalyticsMenu(args[1]).open(p);
                    } else {
                        new SoloAnalyticsMenu(p).open(p);
                    }
                    return true;
                case "update":
                    SaveUtils.update();
                    ;
                    p.sendMessage("Updating... !");
                    return true;
                case "spellmenu":
                    if (args.length >= 2) {
                        new ColorTagSpellSelectionMenu(args[1]).open(p);
                        return true;
                    } else {
                        new ColorTagSpellSelectionMenu(p.getName()).open(p);
                        ;
                        return true;
                    }
                case "weaponmenu":
                    if (args.length >= 2) {
                        new WeaponsAnalyticsMenu(args[1]).open(p);
                        return true;
                    } else {
                        new WeaponsAnalyticsMenu(p.getName()).open(p);
                        ;
                        return true;
                    }
                case "topmenu":
                    if (args.length >= 2) {
                        new SoloSelectionTopMenu(args[1]).open(p);
                        return true;
                    } else {
                        new SoloSelectionTopMenu(p.getName()).open(p);
                        ;
                        return true;
                    }
                case "sk":
                    if (args.length >= 2) {
                        String assembly = "";
                        for (int i = 1; i < args.length; i++) {
                            assembly += args[i] + " ";
                        }
                        assembly = assembly.substring(0, assembly.length() - 1);
                        p.sendMessage("§7The Spell " + assembly + "§7 has a kills of " + SaveUtils.getAnalytics().getSpellKills(p.getName(), assembly) + "§7!");
                        return true;
                    }
                    return false;
                case "sd":
                    if (args.length >= 2) {
                        String assembly = "";
                        for (int i = 1; i < args.length; i++) {
                            assembly += args[i] + " ";
                        }
                        assembly = assembly.substring(0, assembly.length() - 1);
                        p.sendMessage("§7The Spell " + assembly + "§7 has a deaths of " + SaveUtils.getAnalytics().getSpellDeaths(p.getName(), assembly) + "§7%");
                        return true;
                    }
                    return false;

                case "setPassword":
                    main.plugin.getConfig().set("settings.sqlPass", args[1]);
                    SaveUtils.setPassword(args[1]);
                    main.plugin.saveConfig();
                    p.sendMessage("§7Password set!");
                    return true;
                default:
                    p.sendMessage("ERROR");
                    return false;
            }
        }
        if (cmd.getName().equals("spell")) {
            if (args.length == 0) {
                return false;
            }


            ItemStack is = new ItemStack(Material.BOOK);
            ItemMeta im = is.getItemMeta();

            String name = args[0];
            String spellname = args[0];
            for (String partName : args) {
                if (partName == name)
                    continue;
                name = name + " " + partName;
                spellname += partName;
            }
            //name = name.replace("&", "§");

            boolean refined = false;
            if (name.contains("+")) {
                name = "§2" + name;
                refined = true;
            } else {
                name = "§e" + name;
            }
            name = name.replace("+", "");
            spellname = spellname.replace("+", "");
            try {
                spellname = spellname.substring(0, spellname.length());
                spellname = spellname.replace(" ", "");
                spellname = "spells.spells." + spellname;
                // Bukkit.broadcastMessage("F" + s);
                Class clazz = Class.forName(spellname);
                Spell sp = (Spell) clazz.newInstance();
                if (refined) {
                    im.setLore(sp.getSpellDescription().getRefinedLore());
                } else {
                    im.setLore(sp.getSpellDescription().getNormalLore());
                }

                sp.kill();

            } catch (Exception e) {
                e.printStackTrace();
            }

            im.setDisplayName(name);
            is.setItemMeta(im);

            is = NBTUtils.setNBT("Spell", "true", is);
            is = NBTUtils.setNBT("OriginalName", is.getItemMeta().getDisplayName(), is);
            is = NBTUtils.setNBT("SpellKey", "" + SpellKeyUtils.getNextSpellKey(), is);
            p.getInventory().addItem(is);
            return true;
        }

        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender player, Command cmd, String cmdname, String[] args) {
        List<String> to = new ArrayList<>();
        if (args.length == 1) {
            if (cmdname.contains("game")) {
                to.addAll(List.of("start", "stop"));
            } else if (cmdname.contains("setjumppad")) {
                for (String arena : main.plugin.getConfig().getConfigurationSection("maps").getKeys(false)) {
                    if (main.plugin.getConfig().get("maps." + arena) != null) {
                        to.add(arena);
                    }
                }
            } else if (cmdname.contains("ping")) {
                to.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
            } else if (cmdname.contains("setmode")) {
                to.addAll(Arrays.stream(TypeEnum.values()).map(TypeEnum::toString).toList());
            } else if (cmdname.contains("spell")) {
                to.addAll(SpellList.spells.keySet().stream().map(s -> ChatColor.stripColor(s.getName())).distinct().toList());
            } else if (cmdname.contains("setitem")) {
                for (String arena : main.plugin.getConfig().getConfigurationSection("maps").getKeys(false)) {
                    if (main.plugin.getConfig().get("maps." + arena) != null) {
                        to.add(arena);
                    }
                }
            }
        } else if (args.length == 2) {
            if (cmdname.contains("setitem")) {
                to.addAll(Arrays.stream(Material.values()).map(Material::toString).toList());
            }
        } else if (args.length == 3) {
            if (cmdname.contains("setjumppad")) {
                to.addAll(List.of("dir", "up"));
            }
        }
        return to;
    }

    public static void saveFile(URL url, String file, Player p) throws IOException {
        p.sendMessage("§8| §7Die Verbindung zu §6" + url.toString() + " §7wird aufgebaut.");
        HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
        long completeFileSize = httpConnection.getContentLength();

        InputStream in = url.openStream();
        FileOutputStream fos = new FileOutputStream(file);

        p.sendMessage("§8| §7Starte Download.");
        int length;
        long downloadedFileSize = 0;
        byte[] buffer = new byte[1024];// buffer for portion of data from
        // connection
        double lastprog = 0;
        while ((length = in.read(buffer)) > -1) {
            downloadedFileSize += length;
            double currentProgress = (((double) downloadedFileSize) / ((double) completeFileSize)) * 100d;
            currentProgress = round(currentProgress, 1);
            if (currentProgress != lastprog) {
                p.sendMessage("§8| §7Lade Datei herunter... " + currentProgress + "% abgeschlossen");
                lastprog = currentProgress;
            }
            fos.write(buffer, 0, length);
        }


        fos.close();
        in.close();
        p.sendMessage("§8| §7Datei heruntergeladen.");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public int getPing(Player p) {
        return p.getPing();
    }


}
