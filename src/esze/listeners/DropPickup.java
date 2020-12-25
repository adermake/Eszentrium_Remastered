package esze.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeTEAMS;
import esze.utils.ItemStackUtils;
import esze.utils.ParUtils;
import esze.utils.ScoreboardTeamUtils;
import esze.utils.SoundUtils;

public class DropPickup implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		//Player p = e.getPlayer();
		e.setCancelled(true);
		/*if(Gamestate.getGameState() == Gamestate.INGAME && Gametype.type == Gametype.TTT){
			p.getInventory().setItemInMainHand(null);
		}*/
		
		if(Gamestate.getGameState() == Gamestate.INGAME && GameType.getType() instanceof TypeTEAMS){
			throwBook(p,e.getItemDrop().getItemStack().clone());
			e.getItemDrop().remove();
			p.getInventory().setItemInMainHand(null);
			e.setCancelled(false);
		}
		
		
	}
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		
		e.setCancelled(true);
	}
	
	
	
	/// TEAMS TOSS

	
	
	public void throwBook(Player p,ItemStack is) {
		
		TypeTEAMS t = (TypeTEAMS) GameType.getType();
		Item item = p.getWorld().dropItem(p.getEyeLocation(), is);
		item.setPickupDelay(100000);
		ScoreboardTeamUtils.colorEntity(item, t.getTeamOfPlayer(p).color);
		SoundUtils.playSound(Sound.ENTITY_SNOWBALL_THROW,p.getLocation(),0.2F,1);
		item.setGlowing(true);
		
		new BukkitRunnable() {
			Vector dir = p.getLocation().getDirection();
			int i = 0;
			double speedMult = 3;
			public void run() {
				i++;
				speedMult-=0.05F;
				if (speedMult <= 1.5)
					speedMult = 1.5;
				SoundUtils.playSound(Sound.ITEM_BOOK_PAGE_TURN,item.getLocation(),2,1);
				Player target = null;
				for (Player pl : t.getTeammates(p)) {
					if (pl != p && pl.getLocation().distance(item.getLocation())<30) {
						
						if (item.getLocation().distance(pl.getLocation())<2) {
							
							pl.getInventory().addItem(is);
							SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER,pl.getLocation(),1.2F,0.7F);
							SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER,p.getLocation(),1.2F,0.7F);
							item.remove();
							this.cancel();
							return;
						}
						if (target == null) {
							target = pl;
						}
						else {
							if (pl.getLocation().distance(item.getLocation())< target.getLocation().distance(item.getLocation())) {
								target = pl;
							}
						}
					}
					
				}
				
				
				if (i > 20 * 5) {
					SoundUtils.playSound(Sound.ENTITY_ARROW_HIT_PLAYER,p.getLocation(),1.2F,0.7F);
					p.getInventory().addItem(is);
					item.remove();
					this.cancel();
				}
				ParUtils.createRedstoneParticle(item.getLocation().add(0,0.3,0), 0.05, 0.05, 0.05, 2, t.getTeamOfPlayer(p).parcolor, 1.3F);
				item.setVelocity(dir.clone().multiply(speedMult));
				if (target != null ) {
					Vector vec = target.getLocation().toVector().subtract(item.getLocation().toVector()).normalize();
					dir = dir.add(vec).normalize();
				}
				
				
			}
		}.runTaskTimer(main.plugin,1,1);
		
		
		
		
		
		
		
	}
}
