package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class RuneDraw extends Spell {

	ArrayList<Vector> offsets = new ArrayList<Vector>();
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
	Vector origin;
	double vlen = 5;
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		origin = caster.getLocation().getDirection().multiply(vlen);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		//Matrix.alignLocRotation(l, relative)
		Vector v = caster.getLocation().getDirection().multiply(vlen);
		Vector unitNormal = caster.getLocation().getDirection().multiply(-1);
		Vector pv = v.clone().subtract(origin);
		double dist = pv.getX()* unitNormal.getX()+ pv.getY()*unitNormal.getY() + pv.getZ()*unitNormal.getY();
		v = v.add(unitNormal.normalize().multiply(dist));
		offsets.add(v);
		//ArrayList<Vector> removeLater = new ArrayList<Vector>();
		if (offsets.size() > 50) {
			offsets.remove(0);
		}
		
		for (Vector vec : offsets) {
			
			ParUtils.createParticle(Particles.BUBBLE, caster.getEyeLocation().add(vec), 0, 0, 0, 5, 0);
			
			
			
		}
		double distance = getAvgDistanceToCenter();
		Bukkit.broadcastMessage(""+distance);
	}
	
	
	public Vector getCenter() {
		Vector v = new Vector(0,0,0);
		for (Vector vec : offsets) {
			v.add(vec);
		}
		v.multiply(1D/(double)offsets.size());
		
		return v;
	}
	
	public double getAvgDistanceToCenter() {
		Vector v = getCenter();
		double distance = 0;
		for (Vector vec : offsets) {
			distance += v.distance(vec);
		}
		return distance/(double)offsets.size();
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

}
