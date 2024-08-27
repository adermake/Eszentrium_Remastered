package esze.listeners;

import esze.enums.GameType;
import esze.main.LobbyCountdownRunnable;
import esze.main.main;
import esze.menu.*;
import esze.types.TypeTEAMS;
import esze.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import spells.stagespells.Table;
import spells.stagespells.TimerSpell;
import spells.stagespells.WheelOfFortune;

public class Interact implements Listener {


    @EventHandler
    public void onPlayerDoorOpen(PlayerInteractEvent event) {
        Action action = event.getAction();
        org.bukkit.block.Block clicked = event.getClickedBlock();

        //Left or Right click?

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            if ((action == Action.RIGHT_CLICK_BLOCK) || (action == Action.LEFT_CLICK_BLOCK)) {
                //Door Block?
                if ((clicked.getType() == Material.CHEST) ||
                        (clicked.getType() == Material.TRAPPED_CHEST) ||
                        (clicked.getType() == Material.ENDER_CHEST) ||
                        (clicked.getType() == Material.HOPPER) ||
                        (clicked.getType() == Material.DISPENSER) ||
                        (clicked.getType() == Material.DROPPER) ||
                        (clicked.getType() == Material.FURNACE) ||
                        (clicked.getType() == Material.CRAFTING_TABLE) ||
                        (clicked.getType() == Material.BEACON) ||
                        (clicked.getType().toString().contains("TRAPDOOR")) ||
                        (clicked.getType() == Material.LEVER) ||
                        (clicked.getType().toString().contains("FENCE_GATE")) ||
                        (clicked.getType().toString().contains("BED")) ||
                        (clicked.getType() == Material.LECTERN)) {
                    event.setCancelled(true);
                }
            }
        }

        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (clicked.getType() == Material.BELL) {
                LobbyCountdownRunnable.start();
            }
        }

    }

    @EventHandler
    public void onKlick(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
        PlayerInteractEvent IE = new PlayerInteractEvent(p, Action.RIGHT_CLICK_AIR, p.getInventory().getItemInMainHand(), null, null);
        Bukkit.getPluginManager().callEvent(IE);


        if (e.getRightClicked().getType() == EntityType.LLAMA && p.isSneaking()) {
            e.setCancelled(true);
        }
        if (e.getRightClicked().getType() == EntityType.SHEEP) {
            p.addPassenger(e.getRightClicked());
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 14, 1));
        }
    }

    @EventHandler
    public void onKlick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = p.getInventory().getItemInMainHand();
            if (handItem.getType() == Material.WRITTEN_BOOK) {
                BookMeta bookMeta = (BookMeta) handItem.getItemMeta();
                if (bookMeta.getTitle().toLowerCase().contains("wheel")) {
                    new WheelOfFortune(p, bookMeta.getPage(1).split("\\n"));
                    e.setCancelled(true);
                }
                if (bookMeta.getTitle().toLowerCase().contains("timer")) {
                    new TimerSpell(p, Integer.parseInt(bookMeta.getPage(1)));
                    e.setCancelled(true);
                }
                if (bookMeta.getTitle().toLowerCase().contains("table")) {
                    new Table(p, bookMeta.getPages());
                    e.setCancelled(true);
                }

            }

            if (handItem.getType() == Material.ARROW) {
                e.setCancelled(true);
            }
            if (handItem.hasItemMeta()) {
                if (handItem.getItemMeta().getDisplayName().equals("§cErbrochene Fragmente")) {
                    if (p.isSneaking()) {
                        p.openInventory(new ColorTagSpellSelectionMenu(p.getName()).getInventory());
                    }
                }
                if (handItem.getItemMeta().getDisplayName().equals("§3Kosmetik")) {
                    new CosmeticMenu(p, 1);
                }
                if (handItem.getItemMeta().getDisplayName().equals("§3Modifikatoren")) {
                    ModifierMenu.getModifierWindow().open(p);
                }
                if (handItem.getItemMeta().getDisplayName().equals("§3Georg")) {
                    if (p.isSneaking()) {
                        p.openInventory(new ColorTagSpellSelectionMenu(p.getName()).getInventory());
                    } else {
                        int randColor = MathUtils.randInt(0, main.colorTags.size() - 1);
                        int randSpech = MathUtils.randInt(0, 4);

                        String s = "Georg sagt: '" + main.colorTags.get(randColor);

                        if (randSpech == 0) {
                            s += "Sei wie der letzte Keks in der Schüssel.";
                        }
                        if (randSpech == 1) {
                            s += "Führe das Leben wie eine Mutter im 19. Jahrhundert.";
                        }
                        if (randSpech == 2) {
                            s += "Der Sinn des Lebens ist wichtiger als die Frage selbst.";
                        }
                        if (randSpech == 3) {
                            s += "Führe ein Gespräch mit einer Giraffe. Sie könnte dein Leben bereichern.";
                        }
                        if (randSpech == 4) {
                            s += "Ein Spaziergang auf den Knien gibt dir eine neue Perspektive.";
                        }

                        s += "§r'";

                        p.sendMessage(s);
                        handItem.setType(Material.PRISMARINE_CRYSTALS);
                        ItemMeta im = handItem.getItemMeta();
                        im.setDisplayName("§cErbrochene Fragmente");
                        handItem.setItemMeta(im);
                        p.getInventory().setItemInMainHand(handItem);
                    }

                }
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§3Spellsammlung")) {
                    new ColorTagSpellSelectionMenu().open(p);
                }
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§3Teamauswahl")) {
                    if (GameType.getType() instanceof TypeTEAMS) {
                        new TeamSelectionMenu(p);
                    } else {
                        new TeamMonumenteSelectionMenu(p).open(p);
                    }
                }

            }
            if (p.isSneaking() && !p.getPassengers().isEmpty()) {
                if (p.getPassengers().getFirst() instanceof Sheep s) {
                    p.removePassenger(s);
                    s.setVelocity(p.getLocation().getDirection());
                }
            }
        }
    }

}
