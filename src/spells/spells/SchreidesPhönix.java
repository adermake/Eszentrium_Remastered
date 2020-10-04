package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.CorpseUtils;
import esze.utils.ParUtils;
import esze.utils.TTTCorpse;
import esze.utils.TTTRevive;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class SchreidesPhönix extends Spell{

	
	public SchreidesPhönix() {
		name = "§8Schrei des Phönix";
		hitPlayer = false;
		hitEntity = false;
		
		
		traitorSpell = true;
		addSpellType(SpellType.SUPPORT);

	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		revive(caster);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
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
	
	public void revive(Player p) {
		Location loc = p.getLocation();
		for (double t = 1; t <= 10; t=t+0.5) {

			Vector direction = loc.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			Location lo = loc.clone();
		
			// Particel
			
			/*
			if (loc.getBlock().getType() != Material.AIR) {
				p.sendMessage("CANZEL");
				break;
			}
			*/
			
			
			for(TTTCorpse c : TTTCorpse.allCorpses){
				
					if (loc.distance(c.cows.get(0).getLocation())<3) {
						revive(p,c.corpseID,1);
						return;
					}
					
				
			}
			
			// SUBTRACTING LOCATION UM den prozess
			// von vorne zu
			// starten
			loc.subtract(x, y, z);
		}
		return;

	}
	public void revive(final Player p,int id,final int time) {
		Location loc = p.getLocation();
		for (double t = 1; t <= 10; t=t+0.5) {
			

			Vector direction = loc.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			Location lo = loc.clone();
			
			// Particel


			

		
			
			for(TTTCorpse c : TTTCorpse.allCorpses){
				
					if (loc.distance(c.cows.get(0).getLocation())<4) {
					if((c.corpseID == id)){
						
						new BukkitRunnable() {
							public void run() {
								int ti = time;
								Player play = c.player;
								//Vex v = (Vex) c.cows.get(0).getLocation().clone().getWorld().spawnEntity(c.cows.get(0).getLocation().add(0,(40-ti),0), EntityType.VEX);
								
								
								if (time == 40) {
									
									
									if(play.getGameMode() == GameMode.ADVENTURE){
										play.getInventory().clear();
									
									
										play.teleport(c.cows.get(0).getLocation());
										play.setGameMode(GameMode.SURVIVAL);
									try {
										
										
										TTTRevive.revive(c.player.getName(), c);
										
									}
									catch(Exception e) {}
									
									
									
									
									
									for(Player all : Bukkit.getOnlinePlayers()){
										all.showPlayer(play);
									}
									this.cancel();
									}
									
									
								}
								else {
									revive(p,id,ti+1);
									ParUtils.parLineRedstone(c.cows.get(0).getLocation(), p.getLocation(), Color.BLACK, 2, 0.2F);
									ParUtils.chargeDot(c.cows.get(0).getLocation(), Particles.TOTEM_OF_UNDYING, 0.3F, 4);
									ParUtils.createRedstoneParticle(c.cows.get(0).getLocation(),0, 0, 0, 1, Color.BLACK, 1F);
									playSound(Sound.ENTITY_ILLUSIONER_MIRROR_MOVE,p.getLocation(),1,1);
								}
								this.cancel();
								
								
								
								
								
							}
						}.runTaskTimer(main.plugin, 2, 1);
						return;
						
					}
					}
				
			}
			
			// SUBTRACTING LOCATION UM den prozess
			// von vorne zu
			// starten
			loc.subtract(x, y, z);
		}
		return;

	}
	

}
