package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;

public class Align extends Spell {


    ArmorStand a;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        a = createArmorStand(caster.getLocation().setDirection(new Vector(1, 0, 0)));
        a.setVisible(true);
        a.getEquipment().setHelmet(new ItemStack(Material.CHAIN));
        Location l1 = loc.clone();
        l1.setDirection(new Vector(1, 0, 0));


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

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        //Vector dir = caster.getLocation().getDirection();

        //Vector axis = dir.clone().setY(0).crossProduct(dir);
        //axis = dir.clone().crossProduct(axis);

        Vector str = caster.getLocation().getDirection().crossProduct(new Vector(0, 1, 0));
        //setArmorstandHeadPos(a, caster.getLocation().getDirection(), str);
        Bukkit.broadcastMessage("" + Math.toDegrees(a.getHeadPose().getX()) + " " + Math.toDegrees(a.getHeadPose().getY()) + " " + Math.toDegrees(a.getHeadPose().getZ()));
        //a.setHeadPose(new EulerAngle(Math.toRadians(90), 0, 0));
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
