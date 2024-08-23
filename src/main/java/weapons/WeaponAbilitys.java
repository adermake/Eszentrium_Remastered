package weapons;

import esze.analytics.SaveUtils;
import esze.enums.GameType;
import esze.main.main;
import esze.menu.GameModifier;
import esze.menu.ModifierMenu;
import esze.players.PlayerAPI;
import esze.types.TypeTEAMS;
import esze.utils.Actionbar;
import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.DamageCauseContainer;
import spells.spellcore.Spell;

import java.util.ArrayList;
import java.util.HashMap;

public class WeaponAbilitys implements Listener {


    public static ArrayList<Player> cd = new ArrayList<Player>();
    public static ArrayList<Player> cd2 = new ArrayList<Player>();
    public static HashMap<Player, Integer> charge1 = new HashMap<Player, Integer>();
    public static HashMap<Player, Integer> charge2 = new HashMap<Player, Integer>();
    public static HashMap<Player, String> lastLaunched = new HashMap<Player, String>();
    public static HashMap<Player, Vector> lastMovedDir = new HashMap<Player, Vector>();
    public static HashMap<Player, ArrayList<Player>> pList = new HashMap<Player, ArrayList<Player>>();


    @EventHandler
    public void onWeaponUse(PlayerDropItemEvent e) {
        ItemStack is = e.getItemDrop().getItemStack();
        Player p = e.getPlayer();

        if (NBTUtils.getNBT("Weapon", is).equals("true")) {


            if (cd2.contains(p)) {
                e.setCancelled(true);
                return;
            }

            cd2.add(p);
            new BukkitRunnable() {
                int t = 0;

                public void run() {
                    t = t + 1;
                    if (t > 100) {
                        cd2.remove(p);
                        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, (float) 0.9);
                        this.cancel();
                    }

                }
            }.runTaskTimerAsynchronously(main.plugin, 0, 0);
            // BLOCK SYSTEM
            int slot = e.getPlayer().getInventory().getHeldItemSlot();

            if (e.getPlayer().getInventory().getItem(slot) == null) {

                ItemStack ph = new ItemStack(Material.STONE_BUTTON);


                ItemMeta phim = ph.getItemMeta();
                //ph.setItemMeta(phim);
                phim.setDisplayName("§cWarte auf Schwert");
                ph.setItemMeta(phim);


                //Bukkit.broadcastMessage("ITEM");

                //Bukkit.broadcastMessage(""+e.getItemDrop().getItemStack());
                final ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
                as.getEquipment().setItemInMainHand(e.getItemDrop().getItemStack().clone());
                p.getInventory().setItemInMainHand(null);
                as.setVisible(false);
                as.setArms(true);
                as.setMarker(true);
                e.getItemDrop().remove();
                as.setRightArmPose(as.getRightArmPose().add(0, 0, 80));
                //e.getItemDrop().setItemStack(null);
                new BukkitRunnable() {
                    double yaa = 0;

                    double t = 0;
                    Location loc = p.getLocation();
                    boolean hasdropped = false;
                    int toggle = 0;

                    public void run() {
                        e.getPlayer().getInventory().setItem(slot, ph);
                        t = t + 1;
                        toggle++;
                        Vector direction = loc.getDirection().normalize();
                        double x = direction.getX() * t;
                        double y = direction.getY() * t + 1.5;
                        double z = direction.getZ() * t;
                        loc.add(x, y, z);

                        as.teleport(loc);
                        //ParticleEffect.SWEEP_ATTACK.send(Bukkit.getOnlinePlayers(), loc.getX(), loc.getY(), loc.getZ(), 0.1, 0, 0.1, 0, 1);
                        yaa = yaa + 1;
                        as.setRightArmPose(as.getRightArmPose().add(-0.7, 0, 0));
                        if (loc.getBlock().getType() != Material.WATER) {
                            if (loc.getBlock().getType() != Material.AIR) {
                                //ParticleEffect.EXPLOSION_LARGE.send(Bukkit.getOnlinePlayers(), loc.getX(),loc.getY(), loc.getZ(), 0, 0, 0, 0, 1);

                                p.getInventory().setItem(slot, new ItemStack(as.getEquipment().getItemInMainHand()));
                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                    pl.playSound(as.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, 2);
                                }

                                as.remove();

                                this.cancel();
                                return;
                            }
                        }

                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            if (pl instanceof Player && ((Player) pl).getGameMode() == GameMode.SURVIVAL) {

                                if (p == pl || isOnTeam(p, pl)) {
                                    continue;
                                }
                                Location ploc1 = pl.getLocation();
                                Location ploc2 = pl.getLocation();
                                ploc2.add(0, 1, 0);
                                if (ploc1.distance(loc) <= 1 || ploc2.distance(loc) <= 1) {
                                    if (Spell.damageCause.get((Player) pl) == null) {
                                        Spell.damageCause.put(pl, new DamageCauseContainer(p));
                                    }
                                    Spell.damageCause.get((Player) pl).swordDamage(p, "Schwertwurf"); //Damage Cause
                                    //PlayerAPI.getPlayerInfo((Player)pl).damage(p, (int)getAttackDamage(as.getEquipment().getItemInMainHand()), "§3Schwertwurf");
                                    //p.getInventory().clear(slot);
                                    p.getInventory().setItem(slot, new ItemStack(as.getEquipment().getItemInMainHand()));
                                    //p.getInventory().addItem(new ItemStack(as.getEquipment().getItemInMainHand()));
                                    for (Player pl2 : Bukkit.getOnlinePlayers()) {
                                        pl2.playSound(as.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 2, 2);
                                    }
                                    pl.damage(4);
                                    as.remove();
                                    this.cancel();
                                    return;
                                }
                            }

                        }

                        loc.subtract(x, y, z);

                        if (t > 30) {
                            if (!PlayerAPI.getPlayerInfo((Player) p).isAlive) {

                            } else {
                                p.getInventory().setItem(slot, new ItemStack(as.getEquipment().getItemInMainHand()));
                                //p.getInventory().addItem(new ItemStack(as.getEquipment().getItemInMainHand()));
                            }
                            as.remove();
                            this.cancel();
                            return;
                        }
                    }
                }.runTaskTimer(main.plugin, 0, 0);
            } else {
                e.setCancelled(true);
            }
        }

    }

    public boolean isOnTeam(Player c, Player p) {


        if (GameType.getType() instanceof TypeTEAMS) {
            TypeTEAMS teams = (TypeTEAMS) GameType.getType();

            if (teams.getTeammates(p) == null || teams.getTeammates(p).size() <= 0) {
                return false;
            }
            if (teams.getTeammates(p).contains(c)) {
                return true;
            }


        }


        return false;

    }

    @EventHandler
    public void onWeaponUse(PlayerInteractEvent e) {
        final Player p = e.getPlayer();

        EquipmentSlot hand = e.getHand();
        if (hand != null && !hand.equals(EquipmentSlot.HAND))
            return;

        //SPHERE
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (NBTUtils.getNBT("Weapon", e.getPlayer().getInventory().getItemInMainHand()) == "true") {
                if (true) {
                    if (lastLaunched.containsKey(p)) {
                        if (cd.contains(p))
                            return;

                        ParUtils.createParticle(Particle.WITCH, p.getLocation(), 0.2, 0.2, 0.2, 5, 0.1F);
                        try {
                            String name = lastLaunched.get(p);
                            Class clazz = Class.forName(name);
                            Spell sp = (Spell) clazz.newInstance();
                            sp.spellkey = -1;
                            if (!sp.castSpell(p, sp.getName())) {
                                SaveUtils.addSpellUse(p.getName(), sp.getName(), false);
                                cd.add(p);
                                sp.spellkey = -1;
                                new BukkitRunnable() {
                                    int sec = 0;
                                    int cooldown = sp.getSpellDescription().getCooldown() * 2;

                                    @Override
                                    public void run() {
                                        if (ModifierMenu.hasModifier(GameModifier.SCHNELLFEUER)) {
                                            sec += 5;
                                        } else {
                                            sec++;
                                        }

                                        new Actionbar("§bZauberecho: §e" + (cooldown / 20 - sec) + " Sekunden").send(p);
                                        if (sec * 20 >= cooldown || !WeaponMenu.running) {
                                            cd.remove(p);
                                            new Actionbar("").send(p);
                                            this.cancel();
                                        }

                                        // TODO Auto-generated method stub

                                    }
                                }.runTaskTimer(main.plugin, 0, 20);

                            }


                        } catch (Exception ex) {
                            //ex.printStackTrace(System.out);
                            p.sendMessage("Spell is not vaild!");
                        }

                    }
                }

            }

        }
    }


    public void doPull(Entity e, Location toLocation, double speed) {
        // multiply default 0.25

        e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(speed));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        //lastMovedDir.put(e.getPlayer(), e.getTo().toVector().subtract(e.getFrom().toVector()));

    }

    public static void clearLists() {
        pList.clear();
        lastLaunched.clear();
        cd.clear();
        charge1.clear();
        charge2.clear();
    }

}
