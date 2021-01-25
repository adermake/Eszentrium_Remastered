package spells.spells;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class SkyWalker extends Spell {
	ArrayList<Location> blocks = new ArrayList<Location>();
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
		
	}

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
	}
	
	public void phantomBlock(Location l1) {
		//if (blocks.contains(l1))
		//	return;
		blocks.add(l1);
		caster.sendBlockChange(l1,Material.BARRIER, (byte)0);
		
		//ParUtils.parCubeEdgeFly(Particles.END_ROD, l1.getBlock().getLocation().add(0.5,0.5,0.5), 1, 1, 0.1F);
		ParUtils.parCube(Particles.END_ROD, l1.getBlock().getLocation().add(0.5,0.5,0.5),1, 5);
		
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
	}

}
