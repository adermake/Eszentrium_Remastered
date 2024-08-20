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
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class Wassergeysir extends Spell {

	
	ArrayList<FallingBlock> allBlocks = new ArrayList<FallingBlock>();
	public Wassergeysir() {
		
		cooldown = 20 * 20;
		name = "§6Wassergeysir";
		speed = 4;
		steprange = (int) (speed * 20 *5);
		casttime =10;
		hitboxSize = scale;
		canHitSelf = true;
		multihit =true;
		setLore("§7Beschwört einen Geysir um den anvisierten Block herum, der nach kurzer Verzögerung alle Spieler hochwirft und allen Gegnern Schaden zufügt.");
		setBetterLore("§7Beschwört einen Geysir um den anvisierten Block herum, der nach kurzer Verzögerung alle Spieler zum Anwender wirft und allen Gegnern Schaden zufügt.");
	}
	
	Location over; 
	
	public Wassergeysir(Location l, Player caster,String name,double scale) {
		over = l;
		refined = true;
		cooldown = 20 * 22;
		this.name = name;
		speed = 4;
		steprange = (int) (speed * 20 *5);
		casttime =5;
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
			//loc.add(0,1,0);
			loc.add(loc.getDirection().multiply(-1));
		
			loc.setDirection(caster.getLocation().getDirection());
		}
		
		if (over != null)
		loc = over;
		playSound(Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,loc,1,1);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		if (refined) {
			for (int i = 0;i<2;i++) {
				Location l = ParUtils.stepCalcCircle(loc, scale, loc.getDirection().multiply(-1),0, 2*cast+i*22);
				ParUtils.createFlyingParticle(Particle.CLOUD, l, 0, 0,0, 1,0.1, loc.getDirection().multiply(-1));
				//ParUtils.createParticle(Particle.CLOUD, l, 0, 1, 0, 0, 0.1);
			}
			ParUtils.createParticle(Particle.BUBBLE_POP, loc,scale/4, scale/4, scale/4, 20, 0);
			ParUtils.createRedstoneParticle(loc, scale/2,  scale/2, scale/2, 10, Color.fromRGB(51, 51,201),5);
			playSound(Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,loc,1,1);
		}
		else {
			for (int i = 0;i<2;i++) {
				Location l = ParUtils.stepCalcCircle(loc, scale, new Vector(0,1,0),0, 2*cast+i*22);
				
				ParUtils.createParticle(Particle.CLOUD, l, 0, 1, 0, 0, 0.1);
			}
			ParUtils.createParticle(Particle.BUBBLE_POP, loc,scale/4, 0.2, scale/4, 20, 0);
			ParUtils.createRedstoneParticle(loc, scale/2, 0, scale/2, 10, Color.fromRGB(51, 51,201),5);
			playSound(Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,loc,1,1);
		}
	
		
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
			if (refined) {
				b.setVelocity(loc.getDirection().multiply(-2).add(randVector().normalize().multiply(0.1)));
			}
			else {
				b.setVelocity(new Vector(0,2,0).add(randVector().normalize().multiply(0.1)));
			}
			
			allBlocks.add(b);
			if(refined) {
				ParUtils.createFlyingParticle(Particle.CLOUD, loc, 0.3, 1, 0.3, 10, (double)count/(double)maxCount,loc.getDirection().multiply(-1));
				
			}
			else {
				ParUtils.createFlyingParticle(Particle.CLOUD, loc, 0.3, 1, 0.3, 10, (double)count/(double)maxCount,new Vector(0,1,0));
				
			}
			}
		else {
			if (!launched) {
				
				launched = true;
				hitPlayer = false;
				hitEntity = false;
				hitSpell = false;
				/*
				if (refined == scale < 10) {
					new Wassergeysir(loc.clone(), caster, name, scale+3);
				}
				*/
			}
		}
		// TODO Auto-generated method stub
		
	
		
		
		for (FallingBlock bl : allBlocks) {
			if (bl.getVelocity().getY() < 0) {
				//ParUtils.createParticle(Particle.CLOUD, bl.getLocation(), 0.2,0.2, 0.2, 1, 0.01);
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
			if (other > 255) {
				other= 255;
			}
			if (other < 1) {
				other = 1;
			}
		
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
			if (refined) {
				p.setVelocity(loc.getDirection().multiply(-2.5));
			}
		
			else {
				p.setVelocity(new Vector(0,2.5F,0));
			}
			}
			
		else {
			if (!caster.isSneaking()) {
				if (refined) {
					p.setVelocity(loc.getDirection().multiply(-2.5));
				}
			
				else {
					p.setVelocity(new Vector(0,2.5F,0));
				}
			}
		}
		
		if (p != caster) {
			damage(p, 4, caster);
		}
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		if (refined) {
			ent.setVelocity(loc.getDirection().multiply(-2.5));
		}
	
		else {
			ent.setVelocity(new Vector(0,2.5F,0));
		}
		damage(ent, 4, caster);
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
