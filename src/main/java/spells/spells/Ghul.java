package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Ghul extends Spell {

	public Ghul() {
		cooldown = 20*40;
		name = "§6Ghul";
		speed = 3;
		steprange =342;
		hitPlayer = true;
		hitSpell = true;
		multihit = true;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("§7Beschwört eine Kreatur, die durch die#§7Gegend springt und nahe Gegner verfolgt.#§7Wird ein Gegner getroffen, wird dieser#§7weggeschleudert.");
		
		setBetterLore("§7Beschwört drei Kreaturen, die durch die#§7Gegend springen und nahe Gegner verfolgen.#§7Wird ein Gegner getroffen, wird dieser#§7weggeschleudert.");
	}
	public Ghul(Player p,Vector v) {
		
		super();
		name = "§cGhul";
		steprange = 150;
		hitboxSize = 1.5;
		powerlevel = 60;
		speed = 3;
		caster = p;
		cooldown = 20 * 18;
		castSpell(caster, name);
		vel = v;
		
	}
	Entity w;
	
	@Override
	public void setUp() {
		
		
		if (refined) {
			Location l1 = caster.getLocation();
			l1.setYaw(l1.getYaw()+120);
			Location l2 = caster.getLocation();
			l2.setYaw(l2.getYaw()-120);
			
			
			new Ghul(caster,l1.getDirection());
			new Ghul(caster,l2.getDirection());
		}
		
		if (vel == null)
		vel = caster.getLocation().getDirection();
		// TODO Auto-generated method stub
		if (refined) {
			steprange *=8;
			speed = 4;
		}
		playSound(Sound.ENTITY_WITHER_SHOOT,loc,5,0.5F);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	Vector vel = null;
	@Override
	public void move() {
		
		double ysave = -vel.length();
		
		do{
			ysave+=0.2;
			
		collideWithBlock();	
		if (w != null)
		w.remove();
		
		LivingEntity aim = (LivingEntity) getNearestEntity(caster, loc.clone(), 20);
		if (aim != null) {
			
			Vector to = aim.getLocation().toVector().subtract(loc.toVector()).normalize();
			
			
			vel.add(to.multiply(0.005));
			
		}
		
		w = spawnEntity(EntityType.WITHER_SKULL);
		
		w.setInvulnerable(true);
		w.teleport(loc);
		loc.add(vel.normalize().multiply(0.2));
		
		vel.add(new Vector(0,-0.009,0));
		
		
		// AIM#
		
		
		
		//
		
		
		
		} while (ysave <= 0.2);
	}

	int t = 0;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		//ParUtils.createFlyingParticle(Particle.BUBBLE_POP, loc,0, 0, 0, 1, 2, loc.getDirection().multiply(-1));
		ParUtils.createParticle(Particle.SMOKE_LARGE, loc, 0, 0, 0, 1, 0);
		
		
		
	}

	@Override
	public void onPlayerHit(Player p) {
		ParUtils.createParticle(Particle.EXPLOSION_LARGE, loc, 0, 0, 0,1, 1);
		playSound(Sound.ENTITY_GENERIC_EXPLODE,loc,13,2);
		
			p.setVelocity(loc.getDirection().multiply(5));
		
		
		damage(p, 4,caster);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		playSound(Sound.ENTITY_GENERIC_EXPLODE,loc,13,2);
		ParUtils.createParticle(Particle.EXPLOSION_LARGE, loc, 0, 0, 0,1, 1);
		// TODO Auto-generated method stub
		damage(ent, 4,caster);
	
			ent.setVelocity(loc.getDirection().multiply(5));
		
		
		
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
		playSound(Sound.ENTITY_GENERIC_EXPLODE,loc,11,1);
		ParUtils.createParticle(Particle.EXPLOSION_LARGE, loc, 0, 0, 0,1, 1);
		loc.setDirection(vel);
		BlockFace bf = bounce();
		double lvel = vel.length();
		
		vel = loc.getDirection().normalize().multiply(lvel);
		if (bf != BlockFace.DOWN) {
			vel = vel.add(new Vector(0,0.2,0));
		}
		
	}


	@Override
	public void onDeath() {
		if (w != null)
		w.remove();
		
	}


	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}



}
