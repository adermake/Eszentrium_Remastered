package spells.spellcore;

import org.bukkit.entity.Player;

import esze.main.main;

public class DamageCauseContainer {

	
	public Player killer;
	public String spellname;
	public String swordname;
	public boolean spell_d = false;
	public boolean void_d = false;
	public boolean sword_d = false;
	
	public DamageCauseContainer(Player killer,String spellname) {
		
		this.spell_d = true;
		this.killer = killer;
		this.void_d = false;
		this.spellname = spellname;
	}
	
	public DamageCauseContainer(Player killer) {
		
		this.spell_d = false;
		this.killer = killer;
		this.void_d = false;
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
	
	public void swordDamage(Player p, String name) {
		if (p != killer) {
			killer = p;
			spellname = "";
			spell_d = false;
			void_d = false;
		}
		sword_d = true;
		swordname = name;
	}
	
	public static String toMessage(DamageCauseContainer d, String dead) {
		String color = "§7";
		String out = color;
		if (d == null) {
			out += dead + " starb!";
		} else
		if (d.killer == null) {
			if (d.void_d) {
				out += dead + " fiel ins Void!"; // Void
			} else {
				out += dead + " starb!";
			}
		} else if (d.sword_d && !d.void_d) {
			out += dead + " wurde durch " + d.getKiller() + " mit " + d.swordname + color + " getötet!"; // Cause+Player
		} else if (d.sword_d && d.void_d) {
			out += dead + " wurde durch " + d.getKiller() + " mit " + d.swordname + color + " ins Void geworfen!"; // Cause+Player+void
		} else if (d.spell_d && !d.sword_d && !d.void_d) {
			out += dead + " wurde durch " + d.getKiller() + " mit " + d.spellname + color + " getötet!"; // Cause+Player
		} else if (d.spell_d && !d.sword_d && d.void_d) {
			out += dead + " wurde durch " + d.getKiller() + " mit " + d.spellname + color + " ins Void geworfen!"; // Cause+Player+void
		} else {
			out += "No clue how that happend, but " + dead + color + " died!";
		}
		return out;
	}
}
