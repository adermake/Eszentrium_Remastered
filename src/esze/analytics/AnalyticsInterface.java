package esze.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.javafx.collections.SetListenerHelper;

import spells.spellcore.Spell;
import spells.spellcore.SpellList;

public class AnalyticsInterface {
	
	private static final String PLAYER = "player_name";
	private static final String SPELL = "spell_name";
	
	private static final String PLAYERLIST = "select * from Players";
	
	private static final String PLAYERWINS = "select * from SoloWins";
	private static final String PLAYERLOSSES = "select * from SoloLosses";
	private static final String PLAYERTEAMWINS = "select * from TeamWins";
	private static final String PLAYERTEAMLOSSES = "select * from TeamLosses";
	private static final String PLAYERKILLS = "select * from PlayerKills";
	private static final String PLAYERDEATHS = "select * from PlayerDeaths";
	
	private static final String SPELLKILLS = "select * from SpellKills";
	private static final String SPELLKILLSNORMAL = "select * from SpellKillsNormal";
	private static final String SPELLKILLSVOID = "select * from SpellKillsVoid";
	private static final String SPELLAPPERANCE = "select * from AbsoluteSpellApperance";
	private static final String SPELLPICK = "select * from AbsoluteSpellPick";
	private static final String SPELLUSE = "select * from AbsoluteSpellUse";
	
	private static final String SPELLLORE = "select * from SpellLore";
	
	private static final String SPELLKILLSPLAYER = "call getSpellKills(";
	private static final String SPELLKILLSNORMALPLAYER = "call getSpellKillsNormal(";
	private static final String SPELLKILLSVOIDPLAYER = "call getSpellKillsVoid(";
	
	private static final String SPELLDEATHSPLAYER = "call getSpellDeaths(";
	private static final String SPELLDEATHSNORMALPLAYER = "call getSpellDeathsNormal(";
	private static final String SPELLDEATHSVOIDPLAYER = "call getSpellDeathsVoid(";
	
	private static final String SPELLAPPERANCEPLAYER = "call getSpellAppearances(";
	private static final String SPELLPICKPLAYER = "call getSpellPick(";
	private static final String SPELLUSEPLAYER = "call getSpellUse(";
	
	private static final String ENDCALLQUERY = ")";
	private ArrayList<String> players;
	
	private HashMap<String, Integer> playerWins;
	private HashMap<String, Integer> playerLosses;
	private HashMap<String, Integer> playerTeamWins;
	private HashMap<String, Integer> playerTeamLosses;
	private HashMap<String, Integer> playerKills;
	private HashMap<String, Integer> playerDeaths;
	
	private HashMap<String, Integer> spellKills;
	private HashMap<String, Integer> spellKillsNormal;
	private HashMap<String, Integer> spellKillsVoid;
	private HashMap<String, Integer> spellApperances;
	private HashMap<String, Integer> spellPicks;
	private HashMap<String, Integer> spellUse;
	
	private HashMap<String, String> spellLore;
	private HashMap<String, String> spellRefinedLore;
	
	private HashMap<String, HashMap<String, Integer>> spellKillsPlayer = new HashMap<>();
	private HashMap<String, HashMap<String, Integer>> spellKillsNormalPlayer = new HashMap<>();
	private HashMap<String, HashMap<String, Integer>> spellKillsVoidPlayer = new HashMap<>();
	
	private HashMap<String, HashMap<String, Integer>> spellDeathsPlayer = new HashMap<>();
	private HashMap<String, HashMap<String, Integer>> spellDeathsNormalPlayer = new HashMap<>();
	private HashMap<String, HashMap<String, Integer>> spellDeathsVoidPlayer = new HashMap<>();
	
	private HashMap<String, HashMap<String, Integer>> spellApperancesPlayer = new HashMap<>();
	private HashMap<String, HashMap<String, Integer>> spellPicksPlayer = new HashMap<>();
	private HashMap<String, HashMap<String, Integer>> spellUsePlayer = new HashMap<>();
	
	public AnalyticsInterface() {
		
	}


	public int getPlayerVictories(String s) {
		if (playerWins == null || playerWins.get(s) == null || playerTeamWins == null || playerTeamWins.get(s) == null) {
			return 0;
		}
		if (playerWins == null || playerWins.get(s) == null) {
			return playerTeamWins.get(s);
		}
		if (playerTeamWins == null || playerTeamWins.get(s) == null) {
			return playerWins.get(s);
		}
		return playerWins.get(s) + playerTeamWins.get(s);
	}


	public int getPlayerLosses(String s) {
		if (playerLosses == null || playerLosses.get(s) == null || playerTeamLosses == null || playerTeamLosses.get(s) == null) {
			return 0;
		}
		if (playerLosses == null || playerLosses.get(s) == null) {
			return playerTeamLosses.get(s);
		}
		if (playerTeamLosses == null || playerTeamLosses.get(s) == null) {
			return playerLosses.get(s);
		}
		return playerLosses.get(s) + playerTeamLosses.get(s);
	}


	public int getPlayerKills(String s) {
		if (playerKills == null || playerKills.get(s) == null) {
			return 0;
		}
		return playerKills.get(s);
	}


	public int getPlayerDeaths(String s) {
		if (playerDeaths == null || playerDeaths.get(s) == null) {
			return 0;
		}
		return playerDeaths.get(s);
	}


	public int getSpellKills(String p, String spell) {
		if (spellKillsPlayer == null || spellKillsPlayer.get(p) == null || spellKillsPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		
		return spellKillsPlayer.get(p).get(SaveUtils.rmColor(spell));
	}


	public int getSpellKillsNormal(String p, String spell) {
		if (spellKillsNormalPlayer == null || spellKillsNormalPlayer.get(p) == null || spellKillsNormalPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		
		return spellKillsNormalPlayer.get(p).get(SaveUtils.rmColor(spell));
	}


	public int getSpellKillsVoid(String p, String spell) {
		if (spellKillsVoidPlayer == null || spellKillsVoidPlayer.get(p) == null || spellKillsVoidPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		
		return spellKillsVoidPlayer.get(p).get(SaveUtils.rmColor(spell));
	}
	
	public int getSpellDeaths(String p, String spell) {
		if (spellDeathsPlayer == null || spellDeathsPlayer.get(p) == null || spellDeathsPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		
		return spellDeathsPlayer.get(p).get(SaveUtils.rmColor(spell));
	}

	public int getSpellDeathsNormal(String p, String spell) {
		if (spellDeathsNormalPlayer == null || spellDeathsNormalPlayer.get(p) == null || spellDeathsNormalPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		
		return spellDeathsNormalPlayer.get(p).get(SaveUtils.rmColor(spell));
	}


	public int getSpellDeathsVoid(String p, String spell) {
		if (spellDeathsVoidPlayer == null || spellDeathsVoidPlayer.get(p) == null || spellDeathsVoidPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		
		return spellDeathsVoidPlayer.get(p).get(SaveUtils.rmColor(spell));
	}


	public double getSpellWorth(String p, String spell) {
		if (spellApperancesPlayer == null || spellApperancesPlayer.get(p) == null || spellApperancesPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		if (spellPicksPlayer == null || spellPicksPlayer.get(p) == null || spellPicksPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return ( ((double) spellPicksPlayer.get(p).get(SaveUtils.rmColor(spell))) / ((double) spellApperancesPlayer.get(p).get(SaveUtils.rmColor(spell))) )* 100D;
	}
	
	public double getWorthOffensive(String p, String spell) {
		if (spellKillsPlayer == null || spellKillsPlayer.get(p) == null || spellKillsPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		if (spellPicksPlayer == null || spellPicksPlayer.get(p) == null || spellPicksPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return ( ((double) spellKillsPlayer.get(p).get(SaveUtils.rmColor(spell))) / ((double) spellPicksPlayer.get(p).get(SaveUtils.rmColor(spell))) );
	}

	public double getUseWorth(String p, String spell) {
		if (spellKillsPlayer == null || spellKillsPlayer.get(p) == null || spellKillsPlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		if (spellUsePlayer == null || spellUsePlayer.get(p) == null || spellUsePlayer.get(p).get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return ( ((double) spellKillsPlayer.get(p).get(SaveUtils.rmColor(spell))) / ((double) spellUsePlayer.get(p).get(SaveUtils.rmColor(spell))));
	}

	public int getSpellKills(String spell) {
		if (spellKills == null || spellKills.get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return spellKills.get(SaveUtils.rmColor(spell));
	}
	
	public int getSpellKillsNormal(String spell) {
		if (spellKillsNormal == null || spellKillsNormal.get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return spellKillsNormal.get(spell);
	}
	
	public int getSpellKillsVoid(String spell) {
		if (spellKillsVoid == null || spellKillsVoid.get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return spellKillsVoid.get(SaveUtils.rmColor(spell));
	}
	
	public String getSpellLore(String spell) {
		if (spellLore == null || spellLore.get(SaveUtils.rmColor(spell)) == null) {
			return "";
		}
		return spellLore.get(SaveUtils.rmColor(spell));
	}
	
	public String getSpellRefinedLore(String spell) {
		if (spellRefinedLore == null || spellRefinedLore.get(SaveUtils.rmColor(spell)) == null) {
			return "";
		}
		return spellRefinedLore.get(SaveUtils.rmColor(spell));
	}


	public double getWorth(String spell) {
		if (spellApperances == null || spellApperances.get(SaveUtils.rmColor(spell)) == null || spellPicks == null || spellPicks.get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return ( ((double) spellPicks.get(SaveUtils.rmColor(spell))) / ((double) spellApperances.get(SaveUtils.rmColor(spell))) ) * 100D;
	}
	
	public double getWorthOffensive(String spell) {
		if (spellKills == null || spellKills.get(SaveUtils.rmColor(spell)) == null || spellPicks == null || spellPicks.get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return (  ((double) spellKills.get(SaveUtils.rmColor(spell))) / ((double) spellPicks.get(SaveUtils.rmColor(spell))) );
	}

	public double getUseWorth(String spell) {
		if (spellKills == null || spellKills.get(SaveUtils.rmColor(spell)) == null || spellUse == null || spellUse.get(SaveUtils.rmColor(spell)) == null) {
			return 0;
		}
		return (  ((double) spellKills.get(SaveUtils.rmColor(spell))) / ((double) spellUse.get(SaveUtils.rmColor(spell))));
	}
	/*
	public double getEnhancedSpellWorth(String p, String spell) {
		// TODO Auto-generated method stub
		return 0;
	}


	public double getEnhancedWorth(String spell) {
		// TODO Auto-generated method stub
		return 0;
	}
	*/
	
	//SetInfo
	public void update(Connection con) {
		try {
			Statement stmt = con.createStatement();
			setPlayerWins(stmt.executeQuery(PLAYERWINS));
			setPlayerLosses(stmt.executeQuery(PLAYERLOSSES));
			setPlayerKills(stmt.executeQuery(PLAYERKILLS));
			setPlayerDeaths(stmt.executeQuery(PLAYERDEATHS));

			setPlayerTeamWins(stmt.executeQuery(PLAYERTEAMWINS));
			setPlayerTeamLosses(stmt.executeQuery(PLAYERTEAMLOSSES));
			
			setSpellKills(stmt.executeQuery(SPELLKILLS));
			setSpellKillsNormal(stmt.executeQuery(SPELLKILLSNORMAL));
			setSpellKillsVoid(stmt.executeQuery(SPELLKILLSVOID));
			setSpellApperances(stmt.executeQuery(SPELLAPPERANCE));
			setSpellPicks(stmt.executeQuery(SPELLPICK));
			setSpellUse(stmt.executeQuery(SPELLUSE));
			
			setSpellLore(stmt.executeQuery(SPELLLORE));
			
			setPlayers(stmt.executeQuery(PLAYERLIST));
			
			for (String p : players) {
				setSpellKills(p, stmt.executeQuery(SPELLKILLSPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
				setSpellKillsNormal(p, stmt.executeQuery(SPELLKILLSNORMALPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
				setSpellKillsVoid(p, stmt.executeQuery(SPELLKILLSVOIDPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
				
				setSpellDeaths(p, stmt.executeQuery(SPELLDEATHSPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
				setSpellDeathsNormal(p, stmt.executeQuery(SPELLDEATHSNORMALPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
				setSpellDeathsVoid(p, stmt.executeQuery(SPELLDEATHSVOIDPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
				
				setSpellAppreances(p, stmt.executeQuery(SPELLAPPERANCEPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
				setSpellPicks(p, stmt.executeQuery(SPELLPICKPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
				setSpellUse(p, stmt.executeQuery(SPELLUSEPLAYER + SaveUtils.format(p) + ENDCALLQUERY));
			}
			
			for (Spell spell : SpellList.spells.keySet()) {
				spell.updateLore();
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();}
	}
	
	public void setPlayerWins(ResultSet rs) {
		String out = "Victories";
		playerWins = new HashMap<>();
		try {
			while (rs.next()) {
				playerWins.put(rs.getString(PLAYER), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setPlayerLosses(ResultSet rs) {
		String out = "Losses";
		playerLosses = new HashMap<>();
		try {
			while (rs.next()) {
				playerLosses.put(rs.getString(PLAYER), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setPlayerTeamWins(ResultSet rs) {
		String out = "Victories";
		playerTeamWins = new HashMap<>();
		try {
			while (rs.next()) {
				playerTeamWins.put(rs.getString(PLAYER), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setPlayerTeamLosses(ResultSet rs) {
		String out = "Losses";
		playerTeamLosses = new HashMap<>();
		try {
			while (rs.next()) {
				playerTeamLosses.put(rs.getString(PLAYER), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setPlayerKills(ResultSet rs) {
		String out = "Kills";
		playerKills = new HashMap<>();
		try {
			while (rs.next()) {
				playerKills.put(rs.getString(PLAYER), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setPlayerDeaths(ResultSet rs) {
		String out = "Deaths";
		playerDeaths = new HashMap<>();
		try {
			while (rs.next()) {
				playerDeaths.put(rs.getString(PLAYER), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}

	public void setSpellKills(ResultSet rs) {
		String out = "Kills";
		spellKills = new HashMap<>();
		try {
			while (rs.next()) {
				spellKills.put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellKillsNormal(ResultSet rs) {
		String out = "Kills";
		spellKillsNormal = new HashMap<>();
		try {
			while (rs.next()) {
				spellKillsNormal.put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellKillsVoid(ResultSet rs) {
		String out = "Kills";
		spellKillsVoid = new HashMap<>();
		try {
			while (rs.next()) {
				spellKillsVoid.put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellApperances(ResultSet rs) {
		String out = "Appearances";
		spellApperances = new HashMap<>();
		try {
			while (rs.next()) {
				spellApperances.put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellPicks(ResultSet rs) {
		String out = "Pick";
		spellPicks = new HashMap<>();
		try {
			while (rs.next()) {
				spellPicks.put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellUse(ResultSet rs) {
		String out = "Count";
		spellUse = new HashMap<>();
		try {
			while (rs.next()) {
				spellUse.put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellLore(ResultSet rs) {
		String out1 = "lore";
		String out2 = "refined_lore";
		spellLore = new HashMap<>();
		spellRefinedLore = new HashMap<>();
		try {
			while (rs.next()) {
				spellLore.put(rs.getString(SPELL), rs.getString(out1));
				spellRefinedLore.put(rs.getString(SPELL), rs.getString(out2));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	
	
	public void setPlayers(ResultSet rs) {
		players = new ArrayList<>();
		try {
			while (rs.next()) {
				players.add(rs.getString(PLAYER));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellKills(String p, ResultSet rs) {
		String out = "Kills";
		spellKillsPlayer.remove(p);
		spellKillsPlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellKillsPlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellKillsNormal(String p, ResultSet rs) {
		String out = "Kills";
		spellKillsNormalPlayer.remove(p);
		spellKillsNormalPlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellKillsNormalPlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellKillsVoid(String p, ResultSet rs) {
		String out = "Kills";
		spellKillsVoidPlayer.remove(p);
		spellKillsVoidPlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellKillsVoidPlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellDeaths(String p, ResultSet rs) {
		String out = "Deaths";
		spellDeathsPlayer.remove(p);
		spellDeathsPlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellDeathsPlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellDeathsNormal(String p, ResultSet rs) {
		String out = "Deaths";
		spellDeathsNormalPlayer.remove(p);
		spellDeathsNormalPlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellDeathsNormalPlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellDeathsVoid(String p, ResultSet rs) {
		String out = "Deaths";
		spellDeathsVoidPlayer.remove(p);
		spellDeathsVoidPlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellDeathsVoidPlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellAppreances(String p, ResultSet rs) {
		String out = "Appearances";
		spellApperancesPlayer.remove(p);
		spellApperancesPlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellApperancesPlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellPicks(String p, ResultSet rs) {
		String out = "Pick";
		spellPicksPlayer.remove(p);
		spellPicksPlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellPicksPlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void setSpellUse(String p, ResultSet rs) {
		String out = "Count";
		spellUsePlayer.remove(p);
		spellUsePlayer.put(p, new HashMap<>());
		try {
			while (rs.next()) {
				spellUsePlayer.get(p).put(rs.getString(SPELL), rs.getInt(out));
			}
			rs.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
