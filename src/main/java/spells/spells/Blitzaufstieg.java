package spells.spells;

import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Blitzaufstieg extends Spell {

    Player target;

    public Blitzaufstieg() {

        name = "§rBlitzaufstieg";
        hitPlayer = true;
        hitEntity = true;
        hitboxSize = 8;
        casttime = 20;
        steprange = 4;

        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*20
        );

        addSpellType(SpellType.SUPPORT);
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.KNOCKBACK);
    }


    @Override
    public void setUp() {
        target = Bukkit.getPlayer(NBTUtils.getNBT("Archon", caster.getInventory().getItemInMainHand()));
        if (target == null) {
            dead = true;
            hitPlayer = false;
            hitEntity = false;
        }

        // TODO Auto-generated method stub
        Location l = block(caster);
        if (l == null) {
            hitPlayer = false;
            hitEntity = false;
            refund = true;
            dead = true;
        } else {
            loc = l.clone();
            if (!dead) {
                ParUtils.parKreisDot(Particle.CLOUD, loc, 3, 0, 0.1, new Vector(0, 1, 0));
                SoundUtils.playSound(Sound.BLOCK_CONDUIT_ACTIVATE, loc, 1, 40);
            }

        }
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub
        //ParUtils.chargeDot(loc, Particle.CLOUD, 1, 5);
        if (!dead)
            ParUtils.chargeDot(loc, Particle.END_ROD, 0.2, 5, 10);

    }

    @Override
    public void launch() {
        if (!dead) {


            // TODO Auto-generated method stub
            loc.add(0, 1, 0);
            ParUtils.createParticle(Particle.FLASH, loc, 3, 3, 3, 10, 1);
            float speed = 3;
            ParUtils.parKreisDot(Particle.CLOUD, loc, 3, 0, -0.5, new Vector(0, 1, 0));
            ParUtils.parKreisDir(Particle.CLOUD, loc, 3, 0, 2 * speed, new Vector(0, 1, 0), new Vector(0, 1, 0));
            ParUtils.parKreisDir(Particle.CLOUD, loc, 5, 0, 1.5 * speed, new Vector(0, 1, 0), new Vector(0, 1, 0));
            ParUtils.parKreisDir(Particle.CLOUD, loc, 7, 0, 1 * speed, new Vector(0, 1, 0), new Vector(0, 1, 0));
            ParUtils.parKreisDir(Particle.CLOUD, loc, 9, 0, 0.5 * speed, new Vector(0, 1, 0), new Vector(0, 1, 0));
            loc.getWorld().strikeLightningEffect(loc);
            loc.getWorld().strikeLightningEffect(loc);
            loc.getWorld().strikeLightningEffect(loc);
        }
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        p.setVelocity(new Vector(0, 3, 0));
        if (p != target)
            damage(p, 6, target);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        ent.setVelocity(new Vector(0, 3, 0));
        damage(ent, 6, target);
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
