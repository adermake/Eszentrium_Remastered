package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Teleport extends Spell {


    public Teleport() {
        name = "§bTeleport";
        speed = 2;
        hitSpell = true;
        steprange = 300;
        speed = 2;

        spellDescription = new SpellDescription(
                "Schießt ein Projektil in Blickrichtung, das den Spieler bei Kontakt mit einem Block oder Spieler dorthin teleportiert. Wird ein Spieler getroffen,tauschen diese Plätze.",
                "Teleportiert den Spieler auf den anvisierten Block oder Spieler. Wird ein Spieler getroffen, tauschen diese Plätze.",
                null,
                null,
                null,
                null,
                20*30
        );

        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.PROJECTILE);
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub


        if (refined)
            speed = 40;

    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        loc.add(loc.getDirection().multiply(0.5));
        if (!refined) {
            speed = 2 + calcLerpFactor(step, 40) * 2;
        }

        ParUtils.createParticle(Particle.WITCH, loc, 0, 0, 0, 5, 0);

    }

    @Override
    public void display() {
        if (caster.getGameMode() == GameMode.ADVENTURE) {
            dead = true;
        }
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {

        loc = caster.getLocation();
        caster.teleport(p.getLocation());
        p.teleport(loc);
        dead = true;
        damage(p, 1, caster);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub

        loc = caster.getLocation();
        caster.teleport(ent.getLocation());
        ent.teleport(loc);
        dead = true;
        damage(ent, 1, caster);
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub
        getTop(loc);

        caster.teleport(loc.getBlock().getLocation().add(0.5, 0, 0.5).setDirection(caster.getLocation().getDirection()));
        dead = true;
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub
        ParUtils.parKreisDirSolid(Particle.DRAGON_BREATH, loc, 2, 0, 4, loc.getDirection(), loc.getDirection().multiply(-1));
    }


}