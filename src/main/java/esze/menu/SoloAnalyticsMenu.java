package esze.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import esze.analytics.SaveUtils;

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
		
		addClickableItem(1, 1, Material.EMERALD, "§rSiege: " + SaveUtils.getAnalytics().getPlayerVictories(s));
		addClickableItem(2, 1, Material.REDSTONE, "§rNiederlage: " + SaveUtils.getAnalytics().getPlayerLosses(s));
		addClickableItem(3, 1, Material.DIAMOND_SWORD, "§rTötungen: " + SaveUtils.getAnalytics().getPlayerKills(s));
		addClickableItem(4, 1, Material.STONE_SWORD, "§rTode: " + SaveUtils.getAnalytics().getPlayerDeaths(s));
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
