package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import spells.spellcore.SpellType;

public class VelocityTimeStop extends Spell {
	
	LivingEntity target;
	
	public VelocityTimeStop(LivingEntity target,Player caster,String name) {
		
		this.caster = caster;
		this.name = name;	
		this.target = target;
		
		castSpell(caster, name);
		
		speed = 1;
		steprange = 20 * 6;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.PROJECTILE);
		
		
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
		target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,20*6,200,true)); 
		playSound(Sound.ENTITY_RAVAGER_ATTACK,target.getEyeLocation(),2,1);
		SilenceSelection s = new SilenceSelection();
		if (target instanceof Player) {
			Player t = (Player) target;
			silence(t, s);
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
	int delay = 0;
	Vector v = new Vector(0,0,0);
	Vector lastPin = new Vector(0,0,0);
	double speeder = 0;
	float pitch = 0.5F;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		delay++;
		if (delay > 20-speeder) {
			delay = 0;
			pitch+=0.1F;
			speeder+=2.05;
			playSound(Sound.BLOCK_COMPARATOR_CLICK,target.getEyeLocation(),2,pitch);
			
		}
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
		
		Location l1 = ParUtils.stepCalcCircle(target.getLocation(), 1, new Vector(0,1,0),0, step*2);
		ParUtils.dropItemEffectVector(l1, Material.CLOCK, 1, 0, 1, new Vector(0,0,0));
		Location l2 = ParUtils.stepCalcCircle(target.getLocation(), 1, new Vector(0,1,0),0, 22+step*2);
		ParUtils.dropItemEffectVector(l2, Material.CLOCK, 1, 0, 1, new Vector(0,0,0));
		
		if (delay == 0) {
			ParUtils.createFlyingParticle(Particles.TOTEM_OF_UNDYING, l1, 0, 0, 0, 1, 1, target.getLocation().toVector().subtract(l1.toVector()).multiply(-1));
			ParUtils.createFlyingParticle(Particles.TOTEM_OF_UNDYING, l2, 0, 0, 0, 1, 1, target.getLocation().toVector().subtract(l2.toVector()).multiply(-1));
		}
		//ParUtils.createParticle(Particles.TOTEM_OF_UNDYING, l1, 0, 0, 0, 0, 0);
		//ParUtils.createParticle(Particles.TOTEM_OF_UNDYING, l2, 0, 0, 0, 0, 0);
		
		ParUtils.createParticle(Particles.SMOKE, target.getLocation(), 0.1, 0.3, 0.1, 1, 0.1F);
		
		//ParUtils.createFlyingParticle(Particles.END_ROD,target.getLocation(), 0.6F, 0.6, 0.6F, 1, 0.6F, new Vector(0,1,0));
		
		Vector d =  v.clone().multiply(vfactor);
		
		Color c = null;
		double rgb = d.length()*7;
	
		if (rgb > 510) {
			rgb = 510;
		}
		rgb = 510- rgb;
		if (rgb > 255) {
			c= Color.fromBGR(0, 255, 510-(int)rgb);
		}
		else {
			c =  Color.fromBGR(0, (int)rgb, 255);
		}
		
		ParUtils.createRedstoneParticle(target.getLocation(), 0.5f, 1, 0.5F, 1, c, 1.5F);
	
		
		Vector dv = v.clone().multiply(vfactor);
		if (dv.length() > 30) {
			dv = dv.normalize().multiply(30);
		}
		ParUtils.parLineRedstone(target.getLocation(),target.getLocation().add(dv) ,c , 0.5F, 0.2F);
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
	double vfactor = 1.2F;
	@Override
	public void onDeath() {
		silenced.remove(target);
		double sound = v.length();
		
		playSound(Sound.ENTITY_WITHER_SHOOT,target.getEyeLocation(),2*sound/30,2-1.9F*sound/30);
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
