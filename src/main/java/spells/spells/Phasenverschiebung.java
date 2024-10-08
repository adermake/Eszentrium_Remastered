package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;

import java.util.ArrayList;

public class Phasenverschiebung extends Spell {

    ArrayList<Block> blocks = new ArrayList<>();
    ArrayList<Block> oldblocks = new ArrayList<>();
    Vex a;

    public Phasenverschiebung() {
        speed = 1;
        steprange = 100;
    }


    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        loc = caster.getLocation();
        a = (Vex) spawnEntity(EntityType.VEX, loc.clone().add(0, -1, 0));
        a.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 11, 1, false));
        //a.setMarker(true);
        a.setGravity(true);
        a.addPassenger(caster);
        ItemStack is = a.getEquipment().getItemInMainHand();
        is.setAmount(0);
        a.getEquipment().setItemInMainHand(is);
        dir = caster.getLocation().getDirection();
    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    Vector dir;

    @Override
    public void move() {
        a.setVelocity(caster.getLocation().getDirection());
    }

    @Override
    public void display() {
        openTunnel();
        ParUtils.createParticle(Particle.INSTANT_EFFECT, loc, 0, 0, 0, 1, 0);
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
        caster.setGameMode(GameMode.CREATIVE);
        closeTunnel();
        a.remove();
        caster.leaveVehicle();
        caster.setVelocity(dir.multiply(5));
    }


    public void openTunnel() {
        double range = 0;
        loc.setDirection(caster.getLocation().getDirection());
        while (range < 200) {
            int size = 2;
            range += 5;
            loc.add(loc.getDirection().multiply(size - 1));
            if (loc.getY() < 2)
                break;
            if (loc.getY() > 250)
                break;


            ParUtils.createParticle(Particle.CLOUD, loc, 5, 5, 5, 11, 1);

            for (int x = -size; x < size; x++) {
                for (int y = -size; y < size; y++) {
                    for (int z = -size; z < size; z++) {
                        Location l1 = loc.clone().add(new Vector(x, y, z));
                        if (!blocks.contains(l1)) {
                            uncoverBlock(l1.getBlock());
                            blocks.add(l1.getBlock());
                        }


                    }
                }
            }
        }

        for (Block b : oldblocks) {
            if (!blocks.contains(b))
                caster.sendBlockChange(b.getLocation(), b.getLocation().getBlock().getType(), b.getLocation().getBlock().getData());
        }
        oldblocks = (ArrayList<Block>) blocks.clone();
        blocks.clear();
    }


    public void closeTunnel() {
        for (Block b : oldblocks) {
            caster.sendBlockChange(b.getLocation(), b.getLocation().getBlock().getType(), b.getLocation().getBlock().getData());
        }
    }

    public void uncoverBlock(Block b) {
        if (b.getType().isSolid()) {
            caster.sendBlockChange(b.getLocation(), Material.AIR, ((byte) 0));
        }
    }
}
