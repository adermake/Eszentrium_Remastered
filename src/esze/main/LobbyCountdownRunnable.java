package esze.main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.map.MapSelect;
import esze.menu.GameModifier;
import esze.menu.ModifierMenu;
import esze.players.PlayerAPI;
import esze.utils.Actionbar;
import weapons.WeaponAbilitys;

public class LobbyCountdownRunnable {
	
	private static int timeLeft = 15;
	private static int runnableID;
	private static boolean running = false;
	public static void start(){
		if (Gamestate.getGameState()== Gamestate.INGAME)
			return;
		if (running) {
			
			timeLeft = 1;
			
			return;
		}
		running = true;
		timeLeft = 15;
		final int timeDefault = timeLeft;
		
		runnableID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				String timeShow = calculatePercent(15, timeDefault, timeLeft);
				new Actionbar(timeShow).sendAll();
				
				
				
				if(timeLeft <= 0){
					for(Player p : Bukkit.getOnlinePlayers()){
						PlayerAPI.addPlayer(p);
					}
					Gamestate.setGameState(Gamestate.INGAME);
					new Actionbar("").sendAll();
					WeaponAbilitys.lastLaunched.clear();
					
					if (ModifierMenu.modifier.size() >0) {
						Bukkit.broadcastMessage("§e[Modifikatoren]:");
						for (GameModifier gm : ModifierMenu.modifier) {
							Bukkit.broadcastMessage("§7-> "+gm.toString());
						}
					}
					timeLeft = timeDefault;
					GameType.getType().players.clear();
					GameType.getType().startplayers.clear();
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getGameMode() != GameMode.SPECTATOR) {
							GameType.getType().players.add(p);
							GameType.getType().startplayers.add(p);
						}
					}
					GameType.getType().currentmap = MapSelect.maxVotes();
					Gamestate.setGameState(Gamestate.INGAME);
					GameType.getType().gameStart();
					GameRunnable.start();
					LobbyBackgroundRunnable.stop();
					stop();
				}
				
				timeLeft--;
				
			}
		}, 0, 20);
	}
	
	public static void stop(){
		Bukkit.getScheduler().cancelTask(runnableID);
		
		running = false;
	
		
		
		
		
		
	}
	
	public static String calculatePercent(int bars, int FULL, int LEFT){
		//ACTIONBAR
		String tosend = "§rSpielstart ";
		double bar = Math.round(FULL/bars);
		double philled = Math.round(LEFT/bar); 
		
		String s = "";
		
		for(int i = 0; i < philled; i++){
			s+="§e⏹";
		}
		
		for(int i = 0; i < bars - philled; i++){
			s+="§7⏹";
		}
	    tosend = tosend + s + " §r" + LEFT + " §rSekunden";
		return tosend;
	}

}
