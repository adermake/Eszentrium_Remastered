package esze.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class MonumentMenu extends ItemMenu {

	public MonumentMenu(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		// TODO Auto-generated method stub
		
		addClickableItem(1, 1, Material.STRING, "§eLuft","§a2 Punkte");
		addClickableItem(2, 1, Material.BLAZE_POWDER, "§eFeuer","§a6 Punkte");
		addClickableItem(3, 1, Material.DIAMOND, "§eNexus","§a0 Punkte");

	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
		// TODO Auto-generated method stub
		
	}

} 
