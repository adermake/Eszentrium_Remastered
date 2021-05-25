package spells.spells;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.KaminchenEntity;

public class Kaminchen extends Spell {

	
	public Kaminchen() {
		cooldown = 20 * 25;
		name = "§6Kaminchen";
		hitSpell = true;
		addSpellType(SpellType.DAMAGE);
		setLore("§7Wirft ein Kaninchen in Blickrichtung,#§7das auf dem Boden stehen bleibt und bei#§7Gegnerkontakt explodiert.# #§eF:§7 Verärgert#§7die Kaminchen, wodurch sie den#§7naheliegendsten Gegner verfolgen.");
		setBetterLore("§7Wirft 3 Kaninchen in Blickrichtung,#§7die auf dem Boden stehen bleiben und bei#§7Gegnerkontakt explodieren.# #§eF:§7 Verärgert#§7die Kaminchen, wodurch sie den#§7naheliegendsten Gegner verfolgen.");
	}
	
	
	@Override
	public void setUp() {
		
		
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
		if (refined) {
			Location dirLoc = caster.getLocation();
			dirLoc.setYaw(dirLoc.getYaw()+45);
			new KaminchenEntity(caster,dirLoc.getDirection(), name);
			dirLoc.setYaw(dirLoc.getYaw()-90);
			new KaminchenEntity(caster,dirLoc.getDirection(), name);
		}
		else {
			new KaminchenEntity(caster,caster.getLocation().getDirection(), name);
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
