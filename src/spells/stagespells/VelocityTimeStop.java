package spells.stagespells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.SilenceSelection;
import spells.spellcore.Spell;

public class VelocityTimeStop extends Spell {
	
	LivingEntity target;
	
	public VelocityTimeStop(LivingEntity target,Player caster,String name) {
		
		this.caster = caster;
		this.name = name;	
		this.target = target;
		
		castSpell(caster, name);
		
		speed = 1;
		steprange = 20 * 6;
		
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = target.getLocation();
		
		if (target instanceof Player) {
			silence((Player) target, new SilenceSelection());
		}
		//target.setNoDamageTicks(20*6);
		target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*6,200,true)); 
			

	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	Vector v = new Vector(0,0,0);
	Vector lastPin = new Vector(0,0,0);
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		v.add(target.getVelocity().add(new Vector(0,0.079,0).subtract(lastPin)));
		if (target.getLocation().distance(loc.clone())>2) {
			target.teleport(loc.setDirection(target.getLocation().getDirection()));
		}
		lastPin = doPull(target,loc,2F);
		if (target.getLocation().distance(loc.clone())<6) {
			lastPin = doPin(target,loc,1F);
		}
		//target.setVelocity(new Vector(0,0,0));
		
		
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.parLine(Particles.BUBBLE, target.getLocation(), target.getLocation().add(v.clone().multiply(vfactor)), 0, 0, 0, 1, 1, 0.1);
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
	double vfactor = 2;
	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		v = v.multiply(vfactor);
		Location pinLoc = loc.add(v);
		double pullStr = v.length();
		if (pullStr > 15)
			pullStr = 15;
		final double ps = pullStr/3;
		new BukkitRunnable() {
			int l = 60;
			public void run() {
				
				if (l<0) {
					this.cancel();
				}
				l--;
				if (target.getLocation().distance(pinLoc)>4) {
					doPull(target,pinLoc,ps);
				}
				else {
					this.cancel();
				}
				
			}
		}.runTaskTimer(main.plugin,1,1);
		
		silenced.remove(target);
	}

}
