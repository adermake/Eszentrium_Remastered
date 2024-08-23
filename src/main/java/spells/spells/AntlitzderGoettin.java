package spells.spells;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

import java.util.ArrayList;

public class AntlitzderGoettin extends Spell {

    public static ArrayList<Player> deflect = new ArrayList<Player>();

    public AntlitzderGoettin() {
        name = "§3Antlitz der Göttin";
        hitSpell = true;
        hitPlayer = false;
        hitBlock = false;
        hitEntity = false;
        steprange = 20 * 10;
        speed = 1;
        hitboxSize = 4;

        spellDescription = new SpellDescription(
                "Reflektiert die n§chsten 3 gegnerischen#§7Zauber für kurze Zeit. Jede Schadensinstanz#§7eines Zaubers zählt einzeln.",
                "Reflektiert alle gegnerischen Zauber für#§7kurze Zeit. Jede Schadensinstanz#§7eines Zaubers zählt einzeln.",
                null,
                null,
                null,
                null,
                20*35
        );

        addSpellType(SpellType.AURA);
        addSpellType(SpellType.SELFCAST);
    }

    @Override
    public void onDeath() {
        SoundUtils.playSound(Sound.ENTITY_WITCH_DEATH, loc, 0.8F, 5F);

        // TODO Auto-generated method stub
        deflect.remove(caster);
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        SoundUtils.playSound(Sound.ENTITY_WITCH_AMBIENT, loc, 3, 2F);

        deflect.add(caster);
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        if (refined) {
            returns = 10;
        }
    }

    @Override
    public void move() {
        loc = caster.getLocation();
        //if (refined)
        //	caster.setNoDamageTicks(5);

        if (caster.getLocation().getY() < 62) {
            dead = true;
        }
    }

    int returns = 3;

    @Override
    public void display() {
        if (caster.getGameMode() == GameMode.ADVENTURE) {
            dead = true;
        }
        //playSound(Sound.BLOCK_NOTE_BLOCK_COW_BELL, loc, 10F, 0.1F);
        SoundUtils.playSound(Sound.ENTITY_BLAZE_BURN, loc, 3, 0.4F);
        //Location dot = ParUtils.stepCalcCircle(loc, 1.3, new Vector(0,1,0), -0.3, step*3);
        //Location dot2 = ParUtils.stepCalcCircle(loc, 1.3, new Vector(0,1,0), -0.3, step*3+15);
        //Location dot3 = ParUtils.stepCalcCircle(loc, 1.3, new Vector(0,1,0), -0.3, step*3+30);
        loc = caster.getLocation();

        //ParUtils.createParticle(Particle.FLAME, dot, 0, 1, 0, 0, 14);
        //ParUtils.createParticle(Particle.FLAME, dot2, 0, 1, 0, 0, 14);
        if (refined) {
            //ParUtils.dropItemEffectVector(dot, Material.TOTEM, 1, 6, 5,new Vector(0,1,0));
            //ParUtils.dropItemEffectVector(dot2, Material.TOTEM, 1, 6, 5,new Vector(0,1,0));
            //ParUtils.dropItemEffectVector(dot3, Material.TOTEM, 1, 6, 5,new Vector(0,1,0));
            for (int i = 0; i < returns; i++) {
                Location dot = ParUtils.stepCalcCircle(loc, 1.3, new Vector(0, 1, 0), -0.3, step * 3 + i * (44 / returns));
                ParUtils.dropItemEffectVector(dot, Material.TOTEM_OF_UNDYING, 1, 1, 1, new Vector(0, 1, 0));
                ParUtils.createParticle(Particle.FLAME, dot, 0, 1, 0, 0, 0.3F);
            }
        } else {
            for (int i = 0; i < returns; i++) {
                Location dot = ParUtils.stepCalcCircle(loc, 1.3, new Vector(0, 1, 0), -0.3, step * 3 + i * 15);
                ParUtils.dropItemEffectVector(dot, Material.TOTEM_OF_UNDYING, 2, 1, 1, new Vector(0, -1, 0));
                ParUtils.createParticle(Particle.FLAME, dot, 0, 1, 0, 0, -0.3F);
            }
            //ParUtils.dropItemEffectVector(dot, Material.TOTEM, 1, 1, 1,new Vector(0,1,0));
            //ParUtils.dropItemEffectVector(dot2, Material.TOTEM, 1, 1, 1,new Vector(0,1,0));
            //ParUtils.dropItemEffectVector(dot3, Material.TOTEM, 1, 1, 1,new Vector(0,1,0));
        }
        if (returns <= 0) {
            dead = true;
        }

        //ParUtils.createParticle(Particle.VILLAGER_ANGRY, caster.getEyeLocation().add(0,-1.7,0), 0, 1, 0, 0, 1);
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
        if (spell.getName().contains("Antlitz der G§ttin") || spell.caster == caster) {
            return;
        }
        if (returns > 0) {

            returns--;
            // TODO Auto-generated method stub
            spell.caster = caster;
            playSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE, loc, 5, 2);
            spell.loc.setDirection(loc.getDirection());
        }


    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub

    }

}
