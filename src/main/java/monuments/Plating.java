package monuments;

import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class Plating {

    Monument m;
    ArmorStand armorStand;
    double currentHealth = 20;
    double maxHealth = 20;

    public Plating(Monument m) {
        this.m = m;
        maxHealth = m.platingMaxHealth;
        currentHealth = m.platingMaxHealth;
        ArmorStand plating = m.createArmorStand(m.getOffsetLoc());
        plating.setGravity(true);
        m.disableEntityHitbox(plating);
        plating.getEquipment().setHelmet(new ItemStack(m.pState1));
        armorStand = plating;
        m.platings.add(this);
    }

    public boolean damage(double damage) {
        currentHealth -= damage;
        double per = currentHealth / maxHealth;


        if (per < 1D / 3D) {
            armorStand.getEquipment().setHelmet(new ItemStack(m.pState3));
            ParUtils.createBlockcrackParticle(getLocation(), 0.3, 0.3, 0.3, 10, m.pState3);
        } else if (per < 2D / 3D) {
            armorStand.getEquipment().setHelmet(new ItemStack(m.pState2));
            ParUtils.createBlockcrackParticle(getLocation(), 0.3, 0.3, 0.3, 10, m.pState2);
        } else if (per < 1D) {
            armorStand.getEquipment().setHelmet(new ItemStack(m.pState1));
            ParUtils.createBlockcrackParticle(getLocation(), 0.3, 0.3, 0.3, 10, m.pState1);
        }

        return currentHealth <= 0;
    }

    public void checkState() {

        double per = currentHealth / maxHealth;

        if (m.currentNoDamageTicks > 0) {
            if (armorStand.getEquipment().getHelmet().getType() != Material.BEDROCK)
                armorStand.getEquipment().setHelmet(new ItemStack(Material.BEDROCK));

        } else if (per < 1D / 3D) {
            if (armorStand.getEquipment().getHelmet().getType() != m.pState3)
                armorStand.getEquipment().setHelmet(new ItemStack(m.pState3));

        } else if (per < 2D / 3D) {
            if (armorStand.getEquipment().getHelmet().getType() != m.pState2)
                armorStand.getEquipment().setHelmet(new ItemStack(m.pState2));

        } else {
            if (armorStand.getEquipment().getHelmet().getType() != m.pState1)
                armorStand.getEquipment().setHelmet(new ItemStack(m.pState1));

        }
    }

    public void destroy() {
        ParUtils.createParticle(Particle.EXPLOSION, getLocation(), 0, 0, 0, 1, 1);
        m.playSound(Sound.ENTITY_GENERIC_EXPLODE, getLocation(), 1, 0.5F);
        m.platings.remove(this);
        armorStand.remove();

    }

    public Location getLocation() {
        return armorStand.getLocation().add(0, 1, 0);

    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void setArmorStand(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(double currentHealth) {
        this.currentHealth = currentHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

}
