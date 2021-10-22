package spells.stagespells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.ScoreboardTeamUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class WheelOfFortuneWheel extends Spell {
	
	String[] val;
	ArrayList<Entity> carriers = new ArrayList<Entity>();
	ArrayList<Material> blockTypes = new ArrayList<Material>();
	Location dir;
	public WheelOfFortuneWheel(Player c,String[]ls,Location dir) {
		caster = c;
		this.dir  = dir;
		val = ls;
		
		blockTypes.add(Material.RED_WOOL);
		blockTypes.add(Material.ORANGE_WOOL);
		blockTypes.add(Material.YELLOW_WOOL);
		blockTypes.add(Material.GREEN_WOOL);
		blockTypes.add(Material.CYAN_WOOL);
		blockTypes.add(Material.BLUE_WOOL);
		blockTypes.add(Material.LIGHT_BLUE_WOOL);
		blockTypes.add(Material.PURPLE_WOOL);	
		blockTypes.add(Material.MAGENTA_WOOL);	
		blockTypes.add(Material.LIME_WOOL);
		blockTypes.add(Material.PINK_WOOL);
		blockTypes.add(Material.WHITE_WOOL);
		blockTypes.add(Material.BROWN_WOOL);
		blockTypes.add(Material.GRAY_WOOL);
		blockTypes.add(Material.LIGHT_GRAY_WOOL);
		blockTypes.add(Material.BLACK_WOOL);
		castSpell(c, "§eWheel of Fortune");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setUp() {
		playGlobalSound(Sound.BLOCK_CONDUIT_ACTIVATE, 1, 1);
		// TODO Auto-generated method stub
		ParUtils.parKreisDot(Particle.CLOUD, dir.clone().add(0,1,0), 12, 0, -0.07, dir.getDirection());
		Location l = dir;
		loc = dir;
		int index = 0;
		for (String s : val) {
			
			ArmorStand a = createArmorStand(l);
			a.setCustomName("§e"+s);
			a.setCustomNameVisible(true);
			a.setSmall(true);
			FallingBlock fb = spawnFallingBlock(l, blockTypes.get(index));
			fb.setGravity(false);
			
			
				ArmorStand ent = (ArmorStand) createArmorStand(l);
				//ent.setInvisible(true);
				ent.setSmall(true);
				ent.addPassenger(a);
			
			
			ent.addPassenger(fb);
			ent.setGravity(true);
			index++;
			carriers.add(ent);
		}
	
		maxSteps = randInt(10,64);
	}

	int maxSteps;
	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	double spe = 0;
	int tick = 0;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		
	
		spe = -(44/(double)carriers.size()/4) *step;
	
		if (step % 2 == 0) {
			tick++;
			speed -= 1/((double)maxSteps);
			playGlobalSound(Sound.BLOCK_LEVER_CLICK, 1, 2);
		}
	
		
	}
	
	boolean preDead = false;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		double s = 0;
		double cc = carriers.size();
		for (Entity carrier : carriers) {
			
			Location l = ParUtils.stepCalcCircle(loc.clone(), 4, dir.getDirection(), 0, spe*2+s*(44/cc));
			doPin((org.bukkit.entity.Entity) carrier,l);
			ParUtils.createParticle(Particle.CLOUD, carrier.getLocation().add(0,1,0), 0, 0, 0, 1, 0);
			s++;
			carrier.getPassengers().get(1).setGlowing(false);
		}
		Entity a = carriers.get((int) ((tick+5)% carriers.size()));
		a.getPassengers().get(1).setGlowing(true);
		ScoreboardTeamUtils.colorEntity(a.getPassengers().get(1), ChatColor.GOLD);
		if (speed < 0.3 && !preDead) {
			speed = 0;
			
			preDead = true;
			new BukkitRunnable() {
				public void run() {
					dead = true;
				}
			}.runTaskLater(main.plugin, 20);
			
		}
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
		playGlobalSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);
		Entity a = carriers.get((int) ((tick+5)% carriers.size()));
		a.getPassengers().get(1).setGlowing(true);
		Bukkit.broadcastMessage("§7[§eGlücksrad§7]: "+a.getPassengers().get(0).getCustomName());
		for (Entity carrier : carriers) {
			carrier.setGravity(false);
		}
		new BukkitRunnable() {
			public void run() {
				playGlobalSound(Sound.BLOCK_CONDUIT_DEACTIVATE, 1, 1);
				ParUtils.parKreisDot(Particle.CLOUD, loc, 2, 0, 1, dir.getDirection());
				for (Entity carrier : carriers) {
					for (Entity c :carrier.getPassengers() ) {
						c.remove();
					}
					carrier.remove();
				}
			}
		}.runTaskLater(main.plugin, 40);
	
		
	}

}
