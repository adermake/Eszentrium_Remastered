package esze.listeners;

import esze.main.main;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class Launch implements Listener {

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow a) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (a.getTicksLived() > 20 * 10) {
                        a.remove();
                        this.cancel();
                    }
                }
            }.runTaskTimer(main.plugin, 20, 20);
        }
    }
}
