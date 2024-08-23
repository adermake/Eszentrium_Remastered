package spells.spells;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.PlayerUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.ParrotTrail;

public class Vogelattacke extends Spell {

    int stepbound = 15;

    public Vogelattacke() {
        name = "§6Vogelattacke";
        //steprange = 30 * 10;
        stepbound = 15;

        spellDescription = new SpellDescription(
                "Verwandelt den Spieler in einen Vogel und beschwört einen Vogelschwarm, der den Spieler verfolgt. Solange sich der Spieler in dieser Form befindet, fliegt er in Blickrichtung voraus. Nach kurzer Zeit werden alle Vögel in Blickrichtung geschossen und schleudern getroffene Spieler weg.",
                "Verwandelt den Spieler in einen Vogel und beschwört einen Vogelschwarm, der den Spieler verfolgt. Solange sich der Spieler in dieser Form befindet, fliegt er in Blickrichtung voraus. Nach kurzer Zeit werden alle Vögel in Blickrichtung geschossen und schleudern getroffene Spieler weg.",
                null,
                null,
                "Schießt die Vögel sofort und beendet den Flug vorzeitig.",
                "Schießt die Vögel sofort und beendet den Flug vorzeitig.",
                20*30
        );

        addSpellType(SpellType.PROJECTILE);
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.SELFCAST);
    }

    ArrayList<Parrot> parrots = new ArrayList<Parrot>();
    int parrotCount = 14;
    Parrot par;

    @Override
    public void setUp() {
        if (refined) {
            parrotCount = 62;
            stepbound = 30;
            //steprange = 30 * 10;
        }
        par = (Parrot) spawnEntity(EntityType.PARROT);

        PlayerUtils.hidePlayer(caster);
        caster.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 2));


        for (int i = 0; i < parrotCount; i++) {

            parrots.add((Parrot) spawnEntity(EntityType.PARROT, caster.getLocation().add(0, 15, 0).add(randVector().multiply(5))));
            playSound(Sound.ENTITY_PARROT_AMBIENT, loc, 5, 1);
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

    int sneaker = 0;
    Vector d;

    @Override
    public void move() {
        // TODO Auto-generated method stub
        if (caster.isSneaking()) {
            if (step < stepbound) {
                step = stepbound;

            }

        }
        if (step > stepbound) {
            par.remove();
        }
        if (step > stepbound && parrotCount > 0) {

            parrotCount -= 1;
            sneaker++;

            parrots.get(parrots.size() - 1).remove();
            parrots.remove(parrots.size() - 1);
            caster.setVelocity(caster.getLocation().getDirection().multiply(-0.2));
            if (step > stepbound + 5)
                new ParrotProjectile(caster, name, true);


        } else {
            if (parrotCount == 0) {
                dead = true;
                return;
            }

            caster.setVelocity(caster.getLocation().getDirection().multiply(2));


        }


    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        doPin(par, caster.getEyeLocation().add(0, -0.5, 0), 3);
        par.setRotation(caster.getLocation().getYaw(), caster.getLocation().getPitch());
        double rad = 0.5;
        for (int i = 0; i < parrotCount; i++) {
            int polarity = 1;
            if (i % 2 == 0)
                polarity = -1;

            Location l1;
            if (polarity > 0) {
                l1 = ParUtils.stepCalcCircle(caster.getLocation(), rad, caster.getLocation().getDirection(), -i, 0).add(0, 1, 0);

            } else {

                l1 = ParUtils.stepCalcCircle(caster.getLocation(), rad - 0.5, caster.getLocation().getDirection(), -i + 1, 22).add(0, 1, 0);
            }

            doPull(parrots.get(i), l1, 2);
            rad += 0.5;
        }
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

        for (Parrot pr : parrots) {
            pr.remove();
        }


        // TODO Auto-generated method stub
        if (par != null)
            par.remove();
        caster.removePotionEffect(PotionEffectType.INVISIBILITY);
        PlayerUtils.showPlayer(caster);
    }

}
