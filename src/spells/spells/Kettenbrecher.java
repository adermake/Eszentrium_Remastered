package spells.spells;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.ChainConnection;
import spells.stagespells.ChainSegment;

public class Kettenbrecher extends Spell {

	public Kettenbrecher() {
		name = "§cKettenbrecher";
		steprange = 20 * 10;
		cooldown = 20 * 40;
		silencable = true;
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.KNOCKBACK);
		
	}

	ArrayList<ChainSegment> segments = new ArrayList<ChainSegment>();
	ArrayList<ChainConnection> connections = new ArrayList<ChainConnection>();
	ArrayList<Vector> avrg = new ArrayList<Vector>();

	ChainSegment start;
	ChainSegment end;
	ChainSegment next;
	ArmorStand endAr;
	ArmorStand startAr;
	float gravity = 0.0F;
	
	

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		next = new ChainSegment(caster.getLocation().toVector());
		start = next;
		next.setLocked(true);
		end = next;
		segments.add(next);
		avrg.clear();
		for (int i = 0; i < 2; i++) {
			addChainSegment();
		}

		start.setLocked(true);
	
		// endAr = createArmorStand(end.loc.toLocation(caster.getWorld()));
		// endAr.setGravity(true);
		// endAr.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_BLOCK));
		// endAr.setSmall(true);
		// startAr = createArmorStand(start.loc.toLocation(caster.getWorld()));
		// startAr.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_BLOCK));
		// startAr.setGravity(true);
		// startAr.setSmall(true);
		
		/*
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (swap()) {
					activate = !activate;
					Bukkit.broadcastMessage(""+activate);
				}
				if (dead) {
					this.cancel();
				}
				
			}
		}.runTaskTimer(main.plugin,2,2);
		*/
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub

	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub

	}

	org.bukkit.entity.Entity target;
	boolean releaseSneak = false;
	boolean activate = false;
	int maxChain = 70;
	double chainCount = 0;
	@Override
	public void move() {
		if (dead)
			return;
		
		
		if (step%2 == 0 && caster.isSneaking()) {
			playSound(Sound.BLOCK_CHAIN_BREAK, start.loc.toLocation(caster.getWorld()), 1, 0.8);
			playSound(Sound.BLOCK_CHAIN_BREAK, end.loc.toLocation(caster.getWorld()), 1, 0.8);
			
			
		}
		else {
			playSound(Sound.BLOCK_CHAIN_PLACE, start.loc.toLocation(caster.getWorld()), 0.31, 1);
			playSound(Sound.BLOCK_CHAIN_PLACE, end.loc.toLocation(caster.getWorld()), 0.31, 1);
		}
		
		
		if (step < maxChain) {
			
			chainCount++;
			addChainSegment();
			
			//addChainSegment();
			speed = 30;
			if (target == null)
			loc = loc.add(caster.getLocation().getDirection().multiply(0.3F));
			start.loc = loc.toVector();
		
			if (swap()) {
				
				step = maxChain;
				clearswap();
			}
		}
		else {
			//ATTRIBUTE SWICH
			
			speed = 1;
			if (target != null) {
				speed = 2;
			}
			hitPlayer = false;
			hitEntity = false;
			
			if (swap()) {
				dead =true;
				releaseSneak = true;
				if (target == null) {
					caster.setVelocity(caster.getVelocity().add(caster.getLocation().getDirection().multiply(3)));
					
					ParUtils.parKreisDir(Particle.CLOUD, caster.getLocation(), 3, 0, 0.1, caster.getLocation().getDirection(), caster.getLocation().getDirection());
					ParUtils.parKreisDir(Particle.CLOUD, caster.getLocation(), 1, 0, 2, caster.getLocation().getDirection(), caster.getLocation().getDirection());
					ParUtils.parKreisDir(Particle.CLOUD, caster.getLocation(), 2, 0, 1, caster.getLocation().getDirection(), caster.getLocation().getDirection());
				}
				else {
					ParUtils.parKreisDir(Particle.CLOUD,target.getLocation(), 3, 0, 0.1, target.getLocation().getDirection(), target.getVelocity().normalize());
					ParUtils.parKreisDir(Particle.CLOUD, target.getLocation(), 1, 0, 2, target.getLocation().getDirection(), target.getVelocity().normalize());
					ParUtils.parKreisDir(Particle.CLOUD, target.getLocation(), 2, 0, 1, target.getLocation().getDirection(), target.getVelocity().normalize());
				
				}
				playSound(Sound.ENTITY_WITHER_SHOOT,caster.getLocation(),1F,1.2F);
				playSound(Sound.BLOCK_ANVIL_PLACE,caster.getLocation(),5F,1.5F);
				
				
				if (target == null)
				return;
			}
			
			
			if (caster.isSneaking() && !dead) {
				
		
				//Bukkit.broadcastMessage("ON");
				if (target == null) {
					
					ParUtils.createFlyingParticle(Particle.CLOUD, caster.getLocation(), 0.6, 0.6, 0.6, 1, 0.2F, caster.getVelocity().multiply(-1));
					doPin(caster, end.loc.toLocation(caster.getWorld()), 2);
					end.setLocked(false);

					if (!releaseSneak) {
						end.lastLoc = end.lastLoc.clone().add(caster.getLocation().getDirection().multiply(-20F));
						
					} else {
						end.lastLoc = end.lastLoc.clone().add(caster.getLocation().getDirection().multiply(-1F));
					}
					
				}
				

				if (!releaseSneak) {
					releaseSneak = true;
				}

				//gravity = 0.02F;
				
				if (target != null) {
					caster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20,20,true));
					end.loc = caster.getLocation().add(0,1,0).toVector();
					gravity = -0.01F;
					start.setLocked(false);
					doPin(target,start.loc.toLocation(caster.getWorld()),2);
					ParUtils.createFlyingParticle(Particle.CLOUD,target.getLocation(), 0.6, 0.6, 0.6, 1, 0.2F,target.getVelocity().multiply(-1));
					
					
					avrg.add(target.getVelocity().clone());
					if (target != null) {
						
						if (avrg.size() > 6) {
							avrg.remove(0);
						}
						
					}
					
				}

			} else {
				
				gravity = 0;
				if (releaseSneak) {
					
					if (target == null) {
					
						caster.setVelocity(end.loc.clone().subtract(end.lastLoc.clone()));
						releaseSneak = false;
					}
					else {
						caster.removePotionEffect(PotionEffectType.SPEED);
						Vector avr = new Vector(0,0,0);
						
						//Bukkit.broadcastMessage("A");
						if (avrg.size() > 0) {
						
							for (Vector a : avrg) {
								avr.add(a.clone());
							}
							//Bukkit.broadcastMessage(""+avr.length());
							avr.multiply(1/(double)avrg.size());
							
							target.setVelocity(avr.multiply(2));
							
						}
					
						releaseSneak = false;
					}
				
					//Bukkit.broadcastMessage("RALEASA");
				}
				else {
					//Bukkit.broadcastMessage(" NOT RALEASA");
				}
				start.setLocked(true);
				end.setLocked(true);
				
				//gravity = 0.01F;
				end.loc = caster.getLocation().add(0,1,0).toVector();
				
				
				
				if (target != null) {
					loc = target.getLocation();
					start.loc = loc.toVector();
				}
			
				
				
			}
		}
		
	
		// PULL BACK
		
		if (loc.distance(caster.getLocation()) > chainCount * 0.4 +20) {
			if (target != null) {
				
				doPin(target,caster.getLocation(),0.05F);
				doPin(caster,target.getLocation(),0.05F);
			}
		
		}
		
	
		
		
		
		
		
		
		// TODO Auto-generated method stub
		for (ChainSegment segment : segments) {
			if (!segment.isLocked()) {
				Vector prevLoc = segment.loc.clone();
				Vector addLoc = segment.loc.clone().subtract(segment.lastLoc.clone());
				segment.loc = segment.loc.add(addLoc);
				segment.loc = segment.loc.add(new Vector(0, -gravity, 0));
				segment.lastLoc = prevLoc;
			}
		}

		for (int i = 0; i < 55; i++) {

			// Collections.shuffle(connections);
			for (ChainConnection connection : connections) {
				Vector center = connection.segmentA.loc.clone().add(connection.segmentB.loc.clone()).multiply(0.5);
				Vector stickDir = connection.segmentA.loc.clone().subtract(connection.segmentB.loc.clone()).normalize();

				if (!connection.segmentA.isLocked())
					connection.segmentA.loc = center.clone().add(stickDir.clone().multiply(connection.length / 2));
				if (!connection.segmentB.isLocked())
					connection.segmentB.loc = center.clone().subtract(stickDir.clone().multiply(connection.length / 2));

			}
		}

		int index = 0;
		for (ChainConnection connection : connections) {
			Vector center = connection.segmentA.loc.clone().add(connection.segmentB.loc.clone()).multiply(0.5);
			Vector stickDir = connection.segmentA.loc.clone().subtract(connection.segmentB.loc.clone()).normalize();

			if (!connection.segmentA.isLocked())
				connection.segmentA.loc = center.clone().add(stickDir.clone().multiply(connection.length / 2));
			if (!connection.segmentB.isLocked())
				connection.segmentB.loc = center.clone().subtract(stickDir.clone().multiply(connection.length / 2));

			Location arLoc = connection.segmentA.loc.toLocation(caster.getWorld()).add(0, -1.5F, 0)
					.setDirection(stickDir);

			connection.chain.setFallDistance(0);
			doPin(connection.chain, arLoc, 2);
			
			
			// armorStandPitch(stickDir.clone(), connection.chain);
			setArmorstandHeadPos(connection.chain, stickDir.clone().normalize(), 90, -90);
			if (index == 0) {
				// armorStandPitch(stickDir.clone().multiply(-1), connection.chain);
				connection.chain.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_PICKAXE));
			}
			else if (index >= connections.size() - 1) {
				connection.chain.getEquipment().setHelmet(new ItemStack(Material.SOUL_LANTERN));
			}
			else if (connection.chain.getEquipment().getHelmet().getType() != Material.CHAIN) {
				connection.chain.getEquipment().setHelmet(new ItemStack(Material.CHAIN));
			}
			index++;
		}

		
		//doPin(endAr, end.loc.toLocation(caster.getWorld()).add(0,-1,0),2);
		//doPin(startAr,loc.clone().add(0,-1,0),2);

	}

	public void addChainSegment() {

		ChainSegment newChain = new ChainSegment(caster.getLocation().toVector().add(new Vector(0.3,-1, 0)));
		ArmorStand ar = createArmorStand(newChain.loc.toLocation(caster.getWorld()));
		ar.setGravity(true);
		ar.setSilent(true);
		ar.setCollidable(false);
		ar.getEquipment().setHelmet(new ItemStack(Material.CHAIN));
		
		
		connections.add(new ChainConnection(next, newChain, 0.3F, ar));
		segments.add(newChain);
		next = newChain;

		disableEntityHitbox(ar);

		end.setLocked(false);
		end = next;
		next.setLocked(true);
		
		
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		//ParUtils.debug(loc);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		target = (org.bukkit.entity.Entity) p;
		start.setLocked(false);
		step = maxChain;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		target = (org.bukkit.entity.Entity) ent;
		start.setLocked(false);
		step = maxChain;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		step = maxChain;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		for (ChainConnection c : connections) {
			c.chain.remove();
		}
	}

}
