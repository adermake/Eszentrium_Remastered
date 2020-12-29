package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.MaterialMapColor;
import spells.spellcore.Spell;

public class HealingAura extends Spell {

	Location overrideLoc;
	double healAmount = 0;
	boolean antiheal = false;
	public HealingAura(String name,Player caster,Location loca,double heal,double range,boolean antiheal) {
		overrideLoc = loca.clone();
		healAmount = heal;
		this.name = name;
		this.antiheal = antiheal;
		hitboxSize = range;
		hitEntity = true;
		hitPlayer = true;
		canHitSelf = true;
		steprange = 1;
		castSpell(caster, name);
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = overrideLoc;
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
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (!antiheal || p == caster) {
			
		if (p == caster) {
		
		
		double health = p.getHealth();
		health += healAmount;
		if (health > p.getMaxHealth()) {
			health = p.getMaxHealth();
		}
		p.setHealth(health);
		}	
		
		}
		else {
			damage(p,healAmount, caster);
		}
	}

	@Override
	public void onEntityHit(LivingEntity p) {
		// TODO Auto-generated method stub
		if (!antiheal) {
			if (p instanceof Player) {
				heal(p, healAmount,caster);
			}
		
	
		}
		else {
			
			damage(p, healAmount, caster);
			
		}
	
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
