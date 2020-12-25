package esze.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.players.PlayerAPI;
import esze.players.PlayerInfo;
import esze.types.Type;

public class Move implements Listener{
	
	@EventHandler
	public void onVoid(PlayerMoveEvent e){
		Player p = e.getPlayer();
		
			if(Gamestate.getGameState() == Gamestate.INGAME){
				if(e.getTo().getBlockY() <= 60){
					//Register void as damageCause
					if (main.damageCause.get(p) == null) {
						main.damageCause.put(p, Type.unknownDamage);
					}
					if (main.damageCause.get(p).equals("") || main.damageCause.get(p).equals(Type.unknownDamage)) {
						main.damageCause.put(p, Type.voiddamage);
					} else if (!main.damageCause.get(p).endsWith(Type.voiddamage)){
						main.damageCause.put(p, main.damageCause.get(p) + "-" + Type.voiddamage);
					}
					
					PlayerInfo pi = PlayerAPI.getPlayerInfo(p);
					pi.damageVoid();
				}
			}else{
				if(e.getTo().getBlockY() <= 60){
					if (p.getGameMode() != GameMode.CREATIVE)
					p.teleport((Location) main.plugin.getConfig().get("lobby.loc"));
				}
			}
	}

}
