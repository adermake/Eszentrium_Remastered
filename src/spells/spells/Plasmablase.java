package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Plasmablase extends Spell {

	public Plasmablase() {
		cooldown = 20 * 40;
		
		name = "§3Plasmablase";
		hitSpell = true;
		canHitCastersSpells = true;
		hitboxSize = rad;
		canHitSelf = false;
		steprange = 20 * 6;
		
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.SUPPORT);
		
		setLore("§7Erzeugt für kurze Zeit eine Blase um#§7den Spieler herum. Zauber von außerhalb#§7können nicht in das Innere eindringen, während#§7Gegnerin der Blase nicht mehr hinauskommen.#§7Dies gilt nicht für den Anwender.##§7#§eShift:§7 Solange diese Taste gedrückt bleibt,#§7kann der Anwender ebenfalls nicht aus der#§7Blase entkommen.");
		setBetterLore("§7Erzeugt für kurze Zeit eine Blase um den#§7Spieler herum. Zauber von außerhalb können#§7nicht in das Innere eindringen, während#§7Gegnerin der Blase nicht mehr hinauskommen.#§7Dies gilt nicht für den Anwender.##§7#§eShift:§7 Solange diese Taste gedrückt bleibt,#§7kann der Anwender ebenfalls nicht aus der#§7Blase entkommen.");
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET, loc, 12,0.7F);
		playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,0.8F);
		bulletHitEffect(caster.getLocation().getDirection().multiply(-1),loc.clone());
		if (refined) {
			steprange*=2;
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		ParUtils.auraParticle(Particles.ENCHANTED_HIT, caster, 1, 1);
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	Vector dir = new Vector(0,1,0);
	@Override
	public void move() {
		
		if (caster.isSneaking()) {
			canHitSelf = true;
		}
		else {
			canHitSelf = false;
			if (hitEntitys.contains(caster)) {
				hitEntitys.remove(caster);
			}
		}
			
		
		// TODO Auto-generated method stub
		for (Entity ent : hitEntitys) {
			if (ent.getLocation().distance(loc)> rad) {
				if (ent instanceof Player) {
					Player p = (Player)ent;
					if (p.getGameMode() == GameMode.ADVENTURE) {
						continue;
					}
				}
				Vector v = ent.getLocation().toVector().subtract(loc.toVector());
				ParUtils.parKreisDir(Particles.ENCHANTED_HIT, ent.getLocation(), 2, 0, 0, v,v);
				doPull(ent, loc.clone(), 1.5F);
				playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,1.8F);
			}
		}
		
	}
	double rad = 15;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		for (int i = 0;i<3;i++) {
			loc.setPitch(loc.getPitch()+15);
			Location l = ParUtils.stepCalcCircle(loc, rad, loc.getDirection().add(new Vector(0,i,0)), 0, step*(i+1));
			//ParUtils.createParticle(Particles.FISHING, l, 0, 0, 0, 1, 0);
			float f = 3F;
			
			
			if (refined) {
				f = 10;
				Location l2 = ParUtils.stepCalcCircle(loc, rad, loc.getDirection().add(new Vector(0,i,0)), 0, step*(i+1));
				ParUtils.createRedstoneParticle(l, 1, 1, 1, 1, Color.fromBGR(230, 115, 25), f);
			}	
				ParUtils.createRedstoneParticle(l, 0, 0, 0, 1, Color.AQUA, f);
			
			
		}
	}

	public void bulletHitEffect(Vector ind,Location inl) {
		ParUtils.createParticle(Particles.FLASH, inl, 0, 0, 0,1, 1);
		Vector d = ind.clone();
		Location l = inl.clone();
		Location ori = inl.clone();
		l.add(d.normalize().multiply(-rad));
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (int i = 0;i<2;i++) {
					
				
				l.add(ind.clone().normalize().multiply(1.5));
				double distance = l.distance(ori);
				ParUtils.parKreisDir(Particles.ENCHANTED_HIT, l, calcWidthOfCircle(rad, distance+rad), 0, 0, d, d);
				
				//ParUtils.parKreisSolidRedstone(Color.AQUA, 3, l, calcWidthOfCircle(rad, distance+rad),0, 1, ind);
				if (distance > rad*2) {
					this.cancel();
				}
				}
			}
		}.runTaskTimer(main.plugin, 1,1);
		
		
	}
	
	public double calcWidthOfCircle(double r,double h) {
		return Math.sqrt((r*r)-Math.pow((h-r),2));
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
		if (hitEntitys.contains(spell.caster)) {
			return;
		}
		playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET, loc, 12,0.7F);
		playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,0.8F);
		
		// TODO Auto-generated method stub
		Location l1 = spell.getLocation().clone();
		Vector dir = loc.toVector().subtract(l1.clone().toVector());
		bulletHitEffect(dir, loc.clone());
		spell.kill();
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET, loc, 12,2F);
		playSound(Sound.BLOCK_CONDUIT_DEACTIVATE, loc, 12,0.1F);
		bulletHitEffect(caster.getLocation().getDirection().multiply(-1),loc.clone());
	}

}
