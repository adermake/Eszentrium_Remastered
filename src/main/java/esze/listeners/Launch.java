package esze.listeners;

import esze.main.main;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;


public class Launch implements Listener {

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow a = (Arrow) e.getEntity();

            new BukkitRunnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    if (a.getTicksLived() > 20 * 10) {
                        a.remove();
                        this.cancel();
                    }
                }
            }.runTaskTimer(main.plugin, 20, 20);
        }
		/*
		if (e.getEntity().getShooter() instanceof Player) {
			Player p = (Player) e.getEntity().getShooter();
			
			
			if (drawing.contains(p))
			drawing.remove(p);
		}
		*/
    }


    HashMap<Player, Integer> chargeTime = new HashMap<Player, Integer>();
/*	
	public void onCharge(Player p) {
		
		Location l = p.getEyeLocation();
		Vector dir = p.getLocation().getDirection().clone();
		
		float ct = chargeTime.get(p);
		
		if (ct > 40) {
			ct = 40;
		}
		float f = (ct/40)*3;
	
		dir = dir.normalize().multiply(f);
		
		for (float i = 0;i<100;i++) {
			dir = dir.add(new Vector(0,-0.052,0));
			
			l.add(dir);
			ParUtils.createParticle(Particle.WATER_BUBBLE, l, 0, 0, 0, 1, 0);
		}
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		ItemStack is = p.getInventory().getItemInMainHand();
		
		
		
		 if(e.getItem() != null && e.getItem().getType() == Material.BOW) {
             if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                  //If item == Material.BOW
            	 chargeTime.put(p, 0);
            	 new BukkitRunnable() {
            		 public void run() {
            			 onCharge(p);
            			 chargeTime.put(p, chargeTime.get(p)+1);
            			 if (!drawing.contains(p)) {
            				 this.cancel();
            			 }
            		 }
            	 }.runTaskTimer(main.plugin, 1,1);
                 drawing.add(e.getPlayer());
             }
		 }
		 
	}
	@EventHandler
	public void onHeld(PlayerItemHeldEvent e) {
		if(drawing.contains(e.getPlayer())) {
	         drawing.remove(e.getPlayer());
	    }
		
	}
	
	public static ArrayList<Player> drawing = new ArrayList<Player>();
	
		@EventHandler
		public void onShoot(ProjectileLaunchEvent e) {
			if(e.getEntity().getShooter() instanceof Player) {
				//Making sure the shooter is not a Skeleton, etc.
				Player shooter = (Player) e.getEntity().getShooter();
				//Variable to access that is a Player constructor.
	         if(drawing.contains(shooter)) {
	              drawing.remove(shooter.getName());
	         }
	         onLaunch(e);
	     }
	}
	
	public static boolean isDrawing(Player p ) {
		return drawing.contains(p);
	}
*/
}
