package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class BubbleOld extends Spell{
	
	Player target;
	Location overrideLoc;
	public BubbleOld(Location l,Player c,Player t,String namae) {
		caster = c;
		overrideLoc = l;
		target = t;
		casttime = 30;
		hitSpell = true;
		steprange = 100;
		speed = 0.3;
		hitboxSize = 0.3;
		name = namae;
		castSpell(caster,namae);
	}

	@Override
	public void setUp() {
		
		loc = overrideLoc;
	}

	@Override
	public void cast() {
		loc.setDirection(lerp(loc.getDirection(),(target.getLocation().toVector()).subtract(loc.toVector()), 0.1));
		loc.add(loc.getDirection().normalize());	
		ParUtils.createParticle(Particles.BUBBLE, loc, 0, 0, 0, 5, 0);
	}

	@Override
	public void launch() {
		speed = 2;
		
	}

	@Override
	public void move() {
		loc.setDirection(lerp(loc.getDirection(),(target.getLocation().toVector()).subtract(loc.toVector()), 0.05));
		loc.add(loc.getDirection().normalize());		
	}
	
	public Vector lerp(Vector start, Vector end,double val) {
		return new Vector(lerp(start.getX(),end.getX(),val),lerp(start.getY(),end.getY(),val),lerp(start.getZ(),end.getZ(),val));
	}
	
	public double lerp(double start, double end,double val) {
		return ((end - start)*val)+ start;
	}

	@Override
	public void display() {
		ParUtils.createParticle(Particles.BUBBLE, loc, 0, 0, 0, 5, 0);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		tagPlayer(p);
		hitEnt(p);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		hitEnt(ent);
		
	}
	
	public void hitEnt(LivingEntity ent) {
		ent.setVelocity(loc.getDirection().normalize().multiply(3));
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
	}

	@Override
	public void onDeath() {
		
		
	}

}
