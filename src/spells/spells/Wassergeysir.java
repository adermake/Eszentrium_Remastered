package spells.spells;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;

public class Wassergeysir extends Spell {

	
	ArrayList<FallingBlock> allBlocks = new ArrayList<FallingBlock>();
	public Wassergeysir() {
		
		cooldown = 20 * 22;
		name = "§6Wassergeysir";
		speed = 4;
		steprange = (int) (speed * 20 *5);
		casttime =20;
		hitboxSize = scale;
		canHitSelf = true;
		multihit =true;
	}
	
	Location over; 
	
	public Wassergeysir(Location l, Player caster,String name,double scale) {
		over = l;
		refined = true;
		cooldown = 20 * 22;
		this.name = name;
		speed = 4;
		steprange = (int) (speed * 20 *5);
		casttime =10;
		hitboxSize = scale;
		canHitSelf = true;
		multihit =true;
		this.scale = scale;
		castSpell(caster, name);
		
	}
	double scale = 4;
	@Override
	public void setUp() {
		if (scale <= 4) {
			loc = block(caster);
			if (loc == null) {
				refund = true;
				dead = true;
			}
			loc.add(0,1,0);
		
			
		}
		
		if (over != null)
		loc = over;
		playSound(Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,loc,1,1);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		for (int i = 0;i<2;i++) {
			Location l = ParUtils.stepCalcCircle(loc, scale, new Vector(0,1,0),0, 2*cast+i*22);
			
			ParUtils.createParticle(Particles.CLOUD, l, 0, 1, 0, 0, 0.1);
		}
		ParUtils.createParticle(Particles.BUBBLE_POP, loc,scale/4, 0.2, scale/4, 20, 0);
		ParUtils.createRedstoneParticle(loc, scale/2, 0, scale/2, 10, Color.fromRGB(51, 51,201),5);
		playSound(Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,loc,1,1);
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		playSound(Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE,loc,1,1);
		maxCount = (int) (scale * 5);
		count = maxCount;
	}
	int count = 20;
	int maxCount = 20;
	boolean launched = false;
	@Override
	public void move() {
		if (count > 0) {
			count--;
			FallingBlock b = spawnFallingBlock(loc, Material.BLUE_STAINED_GLASS);
			b.setVelocity(new Vector(0,2,0).add(randVector().normalize().multiply(0.1)));
			allBlocks.add(b);
			ParUtils.createFlyingParticle(Particles.CLOUD, loc, 0.3, 1, 0.3, 10, (double)count/(double)maxCount,new Vector(0,1,0));
		}
		else {
			if (!launched) {
				
				launched = true;
				hitPlayer = false;
				hitEntity = false;
				hitSpell = false;
				if (refined == scale < 10) {
					new Wassergeysir(loc.clone(), caster, name, scale+3);
				}
			}
		}
		// TODO Auto-generated method stub
		
	
		
		
		for (FallingBlock bl : allBlocks) {
			if (bl.getVelocity().getY() < 0) {
				//ParUtils.createParticle(Particles.CLOUD, bl.getLocation(), 0.2,0.2, 0.2, 1, 0.01);
			}
			double maxHeight = 33;
			double factor = (bl.getLocation().getY()-loc.getY())/maxHeight;
			if (factor > 1)
				factor = 1;
			int blueness = (int) (255 * factor);
			if (blueness > 255) {
				blueness = 255;
			}
			if (blueness < 1) {
				blueness = 1;
			}
		
			int other = 50+(int) (200*factor*factor*factor*factor);
			
			double stepFactor = (float)count/(float) maxCount;
			ParUtils.createRedstoneParticle(bl.getLocation(), 0, 0, 0, 1, Color.fromRGB(other,other, (int)blueness), (float) (1*(1-stepFactor)));
		}
		
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (p != caster ) {
			p.setVelocity(new Vector(0,2.5F,0));
		}
		else {
			if (!caster.isSneaking()) {
				p.setVelocity(new Vector(0,2.5F,0));
			}
		}
		
		if (p != caster) {
			damage(p, 3, caster);
		}
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		ent.setVelocity(new Vector(0,2.5F,0));
		damage(ent, 3, caster);
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

}
