package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.spells.Todessaege;

public class Sythe extends Spell {

	Vector vOri;
	Vector vdir;
	Location ori;
	ArmorStand sythe;
	ArmorStand sythe2;
	float offset;
	Todessaege boss;
	public Sythe(String name,Player p,Location l,Vector v,float offset,Todessaege boss,boolean refined) {
		this.offset = offset;
		this.name = name;
		this.refined = refined;
		this.boss = boss;
		vdir = v;
		ori = l.clone();
		vOri = vdir.clone();
		multihit = true;
		hitboxSize = 1;
		castSpell(p, name);
		speed = 3;	
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.SUPPORT);
		addSpellType(SpellType.PROJECTILE);
	
	}
	
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		float sign = (float) Math.signum(vdir.getY());
		loc = ori;
		sythe = createArmorStand(loc.clone().add(0,-1,0).add(caster.getLocation().getDirection().multiply(-0.5)));
		sythe.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_HOE));
		sythe.setRightArmPose(new EulerAngle(offset, -vdir.dot(new Vector(0,1,0))+Math.PI , 80));
		sythe2 = createArmorStand(loc.clone().add(0,-1,0).add(caster.getLocation().getDirection().multiply(-0.5)));
		sythe2.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_HOE));
		sythe2.setRightArmPose(sythe.getRightArmPose().add(Math.PI, 0,0));
		sythe.setSmall(true);
		sythe2.setSmall(true);
		
	
		sythe2.setGravity(true);
		sythe.setGravity(true);
		vdir.normalize().multiply(0.5F);
		speed = 4;
		steprange = (int) (20 * speed * 2);
		
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		sythe.teleport(caster.getLocation());
		sythe2.teleport(caster.getLocation());
		
	}
	
	boolean flyBack = false;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (speed <= 1 && step >= steprange-5) {
			flyBack  = true;
			speed =3;
			steprange = (int) (20*6*speed);
			step = 0;
			boss.clearHitBlacklist();
			clearHitBlacklist();
			playSound(Sound.BLOCK_GRINDSTONE_USE,loc,3F,1F);
			
		}
		if (flyBack) {
			multihit = false;
		
			vdir =caster.getEyeLocation().toVector().subtract( loc.toVector()).normalize().multiply(0.5);
			if (caster.getEyeLocation().distance(loc)<1) {
				kill();
				
			}
			ParUtils.createFlyingParticle(Particle.SMOKE_NORMAL, loc.clone().add(0,1,0), 0, 0, 0, 1, 0.5, caster.getLocation().toVector().subtract(loc.clone().add(0,1,0).toVector()).normalize());
			
		}else {
			if (speed <= 1)  {
				speed = 1;
				vdir = new Vector(0,-0.2,0);
				if (caster.isSneaking()) {
					flyBack  = true;
					speed =3;
					steprange = (int) (20*6*speed);
					step = 0;
					boss.clearHitBlacklist();
					clearHitBlacklist();
					playSound(Sound.BLOCK_GRINDSTONE_USE,loc,3F,1F);
					
				}
			
			}
			else {
				
			
				//sythe.teleport(loc);
				//sythe2.teleport(loc);
				speed = speed * 0.95;
			}
		}
		
		
		loc.add(vdir);
		
		
		
		//sythe.teleport(loc.clone());
		//sythe2.teleport(loc.clone());
		doPin(sythe,loc.clone(),speed+2);
		doPin(sythe2,loc.clone(),speed+2);
		/*
		if (flyBack) {
			doPin(sythe,caster.getLocation(),speed+2);
			doPin(sythe2,caster.getLocation(),speed+2);
			loc = sythe.getLocation();
		}
		else {
			doPin(sythe,loc.clone().add(0,-1,0),speed+2);
			doPin(sythe2,loc.clone().add(0,-1,0),speed+2);
		}
	*/	

		
	
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		//ParUtils.createParticle(Particle.DRAGON_BREATH, loc, 0, 0, 0, 1, 0);
		if (speed <= 1)  {
			ParUtils.createParticle(Particle.ASH, loc.clone().add(0,0.9,0), 0.2, 0.2, 0.2 ,1, 1);
		}
		
		sythe.setRightArmPose(sythe.getRightArmPose().add(0.09, 0, 0));
		sythe2.setRightArmPose(sythe2.getRightArmPose().add(0.09, 0, 0));
		
		
	}

	@Override
	public void onPlayerHit(Player p) {
		playSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP,loc,2,1);
		// TODO Auto-generated method stub
		if (flyBack) {
			if (boss.hitEntitys.contains(p)) {
				return;
			}
			playSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP,loc,2,1);
			playSound(Sound.BLOCK_ANVIL_PLACE,loc,0.2,2);
			Vector v =caster.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(1);
			bloodLine( p.getLocation(), v);
			bloodLine( p.getLocation(), v);
			bloodLine( p.getLocation(), v);
			bloodLine( p.getLocation(), v);
			p.setVelocity(caster.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(1));
			damage(p, 7,caster);
			heal(caster, 5, caster);
		}
		else {
			playSound(Sound.BLOCK_LANTERN_BREAK,loc,2,1);
			
			if (boss.getHitCount(p)<10) {
				if (refined) {
					damage(p, 5,caster);
				}
				else {
					damage(p, 3,caster);
				}
				
			}
		
			
			boss.addHitCount(p);
			if (speed <= 1) {
				p.setVelocity(vOri.normalize());
			}
			else {
				if (refined) {
					p.setVelocity(vdir.clone().normalize().multiply(5));
				}else {
					p.setVelocity(vdir.clone().normalize().multiply(3));
				}
				
			}
			
		}
		boss.hitEntitys.add(p);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		
		// TODO Auto-generated method stub
		if (flyBack) {
			
			if (boss.hitEntitys.contains(ent)) {
				return;
			}
			playSound(Sound.BLOCK_ANVIL_PLACE,loc,0.2,2);
			playSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP,loc,2,1);
			damage(ent, 7,caster);
			
			Vector v =caster.getLocation().toVector().subtract(ent.getLocation().toVector()).normalize().multiply(1);
			bloodLine( ent.getLocation(), v);
			bloodLine( ent.getLocation(), v);
			bloodLine( ent.getLocation(), v);
			bloodLine( ent.getLocation(), v);
			
			ent.setVelocity(caster.getLocation().toVector().subtract(ent.getLocation().toVector()).normalize().multiply(1));
			
		}
		else {
			playSound(Sound.BLOCK_LANTERN_BREAK,loc,2,1);
			if (boss.getHitCount(ent)<10) {
				if (refined) {
					damage(ent, 5,caster);
				}
				else {
					damage(ent, 3,caster);
				}
				
			}
				boss.addHitCount(ent);
			if (speed <= 1) {
				ent.setVelocity(vOri.normalize());
			}
			else {
				if (refined) {
					ent.setVelocity(vdir.clone().normalize().multiply(5));
				}else {
					ent.setVelocity(vdir.clone().normalize().multiply(3));
				}
				
				
			}
			
			
		}
		
		boss.hitEntitys.add(ent);
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
		
		if (speed <= 1) {
			loc.add(vdir.clone().multiply(-1));
			speed = 1;
		}
		else {
			loc.add(vdir.clone().multiply(-0.5));
		}
	
		if (step % 2 == 0)
			playSound(Sound.BLOCK_ANCIENT_DEBRIS_BREAK,loc,0.2,1);
		
	
	}
	public void bloodLine(Location locC,Vector dir) {
		Location loc = locC.clone();
		dir = randVector().multiply(0.5).add(dir);
		dir = dir.multiply(5);
		
		loc.add(dir);
		
		ParUtils.parLineRedstoneSpike(locC, loc, Color.RED, 0.2);
		
		
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		sythe.remove();
		sythe2.remove();
	}

}
