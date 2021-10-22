package esze.types;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import esze.utils.EszeTeam;
import esze.utils.ItemStackUtils;
import esze.utils.ScoreboardTeamUtils;
import monuments.Monument;
import spells.spellcore.SilenceSelection;
import spells.spellcore.Spell;

public class TypeMONUMENTE extends TypeTeamBased{

	Monument redNexus;
	Monument blueNexus;
	
	public TypeMONUMENTE() {
		name = "MONUMENTE";
		
		allTeams.add(new EszeTeam("§cRot", ChatColor.RED,Color.RED,Material.RED_WOOL));
		allTeams.add(new EszeTeam("§9Blau", ChatColor.BLUE,Color.BLUE,Material.BLUE_WOOL));
		
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			givePlayerLobbyItems(p);
		}
	}
	
	
	@Override
	public void runEverySecond() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runEveryTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameStart() {
		
		//redNexus = new Nexus() CONSTRUCT NEXUS
		
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}
	
	public void resendScorboardTeams(Player p) {
		
		
		for (EszeTeam team : allTeams) {
			for (Player pl : team.players) {
				ScoreboardTeamUtils.colorPlayer(pl, p,team.color);
			}
			
		}
	}
	
	public void swichPhase(State phase) {
		
		if (phase == State.BUILDPHASE) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				SilenceSelection silence = new SilenceSelection();
				Spell.silence(p, silence);
				p.setGameMode(GameMode.ADVENTURE);
			}
		}
		//--------------------------------------
		if (phase == State.BATTLEPHASE) {
			Spell.silenced.clear();	
		}
		
	}
	@Override
	public void givePlayerLobbyItems(Player p) {
		if (p.getGameMode().equals(GameMode.SURVIVAL)) {
			p.getInventory().clear();
		}
		if (!p.getName().equals("adermake") || p.getGameMode() != GameMode.CREATIVE) {
			if (p.isOp()) {
				p.getInventory().setItem(0, ItemStackUtils.createItemStack(Material.COMMAND_BLOCK, 1, 0, "§3Modifikatoren", null, true));
			}
		
		p.getInventory().setItem(8, ItemStackUtils.createItemStack(Material.MAP, 1, 0, "§3Map wählen", null, true));
		p.getInventory().setItem(7, ItemStackUtils.createItemStack(Material.ENDER_CHEST, 1, 0, "§3Spellsammlung", null, true));
		p.getInventory().setItem(6, ItemStackUtils.createItemStack(Material.DIAMOND, 1, 0, "§3Georg", null, true));
		p.getInventory().setItem(5, ItemStackUtils.createItemStack(Material.NETHER_STAR, 1, 0, "§3Kosmetik", null, true));
		p.getInventory().setItem(4, ItemStackUtils.createItemStack(Material.NAME_TAG, 1, 0, "§3Teamauswahl", null, true));
		
		resendScorboardTeams(p);
		}
	}
	
	enum State {
		BUILDPHASE,BATTLEPHASE,LASERPHASE
	};
	
}
