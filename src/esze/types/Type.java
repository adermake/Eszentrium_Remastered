package esze.types;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.map.JumpPadHandler;
import esze.menu.SoloSpellMenu;
import esze.scoreboards.Scoreboard;
import esze.utils.EszeTeam;
import esze.utils.ItemStackUtils;
import esze.utils.Music;
import esze.utils.PlayerUtils;
import io.netty.util.internal.ThreadLocalRandom;
import spells.spellcore.DamageCauseContainer;
import spells.spellcore.Spell;
import spells.spells.AntlitzderGöttin;
import weapons.Damage;


public abstract class Type {
	

	public String name;
	public String currentmap;
	public ArrayList<Player> startplayers = new ArrayList<Player>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Player> spectator = new ArrayList<Player>();
 	public Scoreboard scoreboard;
	public int spawnloc = 1;
	public static final String voiddamage = "void";
	public static final String unknownDamage = "unknown";
	public abstract void runEverySecond();
	
	public abstract void runEveryTick();
	
	public void death(PlayerDeathEvent event) {

		Player p = event.getEntity();
		
		// CHECK IF DEATH WAS VALID IF NOT RESET HEALTH TO LAST DAMAGE TAKEN
		if (deathCheck(p)) {
			p.setHealth(Damage.lastHealthTaken.get(p));
			return;
		}
		
		// CHECK IF ENTITY HAS A DAMAGE CAUSE IF NOT FILL IT WITH UNKNOWN
		if (main.damageCause.get(p) == null) {
			main.damageCause.remove(p);
			main.damageCause.put(p, unknownDamage);
		}

		// DEATH MESSAGE 
		if (!spectator.contains(p)) {
			
			String out = toStringCause(p);
			for (Player rec : Bukkit.getOnlinePlayers()) {
				rec.sendMessage(out);
			}
			
		} 

		main.damageCause.put(p, unknownDamage);
		p.setVelocity(new Vector(0, 0, 0));
		
		
	}
	
	public abstract void gameStart();
	
	public Location nextLoc(){
		Location loc = null;
		
		
		
		if(main.plugin.getConfig().contains("maps."+currentmap+"."+spawnloc)){
			
			loc = (Location) main.plugin.getConfig().get("maps."+currentmap+"."+spawnloc);
			spawnloc++;
		}else{
			spawnloc = 1;
			return nextLoc();
		}
		
		
		return loc;
	}
	public void setupJumpPad(String map) {
		JumpPadHandler.loadJumpPads(map);
		//setup();
	}
	public void setup() {
		for (int i = randInt(0,10);i>0;i++) {
			nextLoc();
		}
			
	}
	
	
	public static int randInt(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	public void out(Player p) {
	
		p.getInventory().clear();
		p.setGameMode(GameMode.ADVENTURE);
		p.setAllowFlight(true);
		p.setFlying(true);
		if (p.getLocation().getY()<60) {
			p.teleport(nextLoc());
		}
		PlayerUtils.hidePlayer(p);
		p.setVelocity(p.getVelocity().multiply(0));
		p.setHealth(p.getMaxHealth());
		players.remove(p);
		spectator.add(p);
		
		
		
		
	}
	
	
	
	
	public boolean deathCheck(Player p) {
		
		
		if (p.getLocation().getY()<=60) {
			return false;
		}
		if (p.getKiller() != null) {
			if(!(p.getKiller() instanceof Player)) {
				return true;
			}
		}
		if (p.getLastDamageCause() != null && p.getLastDamageCause().getCause() != null && p.getLastDamageCause().getCause() == DamageCause.FLY_INTO_WALL) {
			return true;
		}
		
		
	
		
		return false;
	}
	
	
	public void killInVoidCheck() {
		ArrayList<Player> kill = new ArrayList<>();
		for (Player p : players) {
			if (p.getLocation().getY()<60 && p.getGameMode() == GameMode.SURVIVAL) {
				
				if (main.damageCause.get(p) == null) {
					main.damageCause.put(p, unknownDamage);
				}
				if (main.damageCause.get(p).equals("") || main.damageCause.get(p).equals(unknownDamage)) {
					main.damageCause.put(p, voiddamage);
				} else if (!main.damageCause.get(p).endsWith(voiddamage)){
					main.damageCause.put(p, main.damageCause.get(p) + "-" + voiddamage);
				}
				
				if (Spell.damageCause.get(p) == null) {
					Spell.damageCause.put(p, new DamageCauseContainer(null, null));
				}
				Spell.damageCause.get(p).voidDamage();
				kill.add(p);
			}
		}
		
		for (Player p : kill) {
			p.damage(p.getHealth());
		}
	}
	
	
	
	
	public void setupPlayer(Player p) {
		main.damageCause.remove(p);
		main.damageCause.put(p, unknownDamage); //Reset damage Cause		
		p.teleport(nextLoc());
		p.setGameMode(GameMode.ADVENTURE);
		p.getInventory().clear();	
		p.getInventory().addItem(ItemStackUtils.attackSpeedify(ItemStackUtils.createItemStack(Material.WOODEN_SWORD, 1, 0, "§cSchwert", null, true)));
	}
	
	public void openSpellSelection(Player p) {
		PlayerUtils.hidePlayer(p,200);
		SoloSpellMenu s = new SoloSpellMenu();
		s.open(p);
		new BukkitRunnable() {
			int t =0;
			public void run() {
				t++;
				p.setNoDamageTicks(4);
				if (t > 10 * 10) {
					p.setGameMode(GameMode.SURVIVAL);
				}
				if (p.getGameMode() == GameMode.SURVIVAL) {
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin, 2,2);
	}
	
	public static String toStringCause(Player p) {
		String[] in = main.damageCause.get(p).split("-");
		String color = "§7";
		String out = color;
		// Analysis
		if (in.length == 0) {
			out = "ERROR:::";
		} else if (in.length == 1) {
			if (in[0].equals("")) {
				out += p.getName() + " ERRORED TO DEATH!"; // no Cause old
			} else if (in[0].equals(unknownDamage)) {
				out += p.getName() + " starb!"; // no Cause
			} else if (in[0].equals(voiddamage)) {
				out += p.getName() + " fiel ins Void!"; // Void
			} else {

				out += p.getName() + " ERRORED TO DEATH! (" + in[0] + ")";
			}
		} else if (in.length == 2) {
			out += p.getName() + " wurde durch " + in[1] + " mit " + in[0] + color + " getötet!"; // Cause+Player
		} else if (in.length == 3) {
			out += p.getName() + " wurde durch " + in[1] + " mit " + in[0] + color + " ins Void geworfen!"; // Cause+Player+void
		} else {
			out = main.damageCause.get(p);
		}
		return out;
	}
	public void setupGame() {
	
		for (int i = 0; i < 16; i++) {
			Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(nextLoc()));
		}	
		setupJumpPad(currentmap);
		Music.startRandomMusic();
		spectator.clear();
	}
	public abstract void endGame();
	
	public void givePlayerLobbyItems(Player p) {
		if (p.getGameMode().equals(GameMode.SURVIVAL)) {
			p.getInventory().clear();
		}
		p.getInventory().setItem(8, ItemStackUtils.createItemStack(Material.MAP, 1, 0, "§3Map wählen", null, true));
		p.getInventory().setItem(7, ItemStackUtils.createItemStack(Material.ENDER_CHEST, 1, 0, "§3Spellsammlung", null, true));
		p.getInventory().setItem(6, ItemStackUtils.createItemStack(Material.DIAMOND, 1, 0, "§3Georg", null, true));
		p.getInventory().setItem(5, ItemStackUtils.createItemStack(Material.NETHER_STAR, 1, 0, "§3Kosmetik", null, true));
	}
	
	public void resetMode() {
	}
	
}
