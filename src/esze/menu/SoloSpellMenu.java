package esze.menu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import esze.analytics.solo.SaveEsze;
import esze.analytics.solo.SaveSelection;
import esze.analytics.solo.SaveUtils;
import esze.main.main;
import esze.types.TypeSOLO;
import esze.utils.NBTUtils;
import esze.utils.PlayerUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

public class SoloSpellMenu extends ItemMenu{

	boolean used = false;
	private ArrayList<Spell> spells;
	
	public SoloSpellMenu() {
		super(1,"spellmenu");
		spells = SpellList.getDiffrentRandom(4);
		addClickableItem(2, 1, Material.ENCHANTED_BOOK, spells.get(0).getName());
		addClickableItem(4, 1, Material.ENCHANTED_BOOK, spells.get(1).getName());
		addClickableItem(6, 1, Material.ENCHANTED_BOOK, spells.get(2).getName());
		addClickableItem(8, 1, Material.ENCHANTED_BOOK, spells.get(3).getName());
	}
	
	public SoloSpellMenu(boolean green) {
		super(1,"spellmenu");
		spells = SpellList.getDiffrentRandomGreen(4);
		addClickableItem(2, 1, Material.ENCHANTED_BOOK, "§2"+ spells.get(0).getName().substring(2,spells.get(0).getName().length()));
		addClickableItem(4, 1, Material.ENCHANTED_BOOK, "§2"+ spells.get(1).getName().substring(2,spells.get(1).getName().length()));
		addClickableItem(6, 1, Material.ENCHANTED_BOOK,"§2"+ spells.get(2).getName().substring(2,spells.get(2).getName().length()));
		addClickableItem(8, 1, Material.ENCHANTED_BOOK, "§2"+ spells.get(3).getName().substring(2,spells.get(3).getName().length()));
	}
	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		if (used)
			return;
		
		used = true;
		p.closeInventory();
		ItemStack is = NBTUtils.setNBT("Spell", "true", icon);
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
		
		SaveUtils.addPlayerSelection(p.getName(), new SaveSelection(icon.getName(), spells.get(0).getName(),
				spells.get(1).getName(), spells.get(2).getName(), spells.get(3).getName())); //Analytics
	}

	

}
