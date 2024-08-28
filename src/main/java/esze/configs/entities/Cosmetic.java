package esze.configs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Cosmetic {

    // WEAPON: Swords
    WOODEN_SWORD(CosmeticType.WEAPON, Material.WOODEN_SWORD, null, null, null),
    GOLDEN_SWORD(CosmeticType.WEAPON, Material.GOLDEN_SWORD, null, null, null),
    STONE_SWORD(CosmeticType.WEAPON, Material.STONE_SWORD, null, null, null),
    IRON_SWORD(CosmeticType.WEAPON, Material.IRON_SWORD, null, null, null),
    DIAMOND_SWORD(CosmeticType.WEAPON, Material.DIAMOND_SWORD, null, null, null),
    NETHERITE_SWORD(CosmeticType.WEAPON, Material.NETHERITE_SWORD, null, null, null),
    RED_SWORD(CosmeticType.WEAPON, Material.WOODEN_SWORD, "§r§fRotes Schwert", 1, null),

    // WEAPON: Tools
    WOODEN_AXE(CosmeticType.WEAPON, Material.WOODEN_AXE, null, null, null),
    WOODEN_PICKAXE(CosmeticType.WEAPON, Material.WOODEN_PICKAXE, null, null, null),
    WOODEN_SHOVEL(CosmeticType.WEAPON, Material.WOODEN_SHOVEL, null, null, null),
    GOLDEN_AXE(CosmeticType.WEAPON, Material.GOLDEN_AXE, null, null, null),
    GOLDEN_PICKAXE(CosmeticType.WEAPON, Material.GOLDEN_PICKAXE, null, null, null),
    GOLDEN_SHOVEL(CosmeticType.WEAPON, Material.GOLDEN_SHOVEL, null, null, null),
    STONE_AXE(CosmeticType.WEAPON, Material.STONE_AXE, null, null, null),
    STONE_PICKAXE(CosmeticType.WEAPON, Material.STONE_PICKAXE, null, null, null),
    STONE_SHOVEL(CosmeticType.WEAPON, Material.STONE_SHOVEL, null, null, null),
    IRON_AXE(CosmeticType.WEAPON, Material.IRON_AXE, null, null, null),
    IRON_PICKAXE(CosmeticType.WEAPON, Material.IRON_PICKAXE, null, null, null),
    IRON_SHOVEL(CosmeticType.WEAPON, Material.IRON_SHOVEL, null, null, null),
    DIAMOND_AXE(CosmeticType.WEAPON, Material.DIAMOND_AXE, null, null, null),
    DIAMOND_PICKAXE(CosmeticType.WEAPON, Material.DIAMOND_PICKAXE, null, null, null),
    DIAMOND_SHOVEL(CosmeticType.WEAPON, Material.DIAMOND_SHOVEL, null, null, null),
    NETHERITE_AXE(CosmeticType.WEAPON, Material.NETHERITE_AXE, null, null, null),
    NETHERITE_PICKAXE(CosmeticType.WEAPON, Material.NETHERITE_PICKAXE, null, null, null),
    NETHERITE_SHOVEL(CosmeticType.WEAPON, Material.NETHERITE_SHOVEL, null, null, null),

    // WEAPON: Other
    STICK(CosmeticType.WEAPON, Material.WOODEN_SWORD, "§r§fStock", 2, null),
    SAUSAGE(CosmeticType.WEAPON, Material.WOODEN_SWORD, "§r§fWürstchen", 3, null),
    BLAZE_ROD(CosmeticType.WEAPON, Material.BLAZE_ROD, null, null, null),
    BONE(CosmeticType.WEAPON, Material.BONE, null, null, null),
    BAMBOO(CosmeticType.WEAPON, Material.BAMBOO, null, null, null),
    CARROT_ON_A_STICK(CosmeticType.WEAPON, Material.CARROT_ON_A_STICK, null, null, null),

    // ARMOR: Helmets
    LEATHER_HELMET(CosmeticType.HEAD, Material.LEATHER_HELMET, null, null, null),
    GOLDEN_HELMET(CosmeticType.HEAD, Material.GOLDEN_HELMET, null, null, null),
    CHAINMAIL_HELMET(CosmeticType.HEAD, Material.CHAINMAIL_HELMET, null, null, null),
    IRON_HELMET(CosmeticType.HEAD, Material.IRON_HELMET, null, null, null),
    DIAMOND_HELMET(CosmeticType.HEAD, Material.DIAMOND_HELMET, null, null, null),
    NETHERITE_HELMET(CosmeticType.HEAD, Material.NETHERITE_HELMET, null, null, null),
    DRAGON_HEAD(CosmeticType.HEAD, Material.DRAGON_HEAD, "§r§fDrachenkopf", null, null),
    CYLINDER_HAT(CosmeticType.HEAD, Material.FIRE_CHARGE, "§r§fZylinder", 1, null),
    GLASSES(CosmeticType.HEAD, Material.FIRE_CHARGE, "§r§fBrille", 2, null),


    // ARMOR: Chestplates
    LEATHER_CHESTPLATE(CosmeticType.CHEST, Material.LEATHER_CHESTPLATE, null, null, null),
    GOLDEN_CHESTPLATE(CosmeticType.CHEST, Material.GOLDEN_CHESTPLATE, null, null, null),
    CHAINMAIL_CHESTPLATE(CosmeticType.CHEST, Material.CHAINMAIL_CHESTPLATE, null, null, null),
    IRON_CHESTPLATE(CosmeticType.CHEST, Material.IRON_CHESTPLATE, null, null, null),
    DIAMOND_CHESTPLATE(CosmeticType.CHEST, Material.DIAMOND_CHESTPLATE, null, null, null),
    NETHERITE_CHESTPLATE(CosmeticType.CHEST, Material.NETHERITE_CHESTPLATE, null, null, null),

    // ARMOR: Leggings
    LEATHER_LEGGINGS(CosmeticType.PANTS, Material.LEATHER_LEGGINGS, null, null, null),
    GOLDEN_LEGGINGS(CosmeticType.PANTS, Material.GOLDEN_LEGGINGS, null, null, null),
    CHAINMAIL_LEGGINGS(CosmeticType.PANTS, Material.CHAINMAIL_LEGGINGS, null, null, null),
    IRON_LEGGINGS(CosmeticType.PANTS, Material.IRON_LEGGINGS, null, null, null),
    DIAMOND_LEGGINGS(CosmeticType.PANTS, Material.DIAMOND_LEGGINGS, null, null, null),
    NETHERITE_LEGGINGS(CosmeticType.PANTS, Material.NETHERITE_LEGGINGS, null, null, null),

    // ARMOR: Boots
    LEATHER_BOOTS(CosmeticType.BOOTS, Material.LEATHER_BOOTS, null, null, null),
    GOLDEN_BOOTS(CosmeticType.BOOTS, Material.GOLDEN_BOOTS, null, null, null),
    CHAINMAIL_BOOTS(CosmeticType.BOOTS, Material.CHAINMAIL_BOOTS, null, null, null),
    IRON_BOOTS(CosmeticType.BOOTS, Material.IRON_BOOTS, null, null, null),
    DIAMOND_BOOTS(CosmeticType.BOOTS, Material.DIAMOND_BOOTS, null, null, null),
    NETHERITE_BOOTS(CosmeticType.BOOTS, Material.NETHERITE_BOOTS, null, null, null),;

    private final CosmeticType type;
    private final Material material;
    private final String name;
    private final Integer customModelData;
    private final List<String> lore;

    public ItemStack createItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
        return item;
    }

    public static List<Cosmetic> getCosmeticsByType(CosmeticType cosmeticType) {
        return Arrays.stream(Cosmetic.values())
                .filter(c -> c.getType() == cosmeticType)
                .toList();
    }
}
