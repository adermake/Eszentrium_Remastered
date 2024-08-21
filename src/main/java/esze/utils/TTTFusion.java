package esze.utils;


import esze.main.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import spells.spellcore.SpellList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TTTFusion implements Listener {

    public static ArrayList<Inventory> invs = new ArrayList<Inventory>();

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9 * 3, "§0Reroll - Lege 3 Spells ab");

        ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(" ");
        i.setItemMeta(im);

        ItemStack is = new ItemStack(Material.END_CRYSTAL);
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(" ");
        is.setItemMeta(ism);

        inv.setItem(0, i);
        inv.setItem(1, i);
        inv.setItem(2, i);
        inv.setItem(3, i);
        inv.setItem(4, i);
        inv.setItem(5, i);
        inv.setItem(6, i);
        inv.setItem(7, i);
        inv.setItem(8, i);
        inv.setItem(9, i);
        inv.setItem(10, i);
        inv.setItem(11, is);

        inv.setItem(15, is);
        inv.setItem(16, i);
        inv.setItem(17, i);
        inv.setItem(18, i);
        inv.setItem(19, i);
        inv.setItem(20, i);
        inv.setItem(21, i);
        inv.setItem(22, i);
        inv.setItem(23, i);
        inv.setItem(24, i);
        inv.setItem(25, i);
        inv.setItem(26, i);

        p.openInventory(inv);
        invs.add(inv);
    }

    @SuppressWarnings("deprecation")
    public static void start() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(main.plugin, new Runnable() {

            public void run() {
                if (!invs.isEmpty()) {
                    ArrayList<Inventory> invlist = (ArrayList<Inventory>) invs.clone();
                    for (Inventory inv : invlist) {//12,13,14
                        if (inv.getItem(12) != null && inv.getItem(12).getType() == Material.ENCHANTED_BOOK) {
                            if (inv.getItem(13) != null && inv.getItem(13).getType() == Material.ENCHANTED_BOOK) {
                                if (inv.getItem(14) != null && inv.getItem(14).getType() == Material.ENCHANTED_BOOK) {


                                    ItemStack random;
                                    while (true) {
                                        String rand = SpellList.getRandomSpell().getName();

                                        while (rand.startsWith("§c") || rand.startsWith("§8")) {
                                            rand = SpellList.getRandomSpell().getName();
                                        }

                                        rand = "§2" + ChatColor.stripColor(rand);

                                        System.out.println("GOT" + rand);

                                        ItemStack i1 = new ItemStack(Material.ENCHANTED_BOOK);
                                        ItemMeta i1m = i1.getItemMeta();
                                        i1m.setDisplayName(rand);
                                        i1.setItemMeta(i1m);

                                        if (inv.getItem(12).hasItemMeta() && inv.getItem(12).getItemMeta().hasDisplayName() && !inv.getItem(12).getItemMeta().getDisplayName().equals(i1.getItemMeta().getDisplayName())) {
                                            if (inv.getItem(13).hasItemMeta() && inv.getItem(13).getItemMeta().hasDisplayName() && !inv.getItem(13).getItemMeta().getDisplayName().equals(i1.getItemMeta().getDisplayName())) {
                                                if (inv.getItem(14).hasItemMeta() && inv.getItem(14).getItemMeta().hasDisplayName() && !inv.getItem(14).getItemMeta().getDisplayName().equals(i1.getItemMeta().getDisplayName())) {
                                                    random = i1;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
                                    ItemMeta im = i.getItemMeta();
                                    im.setDisplayName(" ");
                                    i.setItemMeta(im);
                                    ItemStack is = new ItemStack(Material.END_CRYSTAL);
                                    ItemMeta ism = is.getItemMeta();
                                    ism.setDisplayName(" ");
                                    is.setItemMeta(ism);

                                    random.setType(Material.WRITABLE_BOOK);

                                    inv.setItem(11, i);
                                    inv.setItem(12, is);
                                    inv.setItem(13, random);
                                    inv.setItem(14, is);
                                    inv.setItem(15, i);
                                    int last = 0;
                                    for (int in = 1; in < 9; in++) {
                                        main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(main.plugin, new Runnable() {
                                            public void run() {

                                                int no1 = 0;
                                                int no2 = 0;

                                                if (no1 != 5 && no2 != 5 && inv.getItem(5).getType() == Material.END_CRYSTAL) {
                                                    inv.setItem(5, i);
                                                    inv.setItem(14, is);
                                                    if (no1 == 0) {
                                                        no1 = 14;
                                                    } else {
                                                        no2 = 14;
                                                    }
                                                }
                                                if (no1 != 4 && no2 != 4 && inv.getItem(4).getType() == Material.END_CRYSTAL) {
                                                    inv.setItem(4, i);
                                                    inv.setItem(5, is);
                                                    if (no1 == 0) {
                                                        no1 = 5;
                                                    } else {
                                                        no2 = 5;
                                                    }
                                                }
                                                if (no1 != 3 && no2 != 3 && inv.getItem(3).getType() == Material.END_CRYSTAL) {
                                                    inv.setItem(3, i);
                                                    inv.setItem(4, is);
                                                    if (no1 == 0) {
                                                        no1 = 4;
                                                    } else {
                                                        no2 = 4;
                                                    }
                                                }
                                                if (no1 != 12 && no2 != 12 && inv.getItem(12).getType() == Material.END_CRYSTAL) {
                                                    inv.setItem(12, i);
                                                    inv.setItem(3, is);
                                                    if (no1 == 0) {
                                                        no1 = 3;
                                                    } else {
                                                        no2 = 3;
                                                    }
                                                }
                                                if (no1 != 21 && no2 != 21 && inv.getItem(21).getType() == Material.END_CRYSTAL) {
                                                    inv.setItem(21, i);
                                                    inv.setItem(12, is);
                                                    if (no1 == 0) {
                                                        no1 = 12;
                                                    } else {
                                                        no2 = 12;
                                                    }
                                                }
                                                if (no1 != 22 && no2 != 22 && inv.getItem(22).getType() == Material.END_CRYSTAL) {
                                                    inv.setItem(22, i);
                                                    inv.setItem(21, is);
                                                    if (no1 == 0) {
                                                        no1 = 21;
                                                    } else {
                                                        no2 = 21;
                                                    }
                                                }
                                                if (no1 != 23 && no2 != 23 && inv.getItem(23).getType() == Material.END_CRYSTAL) {
                                                    inv.setItem(23, i);
                                                    inv.setItem(22, is);
                                                    if (no1 == 0) {
                                                        no1 = 22;
                                                    } else {
                                                        no2 = 22;
                                                    }
                                                }
                                                if (no1 != 14 && no2 != 14 && inv.getItem(14).getType() == Material.END_CRYSTAL) {
                                                    inv.setItem(14, i);
                                                    inv.setItem(23, is);
                                                    if (no1 == 0) {
                                                        no1 = 23;
                                                    } else {
                                                        no2 = 23;
                                                    }
                                                }
                                            }
                                        }, in * 10L);
                                        last = in + 1;
                                    }

                                    main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(main.plugin, new Runnable() {
                                        public void run() {
                                            ItemStack iss = inv.getItem(13);
                                            iss.setType(Material.ENCHANTED_BOOK);
                                            iss = NBTUtils.setNBT("Spell", "true", iss);
                                            inv.setItem(13, iss);
                                        }
                                    }, last * 10L);

                                    invs.remove(inv);
                                }
                            }
                        }
                    }
                }
            }
        }, 20, 15);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getView().getTitle().equals("§0Reroll - Lege 3 Spells ab")) {
            if (e.getInventory().getItem(12) != null && e.getInventory().getItem(12).getType() == Material.ENCHANTED_BOOK) {

                p.getInventory().addItem(e.getInventory().getItem(12));
            }
            if (e.getInventory().getItem(13) != null && e.getInventory().getItem(13).getType() == Material.ENCHANTED_BOOK) {
                p.getInventory().addItem(e.getInventory().getItem(13));
            }
            if (e.getInventory().getItem(13) != null && e.getInventory().getItem(13).getType() == Material.WRITABLE_BOOK) {
                ItemStack is = e.getInventory().getItem(13);
                is.setType(Material.ENCHANTED_BOOK);
                p.getInventory().addItem(is);
            }
            if (e.getInventory().getItem(14) != null && e.getInventory().getItem(14).getType() == Material.ENCHANTED_BOOK) {
                p.getInventory().addItem(e.getInventory().getItem(14));
            }
            invs.remove(e.getInventory());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("§0Reroll - Lege 3 Spells ab")) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE || e.getCurrentItem().getType() == Material.END_CRYSTAL || e.getCurrentItem().getType() == Material.WRITABLE_BOOK) {
                    e.setCancelled(true);
                }
            }
        }
    }


    public <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

}
