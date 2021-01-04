package esze.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnalyticsInterface {
	
	private Connection con;
	
	public AnalyticsInterface(Connection c) {
		con = c;
	}
	
	
	private int executeSQLFunction(String function) {
		SaveUtils.checkConnection(con);
		
		try {
			Statement stmt = con.createStatement();
			String query = "select " + SaveUtils.DB_NAME + "." + function + ";";
			ResultSet rs = stmt.executeQuery(query);
			
			//rs.next();
			
			int out = rs.getInt(SaveUtils.DB_NAME + "." + function);
			rs.close();
			stmt.close();
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	
	private void executeSQLProcedure(String function) {
		SaveUtils.checkConnection(con);
		
		try {
			Statement stmt = con.createStatement();
			String query = "select " + SaveUtils.DB_NAME + "." + function + ";";
			ResultSet rs = stmt.executeQuery(query);
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	public int getPlayerVictories(String s) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getPlayerLosses(String s) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getPlayerKills(String s) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getPlayerDeaths(String s) {
		// TODO Auto-generated method stub
		return 0;
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


	public int getSpellDeaths(String p, String spell) {
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
		// TODO Auto-generated method stub
		return 0;
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
}
