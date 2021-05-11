package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Notenzauber extends Spell {

	
	public Notenzauber() {
		name = "§eNotenzauber";
		cooldown = 20 * 19;
		casttime = 64;
		hitEntity = false;
		hitPlayer = true;
		hitSpell = true;
		speed = 4;
		steprange = 80;
		hitboxSize = 3;
		
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.PROJECTILE);
		setLore("§7Schießt nach kurzer Verzögerung ein#§7Projektil in Blickrichtung, das getroffene#§7Gegner festhält.");
		setBetterLore("§7Schießt ein Projektil in Blickrichtung,#§7das getroffene Gegner festhält.");
		
	}
	
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		if (refined) {
			casttime = 5;
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		loc = caster.getLocation();
		Location loca = ParUtils.stepCalcSpiral(loc.clone(), 2, new Vector(0,1,0),0, cast);
		playSound(Sound.BLOCK_NOTE_BLOCK_FLUTE,loc,(float) 0.3,(float) 0.3);
		ParUtils.createParticle(Particles.NOTE, loca, 0, 0, 0, 1,1);
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		loc = caster.getEyeLocation();
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		loc.add(loc.getDirection().multiply(0.5));
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.NOTE, loc, 0.2, 0.2, 0.2, 1,2);
		
		playSound(Sound.BLOCK_NOTE_BLOCK_FLUTE,loc,(float) 0.3,(float) 1);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
			spawnWirbel(p, 5, 0.5, 2);
		damage(p, 5, caster);
		
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
	
	public void spawnWirbel(Player p,final double radius, final double dichte, final double höheplus) {
		new BukkitRunnable() {
			Location loca = p.getLocation();
			double t = 0;
			double r = radius;

			public void run() {
				
				t = t + Math.PI / 16;
				r = r - 0.1;
				double x = r * Math.cos(t);
				double y = dichte * t;
				double z = r * Math.sin(t);
				loca.add(x, y, z);
				
					if (p.getGameMode() == GameMode.ADVENTURE) {
						this.cancel();
					}
					Location locP = p.getLocation();
					locP.setDirection(loca.toVector().subtract(locP.toVector()));
					p.teleport(locP);
				
				ParUtils.createParticle(Particles.NOTE, loca, 0.1,0.1, 0.1, 1,0);
				playSound(Sound.BLOCK_NOTE_BLOCK_FLUTE,loca,(float) 0.3,2);
				loca.subtract(x, y, z);
				if (t > Math.PI * 2) {
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin, 0, 0);
	}


	

}
