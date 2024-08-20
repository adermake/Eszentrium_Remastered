package esze.listeners;

import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeTEAMS;
import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import esze.utils.ScoreboardTeamUtils;
import esze.utils.SoundUtils;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DropPickup implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if (!NBTUtils.getNBT("Weapon", e.getItemDrop().getItemStack()).equals("true")) {
            e.setCancelled(true);
        }

        if (Gamestate.getGameState() == Gamestate.INGAME && GameType.getType() instanceof TypeTEAMS) {
            if (!NBTUtils.getNBT("Spell", e.getItemDrop().getItemStack().clone()).equals("true")) {
                return;
            }
            throwBook(p, e.getItemDrop().getItemStack().clone());
            e.getItemDrop().remove();
            p.getInventory().setItemInMainHand(null);
            e.setCancelled(false);
        }
        if (Gamestate.getGameState() == Gamestate.LOBBY && p.getLocation().distance(new Location(p.getWorld(), -2000, 150, -2000)) < 500) {
            if (p.getInventory().getItemInMainHand().getAmount() > 1) {
                p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount());
            } else {
                p.getInventory().setItemInMainHand(null);
            }
            throwItem(p, e.getItemDrop().getItemStack().clone());
            e.getItemDrop().remove();
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent e) {
        e.setCancelled(true);
    }

    /// TEAMS TOSS
    public void throwItem(Player p, ItemStack is) {
        Item item = p.getWorld().dropItem(p.getEyeLocation(), is);
        item.setPickupDelay(100000);
        ScoreboardTeamUtils.colorEntity(item, ChatColor.GOLD);
        SoundUtils.playSound(Sound.ENTITY_SNOWBALL_THROW, p.getLocation(), 0.2F, 1);
        item.setGlowing(true);


        new BukkitRunnable() {
            Vector dir = p.getLocation().getDirection();
            int i = 0;
            final double speedMult = 3;

            public void run() {

                i++;

                SoundUtils.playSound(Sound.ENTITY_ILLUSIONER_CAST_SPELL, item.getLocation(), 2, 1);
                Player target = null;
                double dist = 100000;
                for (Player pl : Bukkit.getOnlinePlayers()) {

                    if (pl != p && pl.getLocation().distance(item.getLocation()) < dist) {
                        dist = pl.getLocation().distance(item.getLocation());
                    }

                    if (pl != p && pl.getLocation().distance(item.getLocation()) < 5) {

                        if (item.getLocation().distance(pl.getLocation()) < 4) {

                            pl.getInventory().addItem(is);
                            SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, pl.getLocation(), 1.2F, 0.7F);
                            SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, p.getLocation(), 1.2F, 0.7F);
                            item.remove();
                            this.cancel();

                            return;
                        }
                        if (target == null) {
                            target = pl;
                        } else {
                            if (pl.getLocation().distance(item.getLocation()) < target.getLocation().distance(item.getLocation())) {
                                target = pl;
                            }
                        }
                    }
                }

                if (i > 20 * 3) {
                    SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, p.getLocation(), 1.2F, 0.7F);
                    p.getInventory().addItem(is);
                    item.remove();
                    this.cancel();
                }
                ParUtils.createRedstoneParticle(item.getLocation().add(0, 0.3, 0), 0.05, 0.05, 0.05, 2, Color.YELLOW, 1.3F);
                item.setVelocity(dir.clone().multiply(speedMult));
                if (target != null) {
                    Vector vec = target.getLocation().toVector().subtract(item.getLocation().toVector()).normalize();
                    dir = dir.add(vec).normalize();
                }


            }
        }.runTaskTimer(main.plugin, 1, 1);
    }


    public void throwBook(Player p, ItemStack is) {
        TypeTEAMS t = (TypeTEAMS) GameType.getType();
        Item item = p.getWorld().dropItem(p.getEyeLocation(), is);
        item.setPickupDelay(100000);
        ScoreboardTeamUtils.colorEntity(item, t.getTeamOfPlayer(p).color);
        SoundUtils.playSound(Sound.ENTITY_SNOWBALL_THROW, p.getLocation(), 0.2F, 1);
        item.setGlowing(true);

        if (p.isSneaking()) {
            Player target = p;
            for (Player pl : t.getTeammates(p)) {
                if (pl.getLocation().distance(p.getLocation()) > target.getLocation().distance(p.getLocation())) {
                    target = pl;
                }
            }

            target.getInventory().addItem(is);
            SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, target.getLocation(), 1.2F, 0.7F);
            SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, p.getLocation(), 1.2F, 0.7F);
            item.remove();

        } else {
            new BukkitRunnable() {

                Vector dir = p.getLocation().getDirection();
                int i = 0;
                final double speedMult = 3;

                public void run() {

                    TypeTEAMS t = (TypeTEAMS) GameType.getType();
                    i++;

                    if (t.gameOver || Gamestate.getGameState() == Gamestate.LOBBY) {
                        this.cancel();
                    }
                    SoundUtils.playSound(Sound.ITEM_BOOK_PAGE_TURN, item.getLocation(), 2, 1);
                    Player target = null;
                    Player closest = null;
                    double dist = 100000;
                    for (Player pl : t.getTeammates(p)) {

                        if (pl != p && t.players.contains(pl) && pl.getLocation().distance(item.getLocation()) < dist) {
                            dist = pl.getLocation().distance(item.getLocation());
                            closest = pl;

                        }

                        if (pl != p && pl.getLocation().distance(item.getLocation()) < 30) {

                            if (item.getLocation().distance(pl.getLocation()) < 4) {

                                pl.getInventory().addItem(is);
                                SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, pl.getLocation(), 1.2F, 0.7F);
                                SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, p.getLocation(), 1.2F, 0.7F);
                                item.remove();
                                this.cancel();

                                return;
                            }
                            if (target == null) {
                                target = pl;
                            } else {
                                if (pl.getLocation().distance(item.getLocation()) < target.getLocation().distance(item.getLocation())) {
                                    target = pl;
                                }
                            }
                        }

                    }

                    if (!t.players.contains(p)) {
                        if (closest != null) {
                            closest.getInventory().addItem(is);
                            SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, closest.getLocation(), 1.2F, 0.7F);
                            SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, p.getLocation(), 1.2F, 0.7F);
                            item.remove();
                            this.cancel();
                        }

                    }
                    if (i > 20 * 3) {
                        SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, p.getLocation(), 1.2F, 0.7F);
                        p.getInventory().addItem(is);
                        item.remove();
                        this.cancel();
                    }
                    ParUtils.createRedstoneParticle(item.getLocation().add(0, 0.3, 0), 0.05, 0.05, 0.05, 2, t.getTeamOfPlayer(p).parcolor, 1.3F);
                    item.setVelocity(dir.clone().multiply(speedMult));
                    if (target != null) {
                        Vector vec = target.getLocation().toVector().subtract(item.getLocation().toVector()).normalize();
                        dir = dir.add(vec).normalize();
                    }


                }
            }.runTaskTimer(main.plugin, 1, 1);
        }


    }
}
