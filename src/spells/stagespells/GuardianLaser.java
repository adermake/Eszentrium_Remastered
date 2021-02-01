package spells.stagespells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class GuardianLaser extends Spell {
	Location startLoc;
	Vector dir;
	public GuardianLaser(Player c,Location l1,Vector dir,boolean refined,String namee,Player target) {
		this.refined = refined;
		caster = c;
		this.name = namee;
		this.dir = dir;
		startLoc = l1.clone();
		speed = 60;
		steprange = 200;
		hitSpell = true;
		if (target != null) {
			this.dir = target.getEyeLocation().toVector().subtract(l1.toVector());
		}
		
		castSpell(caster, name);	
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.PROJECTILE);
		
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = startLoc;
		loc.setDirection(dir);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		ParUtils.createFlyingParticle(Particles.CLOUD, loc, 0, 0, 0, 1, 5, loc.getDirection());
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		loc.add(loc.getDirection().multiply(0.5));
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		if (step > 3) {
			
			ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.AQUA, 1);
		}
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
		if (refined) {
			damage(p,7,caster);
		}
		else {
			damage(p,1,caster);
		}
		
		p.setVelocity(dir.clone().normalize().multiply(3));
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		if (refined) {
			damage(ent,7,caster);
		}
		else {
			damage(ent,1,caster);
		}
		
		ent.setVelocity(dir.clone().normalize().multiply(3));
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
		loc.add(0,1,0);
		
		@SuppressWarnings("deprecation")
		
		MaterialData md = new MaterialData(loc.getBlock().getRelative(BlockFace.DOWN).getType(), loc.getBlock().getRelative(BlockFace.DOWN).getData());
		if (loc.getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.2));
		
		
		}
		
		if (loc.add(0,0,1).getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.2));
			f.setDropItem(false);
		
		}
		if (loc.add(0,0,-1).getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.2));
			f.setDropItem(false);
		
		}
		if (loc.add(-1,0,0).getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.2));
			f.setDropItem(false);
		
		}
		if (loc.add(1,0,0).getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.2));
			f.setDropItem(false);
		
		}
		loc.add(0,-1,0);
		
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.EXPLOSION, loc, 0, 0, 0, 1, 1);
		playSound(Sound.ENTITY_DOLPHIN_JUMP,loc,8,1);
		ParUtils.createRedstoneParticle(loc.clone().add(loc.getDirection().multiply(-1)), 0, 0, 0, 1, Color.AQUA, 5);
		Location dir = loc.clone();
		dir.setPitch(dir.getPitch()-90);
		ParUtils.createFlyingParticle(Particles.CLOUD, loc.clone().add(loc.getDirection().multiply(-1)), 0, 0, 0, 1, 1, dir.getDirection());
		
		Explosion e = new Explosion(3,  3, 1,1, caster, loc.clone(), name);
		
		for (Entity ent : noTargetEntitys) {
			e.addNoTarget(ent);
		}
			
			dead = true;
		
		
		
	}
	
	public void addNoTarget(Entity ent) {
		noTargetEntitys.add(ent);
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
		
	}

}
