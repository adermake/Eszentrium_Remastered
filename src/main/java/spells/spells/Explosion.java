package spells.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Explosion extends Spell {

    public Explosion() {
        name = "§cExplosion";
        hitSpell = false;
        casttime = 20 * 5;

        spellDescription = new SpellDescription(
                "Lädt einen Angriff auf. Nach kurzer Zeit explodiert der Spieler und fügt allen Gegnern in der Nähe Schaden zu. Je früher die Explosion ausgelöst wird, desto geringer fallen Reichweite, Schaden und Höhe aus.",
                "",
                null,
                null,
                "Zündet den Zauber vorzeitig und katapultiert den Spieler hoch.",
                null,
                20*35
        );
        
        //addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.AURA);
        addSpellType(SpellType.SELFCAST);
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        kRotLoc = caster.getLocation();
        loc = caster.getLocation();

    }

    int s = 0;
    Location kRotLoc;
    double power = 0;
    boolean swapped = false;

    @Override
    public void cast() {
        power++;

        // TODO Auto-generated method stub
        Vector dir = loc.getDirection();
        loc = caster.getLocation();
        loc.setDirection(dir);
        SoundUtils.playSound(Sound.BLOCK_SMOKER_SMOKE, loc, 1, 0.3F);


        //ParUtils.auraParticle(Particle.SMOKE_LARGE, caster, 0.6F, 20*1);

        //ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, loc, 0.5, 1, 0.5, 10, dist, d);
        //ParUtils.createFlyingParticle(Particle.FLAME, loc, 0.5, 1, 0.5, 10, dist, d);
        //ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, loc, 0, 0, 0, 1, 1, new Vector(0,1,0));
        //ParUtils.createFlyingParticle(Particle.FLAME, loc, 0, 0, 0, 1, 1, new Vector(0,1,0));

        //kRotLoc.setPitch(kRotLoc.getPitch()+5);
        //ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, caster.getLocation(), 1, 1F, 1, 3, 0.2F,new Vector(0,1,0));
        float c = cast;
        float ct = casttime;
        float speed = 2 * (c / ct);
        SoundUtils.playSound(Sound.ENTITY_PHANTOM_DEATH, loc, speed, 5F);
        Location p1 = ParUtils.stepCalcCircle(loc, 2, new Vector(0, 1, 0), 0, cast * speed);
        Location p2 = ParUtils.stepCalcCircle(loc, 2, new Vector(0, 1, 0), 0, 21 + cast * speed);
        //ParUtils.dropItemEffectVector(p1, Material.TNT, 1, 1, 0, new Vector(0,1,0));
        //ParUtils.dropItemEffectVector(p2, Material.TNT, 1, 1, 0, new Vector(0,1,0));
        if (lp1 != null && lp2 != null) {

            //ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, p2, 0.1, 0.1, 0.1, 10, 1, p2.toVector().subtract(lp2.toVector().add(new Vector(0,1,0))));
            //ParUtils.createFlyingParticle(Particle.SMOKE_LARGE, p1, 0.1, 0.1, 0.1, 10, 1, p1.toVector().subtract(lp1.toVector().add(new Vector(0,1,0))));
            ParUtils.createFlyingParticle(Particle.FLAME, p1, 0.1, 0.1, 0.1, 10, 1, p1.toVector().subtract(lp1.toVector()));
            ParUtils.createFlyingParticle(Particle.FLAME, p2, 0.1, 0.1, 0.1, 10, 1, p2.toVector().subtract(lp2.toVector()));
        }
        lp1 = p1;
        lp2 = p2;

        if (swap()) {
            cast = casttime;
            swapped = true;
        }


    }

    Location lp1;
    Location lp2;


    @Override
    public void launch() {


        float ct = casttime;
        if (swapped) {
            new BukkitRunnable() {
                int t = 0;

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    t++;
                    if (t > 3) {
                        caster.setVelocity(caster.getVelocity().setY((power / ct) * 8));
                        this.cancel();
                    }
                }
            }.runTaskTimer(main.plugin, 1, 1);
        }


        spawnShockWaffel(caster, 5, loc.clone());
        //ParUtils.createParticle(Particle.BARRIER, loc, (power/ct)*8, (power/ct)*8, (power/ct)*8, (int)(power/ct)*10, 10);

        if (!refined)
            //caster.setVelocity(caster.getVelocity().setY(1.0D));

            caster.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1, 20);
        for (LivingEntity le : caster.getWorld().getLivingEntities()) {
            if (checkHit(le, loc, caster, (power / ct) * 15)) {
                damage(le, 6 + (power / ct) * 10, caster);
                doKnockback(le, caster.getLocation().clone().add(0, -2, 0), 1);
            }
        }

        ParUtils.parKreisDir(Particle.FLAME, caster.getLocation(), (power / ct) * 15, 0, 2, new Vector(0, 1, 0), new Vector(0, 1, 0));
        ParUtils.parKreisDir(Particle.EXPLOSION_EMITTER, caster.getLocation(), (power / ct) * 12, 0, 2, new Vector(0, 1, 0), new Vector(0, 1, 0));
        dead = true;
    }

    @Override
    public void move() {
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

    public void spawnShockWaffel(final Player p, final int length, final Location l) {
        new BukkitRunnable() {
            double t = Math.PI / 4;
            Location loc = l;

            public void run() {
                t = t + 0.1 * Math.PI;
                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                    double x = t * Math.cos(theta);
                    double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 0.5;
                    double z = t * Math.sin(theta);
                    loc.add(x, y, z);
                    // ParticleEffect.FIREWORKS_SPARK.display(loc,0,0,0,0,1);
                    ParUtils.createParticle(Particle.LARGE_SMOKE, loc, 0, 0, 0, 0, 0);

                    loc.subtract(x, y, z);

                    theta = theta + Math.PI / 64;

                    x = t * Math.cos(theta);
                    y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
                    z = t * Math.sin(theta);
                    loc.add(x, y, z);
                    // ParticleEffect.WITCH_MAGIC.display(loc,0,0,0,0,1);
                    ParUtils.createParticle(Particle.CLOUD, loc, 0, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
                if (t > length) {
                    this.cancel();
                }
            }

        }.runTaskTimer(main.plugin, 0, 1);
    }


}
