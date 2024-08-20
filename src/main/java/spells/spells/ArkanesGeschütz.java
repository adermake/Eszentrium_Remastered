package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import spells.spellcore.Spell;

public class ArkanesGeschütz extends Spell {


    ArmorStand bowA;
    ArmorStand stickA;

    public ArkanesGeschütz() {
        name = "§6Arkanes Geschütz";
        cooldown = 20 * 30;
        casttime = 40;
    }

    Location ori;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

        ori = caster.getLocation();
        loc = caster.getLocation().add(0, 1, 0);
        bowA = createArmorStand(caster.getLocation().add(randVector().multiply(5)));
        bowA.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
        bowA.setRightArmPose(new EulerAngle(0, 0, 0));
        bowA.setGravity(true);
        stickA = createArmorStand(caster.getLocation().add(randVector().multiply(5)));
        stickA.getEquipment().setHelmet(new ItemStack(Material.STICK));
        stickA.setRightArmPose(new EulerAngle(0, 0, 0));
        stickA.setGravity(true);
        unHittable.add(bowA);
        unHittable.add(stickA);
        for (int i = 0; i < 40; i++) {
            ParUtils.pullItemEffectVector(loc.clone().add(randVector().multiply(3)), Material.STICK, 52, loc, 0.2, i);
            ParUtils.pullItemEffectVector(loc.clone().add(randVector().multiply(3)), Material.SPRUCE_PLANKS, 152, loc, 0.1, i);
        }
        playSound(Sound.UI_STONECUTTER_TAKE_RESULT, loc, 5, 1);

    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub
        ParUtils.createParticle(Particle.CLOUD, loc, 0.1, 0.1, 0.1, 1, 0);
        ParUtils.pullItemEffectVector(loc.clone().add(randVector().multiply(3)), Material.STICK, 52, loc, 0.3, (int) step);
        // TODO Auto-generated method stub
        doPull(bowA, loc, 1);
        doPull(stickA, loc, 1);
        playSound(Sound.UI_STONECUTTER_TAKE_RESULT, loc, 0.5, 1);

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        dead = true;
        stickA.remove();
        bowA.remove();
        new Crossbow(caster, name, refined, loc, ori);
    }

    @Override
    public void move() {


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
        stickA.remove();
        bowA.remove();
    }

}
