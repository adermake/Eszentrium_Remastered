package spells.spells;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.TTTCorpse;
import esze.utils.TTTRevive;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class SchreidesPhoenix extends Spell {


    public SchreidesPhoenix() {
        name = "§8Schrei des Phönix";
        hitPlayer = false;
        hitEntity = false;

        addSpellType(SpellType.SELFCAST);
        traitorSpell = true;
        addSpellType(SpellType.SUPPORT);

    }

    @Override
    public void setUp() {
        revive(caster);
    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {
        dead = true;
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
    public void onDeath() {

    }

    public void revive(Player p) {
        Location loc = p.getLocation();
        for (double t = 1; t <= 10; t = t + 0.5) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);

            for (TTTCorpse c : TTTCorpse.allCorpses) {
                if (loc.distance(c.cows.get(0).getLocation()) < 3) {
                    revive(p, c.corpseID, 1);
                    return;
                }
            }

            loc.subtract(x, y, z);
        }
    }

    public void revive(final Player p, int id, final int time) {
        Location loc = p.getLocation();
        for (double t = 1; t <= 10; t = t + 0.5) {
            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);

            for (TTTCorpse c : TTTCorpse.allCorpses) {

                if (loc.distance(c.cows.get(0).getLocation()) < 4) {
                    if ((c.corpseID == id)) {

                        new BukkitRunnable() {
                            public void run() {
                                Player play = c.player;

                                if (time == 40) {
                                    if (play.getGameMode() == GameMode.ADVENTURE) {
                                        play.getInventory().clear();

                                        play.teleport(c.cows.get(0).getLocation());
                                        play.setGameMode(GameMode.SURVIVAL);
                                        try {
                                            TTTRevive.revive(c.player.getName(), c);
                                        } catch (Exception ignored) {
                                        }

                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            all.showPlayer(play);
                                        }
                                        this.cancel();
                                    }
                                } else {
                                    revive(p, id, time + 1);
                                    ParUtils.parLineRedstone(c.cows.getFirst().getLocation(), p.getLocation(), Color.BLACK, 2, 0.2F);
                                    ParUtils.chargeDot(c.cows.getFirst().getLocation(), Particle.TOTEM_OF_UNDYING, 0.3F, 4);
                                    ParUtils.createRedstoneParticle(c.cows.getFirst().getLocation(), 0, 0, 0, 1, Color.BLACK, 1F);
                                    playSound(Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, p.getLocation(), 1, 1);
                                }
                                this.cancel();

                            }
                        }.runTaskTimer(main.plugin, 2, 1);
                        return;

                    }
                }

            }

            loc.subtract(x, y, z);
        }

    }


}
