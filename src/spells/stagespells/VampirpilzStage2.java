package spells.stagespells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class VampirpilzStage2 extends Spell {

	Location place;
	public VampirpilzStage2(Player caster,String name,Location loca,boolean refined) {
		this.name = name;
		hitPlayer = true;
		this.refined = refined;
		hitEntity = true;
		hitboxSize = 11;
		this.caster = caster;
		place = loca;
		steprange = 20 * 15;
		castSpell(caster, name);
		multihit = true;
		addSpellType(SpellType.SUPPORT);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.AURA);
	}
	ArrayList<Entity> pulled = new ArrayList<Entity>();
	ArrayList<Entity> removeLater = new ArrayList<Entity>();
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		loc = place;
		for (float i = 0;i<2;i++) {
			ArmorStand a = createArmorStand(place.clone().add(0,i/3-0.4,0));
			a.setSmall(true);
			a.setGravity(false);
			a.setHelmet(new ItemStack(Material.MUSHROOM_STEM));
			removeLater.add(a);
		}
		ArmorStand a = createArmorStand(place.clone().add(0,-0.4,0));
		
		a.setGravity(false);
		a.setHelmet(new ItemStack(Material.RED_MUSHROOM_BLOCK));
		removeLater.add(a);
		aim = loc.clone();
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	Vector dir = new Vector(0,0,0);
	Location aim;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		for (Entity ent : removeLater) {
			Location l = ent.getLocation();
			l.setYaw(ent.getLocation().getYaw()+4);
			double d = step;
			l.add(0,Math.sin(d/3.14)/10,0);
			l.add(dir);
			ent.teleport(l);
		}
		if (step > 20 * 14) {
			hitboxSize = 0;
			hitPlayer = false;
			hitEntity = false;
			for (Entity ent : removeLater) {
				Location l = ent.getLocation();
				l.setYaw(ent.getLocation().getYaw()+4);
				double d = step;
				l.add(0,-0.1,0);
				ent.teleport(l);
			}
		}
		
		if (swap()) {
			Location naim = block(caster);
			if (naim != null)
				aim = naim;
		}
		
		if (refined) {
			loc = removeLater.get(0).getLocation();
			dir = aim.toVector().subtract(removeLater.get(0).getLocation().toVector());
			
			dir = dir.normalize().multiply(0.5);
			if (aim.distance(removeLater.get(0).getLocation())<0.3) {
				dir = new Vector(0,0,0);
			}
		}
	}

	int delay = 0;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		delay++;
		
		if (delay > 8) {
			delay = 0;
			ParUtils.dashParticleToRedstone(caster, loc, 0, Color.RED, 1);
		}
	}

	@Override
	public void onPlayerHit(Player p) {
		if (!pulled.contains(p)) {
			pulled.add(p);
			
			doPull(p, loc, 2);
		}
		// TODO Auto-generated method stub
		if (p.getNoDamageTicks()<= 1) {
			heal(caster, 2, caster);
			ParUtils.pullItemEffectVector(p.getLocation(), Material.NETHER_WART_BLOCK, 44, loc.clone().add(0,0.6,0),0.3F);
			damage(p,1,caster);
		}
		
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		if (!pulled.contains(ent)) {
			pulled.add(ent);
			
			doPull(ent, loc, 2);
		}
		if (ent.getNoDamageTicks()<= 1) {
			ParUtils.pullItemEffectVector(ent.getLocation(), Material.NETHER_WART_BLOCK, 44, loc.clone().add(0,0.6,0),0.3F);
		
			damage(ent,1,caster);
			
		}
		
		
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
		for( Entity e : removeLater) {
			e.remove();
		}
	}

	
	
}
