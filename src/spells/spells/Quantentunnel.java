package spells.spells;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Quantentunnel extends Spell{

	public Quantentunnel() {
		name = "§eQuantentunnel";
		hitSpell = true;
		steprange = 100;
		speed = 3;
		casttime = 20;
		cooldown = 20*40;
		if (refined) 
			casttime = 0;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("§7Schießt nach kurzer Verzögerung ein#§7Projektil, das getroffene Gegner markiert.#§7Wenn der Zauber seine maximaleReichweite#§7erreicht hatoder einen Block trifft, werden#§7alle markierten Gegner an diesen Punkt#§7teleportiert.");
		setBetterLore("§7Schießt ein Projektil, das getroffene#§7Gegner markiert. Wenn der Zauber seine#§7maximaleReichweite erreicht hatoder einen Block#§7trifft, werden alle markierten Gegner an#§7diesen Punkt teleportiert.#Shift: Teleportiert Anwender ebenfalls an den Endpunkt.");
	}

	

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		playSound(Sound.BLOCK_BELL_RESONATE,caster.getLocation(),10,0.5F);
		playSound(Sound.BLOCK_END_PORTAL_SPAWN,loc,11.5f,1.5f);
	}

	int t = 0;
	int stage = 0;
	@Override
	public void cast() {
		if (refined) {
			cast = 1000000;
		}
		t++;
		stage++;
		if (t<10) {
			//ParUtils.parKreisDir(Particles.ENCHANTED_HIT, caster.getLocation(), 3*casttime/cast, 0, 1, new Vector(0,1,0),new Vector(0,1,0));
			
			loc = caster.getLocation();
			Location dot = ParUtils.stepCalcCircle(loc, 0.7, new Vector(0,1,0), -0.3, step*33);
			Location dot2 = ParUtils.stepCalcCircle(loc, 0.7, new Vector(0,1,0), -0.3, step*33+22);
			//ParUtils.createParticle(Particles.FLAME, dot, 0, 1, 0, 0, 14);
			//ParUtils.createParticle(Particles.FLAME, dot2, 0, 1, 0, 0, 14);
			
			ParUtils.dropItemEffectVector(dot, Material.ENDER_PEARL, 1, 1, 0,new Vector(0,1,0));
			ParUtils.dropItemEffectVector(dot2, Material.ENDER_PEARL, 1, 1, 0,new Vector(0,1,0));
			
			
		}
		
		if (t < 40) {
			if (stage > 2) {
				playGlobalSound(Sound.BLOCK_CONDUIT_AMBIENT, 1, 1);
				stage = 0;
			}
			
		}
		
		
		
	}

	@Override
	public void move() {
		loc.add(loc.getDirection());
		loc.setDirection(caster.getLocation().getDirection());
		
	}

	int ti = 0;
	@Override
	public void display() {
		
		// TODO Auto-generated method stub
		ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.BLUE, 1);
		ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.TEAL, 1);
		ParUtils.createParticle(Particles.ENCHANTED_HIT, loc, 0, 0,0, 1, 0);
		ParUtils.dropItemEffectVector(loc, Material.ENDER_PEARL, 1, 1, 0,new Vector(0,1,0));
		ti++;
		if (ti > 20) {
			for (Entity ent : hitEntitys) {
				ParUtils.parLineRedstone(loc.clone(), ent.getLocation().add(0,1,0), Color.AQUA, 0.4F, 2F);
			}
		}
		
	}

	
	@Override
	public void onPlayerHit(Player p) {
		hitEffect(p);
		playSound(Sound.BLOCK_BEACON_ACTIVATE,loc, 10, 1);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		hitEffect(ent);
		playSound(Sound.BLOCK_BEACON_ACTIVATE,loc, 10, 1);
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST,loc, 10, 1);
		
		dead = true;
	}

	@Override
	public void launch() {
		playSound(Sound.ENTITY_VEX_CHARGE,loc, 10,1.5F);
		loc = caster.getEyeLocation();
		playSound(Sound.ENTITY_ZOMBIE_INFECT,loc, 10, 1);
		
	}

	@Override
	public void onDeath() {
		ArrayList<Entity> rem = new ArrayList<Entity>();
		for (Entity ent : hitEntitys) {
			if (ent instanceof Player) {
				Player p  = (Player)ent;
				if (p.getGameMode() == GameMode.ADVENTURE) {
					rem.add(p);
				}
			}
		}
		for (Entity ent : rem) {
			hitEntitys.remove(ent);
		}
		if (refined && caster.isSneaking()) {
			hitEntitys.add(caster);
		}
		for (Entity ent : hitEntitys) {
			playSound(Sound.BLOCK_END_PORTAL_SPAWN,ent.getLocation(), 50,1.5F);
			ParUtils.parLineFly(Particles.END_ROD, loc.clone(), ent.getLocation(), 35, 1, loc.toVector().subtract(ent.getLocation().toVector()).normalize());
			ent.teleport(loc.add(loc.getDirection().multiply(-2)));
			
		}
		
		
		
	}
	
	
	public void hitEffect(Entity e) {
		playSound(Sound.BLOCK_GRINDSTONE_USE,e.getLocation(),11.5f,1.5f);
		new BukkitRunnable() {
			double s = 0;
			public void run() {
				Vector v = loc.getDirection();
				s++;
				double gain = (double)step/(double)steprange;
				for (double i = 0;i<gain*16;i++) {
					Location l1 = ParUtils.stepCalcCircle(e.getLocation().add(0,1,0), 4*gain, v, 0, (i*44/gain)+(s*6*gain));
					Location l2 = ParUtils.stepCalcCircle(loc.clone().add(0,1,0), 2*gain, v, 0, (i*44/gain)+(s*6*gain));
					ParUtils.createFlyingParticle(Particles.END_ROD, l1, 0.01, 0.01, 0.01, 1, 35*gain, l2.toVector().subtract(l1.toVector()).normalize());
					ParUtils.createFlyingParticle(Particles.BUBBLE, l1, 0.01, 0.01, 0.01, 4, 0, v);
				}
				
				//Location l2 = ParUtils.stepCalcCircle(e.getLocation().add(0,1,0), 3*gain, v, 0, 22D+s*6*gain);
				
				//ParUtils.createFlyingParticle(Particles.END_ROD, l2, 0.01, 0.01, 0.01, 11, 16*gain, v);
				if (dead ) {
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin,1,1);
	}

}
