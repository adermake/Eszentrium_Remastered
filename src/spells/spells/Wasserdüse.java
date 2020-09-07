package spells.spells;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftGuardian;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.util.Vector;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.stagespells.GuardianLaser;
import spells.stagespells.ThermolanzeLaser;

public class Wasserdüse extends Spell {

	public Wasserdüse() {
		cooldown = 20 * 70;
		name = "§cWasserdüse";
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		
	
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	Guardian g1;
	Guardian g2;
	@Override
	public void launch() {
		
		if (refined) {
			g1 = (Guardian) spawnEntity(EntityType.ELDER_GUARDIAN);
			g2 = (Guardian) spawnEntity(EntityType.ELDER_GUARDIAN);
		}
		else {
			g1 = (Guardian) spawnEntity(EntityType.GUARDIAN);
			g2 = (Guardian) spawnEntity(EntityType.GUARDIAN);
		}
	
		g1.setMaxHealth(6);
		g1.setHealth(6);
		g2.setMaxHealth(6);
		g2.setHealth(6);
		
		
		//unHittable.add(g1);
		//unHittable.add(g2);
		//g1.setGravity(false);
		//g2.setGravity(false);
	}
	int maxcharges = 100;
	int charges = 100;
	double rad = 2.5;
	@Override
	public void move() {
		if (refined) {
			rad = 5;
		}
		loc = caster.getLocation();
		// TODO Auto-generated method stub
		
		Location l1 = ParUtils.stepCalcCircle(caster.getLocation(), rad, caster.getLocation().getDirection(), 0, 0).add(0,1,0);
		Location l2 = ParUtils.stepCalcCircle(caster.getLocation(), rad, caster.getLocation().getDirection(), 0, 22).add(0,1,0);
		
		doPin(g1, l1);
		doPin(g2, l2);
		g1.teleport(g1.getLocation().setDirection(caster.getLocation().getDirection()));
		g2.teleport(g2.getLocation().setDirection(caster.getLocation().getDirection()));
		
		//ParUtils.parKreisDir(Particles.BUBBLE, caster.getLocation(), 1, 2, 1, caster.getLocation().getDirection(), caster.getLocation().getDirection());
		for (double i = 0;i<charges/10;i++) {
			
			Location l = ParUtils.stepCalcCircle(caster.getEyeLocation().clone().add(0,1,0), 1, caster.getLocation().getDirection(), 3, (i*44/maxcharges*10));
			ParUtils.createParticle(Particles.BUBBLE, l.clone().add(0,-1,0), 0, 0, 0, 5, 0);
		}
		
		//
		//g1.teleport(l1);
		//g2.teleport(l2);
		
		//spawnEntity(EntityType.GUARDIAN,l1,1);
		//spawnEntity(EntityType.GUARDIAN,l2,1);
		//ParUtils.createParticle(Particles.ANGRY_VILLAGER, l1, 0, 0, 0, 1, 1);
		//ParUtils.createParticle(Particles.ANGRY_VILLAGER, l2, 0, 0, 0, 1, 1);
		if (caster.isSneaking() && charges > 0) {
			
			charges--;
			float mult = -0.5F;
			if (g1.isDead() || g2.isDead()) {
				mult/=2;
			}
			caster.setVelocity(caster.getLocation().getDirection().multiply(mult));
			if (!g1.isDead()) {
				GuardianLaser gl = new GuardianLaser(caster, g1.getLocation(), g1.getLocation().getDirection(), refined, name);
				gl.addNoTarget(g1);
				gl.addNoTarget(g2);
			}
			
			if (!g2.isDead()) {
				GuardianLaser gl = new GuardianLaser(caster, g2.getLocation(), g2.getLocation().getDirection(), refined, name);
				gl.addNoTarget(g1);
				gl.addNoTarget(g2);
			}
		}
		if ((g1.isDead() && g2.isDead()) || caster.getGameMode() == GameMode.ADVENTURE) {
			
			
			dead = true;
		}
		if (silenced.contains(caster)) {
			dead = true;
		}
		if (charges <= 0) {
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
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		if (g1 != null)
			g1.remove();
		if (g2 != null)
			g2.remove();
	}

}
