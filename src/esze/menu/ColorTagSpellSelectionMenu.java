package esze.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ColorTagSpellSelectionMenu extends ItemMenu{

	private String player;
	
	public ColorTagSpellSelectionMenu(String p) {
		super(1,"Spells :-)");
		player = p;
		// TODO Auto-generated constructor stub
		addClickableItem(1, 1, Material.ARROW, "§eProjektilzauber");
		addClickableItem(2, 1, Material.ELYTRA, "§bMobilitätszauber");
		addClickableItem(3, 1, Material.PUFFERFISH, "§3Nahkampfzauber");
		addClickableItem(4, 1, Material.BLAZE_SPAWN_EGG, "§6Beschwörungszauber");
		addClickableItem(5, 1, Material.REDSTONE, "§cUltimative Zauber");
		addClickableItem(6, 1, Material.EMERALD, "§aUnterstützungszauber");
		addClickableItem(7, 1, Material.FLINT, "§8Verbotene Zauber");
		addClickableItem(8, 1, Material.TNT, "§4Verräterische Zauber");
	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		// TODO Auto-generated method stub
		if (icon.getName().equals("§4Verräterische Zauber")) {
			p.openInventory(new TraitorshopMenu().inventory);
		}
		else {
			String tag = icon.getName().substring(0,2);
			p.closeInventory();
			p.openInventory(new SpellAnalyticsMenu(tag,player).getInventory());
		}
		
	}

}
