package spells.stagespells;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Item;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class RufDerOzeaneFish extends Spell{

	
	LivingEntity ent;
	public RufDerOzeaneFish(Player p, String namae) {
		name = namae;
		hitSpell = true;
		steprange = 30;
		
		hitboxSize = 2;
		castSpell(p,name);
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.PROJECTILE);
		
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		EntityType type = EntityType.SQUID;
		int i = randInt(1,12);
		if (i==1) {
			type = EntityType.TROPICAL_FISH;
		}
		if (i==2) {
			type = EntityType.COD;
		}
		if (i==3) {
			type = EntityType.DOLPHIN;
		}
		if (i==4) {
			type = EntityType.PUFFERFISH;
		}
		if (i==5) {
			type = EntityType.SALMON;
		}
		if (i>=6) {
			type = EntityType.SQUID;
		}
		ent = (LivingEntity) caster.getWorld().spawnEntity(caster.getLocation(), type);
		ent.setInvulnerable(true);
		ent.setCollidable(false);
		
		//ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,  5, 5));
		if (randInt(1, 2) == 1) {
			ent.removePotionEffect(PotionEffectType.INVISIBILITY);

			ent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 100));
		}
		bindEntity(ent);
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
		loc = ent.getLocation();
		ent.setVelocity(caster.getLocation().getDirection().multiply(2));
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
		if (step>2) {
			ParUtils.createBlockcrackParticle(loc, 0.1,0.1,0.1, 2, Material.LIGHT_BLUE_STAINED_GLASS);
			ParUtils.createBlockcrackParticle(loc, 0.1,0.1,0.1, 2, Material.LIGHT_BLUE_CONCRETE_POWDER);
			ParUtils.createParticle(Particles.BUBBLE_COLUMN_UP, loc, 0.4, 0.4, 0.4, 3, 1);
		}
		
		
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p,1,caster);
		doKnockback(p, caster.getLocation(), 1.5);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent,1,caster);
		doKnockback(ent, caster.getLocation(), 1.5);
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
		ent.remove();
	}
	

}
