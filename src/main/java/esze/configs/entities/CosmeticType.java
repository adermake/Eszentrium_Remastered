package esze.configs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@AllArgsConstructor
@Getter
public enum CosmeticType {
    HEAD("Hut", Material.CHAINMAIL_HELMET, null),
    CHEST("Shirt", Material.DIAMOND_CHESTPLATE, null),
    PANTS("Hose", Material.LEATHER_LEGGINGS, null),
    BOOTS("Schuhe",Material.IRON_BOOTS, null),
    WEAPON("Waffe", Material.NETHERITE_SWORD, null);

    private final String name;
    private final Material material;
    private final Integer customModelData;

    public ItemStack createItem(String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
        return item;
    }
}