package spells.spells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.SchockLaser;

public class Schock extends Spell {

	
	public Schock() {
		cooldown = 20 * 40;
		name = "§eSchock";
		casttime =  3;
		aim = null;
		
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("§7Schießt Blitze in Blickrichtung, die#§7an getroffenen Gegnern Schaden verursachen.#§7Der Schaden der Blitze steigt, wenn der#§7Zauber aus großer Höhe ausgeführt wird.##§7#§eShift:§7 Solange diese Taste gedrückt bleibt,#§7weiten sich die Blitze aus, um mehr Fläche#§7zu treffen auf Kosten der Genauigkeit.");
		setBetterLore("§7Schießt Blitze aus großer Höhe auf den#§7anvisierten Block und verursacht Schaden an#§7getroffenen Gegnern. # #§eShift:§7 Solange#§7diese Taste gedrückt bleibt, weiten sich#§7die Blitze aus, um mehr Fläche zu treffen auf#§7Kosten der Genauigkeit.");
		
		if (refined) {
			
			
			aim = block(caster);
			if (aim == null) {
				refund = true;
			}
			}
	}
	Location aim;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		if (refined) {
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined);
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined);
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined);
			
		}
		else {
			new SchockLaser(caster,name,refined);
			new SchockLaser(caster,name,refined);
			new SchockLaser(caster,name,refined);
		}
		
		playSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER,caster.getLocation(),8F,2f);
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		dead = true;
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
