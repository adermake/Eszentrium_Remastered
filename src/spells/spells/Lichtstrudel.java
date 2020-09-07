package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class Lichtstrudel extends Spell {

	public Lichtstrudel() {
		cooldown = 20 * 45;
		name = "§cLichtstrudel";
		steprange = 100;
		
	}
	
	Location lastdot;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		lastdot = caster.getLocation();
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		playSound(Sound.BLOCK_BELL_USE,caster.getLocation(),8F,0.1f);
	}

	int delay = 0;
	
	@Override
	public void move() {
		delay++;
		
		//playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE,caster.getLocation(),0.1F,2f);
		// TODO Auto-generated method stub
		if (delay > 4) {
			
			delay = 0;
		}
		playSound(Sound.ENTITY_EVOKER_PREPARE_ATTACK,caster.getLocation(),1F,0.5f);
		caster.setVelocity(caster.getLocation().getDirection());
		if (step < 90 && delay == 0) {
			//dot(caster.getLocation().add(0,1,0).clone(),lastdot.clone(),caster,100-steprange);
			//lastdot = caster.getLocation().add(0,1,0).clone();
			dot(caster.getLocation(),lastdot.clone(),caster,(int) (100-step));
			lastdot = caster.getLocation().clone();
		}
			
		
		
		ParUtils.parKreisDir(Particles.END_ROD, caster.getLocation().add(0,1,0), 2, 0, 0.1, caster.getLocation().getDirection(), caster.getLocation().getDirection().multiply(-1));
		if (caster.isSneaking()) {
			dead = true;
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

public void dot(Location la,Location to,Player p,int time ) {
		
		new BukkitRunnable() {
			int t = time;
			Location too = to.clone();
			Location l = la.clone();
			public void run() {
				t--;
				ParUtils.createParticle(Particles.END_ROD, l, 0, 0,0,0, 0);
				for (LivingEntity le : p.getWorld().getLivingEntities()) {
					if (checkHit(le,l,p,3)) {
						doPull(le, too,1);
					}
				}
				if (t<0) {
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin, 1, 1);
	}


}
