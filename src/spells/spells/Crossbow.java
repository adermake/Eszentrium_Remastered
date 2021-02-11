package spells.spells;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.stagespells.BowArrow;
import spells.stagespells.CrossbowArrow;

public class Crossbow extends Spell {
	ArmorStand a = null;
	int activationstep = 20 * 27;
	Location moveTo;
	Location ori;
	public Crossbow(Player p,String name,boolean refined,Location l,Location moveTo) {
		ori = l.clone();
		steprange = 20 * 30;
		if (refined) {
			activationstep = 20;
		}
		this.moveTo = moveTo;
		castSpell(p, name);
	}
	@Override
	public void setUp() {
		unHittable.add(a);
		// TODO Auto-generated method stub
		loc = moveTo;//ori.add(0,-0.8,0);
		Location l1 = loc.clone().add(loc.getDirection().multiply(5));
		a = createArmorStand(loc.clone().setDirection(new Vector(1,0,0)));
		//a.setGravity(true);
		
		setCrossbow(true);
		//a.setVisible(true);
		a.setArms(true);
		
		a.setRightArmPose(new EulerAngle(Math.PI/2-Math.PI*2*l1.getPitch()/360,-Math.toRadians(80)+Math.PI*2*l1.getYaw()/360, 0));
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	boolean activated = false;
	Player target = null;
	int delay = 0;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		//doPull(a,moveTo,0.3);
		
		if (step % 4 == 0) {
			target = pointEntityCone(loc.clone(),caster);
			if (target != null && ! activated) {
				activated = true;
				step = activationstep;
				playSound(Sound.BLOCK_CONDUIT_ACTIVATE,loc,4,2);
			}
		}
		if (target != null) {
			Vector v = target.getLocation().toVector().subtract(a.getLocation().toVector());
			Location l1 = target.getLocation();
			l1.setDirection(v);
			a.setRightArmPose(new EulerAngle(Math.PI/2-Math.PI*2*l1.getPitch()/360,-Math.toRadians(80)+Math.PI*2*l1.getYaw()/360, 0));
			delay++;
			if (delay > 5) {
				delay = 0;
				//Arrow ar = (Arrow) spawnEntity(EntityType.ARROW,a.getLocation().add(v.normalize()).add(new Vector(0,1,0)));
				//		ar.setVelocity(v.multiply(3));
				new CrossbowArrow(caster, a, v, name, refined);
				playSound(Sound.ENTITY_ARROW_SHOOT, loc, 1, 1);
				setCrossbow(false);
			}
			
			if (delay == 2) {
				setCrossbow(true);
			}
		}
		
		
		
		for (Entity ent : caster.getWorld().getEntities()) {
			if (ent.getType() == EntityType.ARMOR_STAND) {
				if (ent != a && !unHittable.contains(ent)) {
					if (a.getLocation().distance(ent.getLocation())<hitboxSize+1) {
						dead = true;
					}
				}
				
			}
		}
		}
	
		

	@Override
	public void display() {
		// TODO Auto-generated method stub
		if (step % 10 == 0)
		ParUtils.createParticle(Particles.WITCH, loc.clone().add(0,1,0), 0.3, 0.3, 0.3, 1, 0);
		
		if (step % 10 == 0 && refined)
			ParUtils.createParticle(Particles.SNEEZE, loc.clone().add(0,1,0), 0.3, 0.3, 0.3, 1, 0);
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
		a.remove();
	}

	
	public void setCrossbow(boolean on) {
		if (on) {
			ItemStack is = new ItemStack(Material.CROSSBOW);
			CrossbowMeta cm = (CrossbowMeta) is.getItemMeta();
			ArrayList<ItemStack> ar = new ArrayList<ItemStack>();
			ar.add(new ItemStack(Material.ARROW));
		
			cm.setChargedProjectiles(ar);
			is.setItemMeta(cm);
			a.getEquipment().setItemInMainHand(is);
		}
		else {
			ItemStack is = new ItemStack(Material.CROSSBOW);
			a.getEquipment().setItemInMainHand(is);
		}
	}
}
