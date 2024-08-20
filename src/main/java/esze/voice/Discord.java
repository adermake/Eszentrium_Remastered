package esze.voice;

import esze.main.main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class Discord {

    public static HashMap<Player, Member> muted = new HashMap<>();
    private static JDA jda;
    private static Guild g;

    public static void run() {
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

    public static void unMuteAll() {
        try {
            for (Player p : ((HashMap<Player, Member>) muted.clone()).keySet()) {
                setMuted(p, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMuted(Player player, boolean shouldMute) {
        try {
            for (Member member : g.getMembers()) {
                User p = member.getUser();
                if (p.getName().equalsIgnoreCase(player.getName()) || (member.getNickname() != null && member.getNickname().equalsIgnoreCase(player.getName()))) {

                    member.mute(shouldMute).complete();
                    if (shouldMute == false) {
                        muted.remove(player);
                    } else {
                        muted.put(player, member);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStatus(Activity a) {
        jda.getPresence().setActivity(a);
    }

    public static void sendLog(MessageEmbed embed) {
        g.getChannelById(TextChannel.class, 621398787155558400L).sendMessageEmbeds(embed).queue();
    }

}
