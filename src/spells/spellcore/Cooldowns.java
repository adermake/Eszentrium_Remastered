package spells.spellcore;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.Actionbar;
import esze.utils.MathUtils;
import esze.utils.NBTUtils;

public class Cooldowns {

	public static ArrayList<ItemStack> removeLater = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> removeresetCooldown = new ArrayList<ItemStack>();
	public static HashMap<ItemStack,Player> refund = new HashMap<ItemStack,Player>();
	public static void startCooldownHandler() {
		int barcount = 10;
		int downticker = 1;
		
		new BukkitRunnable() {
			public void run() {
				
				for (ItemStack is : removeLater) {
					is.setType(Material.AIR);
				}
			
				
				
				for (Player p : Bukkit.getOnlinePlayers()) {
					//Bukkit.broadcastMessage(""+p.getOpenInventory());
					/*
					if (EventCollector.openInventory.containsKey(p)) {
						Bukkit.broadcastMessage("opne");
						continue;
					}
					Bukkit.broadcastMessage("close");
					*/
					for (int slot = 0;slot<p.getInventory().getSize();slot++) {
						ItemStack i = p.getInventory().getItem(slot);
						
						
						if (i ==  null ) {
							continue;
						}
						//Bukkit.broadcastMessage(">>"+NBTUtils.getNBT("Cooldown", i) );
						if (NBTUtils.getNBT("Cooldown", i) == "0" || NBTUtils.getNBT("Cooldown", i) == "" ) {
							continue;
						}
						//Bukkit.broadcastMessage(""+i);
						String nbtData = NBTUtils.getNBT("Cooldown", i);
						String burn = NBTUtils.getNBT("Burn", i);
						if (burn == "true") {
							
							i = new ItemStack(Material.BOOK);
							ItemMeta m = i.getItemMeta();
							m.setDisplayName("§7Verbranntes Buch");
							
							i.setItemMeta(m);
							p.getInventory().setItem(slot, i);
							return;
						}
						if (!(nbtData.equals(""))) {
							double cooldown = Double.parseDouble(nbtData);
							cooldown -= downticker;
							if (cooldown<=0) {
								
								ItemMeta im = i.getItemMeta();
								im.setDisplayName(NBTUtils.getNBT("OriginalName", i));
								i.setItemMeta(im);
								i.setType(Material.ENCHANTED_BOOK);
								i = NBTUtils.setNBT("Cooldown", "0", i);
								p.getInventory().setItem(slot, i);
							}
							else {
								
								double maxCd = Double.parseDouble(NBTUtils.getNBT("MaxCooldown", i));
								ItemMeta im = i.getItemMeta();
								String currentName = NBTUtils.getNBT("OriginalName", i);
								double per = cooldown/maxCd;
								currentName = currentName + " §7<[";
								int anticount = 0;
								for (int count = 0;count<= (int)(per*barcount);count++) {
									currentName = currentName + "§4|";
									anticount++;
								}
								anticount = barcount-anticount;
								for (int count = 0;count<= anticount;count++) {
									currentName = currentName + "§8|";
								}
								currentName = currentName + "§7]>";
								im.setDisplayName(currentName);
								i.setItemMeta(im);
								i.setType(Material.BOOK);
								i = NBTUtils.setNBT("Cooldown", ""+cooldown, i);
								p.getInventory().setItem(slot, i);
								
							}
						}
						//p.updateInventory();
						
					}
					
				}
			}
		}.runTaskTimer(main.plugin, 1, downticker);
		
		
	}
	
	
	
	public static boolean refundContainsSameItem(ItemStack is) {
		for (ItemStack i : refund.keySet()) {
			if (i.equals(is)) {
				return true;
			}
		}
		return false;
	}
	
	public static int getSlotOfItem(Player p,ItemStack is) {
		
		Inventory inv = p.getInventory();
		for (int i = 0;i< p.getInventory().getSize();i++) {
			if (is.equals(inv.getItem(i) )){
				return i;
			}
		}
		return -1;
	}
	
	/*
	public static void refundCurrentSpell(Player p) {
		refund.put(p.getInventory().getItemInMainHand(),p);
		Actionbar a = new Actionbar("§c Kein Ziel gefunden!");
		a.send(p);
		
		
		/*
		new BukkitRunnable() {
			ItemStack is = p.getInventory().getItemInMainHand();
			public void run() {
				
				
				is = NBTUtils.setNBT("Cooldown", "0", is);
				p.getInventory().setItemInMainHand(is);
			}
		}.runTaskLater(main.plugin, 1);
		
	}*/

	public static void refundCurrentRandomSpell(Player p) {
		
		Actionbar a = new Actionbar("§a Cooldown für einen Spell zurückgestezt!");
		a.send(p);
		new BukkitRunnable() {
			public void run() {
				
				ItemStack is = p.getInventory().getItem(MathUtils.randInt(0,10));
				is = NBTUtils.setNBT("Cooldown", "0", is);
				p.getInventory().setItemInMainHand(is);
			}
		}.runTaskLater(main.plugin, 1);
		
	}
	
}
