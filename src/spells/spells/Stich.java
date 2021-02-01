package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.EventCollector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Stich extends Spell {
	
	
	public Stich() {
		name = "§bStich";
		
		speed =2;
		
		hitboxSize = 3;
		cooldown = 20*35;
		hitSpell = true;
		
		steprange = 80;
		hitPlayer = true;
		hitEntity = true;
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.DAMAGE);
		setLore("§7Springt eine kurze Distanz in#§7Blickrichtung. Bei Gegnerkontakt springt der#§7Anwender vom Gegner ab und verursacht Schaden.##§7#§eF:§7 Wird ein Gegner getroffen, kann der#§7Anwender noch einmal in Blickrichtung#§7springen und den Effekt wiederholen. Maximal#§7zweimal möglich.");
		setBetterLore("§7Springt eine kurze Distanz in#§7Blickrichtung. Bei Gegnerkontakt springt der Anwender#§7vom Gegner ab und verursacht Schaden.##§7#§eF:§7 Der Spieler spring in Blickrichtung#§7und wiederholt den Effekt. Maximal zweimal#§7möglich.");
	}
	int dashes = 2;
	public Stich(Player caster,int dashes,boolean refined) {
		super();
		name = "§bStich";
		this.refined = refined;
		speed =2;
		this.dashes = dashes;
		hitboxSize = 3;
		cooldown = 20*45;
		hitSpell = true;
		
		steprange = 80;
		hitPlayer = true;
		hitEntity = true;
		castSpell(caster, name);
	}
	
	boolean hit = false;
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		if (refined) {
			hit = true;
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
	}
	
	int length = 7;
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		if (dashes == 2)
			ParUtils.createRedstoneParticle(loc, 0.1, 0.1, 0.1, 5, Color.WHITE, 1);
			if (dashes == 1)
				ParUtils.createRedstoneParticle(loc, 0.1, 0.1, 0.1, 5, Color.WHITE, 1);
			if (dashes == 0)
				ParUtils.createRedstoneParticle(loc, 0.1, 0.1, 0.1, 5, Color.RED, 1);
		ParUtils.createFlyingParticle(Particles.CLOUD, loc,0.1, 0.1, 0.1, 5, 1.5F, caster.getLocation().getDirection().multiply(-1));
		SoundUtils.playSound(Sound.ENTITY_WITHER_SHOOT, loc,2,5);
		if (dashes < 2) {
			length = 14;
		}
		if (EventCollector.quickSwap.contains(caster)) {
			EventCollector.quickSwap.remove(caster);
		}
	
		
	}

	@Override
	public void move() {
		if (dashes == 2)
			ParUtils.createRedstoneParticle(loc, 0.1, 0.1, 0.1, 5, Color.WHITE, 1);
			if (dashes == 1)
				ParUtils.createRedstoneParticle(loc, 0.1, 0.1, 0.1, 5, Color.WHITE, 1);
			if (dashes == 0)
				ParUtils.createRedstoneParticle(loc, 0.1, 0.1, 0.1, 5, Color.RED, 1);
		
		// TODO Auto-generated method stub
		loc = caster.getLocation();
		if (step > 17) {
			hitPlayer = false;
			hitEntity = false;
		}
		
		if (step < length) {
			
				caster.setVelocity(caster.getLocation().getDirection().multiply(5));
			
			
			ParUtils.createParticle(Particles.END_ROD, loc, 1, 1, 1, 1, speed);
		}
		else {
			
		
			if (hit && dashes > 0) {
				if (swap()) {
					
					new Stich(caster,dashes-1,refined);
					dead = true;
				}
				
			}
			else {
				
				if (dashes == 2) {
					reduceCooldown(20*15);
					//Bukkit.broadcastMessage("REFUNED 15");
				}
				
				if (dashes == 1) {
					reduceCooldown(20*10);
					//Bukkit.broadcastMessage("REFUNED 10" );
				}
				
				caster.setVelocity(caster.getVelocity().multiply(0));
				dead = true;
			}
			
		
			//ParUtils.createFlyingParticle(Particles.CLOUD, loc,0.1, 0.1, 0.1, 5, 1.5F, caster.getVelocity().multiply(-1));
		}
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		
		
		if (dashes == 0) {
			SoundUtils.playSound(Sound.ENTITY_FIREWORK_ROCKET_BLAST, loc, 1F, 5);
			ParUtils.createParticle(Particles.FLASH, loc, 0, 0, 0,1, 1);
			ParUtils.createParticle(Particles.CLOUD, loc, 0, 0, 0, 70, 2);
			ParUtils.createRedstoneParticle(loc, 1, 1, 1,65, Color.RED, 1);
			p.setVelocity(caster.getVelocity());
		}
		else {
			p.setVelocity(caster.getVelocity().normalize());
			SoundUtils.playSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP, loc, 1F, 5);
		}
		if (dashes > 0) {
			damage(p, 2, caster);
		}
		else {
			damage(p, 8, caster);
		}
		
		step = length+1;
		caster.setVelocity(caster.getVelocity().normalize().multiply(2.5).setY(1F));
		// TODO Auto-generated method stub
		hit = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		
		
		if (dashes == 0) {
			SoundUtils.playSound(Sound.ENTITY_FIREWORK_ROCKET_BLAST, loc, 1F, 5);
			ParUtils.createParticle(Particles.FLASH, loc, 0, 0, 0,1, 1);
			ParUtils.createParticle(Particles.CLOUD, loc, 0, 0, 0, 70, 2);
			ParUtils.createRedstoneParticle(loc, 1, 1, 1,65, Color.RED, 1);
			ent.setVelocity(caster.getVelocity());
		}
		else {
			ent.setVelocity(caster.getVelocity().normalize());
			SoundUtils.playSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP, loc,1F, 5);
		}
		if (dashes > 0) {
			damage(ent, 2, caster);
		}
		else {
			damage(ent, 8, caster);
		}
		step = length+1;
		caster.setVelocity(caster.getVelocity().normalize().multiply(2.5).setY(1F));
		// TODO Auto-generated method stub
		hit = true;
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
