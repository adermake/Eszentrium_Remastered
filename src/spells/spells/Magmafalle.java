package spells.spells;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Cooldowns;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;

public class Magmafalle extends Spell {
	
	public Magmafalle() {
		name = "§6Magmafalle";
		cooldown = 20 * 20;
		steprange = 1400;
		hitboxSize = 4;
		hitPlayer = false;
		hitEntity = false;
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.KNOCKBACK);
		setLore("§7Platziert eine Falle auf dem Boden in#§7Blickrichtung. Nach kurzer Zeit wird die#§7Falle aktiviert und explodiert, sobald sich#§7ein Gegner nähert.Fallen können ebenfalls#§7mit einem Schwertwurf ausgelöst werden.");
		setBetterLore("§7Platziert eine Falle auf dem Boden in#§7Blickrichtung. Nach kurzer Zeit wird die#§7Falle aktiviert und explodiert, sobald sich#§7ein Gegner nähert.Fallen können ebenfalls#§7mit einem Schwertwurf ausgelöst werden.");
		
		
	}
	@Override
	public void setUp() {
		loc = null;
		loc = block(caster,10);
		if (loc == null) {
			refund = true;
			dead = true;
		}
		else {
			ParUtils.createParticle(Particles.FLAME, loc, 0, 1, 0, 0, 0.3);
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	int delay = 0;
	@Override
	public void move() {
		delay++;
		if (delay > 10)
			delay = 0;
		// TODO Auto-generated method stub
		if (step == 60) {
			hitPlayer = true;
			hitEntity = true;
		}
		
		if (step > 60) {
			
			if (delay == 0)
			ParUtils.createParticle(Particles.DRIPPING_LAVA, loc.clone().add(0,0.3,0), 1, 0, 1, 1, 0.3);
		}
			
		for (Entity ent : caster.getWorld().getEntities()) {
			if (ent instanceof ArmorStand  && ent.getLocation().distance(loc)<hitboxSize) {
				
				ParUtils.createParticle(Particles.FLAME, loc, 1,1, 1, 20, 0.3);
				playSound(Sound.BLOCK_LAVA_EXTINGUISH,loc,6,1);
				dead = true;
				new BukkitRunnable() {
					public void run() {
						if (refined) {
							new ExplosionDamage(9, 8, caster, loc.clone(),name);
							new Repulsion(9, 16, caster, loc.clone(),false,name);
							ParUtils.parKreisDir(Particles.FLAME, loc.clone(), 9, 0, 6, new Vector(0,1,0), new Vector(0,1,0));
							ParUtils.createParticle(Particles.EXPLOSION_EMITTER, loc.clone(), 2,2, 2,16, 1);
							ParUtils.createParticle(Particles.LAVA, loc.clone(), 2,2, 2,40, 1);
						}
						else {
							new ExplosionDamage(6, 6, caster, loc.clone(),name);
							new Repulsion(6, 5, caster, loc.clone(),false,name);
							ParUtils.parKreisDir(Particles.FLAME, loc.clone(), 5, 0, 6, new Vector(0,1,0), new Vector(0,1,0));
							ParUtils.createParticle(Particles.EXPLOSION_EMITTER, loc.clone(), 1,1, 1,4, 1);
							ParUtils.createParticle(Particles.LAVA, loc.clone(), 2,2, 2,40, 1);
						}
						
						playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,loc.clone(),6,0.1F);
						
					}
				}.runTaskLater(main.plugin,10);
			}
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (isOnTeam(p)) {
			return;
		}
		ParUtils.createParticle(Particles.FLAME, loc, 1,1, 1, 20, 0.3);
		playSound(Sound.BLOCK_LAVA_EXTINGUISH,loc,6,1);
		dead = true;
		
		new BukkitRunnable() {
			public void run() {
				if (refined) {
					new ExplosionDamage(9, 8, caster, loc.clone(),name);
					new Repulsion(9, 10, caster, loc.clone(),false,name);
					ParUtils.parKreisDir(Particles.FLAME, loc.clone(), 9, 0, 6, new Vector(0,1,0), new Vector(0,1,0));
					ParUtils.createParticle(Particles.EXPLOSION_EMITTER, loc.clone(), 2,2, 2,16, 1);
					ParUtils.createParticle(Particles.LAVA, loc.clone(), 2,2, 2,40, 1);
				}
				else {
					new ExplosionDamage(6, 6, caster, loc.clone(),name);
					new Repulsion(6, 5, caster, loc.clone(),false,name);
					ParUtils.parKreisDir(Particles.FLAME, loc.clone(), 5, 0, 6, new Vector(0,1,0), new Vector(0,1,0));
					ParUtils.createParticle(Particles.EXPLOSION_EMITTER, loc.clone(), 1,1, 1,4, 1);
					ParUtils.createParticle(Particles.LAVA, loc.clone(), 2,2, 2,40, 1);
				}
				playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,loc.clone(),6,0.1F);
				
			}
		}.runTaskLater(main.plugin,10);
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
		
	}
	

}
