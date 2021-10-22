package esze.menu;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.NBTUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

public class TraitorshopMenu extends ItemMenu {

	public TraitorshopMenu() {
		super(1, "§cSchwarzmarkt");
		
		addClickableItem(1, 1, Material.SPECTRAL_ARROW, "§4Magmanadel","§a8 Punkte");
		addClickableItem(2, 1, Material.SHEEP_SPAWN_EGG, "§4Verzaubern","§a6 Punkte");
		addClickableItem(3, 1, Material.STRING, "§4Griff der sieben Winde","§a6 Punkte");
		addClickableItem(4, 1, Material.MELON_SLICE, "§4Miiilone","§a4 Punkte");
		addClickableItem(5, 1, Material.WRITABLE_BOOK, "§4Vorbereiten","§a4 Punkte");
	}

	
	/*
	public static int calcTraitorMenuSize() {
		int spellCount = SpellList.traitorSpells.size();
		int size = 0;
		
		while (size<spellCount) {
			size+=9;
		}
		return size;
	}
	*/
	
	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		
		if (icon.getName().equals("§4Magmanadel")) {
			buyItem(p,icon,8);
		}
		if (icon.getName().equals("§4Verzaubern")) {
			buyItem(p,icon,6);
		}
		if (icon.getName().equals("§4Griff der sieben Winde") ) {
			buyItem(p,icon,6);
		}
		if (icon.getName().equals("§4Miiilone")) {
			buyItem(p,icon,4);
		}
		if (icon.getName().equals("§4Vorbereiten")) {
			buyItem(p,icon,4);
		}
	}
	
	public void buyItem(Player p,ItemMenuIcon icon,int price) {
		if (p.getLevel()>= price) {
			
		
			ItemStack is= new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(icon.getName());
		
			is.setItemMeta(im);
			is = NBTUtils.setNBT("Spell", "true", is);
			p.getInventory().addItem(is);
			p.setLevel(p.getLevel()-price);
			p.playSound(p.getLocation(),Sound.BLOCK_BELL_USE, 1, 1.5F);
		}
		else {
			p.playSound(p.getLocation(),Sound.BLOCK_CHEST_LOCKED, 1, 0.1F);
			Material type = icon.getType();
			icon.setType(Material.BARRIER);
			
			new BukkitRunnable() {
				public void run() {
					icon.setType(type);
				}
			}.runTaskLater(main.plugin,5);
			
		}
	}


	@Override
	public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
		// TODO Auto-generated method stub
		
		
	}
		
	

}
