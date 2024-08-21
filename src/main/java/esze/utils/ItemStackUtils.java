package esze.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnull;
import java.util.List;


public class ItemStackUtils {

    public static ItemStack createItemStack(@Nonnull Material mat, @Nonnull int amount, @Nonnull int durability, String name, List<String> lore, @Nonnull boolean unbreakable) {
        ItemStack i = new ItemStack(mat, amount, (short) durability);
        ItemMeta meta = i.getItemMeta();
        if (name != null) meta.setDisplayName(name);
        if (lore != null) meta.setLore(lore);
        meta.setUnbreakable(unbreakable);
        i.setItemMeta(meta);
        return i;
    }
	

    public static ItemStack attackSpeedify(ItemStack is) {
        ItemMeta meta = is.getItemMeta();
        meta.addAttributeModifier(
                Attribute.GENERIC_ATTACK_SPEED,
                new AttributeModifier(
                        NamespacedKey.fromString("generic.attack_speed"),
                        0,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlotGroup.HAND
                )
        );
        is.setItemMeta(meta);
        return is;
    }

    public static ItemStack attackDamage(ItemStack is, int dmg) {
        ItemMeta im = is.getItemMeta();
        im.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("AttackSpeed", 4, Operation.ADD_NUMBER));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        is.setItemMeta(im);
        return is;
    }


    public static ItemStack getPlayerHead(Player p) {
        ItemStack playerhead = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        String name = p.getName();
        SkullMeta playerheadmeta = (SkullMeta) playerhead.getItemMeta();
        playerheadmeta.setOwner(name);
        playerheadmeta.setDisplayName(name + "'s Kopf");
        playerhead.setItemMeta(playerheadmeta);
        return playerhead;
    }

    public static ItemStack createSpell(String name) {
        ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta im = is.getItemMeta();

        im.setDisplayName(name);
        is.setItemMeta(im);
        is = NBTUtils.setNBT("SpellKey", "" + SpellKeyUtils.getNextSpellKey(), is);
        is = NBTUtils.setNBT("Spell", "true", is);
        is = NBTUtils.setNBT("OriginalName", is.getItemMeta().getDisplayName(), is);
        return is;
    }
}
