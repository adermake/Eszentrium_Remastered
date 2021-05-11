package spells.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Cooldowns;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Enterhaken extends Spell{

	public Enterhaken() {
		cooldown = 20*25;
		name = "§bEnterhaken";
		speed = 1;
		steprange = 300;
		hitPlayer = false;
		
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.SELFCAST);
		setLore("Zieht den Spieler an den nächsten Block in Blickrichtung. Dort bleibt er für kurze Zeit hängen. Shift: Der Spieler springt in Blickrichtung.");
		setBetterLore("§7Zieht den Spieler an den nächsten#§7Block in Blickrichtung. Dort bleibt#§7er für kurze Zeit hängen.");
	}	
	
	Trident hook;
	@Override
	public void setUp() {
		
	}
		// TODO Auto-generated method stub
		
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	Vector dir;
	
	@Override
	public void launch() {
		
		// TODO Auto-generated method stub
		loc = blockHo(caster);
		
		
		
		if (loc == null) {
			dead = true;
			refund = true;
		}
		else {
			/* Does inexplicable damage
			hook = (Trident) caster.getWorld().spawnEntity(caster.getEyeLocation(), EntityType.TRIDENT);
			hook.setGravity(false);
			hook.setVelocity(caster.getLocation().getDirection().multiply(6));
			hook.setDamage(0); */
		}
		
		
	}

	
	@Override
	public void move() {
		if (dead)
			return;
		
		if (loc != null && caster != null)
		dir = (loc.clone()).toVector().subtract(caster.getLocation().toVector()).normalize();
		playSound(Sound.BLOCK_TRIPWIRE_ATTACH, caster.getLocation(), 1, 2);
		if (refined) {
			caster.setVelocity(dir.clone().multiply(2.4));
		}else {
			caster.setVelocity(dir.clone().multiply(1.4));
		}
		
		
		if (caster.isSneaking()) {
			playSound(Sound.BLOCK_TRIPWIRE_CLICK_ON, caster.getLocation(), 1, 2);
			if (refined) {
				caster.setVelocity(caster.getLocation().getDirection().multiply(6));
			}else {
				caster.setVelocity(caster.getLocation().getDirection().multiply(2));
			}
			
			dead = true;
		}
		if (((caster.getLocation()).distance(loc))<2) {
			dead = true;
			new BukkitRunnable() {
				int t = 0;
				public void run() {
					t++;
					if (t>60) {
						this.cancel();
					}
					
					playSound(Sound.BLOCK_TRIPWIRE_DETACH, caster.getLocation(), 0.4F, 0.1F);
					caster.setVelocity(caster.getVelocity().multiply(0));
					if (caster.isSneaking()) {
						playSound(Sound.BLOCK_TRIPWIRE_CLICK_ON, caster.getLocation(), 1, 2);
						caster.setVelocity(caster.getLocation().getDirection().multiply(2));
						this.cancel();
					}
				}
			}.runTaskTimer(main.plugin, 1, 1);
			
			
		}
		
	}

	@Override
	public void display() {
		if (loc != null && caster != null)
		ParUtils.parLine(Particles.CRIT,caster.getLocation(), loc.clone(), 0, 0, 0, 0, 0, 3);
		
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
		if (hook != null)
			hook.remove();
	}
	public Location blockHo(Player p) {
		Location loc = p.getLocation();
		for (int t = 1; t <= 60; t++) {

			Vector direction = loc.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
		
			Location lo = loc.clone();

			if (loc.getBlock().getType() != Material.AIR) {
				return loc;

			}

			loc.subtract(x, y, z);
		}
		return null;

	}
	public Location blockHoPar(Player p) {
		Location l = p.getLocation();
		for (int t = 1; t <= 60; t++) {

			Vector direction = l.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			ParUtils.createParticle(Particles.CRIT, l, 0, 0, 0, 1, z);
			
			Location lo = l.clone();

			if (l.getBlock().getType() != Material.AIR) {
				return l;

			}

			l.subtract(x, y, z);
		}
		return null;

	}

	
}
