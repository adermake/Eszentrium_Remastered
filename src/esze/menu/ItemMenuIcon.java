package esze.menu;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMenuIcon extends ItemStack {

	private int gridX;
	private int gridY;
	private ItemMenu itemMenu;

	
	public static ArrayList<ItemMenuIcon> allIcons = new ArrayList<ItemMenuIcon>();

	public ItemMenuIcon(int gridX, int gridY, Material m, String iconname, ItemMenu i) {

		super(m);
		itemMenu = i;
		ItemMeta im = this.getItemMeta();
		im.setDisplayName(iconname);
		this.setItemMeta(im);
		allIcons.add(this);

	}

	public static void ditributeClicks(String name, Inventory i, Player p) {
		try {
			for (ItemMenuIcon icon : allIcons) {
				if (icon.getItemMeta().getDisplayName().equals(name)
						&& icon.getItemMenu().getInventory().equals(i)) {
					icon.getItemMenu().clicked(icon, p);

				}
			}
		}
		catch(ConcurrentModificationException e) {
			
		}
		

	}

	public int getGridX() {
		return gridX;
	}

	public ItemMenu getItemMenu() {
		return itemMenu;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public String getName() {

		if (this.hasItemMeta() && this.getItemMeta().hasDisplayName()) {

			return this.getItemMeta().getDisplayName();
		} else {
			return null;
		}
	}
	
	public boolean compareName(String toCompare) {
		if (this.hasItemMeta() && this.getItemMeta().hasDisplayName()) {

			return toCompare.equals(this.getName());
		} else {
			return false;
		}
	}

}
