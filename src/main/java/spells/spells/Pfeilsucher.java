package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;

public class Pfeilsucher extends Spell {

    Location a;
    Arrow mave;
    Vector velocity = new Vector(0, 4, 0);

    public Pfeilsucher(Player p, String name, Location a) {
        this.a = a;
        hitboxSize = 2;
        hitPlayer = true;
        hitSpell = true;

        steprange = 140;
        castSpell(p, name);
    }


    @Override
    public void setUp() {
        loc = a;
        mave = (Arrow) spawnEntity(EntityType.ARROW);
        mave.setGravity(false);
        mave.setDamage(0);
        playSound(Sound.ENTITY_ARROW_SHOOT, loc, 0.1f, 1f);

    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {
        mave.setVelocity(velocity);
        LivingEntity target = getNearestEntity(caster, loc, 50);
        if (target != null) {
            Vector s = target.getLocation().toVector().subtract(loc.toVector()).normalize().multiply(0.1);
            velocity.add(s);
        }
        if (velocity.length() > 0.5)
            velocity.normalize().multiply(0.5);
        loc = mave.getLocation();
        if (mave.isOnGround())
            dead = true;

    }

    @Override
    public void display() {
        ParUtils.createParticle(Particle.WITCH, loc, 0, 0, 0, 1, 0);
    }

    @Override
    public void onPlayerHit(Player p) {
        damage(p, 2, caster);
        playSound(Sound.ENTITY_ARROW_HIT, loc, 0.1f, 1f);
        dead = true;
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        playSound(Sound.ENTITY_ARROW_HIT, loc, 0.1f, 1f);
        damage(ent, 2, caster);
        dead = true;
    }

    @Override
    public void onSpellHit(Spell spell) {

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {
        mave.remove();
    }

}
