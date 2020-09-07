package esze.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import easyscoreboards.ScoreboardUtil;
import esze.app.AppClientSocket;
import esze.enums.GameType;
import esze.listeners.Reconnect;

public class LobbyBackgroundRunnable {
	
	private static int runnableID;
	
	public static void start(){
		Reconnect.disconnected.clear();
		runnableID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {
		
			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()){
					ArrayList<String> board = new ArrayList<String>();
					
					board.add("§2Spielinfos");
					board.add(" ");
					board.add("§a§lSpielmodus:");
					board.add(GameType.getType().name);
					board.add("  ");
					board.add("§a§lSpieler:");
					board.add(Bukkit.getOnlinePlayers().size()+"/"+Bukkit.getMaxPlayers());
					board.add("   ");
					board.add("§a§lApp:");
					
					boolean usesApp = false;
					for(AppClientSocket socket : main.plugin.appServer.clientSockets) {
						if(socket.getIdentity() != null && socket.getIdentity().getUsername().equalsIgnoreCase(p.getName())) {
							usesApp = true;
							break;
						}
					}
					
					board.add(usesApp ? "VERBUNDEN" : "OFFLINE");
					
					
					String[] array = new String[board.size()];
					int i=0;
					for(String s : board){
						array[i++] = s;
					}
					
					ScoreboardUtil.unrankedSidebarDisplay(p, array);
					
				}
				
			}
		}, 0, 20);
	}
	
	public static void stop(){
		Bukkit.getScheduler().cancelTask(runnableID);
	}
	
}
