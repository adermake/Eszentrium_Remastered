package weapons;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import esze.enums.Gamestate;
import esze.main.main;
import esze.utils.Actionbar;
import esze.utils.SoundUtils;
import spells.spells.AntlitzderGöttin;
import spells.spells.Schwerkraftsmanipulation;

public class Damage implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		
		if (AntlitzderGöttin.deflect.contains(e.getEntity())) {
			
			e.setDamage(0);
			SoundUtils.playSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE, e.getEntity().getLocation(), 5, 2);
		}
		if(e.getCause().equals( DamageCause.FALL)) {
			e.setCancelled(true);
		}
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if (p.getGameMode().equals(GameMode.ADVENTURE) ){
				e.setCancelled(true);
			}
			if(Gamestate.getGameState() == Gamestate.LOBBY){
				e.setCancelled(true);
			}else if(Gamestate.getGameState() == Gamestate.INGAME){
				if(e.getCause().equals( DamageCause.FALL) && !Schwerkraftsmanipulation.gravityMani.contains(p)){
					e.setCancelled(true);
				}
				if(e.getCause().equals( DamageCause.FLY_INTO_WALL) && !Schwerkraftsmanipulation.gravityMani.contains(p)){
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		
		if (!(e.getDamager() instanceof Player)) {
			e.setCancelled(true);
			return;
		}
		if(e.getDamager() instanceof Player) {
			if (e.getEntity() instanceof Player) {
				if (((Player) e.getDamager()).getGameMode().equals(GameMode.ADVENTURE)) {
					e.setCancelled(true);
				}
				Player p = (Player) e.getDamager();
				
				/*
				if (p.getInventory().getItemInMainHand().getType() == Material.BOOK || p.getInventory().getItemInMainHand().getType() == Material.ENCHANTED_BOOK) {
					main.damageCause.remove((Player) e.getEntity());
					main.damageCause.put((Player) e.getEntity(), "Buch-" + ((Player) e.getDamager()).getName());
				} else {
					
					main.damageCause.put((Player) e.getEntity(), p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() +  "-" + ((Player) e.getDamager()).getName());
				}
				*/
				if (p.getInventory().getItemInMainHand().getType() == Material.BOW) {
					//main.damageCause.put((Player) e.getEntity(), "Bogen-" + ((Player) e.getDamager()).getName());
					if (WeaponAbilitys.charge1.containsKey(p)) {
						WeaponAbilitys.charge1.put(p, WeaponAbilitys.charge1.get(p)+1);
						
					}
					else {
						WeaponAbilitys.charge1.put(p, 1);
						
					}
					Player p2 = (Player) e.getEntity();
					
					if (WeaponAbilitys.charge1.containsKey(p2)) {
						if (WeaponAbilitys.charge1.get(p2) > 0)
						WeaponAbilitys.charge1.put(p2, WeaponAbilitys.charge1.get(p2)-1);
						
					}
					
					
				}
				
			}
		}
	}

}
