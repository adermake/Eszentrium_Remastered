package esze.utils;

import esze.main.main;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class PlayerConfig implements ConfigurationSerializable {


    public static HashMap<Player, PlayerConfig> playerConfigs = new HashMap<Player, PlayerConfig>();

    String playerName;
    Material weaponMaterial = Material.WOODEN_SWORD;
    boolean likesMusic = true;


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("player", playerName);
        map.put("weapon", weaponMaterial.toString());
        map.put("music", likesMusic);
        return map;
    }

    public PlayerConfig(String playername) {
        this.playerName = playername;
    }

    public PlayerConfig(Map<String, Object> map) {
        this.playerName = (String) map.get("player");
        //this.weaponMaterial = (String) map.get("weapon");

        this.weaponMaterial = Material.getMaterial((String) map.get("weapon"), false);
        this.likesMusic = (boolean) map.get("music");
    }

    public static void load() {
	/*
				for (Player p : Bukkit.getOnlinePlayers()) {
					
			
					System.out.println("CONTAINS" +p.getName()+main.plugin.getConfig().contains(p.getName()));
					PlayerConfig pc =  (PlayerConfig) main.plugin.getConfig().get("playerconfig."+p.getName());
					
					if (pc == null) {
						pc = new PlayerConfig(p.getName());
						pc.saveMe();
					}
					playerConfigs.put(p,pc);
				}
		
		*/


    }

    public void save() {


        main.plugin.getConfig().set("playerconfig." + playerName, this);
        main.plugin.saveConfig();


    }

    public static PlayerConfig getConfig(Player p) {
        if (playerConfigs.containsKey(p)) {
            return playerConfigs.get(p);
        }
        if (main.plugin.getConfig().contains("playerconfig." + p.getName())) {
            return (PlayerConfig) main.plugin.getConfig().get("playerconfig." + p.getName());
        } else {
            PlayerConfig pc = new PlayerConfig(p.getName());

            playerConfigs.put(p, pc);
            return pc;
        }
    }

    public void setWeapon(Material m) {

        weaponMaterial = m;
        save();
    }

    public void setMusic(boolean b) {
        likesMusic = b;
        save();
    }

    public Material getWeapon() {

        return weaponMaterial;
    }
}
