package esze.analytics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.Bukkit;

import esze.enums.GameType;
import esze.enums.GameType.TypeEnum;
import esze.main.main;

public class SaveUtils {

	private static int currentGame = 1;
	private static Connection currentConnection = null;
	private static GameType.TypeEnum currentType = null;
	private static AnalyticsInterface analytics = null;
	public static final String DB_NAME = "EszeData";
	private static final String URL = "jdbc:mysql://37.187.220.26:3306/EszeData";
	private static final String USER = "j_user";
	private static String PASSWORD = "";

	private static void startGame() {
		currentGame = executeSQLFunction("addGame()");
	}

	public static void startSoloGame(ArrayList<String> players) {

		Bukkit.getScheduler().runTaskAsynchronously(main.plugin, new Runnable() {
			@Override
			public void run() {
				currentType = TypeEnum.SOLO;
				System.out.println("Start Game");

				startGame();

				System.out.println("Started Game: " + currentGame);

				for (String player : players) {
					executeSQLProcedure("addSoloPlayer(" + format(currentGame) + ", " + format(player) + ")");
				}

			}
		});
	}

	public static void startTeamGame(ArrayList<ArrayList<String>> players) {

		Bukkit.getScheduler().runTaskAsynchronously(main.plugin, new Runnable() {
			@Override
			public void run() {
				currentType = TypeEnum.TEAMS;
				startGame();
				for (ArrayList<String> team : players) {
					if (!team.isEmpty()) {
						int teamid = executeSQLFunction("addTeam(" + currentGame + ")");
						for (String player : team) {
							executeSQLProcedure("addPlayerToTeam(" + format(teamid) + ", " + format(player) + ")");
						}
					}
				}
			}
		});
	}

	public static void reload() {
		stopConnection();
	}

	public static void endGame() {
		currentGame = 0;
		currentType = null;
	}

	public static void setPlayerPlace(String name, int place) {
		Bukkit.getScheduler().runTaskAsynchronously(main.plugin, new Runnable() {
			@Override
			public void run() {
				if (currentType == TypeEnum.SOLO) {
					executeSQLProcedure(
							"setSoloPlace(" + format(currentGame) + ", " + format(name) + ", " + format(place) + ")");
				} else if (currentType == TypeEnum.TEAMS) {
					executeSQLProcedure(
							"setTeamPlace(" + format(currentGame) + ", " + format(name) + ", " + format(place) + ")");
				}
			}
		});
	}

	public static void reset() {

	}

	public static void addPlayerDeath(String dead_p, String killer_p, boolean void_death, String spell) {
		Bukkit.getScheduler().runTaskAsynchronously(main.plugin, new Runnable() {
			@Override
			public void run() {
				if (currentType == TypeEnum.SOLO) {
					executeSQLProcedure("addSoloDeath(" + format(currentGame) + ", " + format(dead_p) + ", "
							+ format(killer_p) + ", " + format(void_death) + ", " + format(spell) + ")");
				} else if (currentType == TypeEnum.TEAMS) {
					executeSQLProcedure("addTeamDeath(" + format(currentGame) + ", " + format(dead_p) + ", "
							+ format(killer_p) + ", " + format(void_death) + ", " + format(spell) + ")");
				}
			}
		});
	}

	public static void addPlayerSelection(String name, String choosen_Spell, boolean refined,
			ArrayList<String> spells) {
		// SQL addSelection

		Bukkit.getScheduler().runTaskAsynchronously(main.plugin, new Runnable() {
			@Override
			public void run() {
				int id = executeSQLFunction(
						"addSelection(" + format(name) + ", " + format(choosen_Spell) + ", " + format(refined) + ")");

				if (currentType == TypeEnum.SOLO) {
					executeSQLProcedure("addSoloSelection(" + format(currentGame) + ", " + format(id) + ")");
				} else if (currentType == TypeEnum.TEAMS) {
					executeSQLProcedure("addTeamSelection(" + format(currentGame) + ", " + format(id) + ")");
				}
				// SQL LOOP
				for (String spell : spells) {
					executeSQLProcedure("addSelectionSpell(" + format(id) + ", " + format(spell) + ")");
				}
			}
		});

	}

	public static AnalyticsInterface getAnalytics() {
		return analytics;
	}

	public static void setPassword(String pass) {
		PASSWORD = pass;
	}

	public static void checkConnection() {
		try {
			if (analytics == null) {
				analytics = new AnalyticsInterface();
			}
			if (currentConnection == null || currentConnection.isClosed()) {
				System.out.println("Init SQL connection");
				currentConnection = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("Init SQL connection no error!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void checkConnection(Connection c) {
		try {
			if (c == null || c.isClosed()) {

				Class.forName("com.mysql.jdbc.Driver");
				c = DriverManager.getConnection(URL, USER, PASSWORD);
			}
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void stopConnection() {
		try {
			if (currentConnection != null && !currentConnection.isClosed()) {

				currentConnection.close();
			}
		} catch (SQLException e) {
		}
		
	}

	public static String format(String s) {
		return "\'" + rmColor(s) + "\'";
	}

	public static String format(boolean s) {
		return "" + s;
	}

	public static String format(int s) {
		return "\'" + s + "\'";
	}

	private static int executeSQLFunction(String function) {
		checkConnection();

		try {
			Statement stmt = currentConnection.createStatement();
			String query = "select " + DB_NAME + "." + function + ";";

			System.out.println("Exe func: " + query);
			ResultSet rs = stmt.executeQuery(query);

			int out = 0;
			if (rs.next()) {
				out = rs.getInt(rs.getMetaData().getColumnName(1));
			}
			rs.close();
			stmt.close();
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 1;
	}

	private static void executeSQLProcedure(String function) {
		checkConnection();

		try {
			Statement stmt = currentConnection.createStatement();
			String query = "call " + DB_NAME + "." + function + ";";
			System.out.println("Exe proc: " + query);
			stmt.executeQuery(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static String rmColor(String s) {
		for (String tag : main.colorTags) {
			s = s.replace(tag, "");
		}
		return s;
	}
}
