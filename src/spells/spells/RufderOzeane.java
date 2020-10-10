package spells.spells;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.RufDerOzeaneFish;
import spells.stagespells.RufDerOzeaneRefined;

public class RufderOzeane extends Spell{
	
	public RufderOzeane() {
		name = "§6Ruf der Ozeane";
		cooldown = 20 * 53;
		steprange = 30;
		speed = 0.5;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("§7Feuert Tintenfische in Blickrichtung#§7ab, die getroffene Gegner zurückwerfen. ");
		setBetterLore("§7Feuert einen einzelnen Tintenfisch in#§7Blickrichtung ab. Trifft er einen Gegner,#§7wirft er diesen zurück und kehrt zum Anwender#§7zurück.Sobald er bei diesem ankommt, fliegt#§7er wieder in Blickrichtung los.");
	}
	@Override
	public void setUp() {
		
		if (refined) {
			new RufDerOzeaneRefined(caster,name);
			playSound(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS,loc,1,1);
		}
		
		
		
	}

	@Override
	public void cast() {
		
		
		
		
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (!refined) {
			playSound(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS,loc,1,1);
			new RufDerOzeaneFish(caster,name);
		}
		
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
