package spells.spells;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class BindenOld extends Spell {

	
	public BindenOld() {
		name = "§eBinden";
		//hitEntity = true;
		cooldown = 20*24;
		steprange = 200;
		speed = 1;
		//hitboxSize = 2;
		//
	}
	Entity ent1;
	Entity ent2;
	
	
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		ent1 = pointRealEntity(caster);
		
		if (ent1 == null) {
			refund = true;
			dead = true;
		}
		else {
			SoundUtils.playSound(Sound.ENTITY_SLIME_DEATH, loc);
			SoundUtils.playSound(Sound.ENTITY_SLIME_DEATH, ent1.getLocation());
			
			if (refined && caster.isSneaking()) {
				ent2 = caster;
				standBy = false;
			}
		}
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	int ticker = 0;
	boolean standBy = true;
	@Override
	public void move() {
		if (dead)
			return;
		// TODO Auto-generated method stub
		
		if (ent1 != null) {
			if (ent1 instanceof Player) {
				if (((Player)ent1).getGameMode() == GameMode.ADVENTURE)
				dead = true;
			}
			
		}
		
		if (ent2 != null ) {
			if (ent2 instanceof Player) {
				
			
			if (((Player)ent2).getGameMode() == GameMode.ADVENTURE)
				dead = true;
			}
		}
		
		if (swap() && standBy) {
			ent2 = pointRealEntity(caster);
			if (ent2 != null) {
				standBy = false;
				steprange = 100;
				step = 0;
				SoundUtils.playSound(Sound.ENTITY_SLIME_DEATH, loc);
				SoundUtils.playSound(Sound.ENTITY_SLIME_DEATH, ent2.getLocation());
			}
			
		}
		
		if (!standBy) {
			if (ent1 != ent2) {
				if (ent1 instanceof Player) {
					Player e = (Player) ent1;
					e.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 1, 10));
				}
				if (ent2 instanceof Player) {
					Player e = (Player) ent2;
					e.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 1, 10));
				}
			}
			
			ticker++;
			if (ent1 == ent2) {
				ent1.setVelocity(new Vector(0,0,0));
			}
			SoundUtils.playSound(Sound.ENTITY_SLIME_HURT, ent1.getLocation());
			if (ticker > 0) {
				doPull(ent1,ent2.getLocation(),1);
				doPull(ent2,ent1.getLocation(),1);
				ticker = 0;
			}
			
			ParUtils.parLineRedstone(ent2.getLocation(), ent1.getLocation(), Color.LIME, 1, 1);
		}
		else {
			double speed = 2;
			loc = caster.getLocation();
			ParUtils.parLineRedstone(caster.getLocation(), ent1.getLocation(), Color.LIME, 1, 1);
			ParUtils.createParticle(Particle.SLIME,ParUtils.stepCalcCircle(loc.clone(), 3, caster.getLocation().getDirection(), 0, step*speed), 0, 0, 0, 1, 1);
			ParUtils.createParticle(Particle.SLIME,ParUtils.stepCalcCircle(loc.clone(), 3,caster.getLocation().getDirection(), 0, -step*speed), 0, 0, 0, 1, 1);
		}
		
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
