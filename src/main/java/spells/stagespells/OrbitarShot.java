package spells.stagespells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class OrbitarShot extends Spell {

	
	Entity a;
	public OrbitarShot(Player p,Location l,String name,boolean refined) {
		this.refined = refined;
		cooldown = 20 * 5;
		
		this.name = name;
		
		
		
		noTargetEntitys.add(a);
		hitboxSize = 2;
		multihit = true;
		caster = p;
		speed = 6;
		steprange = 100;
		castSpell(caster, name);
		if (refined)
			steprange = 200;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		
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
		playSound(Sound.ENTITY_WITHER_SHOOT,caster.getLocation(),2,1);
		ParUtils.createRedstoneParticle(loc, 0.6F, 0.6F, 0.6F, 15, Color.GREEN, 2F);
		ParUtils.createFlyingParticle(Particle.CLOUD, caster.getEyeLocation(), 0.5F, 0.5F, 0.5F, 15, -1, caster.getLocation().getDirection());
		ParUtils.parKreisDot(Particle.CLOUD, caster.getLocation(), 1, 0, 1, caster.getLocation().getDirection().multiply(-1));
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		loc.add(loc.getDirection());
	}

	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		if (step % 10 == 0)
		ParUtils.createRedstoneParticle(loc, 0.1F, 0.1F, 0.1F, 5, Color.GREEN, 0.9F);
		if (a != null)
		a.remove();
		if (!loc.getBlock().getType().isSolid()) {
			a = caster.getWorld().spawnFallingBlock(loc, Material.CACTUS,(byte)  0);
		}
		
		//ParUtils.createParticle(Particle.FLAME, loc, 0, 0, 0, 1, 1);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
		
		ParUtils.createParticle(Particle.EXPLOSION_LARGE, loc, 0, 0, 0, 1, 0);
		playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,loc,5,1);
		if (refined) {
			damage(p,4,caster);
			p.setVelocity(loc.getDirection().multiply(3));
		}
		else {
			damage(p,2,caster);
			p.setVelocity(loc.getDirection().multiply(2));
		}
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		
		
		if (refined) {
			damage(ent,4,caster);
			ent.setVelocity(loc.getDirection().multiply(23));
		}
		else {
			damage(ent,2,caster);
			ent.setVelocity(loc.getDirection().multiply(2));
		}
		ParUtils.createParticle(Particle.EXPLOSION_LARGE, loc, 0, 0, 0, 1, 0);
		playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,loc,5,1);
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
		ParUtils.createRedstoneParticle(loc, 0.1F, 0.1F, 0.1F, 5, Color.GREEN, 2);
		a.remove();
	}

}
