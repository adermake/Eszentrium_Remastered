package weapons;

import esze.main.main;
import esze.menu.CosmeticMenu;
import esze.menu.ItemMenu;
import esze.menu.ItemMenuIcon;
import esze.utils.Actionbar;
import esze.utils.ItemStackUtils;
import esze.utils.NBTUtils;
import esze.utils.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class WeaponMenu extends ItemMenu {

    public static HashMap<Player, String> items = new HashMap<Player, String>();

    public WeaponMenu(Player p) {
        super(1, "§cArsenal");

        int count = 1;
        if (items.containsKey(p)) {
            for (String name : WeaponList.weapons.keySet()) {
                addClickableItem(count, 1, WeaponList.weapons.get(name), name, "§e---", items.get(p).contains(removeColorTag(name)));
                count++;
            }
        } else {
            for (String name : WeaponList.weapons.keySet()) {
                addClickableItem(count, 1, WeaponList.weapons.get(name), name, "§e---");
                count++;
            }
        }
    }

    public static String removeColorTag(String s) {
        for (String tag : main.colorTags) {
            s = s.replace(tag, "");
        }
        return s;
    }

    @Override
    public void clicked(ItemMenuIcon icon, Player p) {

        items.put(p, icon.getName());
        p.sendMessage("Du hast " + icon.getName() + "§r ausgewählt!");
        ItemMeta im = icon.getItemMeta();
        im.addEnchant(Enchantment.LURE, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        icon.setItemMeta(im);
        p.closeInventory();

    }

    public static boolean running = true;

    public static void deliverItems() {
        running = true;
        WeaponAbilitys.cd.clear();
        WeaponAbilitys.cd2.clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
            WeaponAbilitys.charge1.put(p, 0);
            WeaponAbilitys.charge2.put(p, 0);

            ItemStack is = ItemStackUtils.createItemStack(PlayerConfig.getConfig(p).getWeaponMaterial(), 1, 0, PlayerConfig.getConfig(p).getWeaponCustomName(), null, true);
            ItemMeta im = is.getItemMeta();
            im.setCustomModelData(PlayerConfig.getConfig(p).getWeaponCustomModelData());
            is.setItemMeta(im);
            is = ItemStackUtils.attackSpeedify(is);
            is = ItemStackUtils.attackDamage(is, 4);
            is = NBTUtils.setNBT("Weapon", "true", is);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (WeaponAbilitys.lastLaunched.containsKey(p)) {
                        String spellname = WeaponAbilitys.lastLaunched.get(p).replace("spells.spells.", "");
                        if (!WeaponAbilitys.cd.contains(p))
                            new Actionbar("§b" + "Zauberecho" + ": " + "§c" + spellname).send(p);
                    }
                    if (!running) {
                        this.cancel();
                    }

                }
            }.runTaskTimer(main.plugin, 5, 5);
            p.getInventory().setItem(0, is);
        }
    }

    public static void stopLoop() {
        running = false;
    }

    @Override
    public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
        // TODO Auto-generated method stub

    }

}
