package esze.listeners;

import esze.configs.PlayerSettingsGuy;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.management.AttributeNotFoundException;
import java.lang.reflect.Field;
import java.util.List;

public class PacketReceiver {

    public static void addPacketReceiver(Player player) {
        Channel channel = getChannel(player);
        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<ServerboundInteractPacket /*Packet<?> for debugging*/>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, ServerboundInteractPacket packet, List<Object> list) throws Exception {
                String packetName = packet.getClass().getSimpleName();
                if(packetName.equals("PacketPlayInUseEntity")) {
                    ServerboundInteractPacket packetPlayInUseEntity = packet;
                    Field entityIdField = FieldUtils.getDeclaredField(packetPlayInUseEntity.getClass(), "b", true);
                    entityIdField.setAccessible(true);
                    int entityId = (int) entityIdField.get(packetPlayInUseEntity);
                    PlayerSettingsGuy.interact(player, entityId);
                }
                list.add(packet);
            }
        });
    }

    public static void removePacketReceivers() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            Channel channel = getChannel(player);
            if(channel.pipeline().names().contains("PacketInjector")) {
                channel.pipeline().remove("PacketInjector");
            }
        }
    }

    private static Channel getChannel(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerGamePacketListenerImpl connection = craftPlayer.getHandle().connection;

        try {
            Field field = FieldUtils.getAllFieldsList(connection.getClass())
                    .stream()
                    .filter(f -> f.getType().equals(Connection.class))
                    .findFirst()
                    .orElseThrow(); //CONNECTION FIELD FINDEN

            field.setAccessible(true);
            Connection c = (Connection) field.get(connection);
            return c.channel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
