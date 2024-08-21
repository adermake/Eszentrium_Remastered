package esze.utils;

import esze.main.main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class NBTUtils {

    public static org.bukkit.inventory.ItemStack setNBT(String key, String value, org.bukkit.inventory.ItemStack is) {
        NamespacedKey namespacedKey = new NamespacedKey(main.plugin, key);
        ItemMeta meta = is.getItemMeta();
        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
        is.setItemMeta(meta);

        return is;
    }

    public static String getNBT(String key, org.bukkit.inventory.ItemStack is) {

        ItemMeta meta = is.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(main.plugin, key);
        if (meta == null) return "";
        if (meta.getPersistentDataContainer() == null) return "";
        if (meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING) == null) return "";
        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);

    }


}
