package spells.spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.KnochenpartySkeleton;
import spells.stagespells.KnochenpartySkeletonSummon;
import spells.stagespells.Repulsion;
import spells.stagespells.VampirpilzStage2;

public class Knochenparty extends Spell {

    HashMap<Entity, Integer> bounces = new HashMap<Entity, Integer>();
    ArrayList<Entity> noChargeLoss = new ArrayList<Entity>();

    public Knochenparty() {
        steprange = 60;
        name = "§cKnochenparty";
        hitSpell = true;

        spellDescription = new SpellDescription(
                "Wirft einen Knochen in Blickrichtung. Um ihn herum erscheint nach kurzer Zeit ein Ring aus Skeletten, die Gegner bei Berührung Schaden zufügen und sie in die Mitte des Rings schleudern.",
                "",
                null,
                null,
                null,
                null,
                20*32
        );
        
        addSpellType(SpellType.LOCKDOWN);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);
    }

    boolean holding = true;
    Item i;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        ItemStack m = new ItemStack(Material.BONE);

        i = caster.getWorld().dropItem(loc, m);

        playSound(Sound.ENTITY_SKELETON_DEATH, loc, 2, 5);

        //ar.addPassenger(i);
        if (caster.isSneaking()) {
            i.setVelocity(caster.getLocation().getDirection().multiply(2));
        } else {
            i.setVelocity(caster.getLocation().getDirection().multiply(1));
            step = 20;
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


    @Override
    public void move() {
        // TODO Auto-generated method stub

        loc = i.getLocation();
        if (i.isOnGround()) {

            dead = true;
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }


    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub
        if (spell.getName().contains("Antlitz der Göttin"))
            i.setVelocity(spell.caster.getLocation().getDirection().multiply(2));
    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub
        loc = i.getLocation();
        loc.setDirection(new Vector(1, 0, 0));
        for (int i = 0; i < 20; i++) {

            loc.setYaw(loc.getYaw() + 18);

            new KnochenpartySkeletonSummon(caster, loc.clone(), loc.getDirection().multiply(0.6F), this);
            //ParUtils.debugRay(loc.clone());


        }

        i.remove();

    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub

    }

    public boolean canBounce(Entity p) {
        if (noChargeLoss.contains(p)) {
            return false;
        }
        if (bounces.containsKey(p)) {
            if (bounces.get(p) > 8) {
                return false;
            }
        }
        return true;
    }

    public void addBounce(Entity p) {

        if (!bounces.containsKey(p)) {
            bounces.put(p, 1);
        } else {
            bounces.put(p, bounces.get(p) + 1);
        }

        noChargeLoss.add(p);
        new BukkitRunnable() {
            public void run() {
                noChargeLoss.remove(p);
            }
        }.runTaskLater(main.plugin, 3);

    }

}
