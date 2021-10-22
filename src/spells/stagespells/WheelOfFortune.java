package spells.stagespells;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class WheelOfFortune extends Spell {

	
	String[] a;
	public WheelOfFortune(Player c,String[] argds) {
		// TODO Auto-generated constructor stub
		a = argds;
		steprange = 200;
		castSpell(c, "§eWheel of Fortune");
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		speed = 4;
		playGlobalSound(Sound.BLOCK_LANTERN_HIT, 1, 2);
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
		loc.setDirection(caster.getLocation().getDirection());
		loc.add(loc.getDirection().multiply(0.5F));
		if (caster.isSneaking()) {
			dead = true;
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particle.END_ROD, loc, 0.1F, 0.1F, 0.1F, 2, 0.1F);
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
		new WheelOfFortuneWheel(caster, a,loc.clone());
	}

	
	
	
}
