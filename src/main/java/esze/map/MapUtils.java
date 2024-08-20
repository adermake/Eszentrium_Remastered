package esze.map;

import io.netty.util.internal.ThreadLocalRandom;

import org.bukkit.Location;

import esze.main.main;

public class MapUtils {
	
	public static int spawnloc = 1;
	
	public static Location nextLoc(){
		Location loc = null;
		
		String mapname = main.mapname;
		if(main.plugin.getConfig().contains("maps."+mapname+"."+spawnloc)){
			loc = (Location) main.plugin.getConfig().get("maps."+mapname+"."+spawnloc);
			spawnloc++;
		}else{
			spawnloc = 1;
			return nextLoc();
		}
		
		
		return loc;
	}
	
	public static int randInt(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}

}
