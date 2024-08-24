package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Schleimschleuder extends Spell {

    public Schleimschleuder() {


        name = "§6Schleimschleuder";
        speed = 1;
        hitEntity = false;
        hitPlayer = false;
        hitBlock = false;
        hitSpell = true;

        spellDescription = new SpellDescription(
                "Beschwört einen Schleim vor dem Spieler, der Gegner in der Nähe verlangsamt. Je weiter der Spieler vom Schleim entfernt ist, desto größer wird dieser und der Verlangsamungsradius. Nach kurzer Zeit wird der Spieler zurück zum Schlim gezogen und schleudert diesen weg. Alle Gegner in der Nähe des Schleims werden mitgezogen.",
                "Beschwört einen Schleim vor dem Spieler, der Gegner in der Nähe verlangsamt. Je weiter der Spieler vom Schleim entfernt ist, desto größer wird dieser und der Verlangsamungsradius. Nach kurzer Zeit wird der Spieler zurück zum Schlim gezogen und schleudert diesen weg. Alle Gegner in der Nähe des Schleims werden mitgezogen.",
                "In Blickrichtung springen.",
                "In Blickrichtung springen und alle verlangsamten Gegner zum Schleim hinziehen.",
                null,
                null,
                20*25
        );

        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.PROJECTILE);
        addSpellType(SpellType.SELFCAST);
    }

    Slime g;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        playSound(Sound.ENTITY_SLIME_ATTACK, loc, 5, 1);
        // TODO Auto-generated method stub
        g = (Slime) spawnEntity(EntityType.SLIME);
        disableEntityHitbox(g);
        g.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 10000000, 100));
        g.setInvulnerable(true);
        g.setSize(1);
        noTargetEntitys.add(g);
        g.setVelocity(loc.getDirection().multiply(s));

        //caster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,40,9));
    }

    float s = 1;
    float size = 1;
    float time = 0;
    int dash = 0;
    boolean stagedone = false;
    int time2 = 0;
    boolean dashCharge = true;
    Vector dir;

    @Override
    public void move() {

        if (dead)
            return;


        if (swap() && dashCharge) {
            dashCharge = false;
            playSound(Sound.ENTITY_SLIME_JUMP, caster.getLocation(), 2, 1);
            caster.setVelocity(caster.getLocation().getDirection().multiply(2));
            if (refined) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (checkHit(p, g.getLocation(), caster, g.getSize() * 3 + 3)) {
                        doPin(p, g.getLocation());
                    }
                }
            }

        }

        if (!stagedone) {
            dir = g.getLocation().toVector().subtract(caster.getLocation().toVector()).normalize();
            time++;
            //if (time == 20*2)

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (checkHit(p, g.getLocation(), caster, g.getSize() * 2 + 3)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20, 3));
                }
            }
            if (time > 30) {
                dash++;

                if (dash == 1) {
                    doPull(caster, g.getLocation(), 1);
                    playSound(Sound.ENTITY_SLIME_DEATH, caster.getLocation(), 2, 1);
                }
                if (dash == 4) {
                    doPull(caster, g.getLocation(), 2);
                    playSound(Sound.ENTITY_SLIME_DEATH, caster.getLocation(), 2, 1);
                }

                if (dash >= 9) {
                    playSound(Sound.ENTITY_SLIME_DEATH, caster.getLocation(), 2, 1);
                    int d = dash;
                    if (dash > 6)
                        d = 6;
                    doPull(caster, g.getLocation(), d / 2);
                }

                if (g.getLocation().distance(caster.getLocation()) < size / 2 + 6) {
                    stagedone = true;
                    hitPlayer = true;
                    hitEntity = true;
                    hitboxSize = 2 + size * 3F;
                    playSound(Sound.BLOCK_SLIME_BLOCK_HIT, loc, 10, 1);
                    if (caster.isSneaking()) {
                        caster.setVelocity(caster.getVelocity().multiply(-1));
                    }
                }
                if (dash > 30) {
                    dead = true;
                }
            } else {
                g.setSize((int) caster.getLocation().distance(loc) / 2 + 1);
            }


            s -= 0.1;
            if (s <= 0)
                s = 0;
            g.setVelocity(loc.getDirection().add(new Vector(0, -0.2, 0)).multiply(s));

        } else {
            if (dash < 5)
                dead = true;
            g.setVelocity(dir.normalize().multiply(dash / 3));
            time2++;
            loc = g.getLocation();

            if (time2 > 20) {
                dead = true;

            }
            for (Entity ent : hitEntitys) {
                ent.setVelocity(g.getVelocity());
            }
        }


    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        if (!stagedone) {
            ParUtils.pullItemEffectVector(caster.getLocation(), Material.SLIME_BLOCK, 44, g, 1F);
            ParUtils.pullItemEffectVector(g.getLocation(), Material.LIME_STAINED_GLASS, 44, caster, 1F);
        }

    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        p.setVelocity(g.getVelocity());
        damage(p, 4, caster);
        ParUtils.createParticle(Particle.ITEM_SLIME, loc, 1, 1, 1, 10, 1);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        ent.setVelocity(g.getVelocity());
        damage(ent, 4, caster);
        ParUtils.createParticle(Particle.ITEM_SLIME, loc, 1, 1, 1, 10, 1);
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

        ParUtils.createParticle(Particle.ITEM_SLIME, loc, 1, 1, 1, 10, 1);
        if (!g.isDead())
            g.remove();


    }

}
