package spells.spells;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Verzaubern extends Spell {

    Sheep sheep;
    Player target;

    public Verzaubern() {
        name = "§4Verzaubern";
        hitSpell = true;
        speed = 1;
        steprange = 8 * 20;

        traitorSpell = true;

        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20
        );

        addSpellType(SpellType.LOCKDOWN);
    }


    @Override
    public void onDeath() {
        if (sheep != null) {
            if (!sheep.isDead()) {
                PlayerUtils.showPlayer(target);
                target.teleport(sheep.getLocation());
                target.setGameMode(GameMode.SURVIVAL);
            } else {

                tagPlayer(target);
                target.setGameMode(GameMode.SURVIVAL);
                target.damage(40);
            }
            sheep.remove();
        }
    }

    @Override
    public void setUp() {
        target = pointEntity(caster, 10);

        if (target == null) {
            refund = true;
            dead = true;
        } else {
            if (sheep != null)
                return;

            PlayerUtils.hidePlayer(target);
            Sheep s = (Sheep) target.getWorld().spawnEntity(target.getLocation(), EntityType.SHEEP);
            sheep = s;
            s.setMaxHealth(20);
            s.setHealth(20);
            s.setCustomName(target.getName());
            Bukkit.getScheduler().runTaskAsynchronously(main.plugin, new Runnable() {

                @Override
                public void run() {
                    try {
                        BufferedImage image = ImageIO.read(new URL("https://crafatar.com/skins/" + target.getUniqueId()));
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

                        DyeColor lowest = null;
                        int lowestval = 10000000;

                        for (DyeColor dyecolor : DyeColor.values()) {
                            if (dyecolor == DyeColor.BLACK)
                                continue;
                            if (dyecolor == DyeColor.GRAY)
                                continue;
                            if (dyecolor == DyeColor.WHITE)
                                continue;
                            int r = dyecolor.getColor().getRed();
                            int g = dyecolor.getColor().getGreen();
                            int b = dyecolor.getColor().getBlue();
                            int sr = r - c.getRed();
                            int sg = g - c.getGreen();
                            int sb = b - c.getBlue();
                            if (sr < 0)
                                sr = -sr;
                            if (sg < 0)
                                sg = -sg;
                            if (sb < 0)
                                sb = -sb;
                            int value = sr + sb + sg;

                            if (lowestval >= value) {
                                lowestval = value;
                                lowest = dyecolor;
                            }

                        }
                        s.setColor(lowest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            s.setCustomName(target.getName());


            target.setGameMode(GameMode.SPECTATOR);
            target.setAllowFlight(true);
            target.setFlying(true);

            target.setSpectatorTarget(sheep);
            ParUtils.createParticle(Particle.EXPLOSION, target.getLocation(), 0, 0, 0, 3, 1);

            ParUtils.chargeDot(target.getLocation(), Particle.WITCH, 0.1, 4, 10);

        }
    }


    @Override
    public void cast() {

    }

    @Override
    public void move() {


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
    public void launch() {
        loc = caster.getEyeLocation();
    }

}
