package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class SchwertausLicht extends Spell {

	Location saveLoc;
	boolean hp = false;
	boolean hb = false;
	
	public SchwertausLicht(Location l,Player c, String namae) {
		caster = c;
		loc = l;
		saveLoc = l;
		casttime = randInt(12,34);
		hitSpell = true;
		steprange = 200;
		speed = 8;
		hitboxSize = 0.3;
		name = namae;
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
		a.setRightArmPose(a.getRightArmPose().add(randDouble(-0.05, 0.05), 0, 0));
		if (checkSilence()) {
			toggleExplode = false;
			dead = true;
		}
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
		
	}

	@Override
	public void move() {
		loc.setDirection(loc.getDirection().add(randVector().multiply(0.016)));
		// TODO Auto-generated method stub
		saveLoc.add(saveLoc.getDirection());
		loc.add(loc.getDirection());
		a.teleport(saveLoc);
		
	}

	@Override
	public void display() {
		ParUtils.createParticle(Particles.CRIT, loc, 0.01, 0.01, 0.01, 1, 0);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		hp = true;
		dead = true;
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
		hb = true;
		dead = true;
		
	}

	@Override
	public void onDeath() {
		a.remove();
		if (toggleExplode) {
			new SwordExplosion(loc,caster, name);
		}
	}

	

	public void rotate(Vector dir) {
		a.setRightArmPose(EulerAngle.ZERO);
		
		EulerAngle ea = new EulerAngle((caster.getLocation().getPitch()/180)*Math.PI, 0, 0);
		
		a.setRightArmPose(ea);
	}
	
	
}
