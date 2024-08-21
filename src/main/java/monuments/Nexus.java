package monuments;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.enums.GameType;
import esze.types.TypeMONUMENTE;
import esze.utils.EszeTeam;
import esze.utils.ParUtils;

public class Nexus extends Monument {

    int soulCount = 0;
    ArrayList<Player> allAllies = new ArrayList<Player>();
    ArrayList<Player> allEnemies = new ArrayList<Player>();

    Location ori;

    public Nexus(Player p, Location ori, EszeTeam team) {
        super(p, "§eNexus", team);
        range = 6;
        this.team = team;
        this.ori = ori;
        platingCount = 0;
        constructMonument();
    }

    @Override
    public void onConstruct() {
        loc = ori.clone();
        Material TeamMaterial;
        Material TeamMaterial2;
        Material TeamOrbitar;
        Material TeamCore;
        if (team.getChatColor() == ChatColor.RED) {
            TeamMaterial = Material.FIRE_CORAL_BLOCK;
            TeamMaterial2 = Material.CRIMSON_HYPHAE;
            TeamOrbitar = Material.RED_STAINED_GLASS;
            TeamCore = Material.REDSTONE_BLOCK;
        } else {
            TeamMaterial = Material.TUBE_CORAL_BLOCK;
            TeamMaterial2 = Material.LAPIS_BLOCK;
            TeamOrbitar = Material.BLUE_STAINED_GLASS;
            TeamCore = Material.DIAMOND_BLOCK;
        }


        addBlock(TeamMaterial2, new Vector(0.4, 2, 0), 0.05);
        addBlock(TeamMaterial2, new Vector(-0.4, 2, 0), 0.05);
        addBlock(TeamMaterial, new Vector(0, 2, 0), 0.05);
        addBlock(TeamMaterial2, new Vector(0, 2, 0.4), 0.05);
        addBlock(TeamMaterial2, new Vector(0, 2, -0.4), 0.05);
        addBlock(TeamMaterial, new Vector(0.6, 1.5, 0.6), 0.05);
        addBlock(TeamMaterial, new Vector(-0.6, 1.5, 0.6), 0.05);
        addBlock(TeamMaterial, new Vector(0.6, 1.5, -0.6), 0.05);
        addBlock(TeamMaterial, new Vector(-0.6, 1.5, -0.6), 0.05);
        addBlock(Material.CRACKED_DEEPSLATE_TILES, new Vector(0, 2.5, 0), 0.05);


        addBlock(TeamMaterial2, new Vector(0.4, -2, 0), -0.05);
        addBlock(TeamMaterial2, new Vector(-0.4, -2, 0), -0.05);
        addBlock(TeamMaterial, new Vector(0, -2, 0), -0.05);
        addBlock(TeamMaterial2, new Vector(0, -2, 0.4), -0.05);
        addBlock(TeamMaterial2, new Vector(0, -2, -0.4), -0.05);
        addBlock(TeamMaterial, new Vector(0.6, -1.5, 0.6), -0.05);
        addBlock(TeamMaterial, new Vector(-0.6, -1.5, 0.6), -0.05);
        addBlock(TeamMaterial, new Vector(0.6, -1.5, -0.6), -0.05);
        addBlock(TeamMaterial, new Vector(-0.6, -1.5, -0.6), -0.05);
        addBlock(Material.CRACKED_DEEPSLATE_TILES, new Vector(0, -2.5, 0), -0.05);

        addBlock(TeamOrbitar, new Vector(3, 1, 0), 0.15);
        addBlock(TeamOrbitar, new Vector(-3, 1, 0), 0.15);
        addBlock(TeamOrbitar, new Vector(0, -1, 3), 0.15);
        addBlock(TeamOrbitar, new Vector(0, -1, -3), 0.15);

        addCore(TeamCore, new Vector(0, 0, 0));

    }

    @Override
    public void onEnter(Player p) {


    }

    @Override
    public void onLeave(Player p) {
        // TODO Auto-generated method stub


        if (p == target) {
            target = null;
        }

    }

    @Override
    public void onEnterEnemy(Player p) {
        // TODO Auto-generated method stub
        Bukkit.broadcastMessage("Enemy Enter");
        if (!allEnemies.contains(p))
            allEnemies.add(p);
    }

    @Override
    public void onLeaveEnemy(Player p) {
        // TODO Auto-generated method stub
        Bukkit.broadcastMessage("Enemy  leave");
        if (allEnemies.contains(p))
            allEnemies.remove(p);
    }

    @Override
    public void onEnterAlly(Player p) {
        // TODO Auto-generated method stub
        Bukkit.broadcastMessage("Ally enter");
        if (!allAllies.contains(p))
            allAllies.add(p);
    }

    @Override
    public void onLeaveAlly(Player p) {
        // TODO Auto-generated method stub
        Bukkit.broadcastMessage("Ally leave");
        if (allAllies.contains(p))
            allAllies.remove(p);
    }


    @Override
    public void onActivate(Player p) {
        // TODO Auto-generated method stub
        Bukkit.broadcastMessage("Activate");
    }

    @Override
    public void coreAnimation(ArmorStand ar, Vector dir) {
        // TODO Auto-generated method stub
        //ParUtils.createFlyingParticle(Particle.END_ROD, ar.getEyeLocation(), 0, 0,0, 1, 5.5F, dir);
        ParUtils.createParticle(Particle.CLOUD, ar.getLocation().add(0, 1.5, 0), 0, 0, 0, 0, 0);
    }

    int sec = 0;

    @Override
    public void onTick() {

        // TODO Auto-generated method stub
        setMonumentOffset(0, Math.sin(step / 15), 0);
        if (sec % 5 == 0) {

            for (Player p : allEnemies) {
                Bukkit.broadcastMessage("C");
                stealSoul(p);
            }
            for (Player p : allAllies) {
                Bukkit.broadcastMessage("D");
                giveSoul(p);
            }
        }


        sec++;
    }

    public int getSoulCount() {
        return soulCount;
    }

    public void setSoulCount(int soulCount) {
        this.soulCount = soulCount;
    }

    public void giveSoul(Player p) {
        TypeMONUMENTE monu = (TypeMONUMENTE) GameType.getType();
        if (monu.soulCount.containsKey(p) && monu.soulCount.get(p) > 0) {
            monu.removeSoul(p, 1);
            playSound(Sound.BLOCK_SMALL_AMETHYST_BUD_PLACE, loc, 1, 5);

            soulCount++;
        }
    }

    public void stealSoul(Player p) {
        if (soulCount > 0) {
            TypeMONUMENTE monu = (TypeMONUMENTE) GameType.getType();
            int c = monu.soulCount.get(p);
            //monu.soulCount.put(p, c+1);
            playSound(Sound.BLOCK_SMALL_AMETHYST_BUD_BREAK, loc, 1, 5);
            new Soul(p, p, loc.clone());
            soulCount--;
        }
    }


}
