package spells.stagespells;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.ScoreboardTeamUtils;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;

import java.util.ArrayList;

public class TimerSpellClock extends Spell {

    int seconds;
    int blockCount = 30;
    Location ori;

    public TimerSpellClock(Player c, int seconds, Location ori) {
        this.ori = ori;
        this.seconds = seconds;
        caster = c;
        steprange = 20 * blockCount;
        castSpell(c, "§eTimer");
        speed = blockCount / seconds;
    }


    ArrayList<Entity> blocks = new ArrayList<Entity>();
    ArrayList<Entity> red = new ArrayList<Entity>();

    @Override
    public void setUp() {
        loc = ori.clone();
        // TODO Auto-generated method stub
        double s = 0;
        ParUtils.parKreisDot(Particle.CLOUD, loc, 6, 0, 0.02F, loc.getDirection());
        ParUtils.parKreisDot(Particle.CLOUD, loc, 6, 0, 0.05F, loc.getDirection());
        playGlobalSound(Sound.BLOCK_CONDUIT_ACTIVATE, 1, 1);
        for (int i = 0; i < blockCount; i++) {

            //Slime fb = (Slime) spawnEntity(EntityType.SLIME,loc);
            //fb.setRotation(0, 0);
            //fb.setSize(1);
            Location l = ParUtils.stepCalcCircle(loc.clone(), 6, loc.getDirection(), 0, s * (44 / (double) blockCount));
            //fb.setCollidable(false);
            FallingBlock fb = spawnFallingBlock(l, Material.SEA_PICKLE);
            fb.setGravity(false);
            fb.setGlowing(true);
            //fb.setInvisible(true);
            blocks.add(fb);
            ScoreboardTeamUtils.colorEntity(fb, ChatColor.GREEN);
            s++;
        }
        clearswap();
        new BukkitRunnable() {
            double t = 0;

            @Override
            public void run() {

                // TODO Auto-generated method stub


                for (int i = 0; i < 3; i++) {
                    Location l = ParUtils.stepCalcCircle(loc.clone(), 7, loc.getDirection(), 0, -t / 10 + i * (44 / 3));
                    ParUtils.createFlyingParticle(Particle.END_ROD, l, 0, 0, 0, 1, 0, new Vector(0, 1, 0));
                }

                for (int i = 0; i < 3; i++) {
                    Location l = ParUtils.stepCalcCircle(loc.clone(), 5, loc.getDirection(), 0, t / 5 + i * (44 / 3));
                    ParUtils.createFlyingParticle(Particle.END_ROD, l, 0, 0, 0, 1, 0, new Vector(0, 1, 0));
                }

                if (t % 5 == 0) {
                    playGlobalSound(Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 2);
                }
                t++;
                if (dead) {
                    this.cancel();
                }
            }
        }.runTaskTimer(main.plugin, 0, 1);
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    int tick = 0;
    int delay = 0;

    @Override
    public void move() {
        delay--;
        if (delay <= 0 && swap()) {
            delay = 20;
            step = 0;
            tick = 0;
            for (Entity f : red) {
                ScoreboardTeamUtils.colorEntity(f, ChatColor.GREEN);
            }
            red.clear();
            playGlobalSound(Sound.ENTITY_ARROW_HIT_PLAYER, 1, 0.5F);
            Firework firework = (Firework) caster.getWorld().spawnEntity(loc.clone().add(0, 1, 0), EntityType.FIREWORK_ROCKET);
            FireworkMeta fd = (FireworkMeta) firework.getFireworkMeta();

            fd.addEffect(FireworkEffect.builder()


                    .trail(true)


                    .with(Type.STAR)


                    .withColor(Color.LIME)
                    .withColor(Color.GREEN)


                    .build());

            firework.setFireworkMeta(fd);

            firework.detonate();

        }
        if (step % 20 == 0) {
            tick++;
            for (Entity fb : blocks) {
                if (red.contains(fb)) {
                    ParUtils.createRedstoneParticle(fb.getLocation().add(0, 0.2, 0), 0, 0, 0, 1, Color.RED, 1.2F);
                } else {
                    ParUtils.createRedstoneParticle(fb.getLocation().add(0, 0.2, 0), 0, 0, 0, 1, Color.LIME, 1.2F);
                }
            }

        }
        // TODO Auto-generated method stub


        //Entity a = blocks.get((int) ((tick+5)% blocks.size()));
        //a.getPassengers().get(1).setGlowing(true);
        Entity ent = blocks.get((tick) % blocks.size());
        ScoreboardTeamUtils.colorEntity(ent, ChatColor.RED);

        red.add(ent);

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        double s = 0;
        for (Entity fb : blocks) {
            Location l = ParUtils.stepCalcCircle(loc.clone(), 6, loc.getDirection(), 0, s * (44 / (double) blocks.size()));
            doPin(fb, l, 0.1F);
            s++;
            fb.setTicksLived(1);

        }


        ParUtils.createParticle(Particle.CLOUD, loc, 0.1F, 0.1F, 0.1F, 1, 0.02F);
        Entity ent = blocks.get((tick) % blocks.size());
        ParUtils.parLine(Particle.BUBBLE, loc.clone(), ent.getLocation(), 0, 0, 0, 1, 0, 0.2F);
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


        new BukkitRunnable() {
            int t = 0;

            public void run() {
                t++;
                Firework firework = (Firework) caster.getWorld().spawnEntity(loc.clone().add(randVector().multiply(7)), EntityType.FIREWORK_ROCKET);


                FireworkMeta fd = (FireworkMeta) firework.getFireworkMeta();

                fd.addEffect(FireworkEffect.builder()

                        .flicker(true)

                        .trail(true)

                        .with(Type.BALL)

                        .with(Type.BALL_LARGE)


                        .withColor(Color.RED)
                        .withColor(Color.BLACK)


                        .build());

                firework.setFireworkMeta(fd);

                firework.detonate();
                if (t > 15) {
                    this.cancel();
                }
                //firework.detonate();
            }
        }.runTaskTimer(main.plugin, 0, 1);
        playGlobalSound(Sound.BLOCK_CONDUIT_DEACTIVATE, 1, 1);
        ParUtils.parKreisDot(Particle.CLOUD, loc, 2, 0, 1, loc.getDirection());
        // TODO Auto-generated method stub
        for (Entity fb : blocks) {
            fb.remove();
        }
    }


}
