package spells.stagespells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class SchwertausLicht extends Spell {

	Location saveLoc;
	boolean hp = false;
	boolean hb = false;
	
	public Entity target;

	public Vector velocity;
	public SchwertausLicht(Location l,Player c,Entity target,String namae) {
		caster = c;
		loc = l;
		saveLoc = l;
		casttime = 20+randInt(2,15);
		hitSpell = true;
		steprange = (int)target.getLocation().distance(loc);
		speed = 1;
		hitboxSize = 0.3;
		name = namae;
		this.target = target;
		velocity = c.getLocation().getDirection();
		castSpell(caster,namae);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
	}
	ArmorStand a;
	@Override
	public void setUp() {
		
		saveLoc.setDirection(saveLoc.getDirection().add(randVector().multiply(0.04)));
		loc = saveLoc.clone();
				
		a = (ArmorStand) caster.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		a.setItemInHand(new ItemStack(Material.GOLDEN_SWORD));
		
		a.setVisible(false);
		
		a.setGravity(false);
		a.setRightArmPose(EulerAngle.ZERO);
		
		EulerAngle ea = new EulerAngle((caster.getLocation().getPitch()/180)*Math.PI, 0, 0);
		
		a.setRightArmPose(ea);
		playSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE,loc,10,2);
				
		setCanBeSilenced(false);
		toggleExplode = true;
	}
	
	private boolean toggleExplode = true;

	@Override
	public void cast() {
		//a.setRightArmPose(a.getRightArmPose().add(randDouble(-0.05, 0.05), 0, 0));
		/*
		if (checkSilence()) {
			toggleExplode = false;
			dead = true;
		}
		*/
		rotate(velocity);
		a.teleport(loc);
		
	}

	@Override
	public void launch() {
		playSound(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH,loc,4,0.5F);
		// TODO Auto-generated method stub
		loc = saveLoc.clone();
		Vector rotMaLoc = new Vector(-0.4,0,0);
		Matrix.rotateMatrixVectorFunktion(rotMaLoc , loc.clone());
		loc.add(rotMaLoc);
		loc = loc.clone().add(0,1.5,0);
		ParUtils.createParticle(Particles.END_ROD, loc.clone().add(0,1.5,0), 0,0,0, 5, 0);
		//ParUtils.parKreisDot(Particle.END_ROD, loc, 1, -1, 1, loc.getDirection());
		//velocity = dirSave;
		
		
	}
	
	@Override
	public void move() {
		//loc.getDirection().add(randVector().multiply(0.016))

		
		Vector v = target.getLocation().toVector().subtract(loc.toVector());
		
		
		float lifetime = (float)(steprange-step);
		if (lifetime > 0) {
			velocity = homeVector(lifetime, velocity.clone(), v);
		}
		
		//velocity = velocity.midpoint(v);	
		loc.setDirection(velocity);
		
		// TODO Auto-generated method stub
		saveLoc.add(saveLoc.getDirection());
		loc.add(velocity);
		//
		
	
		
	}

	@Override
	public void display() {
		ParUtils.createParticle(Particles.CRIT, loc, 0.01, 0.01, 0.01, 1, 0);
		a.teleport(loc);
		rotate(velocity);
	}
	
	
	
	
	public Vector homeVector(float lifetime,Vector current,Vector aim) {
		//Bukkit.broadcastMessage("Life " + lifetime);
		///Bukkit.broadcastMessage("Cur "+current);
		//Bukkit.broadcastMessage("A "+aim);
		float acc = 5/(float)steprange;
		Vector vNorm = current.clone().normalize();
		Vector dNorm = aim.clone().normalize();
		
		Vector v = vNorm.clone().subtract(dNorm);
		//Bukkit.broadcastMessage("Z " +v);
		v = v.clone().multiply(Math.exp((1-lifetime)*acc));
		//Bukkit.broadcastMessage("A " +v);
		v = vNorm.clone().subtract(v);
		//Bukkit.broadcastMessage("B " +v);
		v = v.normalize();
		//Bukkit.broadcastMessage("C " +v);
		v = v.multiply(aim.length()/lifetime);
		//Bukkit.broadcastMessage("D " +v);		
		//Vector v = current.normalize().subtract((current.normalize().subtract(aim.normalize())).multiply(1/lifetime)).normalize().multiply(aim.length()/lifetime);
		//Bukkit.broadcastMessage("Out "+v);
		return v;
		
		
	}

	@Override
	public void onPlayerHit(Player p) {
		hp = true;
		dead = true;
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		dead =true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		if (step > 20) {
			
		
		hb = true;
		dead = true;
		}
	}

	@Override
	public void onDeath() {
		a.remove();
		if (toggleExplode) {
			new SwordExplosion(loc,caster,velocity, name);
		}
	}

	

	public void rotate(Vector dir) {
		a.setRightArmPose(EulerAngle.ZERO);
		
		EulerAngle ea = new EulerAngle((caster.getLocation().getPitch()/180)*Math.PI, 0, 0);
		
		a.setRightArmPose(ea);
	}
	
	
}
