package spells.stagespells;

import esze.utils.ParUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.spells.Knochenparty;

public class KnochenpartySkeleton extends Spell {
    Skeleton ent;
    Location origin;
    Knochenparty kno;

    public KnochenpartySkeleton(Player caster, Location l, Vector dir, Location origin, String namae, Knochenparty k) {
        steprange = 20 * 10;
        this.origin = origin;
        hitboxSize = 2;
        hitPlayer = true;
        hitEntity = true;
        loc = caster.getEyeLocation();
        multihit = true;
        kno = k;

        //


        //
        ent = (Skeleton) caster.getWorld().spawnEntity(l, EntityType.SKELETON);

        bindEntity(ent);
        ent.setFireTicks(-100);
        ent.setVelocity(dir);

        unHittable.add(ent);
        //ent.getEquipment().setItemInOffHand(new ItemStack(Material.WHITE_DYE));
        ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100000, 250, true));
        skelvel = dir;
        //ent.setInvulnerable(false);
        name = namae;

        castSpell(caster, name);


        addSpellType(SpellType.LOCKDOWN);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.AURA);
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        playSound(Sound.ENTITY_WITHER_SKELETON_AMBIENT, loc, 1, 10);
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub


    }

    boolean jumped = false;
    Vector skelvel;

    @Override
    public void move() {

        ent.getEquipment().clear();
        ent.setFireTicks(-100);
        loc = ent.getLocation();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getGameMode() == GameMode.SURVIVAL && !unHittable.contains(p) && p != caster) {
                Location ploc = p.getLocation();
                ploc.setY(0);
                Location cloneLoc = loc.clone();
                cloneLoc.setY(0);

                if (ploc.distance(cloneLoc) < 4) {

                    double dif = p.getLocation().getY() - loc.getY();
                    dif = dif / 2;
                    skelvel = new Vector(0, dif, 0);

                    if (dif > 1 && !jumped) {
                        jumped = true;
                        playSound(Sound.ENTITY_HORSE_JUMP, loc, 1, 1);
                    }

                }

            }
        }

        for (LivingEntity p : caster.getWorld().getLivingEntities()) {
            if (!unHittable.contains(p)) {
                Location ploc = p.getLocation();
                ploc.setY(0);
                Location cloneLoc = loc.clone();
                cloneLoc.setY(0);

                if (ploc.distance(cloneLoc) < 4) {

                    double dif = p.getLocation().getY() - loc.getY();
                    dif = dif / 2;
                    skelvel = new Vector(0, dif, 0);

                    if (dif > 1 && !jumped) {
                        jumped = true;
                        playSound(Sound.ENTITY_HORSE_JUMP, loc, 1, 1);
                    }

                }

            }
        }
        skelvel.setY(skelvel.getY() - 0.1);
        if (skelvel.getY() < -2) {
            skelvel.setY(-2);
            jumped = false;
        }
        ent.setVelocity(skelvel);
        ent.setFallDistance(-1000);
        loc = ent.getLocation();
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {
        if (origin.distance(loc) > 4 && kno.canBounce(p)) {
            kno.addBounce(p);
            loc = ent.getLocation();
            Vector v = origin.toVector().setY(p.getLocation().getY()).subtract(loc.toVector().setY(p.getLocation().getY())).normalize();

            p.setVelocity(v.multiply(2).add(new Vector(0, 0.4, 0)));
            damage(p, 2, caster);

            ParUtils.createParticle(Particle.CLOUD, loc, 0, 0, 0, 3, 1);
            playSound(Sound.ENTITY_WITHER_SKELETON_DEATH, loc, 1, 1);

        }
    }

    @Override
    public void onEntityHit(LivingEntity ent) {

        if (origin.distance(loc) > 4 && kno.canBounce(ent)) {
            kno.addBounce(ent);
            loc = ent.getLocation();
            Vector v = origin.toVector().setY(ent.getLocation().getY()).subtract(loc.toVector().setY(ent.getLocation().getY())).normalize();

            ent.setVelocity(v.multiply(2).add(new Vector(0, 0.4, 0)));
            damage(ent, 2, caster);

            ParUtils.createParticle(Particle.CLOUD, loc, 0, 0, 0, 3, 1);
            playSound(Sound.ENTITY_WITHER_SKELETON_DEATH, loc, 1, 1);

        }


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

        if (ent != null) {
            playSound(Sound.ENTITY_WITHER_SKELETON_DEATH, loc, 1, 10);
            ParUtils.createFlyingParticle(Particle.LARGE_SMOKE, loc, 0.1, 0.1, 0.1, 5, 0.6, ent.getVelocity());
            ent.remove();
        }

    }


}
