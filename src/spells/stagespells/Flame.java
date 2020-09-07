package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class Flame extends Spell {
	Location overrideLoc;
	public Flame(String name,Player caster,Location overrideLoc) {
		
		this.caster = caster;
		this.name = name;
		this.overrideLoc =  overrideLoc;
		hitboxSize = 2F;
		hitPlayer = true;
		multihit = true;
		hitEntity = true;
		steprange = 20 * 10;
		castSpell(caster, name);
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc =  overrideLoc;
		//fb = caster.getWorld().spawnFallingBlock(loc.add(0,0.1F,0), Material.FIRE,((byte) 0));
		//fb.setGravity(false);
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
	
	int i = 0;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		i++;
		
		ParUtils.createFlyingParticle(Particles.FLAME, loc, 0.1F, 0.1F, 0.1F, 1, 0.3F, new Vector(0,1,0));
		//ParUtils.createFlyingParticle(Particles.LAVA, loc, 0.5F, 0.5F, 0.5F, 1, 0.3F, new Vector(0,1,0));
		SoundUtils.playSound(Sound.BLOCK_FIRE_AMBIENT, loc,1.3F,0.3F);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		p.setFireTicks(20);
		damage(p, 3, caster);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		ent.setFireTicks(20);
		damage(ent, 3, caster);
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
