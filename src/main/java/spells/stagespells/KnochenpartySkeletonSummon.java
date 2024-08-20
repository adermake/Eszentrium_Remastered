package spells.stagespells;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.spells.Knochenparty;

public class KnochenpartySkeletonSummon extends Spell {
    Vector vel;
    Location overrideLoc;
    Location origin;
    Knochenparty kno;

    public KnochenpartySkeletonSummon(Player c, Location l, Vector dir, Knochenparty k) {
        vel = dir.clone();
        origin = l.clone();
        kno = k;
        caster = c;
        overrideLoc = l;
        cooldown = 20 * 62;
        steprange = 10;
        name = "§6Knochenparty";
        hitSpell = true;
        castSpell(caster, name);


        addSpellType(SpellType.PROJECTILE);
    }

    boolean holding = true;
    public static int in = 0;
    Item i;

    @Override
    public void setUp() {
        in++;
        if (in > 200000) {
            in = 0;
        }
        loc = overrideLoc;
        // TODO Auto-generated method stub
        ItemStack m = new ItemStack(Material.BONE);
        ItemMeta im = m.getItemMeta();
        im.setDisplayName("" + in);
        playSound(Sound.ENTITY_WITHER_SKELETON_HURT, loc, 1.5F, 30);
        m.setItemMeta(im);
        i = caster.getWorld().dropItem(loc, m);
        i.setPickupDelay(10000);
        //ar.addPassenger(i);
        i.setVelocity(vel.clone().add(new Vector(0, 0.2, 0)));

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
        // TODO Auto-generated method stub


        if (i.isOnGround()) {

            dead = true;
        }

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        //ParUtils.debug(loc);
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

        //loc = i.getLocation();
        loc = loc.add(vel.normalize().multiply(6));
        //ParUtils.debug(loc.clone().add(0,2,0));
        new KnochenpartySkeleton(caster, loc.clone().add(0, 0, 0), new Vector(0, 0.5F, 0), origin.clone(), name, kno);

        ParUtils.dropItemEffectRandomVector(loc, Material.BONE, 6, 50, 0.4);
        //ParUtils.createParticle(Particle.EXPLOSION_LARGE, loc, 0, 0, 0, 5, 2);
        SoundUtils.playSound(Sound.ENTITY_SKELETON_AMBIENT, loc, 1, 10);


        new BukkitRunnable() {
            public void run() {
                i.remove();
            }
        }.runTaskLater(main.plugin, 20);


    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub

    }

}
