package esze.menu;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import esze.analytics.solo.SaveUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

public class SoloSelectionTopMenu extends ItemMenu{

	public SoloSelectionTopMenu(String p) {
		super(6,"" +"Top-Spells    ");
		// TODO Auto-generated constructor stub
		int amount = 0;
		
		int x = 1;
		int y = 1;
		for (Spell s : SpellList.getSortedSpells()) {
			if (amount >= 6*9) {
				continue;
			}
			ArrayList<String> lore = new ArrayList<String>();
			String number = "�e";
			String text = "�7";
			lore.add( text + "T�tungen: "  					+ number + ( (int) SaveUtils.getSaveEsze().getSpellKills(p, SaveUtils.rmColor(s.getName())) ) );
			lore.add( text + "SchadesT�tungen: "  					+ number + ( (int) SaveUtils.getSaveEsze().getSpellKillsNormal(p, SaveUtils.rmColor(s.getName())) ) );
			lore.add( text + "VoidT�tungen: "  					+ number + ( (int) SaveUtils.getSaveEsze().getSpellKillsVoid(p, SaveUtils.rmColor(s.getName())) ) );
			lore.add( text + "Tode: "  						+ number + ( (int) SaveUtils.getSaveEsze().getSpellDeaths(p, SaveUtils.rmColor(s.getName())) ) );
			lore.add( text + "SchadensTode: "  						+ number + ( (int) SaveUtils.getSaveEsze().getSpellDeathsNormal(p, SaveUtils.rmColor(s.getName())) ) );
			lore.add( text + "VoidTode: "  						+ number + ( (int) SaveUtils.getSaveEsze().getSpellDeathsVoid(p, SaveUtils.rmColor(s.getName())) ) );
			lore.add( text + "Deine Auswahlrate: "  		+ number + cut(SaveUtils.getSaveEsze().getSpellWorth(p, SaveUtils.rmColor(s.getName())) ) + "%" );
			lore.add( text + "Allgemeine T�tungen: "  					+ number + ( (int) SaveUtils.getSaveEsze().getSpellKills(SaveUtils.rmColor(s.getName())) ) );
			//lore.add( text + "Allgemeine Tode: "  						+ number + ( (int) SaveUtils.getSaveEsze().getSpellDeaths(SaveUtils.rmColor(s.getName())) ) );
			lore.add( text + "Allgemeine Auswahlrate: "  	+ number + cut(SaveUtils.getSaveEsze().getWorth(SaveUtils.rmColor(s.getName())) ) + "%" );
			addClickableItem(x, y, Material.BOOK, s.getName(), lore);
			x++;
			if (x>9) {
				y++;
				x=1;
			}
			
			amount++;
			
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
		
	}
}
