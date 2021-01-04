package esze.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class AnalyticsInterface {
	
	private static final String PLAYER = "player_name";
	private static final String SPELL = "spell_name";
	
	private static final String PLAYERWINS = "select * from SoloWins";
	private static final String PLAYERLOSSES = "select * from SoloLosses";
	private static final String PLAYERKILLS = "select * from PlayerKills";
	private static final String PLAYERDEATHS = "select * from PlayerDeaths";
	
	private static final String SPELLKILLS = "select * from SpellKills";
	private static final String SPELLKILLSNORMAL = "select * from SpellKillsNormal";
	private static final String SPELLKILLSVOID = "select * from SpellKillsVoid";
	
	private HashMap<String, Integer> playerWins;
	private HashMap<String, Integer> playerLosses;
	private HashMap<String, Integer> playerKills;
	private HashMap<String, Integer> playerDeaths;
	
	private HashMap<String, Integer> spellKills;
	private HashMap<String, Integer> spellKillsNormal;
	private HashMap<String, Integer> spellKillsVoid;
	
	public AnalyticsInterface() {
		
	}


	public int getPlayerVictories(String s) {
		if (playerWins == null || playerWins.get(s) == null) {
			return 0;
		}
		return playerWins.get(s);
	}


	public int getPlayerLosses(String s) {
		if (playerLosses == null || playerLosses.get(s) == null) {
			return 0;
		}
		return playerLosses.get(s);
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
		// TODO Auto-generated method stub
		return 0;
	}


	public int getSpellKillsNormal(String p, String spell) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getSpellKillsVoid(String p, String spell) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getSpellDeaths(String p, String rmColor) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSpellDeathsNormal(String p, String spell) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getSpellDeathsVoid(String p, String spell) {
		// TODO Auto-generated method stub
		return 0;
	}


	public double getSpellWorth(String p, String spell) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getSpellKills(String spell) {
		if (spellKills == null || spellKills.get(spell) == null) {
			return 0;
		}
		return spellKills.get(spell);
	}
	
	public int getSpellKillsNormal(String spell) {
		if (spellKillsNormal == null || spellKillsNormal.get(spell) == null) {
			return 0;
		}
		return spellKillsNormal.get(spell);
	}
	
	public int getSpellKillsVoid(String spell) {
		if (spellKillsNormal == null || spellKillsNormal.get(spell) == null) {
			return 0;
		}
		return spellKillsNormal.get(spell);
	}


	public double getWorth(String spell) {
		// TODO Auto-generated method stub
		return 0;
	}


	public double getEnhancedSpellWorth(String p, String spell) {
		// TODO Auto-generated method stub
		return 0;
	}


	public double getEnhancedWorth(String spell) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	//SetInfo
	public void update(Connection con) {
		try {
			Statement stmt = con.createStatement();
			setPlayerWins(stmt.executeQuery(PLAYERWINS));
			setPlayerLosses(stmt.executeQuery(PLAYERLOSSES));
			setPlayerKills(stmt.executeQuery(PLAYERKILLS));
			setPlayerDeaths(stmt.executeQuery(PLAYERDEATHS));
			
			setSpellKills(stmt.executeQuery(SPELLKILLS));
			setSpellKillsNormal(stmt.executeQuery(SPELLKILLSNORMAL));
			setSpellKillsVoid(stmt.executeQuery(SPELLKILLSVOID));
			
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
	
}
