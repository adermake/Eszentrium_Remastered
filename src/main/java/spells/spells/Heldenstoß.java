package spells.spells;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Heldenstoß extends Spell {

    public Heldenstoß() {
        name = "§cHeldenstoß";
        steprange = 50;

        spellDescription = new SpellDescription(
                "Der Spieler fliegt in Blickrichtung. Nach kurzer Zeit oder bei Bodenkontakt entsteht eine Explosion, die Gegnern schadet und sie wegschleudert. Je länger der Zauber angehalten hat, desto stärker ist der Effekt.",
                "",
                null,
                null,
                "Zauber sofort stoppen.",
                null,
                20*45
        );

        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.AURA);
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.SELFCAST);
    }


    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        playSound(Sound.ENTITY_WITHER_SHOOT, caster.getLocation(), 1, 0.3F);
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        //caster.setVelocity(new Vector(0,0.2,0));
    }

    @Override
    public void move() {
        if (caster.isOnGround() && step > 20) {
            dead = true;
        }
        if (caster.isSneaking()) {
            dead = true;
        }

        playSound(Sound.BLOCK_GRINDSTONE_USE, caster.getLocation(), 1, 2F);
        ParUtils.createRedstoneParticle(caster.getLocation(), 0, 0, 0, 1, Color.ORANGE, 10);
        ParUtils.createFlyingParticle(Particle.CRIT, caster.getLocation(), 1, 2, 1, 25, 0.8F, caster.getVelocity());
        ParUtils.createFlyingParticle(Particle.FIREWORK, caster.getLocation(), 1, 2, 1, 5, 0.8F, caster.getVelocity());
        if (!caster.getLocation().getBlock().getType().isSolid()) {
            FallingBlock fb = caster.getWorld().spawnFallingBlock(caster.getLocation().add(caster.getVelocity().multiply(-1)), Material.GOLD_BLOCK, (byte) 0);
            fb.setGravity(false);
            fb.setVelocity(caster.getVelocity().multiply(0.5F));
            new BukkitRunnable() {
                public void run() {
                    fb.remove();
                }
            }.runTaskLater(main.plugin, 30);
        }
        // TODO Auto-generated method stub
        double h = 0.2;
        Vector d = caster.getLocation().getDirection().multiply(0.3);
        //d.setY(0);
        Vector v = caster.getVelocity().add(d.add(new Vector(0, h - h * calcLerpFactor(step, steprange), 0)));

        caster.setVelocity(v);

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
        double lf = calcLerpFactor(step, steprange);
        new spells.stagespells.Explosion(12, 8 * lf, 10 * lf, 1, caster, caster.getLocation().add(0, -2, 0), name);
        if (caster.isOnGround()) {
            caster.setVelocity(caster.getVelocity().add(new Vector(0, 1.5, 0)));
        }

    }

}
