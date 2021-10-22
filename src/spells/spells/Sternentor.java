package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Sternentor extends Spell {

	public Sternentor() {
		name = "§bSternentor";
		
		cooldown = 20 * 25;
		steprange = 20 * 8;
		speed = 1;
		canHitSelf = true;
		multihit = true;
		hitboxSize = 3;
		

		addSpellType(SpellType.AURA);
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.SUPPORT);
		
		setLore("Erzeugt für kurze Zeit ein Feld auf dem anvisierten Block. Spieler in diesem Feld werden in Blickrichtung katapultiert. Für Gegner " + 
				"wirkt dieser Effekt erst nach kurzer Verzögerung." + 
				"Shift: Solange diese Taste gedrückt bleibt, wird die Wurfrichtung umgekehrt." + 
				"F: Fixiert die Wurfrichtung des Sternentors für die verbleibende Dauer des Zaubers." 
				);
		setBetterLore("Erzeugt für kurze Zeit ein Feld auf dem anvisierten Block. Spieler in diesem Feld werden in Blickrichtung katapultiert." + 
				"Shift: Solange diese Taste gedrückt bleibt, wird die Wurfrichtung umgekehrt." + 
				"F: Fixiert die Wurfrichtung des Sternentors für die verbleibende Dauer des Zaubers.");
		
	}
	int setuptime = 15;
	int hitCooldown = 0;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = caster.getLocation().add(caster.getLocation().getDirection().multiply(15));
		Location l1 = block(caster);
		
		if (l1 != null) {
			loc = l1.add(0,2,0);
			
			
		}
		if (loc.getY() < 60) {
			loc.setY(60);
		}
		ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 3, 1);
		playSound(Sound.BLOCK_END_PORTAL_SPAWN,loc,2,5);
		if (refined) {
			rad = 6;
			hitboxSize = 6;
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		dirvec = caster.getLocation().getDirection();
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	Vector dirvec;
	boolean lock = false;
	double rad = 3;
	@Override
	public void display() {
		
		
			hitCooldown--;
			if (hitCooldown <= 0) {
				hitPlayer = true;
				hitEntity = true;
			}
		
		if (!lock)
		dirvec = caster.getLocation().getDirection();
		if (swap() ) {
			lock = true;
		}
		// TODO Auto-generated method stub
		for (int i = 0;i<3;i++) {
			Location l = ParUtils.stepCalcCircle(loc, rad, loc.getDirection().add(new Vector(0,i,0)), 0, step*(i+1));
			//ParUtils.createParticle(Particle.FISHING, l, 0, 0, 0, 1, 0);
			float f = 1F;
			if (step > setuptime) {
				f = 2F;
			}
			if (caster.isSneaking()) {
				ParUtils.dropItemEffectVector(l, Material.REDSTONE_BLOCK, 1, 1, 0,new Vector(0,1,0));
				ParUtils.createRedstoneParticle(l, 0, 0, 0, 1, Color.RED, f);
			}
			else {
				ParUtils.dropItemEffectVector(l, Material.GLOWSTONE, 1, 1, 0,new Vector(0,1,0));
				ParUtils.createRedstoneParticle(l, 0, 0, 0, 1, Color.YELLOW, f);
			}
			
		}
		if (caster.isSneaking()) {
			ParUtils.createFlyingParticle(Particle.END_ROD, loc, 0.1, 0.1, 0.1, 1, 2,dirvec.clone().multiply(-1));
		}
		else {
			ParUtils.createFlyingParticle(Particle.END_ROD, loc, 0.1, 0.1, 0.1, 1, 2,dirvec);
		}
		if (step == setuptime && !refined) {
			playSound(Sound.BLOCK_BEACON_ACTIVATE,loc,2,5);
			ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 3, 1);
		}
		
		
		
	}

	@Override
	public void onPlayerHit(Player p) {
		if (step < setuptime && p != caster && !refined) {
			return;
		}
		if (p != caster) {
			tagPlayer(p);
		}
		playSound(Sound.ENTITY_BLAZE_SHOOT,loc,0.3F,5);
		ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 3, 1);
		// TODO Auto-generated method stub
		if (caster.isSneaking()) {
			Vector d = dirvec.clone().multiply(-5);
			p.setVelocity(d);
			
		}
		else {
			p.setVelocity(dirvec.clone().multiply(5));
		}
		new BukkitRunnable() {
			public void run( ) {
				hitEntity = false;
				hitPlayer = false;
				hitCooldown = 5;
			}
		}.runTaskLater(main.plugin,1);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 3, 1);
		// TODO Auto-generated method stub
		if (caster.isSneaking()) {
			ent.setVelocity(dirvec.clone().multiply(-5));
		}
		else {
			ent.setVelocity(dirvec.clone().multiply(5));
		}
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
