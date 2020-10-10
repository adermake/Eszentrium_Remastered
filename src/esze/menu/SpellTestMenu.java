package esze.menu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import esze.analytics.solo.SaveUtils;
import esze.utils.NBTUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

public class SpellTestMenu extends ItemMenu {
	public SpellTestMenu(String colorTag,String p,boolean better) {
		super(6,colorTag+"Spells");
		// TODO Auto-generated constructor stub
		
		int x = 1;
		int y = 1;
		for (Spell s : SpellList.getSortedSpellsAlphabetically()) {
			
			if (!s.getName().contains(colorTag)) {
				continue;
			}
			
			if (better) {
				
				addClickableItem(x, y, Material.BOOK, "§2"+s.getName().substring(2,s.getName().length()), s.getBetterLore());
			}
			else {
				addClickableItem(x, y, Material.BOOK, s.getName(), s.getLore());
			}
			
			x++;
			if (x>9) {
				y++;
				x=1;
			}
			
		}
		
		
	}
	
	public String cut(double d) {
		String s = "" + d;
		int max = 4;
		if (s.length() > max) {
			s = s.subSequence(0, max).toString();
		}
		return s;
	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		// TODO Auto-generated method stub
		ItemStack i = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(icon.getName());
		im.setLore(icon.getItemMeta().getLore());
		i.setItemMeta(im);
		i = NBTUtils.setNBT("Spell", "true", i);
        i = NBTUtils.setNBT("OriginalName", i.getItemMeta().getDisplayName(), i);
		p.getInventory().addItem(i);
		
	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
		// TODO Auto-generated method stub
		
	}
}
