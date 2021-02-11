package esze.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import esze.main.main;
import esze.menu.TraitorshopMenu;
import esze.utils.ItemStackUtils;


public class MapSelect implements Listener{
	
	public static HashMap<Player, String> votes = new HashMap<Player, String>();
	public static HashMap<Player, Inventory> invs = new HashMap<Player, Inventory>();
	
	public static void openSelection(Player p){
		Inventory inv = Bukkit.createInventory(null, 9*3, "§aWähle eine Map:");
		fillInv(inv, p);
		invs.put(p, inv);
		p.openInventory(inv);
	}
	
	public static void fillInv(Inventory inv, Player p){
		inv.clear();
		int slot = 0;
		for(String arena: main.plugin.getConfig().getConfigurationSection("maps").getKeys(false)){
			if(main.plugin.getConfig().get("maps."+arena)!=null){
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("");
				int votesint = 0;
				for(String a : votes.values()){
					if(a.equalsIgnoreCase(arena)){
						votesint++;
					}
				}
				int votesamount = 1;
				if(votesint == 1){
					lore.add("§7"+votesint + " Stimme");
				}else{
					lore.add("§7"+votesint + " Stimmen");
					if(votesint != 0){
						votesamount = votesamount;
					}
				}
				
				Material mat = Material.LEGACY_EMPTY_MAP;
				int dur = 0;
				if(main.plugin.getConfig().contains("maps."+arena+".material") && main.plugin.getConfig().get("maps."+arena+".material") != null){
					mat = Material.valueOf(main.plugin.getConfig().getString("maps."+arena+".material"));
					dur = main.plugin.getConfig().getInt("maps."+arena+".durability");
				}
				
				if(votes.containsKey(p) && votes.get(p).equals(arena)){
					lore.add("§8GEWÄHLT");
				}else{
					lore.add("§2Klicke um zu wählen");
				}
				ItemStack i = ItemStackUtils.createItemStack(mat, votesamount, dur, "§3"+arena, lore, false);
				if(votes.containsKey(p)){
					if(votes.get(p).equals(arena)){
						addGlow(i);
					}
				}
				inv.setItem(slot, i);
				slot += 2;
				
			}
		}
	}
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Inventory inv = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		/*
		if(item != null && item.getType() != Material.AIR){
			if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§3Map wählen")){
				e.setCancelled(true);
			}
		}
		
		*/
		if (item != null && item.getType() == Material.STONE_BUTTON && p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		if(inv != null && e.getView().getTitle() != null){
			if(e.getView().getTitle().equals("§aWähle eine Map:")){
				e.setCancelled(true);
				if(item != null && item.getType() != Material.AIR){
					if(votes.containsKey(p) && votes.get(p).equals(ChatColor.stripColor(item.getItemMeta().getDisplayName()))){
						return;
					}
					votes.put(p, ChatColor.stripColor(item.getItemMeta().getDisplayName()));
					for(Player all : invs.keySet()){
						Inventory i = invs.get(all);
						fillInv(i, all);
					}
					p.sendMessage("§8| §7Du hast für die Map "+item.getItemMeta().getDisplayName()+"§7 gestimmt.");
					p.closeInventory();
				}
			}
		}
		
	}
	
	@EventHandler
	public void onOpen(PlayerInteractEvent e){
		Player p = e.getPlayer();
		ItemStack i = e.getItem();
		if(i != null && i.getType() == Material.MAP){
			if(i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("§3Map wählen")){
				e.setCancelled(true);
				openSelection(p);
			}
		}
		
	}
	
	public static String maxVotes(){
		ArrayList<String> max = new ArrayList<String>();
		HashMap<String, Integer> votesints = new HashMap<String, Integer>();
		for(String a : votes.values()){
			if(votesints.containsKey(a)){
				votesints.put(a, votesints.get(a)+1);
			}else{
				votesints.put(a, 1);
			}
		}
		
		int maxint = 0;
		for(String a : votesints.keySet()){
			int v = votesints.get(a);
			if(v > maxint){
				max.clear();
				max.add(a);
				maxint = v;
			}
			if(v == maxint){
				max.add(a);
				maxint = v;
			}
		}
		
		if(max.isEmpty()){
			
			for(String arena: main.plugin.getConfig().getConfigurationSection("maps").getKeys(false)){
				if(main.plugin.getConfig().get("maps."+arena)!=null){
					max.add(arena);
				}
			}
		}
		
		for(Player all : invs.keySet()){
			Inventory i = invs.get(all);
			ArrayList<HumanEntity> hu = new ArrayList<HumanEntity>();
			hu.addAll(i.getViewers());
			for(HumanEntity p : hu){
				p.closeInventory();
			}
		}
		
		invs.clear();
		votes.clear();
		
		Random rand = new Random(); 
	    String randomInt = max.get(rand.nextInt(max.size()));
	    return randomInt;
		
	}
	
	public static void addGlow(ItemStack stack)
    {
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant( Enchantment.LURE, 1, false );
        meta.addItemFlags( ItemFlag.HIDE_ENCHANTS );
        stack.setItemMeta( meta );
    }
	
	

}
