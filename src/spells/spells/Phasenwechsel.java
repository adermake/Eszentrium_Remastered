package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Phasenwechsel extends Spell {

	Player target;
	public Phasenwechsel() {
		
		name = "§rPhasenwechsel";
		cooldown = 20*30;
		hitPlayer = false;
		hitEntity = false;
		hitboxSize = 5;
		
		addSpellType(SpellType.SUPPORT);
		addSpellType(SpellType.MOBILITY);
		
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
		SoundUtils.playSound(Sound.ENTITY_WITHER_HURT, loc,0.3F,30F);
		noTargetEntitys.add(target);
		loc = target.getLocation();
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	boolean charging = true;
	@Override
	public void move() {
		if (charging) {
			Vector dir = caster.getLocation().toVector().subtract(loc.toVector()).normalize();
			
			ParUtils.createParticle(Particle.END_ROD, loc, 0, 0, 0, 3, 0);
			ParUtils.createFlyingParticle(Particle.CLOUD, loc, 0, 0, 0, 1, 0.2F, dir);
			ParUtils.createParticle(Particle.ENCHANTMENT_TABLE, loc, 0.1, 0.1, 0.1, 5, 5);
			loc.add(dir.multiply(2F));
		}
	
		
		if (loc.distance(caster.getLocation())<2 && charging) {
			//ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 1, 1);
			ParUtils.createParticle(Particle.FLASH, target.getLocation(), 0,0, 0, 1, 1);
			ParUtils.createParticle(Particle.FLASH, caster.getLocation(), 0,0, 0, 1, 1);
			SoundUtils.playSound(Sound.ENTITY_SHULKER_TELEPORT, loc, 1.4F, 10);
			target.teleport(caster.getLocation());
			dead = true;
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
	
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
	
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
	
	
	/*
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		target = Bukkit.getPlayer(NBTUtils.getNBT("Archon", caster.getInventory().getItemInMainHand()));
		if (target == null) {
			dead = true;
		}
		SoundUtils.playSound(Sound.ENTITY_SHULKER_TELEPORT, loc, 0.3F, 10);
		SoundUtils.playSound(Sound.BLOCK_CONDUIT_AMBIENT, loc, 2F, 10);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
		ParUtils.parLineRedstone(caster.getLocation(), target.getLocation(), Color.WHITE, 1, 2);
	
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particle.FLASH, target.getLocation(), 0,0, 0, 1, 1);
		ParUtils.createParticle(Particle.FLASH, caster.getLocation(), 0,0, 0, 1, 1);
		SoundUtils.playSound(Sound.ENTITY_SHULKER_TELEPORT, loc, 1.4F, 10);
		target.teleport(caster.getLocation());
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
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
*/
}
