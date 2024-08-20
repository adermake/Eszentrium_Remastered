package esze.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class Spawn implements Listener{

	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		
		if(e.getSpawnReason() == SpawnReason.EGG) {
			e.setCancelled(true);
		}
	}
}
