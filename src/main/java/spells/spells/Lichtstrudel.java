package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Lichtstrudel extends Spell {

	ArrayList<Entity> sounded = new ArrayList<Entity>();  
	public Lichtstrudel() {
		cooldown = 20 * 40;
		name = "§cLichtstrudel";
		steprange = 100;
		hitboxSize = 1;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.AURA);
		setLore("§7Der Spieler fliegt für kurze Zeit in#§7Blickrichtung und hinterlässt eine#§7Lichtspur. Gegner, die von dieser Lichtspur erfasst#§7werden,werden bis zum Startpunkt des#§7Zaubers befördert.# #§eShift:§7 Beendet den#§7Zauber sofort.");
	}
	
	Location lastdot;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		lastdot = caster.getLocation();
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		setGliding(caster, true);
		playSound(Sound.BLOCK_BELL_USE,caster.getLocation(),8F,0.1f);
	}

	int delay = 0;
	
	@Override
	public void move() {
		delay++;
		loc = caster.getLocation();
		//playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE,caster.getLocation(),0.1F,2f);
		// TODO Auto-generated method stub
		if (delay > 4) {
			playSound(Sound.ENTITY_EVOKER_PREPARE_ATTACK,caster.getLocation(),0.2F,0.5f);
			delay = 0;
		}
		
		caster.setVelocity(caster.getLocation().getDirection());
		if (step < 90 && delay == 0) {
			//dot(caster.getLocation().add(0,1,0).clone(),lastdot.clone(),caster,100-steprange);
			//lastdot = caster.getLocation().add(0,1,0).clone();
			dot(caster.getLocation(),lastdot.clone(),caster,(int) (100-step));
			lastdot = caster.getLocation().clone();
		}
			
		
		
		ParUtils.parKreisDir(Particle.END_ROD, caster.getLocation().add(0,1,0), 2, 0, 0.1, caster.getLocation().getDirection(), caster.getLocation().getDirection().multiply(-1));
		if (caster.isSneaking()) {
			dead = true;
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
		if (spell.getName().contains("Antlitz der Göttin")){
			setGliding(spell.caster, true);
			setGliding(originalCaster, false);
			//originalCaster.setFlying(false);
			//originalCaster.setAllowFlight(false);
		}
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		setGliding(caster, false);
	}

public void dot(Location la,Location to,Player p,int time ) {
		
		new BukkitRunnable() {
			int t = time;
			Location too = to.clone();
			Location l = la.clone();
			ArrayList<Entity> blackList = new ArrayList<Entity>();
			
			public void run() {
				t--;
				ParUtils.createParticle(Particle.END_ROD, l, 0, 0,0,0, 0);
				for (LivingEntity le : p.getWorld().getLivingEntities()) {
					if (!blackList.contains(le)) {
						
					
					if (checkHit(le,l,p,3)) {
						blackList.add(le);
						doPin(le, too,2);
						if (!sounded.contains(le)) {
							playSound(Sound.BLOCK_BEACON_ACTIVATE,l,25,2);
							sounded.add(le);
						}
					}
					}
				}
				if (t<0) {
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin, 1, 1);
	}


}
