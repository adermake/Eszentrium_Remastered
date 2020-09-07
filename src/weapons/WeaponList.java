package weapons;

import java.util.LinkedHashMap;

import org.bukkit.Material;


public class WeaponList {
	
	public static final String SWORDNAME = "§cSchwert";
	public static final String BOWNAME = "§cBogen";
	public static final String FOCUSSPHERENAME = "§cFokussphäre";
	public static final String BAMBOONAME = "§cBambus";
	
	public static LinkedHashMap<String, Material> weapons; 
	
	public static void setUpWeapons() {
		weapons = new LinkedHashMap<>();
		weapons.put( SWORDNAME, Material.WOODEN_SWORD);
		//weapons.put( BOWNAME , Material.BOW);
		//weapons.put( FOCUSSPHERENAME , Material.HEART_OF_THE_SEA);
		//weapons.put( BAMBOONAME,Material.BAMBOO);
	}
	

}
