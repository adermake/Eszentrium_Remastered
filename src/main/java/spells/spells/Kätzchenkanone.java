package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;
import spells.stagespells.SelfRepulsion;

public class Kätzchenkanone extends Spell {

    Cat cat;

    public Kätzchenkanone() {

        name = "§6Kätzchenkanone";
        steprange = 70;
        hitSpell = true;

        spellDescription = new SpellDescription(
                "Wirft eine Katze in Blickrichtung, die auf dem Boden stehen bleibt. Nach kurzer Zeit explodiert diese und schadet allen Gegnern und schleudert sie weg. Wenn der Anwender getroffen wird, schleudert ihn die Explosion ebenfalls weg, verursacht aber keinen Schaden.",
                "Wirft eine Katze in Blickrichtung, die auf dem Boden stehen bleibt. Nach kurzer Zeit explodiert diese und schadet allen Gegnern und schleudert sie weg. Wenn der Anwender getroffen wird, schleudert ihn die Explosion ebenfalls weg, verursacht aber keinen Schaden.",
                null,
                null,
                "Katze vorzeitig sprengen.",
                "Katze vorzeitig sprengen.",
                20*18
        );
        
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.PROJECTILE);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.MOBILITY);
    }

    @Override
    public void setUp() {
        cat = (Cat) caster.getWorld().spawnEntity(caster.getEyeLocation().add(caster.getLocation().getDirection()), EntityType.CAT);
        cat.setBaby();
        cat.setSitting(true);

        cat.setVelocity(caster.getLocation().getDirection().multiply(3));

    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {
        playSound(Sound.ENTITY_CAT_AMBIENT, caster.getLocation(), 3.0F, 1F);
    }

    @Override
    public void move() {
        if (caster.isSneaking()) {
            onDeath();

            dead = true;
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
        if (spell.getName().contains("Antlitz der Göttin"))
            cat.setVelocity(spell.caster.getLocation().getDirection().multiply(2));
    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {
        if (cat != null && !cat.isDead()) {

            loc = cat.getLocation();

            playSound(Sound.ENTITY_CAT_DEATH, caster.getLocation(), 1, 1);
            cat.remove();
            if (loc.distance(caster.getLocation()) < 3) {
                doKnockback(caster, loc, 4);
            }
            playSound(Sound.ENTITY_CAT_DEATH, caster.getLocation(), 3.0F, 1F);
            if (refined) {
                new ExplosionDamage(4, 8, caster, loc, name);
                new Repulsion(4, 6, caster, loc, false, name);
                new SelfRepulsion(8, 6, caster, loc, name);
            } else {
                new ExplosionDamage(4, 5, caster, loc, name);
                new Repulsion(4, 4, caster, loc, false, name);
                new SelfRepulsion(8, 4, caster, loc, name);
            }
            playSound(Sound.ENTITY_GENERIC_EXPLODE, caster.getLocation(), 3.0F, 1F);
            ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, 0, 0, 0, 4, 1);
            ParUtils.dropItemEffectRandomVector(loc, Material.TROPICAL_FISH, 1, 40, 0.3);
            ParUtils.dropItemEffectRandomVector(loc, Material.RABBIT_HIDE, 1, 40, 0.3);
        }
    }


}
