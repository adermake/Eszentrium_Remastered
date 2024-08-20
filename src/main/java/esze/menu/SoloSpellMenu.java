package esze.menu;

import java.util.ArrayList;

import javax.swing.Icon;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import esze.analytics.SaveUtils;
import esze.main.main;
import esze.types.TypeSOLO;
import esze.utils.NBTUtils;
import esze.utils.PlayerUtils;
import esze.utils.SpellKeyUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

public class SoloSpellMenu extends ItemMenu{

	boolean used = false;
	private ArrayList<Spell> spells;
	boolean refined = false;
	
	public SoloSpellMenu() {
		super(1,"spellmenu");
		refined = false;
		spells = SpellList.getDiffrentRandom(5);
		addClickableItem(1, 1, Material.ENCHANTED_BOOK, spells.get(0).getName(),spells.get(0).getLore());
		addClickableItem(3, 1, Material.ENCHANTED_BOOK, spells.get(1).getName(),spells.get(1).getLore());
		addClickableItem(5, 1, Material.ENCHANTED_BOOK, spells.get(2).getName(),spells.get(2).getLore());
		addClickableItem(7, 1, Material.ENCHANTED_BOOK, spells.get(3).getName(),spells.get(3).getLore());
		addClickableItem(9, 1, Material.ENCHANTED_BOOK, spells.get(4).getName(),spells.get(4).getLore());
	}
	
	public SoloSpellMenu(boolean green) {
		super(1,"spellmenu");
		refined = true;
		spells = SpellList.getDiffrentRandomGreen(5);
		addClickableItem(1, 1, Material.ENCHANTED_BOOK, "§2"+ spells.get(0).getName().substring(2,spells.get(0).getName().length()),spells.get(0).getBetterLore());
		addClickableItem(3, 1, Material.ENCHANTED_BOOK, "§2"+ spells.get(1).getName().substring(2,spells.get(1).getName().length()),spells.get(1).getBetterLore());
		addClickableItem(5, 1, Material.ENCHANTED_BOOK,"§2"+ spells.get(2).getName().substring(2,spells.get(2).getName().length()),spells.get(2).getBetterLore());
		addClickableItem(7, 1, Material.ENCHANTED_BOOK, "§2"+ spells.get(3).getName().substring(2,spells.get(3).getName().length()),spells.get(3).getBetterLore());
		addClickableItem(9, 1, Material.ENCHANTED_BOOK, "§2"+ spells.get(4).getName().substring(2,spells.get(4).getName().length()),spells.get(4).getBetterLore());
	}
	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		if (used)
			return;
		
		used = true;
		p.closeInventory();
		ItemStack is = NBTUtils.setNBT("Spell", "true", icon);
		is = NBTUtils.setNBT("SpellKey", ""+SpellKeyUtils.getNextSpellKey(), is);
		p.getInventory().addItem(is);
		p.setGameMode(GameMode.SURVIVAL);
		p.setNoDamageTicks(10);
		p.teleport(TypeSOLO.loc.get(p));
		PlayerUtils.snare(p, false);
		new BukkitRunnable() {
			public void run() {
				p.setVelocity(p.getVelocity().setY(1));
			}
		}.runTaskLater(main.plugin,1);
	
		PlayerUtils.showPlayer(p);
		
		ArrayList<String> spellnames = new ArrayList<>();
		for (Spell i : spells) {
			spellnames.add(i.getName());
		}
		
		SaveUtils.addPlayerSelection(p.getName(), icon.getName(),refined, spellnames); //Analytics
	}

	@Override
	public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
		// TODO Auto-generated method stub
		
	}

	

}
