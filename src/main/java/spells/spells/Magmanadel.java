package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Magmanadel extends Spell{
	
	public Magmanadel() {
		name = "§4Magmanadel";
		hitSpell = true;
		steprange = 300;
		speed = 100;
		cooldown = 20*1;
		traitorSpell = true;
		
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	int t = 0;
	int stage = 0;
	@Override
	public void cast() {

		
		
	}

	@Override
	public void move() {
		playSound(Sound.ENTITY_CREEPER_PRIMED,loc,  (float) 1, 1);
		loc.add(loc.getDirection());
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particle.DRIPPING_LAVA, loc, 0, 0, 0, 0, 0);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p, 19,caster);
		p.setFireTicks(30);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		
		damage(ent, 19,caster);
		ent.setFireTicks(30);
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR,loc, 1, 1);
		ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.ORANGE, 1);
		dead = true;
	}

	@Override
	public void launch() {
		
		loc = caster.getEyeLocation();
		//playSound(Sound.ENTITY_ZOMBIE_INFECT,loc, 10, 1);
		
	}

	

}
