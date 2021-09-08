package esze.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCollisionEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;

public class BoatControll implements Listener {

	public BoatControll() {
		System.out.print("REGISTER EVENTS");
	}


	/*
	@EventHandler
	public void boatMove(VehicleMoveEvent e) {
		//Bukkit.broadcastMessage("BF " +e.getFrom());
		//Bukkit.broadcastMessage("BT " +e.getTo());
		Bukkit.broadcastMessage(""+e.getTo().subtract(e.getFrom()).length());
	}*/

	
	
	
}
