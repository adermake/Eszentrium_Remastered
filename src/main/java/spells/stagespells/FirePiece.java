package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spells.Flammenwand;

public class FirePiece extends Spell {

	Flammenwand wd;
	Location saveLoc;
	public FirePiece(Location l,Player p,String name,int time,boolean refined,Flammenwand fw) {
		this.wd = fw;
		this.refined = refined;
		hitboxSize = 2;
		steprange = time;
		saveLoc = l.clone();
		caster = p;
		this.name = name;
		multihit = true;
		hitPlayer = true;
		hitEntity = true;
		
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
			if (step == 1)
			ParUtils.createParticle(Particle.LARGE_SMOKE, loc.clone().add(0,-0.5,0), 0.1F, 0.1F, 0.1F,30, 0.2F);
			activated = true;
			
		
		// TODO Auto-generated method stub
		if (!loc.clone().add(0,-1,0).getBlock().getType().isSolid()) {
			loc.add(0,-1,0);
		}
		
		
	}

	@Override
	public void display() {
		
		// TODO Auto-generated method stub
			if (step % 3 == 0)
			ParUtils.createFlyingParticle(Particle.FLAME, loc, 0.5, 0.5, 0.5, 1, 0.3, new Vector(0,1,0));
		
		
		if (randInt(1, 100) == 2)
		ParUtils.createFlyingParticle(Particle.LAVA, loc, 0.5, 0.5, 0.5, 1, 0.3, new Vector(0,1,0));
		
	}

	@Override
	public void onPlayerHit(Player p) {
		
		if (isOnTeam(p)) {
			return;
		}
		// TODO Auto-generated method stub
		damage(p, 2, caster);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20*2, 3));
		
		if (!wd.hitEntitys.contains(p)) {
			wd.hitEntitys.add(p);
			doKnockback(p, loc,0.1F);
		}
			//
		
		p.setFireTicks(20);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		//ent.removePotionEffect(PotionEffectType.SLOW);
		ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 10, 3));
		
		if (!wd.hitEntitys.contains(ent)) {
			wd.hitEntitys.add(ent);
			doKnockback(ent, loc,0.1F);
		}
		damage(ent, 2, caster);
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
