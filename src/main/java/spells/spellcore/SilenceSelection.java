package spells.spellcore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

public class SilenceSelection {

	ArrayList<SpellType> spellTypes = new ArrayList<SpellType>();
	SilenceFilterType sft = SilenceFilterType.ALL;
	
	public SilenceSelection() {
		
	}
	
	public SilenceSelection(SilenceFilterType sft) {
		this.sft = sft;
	}
	
	public void addFilter(SpellType s) {
		spellTypes.add(s);
	}
	
	public boolean filter(ArrayList<SpellType> as) {
		
		if (sft == SilenceFilterType.ALL) {
			return true;
		}
		
		if (sft == SilenceFilterType.AND) {
			for (SpellType s : spellTypes) {
				if (!as.contains(s)) {
					return false;
				}
			}
			
			
			return true;
		}
		if (sft == SilenceFilterType.OR) {
			for (SpellType s : spellTypes) {
				if (as.contains(s)) {
					return true;
				}
			}
			
			
			return false;
		}
		
		return false;
	}
	
}
