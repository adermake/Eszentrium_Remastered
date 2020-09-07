package spells.spellcore;

import org.bukkit.entity.Player;

public class DamageCauseContainer {

	
	public Player killer;
	public String spellname;
	
	public DamageCauseContainer(Player killer,String spellname) {
		this.killer = killer;
		this.spellname = spellname;
	}
}
