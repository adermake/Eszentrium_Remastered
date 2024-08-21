package esze.players;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Contains important game information about a player
 *
 * @author Philipp
 */
public class PlayerInfo {

    public String uuid;
    public String latestName;

    public boolean isInRound;
    public boolean isAlive;

    public int soloLives;

    private HashMap<Long, DamageCall> damageCalls = new HashMap<Long, DamageCall>();

    /**
     * @param uuid       UUID of player
     * @param latestName Last known name of player
     */
    public PlayerInfo(String uuid, String latestName) {
        this.uuid = uuid;
        this.latestName = latestName;
        this.isAlive = true;
        this.isInRound = true;
        this.soloLives = 4;
    }

    /**
     * @param player Player object
     */
    public PlayerInfo(Player player) {
        this(player.getUniqueId().toString(), player.getName());
    }

    /**
     * @return Player represented by object (null if offline)
     */
    public Player getPlayer() {
        OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        if (oPlayer.isOnline()) {
            Player player = Bukkit.getPlayer(UUID.fromString(uuid));
            return player;
        } else {
            return null;
        }
    }

    /**
     * Deals void damage
     *
     * @return DamageCall object
     */
    public DamageCall damageVoid() {
        DamageCall dc = new DamageCall();
        dc.setVoid(true);
        dc.setMillis(System.currentTimeMillis());

        Player player = getPlayer();
        if (player != null) {
            if (isAlive) {
                damageCalls.put(dc.getMillis(), dc);
                player.damage(30);
            }
        }

        return dc;
    }

    /**
     * Damage player represented by object
     *
     * @param damager Damaging entity
     * @param damage  Damage to deal
     * @param spell   Name of damaging spell
     * @return DamageCall object
     */
    public DamageCall damage(Entity damager, int damage, String spell) {
        DamageCall dc = new DamageCall(damager, damage, spell, false, System.currentTimeMillis());

        Player player = getPlayer();
        if (player != null) {
            if (isAlive) {
                damageCalls.put(dc.getMillis(), dc);
                player.damage(damage);
            }
        }

        return dc;
    }

    /**
     * Gets damage call
     *
     * @param offset gets that damage call in list (0 = last)
     * @return damage call
     */
    public DamageCall getDamageCalls(int offset) {
        ArrayList<Long> sortedKeys = new ArrayList<Long>(damageCalls.keySet());
        Collections.sort(sortedKeys, new Comparator<Long>() {
            @Override
            public int compare(Long a, Long b) {
                return a.compareTo(b);
            }
        });

        int offNow = 0;
        for (Long key : sortedKeys) {
            if (offNow == offset) {
                return damageCalls.get(key);
            }
            offNow++;
        }
        return null;
    }

}
