package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;

public class Feuerball extends Spell {

    public Feuerball() {

        steprange = 200;
        name = "§eFeuerball";
        speed = 1;

        hitSpell = true;
        hitPlayer = true;
        hitBlock = true;

        spellDescription = new SpellDescription(
                "Schießt einen Feuerball in Blickrichtung, der bei Kontakt mit einem Block explodiert und getroffenen Gegnern Schaden zufügt und sie wegschleudert.",
                "Schießt nach kurzer Verzögerung einen riesigen Feuerball, der bei Kontakt mit einem Block explodiert und getroffenen Gegnern Schaden zufügt und sie wegschleudert.",
                null,
                null,
                "Feuerball nach unten umleiten.",
                "Feuerball nach unten umleiten.",
                20*18
        );

        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);

    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

        if (refined) {
            casttime = 30;
        }
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

        ParUtils.dashParticleTo(Particle.FLAME, caster, caster.getLocation().add(randVector().multiply(3)));


    }

    Fireball f;

    @Override
    public void launch() {
        playSound(Sound.ENTITY_BLAZE_SHOOT, caster.getLocation(), 1, 0.1F);
        f = caster.launchProjectile(Fireball.class);
        f.setVelocity(caster.getLocation().getDirection().multiply(3));
        f.setIsIncendiary(false);
        f.setYield(0f);
        f.setShooter(caster);
        dir = caster.getLocation().getDirection();
        if (refined)
            f.setFireTicks(10000);
    }

    Vector dir;

    @Override
    public void move() {
        if (step < 19 && refined)
            ParUtils.dashParticleTo(Particle.FLAME, f, caster.getLocation().add(randVector().multiply(3)));


        loc = f.getLocation();


        if (refined) {
            f.setVelocity(dir.clone().multiply(2));
        }

        if (caster.isSneaking()) {
            if (refined) {
                f.setVelocity(f.getVelocity().add(new Vector(0, -2, 0)));
            } else {
                f.setVelocity(f.getVelocity().add(new Vector(0, -0.5, 0)));
            }

        }


    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        if (f == null || f.isDead())
            dead = true;

    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        damage(p, 5, caster);
        doKnockback(p, loc, 1);

    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        damage(ent, 5, caster);
        doKnockback(ent, loc, 1);

    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub
        if (spell.getName().contains("Antlitz der Göttin"))
            dir = spell.caster.getLocation().getDirection();
    }

    @Override
    public void onBlockHit(Block block) {


    }

    @Override
    public void onDeath() {

        loc = f.getLocation();
        if (refined) {
            ParUtils.createParticle(Particle.EXPLOSION, loc, 0, 0, 0, 4, 1);
            new ExplosionDamage(6, 10, caster, loc, name);


        }
        new Repulsion(5, 2, caster, loc, true, name);

        for (int i = 0; i < 15; i++) {

            Vector vel = new Vector(randInt(-3, 3), randInt(-3, 3), randInt(-3, 3)).add(new Vector(0, 0.1, 0));
            loc.getWorld().spawnFallingBlock(loc, Material.FIRE, (byte) 0).setVelocity(vel.normalize().multiply(1.5));
        }
        callCollision(4);
        if (f != null)
            f.remove();
    }


}
