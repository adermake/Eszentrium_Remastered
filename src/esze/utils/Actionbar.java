package esze.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;

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
		
	
		try {
		    Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), ChatMessageType.class,UUID.class);
		       
		    Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
		    Object packet = constructor.newInstance(icbc, ChatMessageType.GAME_INFO,p.getUniqueId());//(byte) 2
		    Object entityPlayer= p.getClass().getMethod("getHandle").invoke(p);
		    Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
		    
		    playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		  } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | InstantiationException e) {
		    e.printStackTrace();
		  }
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
