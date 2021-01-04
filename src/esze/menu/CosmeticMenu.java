package esze.menu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import esze.analytics.solo.SaveEsze;
import esze.analytics.solo.SaveSelection;
import esze.main.main;
import esze.types.TypeSOLO;
import esze.utils.NBTUtils;
import esze.utils.PlayerConfig;
import esze.utils.PlayerUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

public class CosmeticMenu extends ItemMenu{

	boolean used = false;
	private ArrayList<Spell> spells;
	
	public CosmeticMenu(Player p) {
		super(5,"Kosmetik");
		
		
		
			
			String name = "§eSkin auswählen";
			String prefix = "§e";
			addClickableItem(1, 1, Material.WOODEN_SWORD,prefix+"Holzschwert");
			//addClickableItem(1, 2, Material.WOODEN_AXE,prefix+"Holzaxt");
			addClickableItem(1, 3, Material.WOODEN_PICKAXE,prefix+"Holzspitzhacke");
			addClickableItem(1, 4, Material.WOODEN_HOE,prefix+"Holzhacke");
			addClickableItem(1, 5, Material.WOODEN_SHOVEL,prefix+"Holzschaufel");
			
			addClickableItem(2, 1, Material.STONE_SWORD,prefix+"Steinschwert");
			addClickableItem(2, 2, Material.STONE_AXE,prefix+"Steinaxt");
			addClickableItem(2, 3, Material.STONE_PICKAXE,prefix+"Steinspitzhacke");
			addClickableItem(2, 4, Material.STONE_HOE,prefix+"Steinhacke");
			addClickableItem(2, 5, Material.STONE_SHOVEL,prefix+"Steinschaufel");
			
			addClickableItem(3, 1, Material.IRON_SWORD,prefix+"Eisenschwert");
			addClickableItem(3, 2, Material.IRON_AXE,prefix+"Eisenaxt");
			addClickableItem(3, 3, Material.IRON_PICKAXE,prefix+"Eisenspitzhacke");
			addClickableItem(3, 4, Material.IRON_HOE,prefix+"Eisenhacke");
			addClickableItem(3, 5, Material.IRON_SHOVEL,prefix+"Eisenschaufel");
			
			addClickableItem(4, 1, Material.GOLDEN_SWORD,prefix+"Goldschwert");
			addClickableItem(4, 2, Material.GOLDEN_AXE,prefix+"Goldaxt");
			addClickableItem(4, 3, Material.GOLDEN_PICKAXE,prefix+"Goldspitzhacke");
			addClickableItem(4, 4, Material.GOLDEN_HOE,prefix+"Goldhacke");
			addClickableItem(4, 5, Material.GOLDEN_SHOVEL,prefix+"Goldschaufel");
			
			addClickableItem(5, 1, Material.DIAMOND_SWORD,prefix+"Diamantschwert");
			addClickableItem(5, 2, Material.DIAMOND_AXE,prefix+"Diamantaxt");
			addClickableItem(5, 3, Material.DIAMOND_PICKAXE,prefix+"Diamantspitzhacke");
			addClickableItem(5, 4, Material.DIAMOND_HOE,prefix+"Diamanthacke");
			addClickableItem(5, 5, Material.DIAMOND_SHOVEL,prefix+"Diamantschaufel");
			
			addClickableItem(6, 1, Material.STICK,prefix+"Stock","§7Es ist ein Stock");
			addClickableItem(6, 2, Material.BLAZE_ROD,prefix+"Lohenstab");
			addClickableItem(6, 3, Material.BONE,prefix+"Knochen");
			addClickableItem(6, 4, Material.BAMBOO,prefix+"Bambus");
			addClickableItem(6, 5, Material.CARROT_ON_A_STICK,prefix+"Karottenangel");
			
			Material m = PlayerConfig.getConfig(p).getWeapon();
			
			
			
			for (int i = 0;i<getInventory().getSize();i++) {
				ItemStack is = getInventory().getItem(i);
				if (is != null && is.getType() == m) {
					ItemMeta im = is.getItemMeta();
					im.addEnchant(Enchantment.DAMAGE_ALL,1, false);
					im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					is.setItemMeta(im);
				}
				getInventory().setItem(i, is);
			}
		
		
	}
	
	
	public String getItemName(Material m) {
		for (int i = 0;i<getInventory().getSize();i++) {
			ItemStack is = getInventory().getItem(i);
			if (is != null && is.getType() == m) {
				return is.getItemMeta().getDisplayName();
			}
			
		}
		
		return "";
	}
	
	@Override
	public void clicked(ItemMenuIcon icon, Player p) {

	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
		// TODO Auto-generated method stub
		Material m = icon.getType();

		for (int i = 0;i<getInventory().getSize();i++) {
			ItemStack is = getInventory().getItem(i);
			if (is != null && is.getType() == m) {
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
		
		PlayerConfig.getConfig(p).setWeapon(icon.getType());
	}

	

}
