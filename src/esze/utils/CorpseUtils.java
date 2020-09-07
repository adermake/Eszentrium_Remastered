package esze.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.DataWatcher;
import net.minecraft.server.v1_15_R1.DataWatcherRegistry;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;


public class CorpseUtils {
	
	public static HashMap<Integer, EntityPlayer> allCorpses = new HashMap<Integer, EntityPlayer>();
	
	public static Set<Integer> getAllCorpseIDs(){
		return (Set<Integer>)allCorpses.keySet();
	}
	
	public static String getCorpseName(int cID){
		return allCorpses.get(cID).getName();
	}
	
	public static int spawnCorpseForAll(Player player, Location loc){
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers()){
			players.add(p);
		}
		return spawnCorpseForPlayers(player, loc, players);
	}
	
	public static int spawnCorpseForPlayers(Player player, Location loc, List<Player> showTo){
		int cID = 0;
		Property textures = (Property) ((CraftPlayer) player).getHandle().getProfile().getProperties().get("textures").toArray()[0];
		String signature = textures.getSignature();
		String value = textures.getValue();

		GameProfile gameProfile = new GameProfile(player.getUniqueId(), player.getName());
		gameProfile.getProperties().put("textures", new Property("textures", value, signature));

		EntityPlayer entityPlayer = new EntityPlayer(
		((CraftServer) Bukkit.getServer()).getServer(),
		((CraftWorld) player.getWorld()).getHandle(), gameProfile, new PlayerInteractManager(((CraftWorld) player.getWorld()).getHandle()));
		entityPlayer.setPosition(loc.getX(), loc.getY(), loc.getZ());

		for(Player all : showTo){
			((CraftPlayer) all).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(entityPlayer));
			((CraftPlayer) all).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
		}
		
		Location bed = loc.clone().add(1, 0, 0);
		//entityPlayer.e(new BlockPosition(bed.getX(), bed.getY(), bed.getZ()));
		entityPlayer.e(bed.getX(), bed.getY(), bed.getZ());
		// 1.15 
		DataWatcher watcher = entityPlayer.getDataWatcher();
		Byte b = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40; // each of the overlays (cape, jacket, sleeves, pants, hat)
		watcher.set(DataWatcherRegistry.a.a(15), (byte) b); // 15 is the value for Spigot 1.14 apparently (even though on wiki.vg it says it's 13, but it isn't)

		PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(cID = entityPlayer.getId(), watcher, false);
		
		for(Player all : showTo){
			
			((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
		}
		allCorpses.put(cID, entityPlayer);
		return cID;
	}
	
	public static void teleportCorpseForPlayers(int cID, Location loc, List<Player> teleportFor){
		//Bukkit.broadcastMessage("loc: "+loc);
		//Bukkit.broadcastMessage("ent: "+allCorpses.get(cID));
		allCorpses.get(cID).setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
		//allCorpses.get(cID).locY = loc.getY();
		//allCorpses.get(cID).locZ = loc.getZ();
		//allCorpses.get(cID).pitch = loc.getPitch();
		//allCorpses.get(cID).yaw = loc.getYaw();
		
		//Bukkit.broadcastMessage("p: "+allCorpses.get(cID).pitch);
		//Bukkit.broadcastMessage("y: "+allCorpses.get(cID).yaw);
		
		PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(cID, getFixRotation(loc.getYaw()),getFixRotation(loc.getPitch()), true);
		
		for(Player all : teleportFor){
			((CraftPlayer) all).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(allCorpses.get(cID)));
			((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public static void teleportCorpseForAll(int cID, Location loc){
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers()){
			players.add(p);
		}
		teleportCorpseForPlayers(cID, loc, players);
	}
	
	
	public static void removeCorpseForPlayers(int cID, List<Player> removeFrom){
		for(Player all : removeFrom){
			((CraftPlayer) all).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(cID));
		}
	}
	
	
	public static void removeCorpseForAll(int cID){
		for(Player all : Bukkit.getOnlinePlayers()){
			((CraftPlayer) all).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(cID));
		}
		allCorpses.remove(cID);
	}
	
	
	public static void removeAllCorpses() {
		HashMap<Integer,EntityPlayer> keyInts = (HashMap<Integer, EntityPlayer>) allCorpses.clone();
		for (int key : keyInts.keySet()) {
			removeCorpseForAll(key);
		}
		keyInts.clear();
	}
	private static byte getFixRotation(float yawpitch){
        return (byte) ((int) (yawpitch * 256.0F / 360.0F));
	}
	
	
	
	

}
