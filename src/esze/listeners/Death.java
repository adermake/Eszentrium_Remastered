package esze.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.menu.GameModifier;
import esze.menu.ModifierMenu;
import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import spells.spells.AntlitzderGöttin;

public class Death implements Listener {
	
	@EventHandler
	public void onDeath(EntityDamageEvent e){
			// TEST
		if(e.getCause() != DamageCause.ENTITY_EXPLOSION){
			
		}
		if (e.getEntity() instanceof Player) {
			if((e.getCause() != DamageCause.FALL && e.getCause() != DamageCause.FLY_INTO_WALL) || (ModifierMenu.hasModifier(GameModifier.FALLSCHADEN)&& Gamestate.getGameState() == Gamestate.INGAME )){
				Player p = (Player) e.getEntity();
				
				if (p.getHealth() - e.getFinalDamage() <= 0 ) {
				
				PlayerDeathEvent event = new PlayerDeathEvent(p, null, 0, "he dead");
				
				e.setCancelled(true);
				
				p.setHealth(20);
			
				
				if (p.getLocation().getY()<60)
				p.teleport(GameType.getType().nextLoc());
				ParUtils.createRedstoneParticle(e.getEntity().getLocation(), 0.3, 0.5, 0.3, 10, Color.RED, 3);
				GameType.getType().death(event);
				}
			}else{
				if (!(ModifierMenu.hasModifier(GameModifier.FALLSCHADEN)&& Gamestate.getGameState() == Gamestate.INGAME)) {
					e.setCancelled(true);
					
				}
				
			}
		}
		
	}
	
	@EventHandler
	  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) { 
		if (event.getDamager() instanceof Player) {
			Player p = (Player) event.getDamager();
			if (event.getDamage() > 6 && event.getCause() == DamageCause.ENTITY_ATTACK && NBTUtils.getNBT("Weapon",p.getInventory().getItemInMainHand()) == "true") {
				event.setDamage(6);
			}
		}
		if (event.getDamager() instanceof org.bukkit.entity.EnderDragon) event.setCancelled(true); 
		if (event.getDamager() instanceof org.bukkit.entity.Firework) event.setCancelled(true); 
		if (event.getDamager() instanceof org.bukkit.entity.Slime) event.setCancelled(true); 
	}
	
}
