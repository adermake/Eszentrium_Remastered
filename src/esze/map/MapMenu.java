package esze.map;

  import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;

import esze.main.main;

public class MapMenu {
	
	public static void sendOverview(Player p){
		for(int i = 0; i < 30; i++){
			p.sendMessage("");
		}
		p.sendMessage("§8§m―――――――――§r §3Maps §8§m―――――――――");
		try{
			for(String s : main.plugin.getConfig().getConfigurationSection("maps").getKeys(false)){
				if(main.plugin.getConfig().get("maps."+s) != null){
					TextComponent a = new TextComponent("");
					TextComponent message = new TextComponent("§c[X]");
					message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cLÖSCHEN").create()));
					message.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/removemap "+s));
					TextComponent message1 = new TextComponent(" "+s);
					a.addExtra(message);
					a.addExtra(message1);
					p.spigot().sendMessage(a);
				}
			}
		}catch(Exception e){ p.sendMessage("§7Keine Maps vorhanden!"); }
		p.sendMessage("§8§m―――――――――§r §3Maps §8§m―――――――――");
	}

}
