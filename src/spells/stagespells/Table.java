package spells.stagespells;

import java.util.ArrayList;
import java.util.List;


import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;

public class Table extends Spell {

	List<String> table;
	ArrayList<Material> blockTypes = new ArrayList<Material>();  
	
	public Table(Player c,List<String> ls) {
		table = ls;
		
		
		blockTypes.add(Material.RED_WOOL);
		blockTypes.add(Material.ORANGE_WOOL);
		blockTypes.add(Material.YELLOW_WOOL);
		blockTypes.add(Material.GREEN_WOOL);
		blockTypes.add(Material.CYAN_WOOL);
		blockTypes.add(Material.BLUE_WOOL);
		blockTypes.add(Material.LIGHT_BLUE_WOOL);
		blockTypes.add(Material.PURPLE_WOOL);	
		blockTypes.add(Material.MAGENTA_WOOL);	
		blockTypes.add(Material.LIME_WOOL);
		blockTypes.add(Material.PINK_WOOL);
		blockTypes.add(Material.WHITE_WOOL);
		blockTypes.add(Material.BROWN_WOOL);
		blockTypes.add(Material.GRAY_WOOL);
		blockTypes.add(Material.LIGHT_GRAY_WOOL);
		blockTypes.add(Material.BLACK_WOOL);
		caster = c;
		castSpell(c,"§eTable");
	}
	@Override
	public void setUp() {
		playGlobalSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
		// TODO Auto-generated method stub
	
		
		Vector ruler = loc.getDirection().crossProduct(new Vector(0,1,0)).normalize();
		Vector offset = ruler.clone().multiply(0.9+-1.8*((double)table.size())/2D);
		int in = 0;
		for (String s : table) {
			int index = 0;
			for (String a : s.split("\\n")) {
				
				if (index == 0) {
					
				}
				else {
					
					FallingBlock fb = spawnFallingBlock(loc.clone(), blockTypes.get(in));
					
					ArmorStand ar = createArmorStand(loc);
					ArmorStand holder = createArmorStand(loc.clone().add(offset).add(0,-index*1.8F,0).add(ruler.clone().multiply(in*1.8F)));
					holder.setGravity(false);
					holder.addPassenger(fb);
					holder.addPassenger(ar);
					ar.setCustomName("§e"+a);
					ar.setSmall(true);
					ar.setCustomNameVisible(true);
					
				}
				
				index++;
			}
			in++;
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

	@Override
	public void move() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.CLOUD, loc, 0, 0, 0, 1, 0);
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
		
	}

}
