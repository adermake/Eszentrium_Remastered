package spells.spells;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import esze.utils.ItemStackUtils;
import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import esze.utils.PlayerUtils;
import esze.utils.SoundUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class Archon extends Spell {
	
	public Archon() {
		
			cooldown = 33 * 40;
			name = "§8Archon";
		
			
			hitboxSize = 0;
			speed = 1;
			casttime = 1;
			
		
	}
	Player target;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		target = pointEntity(caster,15);
		
		if (target == null) {
			refund = true;
			dead = true;
		}
		else {
		
			target.setGlowing(true);
			SoundUtils.playSound(Sound.ENTITY_WITHER_HURT, target.getLocation(), 0.6F, 10);
			PlayerUtils.hidePlayer(caster);
			unHittable.add(caster);
			caster.setAllowFlight(true);
			caster.setFlying(true);
			
			target.setMaxHealth(40);
			target.setHealth(40);
		
			new ArchonReflect(caster, "§rArchon Aura");
			new ArchonAura(target, "§rArchon Aura");
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		if (dead)
			return;
		caster.getInventory().clear();
		caster.getInventory().addItem(NBTUtils.setNBT("Archon", target.getName(), ItemStackUtils.createSpell("§rPhasenwechsel")));
		caster.getInventory().addItem(NBTUtils.setNBT("Archon", target.getName(), ItemStackUtils.createSpell("§rSchockwelle")));
		caster.getInventory().addItem(NBTUtils.setNBT("Archon", target.getName(), ItemStackUtils.createSpell("§rDruckwelle")));
		caster.getInventory().addItem(NBTUtils.setNBT("Archon", target.getName(), ItemStackUtils.createSpell("§rBlitzaufstieg")));
		caster.getInventory().addItem(NBTUtils.setNBT("Archon", target.getName(), ItemStackUtils.createSpell("§rBlitzaufstieg")));
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		if (!dead) {
		caster.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 10,true));
		
		// TODO Auto-generated method stub
		if (target.getGameMode() == GameMode.ADVENTURE) {
			caster.setNoDamageTicks(0);
			caster.damage(20); //Who should be the killer
			dead = true;
		}
		
		caster.setNoDamageTicks(20);
		
		if (swap()) {
			caster.setVelocity(caster.getLocation().getDirection().multiply(3));
		}
		
		
		if (caster.getLocation().distance(target.getLocation())>50) {
			
			doPull(caster, target.getLocation(), 1);
			
		}
		
		if (caster.getLocation().distance(target.getLocation())>60) {
			
			caster.teleport(target.getLocation());
			
		}
		
		
		
		
		ParUtils.createParticle(Particles.END_ROD, caster.getLocation(), 0, 0, 0, 1, 0.005F);
		
		
		
		
		
		
		
		
		
		
		
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		ParUtils.dropItemEffectVector(caster.getLocation().add(0,-0.2,0), Material.NETHER_STAR, 1, 1, 0, caster.getLocation().getDirection());
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
		
	}

}
