package esze.utils;

import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.item.ItemStack;

public class NBTUtils {

	
	
	
	public static org.bukkit.inventory.ItemStack setNBT(String key,String value,org.bukkit.inventory.ItemStack is) {
		ItemStack nms = CraftItemStack.asNMSCopy(is);
		NBTTagCompound n = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		n.set(key, NBTTagString.a(value));
		nms.setTag(n);
		is = CraftItemStack.asBukkitCopy(nms);
		return is;
	}
	public static org.bukkit.inventory.ItemStack setNBT(NBTTagCompound n,org.bukkit.inventory.ItemStack is) {
		ItemStack nms = CraftItemStack.asNMSCopy(is);
		
		nms.setTag(n);
		is = CraftItemStack.asBukkitCopy(nms);
		return is;
	}
	public static String getNBT(String key,org.bukkit.inventory.ItemStack is) {
		ItemStack nms = CraftItemStack.asNMSCopy(is);
		NBTTagCompound n = (nms.hasTag()) ? nms.getTag() : new NBTTagCompound();
		
		return n.getString(key);
		
	}
	
	
	
}
