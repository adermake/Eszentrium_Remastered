package spells.spells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.Sythe;

import java.util.HashMap;

public class Todessäge extends Spell {

    public HashMap<Entity, Integer> hitCount = new HashMap<>();

    public Todessäge() {
        name = "§eTodessäge";
        spellDescription = new SpellDescription(
                "Wirft Kreissägen in Blickrichtung, die auf kurzer Distanz nach unten fallen und dort liegen bleiben. Getroffene Gegner werden zurückgeworfen. Nach kurzer Zeit kehren die Sägen zum Anwender zurück, Schaden getroffenen Gegnern und heilen den Anwender für jeden Treffer.",
                "Wirft Kreissägen in Blickrichtung, die auf kurzer Distanz nach unten fallen und dort liegen bleiben. Getroffene Gegner werden zurückgeworfen. Nach kurzer Zeit kehren die Sägen zum Anwender zurück, Schaden getroffenen Gegnern und heilen den Anwender für jeden Treffer.",
                null,
                null,
                "Sägen sofort zurückziehen.",
                "Sägen sofort zurückziehen.",
                20*40
        );
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.SUPPORT);
        addSpellType(SpellType.PROJECTILE);
        autocancel = true;
    }


    @Override
    public void setUp() {
        Location l = caster.getLocation();

        float degStep = 8;
        int count = 11;

        if (refined) {
            degStep = 10;
            count = 35;
        }
        l.setYaw(l.getYaw() - ((count / 2) * degStep));
        for (float i = 0; i < count; i++) {


            new Sythe(name, caster, caster.getEyeLocation(), l.getDirection(), 1 * (float) ((Math.PI * 2) / 3), this, refined);
            new Sythe(name, caster, caster.getEyeLocation(), l.getDirection(), 2 * (float) ((Math.PI * 2) / 3), this, refined);
            new Sythe(name, caster, caster.getEyeLocation(), l.getDirection(), 3 * (float) ((Math.PI * 2) / 3), this, refined);
            l.setYaw(l.getYaw() + degStep);
        }
        playSound(Sound.ITEM_TRIDENT_THROW, loc, 1, 0.2F);
    }

    public void addHitCount(Entity ent) {
        if (hitCount.containsKey(ent)) {
            hitCount.put(ent, hitCount.get(ent) + 1);

        } else {
            hitCount.put(ent, 1);
        }
    }

    public int getHitCount(Entity ent) {
        if (hitCount.containsKey(ent)) {
            return hitCount.get(ent);
        } else {
            return 0;
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
