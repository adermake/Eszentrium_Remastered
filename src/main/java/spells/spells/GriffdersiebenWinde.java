package spells.spells;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import spells.spellcore.EventCollector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class GriffdersiebenWinde extends Spell {


    public GriffdersiebenWinde() {
        steprange = 45;
        speed = 1;
        name = "§4Griff der sieben Winde";
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

        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.LOCKDOWN);

    }

    Player target;

    @Override
    public void setUp() {
        if (EventCollector.quickSwap.contains(caster)) {
            EventCollector.quickSwap.remove(caster);
        }
        target = pointEntity(caster);
        if (target == null) {
            refund = true;
        } else {
            dis = caster.getLocation().distance(target.getEyeLocation()) + 5;
            new BukkitRunnable() {
                int t = 0;

                @Override
                public void run() {
                    t++;
                    if (t > 46) {
                        this.cancel();
                    }
                    if (target == null)
                        return;

                    if (EventCollector.quickSwap.contains(caster)) {
                        EventCollector.quickSwap.remove(caster);
                        this.cancel();
                    }
                    Location loca = loc(caster, dis);

                    if (caster.isSneaking()) {
                        dis++;
                    }


                    if (target.getLocation().distance(loca) > 2) {
                        tagPlayer(target);
                        doPull(target, loca, target.getLocation().distance(loca) / 5);
                    }


                    ParUtils.createParticle(Particle.CLOUD, target.getLocation(), 0, 0, 0, 1, 0);
                    playSound(Sound.ENTITY_CAT_HISS, loca, 0.1F, 0.1F);

                }
            }.runTaskTimer(main.plugin, 1, 1);
        }
        dead = true;


    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    double dis = 0;

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
    public void onDeath() {

    }


}
