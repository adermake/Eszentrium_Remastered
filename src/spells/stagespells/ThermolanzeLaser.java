package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
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
import spells.spellcore.SpellType;

public class ThermolanzeLaser extends Spell{

	Vector dir;
	public ThermolanzeLaser(Player c,Vector dir,boolean refined) {
		this.refined = refined;
		caster = c;
		name ="§eThermolanze";
		this.dir = dir;
		speed = 60;
		steprange = 200;
		hitSpell = true;
		castSpell(caster, name);	
		

		addSpellType(SpellType.PROJECTILE);
		
		
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc.setDirection(dir);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
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
			ParUtils.createFlyingParticle(Particles.FLAME, loc, 0, 0, 0, 1, 5, loc.getDirection());
			ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.ORANGE, 1);
		}
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p,2,caster);
		p.setFireTicks(8);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent,2,caster);
		ent.setFireTicks(8);
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
		if (refined) {
			new Explosion(6, 4, 1, 1, caster, loc, name);
			new Repulsion(6, 8, caster, loc, name);
		}
		else {
			new Explosion(4, 4, 1, 1, caster, loc, name);
			new Repulsion(4, 4, caster, loc, name);
		}
		
		
		ParUtils.createRedstoneParticle(loc.clone().add(loc.getDirection().multiply(-1)), 0, 0, 0, 1, Color.ORANGE, 5);
		Location dir = loc.clone();
		dir.setPitch(dir.getPitch()-90);
		ParUtils.createFlyingParticle(Particles.CAMPFIRE_COSY_SMOKE, loc.clone().add(loc.getDirection().multiply(-1)), 0, 0, 0, 1, 1, dir.getDirection());
		
		
			FallingBlock fb = loc.clone().add(loc.getDirection().multiply(-1)).getWorld().spawnFallingBlock(loc.clone().add(loc.getDirection().multiply(-1)), Material.FIRE, (byte)0);
			fb.setVelocity(dir.getDirection().multiply(0.7));
			dead = true;
		
		
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}
	

}
