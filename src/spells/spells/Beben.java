package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class Beben extends Spell{

	public Beben() {
		
		name = "§eBeben";
		steprange = 42;
		cooldown = 20*30;
		hitEntity = true;
		hitPlayer = true;
		hitSpell = true;
		hitBlock = false;
		hitboxSize = 3;
		casttime = 20;
		speed = 4;
		
		
		
	}
	
	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

	double height = 0;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		height = caster.getLocation().getY();
		caster.setVelocity(caster.getVelocity().add(new Vector(0,-6,0)));
		ParUtils.createFlyingParticle(Particles.CLOUD, caster.getLocation(), 0, 2, 0, 10, 1, new Vector(0,-1,0));
		ParUtils.parKreisDot(Particles.CLOUD, caster.getLocation(), 2, 0, 2, new Vector(0,1,0));
		playSound(Sound.ENTITY_MOOSHROOM_CONVERT,caster.getLocation(),15,2);
	}

	@Override
	public void cast() {
		if (caster.isOnGround() == true) {
			height = height-caster.getLocation().getY();
			if (height < 2)
				height = 2;
			
			cast = casttime;
			if (refined)
			steprange= steprange*2;
		}
		else {
			cast = 0;
			steprange++;
		}
		
		
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		loc = caster.getLocation();
		direction = caster.getLocation().getDirection();
	}
	Vector direction; 
	@Override
	public void move() {
		
		double x = direction.getX() ;
		double y = 0;
		double z = direction.getZ() ;
		loc.add(x, y, z);
		int minus = 0;
		while (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
			loc.add(0, -1, 0);
			minus++;
			if (minus >= 256) {
				break;
			}
		}
		int plus = 0;
		while (loc.getBlock().getType() != Material.AIR) {
			loc.add(0, 1, 0);
			plus++;
			if (plus >= 14) {
				break;
			}
		}
		playSound(Sound.ENTITY_GENERIC_EXPLODE,loc, 1, (float) 0.1);
		@SuppressWarnings("deprecation")
		MaterialData md = new MaterialData(loc.getBlock().getRelative(BlockFace.DOWN).getType(), loc.getBlock().getRelative(BlockFace.DOWN).getData());
		if (loc.getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.7));
		
		
		}
		if (loc.add(0,0,1).getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.7));
			f.setDropItem(false);
		
		}
		if (loc.add(0,0,-1).getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.7));
			f.setDropItem(false);
		
		}
		if (loc.add(-1,0,0).getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.7));
			f.setDropItem(false);
		
		}
		if (loc.add(1,0,0).getBlock().getType() == Material.AIR) {
			FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
			f.setVelocity(f.getVelocity().setY(0.7));
			f.setDropItem(false);
		
		}
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		damage(p,6,caster);
		double h = 1;
		if (refined) {
			h += height/2;
		}
		else {
			h += height/4;
		}
		if (h > 10)
			h = 10;
		
		Vector dir = direction.clone().normalize();
		dir = dir.setY(0);
		dir = dir.normalize();
		p.setVelocity(dir.multiply(h).add(new Vector(0,1,0)));
		//p.setVelocity(p.getVelocity().setY(1.0D));
	
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		damage(ent,6,caster);
		double h = 1;
		if (refined) {
			h += height/2;
		}
		else {
			h += height/4;
		}
		if (h > 10)
			h = 10;
		
		Vector dir = direction.clone();
		dir = dir.setY(0);
		dir = dir.normalize();
		ent.setVelocity(dir.multiply(h).add(new Vector(0,1,0)));
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
	}

	

}
