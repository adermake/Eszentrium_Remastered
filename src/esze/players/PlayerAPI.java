package esze.players;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class PlayerAPI {
	
	private static ArrayList<PlayerInfo> playerInfos = new ArrayList<PlayerInfo>();
	
	/**
	 * Gets player info by bukkit player
	 * @param player bukkit player
	 * @return player info
	 */
	public static PlayerInfo getPlayerInfo(Player player){
		for(PlayerInfo pi : playerInfos){
			if(pi.uuid.equals(player.getUniqueId().toString())){
				return pi;
			}
		}
		
		if(addPlayer(player)){
			return getPlayerInfo(player);
		}
		return null;
	}
	
	/**
	 * Adds a player by bukkit player object
	 * @param player bukkit player object
	 * @return false if already exists
	 */
	public static boolean addPlayer(Player player){
		for(PlayerInfo pi : playerInfos){
			if(pi.uuid.equals(player.getUniqueId().toString())){
				return false;
			}
		}
		playerInfos.add(new PlayerInfo(player));
		return true;
	}
	
	
	/**
	 * Adds player info
	 * @param playerInfo
	 */
	public static void addPlayerInfo(PlayerInfo playerInfo){
		playerInfos.add(playerInfo);
	}
	
	/**
	 * Removes player info
	 * @param playerInfo
	 */
	public static void removePlayerInfo(PlayerInfo playerInfo){
		playerInfos.remove(playerInfo);
	}
	
	/**
	 * Removes player info by bukkit player object
	 * @param player
	 * @return true if has contained
	 */
	public static boolean removePlayer(Player player){
		for(PlayerInfo pi : (ArrayList<PlayerInfo>)playerInfos.clone()){
			if(pi.uuid.equals(player.getUniqueId().toString())){
				playerInfos.remove(pi);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of player infos
	 */
	public static ArrayList<PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}
	
	/**
	 * removes all data about players
	 */
	public static void clearPlayerInfos() {
		playerInfos.clear();
	}

}
