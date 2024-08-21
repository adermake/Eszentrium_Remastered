package esze.types;

import esze.utils.EszeTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class TypeTeamBased extends Type {

    public ArrayList<EszeTeam> allTeams = new ArrayList<EszeTeam>();

    public void removePlayerFromAllTeams(Player p) {
        allTeams.stream()
                .filter(t -> t.containsPlayer(p))
                .forEach(t -> t.removePlayer(p));
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
        return allTeams.stream()
                .map(EszeTeam::getPlayers)
                .anyMatch(players -> players.contains(p));
    }

    public ArrayList<Player> getTeammates(Player p) {
        return allTeams.stream()
                .map(EszeTeam::getPlayers)
                .filter(players -> players.contains(p))
                .findFirst()
                .orElse(null);
    }
}
