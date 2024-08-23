package spells.spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.Actionbar;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;


public class PlatformRise extends Spell {

    public PlatformRise() {
        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                10
        );
    }


    Cat core;
    Location ori;
    Vector dirOir;
    double dist;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        core = getShulkerCore();
        ori = core.getLocation().clone();
        dirOir = caster.getLocation().getDirection();
        dist = caster.getLocation().distance(ori);
        core.setGravity(false);
        core.setInvulnerable(true);
        core.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * 60 * 6, 200));
        core.setCustomName("Core");
        core.setPersistent(true);
        core.setTamed(true);
        core.setSitting(true);
        core.setInvisible(true);
        core.setSilent(true);
        if (core == null) {
            dead = true;
            return;
        }

        clearswap();
        moveBox(2, 100, 2, 8);
        dead = true;
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    @Override
    public void move() {
        if (!dead) {

        }
        // TODO Auto-generated method stub

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

    public void moveBox(int hRad, int vDown, int vUp, int steps) {

        Location center = core.getLocation();
        HashMap<ArmorStand, Vector> blocks = new HashMap<ArmorStand, Vector>();

        for (int x = -hRad; x <= hRad; x++) {
            for (int z = -hRad; z <= hRad; z++) {
                for (int y = -vDown; y <= vUp; y++) {
                    Block b = center.clone().add(x, y, z).getBlock();
                    if (b.getType().isSolid() && b.getType() != Material.BLACKSTONE && b.getType() != Material.BLACKSTONE_STAIRS && b.getType() != Material.SPRUCE_LOG) {


                        FallingBlock fb = spawnFallingBlock(b.getLocation().add(0.5, 0, 0.5), b.getType());
                        fb.setInvulnerable(true);

                        ArmorStand ar = createArmorStand(b.getLocation().add(0.5, -0.8, 0.5));
                        ar.setSmall(true);
                        ar.setInvisible(true);
                        ar.addPassenger(fb);
                        ar.setGravity(true);
                        fb.setGravity(false);
                        blocks.put(ar, ar.getLocation().toVector().subtract(core.getLocation().toVector()));
                        b.setType(Material.AIR);
                    }
                }
            }
        }
        Player closest = null;
        double d = 1000000;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLocation().distance(core.getLocation()) < d) {
                d = p.getLocation().distance(core.getLocation());
                closest = p;
            }
        }
        Vector r = new Vector(0, 0, 0);
        if (closest.getLocation().distance(core.getLocation()) > hRad + 3) {
            closest = null;

        } else {
            r = closest.getLocation().toVector().subtract(ori.toVector());
        }
        final Player move = closest;
        final Vector rel = r;
        System.out.print("GO");
        new BukkitRunnable() {
            int ticks = 0;
            int h = -255;
            Location l;

            @Override
            public void run() {


                //CHANGE
                Vector v = caster.getLocation().getDirection().multiply(caster.getLocation().distance(core.getLocation()));

                double x = Math.sqrt(v.length() * v.length() - v.getZ() * v.getZ());
                double y = v.getY();
                Vector v1 = new Vector(x, y, 0);

                Location other = caster.getLocation().add(v1.clone()).setDirection(new Vector(0, 1, 0));
                Location first = caster.getLocation().setDirection(v1);
                //Bukkit.broadcastMessage(""+);
                int YY = (int) GetIntersection(first, other).getY() + 1;
                if (YY <= 109)
                    YY = 109;
                if (YY > 250)
                    YY = 250;
                //Bukkit.broadcastMessage("YY "+YY);
                Location l3 = ori.clone();
                if (caster.isSneaking()) {
                    if (YY < ori.getY()) {
                        YY = (int) (ori.getY() - 1);
                    } else {
                        YY = (int) (ori.getY() + 1);
                    }

                }
                if (h != YY) {
                    playGlobalSound(Sound.BLOCK_GRINDSTONE_USE, 1F, 0.05F);
                    h = YY;
                }
                l3.setY(YY);
                ParUtils.createParticle(Particle.CLOUD, l3, 0, 0, 0, 1, 0);

                doPin(core, l3, 2);

                if (move != null) {
                    doPin(move, core.getLocation().add(rel));
                }

                for (ArmorStand fb : blocks.keySet()) {
                    fb.getPassengers().get(0).setTicksLived(1);

                    doPin(fb, core.getLocation().add(blocks.get(fb)), 2F);
                }


                double points = Math.round(core.getLocation().getY()) - Math.round(ori.getY());
                if (points > 0) {
                    Actionbar bar = new Actionbar("§a+" + points);
                    bar.send(caster);
                } else if (points < 0) {
                    Actionbar bar = new Actionbar("§c" + points);
                    bar.send(caster);
                } else {
                    Actionbar bar = new Actionbar("§7" + points);
                    bar.send(caster);
                }


                ticks++;
                if (swap()) {

                    Location l2 = core.getLocation();
                    l2.setY(Math.round(l2.getY()));
                    //l2.setY( core.getLocation().getBlock().getY());
                    core.teleport(l2);
                    core.setVelocity(core.getVelocity().multiply(0));

                    if (move != null) {
                        move.teleport(l2.clone().add(rel).setDirection(move.getLocation().getDirection()));
                        move.setVelocity(move.getVelocity().multiply(0));
                    }


                    for (ArmorStand ar : blocks.keySet()) {

                        FallingBlock fb = (FallingBlock) ar.getPassengers().get(0);

                        fb.remove();
                        ar.remove();

                        Location fLoc = core.getLocation().add(blocks.get(ar)).add(0, 1, 0);
                        int yR = (int) Math.round(fLoc.getY());
                        fLoc.setY(yR);
                        fLoc.getBlock().setType(fb.getMaterial());


                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(main.plugin, 1, 1);

    }


    public void fillRow(Location l1, Location l2, Material m) {
        for (int i = 100; i < 255; i++) {
            if (i < l2.getBlockY()) {

            }
        }
    }

    public Cat getShulkerCore() {

        int range = 300;
        int toleranz = 3;
        Location loc = caster.getLocation();
        for (double t = 1; t <= range; t = t + 0.5) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);
            Location lo = loc.clone();

            // Particel

            for (Entity ent : caster.getWorld().getEntities()) {
                if (ent instanceof Cat) {
                    if (ent.getLocation().distance(loc) < 3) {
                        dist = caster.getLocation().distance(ent.getLocation());
                        return (Cat) ent;
                    }
                }
            }

            // SUBTRACTING LOCATION UM den prozess
            // von vorne zu
            // starten
            loc.subtract(x, y, z);

        }

        return null;

    }

    public Location GetIntersection(Location first, Location other) {
        // Don't worry about this, it's the k1 that we calculated before
        double k1 = (first.getDirection().getX() * (first.getY() - other.getY())
                + first.getDirection().getY() * (other.getX() - first.getX()))
                / (other.getDirection().getY() * first.getDirection().getX() - first.getDirection().getY() * other.getDirection().getX());

        if (k1 == Double.NaN || Double.isInfinite(k1)) {
            // strange values indicate that lines are skew or overlapping
            return null;
        } else {
            return other.clone().add(other.getDirection().multiply(k1));

        }
    }

}
