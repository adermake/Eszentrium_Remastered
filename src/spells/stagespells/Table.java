package spells.stagespells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.ScoreboardTeamUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;

public class Table extends Spell {

	List<String> table;
	ArrayList<Material> blockTypes = new ArrayList<Material>();  
	ArrayList<Entity> select = new ArrayList<Entity>();
	ArrayList<Entity> delay = new ArrayList<Entity>();
	HashMap<Entity,Vector> carriers = new HashMap<Entity,Vector>();
	HashMap<Entity,Entity> follow = new HashMap<Entity,Entity>();
	HashMap<Entity,String> name = new HashMap<Entity,String>();
	public Table(Player c,List<String> ls) {
		table = ls;
		canHitCastersSpells = true;
		hitboxSize = 7;
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
		caster = c;
		
		castSpell(c,"§eTable");
	}
	@Override
	public void setUp() {
		playGlobalSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
		// TODO Auto-generated method stub
	
		
		Vector ruler = loc.getDirection().crossProduct(new Vector(0,1,0)).normalize();
		Vector offset = ruler.clone().multiply(0.9+-1.8*((double)table.size())/2D).add(new Vector(0,table.get(0).split("\\n").length/2,0));
		int in = 0;
		for (String s : table) {
			int index = 0;
			String abc = s.split("\\n")[0];
			for (String a : s.split("\\n")) {
				
				if (index == 0) {
					
				}
				else {
					
					//FallingBlock fb = spawnFallingBlock(loc.clone(), blockTypes.get(in));
					
					//ArmorStand ar = createArmorStand(loc);
					ArmorStand holder = createArmorStand(loc.clone().add(offset).add(0,-index*1.8F,0).add(ruler.clone().multiply(in*1.8F)));
					//holder.setGravity(false);
					
					holder.setGravity(true);
					holder.setCustomName("§e"+a);
					holder.setCustomNameVisible(true);
					holder.getEquipment().setHelmet(new ItemStack(blockTypes.get(in)));
					//ar.setSmall(true);
					//ar.setCustomNameVisible(true);
					carriers.put(holder, new Vector(in,index,0));
					name.put(holder, abc);
					
				}
				
				index++;
			}
			in++;
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
	double dist = 0;
	boolean begin = true;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		//ParUtils.createParticle(Particles.CLOUD, loc, 0, 0, 0, 1, 0);
		Vector ruler = loc.getDirection().crossProduct(new Vector(0,1,0)).normalize();
		Vector ruler2 = new Vector(0,-1,0);
		Vector offset = ruler.clone().multiply(0.9+-1.8*((double)table.size())/2D).add(new Vector(0,table.get(0).split("\\n").length/2,0));
		
		for (Entity ent : carriers.keySet()) {
			Vector v = carriers.get(ent);
			ArmorStand a = (ArmorStand) ent;
			
			a.setHeadPose(a.getHeadPose().add(0, 0.1, 0));
			doPin(ent,loc.clone().add(ruler.clone().multiply(v.getX()*1.8).add(offset).add(ruler2.clone().multiply(v.getY()*1.8))));
		}
		for (Entity ent : follow.keySet()) {
			follow.get(ent).setRotation(ent.getLocation().getPitch(), ent.getLocation().getYaw());
			doPin(follow.get(ent),ent.getLocation().add(0,1.2,0));
		}
		
		if (caster.isSneaking()) {
			
			if (begin)  {
				if (pointLoc(caster)) {
					dist = loc.distance(caster.getLocation());
					begin = false;
				}
				return;
			}		
			loc = caster.getLocation().add(caster.getLocation().getDirection().multiply(dist));
		}
		
		else {
			
			begin = true;
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!delay.contains(p) && swap(p)) {
				pointBlock(p);
					
				delay.add(p);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						delay.remove(p);
					}
				}.runTaskLater(main.plugin,5);
		
			}
			
		}
		if (carriers.size() <= 0) {
			dead = true;
		}
	}
	
	public boolean pointLoc(Player p) {
		int range = 200;
		
	
		Location s= p.getLocation();
		for (double t = 1; t <= range; t=t+0.5) {

			

			s.add(s.getDirection());
			Location lo = s.clone();
			//Bukkit.broadcastMessage(""+lo.distance(loc));
			if (lo.distance(loc) < 5) {
				return true;
			}
			
		}
		return false;
	}
	public void pointBlock(Player p) {
		int range = 300;
		int toleranz = 1;
		Location loc = p.getLocation();
		for (double t = 1; t <= range; t=t+0.5) {

			Vector direction = loc.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			Location lo = loc.clone();

			// Particel
			for (Entity ent : carriers.keySet()) {
				if (ent.getLocation().add(0,1.5,0).distance(loc)<toleranz) {
					
					if (p == caster) {
						if (select.contains(ent)) {
							follow.get(ent).remove();
							ent.remove();
							
							carriers.remove(ent);
							
							Firework firework = (Firework) caster.getWorld().spawnEntity(loc.clone().add(0,1,0), EntityType.FIREWORK);
					        FireworkMeta fd = (FireworkMeta) firework.getFireworkMeta();

					        fd.addEffect(FireworkEffect.builder()

					                .flicker(true)

					                .trail(false)

					                .with(Type.BALL)
					                .withColor(Color.YELLOW)
					                .withColor(Color.ORANGE)				              
					                .build());

					        firework.setFireworkMeta(fd);
					        playGlobalSound(Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.51F);
					        firework.detonate();
					        return;
						}
						if (follow.containsKey(ent)) {
							ScoreboardTeamUtils.colorEntity(follow.get(ent), ChatColor.RED);
							select.add(ent);
							
							new BukkitRunnable() {
								public void run() {
									if (follow.containsKey(ent)) {
										select.remove(ent);
										ScoreboardTeamUtils.colorEntity(follow.get(ent), ChatColor.YELLOW);
									}
								}
							}.runTaskLater(main.plugin, 20*1);
							playGlobalSound(Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.8);
						}
					}
					else {
						if (follow.containsKey(ent)) {
							follow.get(ent).remove();
							follow.remove(ent);
							
							playGlobalSound(Sound.BLOCK_NOTE_BLOCK_BIT, 1, 2);
						}
						else {
							playGlobalSound(Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
							MagmaCube s = (MagmaCube) spawnEntity(EntityType.MAGMA_CUBE,ent.getLocation().add(0,1.2,0));
							s.setSize(2);
							s.setInvulnerable(true);
							s.setGlowing(true);
							Bukkit.broadcastMessage("§7"+p.getName() +" wählt "+"§e"+ent.getCustomName() +" §7aus "+"§e"+name.get(ent));
							follow.put(ent, s);
							s.setInvisible(true);
							ScoreboardTeamUtils.colorEntity(s, ChatColor.YELLOW);
						}
					}
					
						
					return;
				}
			}

		
			
			// SUBTRACTING LOCATION UM den prozess
			// von vorne zu
			// starten
			loc.subtract(x, y, z);
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
		for (Entity ent : follow.values()) {
			ent.remove();
		}
		for (Entity ent : carriers.keySet()) {
			ent.remove();
		}
	}

}
