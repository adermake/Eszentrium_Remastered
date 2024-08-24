package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.stagespells.WebTrail;

import java.util.ArrayList;

public class Schweberknecht extends Spell {

    Spider s;
    boolean bounced = false;
    boolean hitbounced = false;
    double yLevel = 0;
    Vector vel;

    ArrayList<Spell> trails = new ArrayList<Spell>();

    public Schweberknecht() {
        steprange = 20 * 6;
        name = "§6Schweberknecht";
        spellDescription = new SpellDescription(
                "Wirft eine Spinne, die bei Bodenkontakt losspringt und alle Spieler, die in Kontakt mit ihrem Netz kommen, mitnimmt. Von je höher sie fällt vor dem ersten Bodenkontakt, desto weiter springt sie.",
                "Wirft eine Spinne, die bei Bodenkontakt losspringt und alle Spieler, die in Kontakt mit ihrem Netz kommen, mitnimmt. Von je höher sie fällt vor dem ersten Bodenkontakt, desto weiter springt sie.",
                null,
                null,
                null,
                null,
                20*35
        );
    }

    @Override
    public void setUp() {

        // TODO Auto-generated method stub
        s = (Spider) spawnEntity(EntityType.SPIDER);
        unHittable.add(s);
        vel = caster.getLocation().getDirection().multiply(1);
        s.setInvulnerable(true);
        addNoTarget(s);
        playSound(Sound.ENTITY_SPIDER_DEATH, loc, 2, 1);

        if (refined) {
            Spell chase = this;
            for (float i = 0; i < 15; i++) {
                Spell s = new WebTrail(chase, caster, name, -1, yLevel);
                chase = s;
                trails.add(s);
            }
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
        loc.add(vel);
        doPin(s, loc, 3);
        vel.add(new Vector(0, -0.1, 0));

        if (hitbounced && !bounced) {
            if (refined) {
                if (s instanceof WebTrail w) {
                    w.height = loc.clone().getY();
                    w.power = 0;
                }
            }
            speed = 2;
            bounced = true;
            playSound(Sound.ENTITY_MAGMA_CUBE_JUMP, loc, 3, 0.5F);
            vel = vel.setY(Math.abs(vel.getY()));
            double y = vel.getY();
            double cap = 3F;
            if (y > cap) {
                y = cap;
            }
            step = 0;
            steprange = 20 * 4;
            vel = vel.setY(0);
            vel.normalize().multiply(y / 2);
            vel.setY(y);
            hitbounced = false;
            yLevel = loc.getY();
            Spell chase = this;
            loc = s.getLocation();
            for (float i = 0; i < 15; i++) {
                chase = new WebTrail(chase, caster, name, y, yLevel);
            }
        }
        if (loc.getY() < yLevel) {
            dead = true;
        }


    }

    @Override
    public void display() {
        ParUtils.createParticle(Particle.CLOUD, loc, 0, 0, 0, 1, 0);
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
        if (hitbounced) {
            dead = true;
        }
        hitbounced = true;

    }

    @Override
    public void onDeath() {
        playSound(Sound.ENTITY_SPIDER_DEATH, loc, 2, 0.5);
        s.remove();
    }


}
