package spells.spells;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.stagespells.VelocityTimeStop;

public class TimeSpell extends Spell {

	
	public TimeSpell() {
		cooldown = 20;
		speed = 4;
		steprange = 40;
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
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
		loc.add(loc.getDirection());
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.END_ROD, loc, 0, 0, 0, 0, 1);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
		new VelocityTimeStop(p, caster, name);
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		new VelocityTimeStop(ent, caster, name);
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		dead = true;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
