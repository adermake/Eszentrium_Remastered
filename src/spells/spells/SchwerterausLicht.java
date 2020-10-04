package spells.spells;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.SchwertausLicht;

public class SchwerterausLicht extends Spell{
	
	Location lolo;

	public SchwerterausLicht() {
		
		name = "§cSchwerter aus Licht";
		cooldown = 20*50;
		
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.PROJECTILE);
		
		
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		lolo = raycast(caster.getEyeLocation());
		summonSwords();
		dead = true;
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
		
	}
	
	
	public ArrayList<ArmorStand> summonSwords() {
		ArrayList<ArmorStand> swords = new ArrayList<ArmorStand>();
		Location l = caster.getLocation();
		double r = 5;
		for (double t = 0; t <= Math.PI;) {

			t = t + Math.PI / 18;

			double x = r * Math.cos(t+Math.PI-Math.PI/18);
			double y = 1;
			double z = r * Math.sin(t+Math.PI-Math.PI/18);
			Location j = l.clone();
			Vector v = new Vector(x, y, z);
			Matrix.rotateMatrixVectorFunktion(v, caster.getLocation());

			l.add(v.getX(), v.getY(), v.getZ());
			l.add(l.getDirection().multiply(-3));
			new SchwertausLicht(l.clone(),caster,name);
			ParUtils.createParticle(Particles.FLASH, l, 0, 0, 0, 1, 1);
			Vector ve = j.subtract(l).toVector();
			Location lala = l.clone();
			l.setDirection(lolo.toVector().subtract(lala.toVector()));
			
			l.add(l.getDirection().multiply(3));
			
			l.subtract(v.getX(), v.getY(), v.getZ());

		}
		
	
		
		return swords;
	}
	
	public Location raycast(Location loc) {
		Collection<? extends Player> plyers = Bukkit.getOnlinePlayers();
		loc = loc.clone();
		int end = 5000;
		for (int i = 0;(!loc.getBlock().getType().isSolid()) && i < end;i++) {
			loc.add(loc.getDirection().multiply(0.2));
			for (Player p :plyers) {
				if (p != caster) {
					if (p.getLocation().distance(loc) <= 1.4) { i = end;}
					if (p.getEyeLocation().distance(loc) <= 1.4) { i = end;}
				}
			}
			
		}
		
		return loc;
		
	}
	
}
