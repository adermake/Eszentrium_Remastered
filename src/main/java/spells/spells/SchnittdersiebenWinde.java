package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.*;

public class SchnittdersiebenWinde extends Spell {


    public SchnittdersiebenWinde() {
        name = "§bSchnitt der sieben Winde";
        steprange = 32;

        spellDescription = new SpellDescription(
                "Teleportiert den Spieler zum anvisierten Gegner. Fügt nach kurzer Zeit Schaden an diesem Gegner an, der steigt, je weiter dieser vom Anwender entfernt ist.",
                "Teleportiert den Spieler zum anvisierten Gegner. Fügt nach kurzer Zeit Schaden an diesem Gegner an, der steigt, je weiter dieser vom Anwender entfernt ist.",
                null,
                null,
                null,
                null,
                20*40
        );

        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.LOCKDOWN);
        addSpellType(SpellType.MOBILITY);
    }

    Player target;


    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        target = pointEntity(caster);
        if (target == null) {
            refund = true;
            dead = true;
        } else {


            target.setVelocity(target.getVelocity().setY(1));
            ParUtils.parKreisDirSolid(Particle.CLOUD, target.getLocation(), 3, 0, 3, new Vector(0, 1, 0), new Vector(0, 1, 0));


            playSound(Sound.ENTITY_RAVAGER_ATTACK, target.getLocation(), 6, 0.3F);
            SilenceSelection s = new SilenceSelection(SilenceFilterType.AND);
            s.addFilter(SpellType.MOBILITY);
            silenced.put(target, s);
            playSound(Sound.AMBIENT_UNDERWATER_ENTER, target.getLocation(), 1, 2);
        }

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
        if (dead)
            return;

        if (caster.getGameMode() == GameMode.ADVENTURE) {
            dead = true;
        }

        if (step > 30) {
            tagPlayer(target);
            if (refined) {
                target.setVelocity(caster.getLocation().getDirection().multiply(12));
                caster.setVelocity(new Vector(0, 3, 0));
            } else {
                target.setVelocity(caster.getLocation().getDirection().multiply(3));
                caster.setVelocity(new Vector(0, 3, 0));
            }
            playSound(Sound.ENTITY_WITHER_SHOOT, target.getLocation(), 1, 2);

            dead = true;
        } else {
            doPull(caster, target.getLocation(), 2);
            target.setVelocity(target.getVelocity().setY(0.1));
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        if (target != null)
            ParUtils.chargeDot(target.getLocation(), Particle.CLOUD, 0.4F, 8);
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
        silenced.remove(target);
    }


}
