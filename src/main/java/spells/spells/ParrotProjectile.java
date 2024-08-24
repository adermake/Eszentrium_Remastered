package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class ParrotProjectile extends Spell {


    public ParrotProjectile(Player p, String name, boolean refined) {

        this.refined = refined;
        this.name = name;
        caster = p;
        speed = 3;
        steprange = 40;
        castSpell(caster, name);
        addSpellType(SpellType.PROJECTILE);
        addSpellType(SpellType.KNOCKBACK);
        hitboxSize = 1.5F;
        if (refined) {
            steprange = 80;
        }
    }

    double rad = 1;
    Parrot par;

    @Override
    public void setUp() {
        Location addSide = loc.clone();
        addSide.setYaw(addSide.getYaw() + 90);
        Location l1 = ParUtils.stepCalcCircle(caster.getLocation(), rad, caster.getLocation().getDirection(), -1, 0).add(0, 1, 0);

        if (randInt(0, 1) == 1) {
            l1 = ParUtils.stepCalcCircle(caster.getLocation(), rad, caster.getLocation().getDirection(), -1, 22).add(0, 1, 0);
        }
        ParUtils.createFlyingParticle(Particle.CLOUD, l1, 0, 0, 0, 1, 1, caster.getLocation().getDirection());
        Location side = l1;
        par = (Parrot) spawnEntity(EntityType.PARROT, side);
        par.setBaby();
        playSound(Sound.ENTITY_PARROT_FLY, loc, 5, 1);

    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {
        if (step > 10)
            par.setAdult();

        loc.setDirection(loc.getDirection().add(randVector().multiply(0.1F)));
    }

    @Override
    public void move() {
        loc.add(loc.getDirection());
    }

    @Override
    public void display() {
        par.teleport(loc);
    }

    @Override
    public void onPlayerHit(Player p) {
        damage(p, 1, caster);
        p.setVelocity(p.getVelocity().add(loc.getDirection().multiply(3)));
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        damage(ent, 1, caster);
        ent.setVelocity(ent.getVelocity().add(loc.getDirection().multiply(3)));
    }

    @Override
    public void onSpellHit(Spell spell) {

    }

    @Override
    public void onBlockHit(Block block) {
        dead = true;
    }

    @Override
    public void onDeath() {
        playSound(Sound.ENTITY_PARROT_DEATH, loc, 5, 2);
        ParUtils.dropItemEffectRandomVector(loc, Material.FEATHER, 5, 20, 1);
        if (par != null)
            par.remove();
    }

}
