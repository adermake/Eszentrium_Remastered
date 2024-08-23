package spells.spells;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;

public class Miiilone extends Spell {

    public Miiilone() {
        steprange = 60;
        name = "§4Miiilone";
        traitorSpell = true;

        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                5
        );

        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);
    }

    boolean holding = true;
    Item i;
    ArmorStand ar;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        ItemStack milone = new ItemStack(Material.MELON);

        i = caster.getWorld().dropItem(loc, milone);
        ar = createArmorStand(loc.clone());
        ar.setCustomName("§eMiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiilone");
        ar.setSmall(true);
        ar.setGravity(true);
        ar.setCustomNameVisible(true);
        //ar.addPassenger(i);
        if (caster.isSneaking()) {
            i.setVelocity(caster.getLocation().getDirection().multiply(2));
        } else {
            i.setVelocity(caster.getLocation().getDirection().multiply(1));
            step = 20;
        }

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

        ar.teleport(i.getLocation());
        String name = "§aM";
        int count = 60 - (int) step;
        if (count < 40) {
            name = "§eM";
        }
        if (count < 20) {
            name = "§cM";
        }
        for (int i = 0; i < count; i++) {
            name = name + "i";
        }

        name = name + "lone";
        ar.setCustomName(name);
        loc = i.getLocation();

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
        loc = i.getLocation();
        ParUtils.dropItemEffectRandomVector(loc, Material.MELON_SLICE, 22, 50, 1);
        ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, 0, 0, 0, 5, 1);
        new ExplosionDamage(7, 11, caster, loc, name);
        new Repulsion(7, 3, caster, loc, name);
        i.remove();
        ar.remove();
    }


}
