package spells.spells;

import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import spells.spellcore.Cooldowns;
import spells.spellcore.Spell;

public class Raumwechsel extends Spell{
	
	public Raumwechsel() {
		name = "§bRaumwechsel";
		cooldown = 20 * 42;
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
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		if (target != null) {
			playSound(Sound.ENTITY_EVOKER_PREPARE_WOLOLO, target.getLocation(), 1, 2);
			playSound(Sound.ENTITY_EVOKER_PREPARE_WOLOLO, target.getLocation(), 1, 2);
			if (refined) {
				doPull(caster, target.getLocation(), 9);
				doPull(target,caster.getLocation(),7);
			}else {
				doPull(caster, target.getLocation(), 6);
				doPull(target,caster.getLocation(),4);
			}
			
			ParUtils.parLineRedstone(target.getLocation(),caster.getLocation(), Color.AQUA, 0.5F, 0.5);
		}
		
		dead = true;
		
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
