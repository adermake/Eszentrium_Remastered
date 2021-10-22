package spells.spells;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.world.entity.animal.EntityChicken;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Eggsplosive;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;

public class H�hnchenluftschlag extends Spell{

	public H�hnchenluftschlag() {
		name = "�6H�hnchenluftschlag";
		cooldown = 20 * 42;
		steprange = 66;
		
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.SELFCAST);
		setLore("Beschw�rt ein H�hnchen und reitet darauf, wobei es sich in Blickrichtung aufw�rts bewegt." + 
				"Shift: Springt vorzeitig vom H�hnchen ab und springt in die H�he." + 
				"F: Schie�t eine Salve aus Eiern, die bei Block- oder Gegnerkontakt explodieren und Schaden verursachen.");
		setBetterLore("Beschw�rt ein H�hnchen und reitet darauf, wobei es sich in Blickrichtung aufw�rts bewegt." + 
				"Shift: Springt vorzeitig vom H�hnchen ab und springt in die H�he. Das H�hnchen wird in Blickrichtung geschossen und explodiert, sobald es einen Block trifft." + 
				"F: Schie�t eine Salve aus Eiern, die bei Block- oder Gegnerkontakt explodieren und Schaden verursachen.");
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
	EntityChicken mcChicken;
	Chicken c;
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
		/*
		mcChicken =  new EntityChicken( , ((CraftWorld) caster.getWorld()).getHandle());
		mcChicken.setPositionRotation(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), caster.getLocation().getPitch(), caster.getLocation().getYaw());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(mcChicken, SpawnReason.CUSTOM);
		c =  (Chicken) mcChicken.getBukkitEntity();
		c.addPassenger(caster);
		*/
		playSound(Sound.ENTITY_CHICKEN_HURT,loc,4,1);
		
		c = (Chicken) caster.getWorld().spawnEntity(loc, EntityType.CHICKEN);
		c.addPassenger(caster);
	}
	boolean dropped = false;
	Vector dir;
	int egg = 1;
	int eggStop = 0;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		//ParUtils.createParticleSqareHorizontal(Particle.FLAME, caster.getLocation(), step/10);
		//mcChicken.setHeadRotation(caster.getLocation().getYaw());
		//mcChicken.setPositionRotation(c.getLocation().getX(), c.getLocation().getY(), c.getLocation().getZ(), caster.getLocation().getYaw(), caster.getLocation().getPitch());
		//c.setVelocity(caster.getLocation().getDirection());
		c.setRotation(caster.getLocation().getYaw(), caster.getLocation().getPitch());
		
		if (!dropped)
		c.setVelocity(caster.getLocation().getDirection().multiply(1).setY(0.5D));
		
		if (c.getPassengers().isEmpty()) {
			if (refined) {
				if (!dropped) {
					playSound(Sound.ENTITY_CHICKEN_DEATH,caster.getLocation(),7,1);
				
				dir = caster.getLocation().getDirection();
				dropped = true;
				caster.setVelocity(caster.getVelocity().setY(2));
				step = 0;
				}
			}
			else {
				c.remove();
				caster.setVelocity(caster.getVelocity().setY(2));
				dead = true;
			}
			
		}
		
		if (dropped) {
			c.setVelocity(dir.normalize().multiply(3));
			
			for (BlockFace bf : BlockFace.values()) {
				if (c.getLocation().getBlock().getRelative(bf).getType() != Material.AIR) {

					
					ParUtils.createParticle(Particle.EXPLOSION_HUGE, loc, 0, 0, 0, 3, 1);
					new ExplosionDamage(4, 8, caster, c.getLocation(), name);
					new Repulsion(4, 1, caster, c.getLocation(), name);
					c.remove();
					
					dead = true;
				}
			}
		}
		
		--eggStop;
		if (swap() && egg > 0 && eggStop <= 0)
		{
			eggStop = 5;
			egg--;
			new BukkitRunnable() {
				int t = 0;
				public void run() {
					t++;
					if (t > 20) {
						this.cancel();
					}
					new Eggsplosive(caster,caster.getLocation().add(caster.getLocation().getDirection().multiply(3).add(randVector().multiply(0.5))), name);
				}
			}.runTaskTimer(main.plugin, 1, 1);
			
			
		}
		clearswap();
		
		
		
		loc = c.getLocation();
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
		playSound(Sound.ENTITY_CHICKEN_DEATH,loc,4,1);
		
		ParUtils.dropItemEffectRandomVector(loc, Material.FEATHER, 42, 43, 0.3);
		if (c != null)
		c.remove();
	}


}
