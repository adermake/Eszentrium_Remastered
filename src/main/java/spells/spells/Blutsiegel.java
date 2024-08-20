package spells.spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Blutsiegel extends Spell {

	public static ArrayList<Blutsiegel> blutsiegel = new ArrayList<Blutsiegel>();
	
	public Blutsiegel() {
		name = "§cBlutsiegel";
		cooldown = 20*40;
		steprange = 20 * 8;
		hitboxSize = rad;
		hitPlayer = true;
		blutsiegel.add(this);
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.SUPPORT);
		
		setLore("ï¿½7Beschwï¿½rt ein Pentagramm auf dem Boden#ï¿½7in Blickrichtung. Gegner, die sich auf dem#ï¿½7Pentagramm befinden, erhalten einmalig#ï¿½7Schaden.Auï¿½erdem wird der Anwender um den#ï¿½7Schaden geheilt, den ein Gegner in diesem Feld#ï¿½7erleidet.");
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		Location l = block(caster);
		if (l == null) {
			refund = true;
			dead = true;
		}
		else {
			ParUtils.parKreisDir(Particle.LARGE_SMOKE, l, 8, 0, 1, new Vector(0,1,0), new Vector(0,1,0));
			//playSound(Sound.BLOCK_ANVIL_PLACE,l,0.5,0.1F);
			playSound(Sound.ENTITY_ENDERMAN_SCREAM,l,0.5,0.5F);
		}
		
		
		loc = getTop(l);
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
   double rad = 10;
	@Override
	public void display() {
		double offset = -0.5;
		if (step % 6 == 0) {
			
		
		// TODO Auto-generated method stub
			
			
		ParUtils.parKreisRedstone(Color.fromBGR(0, 0, 100), 2, loc.clone().add(0,offset,0), rad, 0, 1,32, new Vector(0,1,0));
		Location l1 = ParUtils.stepCalcCircle(loc.clone().add(0,offset,0), rad, new Vector(0,1,0), 0, 8.8);
		Location l2 = ParUtils.stepCalcCircle(loc.clone().add(0,offset,0), rad, new Vector(0,1,0), 0, 8.8*2);
		Location l3 = ParUtils.stepCalcCircle(loc.clone().add(0,offset,0), rad, new Vector(0,1,0), 0, 8.8*3);
		Location l4 = ParUtils.stepCalcCircle(loc.clone().add(0,offset,0), rad, new Vector(0,1,0), 0, 8.8*4);
		Location l5 = ParUtils.stepCalcCircle(loc.clone().add(0,offset,0), rad, new Vector(0,1,0), 0, 8.8*5);
		double tic = 0.5;
		ParUtils.parLineRedstone(l1, l3, Color.RED, 1, tic);
		ParUtils.parLineRedstone(l1, l4, Color.RED, 1, tic);
		ParUtils.parLineRedstone(l3, l5, Color.RED, 1, tic);
		ParUtils.parLineRedstone(l2, l4, Color.RED, 1, tic);
		ParUtils.parLineRedstone(l2, l5, Color.RED, 1, tic);
		ParUtils.createFlyingParticle(Particle.FLAME, l1, 0.05, 0, 0.05, 1, 0.1, new Vector(0,1,0));
		ParUtils.createFlyingParticle(Particle.FLAME, l2, 0.05, 0, 0.05, 1, 0.1, new Vector(0,1,0));
		ParUtils.createFlyingParticle(Particle.FLAME, l3, 0.05, 0, 0.05, 1, 0.1, new Vector(0,1,0));
		ParUtils.createFlyingParticle(Particle.FLAME, l4, 0.05, 0, 0.05, 1, 0.1, new Vector(0,1,0));
		ParUtils.createFlyingParticle(Particle.FLAME, l5, 0.05, 0, 0.05, 1, 0.1, new Vector(0,1,0));
		//ParUtils.parKreisDot(Particle.SMOKE_NORMAL,loc.clone().add(0,offset,0), rad, 0, -1, new Vector(0,1,0));
		}
		ParUtils.createFlyingParticle(Particle.SMOKE, loc.clone(), rad/2, 0, rad/2, 10, 0, new Vector(0,1,0));
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p,2,caster);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent,2,caster);
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
		
		blutsiegel.remove(this);
	}
	
	public void subjectDamage(double dmg,Player subject) {
		if (caster != null && caster != subject) {
			
			double h = caster.getHealth()+dmg;
			if (h > caster.getMaxHealth()) {
				h = caster.getMaxHealth();
			}
			caster.setHealth(h);
		}
		
	}

	
}
