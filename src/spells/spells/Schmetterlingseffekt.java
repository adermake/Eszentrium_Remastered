package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class Schmetterlingseffekt extends Spell {
	
	Player target;
	Location castLoc;
	
	public Schmetterlingseffekt() {
		
		name = "§eSchmetterlingseffekt";
		cooldown = 20*3;
		steprange = 20*3;
		casttime = 80;
		
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		//caster.setVelocity(caster.getVelocity().setY(1.5f));
		
		
		
		//target = pointEntity(caster,300);
		//if (target == null) {
		//	refund = true;
		//	dead = true;
		//}
		
	}
	
	@Override
	public void cast() {
		// TODO Auto-generated method stub

		if (cast == 20) {
			
		}
		castLoc = caster.getLocation().add(0,1,0);
		
		
		if (cast>20) {
			
			//caster.setVelocity(caster.getVelocity().setY(0));
			
			Location l1 =  ParUtils.stepCalcCircle(castLoc.clone(), 5, new Vector(castLoc.getDirection().getX(), 0f ,castLoc.getDirection().getZ()), 1, 38);	
			Location l2 =  ParUtils.stepCalcCircle(castLoc.clone(), 5, new Vector(castLoc.getDirection().getX(), 0f ,castLoc.getDirection().getZ()), 1, 1);
			Location l3 =  ParUtils.stepCalcCircle(castLoc.clone(), 5, new Vector(castLoc.getDirection().getX(), 0f ,castLoc.getDirection().getZ()), 1, 5);
			Location l4 =  ParUtils.stepCalcCircle(castLoc.clone(), 5, new Vector(castLoc.getDirection().getX(), 0f ,castLoc.getDirection().getZ()), 1, 8);
			
			ParUtils.parLineRedstone(castLoc.clone(), l1, Color.RED, 1, 0.2);
			ParUtils.parLineRedstone(castLoc.clone(), l2, Color.YELLOW, 1, 0.2);
			ParUtils.parLineRedstone(castLoc.clone(), l3, Color.GREEN, 1, 0.2);
			ParUtils.parLineRedstone(castLoc.clone(), l4, Color.AQUA, 1, 0.2);

			
		}
	}
	
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
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
