package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Explosion;

public class Delfintorpedo extends Spell {

	Dolphin ent;
	public Delfintorpedo() {
		
		name = "§6Delfintorpedo";
		cooldown = 20 * 32;
		
		hitboxSize = 1;
		hitSpell = true;
		hitPlayer = true;
		hitEntity = true;
		steprange = 80;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		setLore("§7Schießt einen Delfin in Blickrichtung, der bei#§7Blockkontakt explodiert und getroffenen Gegner Schaden#§7zufügt und sie wegschleudert.Der Delfin kann selbst#§7nach der Ausführung noch gesteuert werden.");
		setBetterLore("§7Schießt einen Delfin in Blickrichtung, der bei#§7Blockkontakt explodiert und getroffenen Gegner Schaden#§7zufügt und sie wegschleudert.Der Delfin kann selbst#§7nach der Ausführung noch gesteuert werden.");
	}
	
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		ent = (Dolphin) spawnEntity(EntityType.DOLPHIN);
		ent.setInvulnerable(true);
		ent.setGravity(true);
		
		noTargetEntitys.add(ent);
		SoundUtils.playSound(Sound.ENTITY_FOX_AGGRO, loc,1,10);
		//SoundUtils.playSound(Sound.ENTITY_FOX_SCREECH, loc,0.5F,10);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	
	double speed = 0.3;
	int i = 0;
	@Override
	public void move() {
		i++;
		// TODO Auto-generated method stub
		if (i> 3) {
			SoundUtils.playSound(Sound.ENTITY_DOLPHIN_SWIM, loc,1,0.5F);
			
		}
	
		speed += 0.03;
		if (speed > 2) {
			speed = 2;
		}
		
		
		loc.add(caster.getLocation().getDirection().multiply(speed));
		loc.setDirection(caster.getLocation().getDirection());
		ent.teleport(loc);
		//ent.setVelocity(caster.getLocation().getDirection().multiply(2));
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		if (refined)
		ParUtils.createFlyingParticle(Particle.CLOUD, loc.clone().subtract(loc.getDirection().multiply(2)), 0.3, 0.3, 0.3, 3, speed, loc.getDirection().multiply(-1));
		ParUtils.createFlyingParticle(Particle.WATER_WAKE, loc.clone().subtract(loc.getDirection().multiply(2)), 0.3, 0.3, 0.3, 11, speed, loc.getDirection().multiply(-1));
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		dead = true;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		SoundUtils.playSound(Sound.ENTITY_DOLPHIN_DEATH, loc,1,4);
		if (!ent.isDead())
		ent.remove();
		if (refined) {
			new Explosion(6, 10, 5, 0, caster, loc, name);
		}
		else {
			new Explosion(6, 6, 4, 0, caster, loc, name);
		}
		
	}

}
