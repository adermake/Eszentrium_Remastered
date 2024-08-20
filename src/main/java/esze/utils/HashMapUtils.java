package esze.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class HashMapUtils {


    public static double getValue(Player p, double def, HashMap<Player, Double> map) {
        if (map.containsKey(p)) {
            return map.get(p);
        }
        return def;
    }


    public static float getValue(Player p, float def, HashMap<Player, Float> map) {
        if (map.containsKey(p)) {
            return map.get(p);
        }
        return def;
    }


}
