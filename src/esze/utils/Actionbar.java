package esze.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;





public class Actionbar {
	
	String message;
	
	public Actionbar(String message) {
		this.message = message;
	}
	
	public Actionbar sendAll(){
		for(Player p : Bukkit.getOnlinePlayers()){
			send(p);
		}
		return this;
	}
	
	public Actionbar send(Player p) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	
	
		return this;
	}
	
	private Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server.v1_16_R3." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		    return null;
		}
	}
		

	private String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}

}
