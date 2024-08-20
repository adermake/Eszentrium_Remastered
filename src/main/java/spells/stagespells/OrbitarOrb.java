package spells.stagespells;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;


public class OrbitarOrb extends Spell {
	
	Entity a;
	int offset;
	public OrbitarOrb(Player p,int offset,int steps,String namae,boolean ref) {
		refined = ref;
		name = namae;
		steprange = steps*2;
		hitSpell = true;
		castSpell(p,name);
		speed = 2;
		/*a = createArmorStand(p.getLocation());
		unHittable.add(a);
		a.setGravity(true);
		a.setHelmet(new ItemStack(Material.CACTUS));*/
		noTargetEntitys.add(a);
		hitboxSize = 2;
		
		this.offset = offset;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	public Entity spawnA() {

		if (!loc.getBlock().getType().isSolid()) {
			FallingBlock fb = caster.getWorld().spawnFallingBlock(loc.clone(), Material.CACTUS,(byte)  0);
			fb.setGravity(false);
			return fb;
		}
		else {
			return null;
		}
		
		
		
	}
	Location lastloc;
	boolean launched = false;
	@Override
	public void move() {
		
		if (caster.getGameMode() == GameMode.ADVENTURE)
			dead = true;
		// TODO Auto-generated method stub
		loc.setPitch(loc.getPitch()+90);;
		lastloc = loc;
		loc = ParUtils.stepCalcCircle(caster.getEyeLocation(),6+Math.sin(step/44), loc.getDirection(), 0, step+offset);
		//a.teleport(loc.clone().add(0,-0.5,0));
		//doPin(a, loc.clone().add(0,-0.5,0));
		if (a != null)
		a.remove();
		if (step > 1)
		a = spawnA();
		if (swap()) {
			launched = true;
			
		}
		if (launched && (step+offset+11) % 44 == 0) {
			new OrbitarShot(caster, loc,name,refined);
			dead = true;
		}
		
	}
	Vector dir = new Vector(0,0,0);
	@Override
	public void display() {
		// TODO Auto-generated method stub
		if (checkSilence())
			dead = true;
		if (lastloc == null)
			lastloc = loc;
		dir = loc.toVector().subtract(lastloc.toVector());
		
		ParUtils.createRedstoneParticle(loc.clone().add(0,0.5,0), 0.1F, 0.1F, 0.1F, 8, Color.GREEN, 0.6F);
			//ParUtils.createFlyingParticle(Particle.CRIT, loc.clone().add(0,2.5,0), 0, 0, 0, 1, 5, dir.normalize());
		//ParUtils.createRedstoneParticle(loc.clone().add(0,2.5,0), 0.5,0.5, 0.5, 1, Color.GREEN, 1.5F);
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, 0, 0, 0, 1, 0);
		playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,loc,1,1);
		//p.setVelocity(dir.multiply(speed/2));
		doKnockback(p, caster.getLocation(), 1);
		p.setVelocity(p.getVelocity().add(new Vector(0,1.5F,0)));
		damage(p, 2, caster);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, 0, 0, 0, 1, 0);
		playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,loc,1,1);
		doKnockback(ent, caster.getLocation(), 1);
		ent.setVelocity(ent.getVelocity().add(new Vector(0,1.5F,0)));
		damage(ent, 2, caster);
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
		if (a != null)
		a.remove();
	}
	
	public void setSpeed(int u) {
		steprange+=3;
		speed = u;
	}

	

}
