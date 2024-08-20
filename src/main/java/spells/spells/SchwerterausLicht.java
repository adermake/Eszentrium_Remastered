package spells.spells;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.SchwertausLicht;

import java.util.ArrayList;
import java.util.Collection;

public class SchwerterausLicht extends Spell {


    Location lolo;
    Entity target;

    public SchwerterausLicht() {

        name = "§cSchwerter aus Licht";
        cooldown = 20 * 50;
        //steprange = 20*10;
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.PROJECTILE);
        setLore("§7Feuert Schwerter in Blickrichtung, die#§7bei Kontakt mit Gegnern oder Blöcken#§7explodieren. Getroffene Gegner erhalten Schaden.");
        casttime = 40;

        setCanBeSilenced(true);

    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
		/*
		Player target = null;
		target = pointEntity(caster);
		if (target == null) {
			refund = true;
			dead = true;
			return;
		}
		*/
        Entity t = pointRealEntity(caster);

        if (t != null) {


            lolo = raycast(caster.getEyeLocation());
            loc = caster.getLocation();
            target = t;
            summonSwords(target, caster.getLocation().getDirection());


        } else {

            dead = true;
            refund = true;
        }


    }

    @Override
    public void cast() {
        if (dead)
            return;
        // TODO Auto-generated method stub
        if (caster.isSneaking()) {
            rotateGate();

        }

    }

    @Override
    public void launch() {
        if (dead)
            return;
        // TODO Auto-generated method stub

    }

    @Override
    public void move() {
        // TODO Auto-generated method stub

        if (caster.isSneaking()) {
            speed = 2F;
            for (SchwertausLicht sw : lightsword) {
                sw.rotate(caster.getLocation().getDirection());
            }

        } else {
            speed = 4F;
        }

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

    ArrayList<SchwertausLicht> lightsword = new ArrayList<SchwertausLicht>();

    public ArrayList<ArmorStand> summonSwords(Entity target, Vector dir) {

        ArrayList<ArmorStand> swords = new ArrayList<ArmorStand>();
        Location l = caster.getLocation();
        l.setDirection(dir);
        double r = 5;
        for (double t = 0; t <= 2 * Math.PI; ) {

            t = t + Math.PI / 9;

            double x = r * Math.cos(t + Math.PI - Math.PI / 18);
            double y = 1;
            double z = r * Math.sin(t + Math.PI - Math.PI / 18);
            Location j = l.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, caster.getLocation());

            l.add(v.getX(), v.getY(), v.getZ());
            l.add(l.getDirection().multiply(-3));
            lightsword.add(new SchwertausLicht(l.clone(), caster, target, name));
            ParUtils.createParticle(Particle.FLASH, l, 0, 0, 0, 1, 1);
            Vector ve = j.subtract(l).toVector();
            Location lala = l.clone();
            l.setDirection(lolo.toVector().subtract(lala.toVector()));

            l.add(l.getDirection().multiply(3));

            l.subtract(v.getX(), v.getY(), v.getZ());

        }


        return swords;
    }


    public void rotateGate() {
        int index = 0;
        double r = 5;
        Location l = caster.getLocation();
        for (double t = 0; t <= 2 * Math.PI; ) {

            t = t + Math.PI / 9;

            double x = r * Math.cos(t + Math.PI - Math.PI / 18);
            double y = 1;
            double z = r * Math.sin(t + Math.PI - Math.PI / 18);
            Location j = l.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, caster.getLocation());

            l.add(v.getX(), v.getY(), v.getZ());
            l.add(l.getDirection().multiply(-3));

            SchwertausLicht li = lightsword.get(index);
            Location la = l.clone();
            la.setDirection(caster.getLocation().getDirection());
            li.loc = la;
            li.velocity = caster.getLocation().getDirection();

            //ParUtils.createParticle(Particle.FLASH, l, 0, 0, 0, 1, 1);
            Vector ve = j.subtract(l).toVector();
            Location lala = l.clone();
            l.setDirection(lolo.toVector().subtract(lala.toVector()));

            l.add(l.getDirection().multiply(3));

            l.subtract(v.getX(), v.getY(), v.getZ());

            index++;

        }
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
