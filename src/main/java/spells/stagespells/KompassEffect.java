package spells.stagespells;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.ScoreboardTeamUtils;
import spells.spellcore.SilenceSelection;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class KompassEffect extends Spell {
	
	public  ArrayList<Entity> circle = new ArrayList<Entity>();
	
	LivingEntity target;
	public static ArrayList<Entity> hit = new ArrayList<Entity>();
	public KompassEffect(LivingEntity target,Player caster,String name) {
		
		this.caster = caster;
		this.name = name;	
		this.target = target;
	
		castSpell(caster, name);
		
		speed = 1;
		steprange = 20 * 6;
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.KNOCKBACK);

		
		
	}
	
	@Override
	public void setUp() {
		
		// TODO Auto-generated method stub

		loc = target.getLocation();
		loc.setDirection(caster.getLocation().getDirection());
		playSound(Sound.ITEM_TRIDENT_THUNDER,target.getEyeLocation(),2,1);

		for (int i = 0;i<16;i++) {
			ArmorStand ar = createArmorStand(loc.clone());
			ar.getEquipment().setHelmet(new ItemStack(Material.BASALT));;
			ar.setSmall(true);
			ar.setGravity(true);
			
			disableEntityHitbox(ar);
			circle.add(ar);
		}
		hit.add(target);

	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	boolean lock = false;
	int delay = 0;
	Vector v = new Vector(0,0,0);
	Vector lastPin = new Vector(0,0,0);
	double speeder = 0;
	float pitch = 0.5F;
	@Override
	public void move() {
		
		// TODO Auto-generated method stub
		delay++;
		if (delay >3) {
		
			playSound(Sound.ENTITY_BEE_DEATH,target.getEyeLocation(),0.2F,2*(getLerp()*getLerp()));
		}
		if(swap() && !lock) {
			lock = true;
			playSound(Sound.BLOCK_CHEST_LOCKED,loc,2,0.2F);
			playSound(Sound.BLOCK_CHEST_LOCKED,loc,2,0.4F);
			playSound(Sound.BLOCK_CHEST_LOCKED,loc,2,0.8F);
			for (Entity carrier : circle) {	
				ParUtils.createFlyingParticle(Particle.LARGE_SMOKE, carrier.getLocation().add(0,1,0),0.2, 0.2, 0.2, 6, 0.1, carrier.getLocation().add(0,1,0).toVector().subtract(loc.toVector()).normalize());
				ParUtils.createFlyingParticle(Particle.SMOKE, carrier.getLocation().add(0,1,0),0.1, 0.1, 0.1, 6, 0.2, carrier.getLocation().add(0,1,0).toVector().subtract(loc.toVector()).normalize());
				
			}
		}
	
		//target.setVelocity(new Vector(0,0,0));
		
		if (!lock) {
			loc = target.getLocation();
			loc.setDirection(caster.getLocation().getDirection());
		}
		else {
			Vector save = loc.getDirection();
			loc = target.getLocation();
			loc.setDirection(save);
		}
	
	}

	boolean skip = false;
	@Override
	public void display() {
		
		double s = 0;
		double cc = circle.size();
		for (Entity carrier : circle) {	
			Location l = ParUtils.stepCalcCircle(target.getLocation(), 4, loc.getDirection(), 0, -step*1f*getLerp() +s*(44/cc));
			doPin((org.bukkit.entity.Entity) carrier,l);
			//ParUtils.createRedstoneParticle(l, 0, 0, 0, 1, Color., size);
			ParUtils.createDustTransition(carrier.getLocation().add(0,1,0), 0, 0, 0, 1, Color.BLACK, Color.RED, 0.8F);
			s++;
			((ArmorStand)carrier).getEquipment().setHelmet(new ItemStack(Material.BASALT),false);
		}
		if (circle.size() > 0) {
			Entity a = circle.get((int) ((step/2+5)% (circle.size()-1)));
			((ArmorStand)a).getEquipment().setHelmet(new ItemStack(Material.REDSTONE_BLOCK),false);
			
		}
		
		if (target instanceof Player) 
		{
			if (((Player)target).getGameMode()== GameMode.ADVENTURE) {
				dead = true;
				skip = true;
			}
		}
		
		ParUtils.parLineRedstone( loc.clone().add(0,1,0), loc.clone().add(0,1,0).add(loc.getDirection().multiply(3)),Color.RED, 0.7F, 0.2);
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
		if (skip)
			return;
		
		damage(target,3,caster);
		for (Entity ent : circle) {
			ent.remove();
		}
		circle.clear();
	
		//ParUtils.parLineFly(Particle.END_ROD, target.getLocation(), target.getLocation().add(loc.getDirection().multiply(10)), speeder, 0.002, loc.getDirection().multiply(1));
		target.setVelocity(loc.getDirection().multiply(4));
		
		
		Vector v =  loc.getDirection().multiply(60);
	
		
		playSound(Sound.BLOCK_BELL_RESONATE,target.getLocation(),3F,2F);
		playSound(Sound.BLOCK_END_PORTAL_SPAWN,target.getLocation(),3F,0.5F);
		// TODO Auto-generated method stub
	
		Location pinLoc = loc.clone().add(v);

		ParUtils.parLine(Particle.FLASH, pinLoc.clone(), target.getLocation(), 0, 0, 0, 1, 0, 1);
	
		new BukkitRunnable() {
			int l = 25;
			public void run() {
				
				if (target instanceof Player) 
				{
					if (((Player)target).getGameMode()== GameMode.ADVENTURE) {
						this.cancel();
						return;
					}
				}
				if (l<0) {
					
					target.setVelocity(new Vector(0,0,0));
					this.cancel();
					return;
				}
				l--;
			
					doPin(target,pinLoc,2);
					pinLoc.add(loc.getDirection().multiply(0.2F));
				
				
			}
		}.runTaskTimer(main.plugin,1,1);
	}
	
	
}
