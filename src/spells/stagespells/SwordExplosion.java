package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class SwordExplosion extends Spell{
	
	Location svloc;
	
	public SwordExplosion(Location loca,Player castguy, String namae) {
		loc = loca;
		svloc = loca;
		steprange = 3;
		caster = castguy;
		hitboxSize = 5;
		speed = 3;
		name = namae;
		castSpell(caster,name);
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.DAMAGE);
		
	}

	@Override
	public void setUp() {
		loc = svloc;
		playSound(Sound.ENTITY_GENERIC_EXPLODE, loc, 10, 1.5F);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		
		
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display() {
		ParUtils.createParticle(Particles.EXPLOSION, loc, 0.5, 0.5, 0.5, 3, 1);
		//ParUtils.createRedstoneParticle(loc, 2, 2, 2, 10, Color.YELLOW, 5);
		
		for (int i = 0;i<=10;i++) {
			//Bukkit.broadcastMessage(""+loc);
			Vector v = randVector().add(loc.getDirection().multiply(2)).normalize().multiply(5);
			if (v.getY()<0)
				v= v.setY(-v.getY());
			
			v.setY(v.getY()/9);
			ParUtils.createFlyingParticle(Particles.CLOUD, loc, 0, 0, 0, 1, 1, v);
			//ParUtils.createFlyingParticle(Particle.END_ROD, loc, 0, 0, 0, 1, 1, randVector().add(loc.getDirection().multiply(-1.2)).normalize());
		}
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		doKnockback(p, loc.add(loc.getDirection().multiply(-2).add(new Vector(0, -0.4, 0))), 1.7);
		damage(p, 6, caster);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		doKnockback(ent, loc.add(loc.getDirection().multiply(-2).add(new Vector(0, -0.4, 0))), 1.7);
		
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

	

}
