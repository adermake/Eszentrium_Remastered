package monuments;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import spells.spellcore.Spell;

public class Soul {

    public static ArrayList<Soul> allSouls = new ArrayList<Soul>();
    Entity ent;
    public Player follow;
    Vector vel;
    float step = 0;
    float steprange = 40;
    public static int soulID;
    Location loc;

    public Soul(Player soulOf, Player follow, Location loc) {
        //ItemStack is = ItemStackUtils.getPlayerHead(soulOf);
        //ItemMeta im = is.getItemMeta();
        //im.setDisplayName("" + soulID);
        //is.setItemMeta(im);
        //Item item = soulOf.getWorld().dropItem(loc, is);
	
		/*
		ArmorStand s = (ArmorStand) soulOf.getWorld().spawnEntity(loc.clone().add(0, -2, 0), EntityType.ARMOR_STAND);
		s.setSmall(true);
		s.setInvulnerable(true);
		s.setInvisible(true);
		s.setCollidable(false);
		Spell.disableEntityHitboxStatic(s);
		s.getEquipment().setHelmet(new ItemStack(Material.WITHER_SKELETON_SKULL));
		*/

        ent = follow.getWorld().spawnEntity(loc.clone(), EntityType.VEX);
        ent.setInvulnerable(true);
        ((Vex) ent).getEquipment().setItemInMainHand(null);
        soulID++;

        //s.addPassenger(item);

        this.loc = loc.clone();
        this.follow = follow;
        steprange = (int) follow.getLocation().distance(loc) / 2 + Spell.randInt(0, 10);

        vel = follow.getLocation().getDirection().multiply(1);
        vel.add(Spell.randVector().multiply(3));
        vel.add(new Vector(0, 2, 0));
        vel.multiply(0.5);
        ent.setVelocity(vel);
        allSouls.add(this);

    }

    boolean dead = false;


    public boolean update() {
        if (dead)
            return true;

        Vector v = follow.getLocation().toVector().subtract(loc.toVector());
        float lifetime = (float) (steprange - step);
        if (lifetime > 0) {

            //vel = vel.clone().midpoint(v.clone()).normalize().multiply(1);
            vel = homeVector(lifetime, vel.clone(), v);
            loc.add(vel);
        } else {


            //ent.getPassengers().get(0).remove();
            dead = true;
            //ent.remove();
            ent.remove();

            return true;
        }
        doPin(ent, loc, 2);
        //setArmorstandHeadPos(ent, vel.clone().normalize(), 0, 0);
        ParUtils.createParticle(Particle.WHITE_ASH, loc, 0.02, 0.02, 0.02, 5, 0);
        //ParUtils.createRedstoneParticle(loc,0, 0, 0, 1, Color.NAVY, 0.2F);
        step++;
        return false;
    }

    public void setArmorstandHeadPos(ArmorStand a, Vector dir, float offsetPitch, float offsetYaw) {

        Location del = a.getLocation().setDirection(dir);
        //Bukkit.broadcastMessage(""+del.getPitch());
        a.setHeadPose(new EulerAngle(Math.toRadians(del.getPitch() + offsetPitch), Math.PI / 2 + Math.toRadians(del.getYaw() + offsetYaw), 0));
    }

    public Vector doPin(Entity e, Location toLocation, double power) {
        // multiply default 0.25

        if (toLocation.toVector().distance(e.getLocation().toVector()) > 0) {
            double s = e.getLocation().distance(toLocation) / 5;
            e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s * power));
            return toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s);
        }

        return new Vector(0, 0, 0);
    }

    public void getLocation() {
        ent.getLocation();
    }

    public Entity getEntity() {
        return ent;
    }

    public Vector homeVector(float lifetime, Vector current, Vector aim) {
        double per = step / steprange;
        double angle = current.clone().angle(aim.clone());

        Vector edge = current.clone().normalize().crossProduct(aim.clone().normalize());

        Vector ret = current.clone().normalize().rotateAroundAxis(edge.normalize(), angle * per);

        ret.normalize().multiply(aim.length() / lifetime);
        return ret;
		/*
		// Bukkit.broadcastMessage("Life " + lifetime);
		/// Bukkit.broadcastMessage("Cur "+current);
		// Bukkit.broadcastMessage("A "+aim);
		float acc =5/(float)steprange;
		Vector vNorm = current.clone().normalize();
		Vector dNorm = aim.clone().normalize();

		Vector v = vNorm.clone().subtract(dNorm);
		// Bukkit.broadcastMessage("Z " +v);
		v = v.clone().multiply((1 - lifetime) * acc);
		// Bukkit.broadcastMessage("A " +v);
		v = vNorm.clone().subtract(v);
		// Bukkit.broadcastMessage("B " +v);
		v = v.normalize();
		// Bukkit.broadcastMessage("C " +v);
		v = v.multiply(aim.length() / lifetime);
		// Bukkit.broadcastMessage("D " +v);
		// Vector v =
		// current.normalize().subtract((current.normalize().subtract(aim.normalize())).multiply(1/lifetime)).normalize().multiply(aim.length()/lifetime);
		// Bukkit.broadcastMessage("Out "+v);
		return v;
*/
    }

}
