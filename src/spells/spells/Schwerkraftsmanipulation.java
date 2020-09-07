package spells.spells;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.Actionbar;
import esze.utils.ParUtils;
import spells.spellcore.Cooldowns;
import spells.spellcore.Spell;

public class Schwerkraftsmanipulation extends Spell {

	
	public static ArrayList<Player> gravityMani = new ArrayList<Player>();
	public Schwerkraftsmanipulation() {
		name = "§eSchwerkraftsmanipulation";
		cooldown = 20 * 30;
		steprange = 60;
	}
	
	Player target;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		target = pointEntity(caster);
		if (target == null) {
			refund = true;
			dead = true;
		}
		else {
			ParUtils.parKreisSolidRedstone(Color.GRAY, 2, target.getLocation(), 4, 0, 1, new Vector(0,1,0));
			playSound(Sound.BLOCK_BELL_USE,target.getLocation(),16,2F);
			playSound(Sound.BLOCK_BELL_RESONATE,target.getLocation(),6,1.6F);
			if (refined) {
				gravityMani.add(target);
			}
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
		if (target == null) {
			dead =true;
			return;
		}
		tagPlayer(target);
		target.setVelocity(target.getVelocity().setY(-6));
	}

	int count = 0;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		count++;
		if (count >5) {
			
			
			ParUtils.parKreisSolidRedstone(Color.GRAY, 2, target.getLocation(), 4, 0, 1, new Vector(0,1,0));
			count = 0;
		}
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
		if (refined) {
			gravityMani.remove(target);
		}
	}

	

}
