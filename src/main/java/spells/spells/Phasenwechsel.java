package spells.spells;

import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Phasenwechsel extends Spell {

    Player target;
    boolean charging = true;

    public Phasenwechsel() {

        name = "§rPhasenwechsel";
        hitPlayer = false;
        hitEntity = false;
        hitboxSize = 5;

        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*30
        );

        addSpellType(SpellType.SUPPORT);
        addSpellType(SpellType.MOBILITY);

    }


    @Override
    public void setUp() {
        target = Bukkit.getPlayer(NBTUtils.getNBT("Archon", caster.getInventory().getItemInMainHand()));
        if (target == null) {
            dead = true;
        }
        SoundUtils.playSound(Sound.ENTITY_WITHER_HURT, loc, 0.3F, 30F);
        noTargetEntitys.add(target);
        loc = target.getLocation();
    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }


    @Override
    public void move() {
        if (charging) {
            Vector dir = caster.getLocation().toVector().subtract(loc.toVector()).normalize();

            ParUtils.createParticle(Particle.END_ROD, loc, 0, 0, 0, 3, 0);
            ParUtils.createFlyingParticle(Particle.CLOUD, loc, 0, 0, 0, 1, 0.2F, dir);
            ParUtils.createParticle(Particle.ENCHANT, loc, 0.1, 0.1, 0.1, 5, 5);
            loc.add(dir.multiply(2F));
        }

        if (loc.distance(caster.getLocation()) < 2 && charging) {
            ParUtils.createParticle(Particle.FLASH, target.getLocation(), 0, 0, 0, 1, 1);
            ParUtils.createParticle(Particle.FLASH, caster.getLocation(), 0, 0, 0, 1, 1);
            SoundUtils.playSound(Sound.ENTITY_SHULKER_TELEPORT, loc, 1.4F, 10);
            target.teleport(caster.getLocation());
            dead = true;
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
