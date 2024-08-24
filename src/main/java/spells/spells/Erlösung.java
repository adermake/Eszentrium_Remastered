package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Erlösung extends Spell {


    public Erlösung() {
        name = "§8Erlösung";
        steprange = 20 * 22;
        speed = 1;
        hitPlayer = true;
        hitEntity = true;
        hitSpell = false;
        hitboxSize = 3;
        multihit = true;

        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*60
        );

        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.PROJECTILE);
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        target = pointEntity(caster, 70);

        if (target == null) {
            refund = true;
            dead = true;
        }

    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }


    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    double range = 10;
    double rangeadder = 1;
    double momentum = 0;
    Entity target;
    Vector vel = new Vector(0, -3, 0);

    @Override
    public void move() {
        // TODO Auto-generated method stub

        Location lastLoc = loc.clone();
        speed = (int) vel.length();
        loc = loc.add(vel.clone().normalize());

        if (target != null) {

            Vector dir = target.getLocation().toVector().subtract(loc.toVector()).normalize();
            vel = vel.add(dir);
        }
        if (vel.length() > 4) {
            vel.normalize().multiply(4);
        }

        ParUtils.createParticle(Particle.CRIT, loc, 0.1, 0.1, 0.1, 40, 0.01);
        ParUtils.createParticle(Particle.BUBBLE_POP, loc, 0.1, 0.1, 0.1, 40, 0.01);
        ParUtils.createParticle(Particle.END_ROD, loc, 0.1, 0.1, 0.1, 1, 0.01);

    }

    int pointCooldown = 0;

    @Override
    public void display() {
        // TODO Auto-generated method stub


    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        tagPlayer(p);
        ParUtils.parKreisDot(Particle.END_ROD, loc, 3, 0, 0.25, vel);
        ParUtils.createParticle(Particle.FLASH, loc, 0.1, 0.1, 0.1, 1, 0.01);
        p.setVelocity(vel.multiply(2).add(new Vector(0, 1, 0)));
        playSound(Sound.BLOCK_BELL_USE, loc, 60, 1);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        ParUtils.parKreisDot(Particle.END_ROD, loc, 3, 0, 0.25, vel);
        ParUtils.createParticle(Particle.FLASH, loc, 0.1, 0.1, 0.1, 1, 0.01);
        ent.setVelocity(vel.multiply(2));
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

    public Location loc(Player p, double range) {
        Location loc = p.getLocation();
        for (int t = 1; t <= range; t++) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);
            Location lo = loc.clone();
            if (t >= range - 1) {
                return loc;
            }


            loc.subtract(x, y, z);
        }
        return null;

    }

}
