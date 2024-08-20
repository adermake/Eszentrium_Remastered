package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class SpeerderZwietracht  extends Spell{

	
	public SpeerderZwietracht() {
		name = "§eSpeer der Zwietracht";
		cooldown = 20 * 45;
		speed = 10;
		hitEntity = true;
		hitPlayer = true;
		hitSpell = true;
		hitBlock = true;
		hitboxSize = 2;
		
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("§7Schießt einen Speer in Blickrichtung,#§7der getroffene Gegner am nächsten Block in#§7der Flugbahn für kurze Zeit festhält.##§7#§eShift:§7 Zieht den Speer aus dem Gegner#§7heraus und verursacht Schaden.");
		setBetterLore("§7Schießt einen Speer in Blickrichtung,#§7der getroffene Gegner am nächsten Block in#§7der Flugbahn für kurze Zeit festhält.##§7#§eShift:§7 Zieht den Speer aus dem Gegner#§7heraus und verursacht Schaden.");
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	boolean hasHitBlock = false;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (hasHitBlock) {
			
			for (Entity le : hitEntitys) {
				if (le.getLocation().distance(loc)>5) {
					doPull(le, loc,5);
				}
				if (le.getLocation().distance(loc)<5) {
					doPull(le, loc,1);
				}
				if (le.getLocation().distance(loc)<2) {
					doPull(le, loc,0.3);
				}
				if (pressingF.contains(caster) || caster.isSneaking()) {
					playSound(Sound.ENTITY_IRON_GOLEM_DEATH, loc, 4, 2F);
					Location laa =loc.clone();
					Vector v = caster.getLocation().toVector().subtract(laa.toVector()).normalize();
					v.multiply(9);
					laa.add(v);
					doPull(le, caster.getLocation(), 1);
			
					for (int i = 0;i<3;i++)
					bloodLine(le.getLocation(), loc.getDirection().multiply(-1));
					
					damage(le,6,caster);
					dead = true;
				}
			}
			
		}
		else {
			loc.add(loc.getDirection().multiply(0.5));
		}
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createBlockcrackParticle(loc, 0, 0, 0, 1, Material.IRON_BLOCK);
		ParUtils.createBlockcrackParticle(loc, 0, 0, 0, 1, Material.STONE);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		doKnockback(p,caster.getLocation(),1);
		if (refined) {
			damage(p, 4, caster);
		}
		else {
			damage(p, 2, caster);
		}
		playSound(Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, p.getLocation(), 3, 2F);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		doKnockback(ent,caster.getLocation(),1);
		if (refined) {
			damage(ent, 4, caster);
		}
		else {
			damage(ent, 2, caster);
		}
		
		
		playSound(Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, ent.getLocation(), 3, 2F);
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, loc, 3, 0.4F);
		
		
		hitPlayer = false;
		hitEntity = false;
		hitSpell = false;
		step = 0;
		speed = 1;
		if (refined) {
			steprange = 170;
		}else {
			steprange = 60;
		}
		
		hasHitBlock = true;
		hitBlock = false;
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}
	public void bloodLine(Location locC,Vector dir) {
		Location loc = locC.clone();
		dir = randVector().multiply(7).add(dir);
		dir = dir.multiply(2);
		
		loc.add(dir);
		
		ParUtils.parLineRedstoneSpike(locC, loc, Color.RED, 0.2);
		
		
		
	}

	

}
