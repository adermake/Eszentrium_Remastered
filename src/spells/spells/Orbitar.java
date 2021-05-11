package spells.spells;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.OrbitarOrb;

public class Orbitar extends Spell {

	public ArrayList<OrbitarOrb> orb = new ArrayList<OrbitarOrb>();
	public Orbitar() {
		name = "§3Orbitar";
		cooldown = 20 * 37;
		steprange = 20 * 6;
		hitPlayer = false;
		hitEntity = false;
		
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.SELFCAST);
		setLore("§7Beschwört 3 Projektile, die um den#§7Spieler kreisen. Getroffene Gegner werden in#§7die Luft geworfen.# #§eShift:§7 Schießt die#§7Projektile in Blickrichtung. Getroffene#§7Gegner werden zurückgeworfen.");
		setBetterLore("§7Beschwört 9 Projektile, die um den#§7Spieler kreisen. Getroffene Gegner werden in die#§7Luft geworfen.# #§eShift:§7 Schießt die#§7Projektile in Blickrichtung. Getroffene#§7Gegner werden zurückgeworfen.");
	}
	
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
		float c = 3;
		if (refined) {
			c = 9;
		}
		for (int i = 0;i<c;i++) {
			float cstep = (44/c);
			int coffset = (int)(i * (44/c));
			orb.add(new OrbitarOrb(caster,coffset,steprange,name,refined));
		}
		
		//orb.add(new OrbitarOrb(caster,14,steprange,name,refined));
		//orb.add(new OrbitarOrb(caster,28,steprange,name,refined));
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		/*
		if (caster.isSneaking() && refined) {
			for (OrbitarOrb o : orb) {
				o.setSpeed(8);
				
			}
		}
		else {
			for (OrbitarOrb o : orb) {
				o.setSpeed(4);
			}
		}
		*/
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
