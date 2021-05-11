package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.EventCollector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Astralsprung extends Spell{

	public Astralsprung() {
		cooldown = 20*31;
		name = "§bAstralsprung";
		speed = 1;
		hitPlayer = false;
		hitEntity = false;
		hitSpell = false;
		steprange =120;
		addSpellType(SpellType.SELFCAST);
		
	}
	double dist = 0;
	Player target;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		target = pointEntity(caster,50);
		if (target == null) {
			
			
			refund = true;
			dead = true;
			if (refined)
				steprange = 240;
		}
		else {
			playSound(Sound.BLOCK_CONDUIT_DEACTIVATE,caster.getLocation(),5,2F);
			playSound(Sound.BLOCK_CONDUIT_ACTIVATE,caster.getLocation(),5,2F);
			ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 0, 1, 0.3F, randVector());
			ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 0, 1, 0.3F, randVector());
			ParUtils.parKreisDot(Particles.END_ROD, caster.getLocation(), 0, 1, 0.3F, randVector());
			dist = target.getLocation().distance(caster.getLocation());
		}
		
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	int delay = 0;
	@Override
	public void move() {
		if (dead)
			return;
		
		delay--;
		// TODO Auto-generated method stub
		if (swap() && delay <= 0) {
			delay = 20;
			dist = target.getLocation().distance(caster.getLocation());
			while (true) {
				
			
			Vector randVec2 = new Vector(randDouble(-1, 1),0,randDouble(-1, 1)).normalize().multiply(0.88);
			if (caster.isSneaking()) {
				randVec2 = new Vector(randDouble(-1, 1),0,randDouble(-1, 1)).normalize().multiply(0.5);
			}
			randVec2 = randVec2.multiply(dist);
			
			loc = target.getLocation().add(randVec2);
			loc.add(0,5,0);
			while (!loc.getBlock().getType().isSolid()) {
				
				loc.add(0,-1,0);
				if (loc.getY()<60)
					
					break;
			}
			if (loc.getY()>60) {
				break;
			}
			}
			loc.add(0,1,0);
			
			loc = lookAt(loc, target.getLocation());
			playSound(Sound.BLOCK_END_PORTAL_FRAME_FILL,caster.getLocation(),5,0.3F);
			playSound(Sound.ENTITY_SHULKER_TELEPORT,caster.getLocation(),0.6F,0.3F);
			
			ParUtils.parKreisDirSolid(Particles.END_ROD, caster.getLocation(), 1f, 3, 3F, new Vector(0,1,0),new Vector(0,-1,0));
			
			caster.teleport(loc);
			ParUtils.parKreisDirSolid(Particles.END_ROD, caster.getLocation(), 1f, 0, 3F, new Vector(0,1,0),new Vector(0,1,0));
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
		playSound(Sound.BLOCK_CONDUIT_DEACTIVATE,caster.getLocation(),5,1F);
	}

}
