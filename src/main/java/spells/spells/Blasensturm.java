package spells.spells;

import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Bubble;
import spells.stagespells.BubbleOld;

public class Blasensturm extends Spell{

	int rec = 65;
	public Blasensturm() {
		name = "§eBlasensturm";
		steprange = 20 * 4;
		speed =2;
		
		hitboxSize = 1;
		cooldown = 20*40;
		hitSpell = true;
		
		setBetterLore("§7Schießt eine Menge Blasen, die nach kurzer Zeit#§7den naheliegendsten Gegner verfolgen. Getroffene#§7Gegner werden weggeschleudert.");
		setLore("§7Schießt eine Menge Blasen, die nach kurzer Zeit#§7den naheliegendsten Gegner verfolgen. Getroffene#§7Gegner werden weggeschleudert.");
	}
	
	
	

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
			
	}

	@Override
	public void cast() {
		
	}

	@Override
	public void launch() {
	
		if (!refined) {
			
		
		for (int i = 0;i < 30; i++) {
			new Bubble(caster.getEyeLocation(), caster, name);
		}
		}
	}

	@Override
	public void move() {
		if (refined) {
			new Bubble(caster.getEyeLocation().add(0,-0.3,0), caster, name);
		}
		
	}

	@Override
	public void display() {
		
		
	}

	@Override
	public void onPlayerHit(Player p) {
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
	}
	
	@Override
	public void onDeath() {
		
		
	}
	

}
