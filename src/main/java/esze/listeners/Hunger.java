package esze.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Hunger implements Listener{
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e){
		e.setFoodLevel(20);
	}

}
