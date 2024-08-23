package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Flucht extends Spell {

    public Flucht() {


        steprange = 20 * 3;
        name = "§bFlucht";
        speed = 1;

        hitPlayer = false;
        hitBlock = false;
        hitSpell = false;
        spellDescription = new SpellDescription(
                "Verwandelt den Spieler für kurze Zeit in eine Fledermaus und beschwört einen Schwarm an Fledermäusen. Solange sich der Spieler in dieser Form befindet, fliegt er in Blickrichtung voraus.",
                "Verwandelt den Spieler für kurze Zeit in eine Fledermaus und beschwört einen Schwarm an Fledermäusen, die nahe Gegner wegschieben. Solange sich der Spieler in dieser Form befindet, fliegt er in Blickrichtung voraus.",
                null,
                null,
                "Beendet den Zauber sofort.",
                "Beendet den Zauber sofort.",
                20*30
        );
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.AURA);
        addSpellType(SpellType.SELFCAST);
    }


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
        // TODO Auto-generated method stub
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.hidePlayer(main.plugin, caster);
        }

        ParUtils.createParticle(Particle.LARGE_SMOKE, loc, 1, 1, 1, 100, 0);
        caster.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 100));
        follow(bat(caster, refined), caster);
        for (int i = 0; i < 35; i++) {
            bat(caster, refined);
        }
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        caster.setVelocity(caster.getLocation().getDirection().multiply(0.6));
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
        if (caster.getGameMode() != GameMode.ADVENTURE) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.showPlayer(main.plugin, caster);
            }
        }

    }

    public LivingEntity bat(final Player p, boolean refined) {

        final Bat bat = (Bat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BAT);
        unHittable.add(bat);


        int start = 0;
        if (refined) {
            start = randInt(-40, 0);
        }
        final int fstart = start;
        new BukkitRunnable() {
            int t = fstart;

            public void run() {
                t++;
                if (t > 100) {
                    if (refined) {
                        new spells.stagespells.Explosion(3, 6, 1, 1.2F, caster, bat.getLocation(), name);
                    }
                    this.cancel();
                    bat.remove();
                }

                if (refined && t % 10 == 0) {
                    ParUtils.createParticle(Particle.FLAME, bat.getLocation(), 0.01, 0.01, 0.01, 1, 0.01);
					/*
					bat.setVelocity(bat.getLocation().getDirection());
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (caster == p)
							continue;
						
						if (p.getGameMode() == GameMode.SURVIVAL) {
							if (bat.getLocation().distance(p.getLocation())<2) {
								tagPlayer(p);
								p.setVelocity(bat.getVelocity());
							}
						}
					}
					*/
                }
            }
        }.runTaskTimer(main.plugin, 1, 1);
        return bat;
    }

    public void follow(LivingEntity le, LivingEntity p) {
        new BukkitRunnable() {
            public void run() {

                if (caster.isSneaking()) {
                    this.cancel();
                    dead = true;
                    le.remove();
                }
                if (le.isDead()) {
                    this.cancel();
                }
                le.teleport(p);
                //doPull(le, p.getLocation(), 0.8D);
            }
        }.runTaskTimer(main.plugin, 1, 1);
    }


}
