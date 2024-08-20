package esze.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {

	
	
	
	public static void playSound(Sound s,Location loc) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(loc, s, 1, 1);
		}
	}
	
	public static void playSound(Sound s,Location loc,float pitch,float volume) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(loc, s, volume, pitch);
		}
	}
	
	
}
