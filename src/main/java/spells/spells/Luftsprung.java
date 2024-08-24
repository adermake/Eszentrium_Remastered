package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Luftsprung extends Spell {


    public Luftsprung() {
        steprange = 40;
        name = "§bLuftsprung";
        hitPlayer = false;
        hitEntity = false;
        hitSpell = false;

        spellDescription = new SpellDescription(
                "Der Spieler katapultiert sich selbst in Blickrichtung.",
                "Der Spieler katapultiert sich selbst in Blickrichtung.",
                null,
                null,
                null,
                "Die selbe Distanz zurückfliegen.",
                20*22
        );

        addSpellType(SpellType.MOBILITY);

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
        // TODO Auto-generated method stub

    }

    boolean reversed = false;
    int i = 0;

    @Override
    public void move() {
        // TODO Auto-generated method stub
        if (step < 10) {


            if (step % 3 == 0) {
                ParUtils.parKreisDot(Particle.CLOUD, caster.getLocation(), 1, 0, 0.1, caster.getLocation().getDirection());

                if (reversed) {
                    caster.setVelocity(caster.getLocation().getDirection().multiply(-2));
                } else {
                    caster.setVelocity(caster.getLocation().getDirection().multiply(2));
                }
                playSound(Sound.ENTITY_WITHER_SHOOT, caster.getLocation(), 1, 2);
                i++;
            }
        } else {
            if (refined && !reversed) {
                if (caster.isSneaking()) {
                    reversed = true;
                    step = 0;
                    steprange = 10;
                }
            }
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
