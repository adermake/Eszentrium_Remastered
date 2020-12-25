package esze.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.enums.GameType;
import esze.listeners.Join;
import esze.main.main;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import weapons.WeaponAbilitys;
import weapons.WeaponMenu;

public class LobbyUtils {

	
	
	public static void recall(Player p) {
		
		Location l = new Location(p.getWorld(),0 ,108 ,3);
		p.teleport((Location) main.plugin.getConfig().get("lobby.loc"));
		SoundUtils.playSound(Sound.BLOCK_PORTAL_TRAVEL, l,2,0.6F);
		PlayerUtils.hidePlayer(p,35);
		Spell.unHittable.clear();
		p.setMaxHealth(20);
		p.setHealth(20);
		new BukkitRunnable() {
			int t = 0;
			public void run() {
				t++;
				ArrayList<Location> locs = ParUtils.preCalcCircle(l, 4, new Vector(0,0,-1), 0);
				
				for (Location loc : locs) {
					ParUtils.createFlyingParticle(Particles.END_ROD, loc, 0, 0, 0, 1, 1, p.getLocation().toVector().subtract(loc.toVector()).multiply(0.1));
				}
				
				if (t>20) {
					this.cancel();
				}
				
			}
		}.runTaskTimer(main.plugin, 1, 1);
		
		
		
	}
	public static void recallAll() {
		
		WeaponAbilitys.clearLists();
		WeaponMenu.stopLoop();
		Location l = new Location(Bukkit.getWorld("world"),0 ,108 ,3);
		
		Spell.clearSpells();
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setGameMode(GameMode.SURVIVAL);
			p.setFlying(false);
			p.setAllowFlight(false);
			p.getInventory().clear();
			p.teleport((Location) main.plugin.getConfig().get("lobby.loc"));
			p.setGlowing(false);
			
			p.setMaxHealth(20);
			p.setHealth(20);
			PlayerUtils.hidePlayer(p,35);
			GameType.getType().givePlayerLobbyItems(p);
		}
		
		
		Spell.unHittable.clear();
		SoundUtils.playSound(Sound.BLOCK_PORTAL_TRAVEL, l,2,0.6F);
		
		new BukkitRunnable() {
			int t = 0;
			public void run() {
				t++;
				ArrayList<Location> locs = ParUtils.preCalcCircle(l, 4, new Vector(0,0,-1), 0);
				
				for (Location loc : locs) {
					ParUtils.createFlyingParticle(Particles.END_ROD, loc, 0, 0, 0, 1, 1,((Location) main.plugin.getConfig().get("lobby.loc")).toVector().subtract(loc.toVector()).multiply(0.1));
				}
				
				if (t>20) {
					this.cancel();
				}
				
			}
		}.runTaskTimer(main.plugin, 1, 1);
		
		
		
	}
}
