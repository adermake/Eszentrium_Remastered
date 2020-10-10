package spells.spells;

import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Bubble;
import spells.stagespells.BubbleOld;

public class Blasensturm extends Spell{

	int rec = 65;
	public Blasensturm() {
		name = "ßcBlasensturm";
		steprange = 30;
		speed =2;
		
		hitboxSize = 1;
		cooldown = 20*45;
		hitSpell = true;
		
		
		setLore("ß7Schieﬂt eine Menge Blasen, die nach kurzer Zeit#ß7den naheliegendsten Gegner verfolgen. Getroffene#ß7Gegner werden weggeschleudert.");
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
	
		for (int i = 0;i < 30; i++) {
			new Bubble(caster.getEyeLocation(), caster, name);
		}
		
	}

	@Override
	public void move() {
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
