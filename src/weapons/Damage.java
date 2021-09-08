package weapons;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import esze.enums.Gamestate;
import esze.main.main;
import esze.menu.GameModifier;
import esze.menu.ModifierMenu;
import esze.utils.Actionbar;
import esze.utils.SoundUtils;
import spells.spellcore.DamageCauseContainer;
import spells.spellcore.Spell;
import spells.spells.AntlitzderGöttin;
import spells.spells.Blutsiegel;
import spells.spells.Eisstachel;
import spells.spells.Schwerkraftsmanipulation;
import spells.spells.Seelenmarionette;
import spells.stagespells.VelocityTimeStop;

public class Damage implements Listener{
	
	public static HashMap<Player,Double> lastHealthTaken = new HashMap<Player,Double>();
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		
		
		/*
		if (AntlitzderGöttin.deflect.contains(e.getEntity())) {
			
			e.setDamage(0);
			SoundUtils.playSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE, e.getEntity().getLocation(), 5, 2);
		}
		*/if(e.getCause().equals( DamageCause.FLY_INTO_WALL) && !(ModifierMenu.hasModifier(GameModifier.FALLSCHADEN)&& Gamestate.getGameState() == Gamestate.INGAME)){
			e.setCancelled(true);
		}
		
		if(e.getCause().equals( DamageCause.FALL) && !(ModifierMenu.hasModifier(GameModifier.FALLSCHADEN)&& Gamestate.getGameState() == Gamestate.INGAME)) {
			e.setCancelled(true);
			return;
		}
		if (e.getEntity() instanceof Drowned) {
			
			Drowned ghost = (Drowned) e.getEntity();
			
			for (Seelenmarionette sg : Seelenmarionette.souls) {
				if (sg.ghost == ghost) {
					
					sg.ghostDamaged(e.getDamage());
					
				}
			}
			
		}
		
		
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			lastHealthTaken.put(p, p.getHealth());
			for (Blutsiegel bs : Blutsiegel.blutsiegel) {
				
				bs.subjectDamage(e.getDamage(),p);
			}
			
			for (Eisstachel eis : Eisstachel.eistacheln) {
				
				eis.playerTookDamage(p,e.getDamage());
			}
			
			for (Spell s : Spell.takeDamageEvent) {
				if (s.caster == p) {
					s.callEvent("takeDamageEvent");
				}
				
			}
			
			
			
			if (p.getGameMode().equals(GameMode.ADVENTURE) ){
				e.setCancelled(true);
			}
			if(Gamestate.getGameState() == Gamestate.LOBBY){
				e.setCancelled(true);
			}else if(Gamestate.getGameState() == Gamestate.INGAME && !(ModifierMenu.hasModifier(GameModifier.FALLSCHADEN)&& Gamestate.getGameState() == Gamestate.INGAME)){
				if(e.getCause().equals( DamageCause.FALL)){
					e.setCancelled(true);
				}
				if(e.getCause().equals( DamageCause.FLY_INTO_WALL) ){
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
		if (((Player) e.getDamager()).getGameMode().equals(GameMode.ADVENTURE)) {
			e.setCancelled(true);
		}
		
		if (VelocityTimeStop.timestop.contains(e.getEntity())) {
			//Bukkit.broadcastMessage("ADD VELO To " +e.getEntity().getName());
			e.setCancelled(true);
			e.getEntity().setVelocity(e.getEntity().getLocation().toVector().subtract(e.getDamager().getLocation().toVector()).normalize().multiply(0.5));
		}
		if(e.getDamager() instanceof Player) {
			if (e.getEntity() instanceof Player) {
				if (((Player) e.getDamager()).getGameMode().equals(GameMode.ADVENTURE)) {
					e.setCancelled(true);
				}
				Player p = (Player) e.getDamager();
				
				for (Seelenmarionette sg : Seelenmarionette.souls) {
					if (sg.caster == e.getEntity()) {
						e.getEntity().setVelocity(p.getLocation().getDirection().multiply(1.5F));
						e.setCancelled(true);
					}
				}
				
				if (p.getInventory().getItemInMainHand().getType() == Material.BOOK || p.getInventory().getItemInMainHand().getType() == Material.ENCHANTED_BOOK) {
					
					
				} else {
					
					
					DamageCauseContainer d = Spell.damageCause.get((Player) e.getEntity());
					if (d == null) {
						d = new DamageCauseContainer(p);
						Spell.damageCause.put(p, d);
					}
					if (p != null && p.getInventory() != null && p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getItemMeta() != null && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null) {
						d.swordDamage(p, p.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
					} else {
						d.swordDamage(p, "Hand");
					}
					
				}
				
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
