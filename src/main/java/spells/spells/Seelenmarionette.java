package spells.spells;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Seelenmarionette extends Spell {


    public static ArrayList<Seelenmarionette> souls = new ArrayList<Seelenmarionette>();

    public Drowned ghost;

    public Seelenmarionette() {

        name = "§cSeelenmarionette";
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.AURA);
        addSpellType(SpellType.SUPPORT);
        cooldown = 20 * 50;
        autocancel = true;
        souls.add(this);
        hitSpell = false;
        hitBlock = false;
        hitPlayer = false;
        steprange = 20 * 10;
        setLore("§7Die Seele des Spielers verlässt seinen Körper, was ihn fliegen lässt und unverwundbar macht. Der Körper bleibt hierbei zurück und kann weiterhin angegriffen werden, was dem Spieler auch schadet. §eF: §7Beendet den Zauber.");
    }

    double radius = 30;

    Location lastLoc;

    @Override
    public void setUp() {


        lastLoc = caster.getLocation();
        caster.getInventory().setHelmet(new ItemStack(Material.SKELETON_SKULL));
        caster.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 2));
        playSound(Sound.ENTITY_VEX_HURT, caster.getLocation(), 1, 0.2F);
        playSound(Sound.ENTITY_VEX_HURT, caster.getLocation(), 1, 0.2F);
        playSound(Sound.ENTITY_VEX_HURT, caster.getLocation(), 1, 0.2F);
        playSound(Sound.ENTITY_VEX_AMBIENT, caster.getLocation(), 5, 0.5F);
        originalCaster.setAllowFlight(true);
        originalCaster.setFlying(true);
        unHittable.add(caster);


        ghost = (Drowned) spawnEntity(EntityType.DROWNED);
        ghost.getEquipment().clear();
        ghost.setCustomName("Ghost");
        ghost.setAdult();
        ItemStack playerhead = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        String name = caster.getName();
        SkullMeta playerheadmeta = (SkullMeta) playerhead.getItemMeta();
        playerheadmeta.setOwner(name);
        playerheadmeta.setDisplayName(name + "'s Kopf");
        playerhead.setItemMeta(playerheadmeta);
        ghost.getEquipment().setHelmet(playerhead);
        ghost.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
        ghost.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100000, 4));
        ghost.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100000, 100));
        ghost.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100000, 10));


        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    BufferedImage image = ImageIO.read(new URL("https://crafatar.com/skins/" + caster.getUniqueId().toString()));
                    int height = image.getHeight();
                    int width = image.getWidth();

                    Map m = new HashMap();
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            int rgb = image.getRGB(i, j);
                            int[] rgbArr = getRGBArr(rgb);
                            // Filter out grays....
                            if (!isGray(rgbArr)) {
                                Integer counter = (Integer) m.get(rgb);
                                if (counter == null)
                                    counter = 0;
                                counter++;
                                m.put(rgb, counter);
                            }
                        }
                    }
                    int[] colours = getMostCommonColour(m);
                    Color c = Color.fromRGB(colours[0], colours[1], colours[2]);

                    //Bukkit.broadcastMessage(c.asRGB()+"");

                    setLeatherArmor(ghost, c);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskLaterAsynchronously(main.plugin, 0);


        caster.setVelocity(new Vector(0, 0.5, 0).add(caster.getLocation().getDirection()));
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
        ParUtils.createFlyingParticle(Particle.LARGE_SMOKE, ghost.getLocation(), 0.05, 1, 0.05, 2, 0.05F, new Vector(0, 1, 0).add(randVector().normalize().multiply(0.2F)));
        ParUtils.createFlyingParticle(Particle.SMOKE, ghost.getLocation(), 0.05, 1, 0.05, 10, 0.1F, caster.getLocation().toVector().subtract(ghost.getLocation().toVector()).normalize().multiply(4).add(randVector()));

        if (caster.getLocation().distance(ghost.getLocation()) > radius) {

            ParUtils.parLine(Particle.SMOKE, caster.getLocation().add(0, 1, 0), ghost.getLocation().add(0, 1, 0), 0.1F, 0.1F, 0.1F, 1, 0.01, 0.5);
            doPull(caster, ghost.getLocation(), 1);
            playSound(Sound.ENTITY_VEX_AMBIENT, caster.getLocation(), 5, 0.7F);
        }
        if (caster.getLocation().distance(ghost.getLocation()) > radius * 2) {
            ParUtils.parLine(Particle.SMOKE, caster.getLocation().add(0, 1, 0), ghost.getLocation().add(0, 1, 0), 0.1F, 0.1F, 0.1F, 1, 0.01, 0.5);
            caster.teleport(ghost.getLocation());
        }

        if (swap() || ghost.isDead()) {
            dead = true;
        }

        Vector v = caster.getLocation().toVector().subtract(lastLoc.toVector());


        ParUtils.createFlyingParticle(Particle.LARGE_SMOKE, lastLoc, 0.5F, 0.5F, 0.5F, 1, v.length(), v.normalize().multiply(1));
        ParUtils.createRedstoneParticle(caster.getLocation().add(0, 1, 0), 0.1F, 0.1F, 0.1F, 2, Color.BLACK, 10);
        lastLoc = caster.getLocation();
        //ParUtils.parLineRedstone(caster.getLocation().add(0,1,0), ghost.getLocation().add(0,1,0), Color.BLACK, 0.4F, 0.5F);
        //ParUtils.createParticle(Particle.ASH, ghost.getLocation(), 1, 1, 1,2, 1);
    }


    public void ghostDamaged(double dmg) {
        playSound(Sound.PARTICLE_SOUL_ESCAPE, caster.getLocation(), 5, 2F);
        caster.damage(dmg);
        //caster.setHealth(caster.getHealth()-dmg);
        //ghost.setHealth(caster.getHealth());
        ParUtils.parLine(Particle.CRIT, caster.getLocation().add(0, 1, 0), ghost.getLocation().add(0, 1, 0), 0.1F, 0.1F, 0.1F, 1, 0.01, 0.5);
        //ParUtils.dashParticleTo(Particle.EFFECT, caster, ghost.getLocation());
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
        caster.getInventory().setHelmet(new ItemStack(Material.AIR));
        caster.removePotionEffect(PotionEffectType.INVISIBILITY);
        souls.remove(this);
        originalCaster.setFlying(false);
        originalCaster.setAllowFlight(false);
        unHittable.remove(originalCaster);
        ghost.remove();
        Location l = ghost.getLocation();
        l.setDirection(caster.getLocation().getDirection());
        caster.teleport(l);
        playSound(Sound.ENTITY_VEX_DEATH, caster.getLocation(), 1, 1F);
    }

}
