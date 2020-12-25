package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.FirePiece;

public class Flammenwand extends Spell {

	
	public Flammenwand()
	{
		name = "§6Flammenwand";
		cooldown = 20 * 40;
		casttime = 20 * 5;
		speed = 3;
		
		steprange = 50;
	
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.MULTIHIT);
		
		setLore("§7Setzt einen Startpunkt. Nach kurzer#§7Zeit entsteht an dieser Stelle eine#§7Flammensäule, die nahen Gegnern wiederholt Schaden#§7zufügt.# #§eF:§7 Setzt einen Endpunkt.#§7Zwischen dem Startpunkt und dem Endpunkt#§7entsteht eine Flammenwand, die nahen Gegnern#§7wiederholt Schaden zufügt.");
		setBetterLore("Setzt einen Startpunkt. Nach kurzer Zeit entsteht an dieser Stelle eine Flammensäule, die nahen Gegnern wiederholt Schaden zufügt und sie zurückdrängt.F: Setzt einen Endpunkt. Zwischen dem Startpunkt und dem Endpunkt entsteht eine Flammenwand, die nahen Gegnern wiederholt Schaden zufügt und sie zurückdrängt.");
	}
	Location l1;
	Location l2;
	Location l3;
	public void setUp() {
		// TODO Auto-generated method stub
		l1 = block(caster);
		if (l1 == null) {
			refund = true;
			dead = true;
		}
		
		if (refined)  {
			steprange = 300;
			
		}
			
		
	}
	Vector v;
	@Override
	public void cast() {
		// TODO Auto-generated method stub
		if (l2 != null && !refined) {
			cast = casttime+1;
			v = l2.toVector().subtract(l1.toVector());
		}
		if (l3 != null && refined) {
			cast = casttime+1;
			v = l2.toVector().subtract(l1.toVector());
		}
		if (swap()) {
			if (l2 == null) {
				l2 = block(caster);
				
			}
			else {
				
				l3 = block(caster);
			}
			
			clearswap();
		}
	}

	Location start;
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
		if (l2== null) {
			
			l2 = l1.clone();
			v = caster.getLocation().getDirection();
		}
		if (l3 == null) {
			l3 = l2.clone();
			v = l2.toVector().subtract(l1.toVector()).normalize();
		}
		start = l1.clone();
	}
	boolean lastRound = false;
	@Override
	public void move() {
		
		// TODO Auto-generated method stub
		l1.add(v.normalize().multiply(1));
		if (l1.distance(l2)< 2) {
			if (!refined) {
				dead = true;
			}
			else {
				v = l3.toVector().subtract(l2.toVector());
				
			}
			
		}
		
		
		if (refined && l1.distance(l3)< 2 ) { 
			lastRound = true;
			v = start.toVector().subtract(l3.toVector());
		}
		if (lastRound && l1.distance(start )< 2) {
			dead = true;
		}
		
		
		Location bLoc = l1.clone();
		int i = 0;
		while (bLoc.getBlock().getType().isSolid()) {
			bLoc.add(0,1,0);
			i++;
			
			if (i > 100) {
				break;
			}
		}
		
		new FirePiece(bLoc.add(0,0,0), caster, name, 20 * 8,refined);
	}

	@Override
	public void display() {
		playSound(Sound.BLOCK_FIRE_AMBIENT,l1,11,0.7F);
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.SMOKE, l1, 1, 1, 1, 5, 0.1F);
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
