package spells.spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.SchockLaser;

public class Schock extends Spell {

	
	public Schock() {
		cooldown = 20 * 40;
		name = "�cSchock";
		//casttime =  3;
		aim = null;
		steprange = 3;
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("�7Schie�t Blitze in Blickrichtung, die#�7an getroffenen Gegnern Schaden verursachen.#�7Der Schaden der Blitze steigt, wenn der#�7Zauber aus gro�er H�he ausgef�hrt wird.##�7#�eShift:�7 Solange diese Taste gedr�ckt bleibt,#�7weiten sich die Blitze aus, um mehr Fl�che#�7zu treffen auf Kosten der Genauigkeit.");
		setBetterLore("�7Schie�t Blitze aus gro�er H�he auf den#�7anvisierten Block und verursacht Schaden an#�7getroffenen Gegnern. # #�eShift:�7 Solange#�7diese Taste gedr�ckt bleibt, weiten sich#�7die Blitze aus, um mehr Fl�che zu treffen auf#�7Kosten der Genauigkeit.");
		casttime = 20 * 6;
		
	}
	double height = 0;
	double castheight = 0;
	Location aim;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		/*
		if (refined) {
			
			
			aim = block(caster);
			if (aim == null) {
				refund = true;
				dead = true;
			}
		}
		*/
		
		//ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 3, 1, 0.1f, randVector());
		//ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 3, 1, 0.1f, randVector());
		castheight = caster.getLocation().getY();
		playGlobalSound(Sound.ENTITY_BLAZE_DEATH, 5, 0.8F);
		
	}

	@Override
	public void cast() {
		
		float c = cast;
		float ct = casttime;
		playSound(Sound.BLOCK_SHROOMLIGHT_HIT,caster.getLocation(),4,2*(c/ct));
		// TODO Auto-generated method stub
		Location l1 =  caster.getLocation();
		l1.setY(castheight);
		ParUtils.parLineRedstone(caster.getLocation(),l1,Color.GRAY , 1F, 0.5D);
		//ParUtils.createParticle(Particles.END_ROD, caster.getLocation(), 0, -1, 0, 0,1);
		ParUtils.createFlyingParticle(Particles.END_ROD, caster.getLocation(), 1, 1, 1,1, 1, new Vector(0,-1,0));
		if (swap() ) {
			cast = casttime;
		}
	
	}

	@Override
	public void launch() {
		
		// TODO Auto-generated method stub
		
		playSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER,caster.getLocation(),4,0.2F);
		ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
		ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
		ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
		ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
		ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
	}

	@Override
	public void move() {
		
		height = caster.getLocation().getY()-castheight;
		if (height < 0) 
			height = 0;
		if (refined) {
			if (aim == null)
				return;
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined,height);
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined,height);
			new SchockLaser(caster.getLocation().add(0,100,0),aim.clone(),caster,name,refined,height);
			
		}
		else {
			new SchockLaser(caster,name,refined,height);
			new SchockLaser(caster,name,refined,height);
			new SchockLaser(caster,name,refined,height);
		}
		
		playSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER,caster.getLocation(),8F,2f);
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
