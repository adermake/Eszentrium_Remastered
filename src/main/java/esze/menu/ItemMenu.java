package esze.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;


public abstract class ItemMenu {

	public Inventory inventory;
	
	
	
	public ItemMenu(int size) {
		
		inventory = Bukkit.createInventory(null, size*9);		
		
		
	}
	
	public ItemMenu(int size,String name) {
		
		inventory = Bukkit.createInventory(null, size*9,name);		
		
		
	}
	public void addClickableItem(int gridX, int gridY, Material m, String iconname) {
		
		ItemMenuIcon is = new ItemMenuIcon(gridX,gridY,m,iconname,this);
		inventory.setItem((gridY-1)*9+gridX-1, is);
		
		
		
	}
	

	public void addClickableItem(int gridX, int gridY, Material m, String iconname,String l) {
		
		ItemMenuIcon is = new ItemMenuIcon(gridX,gridY,m,iconname,this);
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(l);
		im.setLore(lore);
		is.setItemMeta(im);
		inventory.setItem((gridY-1)*9+gridX-1, is);
		
		
		
	}
	
	public void addClickableItem(int gridX, int gridY, Material m, String iconname,ArrayList<String> l) {
		
		ItemMenuIcon is = new ItemMenuIcon(gridX,gridY,m,iconname,this);
		ItemMeta im = is.getItemMeta();
		
		im.setLore(l);
		is.setItemMeta(im);
		inventory.setItem((gridY-1)*9+gridX-1, is);
		
		
		
	}
	
	public void addClickableItem(int gridX, int gridY, Material m, String iconname,String l,boolean enchanted) {
		
		ItemMenuIcon is = new ItemMenuIcon(gridX,gridY,m,iconname,this);
		if (enchanted)
		is.addUnsafeEnchantment(Enchantment.LURE, 1);
		
		ItemMeta im = is.getItemMeta();
		
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		List<String> lore = new ArrayList<String>();
		lore.add(l);
		im.setLore(lore);
		is.setItemMeta(im);
		inventory.setItem((gridY-1)*9+gridX-1, is);
		
		
		
	}
	public abstract void clicked(ItemMenuIcon icon,Player p);
	public abstract void clicked(ItemMenuIcon icon,Player p,InventoryAction a);
	
	public Inventory getInventory() {
		return inventory;
	}
	
	
	public void open(Player p) {
		p.openInventory(inventory);
	}
	
	
	
		
		
		
	
	
	
	
}
