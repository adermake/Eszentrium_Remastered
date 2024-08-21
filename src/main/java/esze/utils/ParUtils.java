package esze.utils;

import esze.main.main;
import io.netty.util.internal.ThreadLocalRandom;
import org.bukkit.*;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Particle.DustTransition;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class ParUtils {

    public static void debug(Location loc) {
        createParticle(Particle.BLOCK_MARKER, loc, 0, 0, 0, 1, 0);
    }

    public static void createRedstoneParticle(Location loc, double spreadX, double spreadY, double spreadZ, int count,
                                              Color color, float size, Player p) {

        DustOptions dustOptions = new DustOptions(color, size);
        p.spawnParticle(Particle.DUST, loc, count, spreadX, spreadY, spreadZ, 0, dustOptions);

    }

    public static void createRedstoneParticle(Location loc, double spreadX, double spreadY, double spreadZ, int count,
                                              Color color, float size) {

        for (Player p : Bukkit.getOnlinePlayers()) {

            DustOptions dustOptions = new DustOptions(color, size);
            p.spawnParticle(Particle.DUST, loc, count, spreadX, spreadY, spreadZ, 0, dustOptions);
        }
    }


    public static void createDustTransition(Location loc, double spreadX, double spreadY, double spreadZ, int count,
                                            Color from, Color to, float size) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            DustTransition dustTransition = new DustTransition(from, to, size);
            p.spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, count, spreadX, spreadY, spreadZ, 0, dustTransition);
        }
    }

    public static void createBlockcrackParticle(Location loc, double spreadX, double spreadY, double spreadZ, int count,
                                                Material m) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            BlockData fallingDustData = m.createBlockData();
            p.spawnParticle(Particle.BLOCK, loc, count, spreadX, spreadY, spreadZ, 0, fallingDustData);
        }


    }


    public static void createParticle(Particle par, Location loc, double spreadX, double spreadY, double spreadZ,
                                      int count, double speed) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spawnParticle(par, loc, count, spreadX, spreadY, spreadZ, speed);
        }

    }

    public static void createParticleqareHorizontal(Particle par, Location loc, double size) {

        ParUtils.createParticle(par, loc.add(size, 0, 0), 0, 0, size - 0.1, 5, 0);
        ParUtils.createParticle(par, loc.add(0, 0, 0), 0, 0, size - 0.1, 5, 0);
        ParUtils.createParticle(par, loc.add(0, 0, size), size - 0.1, 0, 0, 5, 0);
        ParUtils.createParticle(par, loc.add(0, 0, 0), size - 0.1, 0, 0, 5, 0);

    }

    public static void createFlyingParticle(Particle par, Location loc, double spreadX, double spreadY, double spreadZ,
                                            int count, double speed, Vector v) {

        for (int i = 0; i < count; i++) {
            Location loctmp = loc.clone();
            double randX = 0;
            double randY = 0;
            double randZ = 0;
            if (spreadX != 0)
                randX = ThreadLocalRandom.current().nextDouble(-spreadX, spreadX);
            if (spreadY != 0)
                randY = ThreadLocalRandom.current().nextDouble(-spreadY, spreadY);
            if (spreadZ != 0)
                randZ = ThreadLocalRandom.current().nextDouble(-spreadZ, spreadZ);
            loctmp.add(randX, randY, randZ);
            createParticle(par, loctmp, v.getX(), v.getY(), v.getZ(), 0, speed);
        }

    }

    public static void parLineRedstone(Location l1C, Location l2C, Color color, float size, double thickness) {
        if (thickness == 0) {
            Bukkit.shutdown();
        }
        Location l1 = l1C.clone();
        Location l2 = l2C.clone();
        Vector v = l2.toVector().subtract(l1.toVector()).normalize();
        v.multiply(thickness);
        double counter = l1.distance(l2) / thickness;
        for (int i = 0; i < counter; i++) {
            l1.add(v);
            createRedstoneParticle(l1, 0, 0, 0, 1, color, size);
            if (l1.distance(l2) < 1) {
                break;
            }

        }

    }

    public static void parLineRedstone(Location l1C, Location l2C, Color color, float size, double thickness,
                                       Player p) {
        if (thickness == 0) {
            Bukkit.shutdown();
        }
        Location l1 = l1C.clone();
        Location l2 = l2C.clone();
        Vector v = l2.toVector().subtract(l1.toVector()).normalize();
        v.multiply(thickness);
        double counter = l1.distance(l2) / thickness;
        for (int i = 0; i < counter; i++) {
            l1.add(v);
            // pe.send(Bukkit.getOnlinePlayers(), l1.getX(), l1.getY(), l1.getZ(), 0, 0, 0,
            // 0, 1);
            createRedstoneParticle(l1, 0, 0, 0, 1, color, size, p);
            if (l1.distance(l2) < 1) {
                break;
            }

        }

    }

    public static void parLineRedstoneSpike(Location l1C, Location l2C, Color color, double thickness) {
        float size = 5;
        if (thickness == 0) {
            Bukkit.shutdown();
        }
        Location l1 = l1C.clone();
        Location l2 = l2C.clone();
        Vector v = l2.toVector().subtract(l1.toVector()).normalize();
        v.multiply(thickness);
        double counter = l1.distance(l2) / thickness;
        for (int i = 0; i < counter; i++) {
            l1.add(v);
            createRedstoneParticle(l1, 0, 0, 0, 1, color, size);
            size = size - 0.05F;
            if (l1.distance(l2) < 1) {
                break;
            }

        }

    }

    public static void parLine(Particle p, Location Cl1, Location Cl2, double spreadX, double spreadY, double spreadZ,
                               int count, double speed, double thickness) {
        if (thickness == 0) {
            Bukkit.shutdown();
        }
        Location ori = Cl1.clone();
        Location l1 = Cl1.clone();
        Location l2 = Cl2.clone();
        Vector v = l2.toVector().subtract(l1.toVector()).normalize();
        v.multiply(thickness);
        double counter = l1.distance(l2) / thickness;
        for (int i = 0; i < counter; i++) {
            l1.add(v);

            if (ori.distance(l2) - l1.distance(ori) < 0) {
                break;
            }
            createParticle(p, l1, spreadX, spreadY, spreadZ, count, speed);
        }

    }

    public static void parLineFly(Particle p, Location Cl1, Location Cl2, double speed, double thickness, Vector dir) {
        Location l1 = Cl1.clone();
        Location l2 = Cl2.clone();
        Vector v = l2.toVector().subtract(l1.toVector()).normalize();
        v.multiply(thickness);
        double counter = l1.distance(l2) / thickness;
        for (int i = 0; i < counter; i++) {
            l1.add(v);
            createParticle(p, l1, (float) dir.getX(), (float) dir.getY(), (float) dir.getZ(), 0, speed);
            if (l1.distance(l2) < 1) {
                break;
            }

        }

    }

    public static void parCube(Particle pt, Location l1, double size, double count) {

        ParUtils.parVectorLine(pt, l1.clone().add(size / 2, size / 2, size / 2), new Vector(-size, 0, 0), count);
        ParUtils.parVectorLine(pt, l1.clone().add(size / 2, size / 2, size / 2), new Vector(0, -size, 0), count);
        ParUtils.parVectorLine(pt, l1.clone().add(size / 2, size / 2, size / 2), new Vector(0, 0, -size), count);
        ParUtils.parVectorLine(pt, l1.clone().add(-size / 2, -size / 2, -size / 2), new Vector(size, 0, 0), count);
        ParUtils.parVectorLine(pt, l1.clone().add(-size / 2, -size / 2, -size / 2), new Vector(0, size, 0), count);
        ParUtils.parVectorLine(pt, l1.clone().add(-size / 2, -size / 2, -size / 2), new Vector(0, 0, size), count);

        ParUtils.parVectorLine(pt, l1.clone().add(size / 2, -size / 2, size / 2), new Vector(-size, 0, 0), count);
        ParUtils.parVectorLine(pt, l1.clone().add(size / 2, -size / 2, size / 2), new Vector(0, 0, -size), count);

        ParUtils.parVectorLine(pt, l1.clone().add(-size / 2, size / 2, -size / 2), new Vector(size, 0, 0), count);
        ParUtils.parVectorLine(pt, l1.clone().add(-size / 2, size / 2, -size / 2), new Vector(0, 0, size), count);

        ParUtils.parVectorLine(pt, l1.clone().add(size / 2, size / 2, -size / 2), new Vector(0, -size, 0), count);
        ParUtils.parVectorLine(pt, l1.clone().add(-size / 2, size / 2, size / 2), new Vector(0, -size, 0), count);

    }


    public static void parVectorLine(Particle pt, Location l1, Vector vec, double split) {
        for (double i = 0; i < split; i++) {
            double m = i / split;
            // Bukkit.broadcastMessage(""+m);
            createParticle(pt, l1.clone().add(vec.clone().multiply(m)), 0, 0, 0, 1, 0);
        }

    }

    public static void chargeDot(Location l, Particle pe, double speed, int spread) {
        Location loc = l.clone().add(randInt(-spread, spread), randInt(-spread, spread), randInt(-spread, spread));

        createParticle(pe, loc, l.getX() - loc.getX(), l.getY() - loc.getY(), l.getZ() - loc.getZ(), 0, speed);

    }

    public static void chargeDot(Location l, Particle pe, double speed, int spread, int count) {

        for (int i = 0; i < count; i++) {
            ParUtils.chargeDot(l.clone(), pe, speed, count);
        }

    }

    public static void parKreisDot(Particle pe, final Location l, double radius, double offset, double speed,
                                   Vector rotV) {

        double r = radius;
        Location loc = l.clone();
        Location rot = l.clone().setDirection(rotV);

        double ti = radius * 12;
        if (radius < 1)
            ti = 6;
        ti = ti > 600 ? 600 : ti;

        for (double t = 0; t <= ti; ) {

            t = t + Math.PI / randInt(4, 16);

            double x = r * Math.cos(t);
            double y = offset;
            double z = r * Math.sin(t);
            Location j = loc.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, rot);

            loc.add(v.getX(), v.getY(), v.getZ());

            Vector ve = j.subtract(loc).toVector();
            createParticle(pe, loc, (float) (loc.getX() - l.clone().getX()), (float) (loc.getY() - l.clone().getY()),
                    (float) (loc.getZ() - l.clone().getZ()), 0, (float) speed);

            loc.subtract(v.getX(), v.getY(), v.getZ());

        }

    }

    public static void parKreisDir(Particle pe, final Location l, double radius, double offset, double speed,
                                   Vector rotV, Vector dir) {

        double r = radius;
        Location loc = l.clone();
        Location rot = l.clone().setDirection(rotV);

        double ti = radius * 6;
        if (radius < 1)
            ti = 6;
        ti = ti > 100 ? 100 : ti;
        ti = ti > 100 ? 100 : ti;

        for (double t = 0; t <= ti; ) {

            t = t + Math.PI / randInt(4, 16);

            double x = r * Math.cos(t);
            double y = offset;
            double z = r * Math.sin(t);
            Location j = loc.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, rot);

            loc.add(v.getX(), v.getY(), v.getZ());

            Vector ve = j.subtract(loc).toVector();

            createParticle(pe, loc, (float) dir.getX(), (float) dir.getY(), (float) dir.getZ(), 0, (float) speed);

            loc.subtract(v.getX(), v.getY(), v.getZ());

        }

    }

    public static void parKreisDirSolid(Particle pe, final Location l, double radius, double offset, double speed,
                                        Vector rotV, Vector dir) {

        double r = radius;
        Location loc = l.clone();
        Location rot = l.clone().setDirection(rotV);

        double ti = 100;
        if (radius < 1)
            ti = 6;
        ti = ti > 100 ? 100 : ti;
        ti = ti > 100 ? 100 : ti;

        for (double t = 0; t <= ti; ) {

            t = t + Math.PI / randInt(4, 16);

            double x = r * Math.cos(t);
            double y = 1 + offset;
            double z = r * Math.sin(t);
            Location j = loc.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, rot);

            loc.add(v.getX(), v.getY(), v.getZ());

            Vector ve = j.subtract(loc).toVector();

            createParticle(pe, loc, (float) dir.getX(), (float) dir.getY(), (float) dir.getZ(), 0, (float) speed);

            loc.subtract(v.getX(), v.getY(), v.getZ());

        }

    }

    public static void parKreisSolidRedstone(Color color, float size, final Location l, double radius, double offset,
                                             double speed, Vector rotV) {

        double r = radius;
        Location loc = l.clone();
        Location rot = l.clone().setDirection(rotV);

        double ti = 100;
        if (radius < 1)
            ti = 6;
        ti = ti > 100 ? 100 : ti;
        ti = ti > 100 ? 100 : ti;

        for (double t = 0; t <= ti; ) {

            t = t + Math.PI / randInt(4, 16);

            double x = r * Math.cos(t);
            double y = offset;
            double z = r * Math.sin(t);
            Location j = loc.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, rot);

            loc.add(v.getX(), v.getY(), v.getZ());

            Vector ve = j.subtract(loc).toVector();

            createRedstoneParticle(loc, 0, 0, 0, 0, color, size);
            loc.subtract(v.getX(), v.getY(), v.getZ());

        }

    }

    public static void parKreisRedstone(Color color, float size, final Location l, double radius, double offset,
                                        double speed, double thickness, Vector rotV) {

        double r = radius;
        Location loc = l.clone();
        Location rot = l.clone().setDirection(rotV);

        double ti = thickness;

        for (double t = 0; t <= Math.PI * 2; ) {

            t = t + Math.PI / ti;

            double x = r * Math.cos(t);
            double y = offset;
            double z = r * Math.sin(t);
            Location j = loc.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, rot);

            loc.add(v.getX(), v.getY(), v.getZ());

            Vector ve = j.subtract(loc).toVector();

            // createParticle(pe, loc, (float)dir.getX(),(float)
            // dir.getY(),(float)dir.getZ(), 0, (float)speed);
            createRedstoneParticle(loc, 0, 0, 0, 0, color, size);
            loc.subtract(v.getX(), v.getY(), v.getZ());

        }

    }

    public static void parKreisRedstone(Color color, float size, final Location l, double radius, double offset,
                                        double speed, double thickness, Vector rotV, Player p) {

        double r = radius;
        Location loc = l.clone();
        Location rot = l.clone().setDirection(rotV);

        double ti = thickness;

        for (double t = 0; t <= Math.PI * 2; ) {

            t = t + Math.PI / ti;

            double x = r * Math.cos(t);
            double y = offset;
            double z = r * Math.sin(t);
            Location j = loc.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, rot);

            loc.add(v.getX(), v.getY(), v.getZ());

            Vector ve = j.subtract(loc).toVector();

            // createParticle(pe, loc, (float)dir.getX(),(float)
            // dir.getY(),(float)dir.getZ(), 0, (float)speed);
            createRedstoneParticle(loc, 0, 0, 0, 0, color, size, p);
            loc.subtract(v.getX(), v.getY(), v.getZ());

        }

    }

    public static void dashParticleTo(Particle par, Entity p, Location l) {
        Location loc = l.clone();

        loc.add(randInt(-16, 16), randInt(-16, 16), randInt(-16, 16));

        new BukkitRunnable() {
            public void run() {
                Vector flyTo = p.getLocation().toVector().subtract(loc.toVector()).normalize();
                createParticle(par, loc, 0, 0, 0, 1, 0);
                loc.add(p.getLocation().toVector().subtract(loc.toVector()).normalize().multiply(1));
                if (loc.distance(p.getLocation()) < 1) {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(esze.main.main.plugin, 1, 1);
    }

    public static void dashParticleToRedstone(Entity p, Location l, double spread, Color c, float size) {
        Location loc = l.clone();
        if (spread != 0)
            loc.add(randDouble(-spread, spread), randDouble(-spread, spread), randDouble(-spread, spread));

        new BukkitRunnable() {
            public void run() {
                Vector flyTo = p.getLocation().toVector().subtract(loc.toVector()).normalize();
                createRedstoneParticle(loc, 0, 0, 0, 1, c, size);
                loc.add(p.getLocation().toVector().subtract(loc.toVector()).normalize().multiply(1));
                if (loc.distance(p.getLocation()) < 1) {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(esze.main.main.plugin, 1, 1);
    }

    public static void auraParticle(Particle par, Entity p, double speed, int time) {
        Location loc = p.getLocation();

        loc.add(0, randInt(1, 16) / 16, 0);

        new BukkitRunnable() {
            int t = 0;

            public void run() {
                t++;
                createParticle(par, loc, 0, 0, 0, 1, 0);
                loc.add(randDouble(-speed, speed), randDouble(-speed, speed), randDouble(-speed, speed));
                if (t > time)
                    this.cancel();
            }
        }.runTaskTimerAsynchronously(esze.main.main.plugin, 1, 1);
    }

    /*
     * public static ArrayList<Location> grabBlocks(Location l,int count) {
     * ArrayList<Location> locList = new ArrayList<Location>(); ArrayList<Location>
     * locSecList = new ArrayList<Location>(); for (BlockFace bf :
     * BlockFace.values()) {
     *
     * if (MathUtils.randInt(1, 3) == 1) { continue; } else {
     * Bukkit.broadcastMessage("L"); }
     *
     * if (count>1) { locSecList =
     * grabBlocks(l.getBlock().getRelative(bf).getLocation(),count-1); } else {
     * locSecList.add(l.getBlock().getRelative(bf).getLocation()); }
     *
     *
     * for (Location locSec : locSecList ) { Bukkit.broadcastMessage(""+locSec);
     * locList.add(locSec); } } return locList; }
     */
    public static ArrayList<Location> grabBlocks(Location l, int count, int radius) {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (int i = count; i > 0; i--) {
            Location loc = l.clone();
            loc.add(MathUtils.randInt(-radius, radius), 0, MathUtils.randInt(-radius, radius));

            locs.add(loc);
        }
        return locs;
    }

    public static int randInt(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    public static double randDouble(double min, double max) {
        double randomNum = ThreadLocalRandom.current().nextDouble(min, max);
        return randomNum;
    }

    public static Vector randVector() {
        int ix = randInt(-100, 100);
        int iy = randInt(-100, 100);
        int iz = randInt(-100, 100);
        double dx = ((double) ix) / 100;
        double dy = ((double) iy) / 100;
        double dz = ((double) iz) / 100;
        Vector v = new Vector(dx, dy, dz);
        return v;

    }

    public static ArrayList<Location> preCalcCircle(Location l, double radius, Vector rotV, double offset) {
        ArrayList<Location> locs = new ArrayList<Location>();

        double r = radius;
        Location loc = l.clone();
        Location rot = l.clone().setDirection(rotV);

        double ti = radius * 6;
        ti = ti > 100 ? 100 : ti;

        for (double t = 0; t <= ti; ) {

            t = t + Math.PI / randInt(4, 16);

            double x = r * Math.cos(t);
            double y = 1 + offset;
            double z = r * Math.sin(t);
            Location j = loc.clone();
            Vector v = new Vector(x, y, z);
            Matrix.rotateMatrixVectorFunktion(v, rot);

            loc.add(v.getX(), v.getY(), v.getZ());

            Vector ve = j.subtract(loc).toVector();
            locs.add(loc.clone());

            loc.subtract(v.getX(), v.getY(), v.getZ());

        }
        return locs;
    }

    public static Location stepCalcCircle(Location l, double r, Vector rotV, double offset, double steps) {
        double t = (Math.PI / 22D) * ((double) steps);
        Location loc = l.clone();
        Location rot = loc.clone().setDirection(rotV);
        double x = r * Math.cos(t);
        double y = offset;
        double z = r * Math.sin(t);
        Location j = loc.clone();
        Vector v = new Vector(x, y, z);
        Matrix.rotateMatrixVectorFunktion(v, rot);

        loc.add(v.getX(), v.getY(), v.getZ());

        return loc;

    }

    public static Location stepCalcSpiral(Location l, double r, Vector rotV, double offset, double steps) {
        double t = (Math.PI / 22) * ((double) steps);
        Location loc = l.clone();
        Location rot = loc.clone().setDirection(rotV);
        r = r - steps / 44;
        double x = r * Math.cos(t);
        double y = 1 + offset;
        double z = r * Math.sin(t);
        Location j = loc.clone();
        Vector v = new Vector(x, y, z);
        Matrix.rotateMatrixVectorFunktion(v, rot);

        loc.add(v.getX(), v.getY(), v.getZ());

        return loc;

    }

    // SPECIAL EFFECTS

    public static ArrayList<Item> dropItemEffectRandomVector(Location loc, Material m, int count, int delay,
                                                             double power) {
        ArrayList<Item> items = new ArrayList<Item>();
        for (int i = 0; i < count; i++) {
            ItemStack im = new ItemStack(m);
            ItemMeta imet = im.getItemMeta();
            imet.setDisplayName("" + i);
            im.setItemMeta(imet);
            Item it = loc.getWorld().dropItem(loc, im);

            it.setCustomName("" + i);
            it.setVelocity(randVector().multiply(power));
            it.setPickupDelay(1000);
            items.add(it);
        }

        new BukkitRunnable() {
            public void run() {
                for (Item i : items) {
                    i.remove();
                }
            }
        }.runTaskLater(main.plugin, delay);
        return items;
    }

    public static ArrayList<Item> dropItemEffectVector(Location loc, Material m, int count, int delay, double power,
                                                       Vector dir) {
        ArrayList<Item> items = new ArrayList<Item>();
        for (int i = 0; i < count; i++) {
            ItemStack im = new ItemStack(m);

            ItemMeta imet = im.getItemMeta();
            imet.setDisplayName("" + i);
            im.setItemMeta(imet);
            Item it = loc.getWorld().dropItem(loc, im);
            it.setVelocity(dir.multiply(power));
            it.setPickupDelay(1000);
            items.add(it);
        }

        new BukkitRunnable() {
            public void run() {
                for (Item i : items) {
                    i.remove();
                }
            }
        }.runTaskLater(main.plugin, delay);

        return items;
    }

    public static ArrayList<Item> dropItemEffectVector(Location loc, Material m, int count, int delay, double power,
                                                       Vector dir, int idoffset) {
        ArrayList<Item> items = new ArrayList<Item>();
        for (int i = 0; i < count; i++) {
            ItemStack im = new ItemStack(m);

            ItemMeta imet = im.getItemMeta();
            imet.setDisplayName("" + (i + idoffset));
            im.setItemMeta(imet);
            Item it = loc.getWorld().dropItem(loc, im);

            it.setVelocity(dir.multiply(power));
            it.setPickupDelay(1000);
            items.add(it);
        }

        new BukkitRunnable() {
            public void run() {
                for (Item i : items) {
                    i.remove();
                }
            }
        }.runTaskLater(main.plugin, delay);

        return items;
    }

    public static void pullItemEffectVector(Location loc, Material m, int delay, Location toLocation, double speed) {

        ItemStack im = new ItemStack(m);
        Item it = loc.getWorld().dropItem(loc, im);

        it.setPickupDelay(1000 + delay);

        new BukkitRunnable() {
            int t = 0;

            public void run() {

                t++;
                it.setVelocity(toLocation.toVector().subtract(it.getLocation().toVector()).normalize().multiply(speed));
                if (t > delay || it.getLocation().distance(toLocation) < 0.3) {
                    this.cancel();
                    it.remove();
                }
            }
        }.runTaskTimer(main.plugin, 1, 1);

    }

    public static void pullItemEffectVector(Location loc, Material m, int delay, Location toLocation, double speed,
                                            int offsetid) {

        ItemStack im = new ItemStack(m);

        ItemMeta imet = im.getItemMeta();
        imet.setDisplayName("" + (offsetid));
        im.setItemMeta(imet);
        Item it = loc.getWorld().dropItem(loc, im);
        it.setPickupDelay(1000 + delay);

        new BukkitRunnable() {
            int t = 0;

            public void run() {

                t++;
                it.setVelocity(toLocation.toVector().subtract(it.getLocation().toVector()).normalize().multiply(speed));
                if (t > delay || it.getLocation().distance(toLocation) < 0.3) {
                    this.cancel();
                    it.remove();
                }
            }
        }.runTaskTimer(main.plugin, 1, 1);

    }

    public static void pullItemEffectVector(Location loc, Material m, int delay, Entity ent, double speed) {

        ItemStack im = new ItemStack(m);
        Item it = loc.getWorld().dropItem(loc, im);

        it.setPickupDelay(1000 + delay);

        new BukkitRunnable() {
            int t = 0;

            public void run() {

                t++;
                it.setVelocity(
                        ent.getLocation().toVector().subtract(it.getLocation().toVector()).normalize().multiply(speed));
                if (t > delay || it.getLocation().distance(ent.getLocation()) < 0.3) {
                    this.cancel();
                    it.remove();
                }
            }
        }.runTaskTimer(main.plugin, 1, 1);

    }

    public static void debugRay(Location locC) {
        Location loc = locC.clone();
        for (double t = 1; t <= 10; t = t + 0.5) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);

            ParUtils.createParticle(Particle.FLAME, loc, 0, 0, 0, 1, 0);

            loc.subtract(x, y, z);
        }

    }

    /*
     * public static Particle getNmsParticle(EszeParticle par) { switch(par){ case
     * ENTITY_EFFECT: return Particle.a; case ANGRY_VILLAGER: return Particle.b;
     * case BARRIER: return Particle.c; case LIGHT: return Particle.d; case BLOCK:
     * return Particle.e; case BUBBLE: return Particle.f; case CLOUD: return
     * Particle.g; case CRIT: return Particle.h; case case WATER_SPLASH: case
     * WATER_WAKE: case SUSPENDED: case SUSPENDED_DEPTH: case CRIT: case CRIT_MAGIC:
     * case SMOKE_NORMAL: case SMOKE_LARGE: case SPELL: case SPELL_INSTANT: case
     * SPELL_MOB: case SPELL_MOB_AMBIENT: case SPELL_WITCH: case DRIP_WATER: case
     * DRIP_LAVA: case VILLAGER_ANGRY: case VILLAGER_HAPPY: case TOWN_AURA: case
     * NOTE: case PORTAL: case ENCHANTMENT_TABLE: case FLAME: case LAVA: case CLOUD:
     * case REDSTONE: case SNOWBALL: case SNOW_SHOVEL: case SLIME: case HEART: case
     * BARRIER: case ITEM_CRACK: case BLOCK_CRACK: case BLOCK_DUST: case WATER_DROP:
     * case MOB_APPEARANCE: case DRAGON_BREATH: case END_ROD: case DAMAGE_INDICATOR:
     * case SWEEP_ATTACK: case FALLING_DUST: case TOTEM: case SPIT: case SQUID_INK:
     * case BUBBLE_POP: case CURRENT_DOWN: case BUBBLE_COLUMN_UP: case NAUTILUS:
     * case DOLPHIN: case SNEEZE: case CAMPFIRE_COSY_SMOKE: case
     * CAMPFIRE_SIGNAL_SMOKE: case COMPOSTER: case FLASH: case FALLING_LAVA: case
     * LANDING_LAVA: case FALLING_WATER: case DRIPPING_HONEY: case FALLING_HONEY:
     * case LANDING_HONEY: case FALLING_NECTAR: case SOUL_FIRE_FLAME: case ASH: case
     * CRIMSON_SPORE: case WARPED_SPORE: case SOUL: case DRIPPING_OBSIDIAN_TEAR:
     * case FALLING_OBSIDIAN_TEAR: case LANDING_OBSIDIAN_TEAR: case REVERSE_PORTAL:
     * case WHITE_ASH: case LEGACY_BLOCK_CRACK: case LEGACY_BLOCK_DUST: case
     * LEGACY_FALLING_DUST: default: return Particle.b;
     *
     * } }
     */
}
