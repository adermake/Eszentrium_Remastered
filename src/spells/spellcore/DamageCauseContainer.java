package spells.spellcore;

import org.bukkit.entity.Player;

public class DamageCauseContainer {

	
	public Player killer;
	public String spellname;
	public boolean void_d = false;
	
	public DamageCauseContainer(Player killer,String spellname) {
		this.killer = killer;
		this.void_d = false;
		this.spellname = spellname;
	}
	
	public String getKiller() {
		if (killer == null) {
			return "";
		}
		return killer.getName();
	}
	
	public String getSpell() {
		if (spellname == null) {
			return "";
		}
		return spellname;
	}
	
	public void voidDamage() {
		void_d = true;
	}
}
