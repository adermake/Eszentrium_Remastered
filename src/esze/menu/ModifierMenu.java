package esze.menu;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModifierMenu extends ItemMenu {

	private static ModifierMenu mod;
	public static ArrayList<GameModifier> modifier = new ArrayList<GameModifier>();
	

	public ModifierMenu() {
		super(2,"Modifikatoren");
		
		
		
			
		
			addClickableItem(1, 1, Material.DISPENSER,"§aSchnellfeuer");
			addClickableItem(2, 1, Material.ARROW,"§aDoppelschuss");
			addClickableItem(3, 1, Material.FEATHER,"§aGeschwindigkeit");
			addClickableItem(4, 1, Material.LEATHER_BOOTS,"§aFallschaden");
			addClickableItem(5, 1, Material.WRITABLE_BOOK,"§aZufallszauber");
			
			for (int i = 0;i<getInventory().getSize();i++) {
				ItemStack is = getInventory().getItem(i);
				if (is != null && is.hasItemMeta()) {
					
				
				GameModifier mod = GameModifier.valueOf(is.getItemMeta().getDisplayName().replace("§a", "").toUpperCase());
				if (modifier.contains(mod)) {
					ItemMeta im = is.getItemMeta();
					im.addEnchant(Enchantment.DAMAGE_ALL,1, false);
					im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					is.setItemMeta(im);
				}
				getInventory().setItem(i, is);
				}
			}
	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
		// TODO Auto-generated method stub
		
		String name = icon.getName();
		GameModifier mod = GameModifier.valueOf(icon.getItemMeta().getDisplayName().replace("§a", "").toUpperCase());
		if (modifier.contains(mod)) {
			modifier.remove(mod);
		}
		else {
			modifier.add(mod);
		}
		
		
		
		for (int i = 0;i<getInventory().getSize();i++) {
			ItemStack is = getInventory().getItem(i);
			if (is != null &&  modifier.contains(GameModifier.valueOf(is.getItemMeta().getDisplayName().replace("§a", "").toUpperCase()))) {
				ItemMeta im = is.getItemMeta();
				im.addEnchant(Enchantment.DAMAGE_ALL,1, false);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				is.setItemMeta(im);
			}
			else {
				if (is != null) {
					ItemMeta im = is.getItemMeta();
					im.removeEnchant(Enchantment.DAMAGE_ALL);
					is.setItemMeta(im);
					
				}
			}
			
			getInventory().setItem(i, is);
		}
		
	}
	public static boolean hasModifier(GameModifier gm) {
		return modifier.contains(gm);
	}
	
	public static ModifierMenu getModifierWindow() {	
		if (mod == null)
		mod = new ModifierMenu();
		return mod;
	}
	
}
