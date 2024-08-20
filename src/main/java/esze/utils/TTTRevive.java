package esze.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import esze.enums.GameType;
import esze.types.TypeTTT;
import esze.voice.Discord;


public class TTTRevive {
	
	public static void revive(String playername, TTTCorpse cd){
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getName().equalsIgnoreCase(playername)){
				Discord.setMuted(p, false);
				p.getInventory().clear();
				PlayerUtils.showPlayer(p);
				ItemStack is = new ItemStack(Material.EMERALD);
				ItemMeta ism = is.getItemMeta();
				ism.setDisplayName("§eWeltenkatalysator");
				is.setItemMeta(ism);
				p.getInventory().setItem(8, is);
				
				ItemStack i = new ItemStack(Material.WOODEN_SWORD);
				ItemMeta im = i.getItemMeta();
				im.setUnbreakable(true);
				i.setItemMeta(im);
				p.getInventory().addItem(i);
				for(int j = 0; j< cd.inv.getSize();j++){
					ItemStack co = cd.inv.getItem(j);
					if (co != null) {
						p.getInventory().addItem(co);
					}
					
				}
				
				
				
				
				TypeTTT t = (TypeTTT) GameType.getType();
				if (t.startInnocent.contains(p)) {
					t.innocent.add(p);
				}
				else {
					t.traitor.add(p);
				}
				t.players.add(p);
				t.spectator.remove(p);
				p.setGameMode(GameMode.SURVIVAL);
				p.setFlying(false);
				p.setAllowFlight(false);
				
			}
		}
		if(cd != null){
			try{
				cd.remove();
			}catch(Exception e){ }
			}
			
	}

}
