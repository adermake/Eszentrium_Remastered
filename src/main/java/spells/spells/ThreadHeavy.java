package spells.spells;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;

import java.util.ArrayList;
import java.util.Collection;

public class ThreadHeavy extends Spell {

    Location lolo;

    public ThreadHeavy() {

        name = "§cSThreadHeavy";
        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20 * 50
        );


    }

    @Override
    public void setUp() {
        lolo = raycast(caster.getEyeLocation());
        summonSwords();
        dead = true;
    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {

    }

    @Override
    public void display() {

    }

    @Override
    public void onPlayerHit(Player p) {

    }

    @Override
    public void onEntityHit(LivingEntity ent) {

    }

    @Override
    public void onSpellHit(Spell spell) {

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {

    }


    public ArrayList<ArmorStand> summonSwords() {
        ArrayList<ArmorStand> swords = new ArrayList<ArmorStand>();
        Location l = caster.getLocation();
        double r = 5;
        for (double t = 0; t <= Math.PI * 20; ) {

            t = t + Math.PI / 18;

            double x = r * Math.cos(t + Math.PI - Math.PI / 18);
            double y = 1;
            double z = r * Math.sin(t + Math.PI - Math.PI / 18);
            Location j = l.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, caster.getLocation());

            l.add(v.getX(), v.getY(), v.getZ());
            l.add(l.getDirection().multiply(-3));
            //new SchwertausLicht(l.clone(),caster,dir,target,name);
            ParUtils.createParticle(Particle.FLASH, l, 0, 0, 0, 1, 1);
            Vector ve = j.subtract(l).toVector();
            Location lala = l.clone();
            l.setDirection(lolo.toVector().subtract(lala.toVector()));

            l.add(l.getDirection().multiply(3));

            l.subtract(v.getX(), v.getY(), v.getZ());

        }


        return swords;
    }

    public Location raycast(Location loc) {
        Collection<? extends Player> plyers = Bukkit.getOnlinePlayers();
        loc = loc.clone();
        int end = 5000;
        for (int i = 0; (!loc.getBlock().getType().isSolid()) && i < end; i++) {
            loc.add(loc.getDirection().multiply(0.2));
            for (Player p : plyers) {
                if (p != caster) {
                    if (p.getLocation().distance(loc) <= 1.4) {
                        i = end;
                    }
                    if (p.getEyeLocation().distance(loc) <= 1.4) {
                        i = end;
                    }
                }
            }

        }

        return loc;

    }

}
