package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Repulsion;

public class Schockwelle extends Spell {

	Player target;
	public Schockwelle() {
		
		name = "§rSchockwelle";
		cooldown = 20*30;
		hitPlayer = true;
		hitEntity = true;
		hitboxSize = 6;
		casttime = 20;
		steprange =22;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.SUPPORT);
		
	}
	
	
	
	@Override
	public void setUp() {
	
		target = Bukkit.getPlayer(NBTUtils.getNBT("Archon", caster.getInventory().getItemInMainHand()));
		if (target == null) {
			dead = true;
		}
		// TODO Auto-generated method stub
		//ParUtils.createParticle(Particle.FLASH,target.getLocation(), 0, 0, 0, 1, 1);
		//ParUtils.createParticle(Particle.END_ROD,target.getLocation(), 0, 0, 0, 222, 10);
		//ParUtils.createParticle(Particle.ENCHANT,target.getLocation(), 0, 0, 0, 102, 10);
		SoundUtils.playSound(Sound.ENTITY_WITHER_AMBIENT, loc,0.3F,30F);
		noTargetEntitys.add(target);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		ParUtils.chargeDot(target.getLocation(), Particle.END_ROD, 0.2F, 10, 10);
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		SoundUtils.playSound(Sound.ENTITY_WITHER_SPAWN, loc,2F,0.1F);
		
		loc = target.getLocation();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		double t = step*6;
		for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
			double x = t * Math.cos(theta);
			double y = 0;
			double z = t * Math.sin(theta);
			loc.add(x, y, z);
			int minus = 0;
			while (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
				loc.add(0, -1, 0);
				minus++;
				if (minus >= 256) {
					break;
				}
			}

			// ParticleEffect.FIREWORKS_SPARK.display(loc,0,0,0,0,1);

			
			ParUtils.createParticle(Particle.CLOUD, loc.clone().add(0,1,0), 0, 1, 0, 0,1);
			ParUtils.createParticle(Particle.FLASH, loc.clone().add(0,1,0), 1, 2,1, 1, 0);
			
			collideWithEntity();
			collideWithPlayer();
			loc.subtract(x, y, z);
			loc.add(0, minus, 0);
			theta = theta + Math.PI / 64;

		}
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		double distance = p.getLocation().distance(target.getLocation());
		doKnockback(p, target.getLocation(), 5*(distance/hitboxSize));
		damage(p, 3, target);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		double distance = ent.getLocation().distance(target.getLocation());
		doKnockback(ent, target.getLocation(), 5*(distance/hitboxSize));
		damage(ent, 3, target);
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
	public void effect(double t) {
	
	}
}
