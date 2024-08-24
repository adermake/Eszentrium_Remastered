package spells.spells;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;

public class Binden extends Spell {

    public Binden() {
        hitSpell = false;
        steprange = 20 * 5;
        name = "§bBinden";
        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*10
        );
    }

    Spell chase = null;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        chase = pointSpell(caster);


        if (chase == null) {

            refund = true;

            dead = true;
        }
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        if (dead)
            return;
        // TODO Auto-generated method stub
        playGlobalSound(Sound.ENTITY_HOGLIN_CONVERTED_TO_ZOMBIFIED, 2, 1.5F);
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        if (caster.isSneaking()) {
            dead = true;
            return;
        }
        if (chase.isDead()) {
            dead = true;
            return;
        }
        doPin(caster, chase.getLocation(), 1);
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
