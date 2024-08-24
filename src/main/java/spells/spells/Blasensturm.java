package spells.spells;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.stagespells.Bubble;

public class Blasensturm extends Spell {

    int rec = 65;

    public Blasensturm() {
        name = "§eBlasensturm";
        steprange = 20 * 4;
        speed = 2;

        hitboxSize = 1;
        hitSpell = true;

        spellDescription = new SpellDescription(
                "Schießt eine Menge Blasen, die nach kurzer Zeit den naheliegendsten Gegner verfolgen. Getroffene Gegner werden weggeschleudert.",
                "Schießt eine Menge Blasen, die nach kurzer Zeit den naheliegendsten Gegner verfolgen. Getroffene Gegner werden weggeschleudert.",
                null,
                null,
                null,
                null,
                20*40
        );

    }


    @Override
    public void setUp() {
        // TODO Auto-generated method stub


    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {
        if (!refined) {
            for (int i = 0; i < 30; i++) {
                new Bubble(caster.getEyeLocation(), caster, name);
            }
        }
    }

    @Override
    public void move() {
        if (refined) {
            new Bubble(caster.getEyeLocation().add(0, -0.3, 0), caster, name);
        }
    }

    @Override
    public void display() {

    }

    @Override
    public void onPlayerHit(Player p) {

    }

    @Override
    public void onEntityHit(LivingEntity ent) {

    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {

    }


}
