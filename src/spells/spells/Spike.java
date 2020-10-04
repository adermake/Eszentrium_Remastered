package spells.spells;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Spike extends Spell {

	public Spike() {
		cooldown = 20*10;
		this.name = name;
		speed = 3;
		steprange =12;
		hitPlayer = true;
		hitSpell = true;
		
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		
	}
	Location saveLoc;
	public Spike(Player c,Vector v,String name,Location l,int length) {
		super();
		cooldown = 20*10;
		this.name = name;
		speed = 3;
		steprange =42;
		hitPlayer = true;
		hitSpell = true;
		caster = c;
		loc = l;
		steprange = length;
		saveLoc = l;
		saveLoc.setDirection(v);
		loc.setDirection(v);
		castSpell(c, name);
		
	}
	Entity target;
	public Spike(Player c,Vector v,String name,Location l,int length,int delay) {
		super();
		cooldown = 20*10;
		this.name = name;
		speed = 3;
		steprange =42;
		hitPlayer = true;
		hitSpell = true;
		caster = c;
		loc = l;
		steprange = length;
		saveLoc = l;
		saveLoc.setDirection(v);
		loc.setDirection(v);
		this.delay = delay;
		
		castSpell(c, name);
		
	}
	public int delay = 40;
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
	public void move() {
		loc.add(loc.getDirection().multiply(1));
		playSound(Sound.BLOCK_GLASS_BREAK,loc,0.1f,1f);
	}

	public ArrayList<FallingBlock> blocks = new ArrayList<FallingBlock>();
	int i = 0;
	@Override
	public void display() {
		
		
		if (dead)
			return;
		
		if (!loc.getBlock().getType().isSolid()) {
			FallingBlock fb = caster.getWorld().spawnFallingBlock(loc, Material.ICE,(byte) 0);
			fb.setGravity(false);
			blocks.add(fb);
		}
		
		
		// TODO Auto-generated method stub
		//ParUtils.createFlyingParticle(Particle.BUBBLE_POP, loc,0, 0, 0, 1, 2, loc.getDirection().multiply(-1));
		ParUtils.createParticle(Particles.CLOUD, loc, 0.1, 0.1, 0.1, 3, 0);
		
	}

	@Override
	public void onPlayerHit(Player p) {
	
		
		damage(p, 3,caster);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent, 3,caster);
		
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
		
		//bounce();
		
	}


	@Override
	public void onDeath() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (FallingBlock fb : blocks) {
					ParUtils.createBlockcrackParticle(fb.getLocation(), 0.1F, 0.1F, 0.1F, 4, Material.PACKED_ICE);
					playSound(Sound.BLOCK_GLASS_BREAK,fb.getLocation(),1f,2f);
					fb.remove();
				}
				
				
			}
		}.runTaskLater(main.plugin, delay);
		
		
	}


	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}


}
