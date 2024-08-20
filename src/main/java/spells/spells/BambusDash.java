package spells.spells;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import spells.spellcore.Spell;

public class BambusDash extends Spell {

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        cooldown = 20 * 10;
        name = "§eBambusDash";
        speed = 3;
        steprange = 10;
        hitPlayer = false;
        hitSpell = false;

    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    Vector dir;

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        dir = caster.getLocation().getDirection().normalize();
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        caster.setVelocity(dir.normalize().multiply(1.2F));
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        ParUtils.dropItemEffectRandomVector(caster.getLocation(), Material.BAMBOO, 2, 11, 0.3F);
        playSound(Sound.BLOCK_BAMBOO_BREAK, caster.getLocation(), 1, 1);
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

    }

}
