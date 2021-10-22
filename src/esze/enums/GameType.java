package esze.enums;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import esze.main.main;
import esze.types.Type;

public class GameType {
	
	private static Type type;
	
	public static Type getType() {
		if (type == null)
			setTypeByName("SOLO");
		return type;
	}
	
	public static void setType(Type type){
		type = GameType.type;
	}
	
	public static boolean setTypeByEnum(TypeEnum typeEnum){
		try{
			type = (Type) Class.forName("eszeRemastered.types.Type" + typeEnum.toString().toUpperCase()).newInstance();
			
			main.plugin.getConfig().set("settings.mode", typeEnum.toString().toUpperCase());
			main.plugin.saveConfig();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean setTypeByName(String typeName){
	
		
		try{
			
			type = (Type) Class.forName("esze.types.Type" + typeName.toUpperCase()).newInstance();
			
				
			
			main.plugin.getConfig().set("settings.mode", typeName.toUpperCase());
			main.plugin.saveConfig();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static enum TypeEnum {
		SOLO,TTT,TEAMS,MONUMENTE;
	}

	public static void refreshGameType() {
		if (type != null) {
			try {
				type = (Type) type.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				Bukkit.broadcastMessage("Apperently Logic doesn't work anymore (GameType:54)");
			}
		}
	}
	


}
