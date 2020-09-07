package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.ParticleType;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class RepulsionDirectional extends Spell {

	double damage = 0;
	double knockback = 0;
	float pitch = 1;
	Location overrideLoc;
	Vector dir;
	public RepulsionDirectional(double size,double knockback,Player caster,Location loca,Vector dir, String namae) {
		hitboxSize = size;
		this.dir = dir;
		steprange = 1;
		this.pitch = pitch;
		this.knockback = knockback;
		overrideLoc = loca;
		name = namae;
		castSpell(caster, name);
	}
	public RepulsionDirectional(double size,double knockback,Player caster,Location loca,Vector dir,boolean b, String namae) {
		hitboxSize = size;
		steprange = 1;
		this.dir = dir;
		this.pitch = pitch;
		this.knockback = knockback;
		overrideLoc = loca;
		canHitSelf =b;
		name = namae;
		castSpell(caster, name);
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = overrideLoc;
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
		
		
	}

	@Override
	public void display() {
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		tagPlayer(p);
		p.setVelocity(dir.multiply(knockback));
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		
		ent.setVelocity(dir.multiply(knockback));
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
