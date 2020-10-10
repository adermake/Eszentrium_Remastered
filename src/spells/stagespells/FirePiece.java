package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class FirePiece extends Spell {

	
	Location saveLoc;
	public FirePiece(Location l,Player p,String name,int time) {
		
		
		hitboxSize = 2;
		steprange = time;
		saveLoc = l.clone();
		caster = p;
		this.name = name;
		multihit = true;
		hitPlayer = false;
		hitEntity = false;
		castSpell(caster, name);
	}
	 
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = saveLoc;
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	boolean activated = false;
	@Override
	public void move() {
		if (step == 20) {
			ParUtils.createParticle(Particles.LARGE_SMOKE, loc.clone().add(0,-0.5,0), 0.1F, 0.1F, 0.1F,30, 0.2F);
			activated = true;
			hitPlayer = true;
			hitEntity = true;
		}
		// TODO Auto-generated method stub
		if (!loc.clone().add(0,-1,0).getBlock().getType().isSolid()) {
			loc.add(0,-1,0);
		}
		
		
	}

	@Override
	public void display() {
		
		// TODO Auto-generated method stub
		if (activated) {
			ParUtils.createFlyingParticle(Particles.FLAME, loc, 0.5, 0.5, 0.5, 2, 0.3, new Vector(0,1,0));
		}
		else {
			ParUtils.createFlyingParticle(Particles.FLAME, loc, 0.5, 0.5, 0.5, 2, 0.2, new Vector(0,-1,0));
		}
		
		if (randInt(1, 10) == 2)
		ParUtils.createFlyingParticle(Particles.LAVA, loc, 0.5, 0.5, 0.5, 1, 0.3, new Vector(0,1,0));
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p, 3, caster);
		p.setFireTicks(20);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent, 3, caster);
		ent.setFireTicks(20);
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
