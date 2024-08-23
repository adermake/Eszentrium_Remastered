package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.Meteor;

public class Meteoritenhagel extends Spell {

    public Meteoritenhagel() {
        casttime = 0;
        steprange = 65;
        name = "§cMeteoritenhagel";
        speed = 1;
        hitSpell = false;

        spellDescription = new SpellDescription(
                "Lässt nach kurzer Verzögerung Meteoriten vom Himmel fallen, die bei Bodenkontakt explodieren und nahe Gegner schaden.",
                "",
                null,
                null,
                null,
                null,
                20*40
        );
        
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.PROJECTILE);

    }

    @Override
    public void setUp() {

        // TODO Auto-generated method stub
        loc = null;
        // loc = block(caster,200);
        loc = caster.getLocation();
        if (loc == null) {
            dead = true;
            refund = true;
        } else {
            playSound(Sound.ENTITY_SHULKER_CLOSE, loc, 10, 0.1F);
            playSound(Sound.ENTITY_CAT_HISS, loc, 10, 0.5F);
            Location hBlock = loc.clone();
            hBlock.setY(255);
            while (hBlock.getBlock().getType() == Material.AIR) {
                hBlock.add(0, -1, 0);
                if (hBlock.getY() < 10) {
                    break;

                }
            }
            loc = hBlock;
        }

    }

    double range = 5;

    @Override
    public void cast() {

        // ParUtils.parKreisSolidRedstone(Color.RED, 1, loc.clone(), cast/4, 0, 1, new
        // Vector(0,1,0));

        // ParUtils.createParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, dot, 0, 1, 0, 0, 5);
    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        // ParUtils.parKreisDot(Particle.FLAME, loc, 3, 1, 1.5, new Vector(0,-1,0));
        // ParUtils.parKreisDot(Particle.FLAME, loc, 2, 1, 0.5, new Vector(0,-1,0));
        ParUtils.parKreisSolidRedstone(Color.ORANGE, 1.5F, loc, range * 2, 0, 1, new Vector(0, 1, 0));
    }

    int delay = 0;

    @Override
    public void move() {
        delay++;
        if (delay > 3) {
            Location calc = loc.clone().add(0, 90, 0);
            calc.add(randDouble(-range, range), 0, randDouble(-range, range));
            new Meteor(calc, caster, name);
            delay = 0;
        }

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

}
