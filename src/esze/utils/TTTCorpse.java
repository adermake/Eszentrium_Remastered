package esze.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftCow;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import esze.enums.GameType;
import esze.main.main;
import esze.types.TypeTTT;

public class TTTCorpse implements Listener{
	
	public Player player;
	private String username;
	public Inventory inv = null;
	public int corpseID = 0;
	public ArrayList<Entity> cows;
	private Player carrier = null;
	private BukkitTask carryTask;
	public boolean isLooted = false;
	public boolean isExposed = false;
	public static HashMap<Player,Block> droppedCorpse = new HashMap<Player,Block>();
	private ArmorStand armor;
	
	public static ArrayList<TTTCorpse> allCorpses = new ArrayList<TTTCorpse>();
	public TTTCorpse(Player player, boolean withInv) {
		this.player = player;
		this.username = player.getName();
		
		this.cows = new ArrayList<Entity>();
		if(withInv){
			this.inv = Bukkit.createInventory(null, 9*4, username + "s Inventar");
		
			int i = 0;
			for(ItemStack isOld : player.getInventory()){
				if(isOld != null && (isOld.getType() == Material.ENCHANTED_BOOK || isOld.getType() == Material.BOOK)){
					/*
					ItemStack is = NBTUtils.setNBT("Cooldown", "0", isOld);
					String spellname = NBTUtils.getNBT("OriginalName", isOld);
					ItemMeta meta = is.getItemMeta();
					meta.setDisplayName(spellname);
					is.setItemMeta(meta);
					is.setType(Material.ENCHANTED_BOOK);
					*/
					inv.setItem(i, isOld);
					i++;
				}
			}
			allCorpses.add(this);
		}
		
		main.plugin.getServer().getPluginManager().registerEvents(this, main.plugin);
		
	}
	@EventHandler
	public void onRightClickBlock(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.isSneaking()) {
			if (!droppedCorpse.containsKey(p))
				droppedCorpse.put(p,e.getClickedBlock());
		}
		
	}
	@EventHandler
	public void onClick(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		
		if(this.cows.contains(ent) && p.isSneaking() && !isLooted){
			
			
				if(inv != null  && p.getGameMode() == GameMode.SURVIVAL)
					p.openInventory(inv);
			
		}
		
		if(!isExposed && e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			isExposed = true;
			
			armor = Bukkit.getWorlds().get(0).spawn(CorpseUtils.allCorpses.get(corpseID).getBukkitEntity().getLocation().clone().add(0,-1.5,0), ArmorStand.class);
			TypeTTT type = (TypeTTT) GameType.getType();
			armor.setCustomName(type.startTraitor.contains(player) ? "§cTraitor" : "§aInnocent");
			armor.setCustomNameVisible(true);
			armor.setGravity(false);
			armor.setMarker(false);
			armor.setBasePlate(false);
			armor.setVisible(false);
			
		}
		
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getClickedInventory() == inv && e.getCurrentItem() != null){
			p.getInventory().addItem(e.getCurrentItem());
			inv.remove(e.getCurrentItem());
			e.setCancelled(true);
			
			ArrayList<HumanEntity> viewers = new ArrayList<HumanEntity>();
			viewers.addAll(inv.getViewers());
			for(HumanEntity viewer : viewers){
				viewer.closeInventory();
			}
			isLooted = true;
		}
	}
	
	public void carryRunner(){
		carryTask = Bukkit.getScheduler().runTaskTimer(main.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				
				if(carrier != null && !droppedCorpse.containsKey(carrier)){
					Location loc = carrier.getLocation().clone().add(0, 2, 0);
					
					
					loc.setYaw(carrier.getLocation().getYaw()*-1);
					Location turn = loc.clone();
					
					turn.setPitch(0);
					turn.setYaw(turn.getYaw()*-1-90);
					
					Vector dir = turn.getDirection();
					
					ArrayList<Player> players = new ArrayList<Player>();
					for(Player p : Bukkit.getOnlinePlayers()){
						if(p != carrier){
							players.add(p);
						}
					}
					CorpseUtils.teleportCorpseForPlayers(corpseID, loc.add(dir), players);
					
					CorpseUtils.teleportCorpseForPlayers(corpseID, loc.add(0,1,0), Arrays.asList(carrier));
					
					cows.get(0).teleport(loc.clone().add(1, 0, 0));
					cows.get(1).teleport(loc.clone().add(0, 0, 0));
				}else{
					
					
					stopCarrying();
				}
				
			}
		}, 0, 1);
	}
	
	public void stopCarrying(){
		
		Block b = droppedCorpse.get(carrier);
		Location l = b.getLocation();
		
		l = l.add(0,1,0);
		while (l.getBlock().getType().isSolid()) {
			l = l.add(0,1,0);
		}
	
		l.setYaw(0);
		l.setPitch(0);
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers()){
			
				players.add(p);
			
		}
		//cows.get(0).teleport(l.add(1, 0, 0));
		//cows.get(1).teleport(l);
		CorpseUtils.teleportCorpseForPlayers(corpseID, l.add(0,1F,0), players);
		Location cL = l.clone();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CorpseUtils.teleportCorpseForPlayers(corpseID, cL, players);
			}
		}.runTaskTimer(main.plugin, 1,1);
		
		//cows.get(0).teleport(l.add(1, 0, 0));
		
		if(carryTask != null){
			carryTask.cancel();
		}
		carrier = null;
		
	}
	
	public void carry(Player carrier){
		if (droppedCorpse.containsKey(carrier))
			droppedCorpse.remove(carrier);
		if(this.carrier == null){
			this.carrier = carrier;
			carryRunner();
		}else{
			
		}
	}
	
	public void remove() {
		for (Entity ent : cows) {
			ent.remove();
		}
		
		CorpseUtils.removeCorpseForAll(corpseID);
		
		player = null;
		username = "";
		armor.remove();
		armor = null;
		inv = null;
		allCorpses.remove(this);
	}
	
	public void spawn(){
		corpseID = CorpseUtils.spawnCorpseForAll(player, player.getLocation());
		
		Entity e = player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.COW);
		((CraftCow)e).setAI(false);
		((CraftCow)e).setSilent(true);
		((CraftCow)e).setGravity(false);
		((CraftCow)e).setCollidable(false);
		((CraftCow)e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000*1000*1000, 100), false);
		((CraftCow)e).setInvulnerable(true);
		
		Entity e2 = player.getLocation().getWorld().spawnEntity(player.getLocation().clone().add(1, 0, 0), EntityType.COW);
		((CraftCow)e2).setAI(false);
		((CraftCow)e2).setSilent(true);
		((CraftCow)e2).setGravity(false);
		((CraftCow)e2).setCollidable(false);
		((CraftCow)e2).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000*1000*1000, 100), false);
		((CraftCow)e2).setInvulnerable(true);
		
		cows.add(e);
		cows.add(e2);
		
	}
	
	public static ArrayList<TTTCorpse> getCorpses(Location loc,float radius) {
		
		ArrayList<TTTCorpse> ret = new ArrayList<TTTCorpse>();
		
		for (TTTCorpse c : allCorpses) {
			if (c.cows.get(0).getLocation().distance(loc)<radius+1) {
				ret.add(c);
			}
		}
		return ret;
		
	}

	public void teleport(Location loc) {
		ArrayList<Player> send = new ArrayList<Player>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			send.add(p);
		}
		CorpseUtils.teleportCorpseForPlayers(corpseID, loc, send);
		
	
		
		cows.get(0).teleport(loc.clone().add(1, 0, 0));
		cows.get(1).teleport(loc.clone().add(0, 0, 0));
	}

}
