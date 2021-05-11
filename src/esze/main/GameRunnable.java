package esze.main;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import esze.enums.GameType;
import esze.types.TypeTEAMS;
import net.minecraft.server.v1_16_R3.EntityPlayer;

public class GameRunnable {
	
	private static int runnableTickID;
	private static int runnableSecID;
	
	public static void start(){
		runnableTickID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {
			
			@Override
			public void run() {
				lagCheck();
				GameType.getType().runEveryTick();
				
			}
		}, 0, 1);
		
		runnableSecID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				GameType.getType().runEverySecond();
				
			}
		}, 0, 20);
	}
	
	public static void stop(){
		Bukkit.getScheduler().cancelTask(runnableTickID);
		Bukkit.getScheduler().cancelTask(runnableSecID);
		if (!(GameType.getType() instanceof TypeTEAMS))
		GameType.refreshGameType();
	}

	
	public static void lagCheck() {
		boolean lag = true;
		for (Player p : Bukkit.getOnlinePlayers()) {
			CraftPlayer cp = (CraftPlayer) p;
			EntityPlayer ep = cp.getHandle();
			if (ep.ping < 350 )
				lag = false;
		}
		
		if (lag) {
			Bukkit.broadcastMessage("§c[L.A.G] Low ping detected");
		}
		
	}
}
