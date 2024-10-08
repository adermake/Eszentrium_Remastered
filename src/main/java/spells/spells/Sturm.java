package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;

import java.util.ArrayList;

public class Sturm extends Spell {

    public Sturm() {
        steprange = 5 * 20 * 22;

        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*5
        );
    }

    Location bLoc;

    @Override
    public void setUp() {
        speed = 5;
        bLoc = block(caster);
        if (bLoc == null) {
            refund = true;
            dead = true;
        } else {
            loc = bLoc;
        }

    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    ArrayList<FallingBlock> fBlocks = new ArrayList<FallingBlock>();
    float buildUp = 1;
    int spawnFallingBlock = 0;

    @Override
    public void move() {
        buildUp += 0.2;
        int b = (int) buildUp;
        if (b > 33)
            b = 33;
        for (float i = 0; i < b; i += (i / 10 + 0.5)) {
            float s = (i / 5);
            Location last = ParUtils.stepCalcCircle(loc, ((i * i) / 60) + 1, new Vector(0, 1, 0), i, (step - 1) * s);
            Location p1 = ParUtils.stepCalcCircle(loc, ((i * i) / 60) + 1, new Vector(0, 1, 0), i, step * s);
            Vector dir = p1.toVector().subtract(last.toVector()).normalize().add(new Vector(0, 1, 0));
            ParUtils.createFlyingParticle(Particle.CLOUD, p1, 0.1F, 0.1F, 0.1F, 1, 0.5F, dir);
        }
        spawnFallingBlock--;
        if (spawnFallingBlock <= 0) {
            Location fpos = ParUtils.grabBlocks(loc, 1, 6).get(0);
            FallingBlock fb = caster.getWorld().spawnFallingBlock(loc.clone().add(0, 1, 0).add(randVector().setY(0)), fpos.getBlock().getType(), (byte) 0);
            fb.setVelocity(fb.getVelocity().add(randVector().add(new Vector(0, 2, 0))));

            fBlocks.add(fb);
            spawnFallingBlock = randInt(0, 11);
        }

        ParUtils.createParticle(Particle.CLOUD, loc, 0, 4, 0, 2, 0.05F);
        if (step % speed == 0) {
            ArrayList<FallingBlock> removeLater = new ArrayList<FallingBlock>();
            for (FallingBlock fb : fBlocks) {
                if (fb.getTicksLived() > randInt(20, 20 * 5 * 20)) {
                    removeLater.add(fb);
                }
                double relativeHeight = fb.getLocation().getY() - loc.getY();
                Location fLoc = fb.getLocation();
                fLoc.setDirection(loc.toVector().subtract(fLoc.toVector()).normalize());
                fLoc.setPitch(0);
                fLoc.setYaw(fLoc.getYaw() - 90);
                fb.setVelocity(fLoc.getDirection().multiply(relativeHeight / 10).add(new Vector(0, 0.6, 0)));

                for (LivingEntity le : loc.getWorld().getLivingEntities()) {
                    if (checkHit(le, fLoc, caster, 4)) {
                        doPull(le, fb.getLocation(), relativeHeight / 10);
                    }
                }

                for (Player pe : Bukkit.getOnlinePlayers()) {
                    if (checkHit(pe, fLoc, caster, 4)) {
                        doPull(pe, fb.getLocation(), relativeHeight / 2);
                    }
                }

            }
            for (FallingBlock fb : removeLater) {
                fBlocks.remove(fb);
            }
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

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {

    }

}
