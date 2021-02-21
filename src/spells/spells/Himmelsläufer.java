package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Himmelsläufer extends Spell {
	ArrayList<Location> blocks = new ArrayList<Location>();
	
	
	public Himmelsläufer() {
		
		name = "§bHimmelsläufer";
		steprange = 20 * 8;
		cooldown = 20* 30;
		
		addSpellType(SpellType.MOBILITY);
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		if (refined) {
			steprange = 20 * 10;
			caster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20* 10, 2));
			caster.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20* 10, 2));
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
	int length = 0;
	@Override
	public void move() {
		//caster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100,20,true));
		// TODO Auto-generated method stub
		if (caster.getLocation().add(0,-1,0).getBlock().getType() == Material.AIR) {
			phantomBlock(caster.getLocation().add(0,-1,0));
			
		}
		ArrayList<Location> removeLater = new ArrayList<Location>();
		for (Location l1 : blocks) {
			if (l1.distance(caster.getLocation() )> 3) {
				caster.sendBlockChange(l1, l1.getBlock().getBlockData());
				removeLater.add(l1);
			}
		}
		
		for (Location l1 : removeLater) {
			blocks.remove(l1);
		}
		
		if (swap()) {
			dead = true;
		}
		length++;
	}
	
	public void phantomBlock(Location l1) {
		//if (blocks.contains(l1))
		//	return;
		if (!blocks.contains(l1.getBlock().getLocation())) {
			double lerpFactor = (double)length/(double)steprange;
			//Bukkit.broadcastMessage(""+lerpFactor);
			playSound(Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO,l1,5,2F*lerpFactor);
		}
		blocks.add(l1.getBlock().getLocation());
		caster.sendBlockChange(l1,Material.BARRIER, (byte)0);
		
		//ParUtils.parCubeEdgeFly(Particles.END_ROD, l1.getBlock().getLocation().add(0.5,0.5,0.5), 1, 1, 0.1F);
		ParUtils.parCube(Particles.END_ROD, l1.getBlock().getLocation().add(0.5,0.5,0.5),1, 5);
		if (refined) {
			ParUtils.createParticle(Particles.CLOUD, l1.getBlock().getLocation().add(0.5,0.5,0.5), 0.1, 0.1, 0.1, 10, 0);
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
		for (Location l1 : blocks) {
			
			
			caster.sendBlockChange(l1, l1.getBlock().getBlockData());
			
		}
		if (refined) {
			caster.removePotionEffect(PotionEffectType.SPEED);
			caster.removePotionEffect(PotionEffectType.JUMP);
		}
		int refunde = steprange*2 - length*2;
		//Bukkit.broadcastMessage("X"+refunde/20);
		reduceCooldown(refunde);
	}

}
