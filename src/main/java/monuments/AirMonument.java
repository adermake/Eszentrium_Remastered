package monuments;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.scoreboards.TeamsScoreboard;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spells.Aufwind;

public class AirMonument extends Monument {


    public AirMonument(Player p) {
        super(p, "§eAir Monument", null);
        range = 6;

        constructMonument();
    }

    @Override
    public void onConstruct() {


        addBlock(Material.POLISHED_ANDESITE, new Vector(0, 6, 0));
        // Layer ---
        addBlock(Material.POLISHED_ANDESITE, new Vector(0.5, 5, 0.5));
        addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5, 5, -0.5));
        addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5, 5, 0.5));
        addBlock(Material.STONE, new Vector(0.5, 5, -0.5));
        // Layer ---
        addBlock(Material.STONE, new Vector(0.5, 4, 0.5));
        addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5, 4, -0.5));
        addBlock(Material.STONE, new Vector(-0.5, 4, 0.5));
        addBlock(Material.STONE, new Vector(0.5, 4, -0.5));
        // Layer ---
        addBlock(Material.POLISHED_ANDESITE, new Vector(0.5, 3, 0.5));
        addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5, 3, -0.5));
        addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5, 3, 0.5));
        addBlock(Material.STONE, new Vector(0.5, 3, -0.5));
        // Layer ---
        addBlock(Material.STONE, new Vector(0.5, 1, 0.5));
        addBlock(Material.STONE, new Vector(-0.5, 1, -0.5));
        addBlock(Material.STONE, new Vector(-0.5, 1, 0.5));
        addBlock(Material.STONE, new Vector(0.5, 1, -0.5));
        // Layer ---
        addBlock(Material.POLISHED_ANDESITE, new Vector(0.5, 0, 0.5));
        addBlock(Material.STONE, new Vector(-0.5, 0, -0.5));
        addBlock(Material.POLISHED_ANDESITE, new Vector(-0.5, 0, 0.5));
        addBlock(Material.STONE, new Vector(0.5, 0, -0.5));


        addBlock(Material.POLISHED_ANDESITE, new Vector(0, -1, 0));
        addBlock(Material.POLISHED_ANDESITE, new Vector(0, -2, 0));

        addCore(Material.GLASS, new Vector(0, 2, 0));
    }

    @Override
    public void onEnter(Player p) {
        Aufwind a = new Aufwind();
        a.castSpell(p, "wui");
        target = p;
    }

    @Override
    public void onLeave(Player p) {
        if (p == target) {
            target = null;
        }
    }

    @Override
    public void onEnterEnemy(Player p) {

    }

    @Override
    public void onLeaveEnemy(Player p) {

    }

    @Override
    public void onActivate(Player p) {
        Bukkit.broadcastMessage("Activate");
    }

    @Override
    public void coreAnimation(ArmorStand ar, Vector dir) {
        //ParUtils.createFlyingParticle(Particle.END_ROD, ar.getEyeLocation(), 0, 0,0, 1, 5.5F, dir);
        ParUtils.createParticle(Particle.CLOUD, ar.getLocation().add(0, 1.5, 0), 0, 0, 0, 0, 0);
    }

    @Override
    public void onTick() {
        setMonumentOffset(0, Math.sin(step / 15), 0);
    }

    @Override
    public void onEnterAlly(Player p) {

    }

    @Override
    public void onLeaveAlly(Player p) {

    }


}
