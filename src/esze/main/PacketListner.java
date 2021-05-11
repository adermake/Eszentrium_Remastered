package esze.main;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle;



public class PacketListner {

	
	public static ArrayList<Player> noExit = new ArrayList<Player>(); 
	@SuppressWarnings("deprecation")
	public static void registerPackets() {
		
		PacketListenerAPI.addPacketHandler(new PacketHandler() {

            @Override
            public void onSend(SentPacket packet) {
            	
            	
            }

            @Override
            public void onReceive(ReceivedPacket packet) {
            	

            	if (packet.getPacket() instanceof PacketPlayInSteerVehicle) {
        			PacketPlayInSteerVehicle steer = (PacketPlayInSteerVehicle) packet.getPacket();
        			
        			if (noExit.contains(packet.getPlayer())) {
        				packet.setPacketValue("d", false);
        			}
        			
        			
				}
            	
            }

        });
	}
}
