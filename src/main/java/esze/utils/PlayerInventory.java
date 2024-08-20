package esze.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInventory {

	HashMap<Integer,ItemStack> inventoryMap = new HashMap<Integer,ItemStack>();
	ArrayList<ItemStack> additionalItems = new ArrayList<ItemStack>();
	Player p;
	
	
	
		

	
	public PlayerInventory(Player p) {
		this.p = p;
		Inventory inv = p.getInventory();
		for (int i = 0; i< inv.getSize();i++) {
			ItemStack is = inv.getItem(i);
			if (is != null) {
				inventoryMap.put(i, is);
			}
		}
	}
	
	public void setInventory() {
		p.getInventory().clear();
		
		for (Integer in : inventoryMap.keySet()) {
			p.getInventory().setItem(in, inventoryMap.get(in));
		}
		for (ItemStack is : additionalItems) {
			p.getInventory().addItem(is);
		}
	}
	
	public void addItem(ItemStack is) {
		additionalItems.add(is);
	}
}
