package spells.spells;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class AugedesDrachen extends Spell {

    public AugedesDrachen() {
        name = "§cAuge des Drachen";
        hitBlock = false;
        hitPlayer = false;
        hitEntity = false;
        hitSpell = true;
        speed = 1F;
        cooldown = 20 * 60;
        steprange = 200;
        hitboxSize = 1;
        //multihit = true;
        multihit = false;
        addSpellType(SpellType.DAMAGE);
        //addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.KNOCKBACK);

        setLore("§7Schießt ein Projektil in Blickrichtung,#§7das nach kurzer Zeit zwei Drachen beschwört.#§7Diese Drachen folgen der Fluglinie und schaden#§7getroffenen Gegnern und werfen sie zurück. Shift: Solange diese Taste gedrückt bleibt, kann die Richtung des Projektils gesteuert werden, bis die Drachen erscheinen.");

    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub
        d1.remove();

        d2.remove();

    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {

        playSound(Sound.ENTITY_ENDER_EYE_DEATH, loc, 5f, 0.5f);
    }


    EnderDragon d1;
    EnderDragon d2;
    boolean calcH = false;

    @Override
    public void move() {
        if (step <= 50) {
            if (caster.isSneaking())
                loc.setDirection(loc.getDirection().multiply(4).add(caster.getLocation().getDirection()));
            loc.add(loc.getDirection().multiply(0.4));
        } else {
            loc.add(loc.getDirection().multiply(0.6));
        }
        if (step == 51) {
            hitboxSize = 7;
            hitPlayer = true;
            hitEntity = true;
            Location l = loc.clone();
            l.setDirection(l.getDirection().multiply(-1));
            d1 = (EnderDragon) loc.getWorld().spawnEntity(l, EntityType.ENDER_DRAGON);
            d1.setSilent(true);
            d2 = (EnderDragon) loc.getWorld().spawnEntity(l, EntityType.ENDER_DRAGON);
            d2.setSilent(true);
            d1.setCollidable(false);
            d2.setCollidable(false);
            d1.setRemoveWhenFarAway(false);
            d2.setRemoveWhenFarAway(false);
            speed = 2;
            //d1.setPhase(Phase.CHARGE_PLAYER);
            //d2.setPhase(Phase.HOVER);
        }
        if (step > 51) {

            if (calcH) {
                moveHelix(d1, 3, (Math.PI));
                moveHelix(d2, 3, 0);
                ParUtils.createRedstoneParticle(d1.getLocation(), 0, 0, 0, 1, Color.PURPLE, 3);
                ParUtils.createRedstoneParticle(d2.getLocation(), 0, 0, 0, 1, Color.PURPLE, 3);
                ParUtils.createParticle(Particle.LARGE_SMOKE, d1.getLocation(), 0, 0, 0, 1, 0);
                ParUtils.createParticle(Particle.LARGE_SMOKE, d2.getLocation(), 0, 0, 0, 1, 0);
            }
            calcH = !calcH;
        }

    }

    @Override
    public void display() {
        if (step == 50) {
            playGlobalSound(Sound.ENTITY_ENDER_DRAGON_DEATH, 0.6f, 1f);
        }
        if (step <= 50) {
            ParUtils.createParticle(Particle.HAPPY_VILLAGER, loc, 0, 0, 0, 1, 0);
        } else {
            ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.fromBGR(255, 170, 231), 4);
        }


    }

    @Override
    public void onPlayerHit(Player p) {
        damage(p, 12, caster);
        doKnockback(p, loc, 5);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        damage(ent, 12, caster);
        doKnockback(ent, loc, 5);
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub

    }

    public void moveHelix(EnderDragon d, double r, double pushover) {
        double x = r * Math.cos(0.5 * step * (Math.PI / 16) + pushover);
        double y = 0;
        double z = r * Math.sin(0.5 * step * (Math.PI / 16) + pushover);
        Vector v = new Vector(x, y, z);
        Matrix.rotateMatrixVectorFunktion(v, loc.clone());
        Location l = loc.clone().add(v);
        l.setDirection(l.getDirection().multiply(-1));
        d.teleport(l);


    }


}
