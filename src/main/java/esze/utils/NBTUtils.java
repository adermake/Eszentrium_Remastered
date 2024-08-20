package esze.utils;

import esze.main.main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class NBTUtils {


    public static org.bukkit.inventory.ItemStack setNBT(String key, String value, org.bukkit.inventory.ItemStack is) {
		/*ItemStack nms = CraftItemStack.asNMSCopy(is);
		NBTTagCompound n = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		n.set(key, NBTTagString.a(value));
		nms.setTag(n);
		is = CraftItemStack.asBukkitCopy(nms);*/

        NamespacedKey namespacedKey = new NamespacedKey(main.plugin, key);
        ItemMeta meta = is.getItemMeta();
        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
        is.setItemMeta(meta);

        return is;
    }
	/*public static org.bukkit.inventory.ItemStack setNBT(NBTTagCompound n,org.bukkit.inventory.ItemStack is) {
		ItemStack nms = CraftItemStack.asNMSCopy(is);
		
		nms.setTag(n);
		is = CraftItemStack.asBukkitCopy(nms);
		return is;
	}*/

    public static String getNBT(String key, org.bukkit.inventory.ItemStack is) {

        ItemMeta meta = is.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(main.plugin, key);
        if (meta == null) return "";
        if (meta.getPersistentDataContainer() == null) return "";
        if (meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING) == null) return "";
        return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);

		/*ItemStack nms = CraftItemStack.asNMSCopy(is);
		NBTTagCompound n = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		
		return n.getString(key);*/

    }


}
