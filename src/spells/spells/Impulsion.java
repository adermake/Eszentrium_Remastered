package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;

public class Impulsion extends Spell {

	public Impulsion() {
		cooldown = 20*20;
		name = "§eImpulsion";
		speed = 5;
		steprange = 200;
		hitPlayer = false;
		hitSpell = true;
		hitEntity = false;
		
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		setLore("§7Schießt ein Projektil in#§7Blickrichtung, das bei Blockkontakt ein Feld erzeugt,#§7das Spieler anzieht und ihnen Schaden zufügt.");
		setBetterLore("§7Schießt ein Projektil in#§7Blickrichtung, das bei Blockkontakt ein Feld erzeugt,#§7das Spieler anzieht und ihnen Schaden zufügt.");
	}
	
	
	@Override
	public void setUp() {
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		loc.add(loc.getDirection().multiply(0.5));
		
	}

	int t = 100;
	@Override
	public void display() {
		t-=2;
		// TODO Auto-generated method stub
		//ParUtils.createFlyingParticle(Particle.BUBBLE_POP, loc,0, 0, 0, 1, 2, loc.getDirection().multiply(-1));
		if (t < 0) {
			t = 0;
		}
		ParUtils.createRedstoneParticle(loc,0, 0, 0, 1, Color.fromRGB(t, 0, t), 2F);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		
		damage(p, 1,caster);
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent, 1,caster);
	
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
		
		
		//bounce();
		
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				t++;
				if (t > 2 && !refined) {
					this.cancel();
				}
				if (t > 6) {
					this.cancel();
				}
				new ExplosionDamage(12, 1, caster, loc, name);
				wurmloch(loc.add(loc.getDirection().multiply(-1)), caster, 8);
				new Repulsion(12, -1, caster, loc, name);
			}
		}.runTaskTimer(main.plugin, 0, 15);
		dead = true;
	}


	@Override
	public void onDeath() {
		
		
	}


	@Override
	public void launch() {
		// TODO Auto-generated method stub
		playSound(Sound.BLOCK_CONDUIT_DEACTIVATE,caster.getLocation(),1,0.3F);
	}

	
	public void wurmloch(final Location setloc, final Player p,final int range) {
		playSound(Sound.ENTITY_ELDER_GUARDIAN_HURT,setloc,5,0.3F);
		for (int t = 0;t<100;t++) {
			int r = randInt(0,100);
			ParUtils.createRedstoneParticle(loc,0.3, 0.3, 0.3, 2, Color.fromRGB(r, 0, r), 2F);
		}
		ParUtils.parKreisDot(Particles.LARGE_SMOKE, loc, 7, 1, -0.1F, new Vector(0,1,0));
		new BukkitRunnable() {
			double t = Math.PI / 4;
			Location loc = setloc;
			Location lu = loc.clone().add(0, 0, 0);
			Location loc2 = loc.clone().add(0, 5, 0);

			public void run() {
				loc2.add(0, 1, 0);

				t = t + 1 * Math.PI;
				for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
					double x = t * Math.cos(theta)*range/8;
					double y = 0;
					double z = t * Math.sin(theta)*range/8;
					loc.add(x, y, z);
					int minus = 0;
					while (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
						loc.add(0, -1, 0);
						minus++;
						if (minus >= 256) {
							break;
						}
					}

					Location locfalling = loc.clone();
					locfalling.subtract(0, 1, 0);
					// ParticleEffect.FIREWORKS_SPARK.display(loc,0,0,0,0,1);
					if (loc.getBlock().getType() == Material.AIR) {
						FallingBlock f = p.getWorld().spawnFallingBlock(loc, locfalling.getBlock().getType(),  locfalling.getBlock().getData());
						f.setVelocity(f.getVelocity().setY(0.3D));
						
						// doPull(f, loc2);
						doKnockback(f, loc2, -t / 15);
						f.setDropItem(false);
					}

					loc.subtract(x, y, z);
					loc.add(0, minus, 0);
					theta = theta + Math.PI / 64;

					x = t * Math.cos(theta);
					y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
					z = t * Math.sin(theta);
					loc.add(x, y, z);

					loc.subtract(x, y, z);
				}
				if (t > 7) {
					this.cancel();
				}
			}

		}.runTaskTimer(main.plugin, 0, 1);

	}
}
