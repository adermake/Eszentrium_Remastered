package spells.spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import esze.utils.PlayerUtils;
import esze.utils.SoundUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Cooldowns;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Schicksalsschnitt extends Spell {

	
	public Schicksalsschnitt() {
		
		name = "§cSchicksalsschnitt";
		steprange = 82;
		cooldown = 20 * 45;
		
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.MOBILITY);
		setLore("§7Teleportiert den Spieler zum#§7anvisierten Gegner. Fügt nach kurzer Zeit Schaden an#§7diesem Gegner an, der steigt, je weiter#§7dieser vomAnwender entfernt ist.# #§eF:§7 Der#§7Spieler springt in Blickrichtung.");
	}
	
	Player target;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		target = pointEntity(caster,33);
		if (target == null) {
			refund = true;
			dead = true;
		}
		else {
			
			
			
			
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		if (target == null)
			return;
		Location locTp = target.getLocation();
		locTp.setDirection(caster.getLocation().getDirection());
		ParUtils.createFlyingParticle(Particles.CLOUD, caster.getLocation(), 0.1, 0.4, 0.1, 30, 2, locTp.toVector().subtract(caster.getLocation().toVector()).normalize());
		caster.teleport(locTp);
		//PlayerUtils.hidePlayer(caster,80);
	}
	boolean dashed = false;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (target == null)
			return;
		if (step< 30) {
			spawnCut(target.getLocation(),caster,(int)step);
		}
		
		
		if ((int)step % 20 == 0) {
			SoundUtils.playSound(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, loc,2,0.1F);
		}
		if (swap() && !dashed) {
			dashed = true;
			caster.setVelocity(caster.getLocation().getDirection().multiply(3));
		}
		
		if(step>80) {
			playSound(Sound.ENTITY_POLAR_BEAR_DEATH, target.getLocation(), 5, (float) 1.4);
			for (int i = 0;i<5;i++) {
				bloodLine(target.getLocation(), caster.getLocation().toVector().subtract(target.getLocation().toVector()));
			}
			dead = true;
			
			double dmg = caster.getLocation().distance(target.getLocation());
			dmg = dmg/4;
			if (dmg > 13)
				dmg = 13;
			
			damage(target, dmg, caster);
		}
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

	
	public void spawnCut(Location l,Player p,int war) {
		if (randInt(1,11)<war) {
			if (war%2==0) {
				playSound(Sound.BLOCK_ANVIL_PLACE, l, 2F, 1.6F);
			}
			
			Location rot = l.clone();
			Vector v = new Vector(randInt(-10,10)/6,randInt(-10,10)/6,randInt(-10,10)/6);
			rot.setDirection(v);
			//parKreisCut(l,p,randInt(2,4),0,rot);
			ParUtils.parKreisDot(Particles.CRIT, l, randInt(2,4), 0, 1, rot.getDirection());
		}
		
	}
	
	
	
	public void bloodLine(Location locC,Vector dir) {
		Location loc = locC.clone();
		dir = randVector().multiply(7).add(dir);
		dir = dir.multiply(2);
		
		loc.add(dir);
		
		ParUtils.parLineRedstoneSpike(locC, loc, Color.RED, 0.2);
		
		
		
	}

	
}
