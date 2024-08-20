package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Druckwelle extends Spell {
    Player target;

    public Druckwelle() {

        name = "§rDruckwelle";
        cooldown = 20 * 25;
        hitPlayer = false;
        hitEntity = false;
        hitboxSize = 1;

        addSpellType(SpellType.KNOCKBACK);

        addSpellType(SpellType.AURA);

    }


    @Override
    public void setUp() {

        target = Bukkit.getPlayer(NBTUtils.getNBT("Archon", caster.getInventory().getItemInMainHand()));
        if (target == null) {
            dead = true;
        }
        // TODO Auto-generated method stub
        //ParUtils.createParticle(Particle.FLASH,target.getLocation(), 0, 0, 0, 1, 1);
        //ParUtils.createParticle(Particle.END_ROD,target.getLocation(), 0, 0, 0, 222, 10);
        //ParUtils.createParticle(Particle.ENCHANT,target.getLocation(), 0, 0, 0, 102, 10);
        SoundUtils.playSound(Sound.ENTITY_WITHER_HURT, loc, 0.3F, 30F);
        noTargetEntitys.add(target);
        loc = target.getLocation();
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    boolean charging = true;

    @Override
    public void move() {
        if (charging) {
            Vector dir = caster.getLocation().toVector().subtract(loc.toVector()).normalize();
            ParUtils.createParticle(Particle.END_ROD, loc, 0, 0, 0, 3, 0);
            ParUtils.createFlyingParticle(Particle.CLOUD, loc, 0, 0, 0, 1, 0.2F, dir);
            ParUtils.createParticle(Particle.CRIT, loc, 0.1, 0.1, 0.1, 5, 5);
            loc.add(dir.multiply(1.3F));
        } else {

            loc.add(caster.getLocation().getDirection().multiply(2));
            ParUtils.createParticle(Particle.FLASH, loc, 4, 4, 3, 10, 1);

        }

        if (loc.distance(caster.getLocation()) < 2 && charging) {
            hitPlayer = true;
            hitEntity = true;
            hitboxSize = 6;
            charging = false;
            steprange = 15;
            loc = caster.getLocation();
            caster.setVelocity(caster.getLocation().getDirection().multiply(-1));
            SoundUtils.playSound(Sound.BLOCK_CONDUIT_ACTIVATE, loc, 2, 5);
            SoundUtils.playSound(Sound.ENTITY_GENERIC_EXPLODE, loc, 0.5F, 5);
            ParUtils.parKreisDir(Particle.CLOUD, loc, 3, 0, 2, caster.getLocation().getDirection(), caster.getLocation().getDirection());
            ParUtils.parKreisDir(Particle.CLOUD, loc, 5, 0, 1, caster.getLocation().getDirection(), caster.getLocation().getDirection());
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        if (p == target)
            return;
        double distance = p.getLocation().distance(target.getLocation());
        p.setVelocity(caster.getLocation().getDirection().multiply(2));
        damage(p, 3, target);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        double distance = ent.getLocation().distance(target.getLocation());
        ent.setVelocity(caster.getLocation().getDirection().multiply(2));
        damage(ent, 3, target);
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

    }

}
