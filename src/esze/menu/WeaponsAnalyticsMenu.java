package esze.menu;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import esze.analytics.solo.SaveUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;
import weapons.WeaponList;

public class WeaponsAnalyticsMenu extends ItemMenu{
	
	public WeaponsAnalyticsMenu(Player p) {
		super(4,"§rWeaponStats: " + p.getName());
		init(p.getName());
	}

	public WeaponsAnalyticsMenu(String string) {
		super(4,"§rWeaponStats: " + string);
		init(string);
	}

	public void init(String p) {
		int count = 1;
		
		String number = "§e";
		String text = "§7";
		
		for (String name : WeaponList.weapons.keySet()) {
			
			ArrayList<String> lore = new ArrayList<String>();
			lore.add( text + "Tötungen: "  					+ number + ( (int) SaveUtils.getSaveEsze().getSpellKills(p, SaveUtils.rmColor(name)) ) );
			lore.add( text + "Tode: "  						+ number + ( (int) SaveUtils.getSaveEsze().getSpellDeaths(p, SaveUtils.rmColor(name)) ) );
			
			/*lore.add( text + "Deine Auswahlrate: "  		+ number + cut(SaveUtils.getSaveEsze().getSpellWorth(p, SaveUtils.rmColor(s.getName())) ) + "%" );
			lore.add( text + "Allgemeine Auswahlrate: "  	+ number + cut(SaveUtils.getSaveEsze().getWorth(SaveUtils.rmColor(s.getName())) ) + "%" );*/
			addClickableItem(count, 1, WeaponList.weapons.get(name), name, lore);
			
			count++;
		}
		
		ArrayList<String> spellLore = new ArrayList<String>();
		
		
		int spellKills = 0;
		for (Spell s : SpellList.spells.keySet()) {
			spellKills += ( (int) SaveUtils.getSaveEsze().getSpellKills(p, SaveUtils.rmColor(s.getName())) );
		}
		
		int spellDeaths = 0;
		for (Spell s : SpellList.spells.keySet()) {
			spellDeaths += ( (int) SaveUtils.getSaveEsze().getSpellDeaths(p, SaveUtils.rmColor(s.getName()) ));
		}
		
		spellLore.add( text + "Tötungen: "  					+ number +  spellKills);
		spellLore.add( text + "Tode: "  						+ number +  spellDeaths);
		
		count++;
		
		addClickableItem(count, 1, Material.BOOK, number + "Spells", spellLore);
		
	}
	
	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		// TODO Auto-generated method stub
		
	}

}
