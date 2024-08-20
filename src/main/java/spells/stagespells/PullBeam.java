package spells.stagespells;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;

public class PullBeam extends Spell {


    public PullBeam(Player caster, String name) {
        steprange = 100;
        speed = 50;
        castSpell(caster, name);
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

    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        loc.add(loc.getDirection().multiply(0.5));

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
        ParUtils.createFlyingParticle(Particle.CLOUD, loc, 3, 3, 3, 3, 0, caster.getLocation().getDirection().multiply(-10));
        ParUtils.createFlyingParticle(Particle.END_ROD, loc, 5, 5, 5, 3, 1.2F, caster.getLocation().getDirection().multiply(-10));
    }

}
