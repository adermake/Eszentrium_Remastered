package esze.types;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import esze.utils.EszeTeam;

public abstract class TypeTeamBased extends Type {
	
	
	public ArrayList<EszeTeam> allTeams = new ArrayList<EszeTeam>();
	
	public void removePlayerFromAllTeams(Player p) {
		for (EszeTeam t : allTeams) {
			if (t.players.contains(p)) {
				t.removePlayer(p);
			}
		}
	}
	
	public boolean playerHasTeam(Player p) {
		boolean hasTeam = false;
		for (EszeTeam t : allTeams) {
			if (t.players.contains(p)) {
				hasTeam = true;
			}
		}
		return hasTeam;
		
	}
	
	public ArrayList<Player> getTeammates(Player p) {
		
		for (EszeTeam t : allTeams) {
			if (t.players.contains(p)) {
				return t.players;
			}
		}
		return null;
		
	}
}
