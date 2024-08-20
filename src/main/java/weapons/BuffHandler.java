package weapons;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class BuffHandler {

    //uncleared list
    public static HashMap<Player, Integer> bambusDebuf = new HashMap<Player, Integer>();


    public static void tickMethod() {
        new BukkitRunnable() {
            public void run() {
                for (Player p : bambusDebuf.keySet()) {

                    if (bambusDebuf.get(p) > 0) {
                        bambusDebuf.put(p, bambusDebuf.get(p) - 5);
                        ParUtils.createParticle(Particle.CLOUD, p.getLocation(), 0.1F, 0.1F, 0.1F, 2, 0.1F);
                    }
                }


            }
        }.runTaskTimer(main.plugin, 5, 5);

    }


    public static boolean onList(Player p, HashMap<Player, Integer> map) {
        if (map.containsKey(p)) {
            if (map.get(p) <= 0) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

}
