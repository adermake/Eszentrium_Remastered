package spells.spells;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Aufwind extends Spell{

	public Aufwind() {
		name = "§bAufwind";
		cooldown = 20*18;
		hitPlayer = false;
		hitEntity = false;
		hitboxSize = 5;
		steprange = 130;
		addSpellType(SpellType.MOBILITY);
	}
	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public void setUp() {
		playSound( Sound.ITEM_ELYTRA_FLYING,caster.getLocation(), 0.3F, 2);
		if (refined) {
			hitPlayer = true;
			hitEntity = true;
			spawnSpirale(caster,caster, 5,  2.5, 0);
			spawnSpirale(caster,caster, 5,  2.5, 1);
			spawnSpirale(caster,caster, -5,  2.5, 1);
			spawnSpirale(caster,caster, -5,  2.5, 0);
		}
		else {
			spawnSpirale(caster,caster, 2, 2.5, 0);
			spawnSpirale(caster,caster, 2, 2.5, 1);
			spawnSpirale(caster,caster, -2, 2.5, 1);
			spawnSpirale(caster,caster, -2, 2.5, 0);
			dead = true;
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

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (refined) {
			for (Entity ent : hitEntitys) {
				doPull(ent,caster.getLocation().add(0,5,0),1.5);
				
				if (caster.isSneaking()) {
					ent.setVelocity(caster.getLocation().getDirection().multiply(4));
					dead = true;
				}
			}
			if (caster.isSneaking()) {
				ParUtils.parKreisDir(Particles.CLOUD, caster.getLocation(), 3, 0, 1, caster.getLocation().getDirection(),caster.getLocation().getDirection());
				dead = true;
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
	
	public void spawnSpirale(final Player p,final Player caster, final double radius, final double dichte, final double höheplus) {
		new BukkitRunnable() {
			Location la = p.getLocation();
			double t = 0;
			double r = radius;

			public void run() {
				
				t = t + Math.PI / 16;
				double x = r * Math.cos(t);
				double y = dichte * t;
				double z = r * Math.sin(t);
				la.add(x, y, z);
				if (p.getGameMode() == GameMode.ADVENTURE) {
					this.cancel();
				}
				ParUtils.createParticle(Particles.CLOUD, la, 0, 0, 0, 1, 0);
				
				
				p.setVelocity(p.getVelocity().setY(0.5));

				la.subtract(x, y, z);
				la.setX(p.getLocation().getX());
				la.setZ(p.getLocation().getZ());
				if (caster.isSneaking()) {
					this.cancel();
					p.setVelocity(caster.getLocation().getDirection().multiply(2));
					
				}
				if (t > Math.PI * 5) {
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin, 0, 1);
	}


}
