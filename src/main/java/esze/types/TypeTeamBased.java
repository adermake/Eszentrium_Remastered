package esze.types;

import esze.utils.EszeTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class TypeTeamBased extends Type {


    public ArrayList<EszeTeam> allTeams = new ArrayList<EszeTeam>();

    public void removePlayerFromAllTeams(Player p) {
        for (EszeTeam t : allTeams) {
            if (t.players.contains(p)) {
                t.removePlayer(p);
            }
        }
    }

    public void autoFillPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!playerHasTeam(p)) {
                EszeTeam et = allTeams.get(randInt(0, allTeams.size() - 1));
                et.addPlayer(p);
            }
            p.setGlowing(false);
        }
    }

    public boolean playerHasTeam(Player p) {
        boolean hasTeam = false;
        for (EszeTeam t : allTeams) {
            if (t.players.contains(p)) {
                hasTeam = true;
            }
        }
        return hasTeam;

    }

    public ArrayList<Player> getTeammates(Player p) {

        for (EszeTeam t : allTeams) {
            if (t.players.contains(p)) {
                return t.players;
            }
        }
        return null;

    }
}
