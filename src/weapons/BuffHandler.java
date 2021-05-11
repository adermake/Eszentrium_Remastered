package weapons;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;

public class BuffHandler {

	//uncleared list
	public static HashMap<Player,Integer> bambusDebuf = new HashMap<Player,Integer>();
	
	
	
	
	public static void tickMethod() {
		new BukkitRunnable() {
			public void run() {
				for (Player p : bambusDebuf.keySet()) {
					
					if (bambusDebuf.get(p)>0) {
						bambusDebuf.put(p, bambusDebuf.get(p)-5);
						ParUtils.createParticle(Particles.CLOUD, p.getLocation(), 0.1F, 0.1F, 0.1F, 2, 0.1F);
					}
				}
				
				
				
			}
		}.runTaskTimer(main.plugin, 5, 5);
		
	}
	
	
	public static boolean onList(Player p,HashMap<Player,Integer> map) {
		if (map.containsKey(p)) {
			if (map.get(p) <= 0) {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}
	
}
