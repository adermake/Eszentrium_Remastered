package spells.spells;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.KaminchenEntity;

public class Kaminchen extends Spell {


    public Kaminchen() {
        name = "§6Kaminchen";
        hitSpell = true;

        spellDescription = new SpellDescription(
                "Wirft ein Kaninchen in Blickrichtung, das auf dem Boden stehen bleibt und bei Gegnerkontakt explodiert.",
                "Wirft 3 Kaninchen in Blickrichtung, die auf dem Boden stehen bleiben und bei Gegnerkontakt explodieren.",
                "Verärgert die Kaminchen, wodurch sie den naheliegendsten Gegner verfolgen.",
                "Verärgert die Kaminchen, wodurch sie den naheliegendsten Gegner verfolgen.",
                null,
                null,
                20*25
        );
        
        addSpellType(SpellType.DAMAGE);
    }


    @Override
    public void setUp() {


    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

        if (refined) {
            Location dirLoc = caster.getLocation();
            dirLoc.setYaw(dirLoc.getYaw() + 45);
            new KaminchenEntity(caster, dirLoc.getDirection(), name);
            dirLoc.setYaw(dirLoc.getYaw() - 90);
            new KaminchenEntity(caster, dirLoc.getDirection(), name);
        } else {
            new KaminchenEntity(caster, caster.getLocation().getDirection(), name);
        }

        dead = true;
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub

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
