package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.RepulsionDirectional;

public class Feuerwerkshelix extends Spell {

    public Feuerwerkshelix() {
        name = "§eFeuerwerkshelix";
        steprange = 15;


        hitboxSize = 3;
        speed = 1;
        multihit = true;
        hitSpell = true;

        spellDescription = new SpellDescription(
                "Schießt Feuerwerksraketen in Blickrichtung, die getroffene Gegner mitziehen. Nach kurzer Zeit explodieren die Raketen und schaden nahen Gegnern. Die Raketen können selbst nach der Ausführung noch gesteuert werden.",
                "Schießt Feuerwerksraketen in Blickrichtung, die getroffene Gegner mitziehen. Nach kurzer Zeit explodieren die Raketen und schaden nahen Gegnern. Die Raketen können selbst nach der Ausführung noch gesteuert werden.",
                null,
                null,
                null,
                "Mit Feuerwerksraketen mitfliegen",
                20*27
        );

        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        if (refined)
            steprange = 30;

        f = (Firework) spawnEntity(EntityType.FIREWORK_ROCKET);
        f1 = (Firework) spawnEntity(EntityType.FIREWORK_ROCKET);
        f2 = (Firework) spawnEntity(EntityType.FIREWORK_ROCKET);

        f.setInvulnerable(true);
        f1.setInvulnerable(true);
        f2.setInvulnerable(true);


        FireworkMeta fd = (FireworkMeta) f.getFireworkMeta();
        fd.setUnbreakable(true);
        fd.setPower(2);
        if (refined) {
            fd.addEffect(FireworkEffect.builder()
                    .with(Type.BALL)
                    .withColor(Color.BLUE)
                    .withColor(Color.GREEN)
                    .withColor(Color.TEAL)
                    .withFade(Color.LIME)
                    .build());
        } else {
            fd.addEffect(FireworkEffect.builder()
                    .with(Type.BALL)
                    .withColor(Color.RED)
                    .withColor(Color.ORANGE)
                    .withColor(Color.MAROON)
                    .withFade(Color.BLACK)
                    .build());
        }


        f.setFireworkMeta(fd);
        f1.setFireworkMeta(fd);
        f2.setFireworkMeta(fd);

        loc = caster.getEyeLocation();
    }

    Firework f;
    Firework f1;
    Firework f2;

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    /*
     Vector v = caster.getLocation().getDirection();
        v.setY(0);
        v.rotateAroundAxis(new Vector(0,1,0), cast*12);
        v = v.multiply(0.1F);
        f.setVelocity(f.getVelocity().add(v));
     */
    @Override
    public void move() {
        // TODO Auto-generated method stub
        f.setTicksLived(1);
        loc.add(caster.getLocation().getDirection());
        Location cl = loc.clone();
        loc.setDirection(caster.getLocation().getDirection());
        //Matrix.rotateMatrixVectorFunktion(loc.getDirection(), f1);
        f.teleport(ParUtils.stepCalcCircle(cl, 1, caster.getLocation().getDirection(), 0, step * 3 + 12));
        f1.teleport(ParUtils.stepCalcCircle(cl, 1, caster.getLocation().getDirection(), 0, step * 3 + 25));
        f2.teleport(ParUtils.stepCalcCircle(cl, 1, caster.getLocation().getDirection(), 0, step * 3 + 37));
        f.setVelocity(new Vector(0, 0, 0));

        if (step >= 13) {
            f.teleport(loc);
            f1.teleport(loc);
            f2.teleport(loc);
        }

        if (caster.isSneaking() && refined) {
            doPull(caster, loc, 1F);
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        doPull(p, loc, 1F);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        doPull(ent, loc, 1F);
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
        int power = 1;


        if (refined) {
            power = 2;
        }
        // TODO Auto-generated method stub
        new ExplosionDamage(3, 6, caster, loc, name);
        new RepulsionDirectional(3, power, caster, loc, loc.getDirection(), name);
        //ParUtils.parKreisDir(Particle.SMOKE_NORMAL, loc, 3, 0, 0.5F, caster.getLocation().getDirection(), caster.getLocation().getDirection().multiply(-1));
        f.teleport(loc);
        f1.teleport(loc);
        f2.teleport(loc);
        f.detonate();
        f1.detonate();
        f2.detonate();
    }

}
