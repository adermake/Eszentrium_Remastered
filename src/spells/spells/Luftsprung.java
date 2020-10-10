package spells.spells;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Luftsprung extends Spell {

	
	public Luftsprung() {
		steprange = 40;
		name = "§bLuftsprung";
		hitPlayer = false;
		hitEntity = false;
		hitSpell = false;
		cooldown = 20 * 22;
		
		addSpellType(SpellType.MOBILITY);
		setLore("§7Der Spieler katapultiert sich selbst#§7in Blickrichtung.");
		setBetterLore("§7Der Spieler katapultiert sich selbst in#§7Blickrichtung.# #§eShift:§7 Der Spieler#§7springt die selbe Distanz rückwärts.");
		
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
		
	}

	boolean reversed = false;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (step<10) {
			
		
		if (step%3 == 0) {
			ParUtils.parKreisDot(Particles.CLOUD, caster.getLocation(), 1,0, 0.1, caster.getLocation().getDirection());
			
			if (reversed) {
				caster.setVelocity(caster.getLocation().getDirection().multiply(-2));
			}
			else {
				caster.setVelocity(caster.getLocation().getDirection().multiply(2));
			}
			playSound( Sound.ENTITY_WITHER_SHOOT,caster.getLocation(), 1, 2);
			
		}
		}else {
			if (refined && !reversed) {
				if (caster.isSneaking()) {
					reversed = true;
					step = 0;
					steprange = 10;
				}
			}
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
