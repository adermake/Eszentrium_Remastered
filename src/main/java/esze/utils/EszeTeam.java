package esze.utils;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Getter
public class EszeTeam {

    private final String teamName;
    private final ChatColor chatColor;
    private final Material teamIcon;
    private final Color particleColor;
    private final ArrayList<Player> players = new ArrayList<>();

    public EszeTeam(String teamName, ChatColor chatColor, Color particleColor, Material teamIcon) {
        this.teamName = teamName;
        this.particleColor = particleColor;
        this.chatColor = chatColor;
        this.teamIcon = teamIcon;
    }

    public String getTeamTag() {
        return switch(chatColor) {
            case RED -> CharRepo.TAG_TEAM_RED;
            case BLUE -> CharRepo.TAG_TEAM_BLUE;
            case GREEN -> CharRepo.TAG_TEAM_GREEN;
            case YELLOW -> CharRepo.TAG_TEAM_YELLOW;
            default -> "[§"+ chatColor.getChar() + getTeamName() +"§r]";
        } + "";
    }

    public void addPlayer(Player p) {
        players.add(p);

        p.sendMessage("§8| §7Du bist Team §r" + getTeamTag() + " §7beigetreten!");
        ScoreboardTeamUtils.colorPlayer(p, chatColor);

        String tagName = getTeamTag() + " §" + chatColor.getChar() + p.getName() + "§r";
        p.setDisplayName(tagName);
        p.setPlayerListName(tagName);

        p.setGlowing(true);
    }

    public void removePlayer(Player p) {
        players.remove(p);
        ScoreboardTeamUtils.clearColor(p);
        p.setDisplayName(p.getName());
        p.setPlayerListName(p.getName());
        p.setGlowing(false);
    }


    public Player getRandomPlayer() {
        if (players.size() > 1) {
            return players.get(MathUtils.randInt(0, players.size() - 1));
        }
        return players.getFirst();
    }

    public boolean containsPlayer(Player p) {
        return players.contains(p);
    }


}
