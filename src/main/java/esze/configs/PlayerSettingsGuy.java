package esze.configs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import esze.configs.entities.Cosmetic;
import esze.configs.entities.CosmeticType;
import esze.main.main;
import esze.menu.CosmeticMenu;
import esze.utils.Tuple;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.CraftServer;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

public class PlayerSettingsGuy {

    private static final HashMap<String, Tuple<ServerPlayer, ServerEntity>> settingsGuys = new HashMap<>();

    public static ServerPlayer getPlayerSettingsGuy(Player player) {
        if(!settingsGuys.containsKey(player.getUniqueId().toString())) {
            return null;
        }
        return settingsGuys.get(player.getUniqueId().toString()).a();
    }

    public static Location getPlayerSettingsGuyLocation(Player player) {
        if(getPlayerSettingsGuy(player) == null) {
            return null;
        }
        return getPlayerSettingsGuy(player).getBukkitEntity().getLocation();
    }

    public static void spawnPlayerSettingsGuy(Player player) {
        Location loc = new Location(player.getWorld(), -4, 103, -18);

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        Object[] textureProperties = ((CraftPlayer) player).getHandle().getGameProfile().getProperties().get("textures").toArray();
        if (textureProperties.length > 0) {
            Property textures = (Property) ((CraftPlayer) player).getHandle().getGameProfile().getProperties().get("textures").toArray()[0];
            gameProfile.getProperties().put("textures", new Property("textures", textures.value(), textures.signature()));
        }

        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel serverLevel = ((CraftWorld) loc.getWorld()).getHandle();
        ServerPlayer serverPlayer = new ServerPlayer(minecraftServer, serverLevel, gameProfile, ClientInformation.createDefault());
        serverPlayer.setPos(loc.getX(), loc.getY(), loc.getZ());

        SynchedEntityData synchedEntityData = serverPlayer.getEntityData();
        synchedEntityData.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 127);

        setValue(serverPlayer, "c", ((CraftPlayer) player).getHandle().connection);

        var serverE = new ServerEntity(((CraftWorld) loc.getWorld()).getHandle(),
                serverPlayer, 0, false, packet -> {
        }, Set.of()
        );


        PlayerTeam teamScore = new PlayerTeam(new Scoreboard(), player.getName());
        Style style = Style.EMPTY.withFont(ResourceLocation.withDefaultNamespace("small"));
        Component component = Component.literal("§e★ ")
                .append(Component.literal("§6KOSMETIK").withStyle(style))
                .append(Component.literal(" §e★"));
        teamScore.setPlayerPrefix(component);
        teamScore.getPlayers().add(serverPlayer.getScoreboardName());


        sendPacket(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, serverPlayer), player);
        sendPacket(new ClientboundAddEntityPacket(serverPlayer, serverE), player);
        sendPacket(new ClientboundSetEntityDataPacket(serverPlayer.getId(), synchedEntityData.getNonDefaultValues()), player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                main.plugin,
                () -> sendPacket(new ClientboundPlayerInfoRemovePacket(Collections.singletonList(serverPlayer.getUUID())), player),
                40
        );
        sendPacket(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(teamScore, true), player);
        settingsGuys.put(player.getUniqueId().toString(), new Tuple<>(serverPlayer, serverE));
        updatePlayerSettingsGuy(player);
    }

    public static void updatePlayerSettingsGuy(Player player) {
        ServerPlayer serverPlayer = settingsGuys.get(player.getUniqueId().toString()).a();

        ArrayList<Pair<EquipmentSlot, ItemStack>> equipment = new ArrayList<>();

        Cosmetic headCosmetic = PlayerSettingsService.getPlayerSettings(player).getCosmetic(CosmeticType.HEAD);
        equipment.add(new Pair<>(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(headCosmetic == null ? new org.bukkit.inventory.ItemStack(Material.AIR) : headCosmetic.createItem())));
        Cosmetic chestCosmetic = PlayerSettingsService.getPlayerSettings(player).getCosmetic(CosmeticType.CHEST);
        equipment.add(new Pair<>(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(chestCosmetic == null ? new org.bukkit.inventory.ItemStack(Material.AIR) : chestCosmetic.createItem())));
        Cosmetic pantsCosmetic = PlayerSettingsService.getPlayerSettings(player).getCosmetic(CosmeticType.PANTS);
        equipment.add(new Pair<>(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(pantsCosmetic == null ? new org.bukkit.inventory.ItemStack(Material.AIR) : pantsCosmetic.createItem())));
        Cosmetic bootsCosmetic = PlayerSettingsService.getPlayerSettings(player).getCosmetic(CosmeticType.BOOTS);
        equipment.add(new Pair<>(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(bootsCosmetic == null ? new org.bukkit.inventory.ItemStack(Material.AIR) : bootsCosmetic.createItem())));


        org.bukkit.inventory.ItemStack isWeapon = PlayerSettingsService.getPlayerSettings(player).getCosmetic(CosmeticType.WEAPON).createItem();
        equipment.add(new Pair<>(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(isWeapon)));


        ClientboundSetEquipmentPacket packet =
                new ClientboundSetEquipmentPacket(
                        serverPlayer.getId(),
                        equipment);


        sendPacket(packet, player);
    }

    public static void removePlayerSettingsGuy(Player player) {
        if (settingsGuys.containsKey(player.getUniqueId().toString())) {
            ServerPlayer serverPlayer = settingsGuys.get(player.getUniqueId().toString()).a();
            sendPacket(new ClientboundRemoveEntitiesPacket(serverPlayer.getId()), player);
        }
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

    public static void interact(Player player, int entityId) {
        if (settingsGuys.containsKey(player.getUniqueId().toString())) {
            ServerPlayer serverPlayer = settingsGuys.get(player.getUniqueId().toString()).a();
            if (serverPlayer.getId() == entityId) {
                Bukkit.getScheduler().runTask(main.plugin, () -> new CosmeticMenu(player, 1));
            }
        }
    }

}
