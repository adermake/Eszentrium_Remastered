package esze.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import esze.main.main;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R1.CraftServer;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;


public class CorpseUtils {

    private static final HashMap<Integer, Tuple<ServerPlayer, ServerEntity>> corpses = new HashMap<>();

    public static Integer[] getAllCorpseIDs() {
        return corpses.keySet().toArray(new Integer[0]);
    }

    public static String getCorpseName(int cID) {
        return corpses.get(cID).a().getGameProfile().getName();
    }

    public static int spawnCorpseForAll(Player player, Location loc) {
        ArrayList<Player> players = new ArrayList<Player>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            players.add(p);
        }
        return spawnCorpseForPlayers(player, loc, players);
    }

    public static void setValue(Object packet, String fieldName, Object value) {
        try {
            Field field = packet.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(packet, value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void sendPacket(Packet<?> packet, Player player) {
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    public static int spawnCorpseForPlayers(Player player, Location loc, List<Player> showTo) {

        Property textures = (Property) ((CraftPlayer) player).getHandle().getGameProfile().getProperties().get("textures").toArray()[0];
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName());
        gameProfile.getProperties().put("textures", new Property("textures", textures.value(), textures.signature()));

        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel serverLevel = ((CraftWorld) loc.getWorld()).getHandle();
        ServerPlayer serverPlayer = new ServerPlayer(minecraftServer, serverLevel, gameProfile, ClientInformation.createDefault());
        serverPlayer.setPos(loc.getX(), loc.getY(), loc.getZ());
        serverPlayer.startSleeping(new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));

        SynchedEntityData synchedEntityData = serverPlayer.getEntityData();
        synchedEntityData.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 127);

        setValue(serverPlayer, "c", ((CraftPlayer) player).getHandle().connection);

        var serverE = new ServerEntity(((CraftWorld) loc.getWorld()).getHandle(),
                serverPlayer, 0, false, packet -> {
        }, Set.of()
        );


        for (Player all : showTo) {
            sendPacket(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, serverPlayer), all);
            sendPacket(new ClientboundAddEntityPacket(serverPlayer, serverE), all);
            sendPacket(new ClientboundSetEntityDataPacket(serverPlayer.getId(), synchedEntityData.getNonDefaultValues()), all);
            Bukkit.getScheduler().runTaskLaterAsynchronously(
                    main.plugin,
                    () -> sendPacket(new ClientboundPlayerInfoRemovePacket(Collections.singletonList(serverPlayer.getUUID())), player),
                    40
            );
        }
        int cId = serverPlayer.getId();
        corpses.put(cId, new Tuple<>(serverPlayer, serverE));
        return cId;
    }

    public static void teleportCorpseForPlayers(int corpseID, Location loc, List<Player> teleportFor) {
        ServerPlayer serverPlayer = corpses.get(corpseID).a();
        serverPlayer.setPos(loc.getX(), loc.getY(), loc.getZ());
        teleportFor.forEach(player -> sendPacket(
                new ClientboundTeleportEntityPacket(serverPlayer), player));
    }

    public static void teleportCorpseForAll(int corpseID, Location loc) {
        teleportCorpseForPlayers(corpseID, loc, new ArrayList<>(Bukkit.getOnlinePlayers()));
    }

    public static void removeCorpseForPlayers(int corpseID, List<Player> removeFrom) {
        removeFrom.forEach(player -> sendPacket(new ClientboundRemoveEntitiesPacket(corpseID), player));
    }

    public static void removeCorpseForAll(int corpseID) {
        removeCorpseForPlayers(corpseID, new ArrayList<>(Bukkit.getOnlinePlayers()));
        corpses.remove(corpseID);
    }

    public static void resetAllCorpses() {
        Arrays.stream(getAllCorpseIDs()).forEach(CorpseUtils::removeCorpseForAll);
        corpses.clear();
    }

}
