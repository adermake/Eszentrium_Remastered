package spells.spells;
//shit
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
        cooldown = 20*40;
        steprange = 300;
        speed = 7;
        hitboxSize = 3;
        
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);
        setLore("§7Schießt eine Feuerspirale in Blickrichtung, die getroffenen Gegnern Schaden zufügt. Nach einiger Zeit ändert das Projektil seine Richtung auf den anvisierten Block. Je länger es bis dahin fliegt, desto stärker ist die Explosion bei Blockkontakt.§eShift: §7Lenkt das Projektil sofort um. Wird die Taste gedrückt gehalten, bleibt das Projektil auf der Stelle.");
        setBetterLore("§7Schießt eine Feuerspirale in Blickrichtung, die getroffenen Gegnern Schaden zufügt. Nach einiger Zeit ändert das Projektil seine Richtung auf den anvisierten Block. Je länger es bis dahin fliegt, desto stärker ist die Explosion bei Blockkontakt.§eShift: §7Lenkt das Projektil sofort um. Wird die Taste gedrückt gehalten, bleibt das Projektil auf der Stelle.");
    }
    boolean isSneaking = false;
    double damage = 1;
    double steppo = 0;
    boolean nextStage = false;
    Location blocLoc;
    
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
        if (!nextStage) {
            if (!caster.isSneaking()) {
                loc.add(loc.getDirection().multiply(0.5));
                if (isSneaking || step>=steprange-10) {
                    playSound(Sound.ITEM_FIRECHARGE_USE, loc, 50, 0.5);
                    blocLoc = block(caster);
                    if (blocLoc == null) {
                        blocLoc = loc(caster, 10);
                    }
                    loc.setDirection(blocLoc.toVector().subtract(loc.toVector()));
                    nextStage = true;
                    step = 0;
                    speed = 5;
                }
            } else {
                step--;
                isSneaking = true;
                blocLoc = block(caster);
                if (blocLoc == null) {
                    blocLoc = loc(caster, 10);
                }
                loc.setDirection(blocLoc.toVector().subtract(loc.toVector()));
                double distance = blocLoc.toVector().subtract(loc.toVector()).length();
                if(!refined) {
                    if (distance > 150) {
                        distance = 150;
                    }
                } else {
                    if (distance > 300) {
                        distance = 300;
                    }
                }
                ParUtils.createFlyingParticle(Particles.FLAME, loc, 0, 0, 0, 1, distance*0.055, loc.getDirection().add(randVector().multiply(0.003)));
                ParUtils.createFlyingParticle(Particles.SOUL_FIRE_FLAME, loc, 0, 0, 0, 1, distance*0.05, loc.getDirection().add(randVector().multiply(0.003)));
                ParUtils.createFlyingParticle(Particles.FLAME, loc, 0, 0, 0, 1, distance*0.045, loc.getDirection().add(randVector().multiply(0.003)));
            }
        } else {
            loc.add(loc.getDirection());
            damage += loc.getDirection().length() * 0.07;
            if(!refined) {
                if (damage > 10) {
                    damage = 10;
                }
            } else {
                if (damage > 15) {
                    damage = 15;
                }
            }
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
            if(step%speed==0) {
                if(!refined) {
                    Location l = ParUtils.stepCalcCircle(loc, 1 + damage/8, caster.getLocation().getDirection(), damage/3, steppo);
                    Location l2 = ParUtils.stepCalcCircle(loc, 1 + damage/8, caster.getLocation().getDirection(), damage/3, steppo+22);
                    steppo+=(2+damage/13);
                    ParUtils.createFlyingParticle(Particles.SOUL_FIRE_FLAME, l, damage*0.05, damage*0.05, damage*0.05, (int)damage*2 + 1, 0.2, l.toVector().subtract(loc.toVector()));
                    ParUtils.createFlyingParticle(Particles.FLAME, l2, damage*0.05, damage*0.05, damage*0.05, (int)damage*2 + 1, 0.2, l2.toVector().subtract(loc.toVector()));
                } else {
                    Location l = ParUtils.stepCalcCircle(loc, 1 + damage/8, caster.getLocation().getDirection(), damage/3, steppo);
                    Location l2 = ParUtils.stepCalcCircle(loc, 1 + damage/8, caster.getLocation().getDirection(), damage/3, steppo+11);
                    Location l3 = ParUtils.stepCalcCircle(loc, 1 + damage/8, caster.getLocation().getDirection(), damage/3, steppo+22);
                    Location l4 = ParUtils.stepCalcCircle(loc, 1 + damage/8, caster.getLocation().getDirection(), damage/3, steppo+33);
                    steppo+=(1.5+damage/18);
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
            damage(p, 2 + damage*0.7, caster);
        } else {
            damage(p, 2 + damage*0.7, caster);
            playSound(Sound.ENTITY_BLAZE_SHOOT, loc, 5 + damage*5, 2 - (damage/4));
        }
        p.setVelocity(caster.getLocation().getDirection());
        kill();
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        if(!refined) {
            damage(ent, 2 + damage*0.7, caster);
        } else {
            damage(ent, 2 + damage*0.7, caster);
            playSound(Sound.ENTITY_BLAZE_SHOOT, loc, 5 + damage*5, 2 - (damage/4));
        }
        ent.setVelocity(caster.getLocation().getDirection());
        kill();
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub
        new spells.stagespells.Explosion(2 + damage/2, 2 + damage*0.7 , 1, 2, caster, loc, name);
        kill();
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub
        ParUtils.createParticle(Particles.SOUL_FIRE_FLAME, loc, damage/2, damage/2, damage/2, (int)damage*30, 0.2);
        ParUtils.createParticle(Particles.FLAME, loc, damage/3, damage/3, damage/3, (int)damage*30, 0.3);
        playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, loc, damage*2 - 4, 1.2);
        playSound(Sound.ENTITY_BLAZE_SHOOT, loc, damage*5, 2 - (damage/4));
    }

}