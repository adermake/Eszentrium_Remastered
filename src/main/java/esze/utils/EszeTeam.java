package esze.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EszeTeam {


    public String teamName;
    public ChatColor color;
    public Material teamIcon;
    public Color parcolor;
    public ArrayList<Player> players = new ArrayList<Player>();


    public EszeTeam(String teamName, ChatColor c, Color col, Material teamIcon) {
        this.teamName = teamName;
        this.parcolor = col;
        this.color = c;
        this.teamIcon = teamIcon;
    }

    public void addPlayer(Player p) {
        players.add(p);

        p.sendMessage("§7Du bist Team " + teamName + " §7beigetreten!");
        ScoreboardTeamUtils.colorPlayer(p, color);


        p.setGlowing(true);
    }

    public void removePlayer(Player p) {
        players.remove(p);
        ScoreboardTeamUtils.clearColor(p);
        p.setGlowing(false);

    }


    public Player getRandomPlayer() {
        if (players.size() > 1) {
            return players.get(MathUtils.randInt(0, players.size() - 1));
        }
        return players.get(0);
    }

    public void clearTeam() {

        players.clear();
    }


}
