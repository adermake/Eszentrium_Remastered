package esze.utils;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;



public class ItemStackUtils {
	
	public static ItemStack createItemStack(@Nonnull Material mat, @Nonnull int amount, @Nonnull int durability, String name, List<String> lore, @Nonnull boolean unbreakable){
		ItemStack i = new ItemStack(mat, amount, (short)durability);
		ItemMeta meta = i.getItemMeta();
		if(name != null)meta.setDisplayName(name);
		if(lore != null)meta.setLore(lore);
		meta.setUnbreakable(unbreakable);
		i.setItemMeta(meta);
		return i;
	}
	
	/*public static ItemStack createSkullStack(@Nonnull int amount, String name, String owner, List<String> lore, @Nonnull boolean unbreakable){
		ItemStack i = new ItemStack(Material.LEGACY_SKULL_ITEM, amount, (short)3);
		SkullMeta meta = (SkullMeta) i.getItemMeta();
		if(name != null)meta.setDisplayName(name);
		if(lore != null)meta.setLore(lore);
		if(owner != null)meta.setOwner(owner);
		meta.setUnbreakable(unbreakable);
		i.setItemMeta(meta);
		return i;
	}*/
	
	public static ItemStack createLeatherArmor(@Nonnull Material mat, @Nonnull int amount, @Nonnull int durability, String name, List<String> lore, @Nonnull boolean unbreakable, Color color){
		ItemStack i = new ItemStack(mat, amount, (short)durability);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		if(name != null)meta.setDisplayName(name);
		if(lore != null)meta.setLore(lore);
		meta.setColor(color);
		meta.setUnbreakable(unbreakable);
		i.setItemMeta(meta);
		return i;
	}
	
	public static ItemStack attackSpeedify(ItemStack is) {
		net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound speed = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		
    
		speed.set("AttributeName",  NBTTagString.a("generic.attack_speed"));
		speed.set("Name", NBTTagString.a("generic.attack_speed"));
		speed.set("Amount", NBTTagDouble.a(0));
		speed.set("Operation", NBTTagInt.a(0));
		speed.set("UUIDLeast", NBTTagInt.a(894654));
		speed.set("UUIDMost", NBTTagInt.a(2872));
		speed.set("Slot", NBTTagString.a("mainhand"));
		
		
		return NBTUtils.setNBT(speed, is);
	}
	
	public static ItemStack attackDamage(ItemStack is,int dmg) {
	
		  /*
	      
        net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = nmsStack.getTag();
        if (compound == null) {
           compound = new NBTTagCompound();
            nmsStack.setTag(compound);
            compound = nmsStack.getTag();
        }
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound damage = new NBTTagCompound();
        damage.set("AttributeName", NBTTagString.a("generic.attack_damage"));
        damage.set("Name", NBTTagString.a("generic.attack_damage"));
        damage.set("Amount", NBTTagInt.a(dmg));
        damage.set("Slot", NBTTagString.a("mainhand"));
        damage.set("Operation", NBTTagInt.a(0));
       // damage.set("UUIDLeast", NBTTagInt.a(724099));
       // damage.set("UUIDMost", NBTTagInt.a(439684));
    	damage.set("UUIDLeast", NBTTagInt.a(894654));
		damage.set("UUIDMost", NBTTagInt.a(2872));
        modifiers.add(damage);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        is = CraftItemStack.asBukkitCopy(nmsStack);
      
         
		*/
		ItemMeta im  = is.getItemMeta();
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
	    playerheadmeta.setDisplayName(name+"'s Kopf");
	    playerhead.setItemMeta(playerheadmeta);
	    return playerhead;
	}
	
	public static ItemStack createSpell(String name) {
		ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
	    ItemMeta im = is.getItemMeta();
	    
   
        //name = name.replace("&", "�");
      
        
       
       
        im.setDisplayName(name);
        is.setItemMeta(im);
        is = NBTUtils.setNBT("SpellKey",""+ SpellKeyUtils.getNextSpellKey(), is);
        is = NBTUtils.setNBT("Spell", "true", is);
        is = NBTUtils.setNBT("OriginalName", is.getItemMeta().getDisplayName(), is);
        return is;
	}
}
