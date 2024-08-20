package spells.stagespells;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

import java.util.HashMap;

public class PullRanke extends Spell {

    HashMap<Integer, Vector> path = new HashMap<Integer, Vector>();
    HashMap<Integer, FallingBlock> blocks = new HashMap<Integer, FallingBlock>();
    Entity victim;
    int backstep;

    public PullRanke(Player caster, Entity p, HashMap<Integer, Vector> path, HashMap<Integer, FallingBlock> blocks, Location loc, int setstep, String namae) {
        backstep = setstep;
        this.loc = loc;
        this.path = path;
        this.blocks = blocks;
        victim = p;
        name = namae;


        hitboxSize = 2;
        powerlevel = 20;
        speed = 3;

        multihit = false;
        this.caster = caster;
        cooldown = 0;

        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.PROJECTILE);
        castSpell(caster, namae);
    }


    @Override
    public void cast() {

    }

    @Override
    public void setUp() {
        //loc = caster.getEyeLocation();
        if (path.size() < 5) {
            finalVec = caster.getLocation().toVector().subtract(loc.toVector());
            dead = true;
        } else {
            finalVec = path.get(1).clone().subtract(path.get(4).clone()).normalize();
        }


    }

    Vector finalVec;

    @Override
    public void move() {
        if (victim instanceof Player) {
            Player ent = (Player) victim;
            if (ent.getGameMode() != GameMode.SURVIVAL) {
                dead = true;
                return;
            }
            if (!isOnTeam(ent)) {
                ent.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 6));
            }

        }
        if (backstep > 1) {
            playSound(Sound.ENTITY_LEASH_KNOT_BREAK, loc, 5, 1);

            //blocks.get(backstep-7).setVelocity(victim.getVelocity());

            if (blocks.get(backstep) != null) {
                blocks.get(backstep).remove();
            }

            Vector vd = path.get(backstep);
            loc = new Location(caster.getWorld(), vd.getX(), vd.getY(), vd.getZ());
            if (victim != null) {
                doPin(victim, loc.clone());


            }
            backstep--;
        } else {
            if (victim != null)
                victim.setVelocity(finalVec.multiply(1.7));
            //GlobalUtils.doPull(victim,caster.getLocation(), 1.2);

            for (FallingBlock fb : blocks.values()) {
                if (fb != null) {
                    fb.remove();
                }
            }
            dead = true;
        }

        if (victim instanceof Player) {
            Player p = (Player) victim;
            if (p.getGameMode() == GameMode.ADVENTURE) {
                dead = true;
            }

        }
    }


    @Override
    public void launch() {
        // TODO Auto-generated method stub

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

    }
}
