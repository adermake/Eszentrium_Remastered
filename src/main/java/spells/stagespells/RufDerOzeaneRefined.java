package spells.stagespells;

import esze.utils.ParUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class RufDerOzeaneRefined extends Spell {


    boolean back = false;
    LivingEntity ent;

    public RufDerOzeaneRefined(Player p, String namae) {
        name = namae;
        hitSpell = true;
        steprange = 100;
        hitboxSize = 2;
        multihit = true;
        castSpell(p, name);
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.PROJECTILE);

    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        EntityType type = EntityType.SQUID;
        int i = 6;
        if (i == 1) {
            type = EntityType.TROPICAL_FISH;
        }
        if (i == 2) {
            type = EntityType.COD;
        }
        if (i == 3) {
            type = EntityType.DOLPHIN;
        }
        if (i == 4) {
            type = EntityType.PUFFERFISH;
        }
        if (i == 5) {
            type = EntityType.SALMON;
        }
        if (i == 6) {
            type = EntityType.SQUID;
        }
        ent = (LivingEntity) caster.getWorld().spawnEntity(caster.getLocation(), type);
        ent.setInvulnerable(true);
        ent.setCollidable(false);

        //ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,  5, 5));


        bindEntity(ent);
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
        loc = ent.getLocation();
        if (back) {

            doPull(ent, caster.getLocation(), 1.5);
            if (ent.getLocation().distance(caster.getLocation()) <= 2) {
                back = false;
            }
        } else {

            ent.setVelocity(caster.getLocation().getDirection().multiply(2));
        }

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

        if (step > 2) {
            ParUtils.createBlockcrackParticle(loc, 0.1, 0.1, 0.1, 2, Material.LIGHT_BLUE_STAINED_GLASS);
            ParUtils.createBlockcrackParticle(loc, 0.1, 0.1, 0.1, 2, Material.LIGHT_BLUE_CONCRETE_POWDER);
            ParUtils.createParticle(Particle.BUBBLE_COLUMN_UP, loc, 0.4, 0.4, 0.4, 3, 1);
        }


    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        damage(p, 1, caster);
        doKnockback(p, caster.getLocation(), 4.5);
        //back = true;
        reduceCooldown(52 * 20);
        step = 0;
        dead = true;
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        damage(ent, 1, caster);
        doKnockback(ent, caster.getLocation(), 4.5);
        //back = true;
        step = 0;
        reduceCooldown(52 * 20);
        dead = true;
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
        ent.remove();
    }


}
