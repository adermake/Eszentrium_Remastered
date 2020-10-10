package esze.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import esze.analytics.solo.SaveUtils;

public class SoloAnalyticsMenu extends ItemMenu {
	
	public SoloAnalyticsMenu(Player p) {
		super(4,"§rStats: " + p.getName());
		init(p.getName());
	}

	public SoloAnalyticsMenu(String string) {
		super(4,"§rStats: " + string);
		init(string);
	}

	public void init(String s) {
		
		addClickableItem(1, 1, Material.EMERALD, "§rSiege: " + SaveUtils.getSaveEsze().getVictories(s));
		addClickableItem(2, 1, Material.REDSTONE, "§rNiederlage: " + SaveUtils.getSaveEsze().getLosses(s));
		addClickableItem(3, 1, Material.DIAMOND_SWORD, "§rTötungen: " + SaveUtils.getSaveEsze().getKills(s));
		addClickableItem(4, 1, Material.STONE_SWORD, "§rTode: " + SaveUtils.getSaveEsze().getDeaths(s));
	}
	
	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
		// TODO Auto-generated method stub
		
	}

}
