package spells.spells;

import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Schockwelle extends Spell {

    Player target;

    public Schockwelle() {

        name = "§rSchockwelle";
        hitPlayer = true;
        hitEntity = true;
        hitboxSize = 6;
        casttime = 20;
        steprange = 22;

        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*30
        );

        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.AURA);
        addSpellType(SpellType.SUPPORT);

    }


    @Override
    public void setUp() {

        target = Bukkit.getPlayer(NBTUtils.getNBT("Archon", caster.getInventory().getItemInMainHand()));
        if (target == null) {
            dead = true;
        }
        SoundUtils.playSound(Sound.ENTITY_WITHER_AMBIENT, loc, 0.3F, 30F);
        noTargetEntitys.add(target);
    }

    @Override
    public void cast() {
        ParUtils.chargeDot(target.getLocation(), Particle.END_ROD, 0.2F, 10, 10);
    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {
        SoundUtils.playSound(Sound.ENTITY_WITHER_SPAWN, loc, 2F, 0.1F);

        loc = target.getLocation();
    }

    @Override
    public void display() {
        double t = step * 6;
        for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
            double x = t * Math.cos(theta);
            double y = 0;
            double z = t * Math.sin(theta);
            loc.add(x, y, z);
            int minus = 0;
            while (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
                loc.add(0, -1, 0);
                minus++;
                if (minus >= 256) {
                    break;
                }
            }

            ParUtils.createParticle(Particle.CLOUD, loc.clone().add(0, 1, 0), 0, 1, 0, 0, 1);
            ParUtils.createParticle(Particle.FLASH, loc.clone().add(0, 1, 0), 1, 2, 1, 1, 0);

            collideWithEntity();
            collideWithPlayer();
            loc.subtract(x, y, z);
            loc.add(0, minus, 0);
            theta = theta + Math.PI / 64;

        }
    }

    @Override
    public void onPlayerHit(Player p) {
        double distance = p.getLocation().distance(target.getLocation());
        doKnockback(p, target.getLocation(), 5 * (distance / hitboxSize));
        damage(p, 3, target);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        double distance = ent.getLocation().distance(target.getLocation());
        doKnockback(ent, target.getLocation(), 5 * (distance / hitboxSize));
        damage(ent, 3, target);
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
