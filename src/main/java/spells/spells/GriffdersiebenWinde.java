package spells.spells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.players.DamageCall;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Cooldowns;
import spells.spellcore.EventCollector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class GriffdersiebenWinde extends Spell {
	
	
	
	public GriffdersiebenWinde() {
		cooldown = 5;
		steprange = 45;
		speed = 1;
		name = "§4Griff der sieben Winde";
		traitorSpell = true;
		
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.LOCKDOWN);
		
	}
	
	boolean swich = true;
	Player target;
	@Override
	public void setUp() {
		if (EventCollector.quickSwap.contains(caster)) {
			EventCollector.quickSwap.remove(caster);
		}
		// TODO Auto-generated method stub
		target = pointEntity(caster);
		if (target == null) {
			
			refund = true;
			dead = true;
		}
		else {
			dis = caster.getLocation().distance(target.getEyeLocation()) + 5;
			new BukkitRunnable() {
				int t = 0;
				@Override
				public void run() {
					t++;
					if (t>46) {
						this.cancel();
					}
					// TODO Auto-generated method stub
					if (target == null)
						return;
					
					if (EventCollector.quickSwap.contains(caster)) {
						EventCollector.quickSwap.remove(caster);
						this.cancel();
					}
					Location loca = loc(caster,  dis);
					
					if (caster.isSneaking()) {
						dis++;
					}
					
					
					if (target.getLocation().distance(loca) > 2) {
						tagPlayer(target);
						doPull(target, loca,target.getLocation().distance(loca)/5);
					}
					
					
					ParUtils.createParticle(Particle.CLOUD, target.getLocation(), 0, 0, 0, 1, 0);
					playSound(Sound.ENTITY_CAT_HISS, loca, 0.1F, 0.1F);
					
				}
			}.runTaskTimer(main.plugin, 1, 1);
			dead = true;
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
	double dis = 0;
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
