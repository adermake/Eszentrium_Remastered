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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;


public class Discord {
	
	public static JDA jda;
	public static HashMap<Player, Member> muted = new HashMap<Player, Member>();
	public static Guild g;
	static long gID = 429733093050679306L;
	static long rID = 1;
	
	public static void run(){

		try {
			jda = JDABuilder.createLight(main.discord_TOKEN)
					.setActivity(Activity.watching("people die"))
					.build()
					.awaitReady();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		g = jda.getGuildById(429733093050679306L);

	}
	public static void logout() {
		jda.shutdownNow();
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
 	


}
