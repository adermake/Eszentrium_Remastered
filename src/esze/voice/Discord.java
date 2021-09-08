package esze.voice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import esze.main.main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite.Channel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;


public class Discord {
	
	public static JDA jda;
	public static HashMap<Player, Member> muted = new HashMap<Player, Member>();
	public static VoiceChannel c;
	public static Guild g;
	static long gID = 429733093050679306L;
	static long rID = 1;
	
	private static DiscordMessageListener listener;
	
	public static void run(){

		try {
			jda = new JDABuilder(main.discord_TOKEN)
					.addEventListeners(listener = new DiscordMessageListener())
					.setActivity(Activity.watching("people die"))
					.build()
					.awaitReady();
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
		
		g = jda.getGuildById(429733093050679306L);
		c = jda.getVoiceChannelById(621375797953036328L);
		AudioManager manager = g.getAudioManager();
        manager.openAudioConnection(c);
        
		
		
	}
	public static void logout() {
		jda.removeEventListener(listener);
		jda.shutdownNow();
	}
	public static void test(Channel c) {
		Bukkit.broadcastMessage(""+c.getId());
	}
	
	private static class DiscordMessageListener extends ListenerAdapter{
		@Override
		public void onMessageReceived(MessageReceivedEvent event) {
			MessageChannel channel = event.getChannel();

			if(!event.isFromGuild()) {
				if(event.getMessage().getContentRaw().equalsIgnoreCase("log")) {
					try {
						File f = new File("logs/latest.log");
						Scanner scanner = new Scanner(f);
						ArrayList<String> lines = new ArrayList<String>();
						while (scanner.hasNextLine()) {
						   String line = scanner.nextLine();
						   lines.add(line);
						}
						
						String send = "";
						
						int line = 0;
						for(String s : lines) {
							if(lines.size() - 20 <= line) {
								channel.sendMessage(s).queue();
							}
							line++;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				//Bukkit.broadcastMessage(event.getMessage().getContentRaw());
			}
			
		}
		
	}
	
	
	
	public static void unMuteAll(){
		try  {
		for(Player p : ((HashMap<Player, Member>)muted.clone()).keySet()){
			setMuted(p, false);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setMuted(Player player, boolean shouldMute){
		try  {
		for(Member member : g.getMembers()) {
			User p = member.getUser();
			if(p.getName().equalsIgnoreCase(player.getName()) || (member.getNickname() != null && member.getNickname().equalsIgnoreCase(player.getName()))){
				
				member.mute(shouldMute).complete();
				if(shouldMute == false){
					muted.remove(player);
				}else{
					muted.put(player, member);
				}
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setStatus(Activity a){
		jda.getPresence().setActivity(a);
	}
 	
	/*public static void setMuted(Player player, boolean shouldMute){
		for (Flux<Guild> g : discordbot.getGuilds()) {
			channel = g.getVoiceChannelByID(621375797953036328L);
		}
		
		
		RequestBuffer.request(() -> {
			if (channel == null) {
				System.out.print("CHANNEL IS NULL");
			}
			if (channel.getGuild() == null) {
				System.out.print("GUILD IS NULL");
			}
			for(IUser p : channel.getGuild().getUsers()){
				if (!channel.getConnectedUsers().contains(p)) {
					continue;
				}
				if(p.getName().equalsIgnoreCase(player.getName()) || (p.getNicknameForGuild(channel.getGuild()) != null && p.getNicknameForGuild(channel.getGuild()).equalsIgnoreCase(player.getName()))){
					channel.getGuild().setMuteUser(p, shouldMute);
					if(shouldMute == false){
						muted.remove(p);
					}else{
						muted.put(player, p);
					}
				}
			}
        });
	}
	
	/*
	public static void unMuteAll(){
		for(Player p : ((HashMap<Player, IUser>)muted.clone()).keySet()){
			setMuted(p, false);
		}
	}
	
	public static void setStatus(String what){
		discordbot.changePresence(StatusType.ONLINE, ActivityType.WATCHING, what);
	}
	
	
	public static boolean isLoggedIn(){
		return discordbot.isLoggedIn();
	}
	
	public static String getVersion(){
		return Discord4J.VERSION;
	}
	
*/
	
}
