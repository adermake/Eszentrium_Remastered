package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;

import java.util.ArrayList;

public class Schwerkraftsmanipulation extends Spell {


    public static ArrayList<Player> gravityMani = new ArrayList<Player>();

    public Schwerkraftsmanipulation() {
        name = "§eSchwerkraftsmanipulation";
        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*30
        );
        steprange = 60;
    }

    Player target;

    @Override
    public void setUp() {
        target = pointEntity(caster);
        if (target == null) {
            refund = true;
            dead = true;
        } else {
            ParUtils.parKreisSolidRedstone(Color.GRAY, 2, target.getLocation(), 4, 0, 1, new Vector(0, 1, 0));
            playSound(Sound.BLOCK_BELL_USE, target.getLocation(), 16, 2F);
            playSound(Sound.BLOCK_BELL_RESONATE, target.getLocation(), 6, 1.6F);
            if (refined) {
                gravityMani.add(target);
            }
        }

    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {
        if (target == null) {
            dead = true;
            return;
        }
        tagPlayer(target);
        target.setVelocity(target.getVelocity().setY(-6));
    }

    int count = 0;

    @Override
    public void display() {
        count++;
        if (count > 5) {


            ParUtils.parKreisSolidRedstone(Color.GRAY, 2, target.getLocation(), 4, 0, 1, new Vector(0, 1, 0));
            count = 0;
        }
    }

    @Override
    public void onPlayerHit(Player p) {

    }

    @Override
    public void onEntityHit(LivingEntity ent) {

    }

    @Override
    public void onSpellHit(Spell spell) {

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {
        if (refined) {
            gravityMani.remove(target);
        }
    }


}
