package esze.utils;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.network.PlayerConnection;


public class TabList {
	
	String header;
	String footer;
	
	public TabList(String header, String footer) {
		this.header = header;
		this.footer = footer;
	}
   
    public void send(Player player) {
        CraftPlayer craftplayer = (CraftPlayer)player;
        PlayerConnection connection =
          craftplayer.getHandle().b;
          IChatBaseComponent hj = ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{text: '" + header + "'}"));
          IChatBaseComponent fj = ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{text: '" + footer + "'}"));
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(null); // dunno
        try
        {
          Field headerField = packet.getClass().getDeclaredField("a");
          headerField.setAccessible(true);
          headerField.set(packet, hj);
          headerField.setAccessible(!headerField.isAccessible());
         
          Field footerField = packet.getClass().getDeclaredField("b");
          footerField.setAccessible(true);
          footerField.set(packet, fj);
          footerField.setAccessible(!footerField.isAccessible());
        }
        catch (Exception localException) {}
        connection.sendPacket(packet);
        //DISABLED = NEW CLOUDSYSTEM
      }
    
    
    public static void setPlayerlistHeader(Player player, String header) {
        CraftPlayer cplayer = (CraftPlayer) player;
        PlayerConnection connection = cplayer.getHandle().b;
       
        IChatBaseComponent top = ChatSerializer.a("{text: '" + header + "'}");
       
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(null); //dunno
       
        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, top);
            headerField.setAccessible(!headerField.isAccessible());
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.sendPacket(packet);
    }
}
