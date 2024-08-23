package spells.spells;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;

public class Wildwuchs extends Spell {


    ArrayList<Entity> blocks = new ArrayList<Entity>();
    Location bLoc;

    public Wildwuchs() {

        name = "§bWildwuchs";
        speed = 2;
        hitboxSize = 3;

        spellDescription = new SpellDescription(
                "Beschwört eine Wurzel am anvisierten Block, die sich auf den Spieler zubewegt und Gegnern auf dem Weg Schaden zufügt. Sobald der Anwender in Kontakt mit der Wurzel kommt, reitet er auf dieser, was ihn in Blickrichtung fortbewegt und getroffene Gegner zurückwirft.",
                "Beschwört eine Wurzel am anvisierten Block, die sich auf den Spieler zubewegt und Gegnern auf dem Weg Schaden zufügt. Sobald der Anwender in Kontakt mit der Wurzel kommt, reitet er auf dieser, was ihn in Blickrichtung fortbewegt und getroffene Gegner zurückwirft.",
                null,
                "Beschwört eine zweite Wurzel, sobald die erste endet.",
                "Bricht den Zauber ab.",
                "Bricht den Zauber ab.",
                20*20
        );
    }

    boolean refinedLook = false;

    public Wildwuchs(boolean b) {
        super();
        refinedLook = b;
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        bLoc = block(caster, 100);
        setCanBeSilenced(true);
        if (bLoc == null) {
            refund = true;
            dead = true;
        }
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        if (dead)
            return;
        // TODO Auto-generated method stub
        vel = randVector().add(caster.getLocation().toVector().subtract(bLoc.toVector())).normalize();
        loc = bLoc;
        playSound(Sound.BLOCK_BAMBOO_STEP, loc, 1, 0.5F);

    }

    boolean seeking = true;

    Vector vel;

    @Override
    public void move() {
        if (dead)
            return;
        if (caster.getGameMode() == GameMode.ADVENTURE || loc.distance(caster.getLocation()) > 100) {
            dead = true;
        }

        // TODO Auto-generated method stub
        loc.add(vel.clone().normalize());

        if (!loc.getBlock().getType().isSolid()) {

            FallingBlock fb;

            if (refined || refinedLook) {
                fb = spawnFallingBlock(loc, Material.SPRUCE_LOG);
            } else {
                fb = spawnFallingBlock(loc, Material.OAK_LOG);
            }

            fb.setGravity(false);
            blocks.add(fb);
            playSound(Sound.BLOCK_WOOD_PLACE, loc, 1, 0.2F);

        }
        if (seeking) {
            Vector dir = caster.getLocation().add(0, -1, 0).toVector().subtract(loc.toVector()).normalize();
            vel = slerp(vel, dir, clamp(1 - caster.getLocation().distance(loc) / 10, 0.1, 1));
        } else {
            Vector dir = caster.getLocation().getDirection();
            vel = slerp(vel, dir, 0.2F);
            doPin(caster, loc.clone().add(0, 1, 0));
            if (caster.isSneaking()) {
                dead = true;
            }
        }


        if (loc.distance(caster.getLocation().add(0, -1, 0)) < 2 && seeking) {
            seeking = false;
            steprange = 60;
            step = 0;
            playSound(Sound.ENTITY_RAVAGER_STUNNED, loc, 2, 0.1F);
            clearHitBlacklist();
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        if (!seeking) {
            p.setVelocity(vel.clone().normalize().multiply(2));
        }
        damage(p, 3, caster);
        playSound(Sound.BLOCK_WOOD_BREAK, loc, 1, 0.5F);
        ParUtils.createBlockcrackParticle(p.getLocation(), 0.2F, 1.2F, 1.2F, 35, Material.OAK_LOG);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        if (!seeking) {
            ent.setVelocity(vel.clone().normalize().multiply(2));
        }
        damage(ent, 3, caster);
        playSound(Sound.BLOCK_WOOD_BREAK, loc, 1, 0.5F);
        ParUtils.createBlockcrackParticle(ent.getLocation(), 0.2F, 1.2F, 1.2F, 35, Material.OAK_LOG);
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub
        vel.add(new Vector(0, 0.2, 0)).normalize();
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub+

        new BukkitRunnable() {
            public void run() {
                for (int i = 0; i < 2; i++) {
                    if (blocks.isEmpty()) {
                        this.cancel();
                        return;
                    }
                    Entity e = blocks.get(0);
                    ParUtils.createParticle(Particle.COMPOSTER, e.getLocation(), 0.2, 0.2, 0.2, 1, 1);
                    ParUtils.createBlockcrackParticle(e.getLocation(), 0.2F, 0.2F, 0.2F, 5, Material.OAK_LOG);
                    e.remove();
                    blocks.remove(e);
                }

                playSound(Sound.BLOCK_WOOD_BREAK, loc, 1, 1);

                if (swap() && refined) {
                    refined = false;

                    Wildwuchs w = new Wildwuchs(true);
                    w.castSpell(caster, name);
                }
            }
        }.runTaskTimer(main.plugin, 1, 1);
    }

}
