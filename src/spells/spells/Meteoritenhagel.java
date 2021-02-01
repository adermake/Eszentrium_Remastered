package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;

import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Cooldowns;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Meteor;

public class Meteoritenhagel extends Spell {

	
	public Meteoritenhagel() {
		cooldown = 20 * 40;
		casttime = 0;
		steprange = 25;
		name = "§cMeteoritenhagel";
		speed = 1;
		hitSpell = false;
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("§7Lässt nach kurzer Verzögerung#§7Meteoriten vom Himmel fallen, die bei Bodenkontakt#§7explodieren und nahe Gegner schaden.");
		
	}
	
	@Override
	public void setUp() {
		
		// TODO Auto-generated method stub
		loc = null;
		loc = block(caster,200);
		
		if (loc == null) {
			dead = true;
			refund = true;
		}
		else {
			playSound(Sound.ENTITY_SHULKER_CLOSE,loc,10,0.1F);
			playSound(Sound.ENTITY_CAT_HISS,loc,10,0.5F);
			Location hBlock = loc.clone();
			hBlock.setY(255);
			while (hBlock.getBlock().getType() == Material.AIR) {
				hBlock.add(0,-1,0);
				if (hBlock.getY()<10) {
					Bukkit.broadcastMessage("§c[Error] Logic doesnt seem to apply in this universe");
					break;
					
				}
			}
			loc = hBlock;
		}
		
		
		
	}

	@Override
	public void cast() {
		
		//ParUtils.parKreisSolidRedstone(Color.RED, 1, loc.clone(), cast/4, 0, 1, new Vector(0,1,0));
		Location dot = ParUtils.stepCalcCircle(loc.clone(), 8, new Vector(0,1,0), 0, cast*4);
		ParUtils.createParticle(Particles.LANDING_LAVA, dot, 0, 1, 0, 0, 0);
		ParUtils.createParticle(Particles.LAVA, dot, 0, 1, 0, 0, 0);
		//ParUtils.createParticle(Particles.CAMPFIRE_SIGNAL_SMOKE, dot, 0, 1, 0, 0, 5);
		dot = ParUtils.stepCalcCircle(loc.clone(), 8, new Vector(0,1,0), 0, cast*4+22);
		ParUtils.createParticle(Particles.LANDING_LAVA, dot, 0, 1, 0, 0, 0);
		ParUtils.createParticle(Particles.LAVA, dot, 0, 1, 0, 0, 0);
		//ParUtils.createParticle(Particles.CAMPFIRE_SIGNAL_SMOKE, dot, 0, 1, 0, 0, 5);
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	int delay = 0;
	@Override
	public void move() {
		delay++;
		if (delay > 3) {
			Location calc = loc.clone().add(0,60,0);
			calc.add(randInt(-4,4),0,randInt(-4,4));
			new Meteor(calc,caster,name);
			delay = 0;
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


}
