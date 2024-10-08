package esze.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpellKeyUtils {


    private static int nextspellKey = 0;

    public static void reduceCooldown(Player p, int spellkey, int ticks) {
        //Bukkit.broadcastMessage("LOOKING FOR BOOK "+spellkey);
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack is = p.getInventory().getItem(i);
            if (NBTUtils.getNBT("SpellKey", is) != "") {


                int key = Integer.parseInt(NBTUtils.getNBT("SpellKey", is));
                //Bukkit.broadcastMessage("Found key in inventory "+key);
                if (key == spellkey) {
                    double cd = Double.parseDouble(NBTUtils.getNBT("Cooldown", is));
                    cd -= ticks;
                    if (cd <= 0)
                        cd = 0;
                    is = NBTUtils.setNBT("Cooldown", "" + cd, is);
                    //Bukkit.broadcastMessage("FOUND BOOK");
                    p.getInventory().setItem(i, is);
                    break;
                }
            }
        }
    }


    public static int getNextSpellKey() {
        return nextspellKey++;
    }


}
