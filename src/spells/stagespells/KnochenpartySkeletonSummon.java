package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;
import spells.stagespells.VampirpilzStage2;

public class KnochenpartySkeletonSummon extends Spell{
	Vector vel;
	Location overrideLoc;
	Location origin;
	public KnochenpartySkeletonSummon(Player c,Location l,Vector dir) {
		vel = dir;
		origin = l.clone();
		caster = c;
		overrideLoc = l;
		cooldown = 20 * 62;
		steprange = 60;
		name = "§6Knochenparty";
		hitSpell = true;
		castSpell(caster, name);
	}
	boolean holding = true;
	static int in = 0;
	Item i;
	@Override
	public void setUp() {
		in++;
		if (in > 200000) {
			in = 0;
		}
		loc = overrideLoc;
		// TODO Auto-generated method stub
		ItemStack m = new ItemStack(Material.BONE);
		ItemMeta im = m.getItemMeta();
		im.setDisplayName(""+in);
		playSound(Sound.ENTITY_WITHER_SKELETON_HURT,loc,1.5F,30);
		m.setItemMeta(im);
		i = caster.getWorld().dropItem(loc, m);
		i.setPickupDelay(10000);
		//ar.addPassenger(i);
		i.setVelocity(vel);
		
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
		
		loc = i.getLocation();	
		if (i.isOnGround()) {
			
			dead = true;
		}
	}

	@Override
	public void display() {
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
		
			loc = i.getLocation();
			new KnochenpartySkeleton(caster,  loc.add(0,-1,0),new Vector(0,0.5F,0),origin,name);
			
			ParUtils.dropItemEffectRandomVector(loc, Material.BONE, 6, 50, 0.4);
			//ParUtils.createParticle(Particles.EXPLOSION, loc, 0, 0, 0, 5, 2);
			SoundUtils.playSound(Sound.ENTITY_SKELETON_AMBIENT, loc,1,10);

		
		
		i.remove();
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
	}

}
