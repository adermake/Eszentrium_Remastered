package spells.spells;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.RepulsionDirectional;

public class Schallbrecher extends Spell {

	public Schallbrecher() {
		name = "§bSchallbrecher";
		cooldown = 20*18;
		hitEntity = false;
		hitPlayer = true;
		hitSpell = true;
		hitBlock = false;
		hitboxSize = 2;
		steprange = 15;
		multihit = true;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.SELFCAST);
		setLore("§7Gleitet in Blickrichtung voran und#§7zieht alle Gegner in der Nähe mit. Nach kurzer#§7Zeit werden alle Gegner in Blickrichtung#§7weggeschleudert.# #§eShift:§7 Am Ende des#§7Zaubers springt der Anwender in die entgegen#§7der Blickrichtung.");
		setBetterLore("§7Gleitet in Blickrichtung voran und#§7zieht alle Gegner in der Nähe mit. Nach kurzer#§7Zeit werden alle Gegner in Blickrichtung#§7weggeschleudert.# #§eShift:§7 Am Ende des#§7Zaubers springt der Anwender in die entgegen#§7der Blickrichtung.");
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_PHANTOM_FLAP, loc, 20, 0);
		playSound(Sound.ENTITY_WITHER_SHOOT, loc, 0.9, 0.6);
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		//
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		Location locate = caster.getEyeLocation();
		locate.setY(locate.getY()-0.5);
		Location midloc = caster.getLocation();
		midloc.setY(midloc.getY()+0.2);
		Vector direction = loc.getDirection();
		
		caster.setVelocity(direction);
		ParUtils.parKreisDot(Particles.CLOUD, locate, 0.5, 0.5, 0.2, direction.multiply(-1));
		
		ParUtils.createParticle(Particles.FLAME, midloc, 0.1, 0.1, 0.1, 5, 0);
		Vector dir = loc.getDirection();
		loc = caster.getLocation();
		loc.setDirection(dir);
		
		
		
	}

	@Override
	public void display() {
		
		
	}

	

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
		tagPlayer(p);
		p.setVelocity(caster.getVelocity());
		playSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP, loc, 1, 0.7);
		
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		
		
		ent.setVelocity(caster.getVelocity());
		playSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP, loc, 1, 0.7);
		
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
		Vector direction = loc.getDirection();
		direction.setY(0);
		caster.setVelocity(direction);
		
		Location locate = caster.getEyeLocation();
		locate.setY(locate.getY()-0.5);
		
		
		ParUtils.parKreisDot(Particles.CLOUD, locate, 0.3, 0.0, 0.2, direction.multiply(-1));
		
		
		ParUtils.parKreisDot(Particles.CLOUD, caster.getLocation(), 3, 0, 3, caster.getVelocity());
		if (refined) {
			
			new ExplosionDamage(5, 5, caster, caster.getLocation(), name);
			new RepulsionDirectional(5, 7, caster, caster.getLocation(), caster.getVelocity(), name);
			ParUtils.parKreisDirSolid(Particles.CLOUD, caster.getLocation(), 3, 0, 1, caster.getVelocity(), caster.getVelocity());
			
			ParUtils.parKreisDot(Particles.CLOUD, caster.getLocation(), 3, 0, 2, caster.getVelocity());
			ParUtils.parKreisDot(Particles.CLOUD, caster.getLocation(), 3, 0, 1, caster.getVelocity());
			playSound(Sound.ENTITY_WITCH_DEATH,caster.getLocation(),5,0.5F);
		}
		else {
			new ExplosionDamage(5, 3, caster, caster.getLocation(), name);
			new RepulsionDirectional(5, 3, caster, caster.getLocation(), caster.getVelocity(), name);
		}
		playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, loc, 1, 1);
		
		if (caster.isSneaking()) {
			caster.setVelocity(caster.getLocation().getDirection().multiply(-1.7F));
		}
		
	}

	
	
}
