package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Fokusspirale extends Spell {
    
    public Fokusspirale() {
        // TODO Auto-generated constructor stub
        name = "§eFokusspirale";
        cooldown = 20*35;
        steprange = 300;
        speed = 5;
        hitboxSize = 2;
        
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);
    }
    double swap = 1;
    boolean isSneaking = false;
    double damage = 1;
    double steppo = 0;
    int noSpellTime = 0;
    
    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        loc.add(0, 0.5, 0);
        if(refined) {
            steprange = 500;
        }
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        playSound(Sound.ITEM_FIRECHARGE_USE, loc, 5, 2);
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        if (!caster.isSneaking()) {
               loc.add(caster.getLocation().getDirection().multiply(0.5 * swap));
            if (isSneaking) {
                isSneaking = false;
                playSound(Sound.ITEM_FIRECHARGE_USE, loc, 5 + damage, 2.6 - (damage/5));
                noSpellTime = (int) (15*speed);
            }
            noSpellTime--;
        } else {
            if (!isSneaking && noSpellTime<=0) {
                swap = -swap;
                isSneaking = true;
                clearHitBlacklist();
                playSound(Sound.ITEM_FIRECHARGE_USE, loc, 5 + damage, 2 - (damage/5));
                if (damage < 8) {
                    damage += 2;
                }
            } 
            if (noSpellTime>0) {
                noSpellTime--;
                loc.add(caster.getLocation().getDirection().multiply(0.5 * swap));
            }
            step--;
            ParUtils.createFlyingParticle(Particles.FLAME, loc, 0, 0, 0, 1, swap*1.8, caster.getLocation().getDirection().add(randVector().multiply(0.005)));
            ParUtils.createFlyingParticle(Particles.SOUL_FIRE_FLAME, loc, 0, 0, 0, 1, swap*2, caster.getLocation().getDirection().add(randVector().multiply(0.005)));
            ParUtils.createFlyingParticle(Particles.FLAME, loc, 0, 0, 0, 1, swap*2.2, caster.getLocation().getDirection().add(randVector().multiply(0.005)));
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
            if(step%speed==0) {
                if(!refined) {
                    Location l = ParUtils.stepCalcCircle(loc, 1, caster.getLocation().getDirection().multiply(swap), damage/3, steppo);
                    Location l2 = ParUtils.stepCalcCircle(loc, 1, caster.getLocation().getDirection().multiply(swap), damage/3, steppo+22);
                    steppo+=2;
                    ParUtils.createFlyingParticle(Particles.SOUL_FIRE_FLAME, l, damage*0.05, damage*0.05, damage*0.05, (int)damage*2 + 1, 0.2, l.toVector().subtract(loc.toVector()).add(randVector().multiply(1 - damage/10)));
                    ParUtils.createFlyingParticle(Particles.FLAME, l2, damage*0.05, damage*0.05, damage*0.05, (int)damage*2 + 1, 0.2, l2.toVector().subtract(loc.toVector()).add(randVector().multiply(1 - damage/10)));
                } else {
                    Location l = ParUtils.stepCalcCircle(loc, 1, caster.getLocation().getDirection().multiply(swap), damage/3, steppo);
                    Location l2 = ParUtils.stepCalcCircle(loc, 1, caster.getLocation().getDirection().multiply(swap), damage/3, steppo+11);
                    Location l3 = ParUtils.stepCalcCircle(loc, 1, caster.getLocation().getDirection().multiply(swap), damage/3, steppo+22);
                    Location l4 = ParUtils.stepCalcCircle(loc, 1, caster.getLocation().getDirection().multiply(swap), damage/3, steppo+33);
                    steppo++;
                    ParUtils.createFlyingParticle(Particles.SOUL_FIRE_FLAME, l, damage*0.02, damage*0.02, damage*0.02, (int)damage*2 + 1, 0.2, l.toVector().subtract(loc.toVector()).add(randVector().multiply(0.5 - damage/20)));
                    ParUtils.createFlyingParticle(Particles.FLAME, l2, damage*0.02, damage*0.02, damage*0.02, (int)damage*2 + 1, 0.2, l2.toVector().subtract(loc.toVector()).add(randVector().multiply(0.5 - damage/20)));
                    ParUtils.createFlyingParticle(Particles.SOUL_FIRE_FLAME, l3, damage*0.02, damage*0.02, damage*0.02, (int)damage*2 + 1, 0.2, l3.toVector().subtract(loc.toVector()).add(randVector().multiply(0.5 - damage/20)));
                    ParUtils.createFlyingParticle(Particles.FLAME, l4, damage*0.02, damage*0.02, damage*0.02, (int)damage*2 + 1, 0.2, l4.toVector().subtract(loc.toVector()).add(randVector().multiply(0.5 - damage/20)));
                }
            }
    }

    @Override
    public void onPlayerHit(Player p) {
        // TODO Auto-generated method stub
        if(!refined) {
            damage(p, 3 + damage, caster);
            p.setVelocity(caster.getLocation().getDirection().multiply(swap*3));
            kill();
        } else {
            damage(p, 2 + damage/2, caster);
            p.setVelocity(caster.getLocation().getDirection().multiply(swap));
            playSound(Sound.ENTITY_BLAZE_SHOOT, loc, 5 + damage*5, 2 - (damage/4));
        }
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        if(!refined) {
            damage(ent, 3 + damage, caster);
            ent.setVelocity(caster.getLocation().getDirection().multiply(swap*3));
            kill();
        } else {
            damage(ent, 2 + damage/2, caster);
            ent.setVelocity(caster.getLocation().getDirection().multiply(swap));
            playSound(Sound.ENTITY_BLAZE_SHOOT, loc, 5 + damage*5, 2 - (damage/4));
        }
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub
        new spells.stagespells.Explosion(2 + damage/4, damage/2, 1, 2, caster, loc, name);
        kill();
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub
        ParUtils.createParticle(Particles.SOUL_FIRE_FLAME, loc, damage/4, damage/4, damage/4, (int)damage*20, 0.3);
        ParUtils.createParticle(Particles.FLAME, loc, damage/3, damage/3, damage/3, (int)damage*20, 0.4);
        playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, loc, damage*2 - 4, 1.2);
        playSound(Sound.ENTITY_BLAZE_SHOOT, loc, damage*5, 2 - (damage/4));
    }

}