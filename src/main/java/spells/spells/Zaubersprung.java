package spells.spells;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Zaubersprung extends Spell {

    Location block;
    Location back;

    public Zaubersprung() {
        name = "§bZaubersprung";
        steprange = 100;
        speed = 1;
        hitPlayer = false;
        hitEntity = false;
        hitSpell = false;

        spellDescription = new SpellDescription(
                "Teleportiert den Spieler auf den anvisierten Block. Nach kurzer Zeit wird er wieder zum Ursprungsort zurück teleportiert.",
                "Teleportiert den Spieler auf den anvisierten Block.",
                "Sofort zum Ursprungsort zurück teleportieren.",
                "Sofort zum Ursprungsort zurück teleportieren.",
                null,
                null,
                20*22
        );
        
        addSpellType(SpellType.SELFCAST);
        addSpellType(SpellType.MOBILITY);
    }

    @Override
    public void setUp() {
        back = caster.getLocation();
        block = block(caster);
        if (block == null || block.getY() > 250) {
            refund = true;
            dead = true;
        } else {
            block = getTop(block);
            block.setDirection(caster.getLocation().getDirection());
            spiralyPortAniamtion(caster.getLocation(), block);
        }
    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {
        if (dead)
            return;
        caster.teleport(block);
        playSound(Sound.BLOCK_PORTAL_TRIGGER, caster.getLocation(), 1, 2);
    }

    @Override
    public void move() {
        if (checkSilence()) {
            dead = true;
        }
        if (swap()) {
            spiralyPortAniamtion(caster.getLocation(), back);
            caster.teleport(back);
            dead = true;
        }
    }

    @Override
    public void display() {

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
        if (!refined && !refund) {
            spiralyPortAniamtion(caster.getLocation(), back);
            caster.teleport(back);
        }
    }

    public void spiralyPortAniamtion(Location fromC, Location toC) {
        Location l1 = fromC.clone();
        Location l2 = toC.clone();
        Vector dir = l2.toVector().subtract(l1.toVector()).normalize();

        new BukkitRunnable() {
            final double r = 1;
            int t = 0;

            public void run() {
                for (int i = 0; i < 15; i++) {
                    t++;

                    l1.add(dir.clone().multiply(0.5));
                    Location pos = ParUtils.stepCalcCircle(l1, r, dir, 0, t);
                    ParUtils.createParticle(Particle.SNEEZE, pos, 0, 0, 0, 0, 0);
                }

                if (t > 100 || caster.isSneaking()) {
                    this.cancel();
                }

            }
        }.runTaskTimer(main.plugin, 1, 1);
    }


    @Override
    public void onPlayerHit(Player p) {

    }

}
