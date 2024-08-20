package spells.spells;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.HealingAura;
import spells.stagespells.Repulsion;
import spells.stagespells.RepulsionDirectional;

public class Springkraut extends Spell {

	ArrayList<Material> flowerMat = new ArrayList<Material>();
	Material m;
	
	public Springkraut() {
		flowerMat.add(Material.ORANGE_TULIP);
		flowerMat.add(Material.PINK_TULIP);
		flowerMat.add(Material.RED_TULIP);
		flowerMat.add(Material.WHITE_TULIP);
		speed = 2;
		m = flowerMat.get(randInt(0,flowerMat.size()-1));
		cooldown = 20 * 25;
		name = "§aSpringkraut";
		steprange = 2 * 20 * 10;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.SUPPORT);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("Wirft einen Blumenstrauß, der bei Blockkontakt vom Block abspringt und ein Heilfeld erzeugt, das den Anwender heilt. Betroffene Gegner werden ebenfalls geheilt, werden aber zusätzlich zurückgeworfen." + 
				"F: Stoppt das Projektil sofort.");
		
		setBetterLore("Wirft einen Blumenstrauß, der bei Blockkontakt vom Block abspringt und ein Heilfeld erzeugt, das den Anwender heilt. Betroffene Gegner erleiden stattdessen Schaden und werden zurückgeworfen." + 
				"F: Stoppt das Projektil sofort.");
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		if (refined) {
			speed = speed *2;
			steprange = steprange*2;
			m = Material.WITHER_ROSE;
			
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		vel = caster.getLocation().getDirection();
	}

	Vector vel;
	@Override
	public void move() {
		vel.add(new Vector(0,-0.04,0));
		if (vel.getY() < - 1) 
			vel.setY(-1);
		loc.add(vel.clone().multiply(0.5));
		
		if (swap()) {
			dead = true;
		}
	}
	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		if (refined) {
			ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.BLACK, 0.8F);
		}
		else {
			ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.LIME, 0.8F);
		}
		
		
		ParUtils.dropItemEffectRandomVector(loc, m, 1, 2, 0);
		//ParUtils.createParticle(Particle.TOTEM, loc, 0, 0, 0, 0, 1);
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
		playSound(Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, loc, 22, 1);
		playSound(Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, loc, 22, 1);
		playSound(Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, loc, 22, 1);
		
		loc.setDirection(vel);
		BlockFace bf = bounce();
		if (loc.getDirection().getY() > 0.999) {
			loc.setDirection(loc.getDirection().add(randVector().setY(0).normalize().multiply(0.15)));
		}
		
		double mag = vel.length();
		vel = loc.getDirection().multiply(mag);
		vel.add(caster.getLocation().toVector().subtract(loc.toVector()).multiply(0.08F));
		if (bf != BlockFace.DOWN) {
			if (refined) {
				vel.setY(2F);
			}
			else {
				vel.setY(2.2F);
			}
		}
		
		
		if (vel.length() > 1.5) {
			vel.normalize().multiply(1.5);
		}
		if (refined) {
			ParUtils.parKreisDot(Particle.SMOKE_NORMAL, loc,4, 0.2, 0, new Vector(0,1,0));
		}
		else {
			ParUtils.parKreisDot(Particle.VILLAGER_HAPPY, loc,4, 0.2, 0, new Vector(0,1,0));
		}
		
		ParUtils.parKreisDot(Particle.TOTEM, loc,1, 1, 1, new Vector(0,1,0));
		for (float f = 0;f<44;f+=2) {
			ParUtils.dropItemEffectVector(ParUtils.stepCalcCircle(loc.clone(), 3, new Vector(0,1,0), 0,f), flowerMat.get(randInt(0,flowerMat.size()-1)), 1, 15, 0.3F,new Vector(0,1,0),(int)f);
		}
		new BukkitRunnable() {
			Location l1 = loc.clone();
			float t = 0;
			public void run() {
				t++;
				ParUtils.parKreisDot(Particle.TOTEM, l1,t/1.5F, 0, 1, new Vector(0,1,0));
				if (t>2) {
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin,5,1);
		
		new HealingAura(name,caster, loc.clone(), 4, 5,refined);
		new Repulsion(6, 2, caster, loc.clone(), name);
		
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
