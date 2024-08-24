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
                "Wirft ein Kaninchen in Blickrichtung, das auf dem Boden stehen bleibt und bei Gegnerkontakt explodiert. Werden Kaninchen verärgert verfolgen sie den naheliegendsten Gegner.",
                "Wirft 3 Kaninchen in Blickrichtung, die auf dem Boden stehen bleiben und bei Gegnerkontakt explodieren. Werden Kaninchen verärgert verfolgen sie den naheliegendsten Gegner.",
                "Kaninchen verärgern",
                "Kaninchen verärgern",
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

    }

    @Override
    public void launch() {
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

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {

    }


}
