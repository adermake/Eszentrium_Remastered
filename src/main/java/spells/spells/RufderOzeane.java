package spells.spells;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.RufDerOzeaneFish;
import spells.stagespells.RufDerOzeaneRefined;

public class RufderOzeane extends Spell {

    public RufderOzeane() {
        name = "§6Ruf der Ozeane";
        steprange = 30;
        speed = 0.5;

        spellDescription = new SpellDescription(
                "Feuert Tintenfische in Blickrichtung ab, die getroffene Gegner zurückwerfen.",
                "Feuert einen einzelnen Tintenfisch in Blickrichtung ab. Trifft er einen Gegner, wirft er diesen zurück und kehrt zum Anwender zurück. Sobald er bei diesem ankommt, fliegt er wieder in Blickrichtung los.",
                null,
                null,
                null,
                null,
                20*53
        );
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.PROJECTILE);

    }

    @Override
    public void setUp() {

        if (refined) {
            new RufDerOzeaneRefined(caster, name).sendKey(spellkey);
            ;
            playSound(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS, loc, 1, 1);
        }


    }

    @Override
    public void cast() {


    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        if (!refined) {
            playSound(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS, loc, 1, 1);
            new RufDerOzeaneFish(caster, name);
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
